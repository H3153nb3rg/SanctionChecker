package at.jps.sl.gui.model.swift;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import at.jps.sanction.core.util.BICHelper;
import at.jps.sanction.domain.SanctionListHitResult;
import at.jps.sanction.domain.SanctionWordHitInfo;
import at.jps.sanction.domain.swift.SwiftMessage;
import at.jps.sanction.domain.swift.SwiftMessageParser;
import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.HitResult;
import at.jps.sanction.model.Message;
import at.jps.sanction.model.WordHitInfo;
import at.jps.sanction.model.sl.entities.WL_Entity;
import at.jps.sanction.model.sl.entities.WL_Name;

public class SwiftTableModelHandler implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1980383744603760444L;

    public interface SwiftAnalysisResultTableModel extends TableModel {
        public void setAnalysisResult(AnalysisResult analysisResult);

        public HashMap<Integer, String> getHitRowFieldList();

    }

    // implemented for swiftMessages only
    // TODO: generic

    public interface SwiftTableModel extends TableModel {

        public HashMap<Integer, String> getHitRowFieldList(AnalysisResult result);

        public HashMap<String, Integer> getHitFieldRowList(AnalysisResult result);

        public HashMap<Integer, String> getHitTypeRowList(AnalysisResult result);

        void setFieldsList(List<String> fields2Check);

        public void setMessage(Message message);
    }

    public final static String FIELD_TYPE_UNUSED = "UNUSED";

    public static SwiftAnalysisResultTableModel generateSwiftAnalysisResultTableModel(final AnalysisResult analysisResult) {

        final SwiftAnalysisResultTableModel tm = new SwiftAnalysisResultTableModel() {

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
                        if (hitResult instanceof SanctionListHitResult) {
                            value = ((SanctionListHitResult) hitResult).getHitListName();
                        }
                        else {
                            value = "No List";
                        }
                        break;
                    case 1:
                        if (hitResult instanceof SanctionListHitResult) {
                            value = ((SanctionListHitResult) hitResult).getHitId();
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
                        if (hitResult instanceof SanctionListHitResult) {
                            value = ((SanctionListHitResult) hitResult).getHitOptimized();
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
                    for (HitResult hitResult : analysisResult.getHitList()) {

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

    public static SwiftAnalysisResultTableModel generateSwiftAnalysisWordListTableModel(final AnalysisResult analysisResult) {

        final SwiftAnalysisResultTableModel tm = new SwiftAnalysisResultTableModel() {

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

                        if (wordHitInfo instanceof SanctionWordHitInfo) {
                            value = ((SanctionWordHitInfo) wordHitInfo).getSanctionListName();
                        }
                        else {
                            value = "No List";
                        }
                        break;
                    case 1:
                        if (wordHitInfo instanceof SanctionWordHitInfo) {
                            value = ((SanctionWordHitInfo) wordHitInfo).getSanctionListId();
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
                    for (WordHitInfo wordHitInfo : analysisResult.getHitTokensList()) {

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

    public static SwiftTableModel generateSwiftMessageTableModel(final SwiftMessage message, final List<String> fields2Check, final List<String> fields2BIC) {

        final SwiftTableModel tm = new SwiftTableModel() {

            SwiftMessageParser.MessageBlock contentMessageBlock;

            HashMap<Integer, String>        rowTypelist;
            HashMap<Integer, String>        rowFieldlist;
            HashMap<String, Integer>        fieldRowlist;

            List<String>                    fields2Check;

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
                    for (final String key : contentMessageBlock.getFields().keySet()) {
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

            @Override
            public HashMap<Integer, String> getHitTypeRowList(final AnalysisResult result) {

                if (rowTypelist == null) {
                    rowTypelist = new HashMap<Integer, String>();

                    int i = 0;
                    for (final String key : contentMessageBlock.getFields().keySet()) {
                        // what if more rows per field ?
                        if (!fields2Check.contains(key)) {
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
                return contentMessageBlock.getFields().size();
            }

            @Override
            public Object getValueAt(final int rowIndex, final int columnIndex) {

                int i = 0;
                String value = " ";
                String kkey = "";

                for (final String key : contentMessageBlock.getFields().keySet()) {
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
                        value = kkey;
                        break;
                    case 1:
                        value = rowTypelist.get(new Integer(rowIndex));
                        if ((value == null) || value.equals(FIELD_TYPE_UNUSED)) {
                            value = " ";
                        }
                        break;
                    case 2:
                        final String txt = contentMessageBlock.getFields().get(kkey);
                        value = (((fields2BIC == null) || fields2BIC.contains(kkey)) ? check4BIC(txt) : txt);
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

                final List<SwiftMessageParser.MessageBlock> msgBlocks = SwiftMessageParser.parseMessage(message.getRawContent());

                for (final SwiftMessageParser.MessageBlock messageBlock : msgBlocks) {
                    if (messageBlock.getFields().size() > 0) {
                        contentMessageBlock = messageBlock;
                        break;
                    }
                }

            }

            @Override
            public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {

            }

        };

        tm.setMessage(message);
        tm.setFieldsList(fields2Check);

        return tm;
    }

    public static TableModel getEntityNameTableModel(final WL_Entity entity) {

        TableModel tableModel = new TableModel() {

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

            }

            @Override
            public void removeTableModelListener(TableModelListener l) {

            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                String colName = "";

                WL_Name name = entity.getNames().get(rowIndex);
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
            public String getColumnName(int columnIndex) {
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
            public Class<?> getColumnClass(int columnIndex) {

                return String.class;
            }

            @Override
            public void addTableModelListener(TableModelListener l) {

            }
        };

        return tableModel;
    }

}
