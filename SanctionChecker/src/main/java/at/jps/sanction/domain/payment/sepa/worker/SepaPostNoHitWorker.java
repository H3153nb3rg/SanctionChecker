package at.jps.sanction.domain.payment.sepa.worker;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.domain.payment.sepa.SepaAnalyzer;
import at.jps.sanction.domain.payment.worker.PaymentPostNoHitWorker;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.MessageContent;
import at.jps.sanction.model.OptimizationRecord;
import at.jps.sanction.model.listhandler.OptimizationListHandler;

public class SepaPostNoHitWorker extends PaymentPostNoHitWorker {

    static final Logger logger = LoggerFactory.getLogger(SepaPostNoHitWorker.class);

    @Override
    public MessageContent getMessageContent(Message message) {
        return SepaAnalyzer.getFieldsToCheckInternal(message);
    }

    protected void filterList(OptimizationListHandler optiListHandler, ArrayList<OptimizationRecord> orList) {
        optiListHandler.appendList(orList, false);
    }

}
