/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.core;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.PropertyKeys;
import at.jps.sanction.model.io.file.FileParser;
import at.jps.sanction.model.listhandler.NoWordHitListHandler;
import at.jps.sanction.model.listhandler.OptimizationListHandler;
import at.jps.sanction.model.listhandler.ReferenceListHandler;
import at.jps.sanction.model.listhandler.SanctionListHandler;
import at.jps.sanction.model.listhandler.ValueListHandler;
import at.jps.sanction.model.queue.Queue;
import at.jps.sanction.model.worker.AnalyzerWorker;
import at.jps.sanction.model.worker.in.InputWorker;
import at.jps.sanction.model.worker.out.OutputWorker;

@Deprecated
public class BaseFactory {

    static final Logger logger = LoggerFactory.getLogger(BaseFactory.class);

    public static AnalyzerWorker createAnaylzer(final StreamManager manager) {
        AnalyzerWorker worker = null;
        try {
            worker = (AnalyzerWorker) getImplementationClassInstance(manager, PropertyKeys.PROP_ANALYZER_IMPL);
            worker.setStreamManager(manager);

        }
        catch (final Exception x) {
            logger.error("Wrong implementation specified: " + x.getMessage());
            logger.debug("Exception: ", x);

        }
        return worker;
    }

    private static Object createClassInstance(final String classname)

    {
        Object newinstance = null;
        try {
            newinstance = Class.forName(classname).newInstance();
        }
        catch (final Exception x) {
            logger.error("Problem finding implementation for: " + classname);
            logger.error("Exception : " + x.toString());
            logger.debug("Exception : ", x);
            System.exit(-1);
        }
        return newinstance;
    }

    public static OutputWorker createErrorWorker(final StreamManager manager) {
        OutputWorker writer = null;
        try {
            writer = (OutputWorker) getImplementationClassInstance(manager, PropertyKeys.PROP_ERRORWRITER_IMPL);
            writer.setStreamManager(manager);

        }
        catch (final Exception x) {
            logger.error("Wrong implementation specified: " + x.getMessage());
            logger.debug("Exception: ", x);
        }
        return writer;
    }

    public static OutputWorker createPostProcessHitWorker(final StreamManager manager) {
        OutputWorker writer = null;
        try {
            writer = (OutputWorker) getImplementationClassInstance(manager, PropertyKeys.PROP_POSTPROCESSOR_HIT_IMPL);
            writer.setStreamManager(manager);

        }
        catch (final Exception x) {
            logger.error("Wrong implementation specified: " + x.getMessage());
            logger.debug("Exception: ", x);
        }
        return writer;
    }

    public static OutputWorker createPostProcessNoHitWorker(final StreamManager manager) {
        OutputWorker writer = null;
        try {
            writer = (OutputWorker) getImplementationClassInstance(manager, PropertyKeys.PROP_POSTPROCESSOR_NOHIT_IMPL);
            writer.setStreamManager(manager);

        }
        catch (final Exception x) {
            logger.error("Wrong implementation specified: " + x.getMessage());
            logger.debug("Exception: ", x);
        }
        return writer;
    }

    public static FileParser createFileParser(final StreamManager manager) {
        FileParser parser = null;
        try {

            parser = (FileParser) getImplementationClassInstance(manager, PropertyKeys.PROP_FILEPARSER_IMPL);

            parser.setStreamManager(manager);

        }
        catch (final Exception x) {
            logger.error(manager.getStreamName() + PropertyKeys.PROP_FILEPARSER_IMPL + " failed with " + x.toString());

        }
        return parser;
    }

    public static InputWorker createInputWorker(final StreamManager manager) {
        InputWorker reader = null;
        try {
            reader = (InputWorker) getImplementationClassInstance(manager, PropertyKeys.PROP_INPUTREADER_IMPL);

            reader.setStreamManager(manager);
            reader.setSleepMillisBetween(getInputDelay(manager));

        }
        catch (final Exception x) {
            logger.error("Wrong implementation specified: " + x.getMessage());
            logger.debug("Exception: ", x);
        }
        return reader;
    }

    public static OutputWorker createOutputWorker(final StreamManager manager) {
        OutputWorker writer = null;
        try {
            writer = (OutputWorker) getImplementationClassInstance(manager, PropertyKeys.PROP_OUTPUTWRITER_IMPL);
            writer.setStreamManager(manager);

        }
        catch (final Exception x) {
            logger.error("Wrong implementation specified: " + x.getMessage());
            logger.debug("Exception: ", x);
        }
        return writer;
    }

    @SuppressWarnings("unchecked")
    private static Queue<Message> createQueueInternal1(final StreamManager manager, final String name) {
        final Properties properties = manager.getConfiguration();

        final String implementation = properties.getProperty(manager.getStreamName() + "." + name + ".Implementation", "");

        final Queue<Message> queue = (Queue<Message>) createClassInstance(implementation);

        queue.initialize(properties, manager.getStreamName(), name);

        return queue;
    }

    @SuppressWarnings("unchecked")
    private static Queue<AnalysisResult> createQueueInternal2(final StreamManager manager, final String name) {
        final Properties properties = manager.getConfiguration();

        final String implementation = properties.getProperty(manager.getStreamName() + "." + name + ".Implementation", "");

        final Queue<AnalysisResult> queue = (Queue<AnalysisResult>) createClassInstance(implementation);

        if (queue != null) {
            queue.initialize(properties, manager.getStreamName(), name);
        }

        return queue;
    }

    public static Queue<AnalysisResult> createStreamDefectQueue(final StreamManager manager) {
        return createQueueInternal2(manager, PropertyKeys.PROP_QUEUE_NAME_DEFECT);
    }

    public static Queue<AnalysisResult> createStreamHitQueue(final StreamManager manager) {
        return createQueueInternal2(manager, PropertyKeys.PROP_QUEUE_NAME_HIT);
    }

    public static Queue<Message> createStreamInputQueue(final StreamManager manager) {
        return createQueueInternal1(manager, PropertyKeys.PROP_QUEUE_NAME_INPUT);
    }

    public static Queue<AnalysisResult> createStreamNoHitQueue(final StreamManager manager) {
        return createQueueInternal2(manager, PropertyKeys.PROP_QUEUE_NAME_NOHIT);
    }

    public static Queue<AnalysisResult> createStreamPostProcessNoHitQueue(final StreamManager manager) {
        return createQueueInternal2(manager, PropertyKeys.PROP_QUEUE_NAME_PP_NOHIT);
    }

    public static Queue<AnalysisResult> createStreamPostProcessHitQueue(final StreamManager manager) {
        return createQueueInternal2(manager, PropertyKeys.PROP_QUEUE_NAME_PP_HIT);
    }

    private static Object getImplementationClassInstance(final StreamManager manager, final String name) {
        Object object = null;

        try {
            final Properties properties = manager.getConfiguration();
            final String parserImplementation = properties.getProperty(manager.getStreamName() + "." + name, "");

            object = createClassInstance(parserImplementation);

        }
        catch (final Exception x) {
            logger.error(manager.getStreamName() + name + " failed with " + x.toString());

        }

        return object;

    }

    public static int getInputDelay(final StreamManager manager) {
        final Properties properties = manager.getConfiguration();

        final String delayTxt = properties.getProperty(manager.getStreamName() + ".InputDelay", "0");

        final int delay = Integer.parseInt(delayTxt);

        return delay;
    }

    public static int getNumberOfCheckers(final StreamManager manager) {
        final Properties properties = manager.getConfiguration();

        final String queueSize = properties.getProperty(manager.getStreamName() + "." + PropertyKeys.PROP_NRWORKERS_CHECKER, "1");

        final int size = Integer.parseInt(queueSize);

        return size;
    }

    public static int getNumberOfErrorWorkers(final StreamManager manager) {
        final Properties properties = manager.getConfiguration();

        final String queueSize = properties.getProperty(manager.getStreamName() + "." + PropertyKeys.PROP_NRWORKERS_ERROR, "1");

        final int size = Integer.parseInt(queueSize);

        return size;
    }

    public static int getNumberOfPPHitWorkers(final StreamManager manager) {
        final Properties properties = manager.getConfiguration();

        final String queueSize = properties.getProperty(manager.getStreamName() + "." + PropertyKeys.PROP_NRWORKERS_PP_HIT, "0");

        final int size = Integer.parseInt(queueSize);

        return size;
    }

    public static int getNumberOfPPNoHitWorkers(final StreamManager manager) {
        final Properties properties = manager.getConfiguration();

        final String queueSize = properties.getProperty(manager.getStreamName() + "." + PropertyKeys.PROP_NRWORKERS_PP_NOHIT, "0");

        final int size = Integer.parseInt(queueSize);

        return size;
    }

    public static int getNumberOfInputWorkers(final StreamManager manager) {
        final Properties properties = manager.getConfiguration();

        final String queueSize = properties.getProperty(manager.getStreamName() + "." + PropertyKeys.PROP_NRWORKERS_INPUT, "1");

        final int size = Integer.parseInt(queueSize);

        return size;
    }

    public static int getNumberOfOutputHitWorkers(final StreamManager manager) {
        final Properties properties = manager.getConfiguration();

        final String queueSize = properties.getProperty(manager.getStreamName() + "." + PropertyKeys.PROP_NRWORKERS_OUTPUT_HIT, "1");

        final int size = Integer.parseInt(queueSize);

        return size;
    }

    public static int getNumberOfOutputNoHitWorkers(final StreamManager manager) {
        final Properties properties = manager.getConfiguration();

        final String queueSize = properties.getProperty(manager.getStreamName() + "." + PropertyKeys.PROP_NRWORKERS_OUTPUT_NOHIT, "1");

        final int size = Integer.parseInt(queueSize);

        return size;
    }

    public static ReferenceListHandler getReferenceListloader(final Properties properties, final String listname) {

        final String loader = properties.getProperty(PropertyKeys.PROP_REFLIST_DEF + "." + listname + "." + PropertyKeys.PROP_LISTLOADER);

        final ReferenceListHandler instance = (ReferenceListHandler) createClassInstance(loader);

        instance.initialize();

        return instance;
    }

    public static StreamManager getStreamManager(final Properties properties, final String streamName) {

        final String manager = properties.getProperty(streamName + "." + PropertyKeys.PROP_STREAMMGR_IMPL);

        final StreamManager instance = (StreamManager) createClassInstance(manager);

        instance.setStreamName(streamName);

        instance.setProperties(properties);

        return instance;
    }

    public static ValueListHandler getValueListloader(final Properties properties, final String listname) {

        final String loader = properties.getProperty(PropertyKeys.PROP_VALLIST_DEF + "." + listname + "." + PropertyKeys.PROP_LISTLOADER);

        final ValueListHandler instance = (ValueListHandler) createClassInstance(loader);

        instance.initialize();

        return instance;
    }

    public static SanctionListHandler getWatchListloader(final Properties properties, final String listname) {

        final String loader = properties.getProperty(PropertyKeys.PROP_LIST_DEF + "." + listname + "." + PropertyKeys.PROP_LISTLOADER);

        final SanctionListHandler instance = (SanctionListHandler) createClassInstance(loader);

        instance.initialize();

        return instance;
    }

    private static OptimizationListHandler getOptimizationListloader(final Properties properties, final String propKey) {

        final String loader = properties.getProperty(propKey + "." + PropertyKeys.PROP_LISTLOADER);

        OptimizationListHandler instance = null;
        if (loader != null) {
            instance = (OptimizationListHandler) createClassInstance(loader);

            instance.initialize();
        }
        return instance;
    }

    public static OptimizationListHandler getTXNoHitOptimizationListloader(final Properties properties) {

        return getOptimizationListloader(properties, PropertyKeys.PROP_TXNOHITOPTILIST_DEF);
    }

    public static OptimizationListHandler getTXHitOptimizationListloader(final Properties properties) {

        return getOptimizationListloader(properties, PropertyKeys.PROP_TXHITOPTILIST_DEF);
    }

    public static NoWordHitListHandler getNoHitListloader(final Properties properties) {

        final String loader = properties.getProperty(PropertyKeys.PROP_NOWORDHITLIST_DEF + "." + PropertyKeys.PROP_LISTLOADER);

        NoWordHitListHandler instance = null;
        if (loader != null) {
            instance = (NoWordHitListHandler) createClassInstance(loader);

            instance.initialize();
        }
        return instance;
    }

}
