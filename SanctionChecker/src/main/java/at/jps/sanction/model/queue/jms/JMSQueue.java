/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.model.queue.jms;

import java.io.Serializable;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import at.jps.sanction.core.ListConfigHolder;
import at.jps.sanction.core.io.jms.JMSAdapter;
import at.jps.sanction.core.monitoring.jmx.JMXAMQClient;
import at.jps.sanction.model.queue.AbstractQueue;

public abstract class JMSQueue<X> extends AbstractQueue<X> {

    @Autowired
    private ApplicationContext appContext;

    static final Logger        logger = LoggerFactory.getLogger(JMSQueue.class);

    private String             JMSServerUrl;
    private String             internalQueueName;

    private JMXAMQClient       jmxRemoteActiveMQAdapter;

    private JMSAdapter         jmsAdapter;

    public JMSQueue() {
        super();
    }

    public JMSQueue(final String name, final int capacity) {
        super(name, capacity);
    }

    protected void initialize() {

        internalQueueName = getStreamName() + "/" + getName();

        jmsAdapter = new JMSAdapter();

        jmsAdapter.setConnectionURL(getJMSServerUrl());
        jmsAdapter.setQueueName(internalQueueName);

        jmsAdapter.initialize();

    }

    @Override
    public boolean addMessage(final X message) {

        ObjectMessage jmsMessage;
        try {
            // jmsMessage = getSession().createObjectMessage(SerializationUtils.serialize((Serializable) message));
            jmsMessage = jmsAdapter.createObjectMessage();
            jmsMessage.setObject((Serializable) message);

            jmsAdapter.getProducer().send(jmsMessage);

            // for notification only !!
            super.addMessage(message);

            return true;
        }
        catch (JMSException e) {
            logger.error(getName() + ": JMS Send Error" + e.toString());
            if (logger.isDebugEnabled()) logger.debug("Exception: ", e);

            return false;
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public X getNextMessage(boolean wait) {

        X message = null;

        ObjectMessage jmsMessage;
        try {

            if (!wait) {
                jmsMessage = (ObjectMessage) jmsAdapter.getConsumer().receiveNoWait();
            }
            else {
                jmsMessage = (ObjectMessage) jmsAdapter.getConsumer().receive(500);
            }

            if (jmsMessage != null) {
                message = (X) jmsMessage.getObject();

                if (logger.isDebugEnabled()) logger.debug(getName() + ": JMS Msg Id" + jmsMessage.getJMSMessageID());

                // for notification only !!
                super.getNextMessage(wait);
            }
        }
        catch (JMSException e) {
            logger.error(getName() + ": JMS receive Error" + e.toString());
            if (logger.isDebugEnabled()) logger.debug("Exception: ", e);
        }

        return message;
    }

    @Override
    public void clear() {
        getJmxRemoteActiveMQAdapter().purge(internalQueueName);
    }

    @Override
    public void close() {
        jmsAdapter.close();
    }

    @Override
    public long size() {
        return getJmxRemoteActiveMQAdapter().getQueueSize(internalQueueName); // JMX Call ( must be enabled !!)
    }

    @Override
    public long getRemainingCapacity() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return size() > 0;
    }

    @Override
    public void initialize(final Properties properties, final String streamName, final String queueName) {

        super.initialize(properties, streamName, queueName);

        setBasePath(streamName);

        JMSServerUrl = properties.getProperty(streamName + "." + name + ".JMXURL", "");

        initialize();
    }

    public JMXAMQClient getJmxRemoteActiveMQAdapter() {

        if (jmxRemoteActiveMQAdapter == null) {
            // jmxRemoteActiveMQAdapter = new JMXAMQClient();

            jmxRemoteActiveMQAdapter = (JMXAMQClient) ListConfigHolder.getApplicationContext().getBean("JMXAMQClientAdapter");

            // jmxRemoteActiveMQAdapter = (JMXAMQClient) appContext.getBean("JMXAMQClientAdapter");
            jmxRemoteActiveMQAdapter.connect();
        }

        return jmxRemoteActiveMQAdapter;
    }

    public String getJMSServerUrl() {
        return JMSServerUrl;
    }

    public void setJMSServerUrl(String jMSServerUrl) {
        JMSServerUrl = jMSServerUrl;
    }

}
