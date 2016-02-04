package at.jps.sanction.domain.swift.worker;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.domain.swift.SwiftAnalyzer;
import at.jps.sanction.domain.worker.SanctionPostNoHitWorker;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.MessageContent;
import at.jps.sanction.model.OptimizationRecord;
import at.jps.sanction.model.listhandler.OptimizationListHandler;

public class SwiftPostNoHitWorker extends SanctionPostNoHitWorker {

    static final Logger logger = LoggerFactory.getLogger(SwiftPostNoHitWorker.class);

    @Override
    public MessageContent getMessageContent(Message message) {
        return SwiftAnalyzer.getFieldsToCheckInternal(message);
    }

    protected void filterList(OptimizationListHandler optiListHandler, ArrayList<OptimizationRecord> orList) {
        optiListHandler.appendList(orList, false);
    }

}
