/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.squareup.otto.Subscribe;

import at.jps.sanction.core.monitoring.jmx.JMXManager;
import at.jps.sanction.core.notifications.NotificationManager;
import at.jps.sanction.core.notifications.ShutdownStatus;
import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.Status;
import at.jps.sanction.model.listhandler.NoWordHitListHandler;
import at.jps.sanction.model.listhandler.OptimizationListHandler;
import at.jps.sanction.model.listhandler.ReferenceListHandler;
import at.jps.sanction.model.listhandler.SanctionListHandler;
import at.jps.sanction.model.listhandler.ValueListHandler;
import at.jps.sanction.model.queue.Queue;
import at.jps.sanction.model.queue.QueueEventListener;
import at.jps.sanction.model.sl.entities.WL_Entity;
import at.jps.sanction.model.worker.AnalyzerWorker;
import at.jps.sanction.model.worker.Worker;
import at.jps.sanction.model.worker.in.InputWorker;
import at.jps.sanction.model.worker.out.OutputWorker;

public class StreamManager implements Runnable {

    final static int                    QUEUESIZE             = 10000;
    final static int                    MAX_WORKER_IN         = 2;

    final static int                    MAX_WORKER_ANALYSE    = 5;
    final static int                    MAX_WORKER_OUT        = 2;

    private static final Logger         logger                = LoggerFactory.getLogger(StreamManager.class);

    private static final String         PERSISTENCE_UNIT_NAME = "embargo";

    private String                      streamName;
    private Properties                  properties;

    private int                         nrWorkersInput;
    private int                         nrWorkersOutputHit;
    private int                         nrWorkersOutputNoHit;
    private int                         nrWorkersError;
    private int                         nrWorkersChecker;
    private int                         nrWorkersPostHit;
    private int                         nrWorkersPostNoHit;

    private Queue<Message>              inputQueue;

    private Queue<AnalysisResult>       defectQueue;
    private Queue<AnalysisResult>       hitQueue;

    private Queue<AnalysisResult>       noHitQueue;

    private Queue<AnalysisResult>       postProcessHitQueue;
    private Queue<AnalysisResult>       postProcessNoHitQueue;

    private HashMap<String, Queue<?>>   queueDictionary;

    private ArrayList<OutputWorker>     errorWorkers;
    private ArrayList<AnalyzerWorker>   analyzeWorkers;
    private ArrayList<InputWorker>      inputWorkers;
    private ArrayList<OutputWorker>     outputWorkers;
    private ArrayList<OutputWorker>     ppHitWorkers;
    private ArrayList<OutputWorker>     ppNoHitWorkers;

    private OptimizationListHandler     txNoHitOptimizationListHandler;
    private OptimizationListHandler     txHitOptimizationListHandler;

    private NoWordHitListHandler        noWordHitListHandler;

    private int                         hitCntr;
    private int                         noHitCntr;
    private int                         errorCntr;
    private int                         inputCntr;

    private boolean                     purgeQueuesOnStartup;

    private static EntityManagerFactory entityManagerfactory;

    private EntityManager               entityManager;

    private StreamConfig                streamConfig;

    private ListConfigHolder            listConfig;

    public StreamManager() {
    }

    public StreamManager(final String streamName, final Properties properties) {
        this.streamName = streamName;
        this.properties = properties;
    }

    public void addToErrorList(final AnalysisResult analsysisResult) {
        getDefectQueue().addMessage(analsysisResult);
        incrementErrorCounter();
    }

    public void addToHitList(final AnalysisResult analsysisResult) {
        getHitQueue().addMessage(analsysisResult);
        incrementHitCounter();

        // DBHelper.saveNewAnalysisResult(analsysisResult);

        // // try to persist
        // EntityManager em = getEntityManager(null);
        // //
        // synchronized (this) {
        // if (em != null) {
        // DBHelper.saveNewAnalysisResult(em, analsysisResult);
        // }
        // }

    }

    public void addToInputList(final Message message) {

        // DBHelper.saveMessage(message);

        getInputQueue().addMessage(message);

        incrementInputCounter();
    }

    public void addToNoHitList(final AnalysisResult analsysisResult) {
        getNoHitQueue().addMessage(analsysisResult);
        incrementNoHitCounter();
    }

    public List<String> getAllFieldsToBIC() {
        return streamConfig.getFields2BIC();
    }

    public List<String> getAllFieldsToCheck() {
        return streamConfig.getFields2Check();
    }

    Properties getConfiguration() {

        if (properties == null) {
            // read
            properties = new Properties();
        }

        return properties;
    }

    protected Queue<AnalysisResult> getDefectQueue() {
        return defectQueue;
    }

    public int getErrorCntr() {
        return errorCntr;
    }

    // private void getFields2BIC() {
    // final String allFields = getProperties().getProperty(getStreamName() + ".BICFilds");
    // setBICFields(allFields);
    // }
    //
    // private void getFields2Check() {
    // final String allFields = getProperties().getProperty(getStreamName() + ".checkFields");
    // setCheckFields(allFields);
    // }
    //
    // private void getFuzzyFields() {
    // final String allFields = getProperties().getProperty(getStreamName() + ".fuzzyFields");
    // setFuzzyFields(allFields);
    // }

    Properties getFileConfiguration() {
        return new Properties();
    }

    public int getHitCntr() {
        return hitCntr;
    }

    // TO BE Overwritten !!!
    public ValueListHandler getIndexAusschlussList() {
        return null;
    }

    public int getInputCntr() {
        return inputCntr;
    }

    public Queue<Message> getInputQueue() {
        return inputQueue;
    }

    public int getMinAbsVal() {
        return streamConfig.getMinAbsVal();
    }

    public int getMinRelVal() {
        return streamConfig.getMinRelVal();
    }

    public int getMinTokenLen() {
        return streamConfig.getMinTokenLen();
    }

    public double getFuzzyValue() {
        return streamConfig.getFuzzyValue();
    }

    public int getNoHitCntr() {
        return noHitCntr;
    }

    protected Queue<AnalysisResult> getHitQueue() {
        return hitQueue;
    }

    protected Queue<AnalysisResult> getNoHitQueue() {
        return noHitQueue;
    }

    public ValueListHandler getNotSingleWordHitList() {
        return null;
    }

    public Properties getProperties() {
        return properties;
    }

    public HashMap<String, ReferenceListHandler> getReferenceListHandlers() {
        return listConfig.getReferenceLists();
    }

    public HashMap<String, SanctionListHandler> getSanctionListHandlers() {
        return listConfig.getWatchLists();
    }

    // TO BE Overwritten !!
    public ValueListHandler getStopwordList() {
        return null;
    }

    public String getStreamName() {
        return streamName;
    }

    public HashMap<String, ValueListHandler> getValueListHandlers() {
        return listConfig.getValueLists();
    }

    public synchronized int incrementErrorCounter() {
        return errorCntr++;
    }

    public synchronized int incrementHitCounter() {
        return hitCntr++;
    }

    public synchronized int incrementInputCounter() {
        return inputCntr++;
    }

    public synchronized int incrementNoHitCounter() {
        return noHitCntr++;
    }

    public void close() {
        shutdown();
    }

    public void initialize() {

        NotificationManager.register(this);

        // own global base settings
        /*
         * minTokenLen = Integer.parseInt(properties.getProperty(streamName + ".minTokenLen", "2")); minRelVal = Integer.parseInt(properties.getProperty(streamName + ".minRelVal", "79")); minAbsVal =
         * Integer.parseInt(properties.getProperty(streamName + ".minAbsVal", "79")); fuzzyVal = Integer.parseInt(properties.getProperty(streamName + ".fuzzyVal", "20")); fuzzyVal /= 100; // from % to
         * comma getFields2Check(); getFields2BIC(); getFuzzyFields(); // build queues if (logger.isInfoEnabled()) { logger.info("setup Queues..."); } inputQueue =
         * BaseFactory.createStreamInputQueue(this); defectQueue = BaseFactory.createStreamDefectQueue(this); hitQueue = BaseFactory.createStreamHitQueue(this); nohitQueue =
         * BaseFactory.createStreamNoHitQueue(this); postProcessHitQueue = BaseFactory.createStreamPostProcessHitQueue(this); postProcessNoHitQueue =
         * BaseFactory.createStreamPostProcessNoHitQueue(this);
         */

        // check queues
        if (inputQueue == null) {
            logger.error("Queue Implementatioin missing -- INPUTQUEUE");
            System.exit(-1);
        }

        if (defectQueue == null) {
            logger.error("Queue Implementatioin missing -- ERRORQUEUE");
            System.exit(-1);
        }

        if (hitQueue == null) {
            logger.error("Queue Implementatioin missing -- HITQUEUE");
            System.exit(-1);
        }

        if (noHitQueue == null) {
            logger.error("Queue Implementatioin missing -- NOHITQUEUE");
            System.exit(-1);
        }

        // add to dictionary

        getQueueDictionary().put(inputQueue.getName(), inputQueue);
        getQueueDictionary().put(defectQueue.getName(), defectQueue);
        getQueueDictionary().put(hitQueue.getName(), hitQueue);
        getQueueDictionary().put(noHitQueue.getName(), noHitQueue);
        getQueueDictionary().put(postProcessHitQueue.getName(), postProcessHitQueue);
        getQueueDictionary().put(postProcessNoHitQueue.getName(), postProcessNoHitQueue);

        if (isPurgeQueuesOnStartup()) {
            if (logger.isInfoEnabled()) {
                logger.info("CLEANUP Queues !!");
            }
            inputQueue.clear();
            defectQueue.clear();
            hitQueue.clear();
            noHitQueue.clear();
        }

        if (logger.isInfoEnabled()) {
            logger.info("setup Queues done");
            // analyzer
        }

        if (logger.isInfoEnabled()) {
            logger.info("setup Analyzers...");
        }

        // int size = BaseFactory.getNumberOfCheckers(this);
        int size = getNrWorkersChecker();
        analyzeWorkers = new ArrayList<AnalyzerWorker>(size);

        for (int i = 0; i < size; i++) {

            // final AnalyzerWorker worker = BaseFactory.createAnaylzer(this);
            final AnalyzerWorker worker = (AnalyzerWorker) ListConfigHolder.getApplicationContext().getBean("MessageAnalyser");
            worker.setStreamManager(this);
            analyzeWorkers.add(worker);

        }
        if (logger.isInfoEnabled()) {
            logger.info("setup Analyzers done");
        }
        // input
        if (logger.isInfoEnabled()) {
            logger.info("setup Input Processors...");
        }
        // size = BaseFactory.getNumberOfInputWorkers(this);
        size = getNrWorkersInput();
        inputWorkers = new ArrayList<InputWorker>(size);

        for (int i = 0; i < size; i++) {

            // final InputWorker worker = BaseFactory.createInputWorker(this);
            final InputWorker worker = (InputWorker) ListConfigHolder.getApplicationContext().getBean("InputProcessor");
            worker.setStreamManager(this);
            inputWorkers.add(worker);

        }
        if (logger.isInfoEnabled()) {
            logger.info("setup Input Processors done");
        }

        // output
        if (logger.isInfoEnabled()) {
            logger.info("setup Output Processors...");
        }
        // size = BaseFactory.getNumberOfOutputHitWorkers(this);
        size = getNrWorkersOutputHit();
        outputWorkers = new ArrayList<OutputWorker>(size);

        for (int i = 0; i < size; i++) {

            // final OutputWorker worker = BaseFactory.createOutputWorker(this);
            final OutputWorker worker = (OutputWorker) ListConfigHolder.getApplicationContext().getBean("OutputProcessor");
            worker.setStreamManager(this);
            outputWorkers.add(worker);

            worker.setQueue(hitQueue);

        }
        // size = BaseFactory.getNumberOfOutputNoHitWorkers(this);
        size = getNrWorkersOutputNoHit();
        if (outputWorkers == null) {
            outputWorkers = new ArrayList<OutputWorker>(size);
        }

        for (int i = 0; i < size; i++) {

            // final OutputWorker worker = BaseFactory.createOutputWorker(this);
            final OutputWorker worker = (OutputWorker) ListConfigHolder.getApplicationContext().getBean("OutputProcessor");
            worker.setStreamManager(this);
            outputWorkers.add(worker);

            worker.setQueue(noHitQueue);
        }

        if (logger.isInfoEnabled()) {
            logger.info("setup Output Processors done");
        }

        // output
        if (logger.isInfoEnabled()) {
            logger.info("setup Error Processors...");
        }

        // size = BaseFactory.getNumberOfErrorWorkers(this);
        size = getNrWorkersError();
        errorWorkers = new ArrayList<OutputWorker>(size);

        for (int i = 0; i < size; i++) {

            // final OutputWorker worker = BaseFactory.createErrorWorker(this);
            final OutputWorker worker = (OutputWorker) ListConfigHolder.getApplicationContext().getBean("ErrorProcessor");
            worker.setStreamManager(this);
            errorWorkers.add(worker);

            worker.setQueue(defectQueue);
        }
        logger.info("setup Error Processors done");

        // postprocess
        if (logger.isInfoEnabled()) {
            logger.info("setup Post Processors...");
        }

        // size = BaseFactory.getNumberOfPPHitWorkers(this);
        size = getNrWorkersPostHit();
        if (size > 0) {
            ppHitWorkers = new ArrayList<OutputWorker>(size);

            for (int i = 0; i < size; i++) {

                // final OutputWorker worker = BaseFactory.createPostProcessHitWorker(this);
                final OutputWorker worker = (OutputWorker) ListConfigHolder.getApplicationContext().getBean("PostProcessorHit");
                worker.setStreamManager(this);
                ppHitWorkers.add(worker);

                worker.setQueue(postProcessHitQueue);
            }
        }

        // size = BaseFactory.getNumberOfPPNoHitWorkers(this);
        size = getNrWorkersPostNoHit();
        if (size > 0) {
            ppNoHitWorkers = new ArrayList<OutputWorker>(size);

            for (int i = 0; i < size; i++) {

                // final OutputWorker worker = BaseFactory.createPostProcessNoHitWorker(this);
                final OutputWorker worker = (OutputWorker) ListConfigHolder.getApplicationContext().getBean("PostProcessorNoHit");
                worker.setStreamManager(this);
                ppNoHitWorkers.add(worker);

                worker.setQueue(postProcessNoHitQueue);
            }
        }

        logger.info("setup Post Processors done");

        JMXManager.registerBeans(this);
        logger.info("setup JMX done");

    }

    public boolean isFieldToCheck(final String fieldName, final String listName) {
        return (getAllFieldsToCheck() == null) || (getAllFieldsToCheck().contains(fieldName));
    }

    public boolean isField2BICTranslate(final String fieldName) {
        return (getAllFieldsToBIC() == null) || (getAllFieldsToBIC().contains(fieldName));
    }

    public boolean isField2Fuzzy(final String fieldName) {
        return (streamConfig.getFuzzyFields() == null) || (streamConfig.getFuzzyFields().contains(fieldName));
    }

    private void reportCounterStatus() {
        if (logger.isInfoEnabled()) {
            final StringBuilder status = new StringBuilder();

            status.append(System.getProperty("line.separator")).append("\t").append(getStreamName()).append(" : ").append("Input: ").append("\t").append(getInputCntr())
                    .append(System.getProperty("line.separator"));
            status.append("\t").append(getStreamName()).append(" : ").append("Defects: ").append("\t").append(getErrorCntr()).append(System.getProperty("line.separator"));
            status.append("\t").append(getStreamName()).append(" : ").append("Hits: ").append("\t").append(getHitCntr()).append(System.getProperty("line.separator"));
            status.append("\t").append(getStreamName()).append(" : ").append("NoHits: ").append("\t").append(getNoHitCntr()).append(System.getProperty("line.separator"));

            logger.info(status.toString());
        }
    }

    private void reportQueueStatus() {

        if (logger.isInfoEnabled()) {
            final StringBuilder status = new StringBuilder();

            status.append(System.getProperty("line.separator")).append("\t").append(getStreamName()).append(" : ").append("InQueue: ").append("\t").append(inputQueue.size())
                    .append(System.getProperty("line.separator"));
            status.append("\t").append(getStreamName()).append(" : ").append("DefectQueue: ").append("\t").append(defectQueue.size()).append(System.getProperty("line.separator"));
            status.append("\t").append(getStreamName()).append(" : ").append("HitQueue: ").append("\t").append(hitQueue.size()).append(System.getProperty("line.separator"));
            status.append("\t").append(getStreamName()).append(" : ").append("NoHitQueue: ").append("\t").append(noHitQueue.size()).append(System.getProperty("line.separator"));
            if (postProcessHitQueue != null) {
                status.append("\t").append(getStreamName()).append(" : ").append("PostHitQueue: ").append("\t").append(postProcessHitQueue.size()).append(System.getProperty("line.separator"));
            }
            if (postProcessNoHitQueue != null) {
                status.append("\t").append(getStreamName()).append(" : ").append("PostNoHitQueue: ").append("\t").append(postProcessNoHitQueue.size()).append(System.getProperty("line.separator"));
            }

            logger.info(status.toString());
        }
    }

    @Override
    public void run() {
        // start all threads

        if (logger.isInfoEnabled()) {
            logger.info("start all workers");
        }

        for (final InputWorker worker : inputWorkers) {
            startWorker(worker);
        }

        for (final OutputWorker worker : outputWorkers) {
            startWorker(worker);
        }

        for (final AnalyzerWorker worker : analyzeWorkers) {
            startWorker(worker);
        }

        for (final OutputWorker worker : errorWorkers) {
            startWorker(worker);
        }

        if (ppHitWorkers != null) {
            for (final Worker worker : ppHitWorkers) {
                startWorker(worker);
            }
        }
        if (ppNoHitWorkers != null) {
            for (final Worker worker : ppNoHitWorkers) {
                startWorker(worker);
            }
        }

        if (logger.isInfoEnabled()) {
            logger.info("all workers are up an working");
        }

        // loop -it
        while (Status.isRunning()) {
            try {
                Thread.sleep(2000);

                reportQueueStatus();
                reportCounterStatus();

            }
            catch (final InterruptedException e) {
            }
        }

    }

    public void setDefectQueue(final Queue<AnalysisResult> defectQueue) {
        this.defectQueue = defectQueue;
    }

    public void setErrorCntr(final int errorCntr) {
        this.errorCntr = errorCntr;
    }

    public void setHitCntr(final int hitCntr) {
        this.hitCntr = hitCntr;
    }

    public void setHitQueue(final Queue<AnalysisResult> hitQueue) {
        this.hitQueue = hitQueue;
    }

    public void setInputCntr(final int inputCntr) {
        this.inputCntr = inputCntr;
    }

    public void setInputQueue(final Queue<Message> inputQueue) {
        this.inputQueue = inputQueue;
    }

    public void setNoHitCntr(final int noHitCntr) {
        this.noHitCntr = noHitCntr;
    }

    public void setNoHitQueue(final Queue<AnalysisResult> nohitQueue) {
        this.noHitQueue = nohitQueue;
    }

    public void setProperties(final Properties properties) {
        this.properties = properties;
    }

    public void setStreamName(final String streamName) {
        this.streamName = streamName;
    }

    public void shutdown() {
        Status.shutdown();

        JMXManager.deregisterBeans(this);
        logger.info("shutdown JMX done");

        getInputQueue().close();
        getDefectQueue().close();
        getNoHitQueue().close();
        getHitQueue().close();

        if (postProcessNoHitQueue != null) {
            postProcessNoHitQueue.close();
        }
        if (postProcessHitQueue != null) {
            postProcessHitQueue.close();
        }

        if (entityManager != null) entityManager.close();
    }

    @Subscribe
    public void shutdownEvent(final ShutdownStatus event) {

        shutdown();
    }

    private void startWorker(final Worker worker) {

        if (logger.isInfoEnabled()) {
            logger.info("starting worker: " + worker.getClass().getCanonicalName());
        }

        final Thread thread = new Thread(worker);
        thread.setDaemon(true);
        thread.start();
    }

    public OptimizationListHandler getTxNoHitOptimizationListHandler() {
        return txNoHitOptimizationListHandler;
    }

    public void setTxNoHitOptimizationListHandler(OptimizationListHandler optimizationListHandler) {
        this.txNoHitOptimizationListHandler = optimizationListHandler;
    }

    public OptimizationListHandler getTxHitOptimizationListHandler() {
        return txHitOptimizationListHandler;
    }

    public void setTxHitOptimizationListHandler(OptimizationListHandler optimizationListHandler) {
        this.txHitOptimizationListHandler = optimizationListHandler;
    }

    public NoWordHitListHandler getNoWordHitListHandler() {
        return noWordHitListHandler;
    }

    public void setNoWordHitListHandler(NoWordHitListHandler noWordHitListHandler) {
        this.noWordHitListHandler = noWordHitListHandler;
    }

    public EntityManager getEntityManager(String pesistanceUnit) {
        if (entityManager == null) {
            if (pesistanceUnit == null) {
                pesistanceUnit = PERSISTENCE_UNIT_NAME;
            }
            try {
                if (logger.isInfoEnabled()) {
                    logger.info("Try to initialize PersistanceLayer for unit: " + pesistanceUnit);
                }

                if (entityManagerfactory == null) {
                    entityManagerfactory = Persistence.createEntityManagerFactory(pesistanceUnit);
                }

                entityManager = entityManagerfactory.createEntityManager();

                if (logger.isInfoEnabled()) {
                    logger.info("Initialization of PersistanceLayer for unit: " + pesistanceUnit + " - SUCCESSFULL");
                }

            }
            catch (Exception x) {
                if (logger.isErrorEnabled()) {
                    logger.error("Initialization of PersistanceLayer for unit: " + pesistanceUnit + " - FAILED");
                    logger.error("Exception ", x);
                }
            }
        }
        return entityManager;
    }

    public void addInputQueueListener(final QueueEventListener queueEventListener) {
        getInputQueue().addListener(queueEventListener);
    }

    public void addHitQueueListener(final QueueEventListener queueEventListener) {
        getHitQueue().addListener(queueEventListener);
    }

    public void addNoHitQueueListener(final QueueEventListener queueEventListener) {
        getNoHitQueue().addListener(queueEventListener);
    }

    public void addDefectQueueListener(final QueueEventListener queueEventListener) {
        getDefectQueue().addListener(queueEventListener);
    }

    public HashMap<String, Queue<?>> getQueueDictionary() {

        if (queueDictionary == null) {
            queueDictionary = new HashMap<String, Queue<?>>();
        }
        return queueDictionary;
    }

    public WL_Entity getSanctionListEntityDetails(final String listname, final String id) {

        SanctionListHandler sanctionListHandler = listConfig.getWatchListByName(listname);
        WL_Entity entity = sanctionListHandler.getEntityById(id);

        return entity;
    }

    public boolean isPurgeQueuesOnStartup() {
        return purgeQueuesOnStartup;
    }

    public void setPurgeQueuesOnStartup(boolean purgeQueuesOnStartup) {
        this.purgeQueuesOnStartup = purgeQueuesOnStartup;
    }

    public int getNrWorkersInput() {
        return nrWorkersInput;
    }

    public void setNrWorkersInput(int nrWorkersInput) {
        this.nrWorkersInput = nrWorkersInput;
    }

    public int getNrWorkersOutputHit() {
        return nrWorkersOutputHit;
    }

    public void setNrWorkersOutputHit(int nrWorkersOutputHit) {
        this.nrWorkersOutputHit = nrWorkersOutputHit;
    }

    public int getNrWorkersOutputNoHit() {
        return nrWorkersOutputNoHit;
    }

    public void setNrWorkersOutputNoHit(int nrWorkersOutputNoHit) {
        this.nrWorkersOutputNoHit = nrWorkersOutputNoHit;
    }

    public int getNrWorkersError() {
        return nrWorkersError;
    }

    public void setNrWorkersError(int nrWorkersError) {
        this.nrWorkersError = nrWorkersError;
    }

    public int getNrWorkersChecker() {
        return nrWorkersChecker;
    }

    public void setNrWorkersChecker(int nrWorkersChecker) {
        this.nrWorkersChecker = nrWorkersChecker;
    }

    public int getNrWorkersPostHit() {
        return nrWorkersPostHit;
    }

    public void setNrWorkersPostHit(int nrWorkersPostHit) {
        this.nrWorkersPostHit = nrWorkersPostHit;
    }

    public int getNrWorkersPostNoHit() {
        return nrWorkersPostNoHit;
    }

    public void setNrWorkersPostNoHit(int nrWorkersPostNoHit) {
        this.nrWorkersPostNoHit = nrWorkersPostNoHit;
    }

    public Queue<AnalysisResult> getPostProcessHitQueue() {
        return postProcessHitQueue;
    }

    public void setPostProcessHitQueue(Queue<AnalysisResult> postProcessHitQueue) {
        this.postProcessHitQueue = postProcessHitQueue;
    }

    public Queue<AnalysisResult> getPostProcessNoHitQueue() {
        return postProcessNoHitQueue;
    }

    public void setPostProcessNoHitQueue(Queue<AnalysisResult> postProcessNoHitQueue) {
        this.postProcessNoHitQueue = postProcessNoHitQueue;
    }

    public StreamConfig getStreamConfig() {
        return streamConfig;
    }

    public void setStreamConfig(StreamConfig streamConfig) {
        this.streamConfig = streamConfig;
    }

    public ListConfigHolder getListConfig() {
        return listConfig;
    }

    public void setListConfig(ListConfigHolder listConfig) {
        this.listConfig = listConfig;
    }

}
