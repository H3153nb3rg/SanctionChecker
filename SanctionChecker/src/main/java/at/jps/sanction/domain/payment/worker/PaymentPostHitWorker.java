package at.jps.sanction.domain.payment.worker;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.model.OptimizationRecord;
import at.jps.sanction.model.listhandler.OptimizationListHandler;

public abstract class PaymentPostHitWorker extends PaymentPostNoHitWorker {

    static final Logger logger = LoggerFactory.getLogger(PaymentPostNoHitWorker.class);

    @Override
    protected OptimizationListHandler getOptimizationListHandler() {
        return getStreamManager().getTxHitOptimizationListHandler();
    }

    @Override
    protected void filterList(OptimizationListHandler optiListHandler, ArrayList<OptimizationRecord> orList) {
        optiListHandler.removeList(orList);
    }

    // @Override
    // public MessageContent getMessageContent(Message message) {
    // // TODO Auto-generated method stub
    // return null;
    // }

}
