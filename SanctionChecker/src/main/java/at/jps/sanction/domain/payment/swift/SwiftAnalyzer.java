/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.domain.payment.swift;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.domain.SanctionAnalyzer;
import at.jps.sanction.model.HitRate;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.MessageContent;

public class SwiftAnalyzer extends SanctionAnalyzer {

    static final Logger logger = LoggerFactory.getLogger(SwiftAnalyzer.class);

    public SwiftAnalyzer() {
        super();
    }

    public static MessageContent getFieldsToCheckInternal(final Message message) {

        MessageContent messageContent = message.getMessageContent();

        if (messageContent == null) {
            messageContent = new MessageContent();

            final HashMap<String, String> fieldsAndValues = new HashMap<>();
            messageContent.setFieldsAndValues(fieldsAndValues);

            final String msgText = message.getRawContent();
            final List<SwiftMessageParser.MessageBlock> msgBlocks = SwiftMessageParser.parseMessage(msgText);

            for (final SwiftMessageParser.MessageBlock messageBlock : msgBlocks) {

                // get MT type and direction for now IN ONE TOKEN !!
                if (messageBlock.id.equals("2")) {
                    messageContent.setMessageType(messageBlock.swiftTxMsgBlock.substring(2, 6));
                }

                for (final String msgFieldName : messageBlock.getFields().keySet()) {
                    final String msgFieldText = messageBlock.getFields().get(msgFieldName);

                    fieldsAndValues.put(msgFieldName, msgFieldText);

                    // add business XT ID to Message

                    if (msgFieldName.contentEquals("20")) {  // TODO: this is not generic enough !!
                        if (message instanceof SwiftMessage) {
                            ((SwiftMessage) message).setBusinessId(msgFieldText.trim());
                        }
                    }
                }
            }
            message.setMessageContent(messageContent);
        }
        return messageContent;
    }

    @Override
    public MessageContent getFieldsToCheck(final Message message) {

        return getFieldsToCheckInternal(message);
    }

    // @Override
    // protected boolean isFieldToCheck(final String msgFieldName, final String entityType, final String listname, final String entityCategory) {
    //
    // boolean checkit = (super.isFieldToCheck(msgFieldName, entityType, listname, entityCategory));
    //
    // if (checkit) {
    // if (entityType.contentEquals("TRANSPORT")) { // Vessels only in field 70 !
    // checkit = msgFieldName.contentEquals("70");
    // }
    // /*
    // * else if ((listname.equals("INDKTO") && ( msgFieldName.equals("KS")) ....
    // */
    // }
    //
    // return checkit;
    // }

    @Override
    protected HitRate checkFieldSpecific(final String msgFieldName, final String msgFieldText) {

        HitRate hitrate = null;

        if (msgFieldName.lastIndexOf('F') == (msgFieldName.length() - 1)) {
            hitrate = checkCountry4NCCTOptionF(msgFieldText);
        }

        if (msgFieldName.contentEquals("AMD") || msgFieldName.contentEquals("ALD")) {
            final int pos = 1;
            final String countryISO = msgFieldText.substring(pos, pos + 2);  // TODO: do a IBAN Check on 1. TOKEN ( not on raw fieldcontent) !!
            return checkISO4NCCT(countryISO);

        }

        return hitrate;
    }

    // REGEX BIC ([a-zA-Z]{4}[a-zA-Z]{2}[a-zA-Z0-9]{2}([a-zA-Z0-9]{3})?)
    final static String[] OptionFTokens = { "ARNU/", "CCPT/", "CUST/", "DRLC/", "NIDN/", "SOSE/", "TXID/", "3/", "5/", "6/", "7/", "8/" };

    protected HitRate checkCountry4NCCTOptionF(final String msgFieldText) {
        int pos = -1;

        for (final String fToken : OptionFTokens) {
            pos = msgFieldText.indexOf(fToken);
            if (pos > -1) {
                pos += fToken.length();
                break;
            }
        }
        HitRate hr = null;
        if (pos > 0) {
            final String countryISO = msgFieldText.substring(pos, pos + 2);
            hr = checkISO4NCCT(countryISO);
        }
        return hr;
    }

}
