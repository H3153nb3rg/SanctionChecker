/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.core.io.jms;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.StreamManager;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.worker.in.InputWorker;

public class JMSInputWorker extends InputWorker {

    static final Logger logger = LoggerFactory.getLogger(JMSInputWorker.class);

    private String      connectionURL;
    private String      queueName;
    private JMSAdapter  jmsAdapter;

    public JMSInputWorker() {
        super();
    }

    /**
     * read TXT Message from external MQ Queue and store Message to internal input queue as Message
     *
     * @param manager
     */

    JMSInputWorker(final StreamManager manager) {
        super(manager);
    }

    @Override
    public void initialize() {

        jmsAdapter = new JMSAdapter();

        jmsAdapter.setConnectionURL(connectionURL);
        jmsAdapter.setQueueName(queueName);

        jmsAdapter.initialize();

        try {
            jmsAdapter.getConsumer().setMessageListener(new MessageListener() {

                @Override
                public void onMessage(final javax.jms.Message jmsMessage) {

                    final TextMessage msg = (TextMessage) jmsMessage;
                    if (msg != null) {
                        final Message message = new Message();
                        try {
                            message.setRawContent(msg.getText());

                            handleMessage(message);
                        }
                        catch (final JMSException x) {
                            logger.error("Error on JMS Async Receiver " + x.toString());
                            logger.debug("Exception: ", x);
                        }
                    }

                }
            });
        }
        catch (final JMSException x) {
            logger.error("Error on JMS MSG Listener " + x.toString());
            logger.debug("Exception: ", x);
        }

    }

    @Override
    public Message getNewMessage() {
        // TODO Auto-generated method stub
        return null;
    }

}
