package at.jps.sanction.domain.payment.swift.worker;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.domain.payment.swift.SwiftAnalyzer;
import at.jps.sanction.domain.payment.worker.PaymentPostNoHitWorker;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.MessageContent;
import at.jps.sanction.model.OptimizationRecord;
import at.jps.sanction.model.listhandler.OptimizationListHandler;

public class SwiftPostNoHitWorker extends PaymentPostNoHitWorker {

    static final Logger logger = LoggerFactory.getLogger(SwiftPostNoHitWorker.class);

    @Override
    public MessageContent getMessageContent(final Message message) {
        return SwiftAnalyzer.getFieldsToCheckInternal(message);
    }

    @Override
    protected void filterList(final OptimizationListHandler optiListHandler, final ArrayList<OptimizationRecord> orList) {
        optiListHandler.appendList(orList, false);
    }

}
