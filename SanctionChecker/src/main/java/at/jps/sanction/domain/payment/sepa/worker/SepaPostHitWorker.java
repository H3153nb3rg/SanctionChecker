package at.jps.sanction.domain.payment.sepa.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.domain.payment.sepa.SepaAnalyzer;
import at.jps.sanction.domain.payment.worker.PaymentPostHitWorker;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.MessageContent;

public class SepaPostHitWorker extends PaymentPostHitWorker {

    static final Logger logger = LoggerFactory.getLogger(SepaPostNoHitWorker.class);

    @Override
    public MessageContent getMessageContent(Message message) {
        return SepaAnalyzer.getFieldsToCheckInternal(message);
    }
}
