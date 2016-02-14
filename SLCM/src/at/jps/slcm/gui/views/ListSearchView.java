package at.jps.slcm.gui.views;

import javax.swing.table.TableModel;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

import at.jps.sanction.model.sl.entities.WL_Entity;
import at.jps.sl.gui.AdapterHelper;
import at.jps.sl.gui.core.SearchTableModelHandler;
import at.jps.slcm.gui.components.SanctionListDetails;
import at.jps.slcm.gui.models.DisplayEntitySearchDetails;
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

    public ListSearchView(final AdapterHelper guiAdapter) {
        super();

        this.guiAdapter = guiAdapter;

        buildPage();
    }

    @Override
    public void enter(final ViewChangeEvent event) {
        Notification.show("Search Listinfo...");

    }

    Component buildSearchForm() {
        final HorizontalLayout layout = new HorizontalLayout();

        // layout.setMargin(true);

        layout.setSpacing(false);

        textField_searchToken = new TextField();
        final Button buttonSearch = new Button("start Search");

        textField_searchToken.setWidth("100%");

        buttonSearch.addClickListener(new Button.ClickListener() {

            /**
             *
             */
            private static final long serialVersionUID = -7899503822618589927L;

            @Override
            public void buttonClick(final ClickEvent event) {

                final String textPattern = textField_searchToken.getValue();

                if ((textPattern != null) && (textPattern.length() > 1)) {
                    startSearch(textPattern);
                }
            }
        });

        layout.addComponent(textField_searchToken);
        layout.addComponent(buttonSearch);

        // layout.setComponentAlignment(buttonSearch, Alignment.MIDDLE_CENTER);
        layout.setWidth("100%");
        layout.setHeight("250px");

        return layout;
    }

    Component buildSearchResultForm() {

        sanctionListDetails = new SanctionListDetails(guiAdapter);

        final VerticalLayout layout = new VerticalLayout();

        final TabSheet searchResultsTab = new TabSheet();

        tableSearchResult.setContainerDataSource(new BeanItemContainer<>(DisplayEntitySearchDetails.class));
        tableSearchResult.setColumnOrder("wlname", "wlid", "entry", "remark");
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

        layout.addComponent(searchResultsTab);

        layout.setSpacing(true);
        layout.setSizeFull();

        searchResultsTab.addTab(tableSearchResult).setCaption("Search Results");
        searchResultsTab.setSizeFull();

        final VerticalLayout layout2 = new VerticalLayout();

        final Component searchForm = buildSearchForm();

        layout2.addComponent(searchForm);
        layout2.addComponent(layout);
        layout2.setExpandRatio(searchForm, 1);
        layout2.setExpandRatio(layout, 9);

        layout2.setSpacing(true);
        layout2.setSizeFull();

        return layout2;
    }

    Component buildDetailsForm() {
        return sanctionListDetails;
    }

    void buildPage() {
        final VerticalSplitPanel pageSplitter = new VerticalSplitPanel();

        // Put other components in the panel
        pageSplitter.setFirstComponent(buildSearchResultForm());
        pageSplitter.setSecondComponent(buildDetailsForm());

        pageSplitter.setSplitPosition(60, Unit.PERCENTAGE);

        addComponent(pageSplitter);

        setSizeFull();

    }

    private void startSearch(final String searchPattern) {

        final TableModel tm = SearchTableModelHandler.doSearch(guiAdapter.getConfig().getWatchLists(), searchPattern);

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
            Notification.show("selected: BUG -> no Result found!");
        }
    }
}
