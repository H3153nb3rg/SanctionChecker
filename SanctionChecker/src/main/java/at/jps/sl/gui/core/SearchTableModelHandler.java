package at.jps.sl.gui.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.apache.commons.collections4.MultiValuedMap;

import at.jps.sanction.model.OptimizationRecord;
import at.jps.sanction.model.listhandler.NoWordHitListHandler;
import at.jps.sanction.model.listhandler.ReferenceListHandler;
import at.jps.sanction.model.listhandler.SanctionListHandler;
import at.jps.sanction.model.listhandler.ValueListHandler;
import at.jps.sanction.model.sl.entities.WL_Entity;
import at.jps.sanction.model.sl.entities.WL_Name;

public class SearchTableModelHandler {

    public interface ValueListTableModel extends TableModel {

        public void setValueList(ValueListHandler valueListHandler);

        public HashMap<Integer, String> getHitRowFieldList();

    }

    public interface ReferenceListTableModel extends TableModel {

        public void setReferenceList(ReferenceListHandler refListhandler);

        public HashMap<Integer, String> getHitRowFieldList();

    }

    public interface WatchListTableModel extends TableModel {

        public void setOptiList(List<SearchResultRecord> searchResult);

        public HashMap<Integer, String> getHitRowFieldList();

    }

    public interface OptiListTableModel extends TableModel {

        public void setOptiList(List<OptimizationRecord> searchResult);

        public HashMap<Integer, String> getHitRowFieldList();

    }

    public interface NoHitListTableModel extends TableModel {

        public void setNoHitList(MultiValuedMap<String, String> mvm);

        public HashMap<Integer, String> getHitRowFieldList();

    }

    public static ReferenceListTableModel generateReferenceListTableModel(ReferenceListHandler refListhandler) {

        final ReferenceListTableModel tm = new ReferenceListTableModel() {

            ReferenceListHandler             refListhandler;

            ArrayList<Entry<Object, Object>> entries;

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
                return 2;
            }

            @Override
            public String getColumnName(final int columnIndex) {
                String header;
                switch (columnIndex) {

                    case 0:
                        header = "Key";
                        break;
                    case 1:
                        header = "Value";
                        break;
                    default:
                        header = "nix";
                }
                return header;
            }

            @Override
            public int getRowCount() {
                return refListhandler.getValues().size();
            }

            @Override
            public Object getValueAt(final int rowIndex, final int columnIndex) {

                final Entry<Object, Object> entry = entries.get(rowIndex);

                String value = " ";

                switch (columnIndex) {

                    case 0:
                        value = (String) entry.getKey();
                        break;
                    case 1:
                        value = (String) entry.getValue();
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
            public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {

            }

            @Override
            public HashMap<Integer, String> getHitRowFieldList() {

                return null;
            }

            @Override
            public void setReferenceList(ReferenceListHandler refListhandler) {

                this.refListhandler = refListhandler;

                entries = new ArrayList<Entry<Object, Object>>();

                for (final Entry<Object, Object> e : refListhandler.getValues().entrySet()) {
                    entries.add(e);
                }

            }

        };

        tm.setReferenceList(refListhandler);

        return tm;
    }

    public static ValueListTableModel generateValueListTableModel(ValueListHandler valListhandler) {

        final ValueListTableModel tm = new ValueListTableModel() {

            ValueListHandler  valListhandler;

            ArrayList<String> entries;

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
                return 1;
            }

            @Override
            public String getColumnName(final int columnIndex) {
                String header;
                switch (columnIndex) {

                    case 0:
                        header = "Entry";
                        break;
                    default:
                        header = "nix";
                }
                return header;
            }

            @Override
            public int getRowCount() {
                return valListhandler.getValues().size();
            }

            @Override
            public Object getValueAt(final int rowIndex, final int columnIndex) {

                final String value = entries.get(rowIndex);

                // switch (columnIndex) {
                //
                // case 0:
                // value = (String) entry.getKey();
                // break;
                // default:
                // value = "nix";
                // }

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
            public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {

            }

            @Override
            public HashMap<Integer, String> getHitRowFieldList() {

                return null;
            }

            @Override
            public void setValueList(ValueListHandler valListhandler) {

                this.valListhandler = valListhandler;

                entries = new ArrayList<String>();

                entries.addAll(valListhandler.getValues());
            }

        };

        tm.setValueList(valListhandler);

        return tm;
    }

    public static WatchListTableModel generateWatchListTableModel(List<SearchResultRecord> resultSet) {

        final WatchListTableModel tm = new WatchListTableModel() {

            List<SearchResultRecord> resultSet;

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
                return 4;
            }

            @Override
            public String getColumnName(final int columnIndex) {
                String header;
                switch (columnIndex) {

                    case 0:
                        header = "WL Name";
                        break;
                    case 1:
                        header = "WL Id";
                        break;
                    case 2:
                        header = "Entry";
                        break;
                    case 3:
                        header = "Remark";
                        break;
                    default:
                        header = "nix";
                }
                return header;
            }

            @Override
            public int getRowCount() {
                return resultSet.size();
            }

            @Override
            public Object getValueAt(final int rowIndex, final int columnIndex) {

                final SearchResultRecord sr = resultSet.get(rowIndex);
                String value = "";
                switch (columnIndex) {

                    case 0:
                        value = sr.getListName();
                        break;
                    case 1:
                        value = sr.getListId();
                        break;
                    case 2:
                        value = sr.getToken();
                        break;
                    case 3:
                        value = sr.getComment();
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
            public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {

            }

            @Override
            public HashMap<Integer, String> getHitRowFieldList() {

                return null;
            }

            @Override
            public void setOptiList(List<SearchResultRecord> resultSet) {

                this.resultSet = resultSet;

            }

        };

        tm.setOptiList(resultSet);

        return tm;
    }

    public static NoHitListTableModel generateNoHitListTableModel(NoWordHitListHandler valListhandler) {

        final NoHitListTableModel tm = new NoHitListTableModel() {

            // NoHitListHandler valListhandler;

            SortedMap<String, String> entries;

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
                return 2;
            }

            @Override
            public String getColumnName(final int columnIndex) {
                String header;
                switch (columnIndex) {

                    case 0:
                        header = "TX Content";
                        break;
                    default:
                        header = "WL Content";
                }
                return header;
            }

            @Override
            public int getRowCount() {
                return entries.size();
            }

            @Override
            public Object getValueAt(final int rowIndex, final int columnIndex) {

                String value = "";

                int i = 0;

                for (final String key : entries.keySet()) {
                    if (i == rowIndex) {
                        switch (columnIndex) {

                            case 0:
                                value = key;
                                break;
                            case 1:
                                value = entries.get(key);
                                break;
                            default:
                                value = "nix";
                        }
                        break;
                    }
                    i++;
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
            public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {

            }

            @Override
            public HashMap<Integer, String> getHitRowFieldList() {

                return null;
            }

            @Override
            public void setNoHitList(MultiValuedMap<String, String> mvm) {

                entries = new TreeMap<String, String>();

                for (final String key : mvm.keySet()) {
                    String values = "";

                    for (final String value : mvm.get(key)) {
                        values += value + ";";
                    }
                    entries.put(key, values);
                }

            }

        };

        tm.setNoHitList(valListhandler.getValues());

        return tm;
    }

    public static OptiListTableModel generateOptiListTableModel(List<OptimizationRecord> resultSet) {

        final OptiListTableModel tm = new OptiListTableModel() {

            List<OptimizationRecord> resultSet;

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
                return 5;
            }

            @Override
            public String getColumnName(final int columnIndex) {
                String header;
                switch (columnIndex) {

                    case 0:
                        header = "Field";
                        break;
                    case 1:
                        header = "TX Content";
                        break;
                    case 2:
                        header = "WatchList";
                        break;
                    case 3:
                        header = "WL ID";
                        break;
                    case 4:
                        header = "!";
                        break;
                    default:
                        header = "nix";
                }
                return header;
            }

            @Override
            public int getRowCount() {
                return resultSet.size();
            }

            @Override
            public Object getValueAt(final int rowIndex, final int columnIndex) {

                final OptimizationRecord sr = resultSet.get(rowIndex);
                String value = "";
                switch (columnIndex) {

                    case 0:
                        value = sr.getFieldName();
                        break;
                    case 1:
                        value = sr.getToken();
                        break;
                    case 2:
                        value = sr.getWatchListName();
                        break;
                    case 3:
                        value = sr.getWatchListId();
                        break;
                    case 4:
                        value = sr.getStatus();
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
            public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {

            }

            @Override
            public HashMap<Integer, String> getHitRowFieldList() {

                return null;
            }

            @Override
            public void setOptiList(List<OptimizationRecord> resultSet) {

                this.resultSet = resultSet;

            }

        };

        tm.setOptiList(resultSet);

        return tm;
    }

    private static SearchResultRecord buildSearchResultRecord(String listname, WL_Entity entity, WL_Name name) {
        final SearchResultRecord sr = new SearchResultRecord();
        sr.setComment(entity.getComment());
        sr.setListName(listname);
        sr.setListId(entity.getWL_Id());
        sr.setToken(name.getWholeName());

        return sr;
    }

    public static WatchListTableModel doSearch(final SanctionListHandler watchlistHandler, final String searchPattern) {

        WatchListTableModel wltm = null;

        if (watchlistHandler != null) {
            final String searchString = searchPattern.trim().toUpperCase();

            final List<SearchResultRecord> resultSet = new ArrayList<SearchResultRecord>();

            // search Names
            for (final WL_Entity entity : watchlistHandler.getEntityList()) {
                for (final WL_Name name : entity.getNames()) {
                    if (name.getWholeName().toUpperCase().contains(searchString)) {
                        // Listname | ID | Pattern | Comment
                        final SearchResultRecord sr = buildSearchResultRecord(watchlistHandler.getListName(), entity, name);
                        resultSet.add(sr);
                        break;
                    }
                }
            }
            // search vor Id
            final WL_Entity entity = watchlistHandler.getEntityById(searchString);
            if (entity != null) {
                for (final WL_Name name : entity.getNames()) {
                    final SearchResultRecord sr = buildSearchResultRecord(watchlistHandler.getListName(), entity, name);
                    resultSet.add(sr);
                    break;  // TODO: is this correct ?
                }
            }
            wltm = generateWatchListTableModel(resultSet);
        }

        return wltm;

    }

    public static WatchListTableModel doSearch(final HashMap<String, SanctionListHandler> watchlistHandlers, final String searchPattern) {

        final String searchString = searchPattern.trim().toUpperCase();

        final List<SearchResultRecord> resultSet = new ArrayList<SearchResultRecord>();

        for (final String key : watchlistHandlers.keySet()) {
            final SanctionListHandler wlHandler = watchlistHandlers.get(key);

            // search Names
            for (final WL_Entity entity : wlHandler.getEntityList()) {
                for (final WL_Name name : entity.getNames()) {
                    if (name.getWholeName().toUpperCase().contains(searchString)) {
                        // Listname | ID | Pattern | Comment
                        final SearchResultRecord sr = buildSearchResultRecord(wlHandler.getListName(), entity, name);
                        resultSet.add(sr);
                        break;
                    }
                }
            }
            // search vor Id
            final WL_Entity entity = wlHandler.getEntityById(searchString);
            if (entity != null) {
                for (final WL_Name name : entity.getNames()) {
                    final SearchResultRecord sr = buildSearchResultRecord(wlHandler.getListName(), entity, name);
                    resultSet.add(sr);
                    break;  // TODO: is this correct ?
                }
            }
        }

        final WatchListTableModel wltm = generateWatchListTableModel(resultSet);

        return wltm;
    }
}
