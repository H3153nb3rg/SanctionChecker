package at.jps.slcm.gui.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableModel;

import at.jps.slcm.gui.models.DisplayEntitySearchDetails;

public class DisplayEntitySearchService implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 544151248495418612L;
    TableModel                tableModel;

    public void setModel(TableModel tableModel2) {
        this.tableModel = tableModel2;
    }

    public synchronized List<DisplayEntitySearchDetails> displayAllFields() {

        List<DisplayEntitySearchDetails> fields = new ArrayList<DisplayEntitySearchDetails>();

        for (int i = 0; i < tableModel.getRowCount(); i++) {

            DisplayEntitySearchDetails row = new DisplayEntitySearchDetails();
            fields.add(row);

            row.setId((long) i);
            row.setWlname((String) tableModel.getValueAt(i, 0));
            row.setWlid((String) tableModel.getValueAt(i, 1));
            row.setEntry((String) tableModel.getValueAt(i, 2));
            row.setRemark((String) tableModel.getValueAt(i, 3));

        }
        return fields;
    }

}
