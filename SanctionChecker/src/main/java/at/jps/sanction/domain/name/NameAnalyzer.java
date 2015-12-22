/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.domain.name;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.util.TokenTool;
import at.jps.sanction.domain.SanctionListHitResult;
import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.HitResult;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.listhandler.SanctionListHandler;
import at.jps.sanction.model.sl.entities.WL_Entity;
import at.jps.sanction.model.sl.entities.WL_Name;
import at.jps.sanction.model.worker.AnalyzerWorker;

public class NameAnalyzer extends AnalyzerWorker {

    static final Logger logger = LoggerFactory.getLogger(NameAnalyzer.class);

    public NameAnalyzer() {
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
            getStreamManager().addToHitList(analyzeresult);
        }
        else {
            getStreamManager().addToNoHitList(analyzeresult);
        }

    }

    private void genericListCheck(final SanctionListHandler listhandler, final AnalysisResult analyzeresult) {

        if ((listhandler.getEntityList() != null) && listhandler.getEntityList().isEmpty()) {

            final String msgText = analyzeresult.getMessage().getRawContent();

            final List<String> textTokens = TokenTool.getTokenList(msgText, listhandler.getDelimiters(), listhandler.getDeadCharacters(), getStreamManager().getMinTokenLen(),
                    getStreamManager().getStopwordList().getValues(), false);

            for (final WL_Entity entity : listhandler.getEntityList()) {
                for (final WL_Name name : entity.getNames()) {
                    final List<String> nameTokens = TokenTool.getTokenList(name.getWholeName(), listhandler.getDelimiters(), listhandler.getDeadCharacters(), getStreamManager().getMinTokenLen(),
                            getStreamManager().getIndexAusschlussList().getValues(), true);

                    float totalHitRateRelative = 0;
                    int totalHitRateAbsolute = 0;

                    for (final String token : textTokens) {

                        for (final String keyToken : nameTokens) {
                            float hitValue = TokenTool.compareCheck(keyToken, token, listhandler.isFuzzySearch(), getStreamManager().getMinTokenLen(), getStreamManager().getFuzzyValue());

                            if (hitValue == 100) {
                                totalHitRateAbsolute += hitValue;
                            }
                            else if (hitValue > 79) {
                                totalHitRateRelative += hitValue;
                            }
                        }
                    }

                    // kleineres in größeres wosnsunst...
                    final int minTokens = Math.min(textTokens.size(), nameTokens.size());

                    if (minTokens > 0) {
                        // eval simple text in text search for phrase
                        // check
                        final boolean contains = TokenTool.checkContains(textTokens, nameTokens, " ");
                        if (contains) {

                            logger.debug("CONTAINSCHECK: " + msgText + ": " + name.getWholeName() + "- total --> " + contains);

                            final HitResult hr = new HitResult();

                            hr.setAbsolutHit(100);
                            hr.setHitField("flat");
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
                            final SanctionListHitResult hr = new SanctionListHitResult();

                            hr.setRelativeHit((int) totalHitRateRelative);
                            hr.setHitField("flat");
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
                            final SanctionListHitResult hr = new SanctionListHitResult();

                            hr.setAbsolutHit(totalHitRateAbsolute);
                            hr.setHitField("flat");
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
