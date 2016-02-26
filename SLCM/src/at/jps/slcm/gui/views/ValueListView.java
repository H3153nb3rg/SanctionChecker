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

import at.jps.sanction.model.listhandler.ValueListHandler;
import at.jps.sl.gui.AdapterHelper;
import at.jps.slcm.gui.components.UIHelper;
import at.jps.slcm.gui.models.DisplayValueListRecord;
import at.jps.slcm.gui.services.DisplayValueListService;

public class ValueListView extends VerticalLayout implements View {

    /**
     *
     */
    private static final long      serialVersionUID = -3186524749097609590L;

    /**
     *
     */

    private String                 viewName         = "ValueList-";

    private final Grid             tableList        = new Grid();

    private final AdapterHelper    guiAdapter;

    private final ValueListHandler listHandler;

    public ValueListView(final AdapterHelper guiAdapter, final ValueListHandler listhandler) {
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

        tableList.setContainerDataSource(new BeanItemContainer<>(DisplayValueListRecord.class));
        tableList.setColumnOrder("value");
        tableList.getColumn("value").setHeaderCaption("Value");

        tableList.removeColumn("id");
        tableList.setSelectionMode(Grid.SelectionMode.SINGLE);
        tableList.setSizeFull();

        final TableModel tm = guiAdapter.getValueListTableModel(listHandler);

        final DisplayValueListService displayService = new DisplayValueListService();

        displayService.setModel(tm);
        tableList.setContainerDataSource(new BeanItemContainer<>(DisplayValueListRecord.class, displayService.displayAllFields()));

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

        // packageing....

        final Label h1 = new Label(getViewname());
        h1.addStyleName("h1");
        addComponent(UIHelper.wrapWithVertical(h1));

        final Component c = UIHelper.wrapWithVertical(UIHelper.wrapWithPanel(UIHelper.wrapWithVertical(tableList), "List:" + getViewname(), FontAwesome.LIST));
        addComponent(c);

        setExpandRatio(c, 1);

        setSizeFull();

    }

}
