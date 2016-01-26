package at.jps.slcm.gui.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableModel;

import at.jps.slcm.gui.model.DisplayWordHit;

public class DisplayWordHitService implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5693077502813510439L;
    TableModel                tableModel;

    public void setModel(TableModel tableModel2) {
        this.tableModel = tableModel2;
    }

    public synchronized List<DisplayWordHit> displayAllFields() {

        List<DisplayWordHit> fields = new ArrayList<DisplayWordHit>();

        for (int i = 0; i < tableModel.getRowCount(); i++) {

            DisplayWordHit dm = new DisplayWordHit();
            fields.add(dm);

            dm.setId((long) i);
            dm.setWatchlist((String) tableModel.getValueAt(i, 0));
            dm.setSlid((String) tableModel.getValueAt(i, 1));
            dm.setListentry((String) tableModel.getValueAt(i, 2));
            dm.setField((String) tableModel.getValueAt(i, 3));
            dm.setContent((String) tableModel.getValueAt(i, 4));
            dm.setValue((String) tableModel.getValueAt(i, 5));

        }
        return fields;
    }

}
