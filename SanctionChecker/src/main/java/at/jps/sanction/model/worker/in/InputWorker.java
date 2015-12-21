/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.model.worker.in;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.StreamManager;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.Status;
import at.jps.sanction.model.worker.Worker;

abstract public class InputWorker extends Worker {

    static final Logger logger             = LoggerFactory.getLogger(InputWorker.class);

    long                sleepMillisBetween = 0;

    public InputWorker() {
        super();
    }

    public InputWorker(final StreamManager manager) {
        super(manager);
    }

    public void checkForMessages() {
        Message message = null;
        // check for new message and AddToQueue
        try {

            message = getNewMessage();
            if (message != null) {
                handleMessage(message);
            }

        }
        catch (final Exception e) {
            logger.error("error on input message: " + (message != null ? message.getId() : "no Message") + " : " + e.toString());
            logger.debug("Exception: ", e);
        }
    }

    abstract public Message getNewMessage();

    public long getSleepMillisBetween() {
        return sleepMillisBetween;
    }

    public void handleMessage(final Message message) {
        if (message != null) {
            getStreamManager().addToInputList(message);
        }
    }

    @Override
    public void run() {

        try {
            while (Status.isRunning()) {

                if (sleepMillisBetween > 0) {
                    Thread.sleep(sleepMillisBetween);
                }

                checkForMessages();
            }
        }
        catch (final Exception e) {
            logger.error("error while waiting for new message: " + e.toString());
            logger.debug("Exception: ", e);
        }
    }

    // {
    // return new Message();
    // }

    public void setSleepMillisBetween(final int millis) {
        sleepMillisBetween = millis;
    }
}
