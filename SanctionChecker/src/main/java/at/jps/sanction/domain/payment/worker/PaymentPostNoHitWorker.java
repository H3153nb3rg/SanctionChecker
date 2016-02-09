package at.jps.sanction.domain.payment.worker;

import java.util.ArrayList;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.domain.payment.PaymentHitResult;
import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.HitResult;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.MessageContent;
import at.jps.sanction.model.OptimizationRecord;
import at.jps.sanction.model.listhandler.OptimizationListHandler;
import at.jps.sanction.model.worker.PostNoHitWorker;

public abstract class PaymentPostNoHitWorker extends PostNoHitWorker {

    static final Logger logger = LoggerFactory.getLogger(PaymentPostNoHitWorker.class);

    public abstract MessageContent getMessageContent(Message message);

    @Override
    public void handleMessage(AnalysisResult message) {

        OptimizationListHandler optiListHandler = getOptimizationListHandler();

        // TODO: this is NOT good - we have to parse again.....

        ArrayList<OptimizationRecord> orList = new ArrayList<OptimizationRecord>();

        // we add records as we go on
        for (HitResult slhr : message.getHitList()) {

            String msgFieldContent = null;

            MessageContent messageContent = getMessageContent(message.getMessage());

            msgFieldContent = messageContent.getFieldsAndValues().get(((PaymentHitResult) slhr).getHitField());

            if (msgFieldContent != null) {

                OptimizationRecord or = new OptimizationRecord(((PaymentHitResult) slhr).getHitField(), msgFieldContent, ((PaymentHitResult) slhr).getHitListName(),
                        ((PaymentHitResult) slhr).getHitId(), OptimizationRecord.OPTI_STATUS_NEW);
                if (!orList.contains(or)) {
                    orList.add(or);
                }

                if (logger.isDebugEnabled()) {
                    final StringBuilder status = new StringBuilder();

                    status.append("added opti: " + or.toString()).append(SystemUtils.LINE_SEPARATOR);
                    logger.debug(status.toString());
                }
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
