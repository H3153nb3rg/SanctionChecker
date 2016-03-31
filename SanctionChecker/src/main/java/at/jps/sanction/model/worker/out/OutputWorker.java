/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.model.worker.out;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.io.file.FileOutputWorker;
import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.Status;
import at.jps.sanction.model.queue.Queue;
import at.jps.sanction.model.worker.Worker;

abstract public class OutputWorker extends Worker {

    static final Logger           logger             = LoggerFactory.getLogger(FileOutputWorker.class);

    private Queue<AnalysisResult> inQueue;
    private Queue<AnalysisResult> outQueue;
    private long                  sleepMillisBetween = 0;

    public OutputWorker() {
        super();
    }

    @Override
    public void initialize() {

        super.initialize();

        assert getInQueue() != null : "InQueue not specified - see configuration";
        // assert getOutQueue() != null : "OutQueue not specified - see configuration";

    }

    public void checkForMessage() {
        AnalysisResult message = null;
        // check for new message and AddToQueue
        try {

            message = getInQueue().getNextMessage(true);

            if (message != null) {
                handleMessage(message);
            }

        }
        catch (final Exception e) {
            logger.error("error on output message: " + ((message.getMessage() != null ? message.getMessage().getId() : "no Message") + " : " + e.toString()));
            logger.debug("Exception: ", e);

        }

    }

    public Queue<AnalysisResult> getInQueue() {
        return inQueue;
    }

    public long getSleepTimer() {
        return sleepMillisBetween;
    }

    @Override
    public void run() {

        try {
            while (Status.isRunning()) {

                if (sleepMillisBetween > 0) {
                    Thread.sleep(sleepMillisBetween);
                }

                checkForMessage();

            }
        }
        catch (final Exception e) {
            logger.error("error while waiting for new message: " + e.toString());
            logger.debug("Exception: ", e);
        }
    }

    public void setInQueue(final Queue<AnalysisResult> queue) {
        inQueue = queue;
    }

    abstract public void handleMessage(AnalysisResult message);

    public long getSleepMillisBetween() {
        return sleepMillisBetween;
    }

    public void setSleepMillisBetween(final long sleepMillisBetween) {
        this.sleepMillisBetween = sleepMillisBetween;
    }

    public Queue<AnalysisResult> getOutQueue() {
        return outQueue;
    }

    public void setOutQueue(final Queue<AnalysisResult> outQueue) {
        this.outQueue = outQueue;
    }

}
