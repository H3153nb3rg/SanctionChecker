/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.domain;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.core.banking.iban.Iban;
import at.jps.sanction.core.listhandler.reflist.NCCTListHandler;
import at.jps.sanction.core.util.BICHelper;
import at.jps.sanction.core.util.TokenTool;
import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.HitRate;
import at.jps.sanction.model.HitResult;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.OptimizationRecord;
import at.jps.sanction.model.ProcessStep;
import at.jps.sanction.model.WordHitInfo;
import at.jps.sanction.model.listhandler.SanctionListHandler;
import at.jps.sanction.model.listhandler.ValueListHandler;
import at.jps.sanction.model.sl.entities.Entity;
import at.jps.sanction.model.sl.entities.Name;
import at.jps.sanction.model.worker.AnalyzerWorker;
import at.jps.sanction.model.worker.MessageFields;

public abstract class SanctionAnalyzer extends AnalyzerWorker {

    static final Logger logger = LoggerFactory.getLogger(SanctionAnalyzer.class);

    public SanctionAnalyzer() {
        super();
    }

    boolean firstIteration = true; // for checks that have to only be done once

    public abstract MessageFields getFieldsToCheck(final Message message);

    @Override
    public void processMessage(final Message message) {

        boolean isHit = false;
        final long starttime = System.currentTimeMillis();
        if (logger.isInfoEnabled()) logger.info("start check message: " + message.getUUID());
        final AnalysisResult analyzeresult = new AnalysisResult(message);

        firstIteration = true;

        // go through all lists
        // do a generic check
        for (final SanctionListHandler listhandler : getStreamManager().getSanctionListHandlers().values()) {
            if (logger.isInfoEnabled()) logger.info("start check against list: " + listhandler.getListName());

            genericListCheck(listhandler, analyzeresult);
            firstIteration = false;

            if (logger.isInfoEnabled()) logger.info("finished check against list: " + listhandler.getListName());
        }

        isHit = (analyzeresult.getHitList().size() > 0);

        final long stoptime = System.currentTimeMillis();
        final long difftime = stoptime - starttime;

        analyzeresult.setAnalysisStartTime(starttime);
        analyzeresult.setAnalysisStopTime(stoptime);

        if (isHit) {
            if (logger.isInfoEnabled()) logger.info(" message has hits: " + message.getUUID());

            ProcessStep processStep = new ProcessStep();
            processStep.setRemark("Check");
            analyzeresult.addProcessStep(processStep);

            getStreamManager().addToHitList(analyzeresult);
        }
        else {
            if (logger.isInfoEnabled()) logger.info(" message has NO hits: " + message.getUUID());
            getStreamManager().addToNoHitList(analyzeresult);
        }

        if (logger.isInfoEnabled()) logger.info("stop checking message: " + message.getId() + " (Time needed :  " + difftime + " ms)");
    }

    private HitRate checkISO9362(final String msgFieldText) {

        String countryISO = msgFieldText.substring(4, 6); // TODO: hardcoded ISO !!!

        return checkISO4NCCT(countryISO);
    }

    protected HitRate checkISO4NCCT(final String mightBecountry) {
        HitRate hitRate = null;

        String countryISO = mightBecountry;
        String countryLong = getStreamManager().getReferenceListHandlers().get(NCCTListHandler.getInstance().getListName()).getValues().getProperty(countryISO);

        if (countryLong != null) {
            hitRate = new HitRate();

            hitRate.totalHitRateRelative = 100;
            hitRate.totalHitRateAbsolute = 100;
            hitRate.totalHitRatePhrase = 100;

            hitRate.comment = countryLong;
        }

        return hitRate;
    }

    protected HitRate checkIBAN4NCCT(final String msgFieldText) {

        HitRate hitRate = null;

        if (Iban.isValid(msgFieldText)) {
            // just check for ISO by now

            String countryISO = msgFieldText.substring(0, 2);

            hitRate = checkISO4NCCT(countryISO);
        }

        return hitRate;
    }

    protected HitRate checkFieldSpecific(final String msgFieldName, final String msgFieldText) {

        return null;  // this is a plain dummy - override if needed!!
    }

    private void addHitRateResult(final HitRate hitRate, final AnalysisResult analyzeresult, final String msgFieldName) {
        final SanctionListHitResult hr = new SanctionListHitResult();

        hr.setHitField(msgFieldName);
        hr.setHitDescripton(hitRate.comment);
        hr.setHitListName(NCCTListHandler.getInstance().getListName());
        hr.setHitId("-1");
        hr.setHitLegalBasis("NCCT Country hit");    // TODO: hardcoded
        hr.setHitType("NCCT");                      // TODO: hardcoded
        hr.setHitRemark("Ländertreffer");           // TODO: hardcoded

        hr.setAbsolutHit(hitRate.totalHitRateAbsolute);
        hr.setRelativeHit(hitRate.totalHitRateRelative);
        hr.setPhraseHit(hitRate.totalHitRatePhrase);

        analyzeresult.addHitResult(hr);
    }

    protected boolean isFieldToCheck(final String msgFieldName, final String EntityType, final String listname) {
        boolean checkit = (getStreamManager().isFieldToCheck(msgFieldName, listname));

        return checkit;
    }

    protected boolean isFieldToCheckFuzzy(final String msgFieldName, final SanctionListHandler listhandler) {
        boolean fuzzy = listhandler.isFuzzySearch() && getStreamManager().isField2Fuzzy(msgFieldName);

        return fuzzy;
    }

    private void genericListCheck(final SanctionListHandler listhandler, final AnalysisResult analyzeresult) {

        if ((listhandler.getEntityList() != null) && !listhandler.getEntityList().isEmpty()) {

            MessageFields fieldAndValues = getFieldsToCheck(analyzeresult.getMessage());

            for (final Entity entity : listhandler.getEntityList()) {

                // iterate over all entities / addresses /

                for (final Name name : entity.getNames()) {

                    List<String> nameTokens = name.getTokenizedNames();
                    if (nameTokens == null) {
                        nameTokens = TokenTool.getTokenList(name.getWholeName(), listhandler.getDelimiters(), listhandler.getDeadCharacters(), getStreamManager().getMinTokenLen(),
                                getStreamManager().getIndexAusschlussList().getValues(), true);
                        name.setTokenizedNames(nameTokens);
                    }
                    else {
                        // if (logger.isDebugEnabled()) logger.debug("---optimized tokenizing 1 !! ");
                    }

                    // iterate over all Msg fields
                    for (final String msgFieldName : fieldAndValues.getFieldsAndValues().keySet()) {

                        // should field be checked ?
                        if (!isFieldToCheck(msgFieldName, entity.getType(), listhandler.getListName())) {
                            // if (logger.isDebugEnabled()) logger.debug("SKIPPING field: " + msgFieldName);
                            continue;
                        }

                        float totalHitRateRelative = 0;
                        int totalHitRateAbsolute = 0;
                        int totalHitRatePhrase = 0;

                        final String msgFieldText = fieldAndValues.getFieldsAndValues().get(msgFieldName);

                        List<String> msgFieldTokens = fieldAndValues.getTokenizedField(msgFieldName);
                        if (msgFieldTokens == null) {
                            String longText = null;

                            // check if BIC expansion should be done
                            if (getStreamManager().isField2BICTranslate(msgFieldName)) {
                                longText = BICHelper.extendBIC(msgFieldText);

                                if (firstIteration) {// Country check on BIC - ONLY ONCE!!
                                    // String countryISO = msgFieldText.substring(4, 6); // TODO: hardcoded ISO !!!
                                    // String countryLong = getStreamManager().getReferenceListHandlers().get(NCCTListHandler.getInstance().getListName()).getValues().getProperty(countryISO);
                                    HitRate hitRate = checkISO9362(msgFieldText);
                                    if (hitRate != null) {
                                        // we found an ISO in NCCT

                                        addHitRateResult(hitRate, analyzeresult, msgFieldName);

                                    }
                                }
                            }

                            // userxit for each field 1.time
                            if (firstIteration) {
                                HitRate hitRate = checkFieldSpecific(msgFieldName, msgFieldText);

                                // this is only half baked so far !!!!!

                                if (hitRate != null) {
                                    // we found an ISO in NCCT
                                    addHitRateResult(hitRate, analyzeresult, msgFieldName);
                                }
                            }

                            msgFieldTokens = TokenTool.getTokenList(longText != null ? msgFieldText + " " + longText : msgFieldText, listhandler.getDelimiters(), listhandler.getDeadCharacters(),
                                    getStreamManager().getMinTokenLen(), getStreamManager().getStopwordList().getValues(), false);

                            fieldAndValues.setTokenizedField(msgFieldName, msgFieldTokens);
                        }
                        else {
                            // if (logger.isDebugEnabled()) logger.debug("---optimized tokenizing 2 !! ");
                        }

                        for (final String msgFieldToken : msgFieldTokens) {

                            for (final String nameToken : nameTokens) {

                                // check if this combination is not on the NoList list declared as not to check
                                boolean isDeclaredAsNoHit = checkIfDeclaredAsNoHit(msgFieldToken, nameToken);

                                if (isDeclaredAsNoHit) {
                                    if (logger.isDebugEnabled()) {
                                        logger.debug("DECLARED AS NOT HIT : " + msgFieldText + ": " + nameToken);
                                    }

                                }
                                else {

                                    // >>>>> THE COMPARISION <<<<<

                                    float hitValue = TokenTool.compareCheck(nameToken, msgFieldToken, isFieldToCheckFuzzy(msgFieldName, listhandler), getStreamManager().getMinTokenLen(),
                                            getStreamManager().getFuzzyValue());

                                    // single fuzzy limit !!
                                    if (hitValue > getStreamManager().getMinRelVal()) {
                                        totalHitRateRelative += hitValue;

                                        SanctionWordHitInfo swhi = new SanctionWordHitInfo(listhandler.getListName(), entity.getId(), nameToken, msgFieldName, msgFieldToken, (int) hitValue);

                                        // single word hit list if not already in
                                        if (!analyzeresult.getHitTokensList().contains(swhi)) {
                                            analyzeresult.getHitTokensList().add(swhi);
                                        }
                                    }
                                    if (hitValue == 100)      // no fuzzy hit
                                        totalHitRateAbsolute += hitValue;
                                    // hitValue = TokenTool.compareCheck(nameToken, msgFieldToken, false, getStreamManager().getMinTokenLen());
                                    // totalHitRateAbsolute += hitValue;
                                    // if (logger.isDebugEnabled()) logger.debug("compare : " + nameToken + " <-> " + msgFieldToken + " (" + hitValue + ")");
                                }
                            }
                        }

                        boolean addPhrase = false;
                        boolean addAbs = false;
                        boolean addRel = false;

                        // kleineres in größeres wosnsunst...
                        final int minTokens = Math.min(msgFieldTokens.size(), nameTokens.size());

                        if (minTokens > 0) {

                            if (minTokens > 1) {
                                // eval simple text in text search for
                                // phrase
                                // check
                                // but only if both sides contain more than one token
                                final boolean contains = TokenTool.checkContains(msgFieldTokens, nameTokens, " ");  // TODO: this is no goood sol in gen
                                if (contains) {

                                    totalHitRatePhrase = 100 * minTokens;
                                    if ((totalHitRatePhrase / minTokens) > getStreamManager().getMinAbsVal()) {

                                        logger.debug("PHRASECHECK: " + msgFieldText + ": " + name.getWholeName() + " --> " + contains);

                                        addPhrase = true;
                                    }
                                }
                            }
                            else {
                                // no hit but if other hit we go
                                // totalHitRatePhrase = 100;
                            }
                            // hits for one field
                            // TODO: this is a dummy implementation
                            if ((totalHitRateRelative / minTokens) > getStreamManager().getMinRelVal()) {

                                if (logger.isDebugEnabled()) {
                                    final String keys = TokenTool.buildTokenString(nameTokens, " ");

                                    logger.debug("RELATIVE SUM: " + msgFieldText + ": " + keys + " --> " + totalHitRateRelative);
                                }

                                addRel = true;

                            }

                            if ((totalHitRateAbsolute / minTokens) > getStreamManager().getMinAbsVal()) {

                                if (logger.isDebugEnabled()) {
                                    final String keys = TokenTool.buildTokenString(nameTokens, " ");

                                    logger.debug("ABSOLUTE SUM: " + msgFieldText + ": " + keys + " --> " + totalHitRateAbsolute);
                                }

                                addAbs = true;

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

                            // add cumulated hit
                            if ((addAbs) || (addPhrase) || (addRel)) {

                                // remove single word hits which are not relevant

                                final ValueListHandler notSingleWordListHandler = getStreamManager().getNotSingleWordHitList();
                                int nrOfNotSowords = 0;
                                if (notSingleWordListHandler != null) {
                                    nrOfNotSowords = TokenTool.compareTokenLists(notSingleWordListHandler.getValues(), nameTokens);
                                }

                                if ((totalHitRateAbsolute > (nrOfNotSowords * 100)) || (totalHitRateRelative > (nrOfNotSowords * 100)) || (totalHitRatePhrase > (nrOfNotSowords * 100))) {

                                    final SanctionListHitResult hr = new SanctionListHitResult();

                                    boolean addHit = true;

                                    // check optimizer if we have to cleanup some mess ;-)
                                    if (getStreamManager().getTxNoHitOptimizationListHandler() != null) {
                                        for (OptimizationRecord optimizationRecord : getStreamManager().getTxNoHitOptimizationListHandler().getValues()) {
                                            if (optimizationRecord.getWatchListName().equals(listhandler.getListName()) && optimizationRecord.getWatchListId().equals(entity.getId())
                                                    && optimizationRecord.getFieldName().equals(msgFieldName) && optimizationRecord.getToken().equals(msgFieldText))
                                            // TODO: make this more
                                            // versatile !!
                                            {
                                                // or remove hit on "Confirmed" level
                                                if (getStreamManager().getTxNoHitOptimizationListHandler().isAutoDiscardHitsOnConfirmStatus()) {
                                                    // remove it ;-)
                                                    addHit = false;
                                                }
                                                else {
                                                    hr.setHitOptimized(optimizationRecord.getStatus());
                                                }
                                                break;
                                            }
                                        }
                                    }
                                    if (addHit) {

                                        hr.setHitField(msgFieldName);
                                        hr.setHitDescripton(name.getWholeName());
                                        hr.setHitListName(listhandler.getListName());
                                        hr.setHitId(entity.getId());
                                        hr.setHitLegalBasis(entity.getLegalBasis());
                                        hr.setHitExternalUrl(entity.getInformationUrl());
                                        hr.setHitType(listhandler.getType());
                                        hr.setEntityType(entity.getType());
                                        hr.setHitRemark(entity.getComment());

                                        hr.setAbsolutHit(totalHitRateAbsolute);
                                        hr.setPhraseHit(totalHitRatePhrase);
                                        hr.setRelativeHit((int) totalHitRateRelative);

                                        analyzeresult.addHitResult(hr);
                                    }
                                    else {
                                        if (logger.isDebugEnabled()) {
                                            logger.debug("Optimizer: " + msgFieldText + " -> " + name.getWholeName() + " : hit removed due Confirmed as NOT NEEDED!");
                                        }
                                    }
                                }
                                else {
                                    // if (logger.isDebugEnabled()) {
                                    // logger.debug("NSWH: " + msgFieldText + ": hit removed due to single word hit!");
                                    // }
                                }
                            }
                        }
                        else {
                            // System.out.println("leereListe breakpoint");
                        }
                    }
                }
            }
        }

        // cleanup single hits -- this should be optimized !!

        for (Iterator<WordHitInfo> iteratorWordHits = analyzeresult.getHitTokensList().listIterator(); iteratorWordHits.hasNext();) {
            WordHitInfo wordHit = iteratorWordHits.next();

            boolean found = false;
            for (HitResult hitResult : analyzeresult.getHitList()) {
                if ((((SanctionListHitResult) hitResult).getHitId() == ((SanctionWordHitInfo) wordHit).getSanctionListId())
                        && (((SanctionListHitResult) hitResult).getHitField() == ((SanctionWordHitInfo) wordHit).getFieldName()))  // only remove if Id AND FieldID is identical ( LISTNAME should be
                                                                                                                                   // equal as well)....
                {
                    found = true;
                    break;
                }
            }

            if (!found) {

                // if (logger.isDebugEnabled()) {
                // logger.debug("removed orphaned wordhit : " + wordHit);
                // }

                iteratorWordHits.remove();

            }
        }
    }
}
