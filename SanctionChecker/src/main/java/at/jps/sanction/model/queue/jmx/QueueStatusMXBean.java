package at.jps.sanction.model.queue.jmx;

public interface QueueStatusMXBean {

    public String[] getQueueNames();

    public long getQueueSize(final String queueName);

}

