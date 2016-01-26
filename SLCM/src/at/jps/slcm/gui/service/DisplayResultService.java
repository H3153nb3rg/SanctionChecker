package at.jps.slcm.gui.service;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableModel;

import at.jps.slcm.gui.model.DisplayResult;

public class DisplayResultService {

    TableModel tableModel;

    public void setModel(TableModel tableModel2) {
        this.tableModel = tableModel2;
    }

    public synchronized List<DisplayResult> displayAllFields() {

        List<DisplayResult> fields = new ArrayList<DisplayResult>();
        if (tableModel != null) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {

                DisplayResult dar = new DisplayResult();
                fields.add(dar);

                dar.setId((long) i);
                dar.setWatchList((String) tableModel.getValueAt(i, 0));
                dar.setWlid((String) tableModel.getValueAt(i, 1));
                dar.setField((String) tableModel.getValueAt(i, 2));
                dar.setValue((String) tableModel.getValueAt(i, 3));
                dar.setDescr((String) tableModel.getValueAt(i, 5));
            }
        }
        return fields;
    }

}
