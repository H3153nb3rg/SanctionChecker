/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.domain.sepa;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.domain.SanctionAnalyzer;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.MessageContent;

public class SepaAnalyzer extends SanctionAnalyzer {

    static final Logger         logger = LoggerFactory.getLogger(SepaAnalyzer.class);

    static private List<String> messageTypes;                                                                                                                                                                                                          // nix
                                                                                                                                                                                                                                                       // good

    public SepaAnalyzer() {
        super();
    }

    public static MessageContent getFieldsToCheckInternal(final Message message) {
        MessageContent messageContent = message.getMessageContent();

        if (messageContent == null) {
            messageContent = new MessageContent();

            final String msgText = message.getRawContent();
            HashMap<String, String> fieldsAndValues = SepaMessageParser.parseMessage(msgText, messageTypes);
            messageContent.setFieldsAndValues(fieldsAndValues);

            // add business XT ID to Message
            if (message instanceof SepaMessage) {
                ((SepaMessage) message).setBusinessId(fieldsAndValues.get("/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/PmtId/TxId"));
            }

            message.setMessageContent(messageContent);
        }
        return messageContent;
    }

    public MessageContent getFieldsToCheck(final Message message) {

        return getFieldsToCheckInternal(message);
    }

    public List<String> getMessageTypes() {
        return messageTypes;
    }

    public void setMessageTypes(List<String> messageTypes) {
        this.messageTypes = messageTypes;
    }

}
