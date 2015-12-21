package at.jps.sl.gui.util;

import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.queue.Queue;

public interface RingBufferQueue {

    AnalysisResult getNextMessage();

    AnalysisResult getPrevMessage();

    Queue<AnalysisResult> getQueue();

    boolean hasNextMessage();

    boolean hasPrevMessage();

    void setQueue(Queue<AnalysisResult> queue);

}
