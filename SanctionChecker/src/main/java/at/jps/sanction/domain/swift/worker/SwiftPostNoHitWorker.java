package at.jps.sanction.domain.swift.worker;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.domain.SanctionListHitResult;
import at.jps.sanction.domain.swift.SwiftMessageParser;
import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.HitResult;
import at.jps.sanction.model.OptimizationRecord;
import at.jps.sanction.model.listhandler.OptimizationListHandler;
import at.jps.sanction.model.worker.PostNoHitWorker;

public class SwiftPostNoHitWorker extends PostNoHitWorker {

    static final Logger logger = LoggerFactory.getLogger(SwiftPostNoHitWorker.class);

    @Override
    public void handleMessage(AnalysisResult message) {

        OptimizationListHandler optiListHandler = getOptimizationListHandler();

        // TODO: this is NOT good - we have to parse again.....

        final List<SwiftMessageParser.MessageBlock> msgBlocks = SwiftMessageParser.parseMessage(message.getMessage().getContent());

        ArrayList<OptimizationRecord> orList = new ArrayList<OptimizationRecord>();

        // we add records as we go on
        for (HitResult slhr : message.getHitList()) {

            String msgFieldContent = null;

            for (SwiftMessageParser.MessageBlock msgb : msgBlocks) {
                if (msgb.getFields() != null) {
                    msgFieldContent = msgb.getFields().get(((SanctionListHitResult) slhr).getHitField());
                }
                if (msgFieldContent != null) {
                    break;
                }
            }

            OptimizationRecord or = new OptimizationRecord(((SanctionListHitResult) slhr).getHitField(), msgFieldContent, ((SanctionListHitResult) slhr).getHitListName(),
                    ((SanctionListHitResult) slhr).getHitId(), OptimizationRecord.OPTI_STATUS_NEW);
            if (!orList.contains(or)) {
                orList.add(or);
            }

            if (logger.isDebugEnabled()) {
                final StringBuilder status = new StringBuilder();

                status.append("added opti: " + or.toString()).append(SystemUtils.LINE_SEPARATOR);
                logger.debug(status.toString());
            }
        }

        // optiListHandler.writeList(orList, true);

        filterList(optiListHandler, orList);

        optiListHandler.writeList(optiListHandler.getValues(), false);

    }

    protected OptimizationListHandler getOptimizationListHandler() {
        return getStreamManager().getTxNoHitOptimizationListHandler();
    }

    protected void filterList(OptimizationListHandler optiListHandler, ArrayList<OptimizationRecord> orList) {
        optiListHandler.appendList(orList, false);
    }

}
