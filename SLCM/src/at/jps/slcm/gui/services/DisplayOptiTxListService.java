package at.jps.slcm.gui.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableModel;

import at.jps.slcm.gui.models.DisplayOptiTxListRecord;

public class DisplayOptiTxListService implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5693077502813510439L;
    TableModel                tableModel;

    public void setModel(TableModel tableModel2) {
        tableModel = tableModel2;
    }

    public synchronized List<DisplayOptiTxListRecord> displayAllFields() {

        final List<DisplayOptiTxListRecord> fields = new ArrayList<DisplayOptiTxListRecord>();

        for (int i = 0; i < tableModel.getRowCount(); i++) {

            final DisplayOptiTxListRecord dm = new DisplayOptiTxListRecord();
            fields.add(dm);

            dm.setId((long) i);

            dm.setField((String) tableModel.getValueAt(i, 0));
            dm.setContent((String) tableModel.getValueAt(i, 1));
            dm.setWatchlist((String) tableModel.getValueAt(i, 2));
            dm.setWlid((String) tableModel.getValueAt(i, 3));
            dm.setStatus((String) tableModel.getValueAt(i, 4));

        }
        return fields;
    }

}
