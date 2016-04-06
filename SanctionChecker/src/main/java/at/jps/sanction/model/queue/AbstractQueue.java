/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.model.queue;

import java.util.ArrayList;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractQueue<X> implements Queue<X> {

    static final Logger           logger = LoggerFactory.getLogger(AbstractQueue.class);

    protected long                capacity;
    protected String              name;
    protected String              basePath;

    private boolean               purgeQueuesOnStartup;

    ArrayList<QueueEventListener> queueEventListeners;

    public AbstractQueue() {

    }

    public void initialize() {
        assert (getName() != null) : "Name not configured";
        assert (getBasePath() != null) : "BasePath not configured";
        // assert (getStreamName() != null) : "StreamName not configured";

        // if (isPurgeQueuesOnStartup()) { --> should be done after physical queue is valid
        // clear();
        // }
    }

    public AbstractQueue(final String name, final int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public boolean addMessage(final X message) {
        if (queueEventListeners != null) {
            notifyListeners(message, true);
        }
        return true;
    }

    public String getBasePath() {
        return this.basePath;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public X getNextMessage(final boolean wait) {
        if (queueEventListeners != null) {
            notifyListeners(null, false);
        }

        return null;
    }

    @Override
    public void setBasePath(final String base) {
        this.basePath = base;
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
    public void addListener(final QueueEventListener listener) {

        if (queueEventListeners == null) {
            queueEventListeners = new ArrayList<>();
        }
        queueEventListeners.add(listener);
    }

    private void notifyListeners(final X message, final boolean added) {

        for (final QueueEventListener listener : queueEventListeners) {
            if (added) {
                listener.messageAdded();
            }
            else {
                listener.messageRemoved();
            }
        }
    }

    private static int getConfigQueueSize(final Properties properties, final String streamName, final String queueName) {

        final String queueSize = properties.getProperty(streamName + ".QueueSize." + queueName, "2048");

        final int size = Integer.parseInt(queueSize);

        return size;
    }

    @Override
    public void initialize(final Properties properties, final String streamName, final String queueName) {
        setName(queueName);

        final String base = properties.getProperty(streamName + ".QueueBase" + "." + name, "");
        setBasePath(base);

        setCapacity(getConfigQueueSize(properties, streamName, queueName));

    }

    public boolean isPurgeQueuesOnStartup() {
        return purgeQueuesOnStartup;
    }

    public void setPurgeQueuesOnStartup(final boolean purgeQueuesOnStartup) {
        this.purgeQueuesOnStartup = purgeQueuesOnStartup;
    }

    @Override
    public void flush() {
        // NOOP
    }
}
