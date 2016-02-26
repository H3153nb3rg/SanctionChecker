package at.jps.slcm.gui.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableModel;

import at.jps.slcm.gui.models.DisplayReferenceListRecord;

public class DisplayReferenceListService implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5693077502813510439L;
    TableModel                tableModel;

    public void setModel(TableModel tableModel2) {
        tableModel = tableModel2;
    }

    public synchronized List<DisplayReferenceListRecord> displayAllFields() {

        final List<DisplayReferenceListRecord> fields = new ArrayList<DisplayReferenceListRecord>();

        for (int i = 0; i < tableModel.getRowCount(); i++) {

            final DisplayReferenceListRecord record = new DisplayReferenceListRecord();
            fields.add(record);

            record.setId((long) i);
            record.setKey((String) tableModel.getValueAt(i, 0));
            record.setValue((String) tableModel.getValueAt(i, 1));

        }
        return fields;
    }

}
