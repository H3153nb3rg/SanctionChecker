package at.jps.sanction.core.monitoring.jmx;

import java.util.HashMap;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.StreamManager;
import at.jps.sanction.core.monitoring.jmx.queue.QueueStatus;

public class JMXClient {

    private QueueStatus         mbeanQueueStatusProxy;

    private static final Logger logger = LoggerFactory.getLogger(StreamManager.class);
    private String              JMXServerUrl;
    private String              objectName;

    public JMXClient() {

    }

    // DUMMIES !!
    final static String HOST = "127.0.0.1";
    final static String PORT = "1234";

    public void connect() {
        try {
            final JMXServiceURL url = new JMXServiceURL(getJMXServerUrl()); // "service:jmx:rmi:///jndi/rmi://" + HOST + ":" + PORT + "/jmxrmi");

            // for passing credentials for password
            /*
             * Map<String, String[]> env = new HashMap<>(); String[] credentials = {"myrole", "MYP@SSWORD"}; env.put(JMXConnector.CREDENTIALS, credentials); JMXConnector jmxConnector =
             * JMXConnectorFactory.connect(url, env);
             */
            final JMXConnector jmxConnector = JMXConnectorFactory.connect(url);
            final MBeanServerConnection mbeanServerConnection = jmxConnector.getMBeanServerConnection();

            final ObjectName mbeanName = new ObjectName(getObjectName()); // "Embargo:name=<StreamName>-Queues");
            logger.error("JMX Client Objectname:" + getObjectName());
            // Get MBean proxy instance that will be used to make calls to registered MBean
            mbeanQueueStatusProxy = MBeanServerInvocationHandler.newProxyInstance(mbeanServerConnection, mbeanName, QueueStatus.class, true);
        }
        catch (final Exception x) {
            logger.error("JMX Client Error:" + x.toString());
            logger.debug("Exception", x);
        }
    }

    public HashMap<String, Long> getQueueSizes() {
        final HashMap<String, Long> queueSizes = new HashMap<String, Long>();

        for (final String queueName : mbeanQueueStatusProxy.getQueueNames()) {
            queueSizes.put(queueName, new Long(mbeanQueueStatusProxy.getQueueSize(queueName)));
        }

        return queueSizes;
    }

    public String getJMXServerUrl() {
        return JMXServerUrl;
    }

    public void setJMXServerUrl(final String jMXServerUrl) {
        JMXServerUrl = jMXServerUrl;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(final String objectName) {
        this.objectName = objectName;
    }

}
