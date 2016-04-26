/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.domain.person;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.text.StrTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.util.TokenTool;
import at.jps.sanction.domain.SanctionHitResult;
import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.HitResult;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.MessageContent;
import at.jps.sanction.model.listhandler.SanctionListHandler;
import at.jps.sanction.model.wl.entities.WL_Entity;
import at.jps.sanction.model.wl.entities.WL_Name;
import at.jps.sanction.model.worker.AnalyzerWorker;

public class PersonAnalyzer extends AnalyzerWorker {

    static final Logger logger = LoggerFactory.getLogger(PersonAnalyzer.class);

    public PersonAnalyzer() {
        super();
    }

    @Override
    public void processMessage(final Message message) {

        boolean isHit = false;

        final AnalysisResult analyzeresult = new AnalysisResult(message);

        // here the magic happens....
        // not much for now but more than nothing
        // go through all lists
        // do a generic check
        for (final SanctionListHandler listhandler : getStreamManager().getSanctionListHandlers().values()) {
            logger.info("start check against list: " + listhandler.getListName());

            genericListCheck(listhandler, analyzeresult);

            logger.info("finished check against list: " + listhandler.getListName());
        }

        isHit = (analyzeresult.getHitList().size() > 0);

        if (isHit) {
            // getStreamManager().addToHitList(analyzeresult);
            getHitQueue().addMessage(analyzeresult);
        }
        else {
            // getStreamManager().addToNoHitList(analyzeresult);
            getNoHitQueue().addMessage(analyzeresult);
        }

    }

    public static MessageContent getFieldsToCheckInternal(final Message message) {

        MessageContent messageContent = message.getMessageContent();

        if (messageContent == null) {
            messageContent = new MessageContent();

            final HashMap<String, String> fieldsAndValues = new HashMap<String, String>();
            messageContent.setFieldsAndValues(fieldsAndValues);

            final String msgText = message.getRawContent();

            final StrTokenizer textTokenizer = new StrTokenizer(msgText, ","); // TODO hardcoded delimiter
            textTokenizer.setEmptyTokenAsNull(false);
            textTokenizer.setIgnoreEmptyTokens(false);

            int i = -2; // TODO: there are currently 2 dummy columns in our testsample ( list & id )
            while (textTokenizer.hasNext()) {
                final String value = textTokenizer.next();
                if ((value != null) && (value.length() > 0) && (i >= 0)) {
                    fieldsAndValues.put(PersonMessage.fieldNames[i], value);
                }
                i++;
            }
            message.setMessageContent(messageContent);
        }
        return messageContent;
    }

    public MessageContent getFieldsToCheck(final Message message) {

        return getFieldsToCheckInternal(message);
    }

    private void genericListCheck(final SanctionListHandler listhandler, final AnalysisResult analyzeresult) {

        if ((listhandler.getEntityList() != null) && !listhandler.getEntityList().isEmpty()) {

            final MessageContent messageContent = getFieldsToCheck(analyzeresult.getMessage());

            if (messageContent != null) {

                // first we check the wholename field

                final String msgText = messageContent.getFieldsAndValues().get(PersonMessage.fieldNames[3]);  // "wholename"

                final List<String> fieldContentTokens = TokenTool.getTokenList(msgText, listhandler.getDelimiters(), listhandler.getDeadCharacters(), getStreamManager().getMinTokenLen(),
                        getStreamManager().getStopwordList().getValues(), false);

                for (final WL_Entity entity : listhandler.getEntityList()) {
                    for (final WL_Name name : entity.getNames()) {
                        final List<String> nameTokens = TokenTool.getTokenList(name.getWholeName(), listhandler.getDelimiters(), listhandler.getDeadCharacters(), getStreamManager().getMinTokenLen(),
                                getStreamManager().getIndexAusschlussList().getValues(), true);

                        float totalHitRateRelative = 0;
                        int totalHitRateAbsolute = 0;

                        for (final String token : fieldContentTokens) {

                            for (final String keyToken : nameTokens) {
                                final float hitValue = TokenTool.compareCheck(keyToken, token, listhandler.isFuzzySearch(), getStreamManager().getMinTokenLen(), getStreamManager().getFuzzyValue());

                                if (hitValue == 100) {
                                    totalHitRateAbsolute += hitValue;
                                }
                                else if (hitValue > 79) {
                                    totalHitRateRelative += hitValue;
                                }
                            }
                        }

                        // kleineres in größeres wosnsunst...
                        final int minTokens = Math.min(fieldContentTokens.size(), nameTokens.size());

                        if (minTokens > 0) {
                            // eval simple text in text search for phrase
                            // check
                            final boolean contains = TokenTool.checkContains(fieldContentTokens, nameTokens, " ");
                            if (contains) {

                                logger.debug("CONTAINSCHECK: " + msgText + ": " + name.getWholeName() + "- total --> " + contains);

                                final HitResult hr = new HitResult();

                                hr.setAbsolutHit(100);
                                hr.setHitField("wholename");
                                hr.setHitDescripton(listhandler.getListName() + ": Containscheck:" + name.getWholeName());

                                analyzeresult.addHitResult(hr);
                            }

                            // hits for one field
                            // TODO: this is a dummy implementation
                            if ((totalHitRateRelative / minTokens) > getStreamManager().getMinRelVal()) {

                                if (logger.isDebugEnabled()) {
                                    final String keys = TokenTool.buildTokenString(nameTokens, " ");

                                    logger.debug("RELATIVE SUM: " + msgText + ": " + keys + "- total --> " + totalHitRateRelative);
                                }
                                final SanctionHitResult hr = new SanctionHitResult();

                                hr.setRelativeHit((int) totalHitRateRelative);
                                hr.setHitField(PersonMessage.fieldNames[3]);  // "wholename"
                                hr.setHitDescripton(listhandler.getListName() + " :" + name.getWholeName());

                                hr.setHitId(entity.getWL_Id());
                                hr.setHitLegalBasis(entity.getLegalBasis());
                                // hr.setHitExternalUrl(entity.getPdfLink());

                                analyzeresult.addHitResult(hr);
                            }

                            if ((totalHitRateAbsolute / minTokens) > getStreamManager().getMinAbsVal()) {

                                if (logger.isDebugEnabled()) {
                                    final String keys = TokenTool.buildTokenString(nameTokens, " ");

                                    logger.debug("ABSOLUTE SUM: " + msgText + ": " + keys + "- total --> " + totalHitRateAbsolute);
                                }
                                final SanctionHitResult hr = new SanctionHitResult();

                                hr.setAbsolutHit(totalHitRateAbsolute);
                                hr.setHitField(PersonMessage.fieldNames[3]);  // "wholename"
                                hr.setHitDescripton(listhandler.getListName() + " :" + name.getWholeName());

                                hr.setHitId(entity.getWL_Id());
                                hr.setHitLegalBasis(entity.getLegalBasis());
                                hr.setHitExternalUrl(entity.getInformationUrl());

                                analyzeresult.addHitResult(hr);

                            }
                            else {
                                // if (((totalHitRateAbsolute == 100) &&
                                // (keyTokens.size() ==1)) ||
                                // ((totalHitRateAbsolute > 100) &&
                                // (keyTokens.size() >1)))
                                // System.out.println(field
                                // +" <-> "+TokenComparer
                                // .buildTokenString(keyTokens, " ")
                                // +totalHitRateAbsolute);
                            }
                        }
                        else {
                            // System.out.println("leereListe breakpoint");
                        }
                    }
                }
            }
        }
    }
}
