package at.jps.sanction.core.monitoring.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.StreamManager;
import at.jps.sanction.core.monitoring.jmx.queue.QueueStatus;

public class JMXManager {

    static MBeanServer  jmxServer;
    static ObjectName[] jmxObjectNames;

    static final Logger logger = LoggerFactory.getLogger(JMXManager.class);

    public static void registerBeans(final StreamManager streamManager) {

        try {

            jmxServer = ManagementFactory.getPlatformMBeanServer();

            // MBeanServer server = MBeanServerFactory.createMBeanServer();

            jmxObjectNames = new ObjectName[1];

            jmxObjectNames[0] = new ObjectName("Embargo:name=Queues");

            // ..

            for (final ObjectName jmxObjectName : jmxObjectNames) {
                jmxServer.registerMBean(new QueueStatus(streamManager), jmxObjectName);
            }

            // HtmlAdaptorServer adaptor = new HtmlAdaptorServer();
            // ObjectName adaptorName = new ObjectName("adaptor:proptocol=HTTP");
            // server.registerMBean(adaptor, adaptorName);
            // adaptor.start();

        }
        catch (final Exception e) {
            logger.error("JMX Error registration: " + e.toString());
            logger.debug("Exception: ", e);
        }

    }

    public static void deregisterBeans(final StreamManager streamManager) {
        try {

            for (final ObjectName jmxObjectName : jmxObjectNames) {
                jmxServer.unregisterMBean(jmxObjectName);
            }
        }
        catch (final Exception e) {
            logger.error("JMX Error deregistration: " + e.toString());
            logger.debug("Exception: ", e);
        }

    }

}
