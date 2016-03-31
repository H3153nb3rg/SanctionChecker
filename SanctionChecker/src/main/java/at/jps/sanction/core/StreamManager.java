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
import at.jps.sanction.domain.payment.PaymentListConfigHolder;
import at.jps.sanction.model.Status;
import at.jps.sanction.model.listhandler.NoWordHitListHandler;
import at.jps.sanction.model.listhandler.OptimizationListHandler;
import at.jps.sanction.model.listhandler.ReferenceListHandler;
import at.jps.sanction.model.listhandler.SanctionListHandler;
import at.jps.sanction.model.listhandler.ValueListHandler;
import at.jps.sanction.model.queue.Queue;
import at.jps.sanction.model.queue.QueueEventListener;
import at.jps.sanction.model.wl.entities.WL_Entity;
import at.jps.sanction.model.worker.Worker;

public class StreamManager implements Runnable {

    final static int                    QUEUESIZE             = 10000;
    final static int                    MAX_WORKER_IN         = 2;

    final static int                    MAX_WORKER_ANALYSE    = 5;
    final static int                    MAX_WORKER_OUT        = 2;

    private static final Logger         logger                = LoggerFactory.getLogger(StreamManager.class);

    private static final String         PERSISTENCE_UNIT_NAME = "embargo";

    private String                      streamName;
    private Properties                  properties;

    private ArrayList<Worker>           workers;

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

    Properties getFileConfiguration() {
        return new Properties();
    }

    // TO BE Overwritten !!!
    public ValueListHandler getIndexAusschlussList() {
        return null;
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

    public void close() {
        shutdown();
    }

    public void initialize() {

        assert getWorkers() != null : "no workers specified - see configuration";

        NotificationManager.register(this);

        // JMX BEAN <--
        JMXManager.registerBeans(this);
        logger.info("setup JMX done");

    }

    public boolean isFieldToCheck(final String fieldName, final String listName, final String entityType) {
        return streamConfig.isFieldToCheck(fieldName, listName, entityType, false);
    }

    public boolean isField2BICTranslate(final String fieldName) {
        return streamConfig.isField2BICTranslate(fieldName, false);
    }

    public boolean isField2IBANCheck(final String fieldName) {
        return streamConfig.isField2IBANCheck(fieldName, false);
    }

    public boolean isField2Fuzzy(final String fieldName) {
        return streamConfig.isField2Fuzzy(fieldName, false);
    }

    // private void reportCounterStatus() {
    // if (logger.isInfoEnabled()) {
    // final StringBuilder status = new StringBuilder();
    //
    // // status.append(System.getProperty("line.separator")).append("\t").append(getStreamName()).append(" : ").append("Input: ").append("\t").append(getInputCntr())
    // // .append(System.getProperty("line.separator"));
    // // status.append("\t").append(getStreamName()).append(" : ").append("Defects: ").append("\t").append(getErrorCntr()).append(System.getProperty("line.separator"));
    // // status.append("\t").append(getStreamName()).append(" : ").append("Hits: ").append("\t").append(getHitCntr()).append(System.getProperty("line.separator"));
    // // status.append("\t").append(getStreamName()).append(" : ").append("NoHits: ").append("\t").append(getNoHitCntr()).append(System.getProperty("line.separator"));
    //
    // logger.info(status.toString());
    // }
    // }

    private void reportQueueStatus() {

        if (logger.isInfoEnabled()) {
            final StringBuilder status = new StringBuilder();

            // status.append(System.getProperty("line.separator")).append("\t").append(getStreamName()).append(" : ").append("InQueue: ").append("\t").append(inputQueue.size())
            // .append(System.getProperty("line.separator"));
            // status.append("\t").append(getStreamName()).append(" : ").append("DefectQueue: ").append("\t").append(defectQueue.size()).append(System.getProperty("line.separator"));
            // status.append("\t").append(getStreamName()).append(" : ").append("HitQueue: ").append("\t").append(hitQueue.size()).append(System.getProperty("line.separator"));
            // status.append("\t").append(getStreamName()).append(" : ").append("NoHitQueue: ").append("\t").append(noHitQueue.size()).append(System.getProperty("line.separator"));
            // if (postProcessHitQueue != null) {
            // status.append("\t").append(getStreamName()).append(" : ").append("PostHitQueue: ").append("\t").append(postProcessHitQueue.size()).append(System.getProperty("line.separator"));
            // }
            // if (postProcessNoHitQueue != null) {
            // status.append("\t").append(getStreamName()).append(" : ").append("PostNoHitQueue: ").append("\t").append(postProcessNoHitQueue.size()).append(System.getProperty("line.separator"));
            // }

            logger.info(status.toString());
        }
    }

    @Override
    public void run() {
        // start all threads

        if (logger.isInfoEnabled()) {
            logger.info("start all workers");
        }

        for (final Worker worker : workers) {
            worker.setStreamManager(this);

            startWorker(worker);
        }

        if (logger.isInfoEnabled()) {
            logger.info("all workers are up an working");
        }

        // loop -it
        while (Status.isRunning()) {
            try {
                Thread.sleep(2000);

                reportQueueStatus();
                // reportCounterStatus();

            }
            catch (final InterruptedException e) {
            }
        }

    }

    public void shutdown() {
        Status.shutdown();

        JMXManager.deregisterBeans(this);
        logger.info("shutdown JMX done");

        if (entityManager != null) {
            entityManager.close();
        }
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

        OptimizationListHandler listHandler = null;

        if (listConfig instanceof PaymentListConfigHolder) {
            listHandler = ((PaymentListConfigHolder) listConfig).getTxNoHitOptimizationListHandler();
        }

        return listHandler;
    }

    public OptimizationListHandler getTxHitOptimizationListHandler() {

        OptimizationListHandler listHandler = null;

        if (listConfig instanceof PaymentListConfigHolder) {
            listHandler = ((PaymentListConfigHolder) listConfig).getTxHitOptimizationListHandler();
        }

        return listHandler;
    }

    public NoWordHitListHandler getNoWordHitListHandler() {
        NoWordHitListHandler listHandler = null;

        if (listConfig instanceof PaymentListConfigHolder) {
            listHandler = ((PaymentListConfigHolder) listConfig).getNoWordHitListHandler();
        }

        return listHandler;

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
            catch (final Exception x) {
                if (logger.isErrorEnabled()) {
                    logger.error("Initialization of PersistanceLayer for unit: " + pesistanceUnit + " - FAILED");
                    logger.error("Exception ", x);
                }
            }
        }
        return entityManager;
    }

    public void addQueueListener(final String queueName, final QueueEventListener queueEventListener) {

        final Queue<?> queue = getQueueDictionary().get(queueName);
        if (queue != null) {
            queue.addListener(queueEventListener);
        }
    }

    public HashMap<String, Queue<?>> getQueueDictionary() {

        return getStreamConfig().getQueues();
    }

    public WL_Entity getSanctionListEntityDetails(final String listname, final String id) {

        final SanctionListHandler sanctionListHandler = listConfig.getWatchListByName(listname);
        final WL_Entity entity = sanctionListHandler.getEntityById(id);

        return entity;
    }

    public StreamConfig getStreamConfig() {
        return streamConfig;
    }

    public void setStreamConfig(final StreamConfig streamConfig) {
        this.streamConfig = streamConfig;
    }

    public ListConfigHolder getListConfig() {
        return listConfig;
    }

    public void setListConfig(final ListConfigHolder listConfig) {
        this.listConfig = listConfig;
    }

    public ArrayList<Worker> getWorkers() {
        if (workers == null) {
            workers = new ArrayList<Worker>();
        }
        return workers;
    }

    public void setWorkers(final ArrayList<Worker> workers) {
        this.workers = workers;
    }

    public void setStreamName(final String streamName) {
        this.streamName = streamName;
    }

}
