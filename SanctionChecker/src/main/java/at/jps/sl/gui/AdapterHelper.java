package at.jps.sl.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.TableModel;

import at.jps.sanction.core.listhandler.valuelist.IAListHandler;
import at.jps.sanction.core.listhandler.valuelist.NSWHListHandler;
import at.jps.sanction.core.listhandler.valuelist.SWListHandler;
import at.jps.sanction.domain.payment.PaymentHitResult;
import at.jps.sanction.domain.payment.sepa.SepaMessage;
import at.jps.sanction.domain.payment.swift.SwiftMessage;
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

    static final String              PROP_FILENAME = "checker.properties";
    public static final String       PROP_GUI_MAIN = "GUI.Stream.Name";

    private GUIConfigHolder          config;

    private RingBufferQueue          hitBuffer;
    private RingBufferQueue          noHitBuffer;

    private AnalysisResult           currentMessage;
    private HashMap<Integer, String> hitTypeRows;
    // maps
    // from
    // rowid
    // (hit)
    // to
    // List/Entity
    // Type
    private HitResult                focussedHitResult;

    private HashMap<Integer, String> hitRowFields;
    // maps
    // from
    // rowid
    // to
    // fieldname
    // (
    // TX
    // side)
    private HashMap<Integer, String> tokenRowFields;
    // maps
    // from
    // rowid
    // to
    // fieldname
    // (
    // Token
    // side)
    private HashMap<String, Integer> hitFieldRows;
    // maps
    // from
    // fieldname
    // to
    // rowid
    // (
    // TX
    // side)
    private HashMap<Integer, String> resultRowFields;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   // maps
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        // result
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        // side
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        // !!)
    private String                   streamName;

    // public void initializeFields2BIC() {
    // final String allFields = getProperties().getProperty(getStreamName() + ".BICFilds");
    //
    // if (allFields != null) {
    // fields2BIC = new ArrayList<String>();
    //
    // final StringTokenizer tokenizer = new StringTokenizer(allFields, ",");
    //
    // while (tokenizer.hasMoreTokens()) {
    // fields2BIC.add(tokenizer.nextToken());
    // }
    // }
    // }
    //
    // public void initializeFields2Check() {
    // final String allFields = properties.getProperty(getStreamName() + ".checkFields");
    //
    // if (allFields != null) {
    // fields2Check = new ArrayList<String>();
    //
    // final StringTokenizer tokenizer = new StringTokenizer(allFields, ",");
    //
    // while (tokenizer.hasMoreTokens()) {
    // fields2Check.add(tokenizer.nextToken());
    // }
    // }
    // }

    public RingBufferQueue getNoHitBuffer() {
        if (noHitBuffer == null) {
            noHitBuffer = new RingBufferQueueImpl();

            noHitBuffer.setQueue(config.getNoHitQueue());
        }
        return noHitBuffer;
    }

    public RingBufferQueue getHitBuffer() {
        if (hitBuffer == null) {
            hitBuffer = new RingBufferQueueImpl();

            hitBuffer.setQueue(config.getHitQueue());
        }
        return hitBuffer;
    }

    public void addToFinalHitQueue(final AnalysisResult analysisResult) {

        config.getFinalHitQueue().addMessage(analysisResult);
    }

    public void addToFinalNoHitQueue(final AnalysisResult analysisResult) {

        config.getFinalNoHitQueue().addMessage(analysisResult);
    }

    public void addToPostProcessHitQueue(final AnalysisResult analysisResult) {

        config.getPostProcessHitQueue().addMessage(analysisResult);
    }

    public void addToPostProcessNoHitQueue(final AnalysisResult analysisResult) {

        config.getPostProcessNoHitQueue().addMessage(analysisResult);
    }

    public void addToBacklogQueue(final AnalysisResult analysisResult) {

        config.getBacklogQueue().addMessage(analysisResult);
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
        if ((message instanceof SwiftMessage) || (message instanceof SepaMessage)) {  // TODO: this should be factory based
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

    public TableModel getEntityRelationsTableModel(final PaymentHitResult slhr) {

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

    public TableModel getNoWordHitListTableModel(NoWordHitListHandler valListhandler) {

        final TableModel tm = SearchTableModelHandler.generateNoHitListTableModel(valListhandler);

        return tm;

    }

    public TableModel getOptimizationListTableModel(List<OptimizationRecord> resultSet) {

        final TableModel tm = SearchTableModelHandler.generateOptiListTableModel(resultSet);

        return tm;

    }

    public String getStreamName() {
        return streamName;
    }

    public void initialize(GUIConfigHolder config) {

        this.config = config;

        // final BicListHandler bicList = (BicListHandler) getReferenceListByName(BicListHandler.LISTNAME);

    }

    public AnalysisResult getCurrentMessage() {
        return currentMessage;
    }

    public void setCurrentMessage(AnalysisResult analysisResult) {
        currentMessage = analysisResult;
    }

    public HashMap<Integer, String> getHitTypeRows() {
        return hitTypeRows;
    }

    public HitResult getFocussedHitResult() {
        return focussedHitResult;
    }

    public void setFocussedHitResult(HitResult hitResult) {
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
        return config.getStreamConfig().getFields2Check();
    }

    public ArrayList<String> getFields2BIC() {
        return config.getStreamConfig().getFields2BIC();
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
        TokenUpdater.addNoHitInfo(fieldToken, listToken, config.getNoWordHitListHandler().getFilename());
    }

    @Override
    public WL_Entity getSanctionListEntityDetails(final String listname, final String id) {
        WL_Entity entity = null;

        final SanctionListHandler sanctionListHandler = getWatchListByName(listname);

        if (sanctionListHandler != null) {
            // for (WL_Entity listEntity : sanctionListHandler.getEntityList()) {
            // if (listEntity.getWL_Id().equals(id)) {
            // entity = listEntity;
            // break;
            // }
            // }
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

    public void setConfig(GUIConfigHolder config) {
        this.config = config;
    }

}
