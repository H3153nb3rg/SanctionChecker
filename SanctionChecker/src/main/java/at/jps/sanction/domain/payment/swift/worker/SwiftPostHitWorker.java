package at.jps.sanction.domain.payment.swift.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.domain.payment.swift.SwiftAnalyzer;
import at.jps.sanction.domain.payment.worker.PaymentPostHitWorker;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.MessageContent;

public class SwiftPostHitWorker extends PaymentPostHitWorker {

    static final Logger logger = LoggerFactory.getLogger(SwiftPostNoHitWorker.class);

    @Override
    public MessageContent getMessageContent(final Message message) {
        return SwiftAnalyzer.getFieldsToCheckInternal(message);
    }
}
