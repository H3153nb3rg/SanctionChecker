package at.jps.sanction.domain.worker;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.domain.swift.worker.SwiftPostNoHitWorker;
import at.jps.sanction.model.OptimizationRecord;
import at.jps.sanction.model.listhandler.OptimizationListHandler;

public class SanctionPostHitWorker extends SwiftPostNoHitWorker {

    static final Logger logger = LoggerFactory.getLogger(SanctionPostNoHitWorker.class);

    @Override
    protected OptimizationListHandler getOptimizationListHandler() {
        return getStreamManager().getTxHitOptimizationListHandler();
    }

    @Override
    protected void filterList(OptimizationListHandler optiListHandler, ArrayList<OptimizationRecord> orList) {
        optiListHandler.removeList(orList);
    }

}
