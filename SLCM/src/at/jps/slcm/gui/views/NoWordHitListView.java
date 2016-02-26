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

import at.jps.sanction.model.listhandler.NoWordHitListHandler;
import at.jps.sl.gui.AdapterHelper;
import at.jps.slcm.gui.components.UIHelper;
import at.jps.slcm.gui.models.DisplayReferenceListRecord;
import at.jps.slcm.gui.services.DisplayReferenceListService;

public class NoWordHitListView extends VerticalLayout implements View {

    /**
     *
     */
    private static final long          serialVersionUID = -5216331400056752150L;

    private String                     viewName         = "noWordHitList-";

    private final Grid                 tableList        = new Grid();

    private final AdapterHelper        guiAdapter;

    private final NoWordHitListHandler listHandler;

    public NoWordHitListView(final AdapterHelper guiAdapter, final NoWordHitListHandler listhandler) {
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

        tableList.setContainerDataSource(new BeanItemContainer<>(DisplayReferenceListRecord.class));
        tableList.setColumnOrder("key", "value");

        tableList.getColumn("key").setHeaderCaption("WL Token");
        tableList.getColumn("value").setHeaderCaption("TX Token");

        tableList.removeColumn("id");
        tableList.setSelectionMode(Grid.SelectionMode.SINGLE);
        tableList.setSizeFull();

        final TableModel tm = guiAdapter.getNoWordHitListTableModel(listHandler);

        final DisplayReferenceListService displayService = new DisplayReferenceListService();

        displayService.setModel(tm);
        tableList.setContainerDataSource(new BeanItemContainer<>(DisplayReferenceListRecord.class, displayService.displayAllFields()));

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
        addComponent(h1);

        final Component component = UIHelper.wrapWithPanel(tableList, "List:" + getViewname(), FontAwesome.LIST);
        addComponent(component);

        setExpandRatio(component, 1);
        setSizeFull();

    }

}
