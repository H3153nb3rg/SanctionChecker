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
import at.jps.sanction.model.MessageContent;
import at.jps.sanction.model.OptimizationRecord;
import at.jps.sanction.model.ProcessStep;
import at.jps.sanction.model.WordHitInfo;
import at.jps.sanction.model.listhandler.SanctionListHandler;
import at.jps.sanction.model.listhandler.ValueListHandler;
import at.jps.sanction.model.wl.entities.SL_Entry;
import at.jps.sanction.model.worker.AnalyzerWorker;

public abstract class SanctionAnalyzer extends AnalyzerWorker {

    static final Logger logger = LoggerFactory.getLogger(SanctionAnalyzer.class);

    public SanctionAnalyzer() {
        super();
    }

    boolean firstIteration = true; // for checks that have to only be done once

    public abstract MessageContent getFieldsToCheck(final Message message);

    @Override
    public void processMessage(final Message message) {

        boolean isHit = false;
        final long starttime = System.currentTimeMillis();
        if (logger.isInfoEnabled()) {
            logger.info("start check message: " + message.getUUID());
        }
        final AnalysisResult analyzeresult = new AnalysisResult(message);

        firstIteration = true;

        // go through all lists
        // do a generic check
        for (final SanctionListHandler listhandler : getStreamManager().getSanctionListHandlers().values()) {
            if (logger.isInfoEnabled()) {
                logger.info("start check against list: " + listhandler.getListName() + " (Elements # " + listhandler.getEntityList().size() + ")");
            }

            genericListCheck(listhandler, analyzeresult);
            firstIteration = false;

            if (logger.isInfoEnabled()) {
                logger.info("finished check against list: " + listhandler.getListName());
            }
        }

        isHit = (analyzeresult.getHitList().size() > 0);

        final long stoptime = System.currentTimeMillis();
        final long difftime = stoptime - starttime;

        analyzeresult.setAnalysisStartTime(starttime);
        analyzeresult.setAnalysisStopTime(stoptime);

        final ProcessStep processStep = new ProcessStep();
        processStep.setRemark("Check");
        analyzeresult.addProcessStep(processStep);

        if (isHit) {
            if (logger.isInfoEnabled()) {
                logger.info(" message has hits: " + message.getUUID());
            }

            getHitQueue().addMessage(analyzeresult);
        }
        else {
            if (logger.isInfoEnabled()) {
                logger.info(" message has NO hits: " + message.getUUID());
            }

            getNoHitQueue().addMessage(analyzeresult);
        }

        if (logger.isInfoEnabled()) {
            logger.info("stop checking message: " + message.getUUID() + " (Time needed :  " + difftime + " ms)");
        }
    }

    private HitRate checkISO9362(final String msgFieldText) {

        // TODO: hardcoded ISO !!!

        try {
            final String countryISO = msgFieldText.substring(4, 6);
            return checkISO4NCCT(countryISO);
        }
        catch (final Exception x) {
            // intentional shit !!!!!!!!!!
            return null;
        }
    }

    protected HitRate checkISO4NCCT(final String mightBecountry) {
        HitRate hitRate = null;

        final String countryISO = mightBecountry;
        final String countryLong = getStreamManager().getReferenceListHandlers().get(NCCTListHandler.getInstance().getListName()).getValues().getProperty(countryISO);

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

            final String countryISO = msgFieldText.substring(0, 2);

            hitRate = checkISO4NCCT(countryISO);
        }

        return hitRate;
    }

    protected HitRate checkFieldSpecific(final String msgFieldName, final String msgFieldText) {

        return null;  // this is a plain dummy - override if needed!!
    }

    private void addHitRateResultNCCT(final HitRate hitRate, final AnalysisResult analyzeresult, final String msgFieldName) {
        final SanctionHitResult hr = new SanctionHitResult();

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

    protected boolean isFieldToCheck(final String msgFieldName, final String searchIndex, final String watchListname, final String messageType) {
        final boolean checkit = (getStreamManager().isFieldToCheck(msgFieldName, searchIndex, watchListname, messageType));

        return checkit;
    }

    protected boolean isFieldToCheckFuzzy(final String msgFieldName, final SanctionListHandler listhandler, final String messageType) {
        // final boolean fuzzy = listhandler.isFuzzySearch() && getStreamManager().isField2Fuzzy(msgFieldName);
        final boolean fuzzy = getStreamManager().isField2Fuzzy(msgFieldName, messageType);

        return fuzzy;
    }

    private void genericListCheck(final SanctionListHandler listhandler, final AnalysisResult analyzeresult) {

        final MessageContent messageContent = getFieldsToCheck(analyzeresult.getMessage());

        for (final String searchIndexName : listhandler.getSearchLists().keySet()) {

            logger.info("SearchList: " + searchIndexName);

            final List<SL_Entry> searchList = listhandler.getSearchLists().get(searchIndexName);

            // if ((listhandler.getEntityList() != null) && !listhandler.getEntityList().isEmpty()) {

            // for (final WL_Entity entity : listhandler.getEntityList()) {

            // iterate over all entities / addresses /
            // for (final WL_Name name : entity.getNames())
            for (final SL_Entry entry : searchList) {

                List<String> searchTokens = entry.getTokenizedString(listhandler.getListName());

                if (searchTokens == null) { // if not already cached
                    searchTokens = TokenTool.getTokenList(entry.getSearchValue(), listhandler.getDelimiters(), listhandler.getDeadCharacters(),
                            getStreamManager().getMinTokenLen(listhandler.getListName()), getStreamManager().getIndexAusschlussList().getValues(), true);

                    entry.setTokenizedString(listhandler.getListName(), searchTokens);
                }
                else {
                    // if (logger.isDebugEnabled()) logger.debug("---optimized tokenizing 1 !! ");
                }

                // iterate over all Msg fields
                for (final String msgFieldName : messageContent.getFieldsAndValues().keySet()) {

                    // should field be checked ?
                    if (!isFieldToCheck(msgFieldName, searchIndexName, listhandler.getListName(), messageContent.getMessageType())) {
                        if (logger.isDebugEnabled()) {
                            if (!msgFieldName.equalsIgnoreCase("XX")) {
                                logger.debug("SKIPPING field: " + messageContent.getMessageType() + " / " + msgFieldName + "  for " + searchIndexName);
                            }
                        }
                        continue;
                    }

                    float totalHitRateRelative = 0;
                    int totalHitRateAbsolute = 0;
                    int totalHitRatePhrase = 0;

                    final String msgFieldText = messageContent.getFieldsAndValues().get(msgFieldName);

                    List<String> msgFieldTokens = messageContent.getTokenizedField(msgFieldName);
                    if (msgFieldTokens == null) {
                        String longText = null;

                        // check if BIC expansion should be done
                        if (getStreamManager().isField2BICTranslate(msgFieldName, messageContent.getMessageType())) {
                            longText = BICHelper.extendBIC(msgFieldText);

                            if (firstIteration) {// Country check on BIC - ONLY ONCE!!
                                // String countryISO = msgFieldText.substring(4, 6); // TODO: hardcoded ISO !!!
                                // String countryLong = getStreamManager().getReferenceListHandlers().get(NCCTListHandler.getInstance().getListName()).getValues().getProperty(countryISO);
                                final HitRate hitRate = checkISO9362(msgFieldText);
                                if (hitRate != null) {
                                    // we found an ISO in NCCT
                                    addHitRateResultNCCT(hitRate, analyzeresult, msgFieldName);
                                }
                            }
                        }

                        // userxit for each field 1.time
                        if (firstIteration) {
                            final HitRate hitRate = checkFieldSpecific(msgFieldName, msgFieldText);

                            // this is only half baked so far !!!!!

                            if (hitRate != null) {
                                // (we found an ISO in NCCT)
                                // or another user defined hit
                                addHitRateResultNCCT(hitRate, analyzeresult, msgFieldName);
                            }
                        }

                        msgFieldTokens = TokenTool.getTokenList(longText != null ? msgFieldText + " " + longText : msgFieldText, listhandler.getDelimiters(), listhandler.getDeadCharacters(),
                                getStreamManager().getMinTokenLen(listhandler.getListName()), getStreamManager().getStopwordList().getValues(), false);

                        messageContent.setTokenizedField(msgFieldName, msgFieldTokens);
                    }
                    else {
                        // if (logger.isDebugEnabled()) logger.debug("---optimized tokenizing 2 !! ");
                    }

                    // we count different tokenhits to calc relweight
                    final int diffNameTokenHits[] = new int[searchTokens.size()];

                    for (final String msgFieldToken : msgFieldTokens) {

                        int ixNameTokenHit = 0;
                        for (final String nameToken : searchTokens) {

                            // check if this combination is not on the NoList list declared as not to check
                            final boolean isDeclaredAsNoHit = checkIfDeclaredAsNoHit(msgFieldToken, nameToken);

                            if (isDeclaredAsNoHit) {
                                if (logger.isDebugEnabled()) {
                                    logger.debug("DECLARED AS NOT HIT : " + msgFieldText + ": " + nameToken);
                                }
                            }
                            else {

                                // >>>>> THE COMPARISION <<<<<

                                final float hitValue = TokenTool.compareCheck(nameToken, msgFieldToken, isFieldToCheckFuzzy(msgFieldName, listhandler, messageContent.getMessageType()),
                                        getStreamManager().getMinTokenLen(listhandler.getListName()), getStreamManager().getFuzzyValue(listhandler.getListName()));

                                System.out.println(nameToken + " -> " + msgFieldToken + " : " + hitValue);

                                // single fuzzy limit !!
                                if ((hitValue > 0) && (hitValue > getStreamManager().getMinRelVal(listhandler.getListName()))) {
                                    totalHitRateRelative += hitValue;

                                    final SanctionHitInfo swhi = new SanctionHitInfo(listhandler.getListName(), entry.getReferencedEntity().getWL_Id(), nameToken, msgFieldName, msgFieldToken,
                                            (int) hitValue);

                                    // single word hit list if not already in
                                    // add tokens also if not "real hits" ???
                                    if (!analyzeresult.getHitTokensList().contains(swhi)) {
                                        analyzeresult.getHitTokensList().add(swhi);
                                    }
                                    diffNameTokenHits[ixNameTokenHit]++;

                                }
                                if (hitValue == 100) {
                                    totalHitRateAbsolute += hitValue;
                                    // hitValue = TokenTool.compareCheck(nameToken, msgFieldToken, false, getStreamManager().getMinTokenLen());
                                    // totalHitRateAbsolute += hitValue;
                                    // if (logger.isDebugEnabled()) logger.debug("compare : " + nameToken + " <-> " + msgFieldToken + " (" + hitValue + ")");
                                }
                            }
                            ixNameTokenHit++;
                        }
                    }

                    // 1. sum (hit percent of single tokens) / # of tokens
                    // 2. # of hits / # tokens
                    // 3. # of contained tokens / # tokens

                    boolean addPhrase = false;
                    boolean addAbs = false;
                    boolean addRel = false;

                    // kleineres in größeres wosnsunst...
                    final int minTokens = Math.min(msgFieldTokens.size(), searchTokens.size());

                    if (minTokens > 0) {

                        // hits for one field
                        // TODO: this is a dummy implementation
                        if ((totalHitRateRelative / minTokens) > getStreamManager().getMinRelVal(listhandler.getListName())) {

                            if (logger.isDebugEnabled()) {
                                final String keys = TokenTool.buildTokenString(searchTokens, " ");

                                logger.debug(msgFieldName + ": RELATIVE SUM: " + msgFieldText + ": " + keys + " --> " + totalHitRateRelative);
                            }

                            addRel = true;

                        }

                        if ((totalHitRateAbsolute / minTokens) > getStreamManager().getMinAbsVal(listhandler.getListName())) {

                            if (logger.isDebugEnabled()) {
                                final String keys = TokenTool.buildTokenString(searchTokens, " ");

                                logger.debug(msgFieldName + ": ABSOLUTE SUM: " + msgFieldText + ": " + keys + " --> " + totalHitRateAbsolute);
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

                        totalHitRatePhrase = 100 * minTokens;
                        if (minTokens > 1) {
                            // eval simple text in text search for
                            // phrase
                            // check
                            // but only if both sides contain more than one token
                            final boolean contains = TokenTool.checkContains(msgFieldTokens, searchTokens, " ");  // TODO: this is no goood sol in gen

                            if (contains) {

                                if ((totalHitRatePhrase / minTokens) > getStreamManager().getMinAbsVal(listhandler.getListName())) {

                                    logger.debug(msgFieldName + ": PHRASECHECK: " + msgFieldText + ": " + entry.getSearchValue() + " --> " + contains);

                                    addPhrase = true;
                                }
                            }
                            else {
                                totalHitRatePhrase = 0;
                            }
                        }
                        else {
                            // no Phrase hit
                        }

                        // add cumulated hit
                        if ((addAbs) || (addPhrase) || (addRel)) {

                            // remove single word hits which are not relevant

                            final ValueListHandler notSingleWordListHandler = getStreamManager().getNotSingleWordHitList();
                            int nrOfNotSowords = 0;
                            if (notSingleWordListHandler != null) {
                                nrOfNotSowords = TokenTool.compareTokenLists(notSingleWordListHandler.getValues(), searchTokens);
                            }

                            // TODO: "not single Hit Words" should explicitly be searched and counted !!!!!!!!!

                            if ((totalHitRateAbsolute > (nrOfNotSowords * 100)) || (totalHitRateRelative > (nrOfNotSowords * 100)) || (totalHitRatePhrase > (nrOfNotSowords * 100))) {

                                final SanctionHitResult hr = new SanctionHitResult();

                                boolean addHit = true;

                                // check optimizer if we have to cleanup some mess ;-)
                                if (getStreamManager().getTxNoHitOptimizationListHandler() != null) {
                                    for (final OptimizationRecord optimizationRecord : getStreamManager().getTxNoHitOptimizationListHandler().getValues()) {
                                        if (optimizationRecord.getWatchListName().equals(listhandler.getListName())
                                                && optimizationRecord.getWatchListId().equals(entry.getReferencedEntity().getWL_Id()) && optimizationRecord.getFieldName().equals(msgFieldName)
                                                && optimizationRecord.getToken().equals(msgFieldText))

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

                                    int relWeight = 0;
                                    for (int i = 0; i < diffNameTokenHits.length; i++) {
                                        if (diffNameTokenHits[i] != 0) {
                                            relWeight++;
                                        }
                                    }

                                    hr.setHitField(msgFieldName);
                                    hr.setHitDescripton(entry.getSearchValue());
                                    hr.setHitListName(listhandler.getListName());
                                    hr.setHitListPriority(listhandler.getOrderNr());
                                    hr.setHitId(entry.getReferencedEntity().getWL_Id());
                                    hr.setHitLegalBasis(entry.getReferencedEntity().getLegalBasis());
                                    hr.setHitExternalUrl(entry.getReferencedEntity().getInformationUrl());
                                    hr.setHitType(listhandler.getType());
                                    hr.setEntityType(entry.getReferencedEntity().getEntityType().getText());
                                    hr.setHitRemark(entry.getReferencedEntity().getComment());

                                    hr.setAbsolutHit(totalHitRateAbsolute / minTokens);
                                    hr.setRelativeHit((relWeight * 100) / minTokens);
                                    hr.setPhraseHit(totalHitRatePhrase / minTokens);

                                    analyzeresult.addHitResult(hr);
                                }
                                else {
                                    if (logger.isDebugEnabled()) {
                                        logger.debug(msgFieldName + ": Optimizer: " + msgFieldText + " -> " + entry.getSearchValue() + " : hit removed due Confirmed as NOT NEEDED!");
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

        // cleanup single hits -- this should be optimized !!

        for (final Iterator<WordHitInfo> iteratorWordHits = analyzeresult.getHitTokensList().listIterator(); iteratorWordHits.hasNext();) {
            final WordHitInfo wordHit = iteratorWordHits.next();

            boolean found = false;
            for (final HitResult hitResult : analyzeresult.getHitList()) {
                if ((((SanctionHitResult) hitResult).getHitId() == ((SanctionHitInfo) wordHit).getSanctionListId())
                        && (((SanctionHitResult) hitResult).getHitField() == ((SanctionHitInfo) wordHit).getFieldName()))  // only remove if Id AND FieldID is identical ( LISTNAME should be
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
