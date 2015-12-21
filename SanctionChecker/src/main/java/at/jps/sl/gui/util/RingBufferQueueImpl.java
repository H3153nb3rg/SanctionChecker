package at.jps.sl.gui.util;

import java.util.ArrayList;

import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.queue.Queue;

public class RingBufferQueueImpl implements RingBufferQueue {

    private int                             cursor = -1;
    private final ArrayList<AnalysisResult> buffer = new ArrayList<AnalysisResult>();

    private Queue<AnalysisResult> queue;

    private AnalysisResult getMessage(final int index) {
        // System.out.println("Cursor: " + index);
        return buffer.get(index);

    }

    @Override
    public AnalysisResult getNextMessage() {

        AnalysisResult message = null;

        if (hasNextMessage()) {
            cursor++;
            message = getMessage(cursor);
        }

        return message;
    }

    @Override
    public AnalysisResult getPrevMessage() {

        AnalysisResult message = null;

        if (cursor > 0) {
            cursor--;
            message = getMessage(cursor);
        }

        return message;
    }

    @Override
    public Queue<AnalysisResult> getQueue() {

        return queue;
    }

    @Override
    public boolean hasNextMessage() {

        boolean existsNext = false;

        if (cursor < (buffer.size() - 1)) {
            existsNext = true;
        }
        else {
            final AnalysisResult ar = queue.getNextMessage(false);
            if (ar != null) {
                buffer.add(ar);
                existsNext = true;
            }
        }

        return existsNext;
    }

    @Override
    public boolean hasPrevMessage() {
        return (cursor > 0);
    }

    @Override
    public void setQueue(final Queue<AnalysisResult> queue) {
        this.queue = queue;
    }

}
