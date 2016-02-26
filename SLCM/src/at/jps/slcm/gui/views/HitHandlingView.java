package at.jps.slcm.gui.views;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.vaadin.cssinject.CSSInject;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.addon.contextmenu.ContextMenu;
import com.vaadin.addon.contextmenu.ContextMenu.ContextMenuOpenListener;
import com.vaadin.addon.contextmenu.Menu.Command;
import com.vaadin.addon.contextmenu.MenuItem;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.CellReference;
import com.vaadin.ui.Grid.CellStyleGenerator;
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

import at.jps.sanction.domain.payment.PaymentHitResult;
import at.jps.sanction.model.ProcessStep;
import at.jps.sanction.model.wl.entities.WL_Entity;
import at.jps.sl.gui.AdapterHelper;
import at.jps.slcm.gui.components.SanctionListDetails;
import at.jps.slcm.gui.components.UIHelper;
import at.jps.slcm.gui.models.DisplayMessage;
import at.jps.slcm.gui.models.DisplayResult;
import at.jps.slcm.gui.models.DisplayWordHit;
import at.jps.slcm.gui.services.DisplayMessageService;
import at.jps.slcm.gui.services.DisplayResultService;
import at.jps.slcm.gui.services.DisplayWordHitService;

public class HitHandlingView extends VerticalLayout implements View {

    /**
     *
     */
    private static final long           serialVersionUID      = -7550009752493474506L;

    final public static String          ViewName              = "HitHandling";

    private final Grid                  tableTXWithHits       = new Grid();
    private final Grid                  tableResults          = new Grid();
    private final Grid                  tableWordHits         = new Grid();

    // casemanagement
    private TextField                   textField_AnalysisTime;
    private TextArea                    textPane_Comment;
    private ComboBox                    comboBox_Category;

    private final AdapterHelper         guiAdapter;

    private SanctionListDetails         sanctionListDetails;

    public static String                newline               = System.getProperty("line.separator");
    // -----------------------------

    private final DisplayResultService  displayResultService  = new DisplayResultService();
    private final DisplayMessageService displayMessageService = new DisplayMessageService();
    private final DisplayWordHitService displayWordHitService = new DisplayWordHitService();

    private String                      selectedToken         = "";
    private String                      selectedFieldContent  = "";

    public HitHandlingView(final AdapterHelper guiAdapter) {
        super();
        this.guiAdapter = guiAdapter;

        configureComponents();

        buildLayout();
    }

    @Override
    public void enter(final ViewChangeEvent event) {
        Notification.show("Work on!");

    }

    private void configureComponents() {

        sanctionListDetails = new SanctionListDetails(guiAdapter);

        // loadColors
        final CSSInject css = new CSSInject(UI.getCurrent());
        String csss = "";
        for (final String name : guiAdapter.getFieldColors().keySet()) {
            // csss += "$v-grid-cell." + name + " { background-color: " + guiAdapter.getFieldColorsAsHex().get(name) + ";} ";
            csss += "." + name + " { background-color: " + guiAdapter.getFieldColorsAsHex().get(name) + ";} ";
        }

        css.setStyles(csss);

        /*
         * Synchronous event handling. Receive user interaction events on the server-side. This allows you to synchronously handle those events. Vaadin automatically sends only the needed changes to
         * the web page without loading a new page.
         */
        tableTXWithHits.setContainerDataSource(new BeanItemContainer<>(DisplayMessage.class));
        tableTXWithHits.setColumnOrder("field", "hit", "content");
        tableTXWithHits.getColumn("field").setHeaderCaption("Field");
        tableTXWithHits.getColumn("hit").setHeaderCaption("Hit");
        tableTXWithHits.getColumn("content").setHeaderCaption("Content");
        tableTXWithHits.removeColumn("id");

        tableTXWithHits.setSelectionMode(Grid.SelectionMode.SINGLE);

        // for swift
        final int cratioTX[] = { 1, 1, 4 };
        int i = 0;
        for (final Column c : tableTXWithHits.getColumns()) {
            if (i < (cratioTX.length - 1)) {
                c.setExpandRatio(cratioTX[i]);
            }
            i++;
            c.setSortable(false);
        }
        // contactList.addSelectionListener(e
        // -> contactForm.edit((Contact) contactList.getSelectedRow()));

        tableResults.setContainerDataSource(new BeanItemContainer<>(DisplayResult.class));
        tableResults.setColumnOrder("watchList", "wlid", "field", "value", "descr");
        tableResults.getColumn("watchList").setHeaderCaption("Watchlist");

        tableResults.getColumn("wlid").setHeaderCaption("WL ID");
        tableResults.getColumn("field").setHeaderCaption("Field");
        tableResults.getColumn("value").setHeaderCaption("Content");
        tableResults.getColumn("descr").setHeaderCaption("Description");

        tableResults.removeColumn("id");
        tableResults.setSelectionMode(Grid.SelectionMode.SINGLE);

        final int cratioR[] = { 1, 1, 1, 1, 3 };
        i = 0;
        for (final Column c : tableResults.getColumns()) {
            if (i < (cratioR.length - 1)) {
                c.setExpandRatio(cratioR[i]);
            }
            i++;
        }

        // Cell Styler
        tableTXWithHits.setCellStyleGenerator(new CellStyleGenerator() {

            /**
             *
             */
            private static final long serialVersionUID = -7294175510804288222L;

            @Override
            public String getStyle(final CellReference cell) {
                String stylename = null;
                if ((Long) cell.getItemId() > 0) {
                    @SuppressWarnings("unchecked")
                    final BeanItem<DisplayMessage> beanItem = (BeanItem<DisplayMessage>) cell.getItem();
                    stylename = beanItem.getBean().getHit();
                }
                // String stylename = (cell.getItemId())).getHit();
                return stylename;
            }
        });

        tableWordHits.setContainerDataSource(new BeanItemContainer<>(DisplayWordHit.class));
        tableWordHits.setColumnOrder("watchlist", "slid", "listentry", "field", "content", "value");
        tableWordHits.removeColumn("id");
        tableWordHits.setSelectionMode(Grid.SelectionMode.SINGLE);

        tableTXWithHits.setSizeFull();
        tableResults.setSizeFull();
        tableWordHits.setSizeFull();

        // special popup
        addWordHitPopup();

    }

    private void addWordHitPopup() {

        // add contextmenu
        final ContextMenu tableWordHitsContextMenu = new ContextMenu(this, false);

        tableWordHitsContextMenu.setAsContextMenuOf(tableWordHits);

        tableWordHitsContextMenu.addContextMenuOpenListener(new ContextMenuOpenListener() {

            @Override
            public void onContextMenuOpen(final ContextMenuOpenEvent event) {

                final GridContextClickEvent gridE = (GridContextClickEvent) event.getContextClickEvent();

                // Object itemId = tableNameDetails.getContainerDataSource().addItem();

                final DisplayWordHit wordHit = ((DisplayWordHit) tableWordHits.getSelectedRow());

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
                        /**
                         *
                         */
                        private static final long serialVersionUID = -118297987496622159L;

                        @Override
                        public void menuSelected(final MenuItem selectedItem) {
                            Notification.show("did something");
                        }
                    });

                    tableWordHitsContextMenu.addItem("add '" + wordHit.getListentry() + "' to Stopwords", new Command() {
                        /**
                         *
                         */
                        private static final long serialVersionUID = -7313734853304935326L;

                        @Override
                        public void menuSelected(final MenuItem selectedItem) {
                            Notification.show("added to Stopwords");
                        }
                    });
                    tableWordHitsContextMenu.addItem("add '" + wordHit.getListentry() + "' to phrase only", new Command() {
                        /**
                         *
                         */
                        private static final long serialVersionUID = 5797667827963150792L;

                        @Override
                        public void menuSelected(final MenuItem selectedItem) {
                            Notification.show("added to phrase only");
                        }
                    }).setVisible(true);
                    tableWordHitsContextMenu.addItem("add '" + wordHit.getListentry() + "' to Index Exclusion", new Command() {
                        /**
                         *
                         */
                        private static final long serialVersionUID = 5630645302830693827L;

                        @Override
                        public void menuSelected(final MenuItem selectedItem) {
                            Notification.show("added to Index Exclusion");
                        }
                    });
                    final boolean tokensAreGleich = wordHit.getListentry().equals(wordHit.getContent());
                    if (!tokensAreGleich) {

                        tableWordHitsContextMenu.addItem("'" + wordHit.getListentry() + "' does NEVER match '" + wordHit.getContent() + "'", new Command() {
                            /**
                             *
                             */
                            private static final long serialVersionUID = 6241022500987043160L;

                            @Override
                            public void menuSelected(final MenuItem selectedItem) {
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

    private void doNextMessage(final boolean next) {

        checkAndGetNextMessage(next);

        if (guiAdapter.getCurrentMessage() != null) {

            tableResults.setContainerDataSource(new BeanItemContainer<>(DisplayResult.class, displayResultService.displayAllFields()));

            final BeanContainer<Long, DisplayMessage> bc = new BeanContainer<Long, DisplayMessage>(DisplayMessage.class);
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

        if (guiAdapter.getFocussedHitResult() instanceof PaymentHitResult) {

            final PaymentHitResult slhr = (PaymentHitResult) guiAdapter.getFocussedHitResult();

            final WL_Entity entity = guiAdapter.getSanctionListEntityDetails(slhr.getHitListName(), slhr.getHitId());

            sanctionListDetails.updateInfo(slhr.getHitListName(), entity);
        }
        else {
            // TODO: other dingbats

            // btn_ExternalUrl.setEnabled(false);
        }
    }

    private void buildLayout() {

        // // Add the loginForm instance below to your UI
        // DefaultVerticalLoginForm loginForm = new DefaultVerticalLoginForm();
        // loginForm.addLoginListener(new LoginListener() {
        // @Override
        // public void onLogin(LoginEvent event) {
        // System.err.println("Logged in with user name " + event.getUserName() + " and password of length " + event.getPassword().length());
        //
        // loggedInUser = event.getUserName();
        // }
        // });

        final Button button2 = new Button("postpone");
        button2.addClickListener(new Button.ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = 3734735406147554187L;

            @Override
            public void buttonClick(final ClickEvent event) {

                // The quickest way to confirm
                ConfirmDialog.show(UI.getCurrent(), "Postpone Decision", new ConfirmDialog.Listener() {

                    /**
                     *
                     */
                    private static final long serialVersionUID = 853673169272603L;

                    @Override
                    public void onClose(final ConfirmDialog dialog) {
                        if (dialog.isConfirmed()) {

                            // Confirmed to continue
                            doPostpone();
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
        final Button button3 = new Button("No Hit");
        button3.addClickListener(new Button.ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = -3173345285232527353L;

            @Override
            public void buttonClick(final ClickEvent event) {

                // The quickest way to confirm
                ConfirmDialog.show(UI.getCurrent(), "Confirm NoHit", new ConfirmDialog.Listener() {

                    /**
                     *
                     */
                    private static final long serialVersionUID = 1001564814192572881L;

                    @Override
                    public void onClose(final ConfirmDialog dialog) {
                        if (dialog.isConfirmed()) {

                            // Confirmed to continue
                            // feedback(dialog.isConfirmed());
                            // layout.addComponent(new Label("confirmed!!"));

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
        final Button button4 = new Button("Hit");
        button4.addClickListener(new Button.ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = 5263732114286684471L;

            @Override
            public void buttonClick(final ClickEvent event) {

                // The quickest way to confirm
                ConfirmDialog.show(UI.getCurrent(), "Confirm Hit", new ConfirmDialog.Listener() {

                    /**
                     *
                     */
                    private static final long serialVersionUID = -6745664926081052916L;

                    @Override
                    public void onClose(final ConfirmDialog dialog) {
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

        final Button button5 = new Button("Previous");
        button5.addClickListener(new Button.ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = -6045113687827684393L;

            @Override
            public void buttonClick(final ClickEvent event) {

                doNextMessage(false);

            }
        });
        final Button button6 = new Button("Next");
        button6.addClickListener(new Button.ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = -2221502598602123148L;

            @Override
            public void buttonClick(final ClickEvent event) {

                doNextMessage(true);

            }
        });

        tableResults.addSelectionListener(new SelectionListener() {

            /**
             *
             */
            private static final long serialVersionUID = 3082372924362828902L;

            @Override
            public void select(final SelectionEvent event) {

                final DisplayResult displayResult = (DisplayResult) tableResults.getSelectedRow();
                if (displayResult != null) {
                    // TODO: debug only
                    Notification.show("selected: " + displayResult.getField());
                    // messageList.scrollTo(hitList.getSelectedRow());

                    final String field = ((DisplayResult) tableResults.getSelectedRow()).getField();

                    final int txHitTableRow = guiAdapter.getHitFieldRows().get(field);

                    final SingleSelectionModel m = (SingleSelectionModel) tableTXWithHits.getSelectionModel();
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

        final HorizontalLayout actions = new HorizontalLayout(button2, button3, button4, button5, button6);
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
        final VerticalLayout txDetails = new VerticalLayout();
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
        txDetails.setExpandRatio(textPane_Comment, 1);
        txDetails.setSizeFull();

        Component component = UIHelper.wrapWithVerticalTopMargin(UIHelper.wrapWithPanel(UIHelper.wrapWithVertical(txDetails), "Transaction Details", FontAwesome.LIST));

        // left side

        final VerticalSplitPanel listEntryAndDetails = new VerticalSplitPanel();

        // Put other components in the panel
        tableTXWithHits.setSizeFull();

        listEntryAndDetails.setFirstComponent(UIHelper.wrapWithVerticalBottomMargin(tableTXWithHits));
        listEntryAndDetails.setSecondComponent(component);
        listEntryAndDetails.setSplitPosition(75, Unit.PERCENTAGE);
        listEntryAndDetails.setSizeFull();

        // VerticalLayout listEntryAndDetails = new VerticalLayout(tableTXWithHits, txDetails);
        // listEntryAndDetails.setExpandRatio(tableTXWithHits, 8);
        // listEntryAndDetails.setExpandRatio(txDetails, 2);

        final TabSheet tabsheetTX = new TabSheet();
        tabsheetTX.addStyleName("framed");
        tabsheetTX.addTab(UIHelper.wrapWithVertical(listEntryAndDetails)).setCaption("Transaction");
        tabsheetTX.setSizeFull();

        // right side
        final TabSheet tabsheetHits = new TabSheet();
        tabsheetHits.addStyleName("framed");
        final VerticalSplitPanel hitsAndDetails = new VerticalSplitPanel();

        // Put other components in the panel

        component = UIHelper.wrapWithVerticalTopMargin(UIHelper.wrapWithPanel(UIHelper.wrapWithVertical(sanctionListDetails), "Watchlist Details", FontAwesome.LIST));

        hitsAndDetails.setFirstComponent(UIHelper.wrapWithVerticalBottomMargin(tableResults));
        hitsAndDetails.setSecondComponent(component);
        hitsAndDetails.setSplitPosition(60, Unit.PERCENTAGE);

        // VerticalLayout hitsAndDetails = new VerticalLayout(tableResults, listDetails);
        // hitsAndDetails.setExpandRatio(tableResults, 9);
        // hitsAndDetails.setExpandRatio(listDetails, 1);
        hitsAndDetails.setSizeFull();

        tabsheetHits.addTab(UIHelper.wrapWithVertical(hitsAndDetails)).setCaption("List Hits");
        tabsheetHits.addTab(UIHelper.wrapWithVertical(tableWordHits)).setCaption("Word Hits");
        tabsheetHits.setSizeFull();

        final VerticalLayout vll = new VerticalLayout(tabsheetTX);
        final VerticalLayout vlr = new VerticalLayout(tabsheetHits);
        // vll.setMargin(true);
        vll.setSizeFull();
        // vlr.setMargin(true);
        vlr.setSizeFull();

        final HorizontalSplitPanel grids = new HorizontalSplitPanel();
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

        // final VerticalLayout page = new VerticalLayout(grids, actions);
        // page.setExpandRatio(grids, 9);
        // page.setExpandRatio(actions, 1);
        // page.setSizeFull();

        addComponent(grids);
        addComponent(actions);

        setExpandRatio(grids, 9);
        setExpandRatio(actions, 1);
        setSizeFull();
        // page.setMargin(true);

        // left.setExpandRatio(messageList, 1);

        // HorizontalLayout mainLayout = new HorizontalLayout(left, hitList);
        // mainLayout.setSizeFull();
        // mainLayout.setExpandRatio(left, 1);

        // Split and allow resizing
        // setContent(page);

    }

    // private void feedback(boolean confirmed) {
    // Notification.show("Confirmed:" + confirmed);
    // }
    //
    // ----------------------------------

    private void addProcessStep(final String text) {
        final ProcessStep processStep = new ProcessStep();
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
        if ((selectedToken != null) && (selectedToken.length() > 1)) {
            guiAdapter.addStopWord(selectedToken);
        }
    }

    void doAddTokenToNSWH() {
        if ((selectedToken != null) && (selectedToken.length() > 1)) {
            guiAdapter.addNSWH(selectedToken);
        }
    }

    void doAddTokenToIA() {
        if ((selectedToken != null) && (selectedToken.length() > 1)) {
            guiAdapter.addIA(selectedToken);
        }
    }

    void doAddTokenToNoHit() {
        if (((selectedToken != null) && (selectedToken.length() > 1)) && ((selectedFieldContent != null) && (selectedFieldContent.length() > 1))) {
            guiAdapter.addNoHit(selectedFieldContent, selectedToken);
        }
    }

}
