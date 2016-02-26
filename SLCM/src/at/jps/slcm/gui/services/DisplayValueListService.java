package at.jps.slcm.gui.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableModel;

import at.jps.slcm.gui.models.DisplayValueListRecord;

public class DisplayValueListService implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5693077502813510439L;
    TableModel                tableModel;

    public void setModel(TableModel tableModel2) {
        tableModel = tableModel2;
    }

    public synchronized List<DisplayValueListRecord> displayAllFields() {

        final List<DisplayValueListRecord> fields = new ArrayList<DisplayValueListRecord>();

        for (int i = 0; i < tableModel.getRowCount(); i++) {

            final DisplayValueListRecord record = new DisplayValueListRecord();
            fields.add(record);

            record.setId((long) i);
            record.setValue((String) tableModel.getValueAt(i, 0));

        }
        return fields;
    }

}
