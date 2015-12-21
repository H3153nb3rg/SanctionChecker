package at.jps.sanction.model.queue;

public interface QueueEventListener {

    void messageAdded();

    void messageRemoved();

}
