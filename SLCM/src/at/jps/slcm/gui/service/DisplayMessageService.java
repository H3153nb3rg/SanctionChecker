package at.jps.slcm.gui.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableModel;

import at.jps.slcm.gui.model.DisplayMessage;

public class DisplayMessageService implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5693077502813510439L;
    TableModel                tableModel;

    public void setModel(TableModel tableModel2) {
        this.tableModel = tableModel2;
    }

    public synchronized List<DisplayMessage> displayAllFields() {

        List<DisplayMessage> fields = new ArrayList<DisplayMessage>();

        for (int i = 0; i < tableModel.getRowCount(); i++) {

            DisplayMessage dm = new DisplayMessage();
            fields.add(dm);

            dm.setId((long) i);
            dm.setField((String) tableModel.getValueAt(i, 0));
            dm.setHit((String) tableModel.getValueAt(i, 1));
            dm.setContent((String) tableModel.getValueAt(i, 2));

        }
        return fields;
    }

}
