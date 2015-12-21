package at.jps.sanction.core.io.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSAdapter {

    // private QueueConnection connection;
    private Connection                   connection;
    private Queue                        messageQueue;
    private String                       connectionURL;
    private String                       queueName;

    // private ThreadLocal<QueueSession> session;
    private ThreadLocal<Session>         session;
    private ThreadLocal<MessageProducer> producer;
    private ThreadLocal<MessageConsumer> consumer;
    // private InitialContext ctx;

    static final Logger                  logger = LoggerFactory.getLogger(JMSAdapter.class);

    public void initialize() {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(connectionURL);

        try {
            session = new ThreadLocal<Session>();
            producer = new ThreadLocal<MessageProducer>();
            consumer = new ThreadLocal<MessageConsumer>();

            // Properties env = new Properties();
            //
            // env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            // env.put(Context.PROVIDER_URL, "tcp://localhost:61616");
            // env.put("queue.queueSampleQueue", "MyNewQueue");

            // get the initial context
            // ctx = new InitialContext(env);

            // lookup the queue connection factory
            // QueueConnectionFactory connectionFactory = (QueueConnectionFactory) ctx.lookup("QueueConnectionFactory");

            // connection = connectionFactory.createQueueConnection();

            connection = connectionFactory.createConnection();

            connection.start();

        }
        catch (final Exception e) {
            logger.error(getQueueName() + ": JMS Setup Error" + e.toString());
            if (logger.isDebugEnabled()) logger.debug("Exception: ", e);
        }
    }

    protected Session getSession() {

        if (session.get() == null) {
            try {
                session.set(connection.createSession(false, Session.AUTO_ACKNOWLEDGE));

                // session.set(connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE));

            }
            catch (final Exception e) {
                logger.error(getQueueName() + ": JMS Session Error" + e.toString());
                if (logger.isDebugEnabled()) logger.debug("Exception: ", e);
            }
        }
        return session.get();
    }

    protected Queue getQueue() {

        try {
            if (this.messageQueue == null) {

                this.messageQueue = getSession().createQueue(getQueueName());
                // lookup the queue object
                // messageQueue = (Queue) ctx.lookup(getQueueName());

            }
        }
        catch (final Exception e) {
            logger.error(getQueueName() + ": JMS Error" + e.toString());
            if (logger.isDebugEnabled()) logger.debug("Exception: ", e);
        }

        return this.messageQueue;
    }

    public MessageProducer getProducer() {
        if (producer.get() == null) {
            if (getSession() != null) {
                try {
                    producer.set(getSession().createProducer(getQueue()));
                }
                catch (final Exception e) {
                    logger.error(getQueueName() + ": JMS Producer Error" + e.toString());
                    if (logger.isDebugEnabled()) logger.debug("Exception: ", e);
                }
            }
        }

        return producer.get();
    }

    public MessageConsumer getConsumer() {
        if (consumer.get() == null) {
            if (getSession() != null) {
                try {
                    consumer.set(getSession().createConsumer(getQueue()));
                }
                catch (final Exception e) {
                    logger.error(getQueueName() + ": JMS Consumer Error" + e.toString());
                    if (logger.isDebugEnabled()) logger.debug("Exception: ", e);
                }
            }
        }
        return consumer.get();
    }

    public void close() {

        if (producer != null) {
            try {
                getProducer().close();
            }
            catch (JMSException e) {
                logger.error(getQueueName() + ": JMS close Error" + e.toString());
                if (logger.isDebugEnabled()) logger.debug("Exception: ", e);
            }
        }
        if (consumer != null) {
            try {
                getConsumer().close();
            }
            catch (JMSException e) {
                logger.error(getQueueName() + ": JMS close Error" + e.toString());
                if (logger.isDebugEnabled()) logger.debug("Exception: ", e);
            }
        }
        try {
            if (session != null) {
                getSession().close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        catch (final Exception e) {
            logger.error(getQueueName() + ": JMS shutdown Error" + e.toString());
            logger.debug("Exception: ", e);
        }

        if (logger.isDebugEnabled()) logger.debug("Closing queue (" + getQueueName() + ")");
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getConnectionURL() {
        return connectionURL;
    }

    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    public ObjectMessage createObjectMessage() {
        ObjectMessage jmsMessage = null;
        try {
            // jmsMessage = getSession().createObjectMessage(SerializationUtils.serialize((Serializable) message));
            jmsMessage = getSession().createObjectMessage();
            jmsMessage.setJMSDestination(getQueue());
        }
        catch (JMSException e) {
            logger.error(getQueueName() + ": JMS Send Error" + e.toString());
            if (logger.isDebugEnabled()) logger.debug("Exception: ", e);
        }
        return jmsMessage;
    }

}
