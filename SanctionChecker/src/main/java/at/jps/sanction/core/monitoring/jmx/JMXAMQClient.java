package at.jps.sanction.core.monitoring.jmx;

import java.util.HashMap;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMXAMQClient {

    private static final Logger             logger = LoggerFactory.getLogger(JMXAMQClient.class);

    private HashMap<String, QueueViewMBean> queueViewBeanCache;

    private String                          JMXServerUrl;
    private String                          objectName;

    public void connect() {

        if (queueViewBeanCache == null) {
            JMXServiceURL url;
            try {
                // url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1616/jmxrmi");
                url = new JMXServiceURL(getJMXServerUrl());
                JMXConnector jmxc = JMXConnectorFactory.connect(url);
                MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

                ObjectName activeMQ = new ObjectName(getObjectName()); // "org.apache.activemq:BrokerName=localhost,Type=Broker");
                BrokerViewMBean mbean = (BrokerViewMBean) MBeanServerInvocationHandler.newProxyInstance(mbsc, activeMQ, BrokerViewMBean.class, true);

                queueViewBeanCache = new HashMap<String, QueueViewMBean>();

                for (ObjectName name : mbean.getQueues()) {
                    QueueViewMBean queueMbean = (QueueViewMBean) MBeanServerInvocationHandler.newProxyInstance(mbsc, name, QueueViewMBean.class, true);

                    queueViewBeanCache.put(queueMbean.getName(), queueMbean);

                }

            }
            catch (Exception x) {
                logger.error("JMX Active MQ Client Error:" + x.toString());
                logger.debug("Exception", x);
            }
        }
    }

    private void jmxTest() {
        if (System.getProperty("com.sun.management.jmxremote") == null) {
            System.out.println("JMX remote is disabled");
        }
        else {
            String portString = System.getProperty("com.sun.management.jmxremote.port");
            if (portString != null) {
                System.out.println("JMX running on port " + Integer.parseInt(portString));
            }
        }
    }

    public void purge(final String queuename) {
        QueueViewMBean queueMbean = queueViewBeanCache.get(queuename);
        if (queueMbean != null) {
            try {
                queueMbean.purge();
            }
            catch (Exception x) {
                logger.error("JMX Active MQ Client Purge Error:" + x.toString());
                logger.debug("Exception", x);
            }
        }
    }

    public Long getQueueSize(final String queuename) {
        long size = 0;
        if (queueViewBeanCache != null) {
            QueueViewMBean queueMbean = queueViewBeanCache.get(queuename);

            if (queueMbean != null) {
                size = queueMbean.getEnqueueCount();
            }
        }
        return size;
    }

    protected void printAllSizes() {
        StringBuilder sb = new StringBuilder();
        if (queueViewBeanCache != null) for (String name : queueViewBeanCache.keySet()) {
            sb.append(name).append(" ").append(getQueueSize(name)).append("\r\n");
        }
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {

        JMXAMQClient jmxc = new JMXAMQClient();

        jmxc.connect();
        jmxc.printAllSizes();

    }

    public String getJMXServerUrl() {
        return JMXServerUrl;
    }

    public void setJMXServerUrl(String jMXServerUrl) {
        JMXServerUrl = jMXServerUrl;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

}
