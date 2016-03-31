package at.jps.sanction.model.worker;

import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.MessageStatus;
import at.jps.sanction.model.worker.out.OutputWorker;

public class PostHitWorker extends OutputWorker {

    @Override
    public void handleMessage(final AnalysisResult message) {
        message.setAnalysisStatus(MessageStatus.BUSY_POSTPROCESS);
    }

}
