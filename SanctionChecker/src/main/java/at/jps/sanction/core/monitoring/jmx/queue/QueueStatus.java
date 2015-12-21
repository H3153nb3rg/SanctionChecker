package at.jps.sanction.core.monitoring.jmx.queue;

import at.jps.sanction.core.StreamManager;
import at.jps.sanction.model.queue.Queue;
import at.jps.sanction.model.queue.jmx.QueueStatusMXBean;

public class QueueStatus implements QueueStatusMXBean {

    private StreamManager streamManager;

    public QueueStatus(final StreamManager streamManager) {
        this.streamManager = streamManager;
    }

    @Override
    public String[] getQueueNames() {

        String names[] = new String[streamManager.getQueueDictionary().size()];

        streamManager.getQueueDictionary().keySet().toArray(names);

        return names;
    }

    @Override
    public long getQueueSize(String queueName) {

        Queue<?> queue = streamManager.getQueueDictionary().get(queueName);

        return (queue != null ? queue.size() : 0);
    }

}
