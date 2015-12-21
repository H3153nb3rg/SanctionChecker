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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.domain.SanctionAnalyzer;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.worker.MessageFields;

public class SepaAnalyzer extends SanctionAnalyzer {

    static final Logger logger = LoggerFactory.getLogger(SepaAnalyzer.class);

    public SepaAnalyzer() {
        super();
    }

    public MessageFields getFieldsToCheck(final Message message) {
        MessageFields messageFields = new MessageFields();

        final String msgText = message.getContent();
        HashMap<String, String> fieldsAndValues = SepaMessageParser.parseMessage(msgText, ((SepaStreamManager) getStreamManager()).getSupportedMessageTypes());
        messageFields.setFieldsAndValues(fieldsAndValues);

        return messageFields;
    }

}
