/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.model.worker;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.StreamManager;
import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.MessageStatus;
import at.jps.sanction.model.listhandler.NoWordHitListHandler;
import at.jps.sanction.model.worker.in.InputWorker;

public class AnalyzerWorker extends InputWorker {

    static final Logger logger = LoggerFactory.getLogger(AnalyzerWorker.class);

    public AnalyzerWorker() {
    }

    public AnalyzerWorker(final StreamManager manager) {
        super(manager);
    }

    public void processMessage(final Message message) {
        // do some stuff
        logger.error((message != null) ? "NOOP for message: " + message.getUUID() + " OVERRIDE THIS METHOD" : "Message is NULL");
    }

    @Override
    public Message getNewMessage() {
        final Message message = getStreamManager().getInputQueue().getNextMessage(true);

        return message;
    }

    @Override
    public void handleMessage(final Message message) {

        // super.handleMessage(message); NO

        try {
            if (message != null) {
                logger.info("start check message: " + message.getUUID());

                message.setMessageProcessingStatus(MessageStatus.BUSY_ANALYSE);

                processMessage(message);

                logger.info("stop check message: " + message.getUUID());
            }
            else {
                logger.warn("message was NULL!");
            }
        }
        catch (final Exception e) {
            logger.error((message != null) ? "error checking message: " + message.getUUID() : "Message was NULL Error:" + e.toString());
            logger.debug("Exception: ", e);
            if (message != null) {
                final AnalysisResult analysisResult = new AnalysisResult(message);
                analysisResult.setException(e);
                getStreamManager().addToErrorList(analysisResult);
            }
        }
    }

    public boolean checkIfDeclaredAsNoHit(final String msgFieldToken, final String listEntryToken) {
        boolean doNotCheck = false;

        NoWordHitListHandler nhl = getStreamManager().getNoWordHitListHandler();

        if (nhl != null) {
            Collection<String> listEntries = nhl.getValues().get(msgFieldToken); // getCollection(msgFieldToken);

            if (listEntries != null) {
                for (String token : listEntries) {
                    if (token.equals(listEntryToken)) {
                        doNotCheck = true;
                        break;
                    }
                }
            }
        }

        return doNotCheck;
    }

}
