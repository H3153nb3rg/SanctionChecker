package at.jps.slcm.gui.views;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.swing.table.TableModel;

import com.vaadin.addon.contextmenu.ContextMenu;
import com.vaadin.addon.contextmenu.ContextMenu.ContextMenuOpenListener;
import com.vaadin.addon.contextmenu.Menu.Command;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import at.jps.sanction.model.wl.entities.WL_Entity;
import at.jps.sl.gui.AdapterHelper;
import at.jps.sl.gui.model.watchlist.SearchTableModelHandler;
import at.jps.slcm.gui.components.SanctionListDetails;
import at.jps.slcm.gui.components.UIHelper;
import at.jps.slcm.gui.models.DisplayEntitySearchDetails;
import at.jps.slcm.gui.models.DisplayRelation;
import at.jps.slcm.gui.services.DisplayEntitySearchService;

public class ListSearchView extends VerticalLayout implements View {

    /**
     *
     */
    private static final long                serialVersionUID           = -404540661984379897L;

    final public static String               ViewName                   = "listSearch";

    private TextField                        textField_searchToken;

    private final Grid                       tableSearchResult          = new Grid();

    private final DisplayEntitySearchService displayEntitySearchService = new DisplayEntitySearchService();

    private final AdapterHelper              guiAdapter;

    private SanctionListDetails              sanctionListDetails;

    private String                           selectedToken;
    private String                           selectedWatchList;

    public ListSearchView(final AdapterHelper guiAdapter) {
        super();

        this.guiAdapter = guiAdapter;

        buildPage();
    }

    @Override
    public void enter(final ViewChangeEvent event) {
        Notification.show("Search in Watchlists", "easy going intuitive global Searching AND finding...", Type.ASSISTIVE_NOTIFICATION);
    }

    Component buildSearchForm() {
        final HorizontalLayout layout = new HorizontalLayout();

        // layout.setMargin(true);

        layout.setSpacing(true);

        final ComboBox combo_watchlist = new ComboBox();

        for (final String watchlistname : guiAdapter.getConfig().getWatchLists().keySet()) {
            combo_watchlist.addItem(watchlistname);
        }
        combo_watchlist.setNullSelectionAllowed(true);

        textField_searchToken = new TextField();

        textField_searchToken.setIcon(FontAwesome.QUESTION);
        textField_searchToken.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final Button buttonSearch = new Button("start Search");
        buttonSearch.setIcon(FontAwesome.SEARCH);
        buttonSearch.setClickShortcut(KeyCode.ENTER);

        // textField_searchToken.setWidth("100%");

        buttonSearch.addClickListener(new Button.ClickListener() {

            /**
             *
             */
            private static final long serialVersionUID = -7899503822618589927L;

            @Override
            public void buttonClick(final ClickEvent event) {

                selectedToken = textField_searchToken.getValue();
                selectedWatchList = (String) combo_watchlist.getValue();

                if ((selectedToken != null) && (selectedToken.length() > 1)) {

                    startSearch(selectedWatchList, selectedToken);
                }
            }
        });

        textField_searchToken.setWidth("100%");

        layout.addComponent(combo_watchlist);
        layout.addComponent(textField_searchToken);
        layout.setExpandRatio(textField_searchToken, 1);
        layout.addComponent(buttonSearch);

        // layout.setComponentAlignment(buttonSearch, Alignment.MIDDLE_CENTER);
        layout.setWidth("100%");

        return layout;
    }

    Component buildSearchResultForm() {

        sanctionListDetails = new SanctionListDetails(guiAdapter);

        addRelationPopup();

        final VerticalLayout layout = new VerticalLayout();

        final TabSheet searchResultsTab = new TabSheet();
        searchResultsTab.addStyleName("framed");

        tableSearchResult.setContainerDataSource(new BeanItemContainer<>(DisplayEntitySearchDetails.class));
        tableSearchResult.setColumnOrder("wlname", "wlid", "entry", "remark");
        tableSearchResult.getColumn("wlname").setHeaderCaption("Name");
        tableSearchResult.getColumn("wlid").setHeaderCaption("ID");
        tableSearchResult.getColumn("entry").setHeaderCaption("Entry");
        tableSearchResult.getColumn("remark").setHeaderCaption("Remark");

        tableSearchResult.removeColumn("id");
        tableSearchResult.setSelectionMode(Grid.SelectionMode.SINGLE);
        tableSearchResult.setSizeFull();

        tableSearchResult.addSelectionListener(new SelectionListener() {

            /**
             *
             */
            private static final long serialVersionUID = 3082372924362828902L;

            @Override
            public void select(final SelectionEvent event) {

                updateSearchSelection(event);

            }
        });

        final Component tsr = UIHelper.wrapWithVertical(tableSearchResult);
        tsr.setCaption("Search Results");
        tsr.setIcon(FontAwesome.INFO);

        searchResultsTab.addTab(tsr);
        searchResultsTab.setSizeFull();

        layout.addComponent(searchResultsTab);

        layout.setSpacing(true);
        layout.setSizeFull();

        final VerticalLayout layout2 = new VerticalLayout();

        final Component searchForm = buildSearchForm();

        layout2.addComponent(searchForm);
        layout2.addComponent(layout);

        layout2.setExpandRatio(layout, 1);

        layout2.setSpacing(true);
        layout2.setMargin(true);
        layout2.setSizeFull();

        final Component component = UIHelper.wrapWithVertical(UIHelper.wrapWithPanel(layout2, "Search", FontAwesome.SEARCH));

        return component;

    }

    Component buildDetailsForm() {

        sanctionListDetails.setMargin(true);

        final Component component = UIHelper.wrapWithVertical(UIHelper.wrapWithPanel(sanctionListDetails, "Details", FontAwesome.LIST));

        return component;
    }

    void buildPage() {
        final VerticalSplitPanel pageSplitter = new VerticalSplitPanel();

        // Put other components in the panel

        pageSplitter.setFirstComponent(buildSearchResultForm());
        pageSplitter.setSecondComponent(buildDetailsForm());

        pageSplitter.setSplitPosition(60, Unit.PERCENTAGE);

        // addComponent(buildMenuBar());
        addComponent(pageSplitter);
        setExpandRatio(pageSplitter, 1);

        setMargin(false);
        setSizeFull();

    }

    public Component buildMenuBar() {

        // TEST ONLY

        final VerticalLayout layout = new VerticalLayout();

        final MenuBar barmenu = new MenuBar();
        barmenu.addStyleName("mybarmenu");

        // Define a common menu command for all the menu items
        final MenuBar.Command mycommand = new MenuBar.Command() {

            /**
             *
             */
            private static final long serialVersionUID = -2449165909845224538L;
            MenuItem                  previous         = null;

            @Override
            public void menuSelected(MenuItem selectedItem) {

                Notification.show("Ordered a " + selectedItem.getText() + " from menu.");

                if (previous != null) {
                    previous.setStyleName(null);
                }
                selectedItem.setStyleName("highlight");
                previous = selectedItem;
            }
        };

        // A top-level menu item that opens a submenu
        final MenuItem drinks = barmenu.addItem("User", null, null);
        drinks.addItem("logout", null, mycommand);

        final MenuItem tx = barmenu.addItem("Transactions", null, null);
        tx.addItem("work on hits", null, mycommand);
        tx.addItem("work on no-hits", null, mycommand);

        // Another top-level item
        final MenuItem snacks = barmenu.addItem("Search", null, null);
        snacks.addItem("search Watchlist...", null, mycommand);

        // Yet another top-level item
        final MenuItem servs = barmenu.addItem("About", null, null);
        servs.addItem("About...", null, mycommand);

        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponent(barmenu);
        layout.setMargin(new MarginInfo(false, false, false, true));

        return layout;
    }

    private void showHierarchyChart() {
        final Window window = new Window("Hierarchy");
        window.setWidth(400.0f, Unit.PIXELS);
        window.setHeight(400.0f, Unit.PIXELS);

        final String content = "<html>\r\n" + "  <head>\r\n" + "    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\r\n"
                + "    <script type=\"text/javascript\">\r\n" + "      google.charts.load('current', {packages:[\"orgchart\"]});\r\n" + "      google.charts.setOnLoadCallback(drawChart);\r\n" + "\r\n"
                + "      function drawChart() {\r\n" + "        var data = new google.visualization.DataTable();\r\n" + "        data.addColumn('string', 'Name');\r\n"
                + "        data.addColumn('string', 'Manager');\r\n" + "        data.addColumn('string', 'ToolTip');\r\n" + "\r\n"
                + "        // For each orgchart box, provide the name, manager, and tooltip to show.\r\n" + "        data.addRows([\r\n"
                + "          [{v:'Mike', f:'Mike<div style=\"color:red; font-style:italic\">President</div>'},\r\n" + "           '', 'The President'],\r\n"
                + "          [{v:'Jim', f:'Jim<div style=\"color:red; font-style:italic\">Vice President</div>'},\r\n" + "           'Mike', 'VP'],\r\n" + "          ['Alice', 'Mike', ''],\r\n"
                + "          ['Bob', 'Jim', 'Bob Sponge'],\r\n" + "          ['Carol', 'Bob', '']\r\n" + "        ]);\r\n" + "\r\n" + "        // Create the chart.\r\n"
                + "        var chart = new google.visualization.OrgChart(document.getElementById('chart_div'));\r\n"
                + "        // Draw the chart, setting the allowHtml option to true for the tooltips.\r\n" + "        chart.draw(data, {allowHtml:true});\r\n" + "      }\r\n" + "   </script>\r\n"
                + "    </head>\r\n" + "  <body>\r\n" + "    <div id=\"chart_div\"></div>\r\n" + "  </body>\r\n" + "</html>";

        final StreamResource stream = new StreamResource(new StreamResource.StreamSource() {

            /**
             *
             */
            private static final long serialVersionUID = 6872585029763260959L;

            @Override
            public InputStream getStream() {
                return new ByteArrayInputStream(content.getBytes());
            }
        }, "");
        stream.setMIMEType("text/html");

        final BrowserFrame sample = new BrowserFrame("vaadin.com", stream);
        sample.setSizeFull();

        // content.addComponent(sample);
        window.setContent(sample);

        UI.getCurrent().addWindow(window);
    }

    private void addRelationPopup() {

        // add contextmenu
        final ContextMenu tableWordHitsContextMenu = new ContextMenu(this, false);

        tableWordHitsContextMenu.setAsContextMenuOf(sanctionListDetails.getRelationTable());

        tableWordHitsContextMenu.addContextMenuOpenListener(new ContextMenuOpenListener() {

            @Override
            public void onContextMenuOpen(final ContextMenuOpenEvent event) {

                event.getContextClickEvent();

                // Object itemId = tableNameDetails.getContainerDataSource().addItem();

                final DisplayRelation relationInfo = ((DisplayRelation) sanctionListDetails.getRelationTable().getSelectedRow());

                if (relationInfo != null) {

                    selectedToken = relationInfo.getWlid();

                    tableWordHitsContextMenu.removeItems();

                    tableWordHitsContextMenu.addItem("dive into '" + selectedToken + "'", new Command() {
                        /**
                         *
                         */
                        private static final long serialVersionUID = -7313734853304935326L;

                        @Override
                        public void menuSelected(final com.vaadin.addon.contextmenu.MenuItem selectedItem) {
                            textField_searchToken.setValue(selectedToken);
                            Notification.show("search it...");
                            startSearch(selectedWatchList, selectedToken);
                        }
                    });

                    tableWordHitsContextMenu.addItem("show hierarchy", new Command() {
                        /**
                         *
                         */
                        private static final long serialVersionUID = -7313734853304935323L;

                        @Override
                        public void menuSelected(final com.vaadin.addon.contextmenu.MenuItem selectedItem) {
                            showHierarchyChart();
                        }
                    });
                }
            }
        });

    }

    private void startSearch(final String listname, final String searchPattern) {

        // TODO shift to guiAdapter
        final TableModel tm = (listname != null) ? SearchTableModelHandler.doSearch(guiAdapter.getConfig().getWatchLists().get(listname), searchPattern)
                : SearchTableModelHandler.doSearch(guiAdapter.getConfig().getWatchLists(), searchPattern);

        displayEntitySearchService.setModel(tm);
        tableSearchResult.setContainerDataSource(new BeanItemContainer<>(DisplayEntitySearchDetails.class, displayEntitySearchService.displayAllFields()));
    }

    private void updateSearchSelection(SelectionEvent event) {
        final DisplayEntitySearchDetails displayResult = (DisplayEntitySearchDetails) tableSearchResult.getSelectedRow();
        if (displayResult != null) {

            final String watchlistName = displayResult.getWlname();
            final String watchlistId = displayResult.getWlid();

            final WL_Entity entity = guiAdapter.getSanctionListEntityDetails(watchlistName, watchlistId);

            sanctionListDetails.updateInfo(watchlistName, entity);
        }
        else {
            // Notification.show("selected: BUG -> no Result found!");
        }
    }
}
