/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.model.queue.nonpersisted;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.model.queue.AbstractQueue;

public abstract class MemoryQueue<X> extends AbstractQueue<X> {

    static final Logger      logger = LoggerFactory.getLogger(MemoryQueue.class);

    private BlockingQueue<X> messageQueue;

    public MemoryQueue() {
        super();
    }

    public MemoryQueue(final String name, final int capacity) {
        super(name, capacity);
    }

    @Override
    public boolean addMessage(final X message) {

        boolean added = getQueue().add(message);

        super.addMessage(message);

        return added;
    }

    @Override
    public X getNextMessage(final boolean wait) {
        X message = null;
        try {
            if (wait) {
                message = getQueue().take();
            }
            else {
                message = getQueue().remove();
            }
            super.getNextMessage(wait);
        }
        catch (final Exception e) {
            logger.error(getName() + ": Message retrieve Error" + e.toString());
            logger.debug("Exception: ", e);
        }
        return message;
    }

    protected BlockingQueue<X> getQueue() {
        if (this.messageQueue == null) {
            this.messageQueue = new ArrayBlockingQueue<X>((int) this.capacity);
        }
        return this.messageQueue;
    }

    @Override
    public void clear() {
        getQueue().clear();
    }

    @Override
    public void close() {
        logger.debug("Closing queue (" + getName() + ")");
    }

    @Override
    public long size() {
        return getQueue().size();
    }

    @Override
    public long getRemainingCapacity() {
        return getQueue().remainingCapacity();
    }

    @Override
    public boolean isEmpty() {
        return getQueue().isEmpty();
    }

}
