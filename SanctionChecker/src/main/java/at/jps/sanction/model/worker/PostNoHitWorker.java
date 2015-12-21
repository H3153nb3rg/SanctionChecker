package at.jps.sanction.model.worker;

import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.worker.out.OutputWorker;

public class PostNoHitWorker extends OutputWorker {

    @Override
    public void handleMessage(AnalysisResult message) {
        System.out.println("NOOP here :" + getClass().getSimpleName());

    }

}
