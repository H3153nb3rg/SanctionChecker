package at.jps.slcm.gui.services;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableModel;

import at.jps.slcm.gui.models.DisplayNameDetails;

public class DisplayNameDetailsService {

    TableModel tableModel;

    public void setModel(TableModel tableModel2) {
        this.tableModel = tableModel2;
    }

    public synchronized List<DisplayNameDetails> displayAllFields() {

        List<DisplayNameDetails> fields = new ArrayList<DisplayNameDetails>();
        if (tableModel != null) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {

                DisplayNameDetails dnd = new DisplayNameDetails();
                fields.add(dnd);

                dnd.setId((long) i);
                dnd.setFirstname((String) tableModel.getValueAt(i, 0));
                dnd.setMiddleName((String) tableModel.getValueAt(i, 1));
                dnd.setSurname((String) tableModel.getValueAt(i, 2));
                dnd.setWholename((String) tableModel.getValueAt(i, 3));
                dnd.setAka((String) tableModel.getValueAt(i, 4));

            }
        }
        return fields;
    }

}
