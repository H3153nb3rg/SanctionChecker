package at.jps.slcm.gui.views;

import javax.swing.table.TableModel;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import at.jps.sanction.model.listhandler.OptimizationListHandler;
import at.jps.sl.gui.AdapterHelper;
import at.jps.slcm.gui.components.UIHelper;
import at.jps.slcm.gui.models.DisplayOptiTxListRecord;
import at.jps.slcm.gui.services.DisplayOptiTxListService;

public class OptiTxListView extends VerticalLayout implements View {

    /**
     *
     */
    private static final long             serialVersionUID = -5216331400056752150L;

    private String                        viewName         = "OptiTx-";

    private final Grid                    tableList        = new Grid();

    private final AdapterHelper           guiAdapter;

    private final OptimizationListHandler listHandler;

    public OptiTxListView(final AdapterHelper guiAdapter, final OptimizationListHandler listhandler) {
        super();

        this.guiAdapter = guiAdapter;

        listHandler = listhandler;

        viewName += listhandler.getListName();

        buildPage();
    }

    public String getViewname() {
        return viewName;
    }

    @Override
    public void enter(final ViewChangeEvent event) {
        Notification.show("Edit " + getViewname());

    }

    void buildPage() {

        setSpacing(true);

        tableList.setContainerDataSource(new BeanItemContainer<>(DisplayOptiTxListRecord.class));
        tableList.setColumnOrder("field", "content", "watchlist", "wlid", "status");

        tableList.getColumn("field").setHeaderCaption("TX Field");
        tableList.getColumn("content").setHeaderCaption("Field value");
        tableList.getColumn("watchlist").setHeaderCaption("Watchlist");
        tableList.getColumn("wlid").setHeaderCaption("WL Id");
        tableList.getColumn("status").setHeaderCaption("Status");

        tableList.removeColumn("id");
        tableList.setSelectionMode(Grid.SelectionMode.SINGLE);
        tableList.setSizeFull();

        final TableModel tm = guiAdapter.getOptimizationListTableModel(listHandler.getValues());

        final DisplayOptiTxListService displayService = new DisplayOptiTxListService();

        displayService.setModel(tm);
        tableList.setContainerDataSource(new BeanItemContainer<>(DisplayOptiTxListRecord.class, displayService.displayAllFields()));

        tableList.addSelectionListener(new SelectionListener() {

            /**
             *
             */
            private static final long serialVersionUID = 3082372924362828902L;

            @Override
            public void select(final SelectionEvent event) {

                // updateSearchSelection(event);

            }
        });

        final Label h1 = new Label(getViewname());
        h1.addStyleName("h1");
        addComponent(UIHelper.wrapWithVertical(h1));

        final Component component = UIHelper.wrapWithVertical(UIHelper.wrapWithPanel(UIHelper.wrapWithVertical(tableList), "List:" + getViewname(), FontAwesome.LIST));

        addComponent(component);
        setExpandRatio(component, 1);
        setSizeFull();

    }

}
