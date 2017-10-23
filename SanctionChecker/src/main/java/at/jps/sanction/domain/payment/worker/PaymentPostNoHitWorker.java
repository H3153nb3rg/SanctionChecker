package at.jps.sanction.domain.payment.worker;

import java.util.ArrayList;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.domain.SanctionHitResult;
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
    public void handleMessage(final AnalysisResult message) {

        super.handleMessage(message);

        final OptimizationListHandler optiListHandler = getOptimizationListHandler();

        // TODO: this is NOT good - we have to parse again.....

        final ArrayList<OptimizationRecord> orList = new ArrayList<>();

        // we add records as we go on
        for (final HitResult slhr : message.getHitList()) {

            String msgFieldContent = null;

            final MessageContent messageContent = getMessageContent(message.getMessage());

            msgFieldContent = messageContent.getFieldsAndValues().get(((SanctionHitResult) slhr).getHitField());

            if (msgFieldContent != null) {

                final OptimizationRecord or = new OptimizationRecord(((SanctionHitResult) slhr).getHitField(), msgFieldContent, ((SanctionHitResult) slhr).getHitListName(),
                        ((SanctionHitResult) slhr).getHitId(), OptimizationRecord.OPTI_STATUS_NEW);
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

    protected void filterList(final OptimizationListHandler optiListHandler, final ArrayList<OptimizationRecord> orList) {
        optiListHandler.appendList(orList, false);
    }

}
