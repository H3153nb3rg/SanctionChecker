package at.jps.slcm.gui.components;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import at.jps.sanction.model.wl.entities.WL_Entity;
import at.jps.sl.gui.AdapterHelper;
import at.jps.slcm.gui.models.DisplayNameDetails;
import at.jps.slcm.gui.models.DisplayRelation;
import at.jps.slcm.gui.services.DisplayNameDetailsService;
import at.jps.slcm.gui.services.DisplayRelationService;

public class SanctionListDetails extends VerticalLayout {

    /**
     *
     */
    private static final long               serialVersionUID          = -4270199582565969748L;

    private final Grid                      tableEntityNameDetails    = new Grid();
    private final Grid                      tableEntityRelations      = new Grid();

    private ComboBox                        textPane_LegalBack;
    private TextField                       textField_Type;
    private TextField                       textField_ListDescription;
    private TextArea                        textPane_Remark;

    private final DisplayRelationService    displayRelationService    = new DisplayRelationService();
    private final DisplayNameDetailsService displayNameDetailsService = new DisplayNameDetailsService();

    private final AdapterHelper             guiAdapter;

    public SanctionListDetails(AdapterHelper guiAdapter) {
        super();
        this.guiAdapter = guiAdapter;

        buildSanctionListDetails();
    }

    private void buildSanctionListDetails() {

        tableEntityNameDetails.setContainerDataSource(new BeanItemContainer<>(DisplayNameDetails.class));
        tableEntityNameDetails.setColumnOrder("firstname", "middleName", "surname", "wholename", "aka");
        tableEntityNameDetails.removeColumn("id");
        tableEntityNameDetails.setSelectionMode(Grid.SelectionMode.SINGLE);
        // tableNameDetails.addActionHandler(new SpreadsheetDefaultActionHandler());

        final int cratioND[] = { 1, 1, 2, 3, 1 };
        int i = 0;
        for (final Column c : tableEntityNameDetails.getColumns()) {
            if (i < (cratioND.length - 1)) {
                c.setExpandRatio(cratioND[i]);
            }
            i++;
        }

        tableEntityRelations.setContainerDataSource(new BeanItemContainer<>(DisplayRelation.class));
        tableEntityRelations.setColumnOrder("relation", "entity", "wlid", "type");
        tableEntityRelations.removeColumn("id");
        tableEntityRelations.setSelectionMode(Grid.SelectionMode.SINGLE);
        // tableNameDetails.addActionHandler(new SpreadsheetDefaultActionHandler());

        final int cratioER[] = { 1, 2, 1 };
        i = 0;
        for (final Column c : tableEntityRelations.getColumns()) {
            if (i < (cratioER.length - 1)) {
                c.setExpandRatio(cratioER[i]);
            }
            i++;
        }
        tableEntityNameDetails.setSizeFull();
        tableEntityRelations.setSizeFull();

        // form
        final VerticalLayout listDetails = new VerticalLayout();

        textField_ListDescription = new TextField("List description");
        textField_ListDescription.setReadOnly(true);
        textField_ListDescription.setWidth("100%");
        listDetails.addComponent(textField_ListDescription);

        textPane_LegalBack = new ComboBox("Legal Background");
        textPane_LegalBack.setInvalidAllowed(false);
        textPane_LegalBack.setNullSelectionAllowed(false);
        // textPane_LegalBack.addItems("Iran", "Syrien", "InternalUse", "WoswasI");
        textPane_LegalBack.setWidth("100%");

        // textPane_LegalBack = new TextArea("Legal Background");
        // textPane_LegalBack.setReadOnly(true);
        // textPane_LegalBack.setWidth("100%");
        // textPane_LegalBack.setRows(2);
        listDetails.addComponent(textPane_LegalBack);

        textField_Type = new TextField("Entity Type");
        textField_Type.setReadOnly(true);
        textField_Type.setWidth("100%");
        listDetails.addComponent(textField_Type);

        textPane_Remark = new TextArea("Remark");
        textPane_Remark.setReadOnly(true);
        textPane_Remark.setWidth("100%");

        listDetails.addComponent(textPane_Remark);
        listDetails.setExpandRatio(textPane_Remark, 1);

        listDetails.setSizeFull();

        final TabSheet sLDetails = new TabSheet();
        sLDetails.addStyleName("framed");
        sLDetails.addTab(UIHelper.wrapWithVertical(listDetails)).setCaption("General");
        sLDetails.addTab(UIHelper.wrapWithVertical(tableEntityNameDetails)).setCaption("NameDetails");
        sLDetails.addTab(UIHelper.wrapWithVertical(tableEntityRelations)).setCaption("Relations");
        sLDetails.setSizeFull();

        addComponent(sLDetails);

        setSizeFull();

    }

    public void updateInfo(final String watchlistName, final WL_Entity entity) {

        if (entity != null) {

            textField_Type.setReadOnly(false);
            textField_Type.setValue(((entity.getType() != null ? entity.getType() : " ") + (entity.getEntryType() != null ? " | " + entity.getEntryType() : " ")));
            textField_Type.setReadOnly(true);

            // for textarea

            // StringBuilder lbs = new StringBuilder();
            // for (String lb : entity.getLegalBasises()) {
            // lbs.append(lb).append(newline);
            // }
            // textPane_LegalBack.setReadOnly(false);
            // textPane_LegalBack.setValue(lbs.toString());
            // textPane_LegalBack.setReadOnly(true);

            // for combobo
            textPane_LegalBack.setReadOnly(false);
            textPane_LegalBack.removeAllItems();
            if (!entity.getLegalBasises().isEmpty()) {
                for (final String lb : entity.getLegalBasises()) {
                    textPane_LegalBack.addItem(lb);
                }
                textPane_LegalBack.select(entity.getLegalBasises().get(0));
                textPane_LegalBack.setNewItemsAllowed(false);
            }
        }
        else {
            textField_Type.setReadOnly(false);
            textPane_LegalBack.setReadOnly(false);

            textField_Type.setValue("");
            textPane_LegalBack.setValue("");

            textField_Type.setReadOnly(true);
            textPane_LegalBack.setReadOnly(true);
        }

        textField_ListDescription.setReadOnly(false);
        textPane_Remark.setReadOnly(false);

        textField_ListDescription.setValue(guiAdapter.getSanctionListDescription(watchlistName));
        textPane_Remark.setValue(((entity != null) && (entity.getComment() != null)) ? entity.getComment() : " ");

        textField_ListDescription.setReadOnly(true);
        textPane_Remark.setReadOnly(true);

        // TODO external links !!

        // btn_ExternalUrl.setEnabled(slhr.getHitExternalUrl() != null);
        // btn_ExternalUrl.setToolTipText(slhr.getHitExternalUrl() != null ? slhr.getHitExternalUrl() : "");
        //
        // btn_ExternalUrl.setEnabled(slhr.getHitExternalUrl() != null);
        // btn_ExternalUrl.setToolTipText(slhr.getHitExternalUrl() != null ? slhr.getHitExternalUrl() : "");

        // details
        if (entity != null) {

            displayNameDetailsService.setModel(guiAdapter.getEntityDetailsNamesTableModel(entity));
            tableEntityNameDetails.setContainerDataSource(new BeanItemContainer<>(DisplayNameDetails.class, displayNameDetailsService.displayAllFields()));

            displayRelationService.setModel(guiAdapter.getEntityRelationsTableModel(watchlistName, entity));
            tableEntityRelations.setContainerDataSource(new BeanItemContainer<>(DisplayRelation.class, displayRelationService.displayAllFields()));

            // tca.adjustColumns();
        }
        else {
            // TODO: reset detailsview !!!!!!!!!
        }

    }

    public Grid getRelationTable() {
        return tableEntityRelations;
    }
}
