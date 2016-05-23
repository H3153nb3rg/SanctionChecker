/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import at.jps.sanction.core.notifications.NotificationManager;
import at.jps.sanction.core.notifications.ShutdownStatus;
import at.jps.sanction.model.PropertyKeys;
import at.jps.sanction.model.Status;

public class Checker {

    static final Logger         logger        = LoggerFactory.getLogger(Checker.class);

    static final String         PROP_FILENAME = "checker.properties";

    private Properties          properties;

    private List<String>        streams;
    private List<StreamManager> streamManagers;

    private ListConfigHolder    listConfig;

    public boolean initialize(final String filename) {

        properties = new Properties();

        if (filename != null) {
            try {
                properties.load(new FileInputStream(filename));
            }
            catch (final Exception e) {
                System.out.println("properties failed to load from: " + filename);
            }
        }
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(PROP_FILENAME));
        }
        catch (final IOException e) {
            logger.error("properties failed to load from classpath: " + PROP_FILENAME);
            logger.debug("Exception: ", e);
        }

        streams = new ArrayList<String>();

        final String streamkey = properties.getProperty(PropertyKeys.PROP_STREAM_DEF, "");

        final StringTokenizer tokenizer = new StringTokenizer(streamkey, ",;");

        while (tokenizer.hasMoreTokens()) {
            streams.add(tokenizer.nextToken());
        }

        return ((properties.size() > 0) && (streams.size() > 0));
    }

    public void initializeLists(final boolean cleanup) {
        logger.info("Load Lists...");

        final String lists = properties.getProperty(PropertyKeys.PROP_LIST_DEFS, "");
        new StringTokenizer(lists, ",;");

        final String lists2 = properties.getProperty(PropertyKeys.PROP_REFLIST_DEFS, "");
        new StringTokenizer(lists2, ",;");

        final String lists3 = properties.getProperty(PropertyKeys.PROP_VALLIST_DEFS, "");
        new StringTokenizer(lists3, ",;");

    }

    private void shutdown() {
        logger.info("Shutdown triggered");

        // publish event
        NotificationManager.post(new ShutdownStatus(true));

        // set global status
        Status.shutdown();
        logger.info("==== SHUT DOWN finished =====");
    }

    protected void initializeManagers(final boolean cleanup) {

        // start all streams
        streamManagers = new ArrayList<StreamManager>(streams.size());

        // create
        for (final String streamName : streams) {
            logger.info("Start StreamMananger for: " + streamName);
            streamManagers.add(BaseFactory.getStreamManager(properties, streamName));
        }

        // initialize
        for (final StreamManager sm : streamManagers) {
            logger.info(" intialize StreamMananger: " + sm.getStreamName());
            // sm.setPurgeQueuesOnStartup(cleanup);
            sm.initialize();
        }

        // set listhandlers
        addListHandlersToManagers();
        for (final StreamManager sm : streamManagers) {
            // logger.info(" attach ListHandlers StreamMananger: " + sm.getStreamName());
            sm.setListConfig(listConfig);

        }
    }

    private void addListHandlersToManagers() {
        // set listhandlers
        for (final StreamManager sm : streamManagers) {
            logger.info(" attach ListHandlers StreamMananger: " + sm.getStreamName());
            sm.setListConfig(listConfig);
        }
    }

    protected void startupManagers() {
        // startup
        for (final StreamManager sm : streamManagers) {
            logger.info(" launch StreamMananger: " + sm.getStreamName());
            new Thread(sm).start();
        }

    }

    private void addSignalHandlers() {
        // Signal.handle(new Signal("INT"), new SignalHandler() {
        // @Override
        // public void handle(final Signal sig) {
        // logger.info("SIGINT detected!!");
        // shutdown();
        // }
        // });
        //
        // Signal.handle(new Signal("TERM"), new SignalHandler() {
        // @Override
        // public void handle(final Signal sig) {
        // logger.info("SIGTERM detected!!");
        // shutdown();
        // }
        // });

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                logger.info("shutdownhook called ...");
                shutdown();
                System.out.println("ALL DONE!");
            }
        });
    }

    public void startup(final String propertyFilename, final boolean cleanup) {

        addSignalHandlers();

        if (initialize(propertyFilename)) {

            initializeLists(cleanup);

            logger.info("starting up individual Stream Managers");

            initializeManagers(cleanup);

            startupManagers();

            logger.info("Initialization OK");
            logger.info("-----------------");

        }
    }

    /*
     * we come from spring
     */
    public void initialize() {

        assert (getListConfig() != null) : "ListConfig not configured";
        assert (getStreamManagers() != null) && (!getStreamManagers().isEmpty()) : "StreamManagers not configured";

        addSignalHandlers();
        addListHandlersToManagers();
        startupManagers();
    }

    /*
     * we come from spring
     */
    public void close() {
        shutdown();
    }

    public static void main(final String[] args) {

        logger.info("==== STARTING UP =====");

        // if (!AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent", "debug=10;packages=at.jps.sanction.domain.**,at.jps.sanction.core.**,at.jps.sanction.model.**")) {
        // logger.error("avaje-ebeanorm-agent not found in classpath - not dynamically loaded");
        // }

        // SPRING !!
        String configFilename = "SanctionChecker.xml";
        boolean initialized = false;
        ApplicationContext context = null;
        if (args.length > 0) {
            configFilename = args[0];
            try {
                context = new FileSystemXmlApplicationContext(configFilename);
                initialized = true;
            }
            catch (final Exception x) {
                x.printStackTrace();
                System.out.println(x.toString());
            }
        }
        if (!initialized) {
            context = new ClassPathXmlApplicationContext("SanctionChecker.xml");
        }

        context.getBean("EntityManagement");

        context.getBean("SanctionChecker");

        // NO SPRING
        // propertyfile is first parameter
        // String filename = null;
        // if (args.length > 1) {
        // filename = args[0];
        // }
        //
        // final Checker checker = new Checker();
        //
        // final boolean cleanup = ((args.length >= 1) && (args[0].compareTo("-R") == 0));
        //
        // checker.startup(filename, cleanup);

        // logger.info("==== SHUT DOWN initiated =====");
    }

    public List<StreamManager> getStreamManagers() {
        return streamManagers;
    }

    public void setStreamManagers(final List<StreamManager> streamManagers) {
        this.streamManagers = streamManagers;
    }

    public ListConfigHolder getListConfig() {
        return listConfig;
    }

    public void setListConfig(final ListConfigHolder listConfig) {
        this.listConfig = listConfig;
    }

}
