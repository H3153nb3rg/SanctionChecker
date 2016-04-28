package at.jps.sl.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.TableModel;

import at.jps.sanction.core.listhandler.valuelist.IAListHandler;
import at.jps.sanction.core.listhandler.valuelist.NSWHListHandler;
import at.jps.sanction.core.listhandler.valuelist.SWListHandler;
import at.jps.sanction.domain.SanctionHitResult;
import at.jps.sanction.domain.payment.PaymentListConfigHolder;
import at.jps.sanction.domain.payment.sepa.SepaMessage;
import at.jps.sanction.domain.payment.swift.SwiftMessage;
import at.jps.sanction.domain.person.PersonMessage;
import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.HitResult;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.OptimizationRecord;
import at.jps.sanction.model.WatchListInformant;
import at.jps.sanction.model.listhandler.NoWordHitListHandler;
import at.jps.sanction.model.listhandler.ReferenceListHandler;
import at.jps.sanction.model.listhandler.SanctionListHandler;
import at.jps.sanction.model.listhandler.ValueListHandler;
import at.jps.sanction.model.wl.entities.WL_Entity;
import at.jps.sl.gui.core.SearchResultRecord;
import at.jps.sl.gui.model.sanction.SanctionTableModelHandler;
import at.jps.sl.gui.model.watchlist.SearchTableModelHandler;
import at.jps.sl.gui.util.GUIConfigHolder;
import at.jps.sl.gui.util.RingBufferQueue;
import at.jps.sl.gui.util.RingBufferQueueImpl;
import at.jps.sl.gui.util.TokenUpdater;

public class AdapterHelper implements WatchListInformant {

    private GUIConfigHolder                  config;

    private HashMap<String, RingBufferQueue> hitBuffers;
    private HashMap<String, RingBufferQueue> noHitBuffers;

    private AnalysisResult                   currentMessage;

    private HashMap<Integer, String>         hitTypeRows;
    // maps
    // from
    // rowid
    // (hit)
    // to
    // List/Entity
    // Type
    private HitResult                        focussedHitResult;

    private HashMap<Integer, String>         hitRowFields;
    // maps
    // from
    // rowid
    // to
    // fieldname
    // (
    // TX
    // side)
    private HashMap<Integer, String>         tokenRowFields;
    // maps
    // from
    // rowid
    // to
    // fieldname
    // (
    // Token
    // side)
    private HashMap<String, Integer>         hitFieldRows;
    // maps
    // from
    // fieldname
    // to
    // rowid
    // (
    // TX
    // side)
    private HashMap<Integer, String>         resultRowFields;
    // maps
    // side

    public RingBufferQueue getNoHitBuffer() {
        if (noHitBuffers == null) {

            noHitBuffers = new HashMap<>();
        }

        RingBufferQueue noHitBuffer = noHitBuffers.get(getActiveStreamName());

        if (noHitBuffer == null) {
            noHitBuffer = new RingBufferQueueImpl();
            noHitBuffer.setQueue(config.getQueue(GUIConfigHolder.QUEUE_NAME_NOHITS));
            noHitBuffers.put(getActiveStreamName(), noHitBuffer);
        }

        return noHitBuffer;
    }

    public RingBufferQueue getHitBuffer() {

        if (hitBuffers == null) {

            hitBuffers = new HashMap<>();
        }

        RingBufferQueue hitBuffer = hitBuffers.get(getActiveStreamName());

        if (hitBuffer == null) {
            hitBuffer = new RingBufferQueueImpl();
            hitBuffer.setQueue(config.getQueue(GUIConfigHolder.QUEUE_NAME_HITS));
            hitBuffers.put(getActiveStreamName(), hitBuffer);
        }

        return hitBuffer;
    }

    public void addToFinalHitQueue(final AnalysisResult analysisResult) {

        config.getQueue(GUIConfigHolder.QUEUE_NAME_FINALHIT).addMessage(analysisResult);
    }

    public void addToFinalNoHitQueue(final AnalysisResult analysisResult) {

        config.getQueue(GUIConfigHolder.QUEUE_NAME_FINALNOHIT).addMessage(analysisResult);
    }

    public void addToPostProcessHitQueue(final AnalysisResult analysisResult) {

        config.getQueue(GUIConfigHolder.QUEUE_NAME_POSTHIT).addMessage(analysisResult);
    }

    public void addToPostProcessNoHitQueue(final AnalysisResult analysisResult) {

        config.getQueue(GUIConfigHolder.QUEUE_NAME_POSTNOHIT).addMessage(analysisResult);
    }

    public void addToBacklogQueue(final AnalysisResult analysisResult) {

        config.getQueue(GUIConfigHolder.QUEUE_NAME_BACKLOG).addMessage(analysisResult);
    }

    public TableModel getAnalysisResultTableModel(final AnalysisResult analysisResult) {

        // implemented for swiftMessages only
        // TODO: generic
        SanctionTableModelHandler.SanctionAnalysisResultTableModel tm = null;
        if (analysisResult instanceof AnalysisResult) {
            tm = SanctionTableModelHandler.generateSwiftAnalysisResultTableModel(analysisResult);
            resultRowFields = tm.getHitRowFieldList();
        }

        return tm;
    }

    public TableModel getAnalysisWordListTableModel(final AnalysisResult analysisResult) {

        // implemented for swiftMessages only
        // TODO: generic
        SanctionTableModelHandler.SanctionAnalysisResultTableModel tm = null;
        if (analysisResult instanceof AnalysisResult) {
            tm = SanctionTableModelHandler.generateSwiftAnalysisWordListTableModel(analysisResult);

            tokenRowFields = tm.getHitRowFieldList();
        }

        return tm;
    }

    public TableModel getMessageTableModel(final Message message) {

        // TODO: make generic

        SanctionTableModelHandler.SanctionTableModel tm = null;
        if ((message instanceof SwiftMessage) || (message instanceof SepaMessage) || (message instanceof PersonMessage)) {  // TODO: this should be factory based
            tm = SanctionTableModelHandler.generateSanctionMessageTableModel(message, getFields2Check(), getFields2BIC(), config.getFieldNames());

            // mapping lists

            hitTypeRows = tm.getHitTypeRowList(currentMessage);
            hitRowFields = tm.getHitRowFieldList(currentMessage);
            hitFieldRows = tm.getHitFieldRowList(currentMessage);
        }
        else {
            System.err.println("no propriate Message display class implemented !!");
        }

        return tm;

    }

    public TableModel getEntityRelationsTableModel(final SanctionHitResult slhr) {

        final String listname = slhr.getHitListName();
        final WL_Entity focusedEntity = getSanctionListEntityDetails(slhr.getHitListName(), slhr.getHitId());

        final TableModel tm = SanctionTableModelHandler.getEntityRelationsTableModel(this, listname, focusedEntity);

        return tm;
    }

    public TableModel getEntityRelationsTableModel(final String listname, final WL_Entity entity) {

        final WL_Entity focusedEntity = getSanctionListEntityDetails(listname, entity.getWL_Id());

        final TableModel tm = SanctionTableModelHandler.getEntityRelationsTableModel(this, listname, focusedEntity);

        return tm;
    }

    public TableModel getEntityDetailsNamesTableModel(final WL_Entity entity) {

        final TableModel tm = SanctionTableModelHandler.getEntityNameTableModel(entity);

        return tm;
    }

    public TableModel getValueListTableModel(final ValueListHandler valListhandler) {

        final TableModel tm = SearchTableModelHandler.generateValueListTableModel(valListhandler);

        return tm;

    }

    public TableModel getReferenceListTableModel(final ReferenceListHandler refListhandler) {

        final TableModel tm = SearchTableModelHandler.generateReferenceListTableModel(refListhandler);

        return tm;
    }

    public TableModel getWatchListTableModel(final List<SearchResultRecord> resultSet) {

        final TableModel tm = SearchTableModelHandler.generateWatchListTableModel(resultSet);

        return tm;
    }

    public TableModel getNoWordHitListTableModel(final NoWordHitListHandler valListhandler) {

        final TableModel tm = SearchTableModelHandler.generateNoHitListTableModel(valListhandler);

        return tm;
    }

    public TableModel getOptimizationListTableModel(final List<OptimizationRecord> resultSet) {

        final TableModel tm = SearchTableModelHandler.generateOptiListTableModel(resultSet);

        return tm;
    }

    public String getActiveStreamName() {
        return config.getActiveStream();
    }

    public void setActiveStreamName(final String streamName) {
        config.setActiveStream(streamName);
    }

    public void initialize(final GUIConfigHolder config) {

        this.config = config;

        // final BicListHandler bicList = (BicListHandler) getReferenceListByName(BicListHandler.LISTNAME);
    }

    public AnalysisResult getCurrentMessage() {
        return currentMessage;
    }

    public void setCurrentMessage(final AnalysisResult analysisResult) {
        currentMessage = analysisResult;
    }

    public HashMap<Integer, String> getHitTypeRows() {
        return hitTypeRows;
    }

    public HitResult getFocussedHitResult() {
        return focussedHitResult;
    }

    public void setFocussedHitResult(final HitResult hitResult) {
        focussedHitResult = hitResult;
    }

    public HashMap<Integer, String> getHitRowFields() {
        return hitRowFields;
    }

    public HashMap<Integer, String> getTokenRowFields() {
        return tokenRowFields;
    }

    public HashMap<String, Integer> getHitFieldRows() {
        return hitFieldRows;
    }

    public HashMap<Integer, String> getResultRowFields() {
        return resultRowFields;
    }

    public ArrayList<String> getFields2Check() {
        return config.getStreamConfig(config.getActiveStream()).getFields2Check();
    }

    public ArrayList<String> getFields2BIC() {
        return config.getStreamConfig(config.getActiveStream()).getFields2BIC();
    }

    public HashMap<String, String> getFieldColorsAsHex() {
        return config.getFieldColorsAsHex();
    }

    public HashMap<String, Color> getFieldColors() {
        return config.getFieldColors();
    }

    public void addStopWord(final String token) {
        // TODO: just for demo purpose
        TokenUpdater.addStopWord(token, getValueListByName(SWListHandler.LISTNAME).getFilename());
    }

    public void addNSWH(final String token) {
        // TODO: just for demo purpose
        TokenUpdater.addStopNSWH(token, getValueListByName(NSWHListHandler.LISTNAME).getFilename());
    }

    public void addIA(final String token) {
        // TODO: just for demo purpose
        TokenUpdater.addStopIA(token, getValueListByName(IAListHandler.LISTNAME).getFilename());
    }

    public void addNoHit(final String fieldToken, final String listToken) {
        // TODO: just for demo purpose
        TokenUpdater.addNoHitInfo(fieldToken, listToken, ((PaymentListConfigHolder) config.getStreamConfig(config.getActiveStream())).getNoWordHitListHandler().getFilename());  // TODO: UGLY!!
    }

    @Override
    public WL_Entity getSanctionListEntityDetails(final String listname, final String id) {
        WL_Entity entity = null;

        final SanctionListHandler sanctionListHandler = getWatchListByName(listname);

        if (sanctionListHandler != null) {
            entity = sanctionListHandler.getEntityById(id);
        }
        return entity;
    }

    @Override
    public SanctionListHandler getWatchListByName(final String listname) {
        return config.getWatchListByName(listname);
    }

    @Override
    public ReferenceListHandler getReferenceListByName(final String name) {
        final ReferenceListHandler list = config.getReferenceListByName(name);
        return list;
    }

    @Override
    public ValueListHandler getValueListByName(final String name) {
        final ValueListHandler list = config.getValueListByName(name);
        return list;
    }

    @Override
    public String getSanctionListDescription(final String listname) {

        final SanctionListHandler sanctionListHandler = getWatchListByName(listname);

        return (sanctionListHandler != null ? sanctionListHandler.getListDescription() : "unknown");
    }

    public GUIConfigHolder getConfig() {
        return config;
    }

    public void setConfig(final GUIConfigHolder config) {
        this.config = config;
    }

}
