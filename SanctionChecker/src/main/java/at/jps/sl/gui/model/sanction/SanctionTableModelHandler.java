package at.jps.sl.gui.model.sanction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import at.jps.sanction.core.util.BICHelper;
import at.jps.sanction.domain.SanctionHitInfo;
import at.jps.sanction.domain.SanctionHitResult;
import at.jps.sanction.domain.payment.sepa.SepaAnalyzer;
import at.jps.sanction.domain.payment.sepa.SepaMessage;
import at.jps.sanction.domain.payment.swift.SwiftAnalyzer;
import at.jps.sanction.domain.payment.swift.SwiftMessage;
import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.HitResult;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.MessageContent;
import at.jps.sanction.model.WatchListInformant;
import at.jps.sanction.model.WordHitInfo;
import at.jps.sanction.model.wl.entities.WL_Entity;
import at.jps.sanction.model.wl.entities.WL_Name;

public class SanctionTableModelHandler implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1980383744603760444L;

    public interface SanctionAnalysisResultTableModel extends TableModel {
        public void setAnalysisResult(AnalysisResult analysisResult);

        public HashMap<Integer, String> getHitRowFieldList();

    }

    // implemented for swiftMessages only
    // TODO: generic

    public interface SanctionTableModel extends TableModel {

        public HashMap<Integer, String> getHitRowFieldList(AnalysisResult result);

        public HashMap<String, Integer> getHitFieldRowList(AnalysisResult result);

        public HashMap<Integer, String> getHitTypeRowList(AnalysisResult result);

        void setFieldsList(List<String> fields2Check);

        public void setMessage(Message message);

        public void setFieldnameList(HashMap<String, String> fieldNames);
    }

    public final static String FIELD_TYPE_UNUSED = "UNUSED";

    public static SanctionAnalysisResultTableModel generateSwiftAnalysisResultTableModel(final AnalysisResult analysisResult) {

        final SanctionAnalysisResultTableModel tm = new SanctionAnalysisResultTableModel() {

            AnalysisResult           analysisResult;

            HashMap<Integer, String> rowFieldlist;

            @Override
            public void addTableModelListener(final TableModelListener l) {
                // super.addTableModelListener(l);

            }

            @Override
            public Class<?> getColumnClass(final int columnIndex) {
                return String.class;
            }

            @Override
            public int getColumnCount() {
                return 6;
            }

            @Override
            public String getColumnName(final int columnIndex) {
                String header;
                switch (columnIndex) {

                    case 0:
                        header = "Watchlist";
                        break;
                    case 1:
                        header = "Id";
                        break;
                    case 2:
                        header = "Field";
                        break;
                    case 3:
                        header = "Value";
                        break;
                    case 4:
                        header = "!";
                        break;
                    case 5:
                        header = "Listentry";
                        break;
                    default:
                        header = "nix";
                }
                return header;
            }

            @Override
            public int getRowCount() {
                return analysisResult.getHitList().size();
            }

            @Override
            public Object getValueAt(final int rowIndex, final int columnIndex) {

                String value = " ";

                final HitResult hitResult = analysisResult.getHitList().get((analysisResult.getHitList().size() - 1) - rowIndex);

                switch (columnIndex) {

                    case 0:
                        if (hitResult instanceof SanctionHitResult) {
                            value = ((SanctionHitResult) hitResult).getHitListName();
                        }
                        else {
                            value = "No List";
                        }
                        break;
                    case 1:
                        if (hitResult instanceof SanctionHitResult) {
                            value = ((SanctionHitResult) hitResult).getHitId();
                        }
                        else {
                            value = "No Id";
                        }
                        break;
                    case 2:
                        value = hitResult.getHitField();
                        break;
                    case 3:
                        value = String.format("%03d", hitResult.getRelativeHit()) + " | " + String.format("%03d", hitResult.getAbsolutHit()) + " | " + String.format("%03d", hitResult.getPhraseHit());
                        break;
                    case 4:
                        if (hitResult instanceof SanctionHitResult) {
                            value = ((SanctionHitResult) hitResult).getHitOptimized();
                        }
                        else {
                            value = " ";
                        }
                        break;
                    case 5:
                        value = hitResult.getHitDescripton();
                        break;
                    default:
                        value = "nix";
                }

                return value;
            }

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return false;
            }

            @Override
            public void removeTableModelListener(final TableModelListener l) {

            }

            @Override
            public void setAnalysisResult(final AnalysisResult analysisResult) {

                this.analysisResult = analysisResult;

                Collections.sort(analysisResult.getHitList());
            }

            @Override
            public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {

            }

            @Override
            public HashMap<Integer, String> getHitRowFieldList() {

                if (rowFieldlist == null) {
                    rowFieldlist = new HashMap<Integer, String>(analysisResult.getHitList().size());
                    int i = analysisResult.getHitList().size() - 1;
                    for (final HitResult hitResult : analysisResult.getHitList()) {

                        rowFieldlist.put(new Integer(i), hitResult.getHitField());
                        i--;
                    }

                }
                return rowFieldlist;
            }

        };

        tm.setAnalysisResult(analysisResult);

        return tm;
    }

    public static SanctionAnalysisResultTableModel generateSwiftAnalysisWordListTableModel(final AnalysisResult analysisResult) {

        final SanctionAnalysisResultTableModel tm = new SanctionAnalysisResultTableModel() {

            AnalysisResult           analysisResult;

            HashMap<Integer, String> rowFieldlist;

            @Override
            public void addTableModelListener(final TableModelListener l) {
                // super.addTableModelListener(l);

            }

            @Override
            public Class<?> getColumnClass(final int columnIndex) {
                return String.class;
            }

            @Override
            public int getColumnCount() {
                return 6;
            }

            @Override
            public String getColumnName(final int columnIndex) {
                String header;
                switch (columnIndex) {

                    case 0:
                        header = "Watchlist";
                        break;
                    case 1:
                        header = "Id";
                        break;
                    case 2:
                        header = "Listentry";
                        break;
                    case 3:
                        header = "Field";
                        break;
                    case 4:
                        header = "Content";
                        break;
                    case 5:
                        header = "Value";
                        break;
                    default:
                        header = "nix";
                }
                return header;
            }

            @Override
            public int getRowCount() {
                return analysisResult.getHitTokensList().size();
            }

            @Override
            public Object getValueAt(final int rowIndex, final int columnIndex) {

                String value = " ";

                final WordHitInfo wordHitInfo = analysisResult.getHitTokensList().get(rowIndex);

                switch (columnIndex) {

                    case 0:

                        if (wordHitInfo instanceof SanctionHitInfo) {
                            value = ((SanctionHitInfo) wordHitInfo).getSanctionListName();
                        }
                        else {
                            value = "No List";
                        }
                        break;
                    case 1:
                        if (wordHitInfo instanceof SanctionHitInfo) {
                            value = ((SanctionHitInfo) wordHitInfo).getSanctionListId();
                        }
                        else {
                            value = "No Id";
                        }
                        break;
                    case 2:
                        value = wordHitInfo.getToken();
                        break;
                    case 3:
                        value = wordHitInfo.getFieldName();
                        break;
                    case 4:
                        value = wordHitInfo.getFieldText();
                        break;
                    case 5:
                        value = String.format("%03d", wordHitInfo.getValue());
                        break;

                    default:
                        value = "nix";
                }

                return value;
            }

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return false;
            }

            @Override
            public void removeTableModelListener(final TableModelListener l) {

            }

            @Override
            public void setAnalysisResult(final AnalysisResult analysisResult) {

                this.analysisResult = analysisResult;

                // Collections.sort(analysisResult.getHitList());
            }

            @Override
            public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {

            }

            @Override
            public HashMap<Integer, String> getHitRowFieldList() {

                if (rowFieldlist == null) {
                    rowFieldlist = new HashMap<Integer, String>(analysisResult.getHitTokensList().size());
                    int i = 0;
                    for (final WordHitInfo wordHitInfo : analysisResult.getHitTokensList()) {

                        rowFieldlist.put(new Integer(i), wordHitInfo.getFieldName());
                        i++;
                    }

                }
                return rowFieldlist;
            }

        };

        tm.setAnalysisResult(analysisResult);

        return tm;
    }

    public static SanctionTableModel generateSanctionMessageTableModel(final Message message, final List<String> fields2Check, final List<String> fields2BIC, final HashMap<String, String> fieldNames) {

        final SanctionTableModel tm = new SanctionTableModel() {

            // SwiftMessageParser.MessageBlock contentMessageBlock;

            MessageContent           messageContent;

            HashMap<Integer, String> rowTypelist;
            HashMap<Integer, String> rowFieldlist;
            HashMap<String, Integer> fieldRowlist;

            List<String>             fields2Check;

            HashMap<String, String>  fieldNames;

            boolean                  isSepa = false;

            // List<String> fields2BIC;

            @Override
            public void addTableModelListener(final TableModelListener l) {
                // super.addTableModelListener(l);

            }

            // show longtext for BIcs
            private String check4BIC(final String text) {

                String result = text;

                final String longText = BICHelper.extendBIC(text);

                if (longText != null) {
                    result = text + " (= " + longText + " )";
                }
                return result;
            }

            @Override
            public Class<?> getColumnClass(final int columnIndex) {
                return String.class;
            }

            @Override
            public int getColumnCount() {
                return 3;
            }

            @Override
            public String getColumnName(final int columnIndex) {
                String header;
                switch (columnIndex) {

                    case 0:
                        header = "Field";
                        break;
                    case 1:
                        header = "Hit";
                        break;
                    case 2:
                        header = "Content";
                        break;
                    default:
                        header = "nix";
                }
                return header;
            }

            @Override
            public HashMap<Integer, String> getHitRowFieldList(final AnalysisResult result) {

                if (rowFieldlist == null) {
                    rowFieldlist = new HashMap<Integer, String>();
                    fieldRowlist = new HashMap<String, Integer>();

                    int i = 0;
                    for (final String key : messageContent.getFieldsAndValues().keySet()) {
                        // what if more rows per field ?

                        rowFieldlist.put(new Integer(i), key);
                        fieldRowlist.put(key, new Integer(i));
                        i++;
                    }
                }
                return rowFieldlist;
            }

            @Override
            public HashMap<String, Integer> getHitFieldRowList(final AnalysisResult result) {

                return fieldRowlist;
            }

            private boolean isFieldCheck(final String fieldName, final boolean isSepaUglyDuckling) {
                boolean check = false;

                if (isSepaUglyDuckling) {
                    for (final String field : fields2Check) {
                        if (fieldName.contains(field)) {
                            check = true;
                            break;
                        }
                    }
                }
                else {
                    check = fields2Check.contains(fieldName);
                }
                return check;
            }

            private String getFieldName(final String fieldNameShort, final boolean isSepaUglyDuckling) {

                String longName = null;

                if (isSepaUglyDuckling) {
                    for (final String field : fieldNames.keySet()) {
                        if (fieldNameShort.contains(field)) {
                            longName = fieldNames.get(field);
                            break;
                        }
                    }
                }
                else {
                    longName = fieldNames.get(fieldNameShort);
                }

                if (longName == null) {
                    longName = fieldNameShort;
                }

                return longName;

            }

            private String getBicFieldExtended(final String fieldName, final String txt, final boolean isSepaUglyDuckling) {

                String value = txt;

                if (isSepaUglyDuckling) {
                    for (final String field : fields2BIC) {
                        if (fieldName.contains(field)) {
                            value = check4BIC(txt);
                            break;
                        }
                    }
                }
                else {
                    value = (((fields2BIC == null) || fields2BIC.contains(fieldName)) ? check4BIC(txt) : txt);
                }

                return value;

            }

            @Override
            public HashMap<Integer, String> getHitTypeRowList(final AnalysisResult result) {

                if (rowTypelist == null) {
                    rowTypelist = new HashMap<Integer, String>();

                    int i = 0;
                    for (final String key : messageContent.getFieldsAndValues().keySet()) {
                        // what if more rows per field ?
                        // if (!fields2Check.contains(key)) {

                        if (!isFieldCheck(key, isSepa)) {
                            rowTypelist.put(new Integer(i), FIELD_TYPE_UNUSED); // TODO this should be a constant
                        }

                        for (final HitResult hresult : result.getHitList()) {
                            if (hresult.getHitField().equals(key)) {
                                rowTypelist.put(new Integer(i), hresult.getHitType());
                                break;
                            }
                        }
                        i++;
                    }
                }
                return rowTypelist;
            }

            @Override
            public int getRowCount() {
                return messageContent.getFieldsAndValues().size();
            }

            @Override
            public Object getValueAt(final int rowIndex, final int columnIndex) {

                int i = 0;
                String value = " ";
                String kkey = "";

                for (final String key : messageContent.getFieldsAndValues().keySet()) {
                    if (i == rowIndex) {
                        kkey = key;
                        break;
                    }
                    else {
                        i++;
                    }
                }
                switch (columnIndex) {

                    case 0:
                        value = getFieldName(kkey, isSepa);
                        break;
                    case 1:
                        value = rowTypelist.get(new Integer(rowIndex));
                        if ((value == null) || value.equals(FIELD_TYPE_UNUSED)) {
                            value = " ";
                        }
                        break;
                    case 2:
                        final String txt = messageContent.getFieldsAndValues().get(kkey);
                        value = getBicFieldExtended(kkey, txt, isSepa);
                        break;
                    default:
                        value = "nix";
                }

                return value;
            }

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return false;
            }

            @Override
            public void removeTableModelListener(final TableModelListener l) {

            }

            @Override
            public void setFieldsList(final List<String> fields2Check) {
                this.fields2Check = fields2Check;
            }

            @Override
            public void setMessage(final Message message) {

                isSepa = (message instanceof SepaMessage);

                messageContent = message.getMessageContent();

                if ((messageContent == null) || (messageContent.getFieldsAndValues().size() == 0)) {

                    messageContent.setFieldsAndValueTokens(null); // TODO: this is not good here :-)

                    if (message instanceof SwiftMessage) {
                        messageContent = SwiftAnalyzer.getFieldsToCheckInternal(message);
                    }
                    else {
                        if (isSepa) {
                            messageContent = SepaAnalyzer.getFieldsToCheckInternal(message);

                        }
                    }
                }

                // final List<SwiftMessageParser.MessageBlock> msgBlocks = SwiftMessageParser.parseMessage(message.getRawContent());
                //
                // for (final SwiftMessageParser.MessageBlock messageBlock : msgBlocks) {
                // if (messageBlock.getFields().size() > 0) {
                // contentMessageBlock = messageBlock;
                // break;
                // }
                // }

            }

            @Override
            public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {

            }

            @Override
            public void setFieldnameList(final HashMap<String, String> fieldNames) {
                this.fieldNames = fieldNames;
            }
        };

        tm.setMessage(message);
        tm.setFieldsList(fields2Check);
        tm.setFieldnameList(fieldNames);
        return tm;
    }

    public static TableModel getEntityNameTableModel(final WL_Entity entity) {

        final TableModel tableModel = new TableModel() {

            @Override
            public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {

            }

            @Override
            public void removeTableModelListener(final TableModelListener l) {

            }

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return false;
            }

            @Override
            public Object getValueAt(final int rowIndex, final int columnIndex) {
                String colName = "";

                final WL_Name name = entity.getNames().get(rowIndex);
                switch (columnIndex) {
                    case 0:
                        colName = name.getFirstName() != null ? name.getFirstName() : " ";
                        break;
                    case 1:
                        colName = name.getMiddleName() != null ? name.getMiddleName() : " ";
                        break;
                    case 2:
                        colName = name.getLastName() != null ? name.getLastName() : " ";
                        break;
                    case 3:
                        colName = name.getWholeName() != null ? name.getWholeName() : " ";
                        break;
                    case 4:
                        colName = name.isAka() ? "Yes" : "No";
                        break;
                }
                return colName;

            }

            @Override
            public int getRowCount() {

                return entity.getNames().size();
            }

            @Override
            public String getColumnName(final int columnIndex) {
                String value = "";
                switch (columnIndex) {
                    case 0:
                        value = "Firstname";
                        break;
                    case 1:
                        value = "Middlename";
                        break;
                    case 2:
                        value = "Surname";
                        break;
                    case 3:
                        value = "Wholename";
                        break;
                    case 4:
                        value = "A.k.A";
                        break;

                }

                return value;
            }

            @Override
            public int getColumnCount() {

                return 5;
            }

            @Override
            public Class<?> getColumnClass(final int columnIndex) {

                return String.class;
            }

            @Override
            public void addTableModelListener(final TableModelListener l) {

            }
        };

        return tableModel;
    }

    public static TableModel getEntityRelationsTableModel(final WatchListInformant watchlistInformat, final String listname, final WL_Entity focusedEntity) {

        final WatchListInformant watchlistInformatLocal = watchlistInformat;

        final ArrayList<String> refIdList = new ArrayList<>(focusedEntity.getRelations().keySet());

        final TableModel tableModel = new TableModel() {

            @Override
            public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {

            }

            @Override
            public void removeTableModelListener(final TableModelListener l) {

            }

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return false;
            }

            @Override
            public Object getValueAt(final int rowIndex, final int columnIndex) {
                String colName = "";

                // WL_Name name = focusedEntity.getReleations().keySet()..getNames().get(rowIndex);

                final WL_Entity enity = watchlistInformatLocal.getSanctionListEntityDetails(listname, refIdList.get(rowIndex));

                switch (columnIndex) {
                    case 0:
                        colName = focusedEntity.getRelations().get(refIdList.get(rowIndex));
                        break;
                    case 1:
                        colName = enity != null ? (enity.getNames().get(0).getWholeName()) : "unknown";
                        break;
                    case 2:
                        colName = refIdList.get(rowIndex);
                        break;
                    case 3:
                        colName = enity != null ? enity.getType() : "unknown";
                        break;
                }
                return colName;

            }

            @Override
            public int getRowCount() {

                return focusedEntity.getRelations().size();
            }

            @Override
            public String getColumnName(final int columnIndex) {
                String value = "";
                switch (columnIndex) {
                    case 0:
                        value = "Relationship";
                        break;
                    case 1:
                        value = "Entity";
                        break;
                    case 2:
                        value = "Id";
                        break;
                    case 3:
                        value = "Type";
                        break;

                }

                return value;
            }

            @Override
            public int getColumnCount() {

                return 4;
            }

            @Override
            public Class<?> getColumnClass(final int columnIndex) {

                return String.class;
            }

            @Override
            public void addTableModelListener(final TableModelListener l) {

            }
        };

        return tableModel;
    }

}
