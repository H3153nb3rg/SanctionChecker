package at.jps.slcm;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.addon.contextmenu.ContextMenu;
import com.vaadin.addon.contextmenu.ContextMenu.ContextMenuOpenListener;
import com.vaadin.addon.contextmenu.Menu.Command;
import com.vaadin.addon.contextmenu.MenuItem;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.GridContextClickEvent;
import com.vaadin.ui.Grid.SingleSelectionModel;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

import at.jps.sanction.core.EntityManagementConfig;
import at.jps.sanction.domain.SanctionListHitResult;
import at.jps.sanction.model.ProcessStep;
import at.jps.sanction.model.sl.entities.WL_Entity;
import at.jps.sl.gui.AdapterHelper;
import at.jps.sl.gui.util.GUIConfigHolder;
import at.jps.slcm.gui.model.DisplayMessage;
import at.jps.slcm.gui.model.DisplayNameDetails;
import at.jps.slcm.gui.model.DisplayRelation;
import at.jps.slcm.gui.model.DisplayResult;
import at.jps.slcm.gui.model.DisplayWordHit;
import at.jps.slcm.gui.service.DisplayMessageService;
import at.jps.slcm.gui.service.DisplayNameDetailsService;
import at.jps.slcm.gui.service.DisplayRelationService;
import at.jps.slcm.gui.service.DisplayResultService;
import at.jps.slcm.gui.service.DisplayWordHitService;

@SuppressWarnings("serial")
@Theme("slcm")
public class SlcmUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = SlcmUI.class)
    public static class Servlet extends VaadinServlet {
    }

    private Grid                      tableTXWithHits           = new Grid();
    private Grid                      tableResults              = new Grid();
    private Grid                      tableWordHits             = new Grid();
    private Grid                      tableEntityNameDetails    = new Grid();
    private Grid                      tableEntityRelations      = new Grid();

    private TextField                 textField_AnalysisTime;
    private TextArea                  textPane_Comment;
    private ComboBox                  textPane_LegalBack;
    private TextField                 textField_Type;
    private TextField                 textField_ListDescription;
    private TextArea                  textPane_Remark;

    private ComboBox                  comboBox_Category;

    private static AdapterHelper      guiAdapter                = new AdapterHelper();

    private static boolean            initialized               = false;

    public static String              newline                   = System.getProperty("line.separator");
    // -----------------------------

    private DisplayResultService      displayResultService      = new DisplayResultService();
    private DisplayMessageService     displayMessageService     = new DisplayMessageService();
    private DisplayNameDetailsService displayNameDetailsService = new DisplayNameDetailsService();
    private DisplayWordHitService     displayWordHitService     = new DisplayWordHitService();
    private DisplayRelationService    displayRelationService    = new DisplayRelationService();

    private String                    selectedToken             = "";
    private String                    selectedFieldContent      = "";

    @Override
    protected void init(VaadinRequest request) {

        if (!initialized) {

            // if (args.length < 0) {
            // configFilename = args[0];
            // try {
            // context = new FileSystemXmlApplicationContext(configFilename);
            // initialized = true;
            // }
            // catch (Exception x) {
            // x.printStackTrace();
            // System.out.println(x.toString());
            // }
            // }
            if (!initialized) {
                ApplicationContext context = new ClassPathXmlApplicationContext("SanctionChecker.xml");
                initialized = true;

                // important to do this here !!
                final EntityManagementConfig emmCfg = (EntityManagementConfig) context.getBean("EntityManagement");

                GUIConfigHolder config = (GUIConfigHolder) context.getBean("GUIConfig");

                guiAdapter.initialize(config);  // TODO: here ??!

            }
        }

        buildLayout();
        configureComponents();

        doNextMessage(true);
    }

    private void configureComponents() {
        /*
         * Synchronous event handling. Receive user interaction events on the server-side. This allows you to synchronously handle those events. Vaadin automatically sends only the needed changes to
         * the web page without loading a new page.
         */
        tableTXWithHits.setContainerDataSource(new BeanItemContainer<>(DisplayMessage.class));
        tableTXWithHits.setColumnOrder("field", "hit", "content");
        tableTXWithHits.removeColumn("id");

        tableTXWithHits.setSelectionMode(Grid.SelectionMode.SINGLE);

        // for swift
        int cratioTX[] = { 1, 1, 4 };
        int i = 0;
        for (Column c : tableTXWithHits.getColumns()) {
            if (i < cratioTX.length - 1) {
                c.setExpandRatio(cratioTX[i]);
            }
            i++;
            c.setSortable(false);
        }
        // contactList.addSelectionListener(e
        // -> contactForm.edit((Contact) contactList.getSelectedRow()));

        tableResults.setContainerDataSource(new BeanItemContainer<>(DisplayResult.class));
        tableResults.setColumnOrder("watchList", "wlid", "field", "value", "descr");
        tableResults.removeColumn("id");
        tableResults.setSelectionMode(Grid.SelectionMode.SINGLE);

        int cratioR[] = { 1, 1, 1, 1, 3 };
        for (Column c : tableResults.getColumns()) {
            if (i < cratioR.length - 1) {
                c.setExpandRatio(cratioR[i]);
            }
            i++;
        }

        tableEntityNameDetails.setContainerDataSource(new BeanItemContainer<>(DisplayNameDetails.class));
        tableEntityNameDetails.setColumnOrder("firstname", "middleName", "surname", "wholename", "aka");
        tableEntityNameDetails.removeColumn("id");
        tableEntityNameDetails.setSelectionMode(Grid.SelectionMode.SINGLE);
        // tableNameDetails.addActionHandler(new SpreadsheetDefaultActionHandler());

        int cratioND[] = { 1, 1, 2, 3, 1 };
        for (Column c : tableEntityNameDetails.getColumns()) {
            if (i < cratioND.length - 1) {
                c.setExpandRatio(cratioND[i]);
            }
            i++;
        }

        tableEntityRelations.setContainerDataSource(new BeanItemContainer<>(DisplayNameDetails.class));
        tableEntityRelations.setColumnOrder("relationship", "entity", "type");
        tableEntityRelations.removeColumn("id");
        tableEntityRelations.setSelectionMode(Grid.SelectionMode.SINGLE);
        // tableNameDetails.addActionHandler(new SpreadsheetDefaultActionHandler());

        int cratioER[] = { 1, 2, 1 };
        for (Column c : tableEntityRelations.getColumns()) {
            if (i < cratioER.length - 1) {
                c.setExpandRatio(cratioER[i]);
            }
            i++;
        }

        tableWordHits.setContainerDataSource(new BeanItemContainer<>(DisplayWordHit.class));
        tableWordHits.setColumnOrder("watchlist", "slid", "listentry", "field", "content", "value");
        tableWordHits.removeColumn("id");
        tableWordHits.setSelectionMode(Grid.SelectionMode.SINGLE);

        tableTXWithHits.setSizeFull();
        tableResults.setSizeFull();
        tableWordHits.setSizeFull();
        tableEntityNameDetails.setSizeFull();
        tableEntityRelations.setSizeFull();
        // special popup
        addWordHitPopup();

    }

    private void addWordHitPopup() {

        // add contextmenu
        final ContextMenu tableWordHitsContextMenu = new ContextMenu(this, false);

        tableWordHitsContextMenu.setAsContextMenuOf(tableWordHits);

        tableWordHitsContextMenu.addContextMenuOpenListener(new ContextMenuOpenListener() {

            @Override
            public void onContextMenuOpen(ContextMenuOpenEvent event) {

                GridContextClickEvent gridE = (GridContextClickEvent) event.getContextClickEvent();

                // Object itemId = tableNameDetails.getContainerDataSource().addItem();

                DisplayWordHit wordHit = ((DisplayWordHit) tableWordHits.getSelectedRow());

                if (wordHit != null) {

                    selectedToken = wordHit.getListentry();
                    selectedFieldContent = wordHit.getContent();

                    // Item item = tableNameDetails.getContainerDataSource().getItem(itemId);
                    // item.getItemProperty("Section").setValue(String.valueOf(gridE.getSection()));
                    // Object propertyId = gridE.getPropertyId();
                    //
                    // item.getItemProperty("Column").setValue(propertyId != null ? propertyId.toString() : "??");
                    // int rowIndex = gridE.getRowIndex();
                    // item.getItemProperty("Row").setValue(rowIndex < 0 ? "Outside rows" : String.valueOf(rowIndex));

                    tableWordHitsContextMenu.removeItems();

                    tableWordHitsContextMenu.addItem("Called from section " + gridE.getSection().toString() + " on row " + gridE.getRowIndex(), new Command() {
                        @Override
                        public void menuSelected(MenuItem selectedItem) {
                            Notification.show("did something");

                        }
                    });

                    tableWordHitsContextMenu.addItem("add '" + wordHit.getListentry() + "' to Stopwords", new Command() {
                        @Override
                        public void menuSelected(MenuItem selectedItem) {
                            Notification.show("added to Stopwords");

                        }
                    });
                    tableWordHitsContextMenu.addItem("add '" + wordHit.getListentry() + "' to phrase only", new Command() {
                        @Override
                        public void menuSelected(MenuItem selectedItem) {
                            Notification.show("added to phrase only");

                        }
                    }).setVisible(true);
                    tableWordHitsContextMenu.addItem("add '" + wordHit.getListentry() + "' to Index Exclusion", new Command() {
                        @Override
                        public void menuSelected(MenuItem selectedItem) {
                            Notification.show("added to Index Exclusion");

                        }
                    });
                    boolean tokensAreGleich = wordHit.getListentry().equals(wordHit.getContent());
                    if (!tokensAreGleich) {

                        tableWordHitsContextMenu.addItem("'" + wordHit.getListentry() + "' does NEVER match '" + wordHit.getContent() + "'", new Command() {
                            @Override
                            public void menuSelected(MenuItem selectedItem) {
                                Notification.show("added to no hit");

                            }
                        });
                    }
                }
            }
        });

    }

    // void refreshMessageView() {
    // refreshFields("");
    // }

    private void checkAndGetNextMessage(final boolean next) {
        // boolean exists = false;

        // SwingUtilities.invokeLater(new Runnable() {
        // @Override
        // public void run() {
        // get it from a queue ( what else)....

        // HITS
        // if (hitsViewActive) {

        if ((next && guiAdapter.getHitBuffer().hasNextMessage()) || (!next && guiAdapter.getHitBuffer().hasPrevMessage())) {
            guiAdapter.setCurrentMessage((next ? guiAdapter.getHitBuffer().getNextMessage() : guiAdapter.getHitBuffer().getPrevMessage()));

            if (guiAdapter.getCurrentMessage() != null) {
                // exists = true;
                displayResultService.setModel(guiAdapter.getAnalysisResultTableModel(guiAdapter.getCurrentMessage()));
                displayMessageService.setModel(guiAdapter.getMessageTableModel(guiAdapter.getCurrentMessage().getMessage()));
                displayWordHitService.setModel(guiAdapter.getAnalysisWordListTableModel(guiAdapter.getCurrentMessage()));

                // final int columnWidthMsg[] = { 50, 50, 50, 90 };
                //
                // for (int i = 0; i < 2; i++) {
                // final TableColumn column = tableTXHits.getColumnModel().getColumn(i);
                // column.setMinWidth(columnWidthMsg[i]);
                // column.setMaxWidth(columnWidthMsg[i]);
                // column.setPreferredWidth(columnWidthMsg[i]);
                // }
                //
                // final int columnWidthHits[] = { 100, 130, 50, 120, 200 };
                //
                // for (int i = 0; i < columnWidthHits.length; i++) {
                // final TableColumn column = tableResults.getColumnModel().getColumn(i);
                // column.setMinWidth(columnWidthHits[i]);
                // column.setMaxWidth(columnWidthHits[i]);
                // column.setPreferredWidth(columnWidthHits[i]);
                // }
                //
                // final int columnWidthWordHits[] = { 100, 130, 150, 90 };
                //
                // for (int i = 0; i < columnWidthWordHits.length; i++) {
                // final TableColumn column = tableWordHits.getColumnModel().getColumn(i);
                // column.setMinWidth(columnWidthWordHits[i]);
                // column.setMaxWidth(columnWidthWordHits[i]);
                // column.setPreferredWidth(columnWidthWordHits[i]);
                // }

                // settime

                // textField_AnalysisTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(guiAdapter.getCurrentMessage().getAnalysisStopTime())));

                // hitList.scrollTo(0);
                // tableResults.setRowSelectionInterval(0, 0);
            }
        }
    }
    // else { // NOHITS
    // if ((next && guiAdapter.getNoHitBuffer().hasNextMessage()) || (!next && guiAdapter.getNoHitBuffer().hasPrevMessage())) {
    //
    // guiAdapter.setCurrentMessage((next ? guiAdapter.getNoHitBuffer().getNextMessage() : guiAdapter.getNoHitBuffer().getPrevMessage()));
    //
    // if (guiAdapter.getCurrentMessage() != null) {
    // // exists = true;
    // tableTXNoHits.setModel(guiAdapter.getMessageTableModel(guiAdapter.getCurrentMessage().getMessage()));
    //
    // //
    // // Configures table's column width.
    // //
    //
    // for (int i = 0; i < 2; i++) {
    // final TableColumn column = tableTXNoHits.getColumnModel().getColumn(i);
    // column.setMinWidth(50);
    // column.setMaxWidth(50);
    // column.setPreferredWidth(50);
    // }
    // }
    // }
    // }
    // }
    // });

    // return exists;

    // }

    private void doNextMessage(boolean next) {

        checkAndGetNextMessage(next);

        if (guiAdapter.getCurrentMessage() != null) {

            tableResults.setContainerDataSource(new BeanItemContainer<>(DisplayResult.class, displayResultService.displayAllFields()));

            BeanContainer<Long, DisplayMessage> bc = new BeanContainer<Long, DisplayMessage>(DisplayMessage.class);
            bc.setBeanIdProperty("id");
            bc.addAll(displayMessageService.displayAllFields());

            tableTXWithHits.setContainerDataSource(bc);

            tableWordHits.setContainerDataSource(new BeanItemContainer<>(DisplayWordHit.class, displayWordHitService.displayAllFields()));

            // detailfields
            // displayNameDetailService.setModel(tableModel2);

            updateMessageDetails();
        }
    }

    private void updateMessageDetails() {

        textField_AnalysisTime.setReadOnly(false);
        textField_AnalysisTime.setValue(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(guiAdapter.getCurrentMessage().getAnalysisStopTime())));
        textField_AnalysisTime.setReadOnly(true);

        textPane_Comment.setValue(guiAdapter.getCurrentMessage().getComment());
        if (guiAdapter.getCurrentMessage().getCategory() != null) {
            comboBox_Category.setValue(guiAdapter.getCurrentMessage().getCategory());
        }

    }

    private void updateSanctionDetails(final int rowId) {
        // TODO: gather SactionInfos

        guiAdapter.setFocussedHitResult(guiAdapter.getCurrentMessage().getHitList().get((guiAdapter.getCurrentMessage().getHitList().size() - 1) - rowId));

        if (guiAdapter.getFocussedHitResult() instanceof SanctionListHitResult) {

            SanctionListHitResult slhr = (SanctionListHitResult) guiAdapter.getFocussedHitResult();

            WL_Entity entity = guiAdapter.getSanctionListEntityDetails(slhr.getHitListName(), slhr.getHitId());

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
                    for (String lb : entity.getLegalBasises()) {
                        textPane_LegalBack.addItem(lb);
                    }
                    textPane_LegalBack.select(entity.getLegalBasises().get(0));
                    textPane_LegalBack.setNewItemsAllowed(false);
                }
            }
            else {
                textField_Type.setReadOnly(false);
                textPane_LegalBack.setReadOnly(false);

                textField_Type.setValue(slhr.getEntityType());
                textPane_LegalBack.setValue(slhr.getHitLegalBasis());

                textField_Type.setReadOnly(true);
                textPane_LegalBack.setReadOnly(true);
            }

            textField_ListDescription.setReadOnly(false);
            textPane_Remark.setReadOnly(false);

            textField_ListDescription.setValue(guiAdapter.getSanctionListDescription(slhr.getHitListName()));
            textPane_Remark.setValue(slhr.getHitRemark() != null ? slhr.getHitRemark() : " ");

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

                displayRelationService.setModel(guiAdapter.getEntityRelationsTableModel(slhr));
                tableEntityRelations.setContainerDataSource(new BeanItemContainer<>(DisplayRelation.class, displayRelationService.displayAllFields()));

                // tca.adjustColumns();
            }

        }
        else {
            // TODO: other dingbats

            // btn_ExternalUrl.setEnabled(false);
        }
    }

    /*
     * Robust layouts. Layouts are components that contain other components. HorizontalLayout contains TextField and Button. It is wrapped with a Grid into VerticalLayout for the left side of the
     * screen. Allow user to resize the components with a SplitPanel. In addition to programmatically building layout in Java, you may also choose to setup layout declaratively with Vaadin Designer,
     * CSS and HTML.
     */
    private void buildLayout() {

        Button button2 = new Button("postpone");
        button2.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {

                // The quickest way to confirm
                ConfirmDialog.show(getMainWindow(), "Postpone Decission", new ConfirmDialog.Listener() {

                    public void onClose(ConfirmDialog dialog) {
                        if (dialog.isConfirmed()) {

                            // Confirmed to continue
                            doProcessNoHit();
                        }
                        else {
                            // User did not confirm
                            // feedback(dialog.isConfirmed());
                            // layout.addComponent(new Label("not confirmed!!"));
                        }
                    }
                });

            }
        });
        Button button3 = new Button("No Hit");
        button3.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {

                // The quickest way to confirm
                ConfirmDialog.show(getMainWindow(), "Confirm NoHit", new ConfirmDialog.Listener() {

                    public void onClose(ConfirmDialog dialog) {
                        if (dialog.isConfirmed()) {

                            // Confirmed to continue
                            // feedback(dialog.isConfirmed());
                            // layout.addComponent(new Label("confirmed!!"));
                        }
                        else {
                            // User did not confirm
                            // feedback(dialog.isConfirmed());
                            // layout.addComponent(new Label("not confirmed!!"));
                        }
                    }
                });

            }
        });
        Button button4 = new Button("Hit");
        button4.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {

                // The quickest way to confirm
                ConfirmDialog.show(getMainWindow(), "Confirm Hit", new ConfirmDialog.Listener() {

                    public void onClose(ConfirmDialog dialog) {
                        if (dialog.isConfirmed()) {

                            // Confirmed to continue
                            // feedback(dialog.isConfirmed());
                            // layout.addComponent(new Label("confirmed!!"));

                            doProcessHit();

                        }
                        else {
                            // User did not confirm
                            // feedback(dialog.isConfirmed());
                            // layout.addComponent(new Label("not confirmed!!"));
                        }
                    }
                });

            }
        });

        Button button5 = new Button("Previous");
        button5.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {

                doNextMessage(false);

            }
        });
        Button button6 = new Button("Next");
        button6.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {

                doNextMessage(true);

            }
        });

        tableResults.addSelectionListener(new SelectionListener() {

            @Override
            public void select(SelectionEvent event) {

                DisplayResult displayResult = (DisplayResult) tableResults.getSelectedRow();
                if (displayResult != null) {
                    // TODO: debug only
                    Notification.show("selected: " + displayResult.getField());
                    // messageList.scrollTo(hitList.getSelectedRow());

                    String field = ((DisplayResult) tableResults.getSelectedRow()).getField();

                    int txHitTableRow = guiAdapter.getHitFieldRows().get(field);

                    SingleSelectionModel m = (SingleSelectionModel) tableTXWithHits.getSelectionModel();
                    m.select(tableTXWithHits.getContainerDataSource().getIdByIndex(txHitTableRow));

                    // SingleSelectionModel m = (SingleSelectionModel) tableTXHits.getSelectionModel();
                    // m.select(((DisplayResult) tableResults.getSelectedRow()).getId());

                    // tableTXWithHits.scrollTo(((DisplayResult) tableResults.getSelectedRow()).getId());

                    // TODO: is this needed --> tableTXWithHits.scrollTo(

                    updateSanctionDetails(displayResult.getId().intValue());

                }
                else {
                    Notification.show("selected: BUG -> no Result found!");
                }

            }
        });

        HorizontalLayout actions = new HorizontalLayout(button2, button3, button4, button5, button6);
        actions.setWidth("80%");
        actions.setMargin(true);
        // actions.setSizeFull();

        // button.setWidth("100%");
        // actions.setExpandRatio(hitList, 1);

        // VerticalLayout left = new VerticalLayout(actions, messageList);
        // left.setSizeFull();
        // messageList.setSizeFull();
        // left.setExpandRatio(messageList, 1);

        // form
        VerticalLayout txDetails = new VerticalLayout();
        txDetails.setSpacing(true);

        textField_AnalysisTime = new TextField("TX Analysis Time");
        textField_AnalysisTime.setWidth("100%");
        textField_AnalysisTime.setReadOnly(true);
        txDetails.addComponent(textField_AnalysisTime);

        comboBox_Category = new ComboBox("Category");
        comboBox_Category.setInvalidAllowed(false);
        comboBox_Category.setNullSelectionAllowed(false);
        comboBox_Category.addItems("Iran", "Syrien", "InternalUse", "WoswasI");
        comboBox_Category.setWidth("100%");
        txDetails.addComponent(comboBox_Category);

        textPane_Comment = new TextArea("Comment");
        textPane_Comment.setSizeFull();
        txDetails.addComponent(textPane_Comment);

        txDetails.setSizeFull();

        // left side

        VerticalSplitPanel listEntryAndDetails = new VerticalSplitPanel();

        // Put other components in the panel
        tableTXWithHits.setSizeFull();
        listEntryAndDetails.setFirstComponent(tableTXWithHits);
        listEntryAndDetails.setSecondComponent(txDetails);
        listEntryAndDetails.setSplitPosition(75, Unit.PERCENTAGE);
        listEntryAndDetails.setSizeFull();

        // VerticalLayout listEntryAndDetails = new VerticalLayout(tableTXWithHits, txDetails);
        // listEntryAndDetails.setExpandRatio(tableTXWithHits, 8);
        // listEntryAndDetails.setExpandRatio(txDetails, 2);

        TabSheet tabsheetTX = new TabSheet();
        tabsheetTX.addTab(listEntryAndDetails).setCaption("Transaction");
        tabsheetTX.setSizeFull();

        // right side
        TabSheet tabsheetHits = new TabSheet();

        // form
        VerticalLayout listDetails = new VerticalLayout();

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
        textPane_Remark.setRows(2);
        listDetails.addComponent(textPane_Remark);

        listDetails.setSizeFull();

        TabSheet sLDetails = new TabSheet();
        sLDetails.addTab(listDetails).setCaption("General");
        sLDetails.addTab(tableEntityNameDetails).setCaption("NameDetails");
        sLDetails.addTab(tableEntityRelations).setCaption("Relations");
        sLDetails.setSizeFull();

        VerticalSplitPanel hitsAndDetails = new VerticalSplitPanel();

        // Put other components in the panel
        hitsAndDetails.setFirstComponent(tableResults);
        hitsAndDetails.setSecondComponent(sLDetails);
        hitsAndDetails.setSplitPosition(60, Unit.PERCENTAGE);

        // VerticalLayout hitsAndDetails = new VerticalLayout(tableResults, listDetails);
        // hitsAndDetails.setExpandRatio(tableResults, 9);
        // hitsAndDetails.setExpandRatio(listDetails, 1);
        hitsAndDetails.setSizeFull();

        tabsheetHits.addTab(hitsAndDetails).setCaption("List Hits");
        tabsheetHits.addTab(tableWordHits).setCaption("Word Hits");
        tabsheetHits.setSizeFull();

        VerticalLayout vll = new VerticalLayout(tabsheetTX);
        VerticalLayout vlr = new VerticalLayout(tabsheetHits);
        vll.setMargin(true);
        vll.setSizeFull();
        vlr.setMargin(true);
        vlr.setSizeFull();

        HorizontalSplitPanel grids = new HorizontalSplitPanel();
        grids.setFirstComponent(vll);
        grids.setSecondComponent(vlr);
        grids.setSplitPosition(50, Unit.PERCENTAGE);

        // HorizontalLayout grids = new HorizontalLayout(tabsheetTX, tabsheetHits);
        // grids.setWidth("100%");
        // tabsheetTX.setWidth("95%");
        // grids.setExpandRatio(tabsheetTX, 1);
        // // messageList.setSelectionMode(SelectionMode.NONE);
        // grids.setExpandRatio(tabsheetHits, 1);
        tableResults.setColumnReorderingAllowed(true);
        grids.setSizeFull();

        // grids.setMargin(true);

        VerticalLayout page = new VerticalLayout(grids, actions);
        page.setExpandRatio(grids, 9);
        page.setExpandRatio(actions, 1);
        page.setSizeFull();
        // page.setMargin(true);

        // left.setExpandRatio(messageList, 1);

        // HorizontalLayout mainLayout = new HorizontalLayout(left, hitList);
        // mainLayout.setSizeFull();
        // mainLayout.setExpandRatio(left, 1);

        // Split and allow resizing
        setContent(page);
    }

    UI getMainWindow() {
        return this;
    }

    private void feedback(boolean confirmed) {
        Notification.show("Confirmed:" + confirmed);
    }

    // ----------------------------------

    private void addProcessStep(final String text) {
        ProcessStep processStep = new ProcessStep();
        processStep.setRemark(text);
        guiAdapter.getCurrentMessage().addProcessStep(processStep);

    }

    private void doProcessHit() {
        guiAdapter.getCurrentMessage().setComment(textPane_Comment.getValue());

        if (guiAdapter.getConfig().isAutolearnMode()) {

            guiAdapter.addToPostProcessHitQueue(guiAdapter.getCurrentMessage());
            addProcessStep("Autolearned Hit");
        }

        addProcessStep("Confirmed Hit");
        guiAdapter.addToFinalHitQueue(guiAdapter.getCurrentMessage());

        doNextMessage(true);

    }

    private void doProcessNoHit() {
        guiAdapter.getCurrentMessage().setComment(textPane_Comment.getValue());

        if (guiAdapter.getConfig().isAutolearnMode()) {
            guiAdapter.addToPostProcessNoHitQueue(guiAdapter.getCurrentMessage());
            addProcessStep("Autolearned Miss");
        }

        addProcessStep("Confirmed Miss");

        guiAdapter.addToFinalNoHitQueue(guiAdapter.getCurrentMessage());

        doNextMessage(true);
    }

    private void doPostpone() {
        guiAdapter.getCurrentMessage().setComment(textPane_Comment.getValue());

        addProcessStep("Backlogged");

        guiAdapter.addToBacklogQueue(guiAdapter.getCurrentMessage());
    }

    void doAddTokenToStopword() {
        if ((selectedToken != null) && (selectedToken.length() > 1)) guiAdapter.addStopWord(selectedToken);
    }

    void doAddTokenToNSWH() {
        if ((selectedToken != null) && (selectedToken.length() > 1)) guiAdapter.addNSWH(selectedToken);
    }

    void doAddTokenToIA() {
        if ((selectedToken != null) && (selectedToken.length() > 1)) guiAdapter.addIA(selectedToken);
    }

    void doAddTokenToNoHit() {
        if (((selectedToken != null) && (selectedToken.length() > 1)) && ((selectedFieldContent != null) && (selectedFieldContent.length() > 1)))

            guiAdapter.addNoHit(selectedFieldContent, selectedToken);
    }

}
