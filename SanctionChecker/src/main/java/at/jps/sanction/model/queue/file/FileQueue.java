/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.model.queue.file;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leansoft.bigqueue.BigQueueImpl;
import com.leansoft.bigqueue.IBigQueue;

import at.jps.sanction.model.queue.AbstractQueue;

public abstract class FileQueue<X> extends AbstractQueue<X> {

    static final Logger logger       = LoggerFactory.getLogger(FileQueue.class);

    private IBigQueue   messageQueue = null;

    public FileQueue() {
        super();
    }

    public FileQueue(final String name, final int capacity) {
        super(name, capacity);

    }

    @Override
    public boolean addMessage(final X message) {
        try {
            getQueue().enqueue(SerializationUtils.serialize((Serializable) message));

            flush();  // TODO: this IS ugly ...

            super.addMessage(message);

            return true;
        }
        catch (final IOException e) {
            logger.error("Error adding Message:" + e.toString());
            logger.debug("Exception: ", e);
            return false;
        }
    }

    @Override
    public void clear() {
        try {
            getQueue().removeAll();
        }
        catch (final IOException e) {
            logger.error("Error during cleanup...(" + getName() + ") :" + e.toString());
            logger.debug("Exception: ", e);
        }
    }

    @Override
    public void close() {

        try {
            getQueue().close();
        }
        catch (final IOException e) {
            logger.error("Error closing Queue (" + getName() + ") :" + e.toString());
            logger.debug("Exception: ", e);
        }
    }

    private IBigQueue createQueue() {

        try {
            this.messageQueue = new BigQueueImpl(getBasePath(), getName());

            if (logger.isInfoEnabled()) {
                logger.info("Queue created (BigQueueImpl, " + getBasePath() + "/" + getName() + ")");
            }

            if (isPurgeQueuesOnStartup()) {
                if (logger.isInfoEnabled()) {
                    logger.info("CLEANUP Queue !!");
                }
                clear();
            }

        }
        catch (final IOException e) {
            logger.error("Queue creation failed (BigQueueImpl," + getBasePath() + "/" + getName() + ") :" + e.toString());
            logger.debug("Exception: ", e);
        }

        return this.messageQueue;
    }

    @Override
    public X getNextMessage(final boolean wait) {
        X message = null;
        try {
            if (wait) {
                while (isEmpty()) {
                    Thread.sleep(100);
                }
            }
            // } else {
            // message = getQueue().remove();
            if (!isEmpty()) {
                final byte[] object = getQueue().dequeue();
                if (object != null) {
                    message = SerializationUtils.deserialize(object);
                }
            }
            super.getNextMessage(wait);
        }
        catch (final Exception x) {
            logger.error(getName() + ": Message retrieve Error: " + x.toString());
            logger.debug("Exception: ", x);
        }
        return message;
    }

    private IBigQueue getQueue() {

        if (this.messageQueue == null) {
            this.messageQueue = createQueue();
        }

        return this.messageQueue;
    }

    @Override
    public long getRemainingCapacity() {
        // TODO: calc it!!
        return size();
    }

    @Override
    public boolean isEmpty() {
        if (size() > 0) {
            System.out.println("Queuesize:" + size());
        }
        return getQueue().isEmpty();
    }

    @Override
    public void setBasePath(final String base) {
        basePath = base;
    }

    @Override
    public void setCapacity(final long capacity) {
        this.capacity = capacity;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public long size() {
        return getQueue().size();
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void flush() {
        getQueue().flush();
    }
}
