package at.jps.sanction.model.worker;

import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.MessageStatus;
import at.jps.sanction.model.queue.Queue;
import at.jps.sanction.model.worker.out.OutputWorker;

public class PostHitWorker extends OutputWorker {

    @Override
    public void handleMessage(final AnalysisResult message) {
        super.handleMessage(message);
        message.setAnalysisStatus(MessageStatus.BUSY_POSTPROCESS);

    }

    @Override
    public void finishMessage(AnalysisResult message) {
        super.finishMessage(message);
        message.setAnalysisStatus(MessageStatus.FINISHED_POSTPROCESS);

        // add for further Processing
        final Queue<AnalysisResult> outputQueue = getOutQueue();
        if (outputQueue != null) {
            outputQueue.addMessage(message);
        }

    }

}
