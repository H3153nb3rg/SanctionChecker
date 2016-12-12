package at.jps.slcm.gui.views.payment;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.addon.contextmenu.ContextMenu;
import com.vaadin.addon.contextmenu.ContextMenu.ContextMenuOpenListener;
import com.vaadin.addon.contextmenu.Menu.Command;
import com.vaadin.addon.contextmenu.MenuItem;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.CellStyleGenerator;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SingleSelectionModel;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

import at.jps.sanction.domain.SanctionHitResult;
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
import at.jps.slcm.gui.views.HitHandlingView;

public class TransactionHitHandling extends VerticalLayout implements HitHandlingView {

    /**
     *
     */
    private static final long           serialVersionUID      = 2118105388052490865L;

    // casemanagement

    private final Grid                  tableTXWithHits       = new Grid();
    private final Grid                  tableResults          = new Grid();
    private final Grid                  tableWordHits         = new Grid();

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

    public TransactionHitHandling(final AdapterHelper guiAdapter) {
        super();
        this.guiAdapter = guiAdapter;

        configureComponents();

        buildPage();
    }

    private void configureComponents() {

        sanctionListDetails = new SanctionListDetails(guiAdapter);

        // loadColors
        final Styles styles = Page.getCurrent().getStyles();
        String csss = "";
        for (final String name : guiAdapter.getFieldColors().keySet()) {
            // csss += ".v-grid-cell." + name + " { background-color: " + guiAdapter.getFieldColorsAsHex().get(name) + ";} ";
            csss = "td.v-grid-cell.cell-content-" + name + " { background-color:" + guiAdapter.getFieldColorsAsHex().get(name) + "; }"; // .vapp .v-grid-cell-content-
            styles.add(csss);
        }

        // css.setStyles(csss);

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
        tableResults.getColumn("value").setHeaderCaption("Hitratio");
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
            public String getStyle(final Grid.CellReference cell) {
                String stylename = null;

                @SuppressWarnings("unchecked")
                final BeanItem<DisplayMessage> beanItem = (BeanItem<DisplayMessage>) cell.getItem();

                if (beanItem != null) {
                    stylename = "cell-content-" + beanItem.getBean().getHit();
                    if (stylename.equals(" ")) {
                        stylename = null;
                    }
                }

                // if ((Long) cell.getItemId() > 0) {
                // @SuppressWarnings("unchecked")
                // final BeanItem<DisplayMessage> beanItem = (BeanItem<DisplayMessage>) cell.getItem();
                // stylename = beanItem.getBean().getHit();
                // }
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

                event.getContextClickEvent();

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

                    // tableWordHitsContextMenu.addItem("Called from section " + gridE.getSection().toString() + " on row " + gridE.getRowIndex(), new Command() {
                    // /**
                    // *
                    // */
                    // private static final long serialVersionUID = -118297987496622159L;
                    //
                    // @Override
                    // public void menuSelected(final MenuItem selectedItem) {
                    // Notification.show("did something");
                    // }
                    // });

                    tableWordHitsContextMenu.addItem("add '" + wordHit.getListentry() + "' to Stopwords", new Command() {
                        /**
                         *
                         */
                        private static final long serialVersionUID = -7313734853304935326L;

                        @Override
                        public void menuSelected(final MenuItem selectedItem) {
                            doAddTokenToStopword();
                        }
                    });

                    tableWordHitsContextMenu.addItem("add '" + wordHit.getListentry() + "' to phrase only", new Command() {
                        /**
                         *
                         */
                        private static final long serialVersionUID = 5797667827963150792L;

                        @Override
                        public void menuSelected(final MenuItem selectedItem) {
                            // Notification.show("added to phrase only (NYI)");
                            doAddTokenToNSWH();
                        }
                    }).setVisible(true);

                    tableWordHitsContextMenu.addItem("add '" + wordHit.getListentry() + "' to Index Exclusion", new Command() {
                        /**
                         *
                         */
                        private static final long serialVersionUID = 5630645302830693827L;

                        @Override
                        public void menuSelected(final MenuItem selectedItem) {
                            // Notification.show("added to Index Exclusion (NYI)");
                            doAddTokenToIA();
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
                                // Notification.show("added to no hit (NYI)");
                                doAddTokenToNoHit();
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onNextMessage() {

        displayResultService.setModel(guiAdapter.getAnalysisResultTableModel(guiAdapter.getCurrentMessage()));
        displayMessageService.setModel(guiAdapter.getMessageTableModel(guiAdapter.getCurrentMessage().getMessage()));
        displayWordHitService.setModel(guiAdapter.getAnalysisWordListTableModel(guiAdapter.getCurrentMessage()));

        tableResults.setContainerDataSource(new BeanItemContainer<>(DisplayResult.class, displayResultService.displayAllFields()));

        // final BeanContainer<Long, DisplayMessage> bc = new BeanContainer<Long, DisplayMessage>(DisplayMessage.class);
        // bc.setBeanIdProperty("id");
        // bc.addAll(displayMessageService.displayAllFields());

        tableTXWithHits.setContainerDataSource(new BeanItemContainer<>(DisplayMessage.class, displayMessageService.displayAllFields()));

        tableWordHits.setContainerDataSource(new BeanItemContainer<>(DisplayWordHit.class, displayWordHitService.displayAllFields()));

        // detailfields
        // displayNameDetailService.setModel(tableModel2);

        updateMessageDetails();

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
        // TODO: gather SanctionInfos

        guiAdapter.setFocussedHitResult(guiAdapter.getCurrentMessage().getHitList().get((guiAdapter.getCurrentMessage().getHitList().size() - 1) - rowId));

        if (guiAdapter.getFocussedHitResult() instanceof SanctionHitResult) {

            final SanctionHitResult slhr = (SanctionHitResult) guiAdapter.getFocussedHitResult();

            final WL_Entity entity = guiAdapter.getSanctionListEntityDetails(slhr.getHitListName(), slhr.getHitId());

            sanctionListDetails.updateInfo(slhr.getHitListName(), entity);
        }
        else {
            // TODO: other dingbats

            // btn_ExternalUrl.setEnabled(false);
        }
    }

    @Override
    public Component buildPage() {

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
                    // Notification.show("selected: " + displayResult.getField());
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

        // final HorizontalLayout actions = new HorizontalLayout(buttonPostpone, buttonHit, buttonHit, buttonPrev, buttonNext);
        // actions.setWidth("80%");
        // actions.setMargin(true);
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

        Component TXDetailCcomponent = UIHelper.wrapWithVerticalTopMargin(UIHelper.wrapWithPanel(UIHelper.wrapWithVertical(txDetails), "Transaction Details", FontAwesome.LIST));

        // left side

        final VerticalSplitPanel listEntryAndDetails = new VerticalSplitPanel();

        // Put other components in the panel
        tableTXWithHits.setSizeFull();

        listEntryAndDetails.setFirstComponent(UIHelper.wrapWithVerticalBottomMargin(tableTXWithHits));
        listEntryAndDetails.setSecondComponent(TXDetailCcomponent);
        listEntryAndDetails.setSplitPosition(75, Unit.PERCENTAGE);
        listEntryAndDetails.setSizeFull();

        // VerticalLayout listEntryAndDetails = new VerticalLayout(tableTXWithHits, txDetails);
        // listEntryAndDetails.setExpandRatio(tableTXWithHits, 8);
        // listEntryAndDetails.setExpandRatio(txDetails, 2);

        final TabSheet tabsheetTX = new TabSheet();
        tabsheetTX.addStyleName("framed");

        final Component lead = UIHelper.wrapWithVertical(listEntryAndDetails);
        lead.setIcon(FontAwesome.FILE_TEXT);
        lead.setCaption("Transaction");

        tabsheetTX.addTab(lead);
        tabsheetTX.setSizeFull();

        // right side
        final TabSheet tabsheetHits = new TabSheet();
        tabsheetHits.addStyleName("framed");
        final VerticalSplitPanel hitsAndDetails = new VerticalSplitPanel();

        // Put other components in the panel

        TXDetailCcomponent = UIHelper.wrapWithVerticalTopMargin(UIHelper.wrapWithPanel(UIHelper.wrapWithVertical(sanctionListDetails), "Watchlist Details", FontAwesome.LIST));

        hitsAndDetails.setFirstComponent(UIHelper.wrapWithVerticalBottomMargin(tableResults));
        hitsAndDetails.setSecondComponent(TXDetailCcomponent);
        hitsAndDetails.setSplitPosition(60, Unit.PERCENTAGE);

        // VerticalLayout hitsAndDetails = new VerticalLayout(tableResults, listDetails);
        // hitsAndDetails.setExpandRatio(tableResults, 9);
        // hitsAndDetails.setExpandRatio(listDetails, 1);
        hitsAndDetails.setSizeFull();

        final Component had = UIHelper.wrapWithVertical(hitsAndDetails);
        had.setCaption("List Hits");
        had.setIcon(FontAwesome.TH_LIST);
        tabsheetHits.addTab(had);

        final Component twh = UIHelper.wrapWithVertical(tableWordHits);
        twh.setCaption("Word Hits");
        twh.setIcon(FontAwesome.TH);
        tabsheetHits.addTab(twh);
        tabsheetHits.setSizeFull();

        final VerticalLayout vll = new VerticalLayout(tabsheetTX);
        final VerticalLayout vlr = new VerticalLayout(tabsheetHits);

        vll.setSizeFull();
        vlr.setSizeFull();

        final HorizontalSplitPanel mainHSplitPanel = new HorizontalSplitPanel();
        mainHSplitPanel.setFirstComponent(UIHelper.wrapWithMargin(vll));
        mainHSplitPanel.setSecondComponent(UIHelper.wrapWithMargin(vlr));
        mainHSplitPanel.setSplitPosition(50, Unit.PERCENTAGE);

        tableResults.setColumnReorderingAllowed(true);
        mainHSplitPanel.setSizeFull();

        // addComponent(mainHSplitPanel);
        // addComponent(actions);
        //
        // setExpandRatio(mainHSplitPanel, 9);
        // setExpandRatio(actions, 1);
        // // setMargin(new MarginInfo(true));
        // setSizeFull();

        return mainHSplitPanel;

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

    @Override
    public void doProcessHit() {
        guiAdapter.getCurrentMessage().setComment(textPane_Comment.getValue());

    }

    @Override
    public void doProcessNoHit() {
        guiAdapter.getCurrentMessage().setComment(textPane_Comment.getValue());
    }

    @Override
    public void doPostpone() {
        guiAdapter.getCurrentMessage().setComment(textPane_Comment.getValue());
    }

}
