package at.jps.slcm.gui.services;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableModel;

import at.jps.slcm.gui.models.DisplayRelation;

public class DisplayRelationService {

    TableModel tableModel;

    public void setModel(TableModel tableModel2) {
        this.tableModel = tableModel2;
    }

    public synchronized List<DisplayRelation> displayAllFields() {

        List<DisplayRelation> fields = new ArrayList<DisplayRelation>();
        if (tableModel != null) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {

                DisplayRelation dnd = new DisplayRelation();
                fields.add(dnd);

                dnd.setId((long) i);
                dnd.setRelation((String) tableModel.getValueAt(i, 0));
                dnd.setEntity((String) tableModel.getValueAt(i, 1));
                dnd.setWlid((String) tableModel.getValueAt(i, 2));
                dnd.setType((String) tableModel.getValueAt(i, 3));

            }
        }
        return fields;
    }

}
