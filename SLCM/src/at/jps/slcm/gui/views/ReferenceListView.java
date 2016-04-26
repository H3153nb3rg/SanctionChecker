package at.jps.slcm.gui.views;

import javax.swing.table.TableModel;

import org.vaadin.gridutil.renderer.EditDeleteButtonValueRenderer;
import org.vaadin.gridutil.renderer.EditDeleteButtonValueRenderer.EditDeleteButtonClickListener;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent;

import at.jps.sanction.model.listhandler.ReferenceListHandler;
import at.jps.sl.gui.AdapterHelper;
import at.jps.slcm.gui.components.UIHelper;
import at.jps.slcm.gui.models.DisplayReferenceListRecord;
import at.jps.slcm.gui.services.DisplayReferenceListService;

public class ReferenceListView extends VerticalLayout implements View {

    /**
     *
     */
    private static final long          serialVersionUID = -5216331400056752150L;

    private String                     viewName         = "ReferenceList-";

    private final Grid                 tableList        = new Grid();

    private final AdapterHelper        guiAdapter;

    private final ReferenceListHandler listHandler;

    public ReferenceListView(final AdapterHelper guiAdapter, final ReferenceListHandler listhandler) {
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

        tableList.getColumn("key").setHeaderCaption("Key");
        tableList.getColumn("value").setHeaderCaption("Value");

        // tableList.removeColumn("id");
        tableList.setSelectionMode(Grid.SelectionMode.SINGLE);
        tableList.setSizeFull();

        final TableModel tm = guiAdapter.getReferenceListTableModel(listHandler);

        final DisplayReferenceListService displayService = new DisplayReferenceListService();

        displayService.setModel(tm);
        tableList.setContainerDataSource(new BeanItemContainer<>(DisplayReferenceListRecord.class, displayService.displayAllFields()));

        // tableList.addSelectionListener(new SelectionListener() {
        //
        // /**
        // *
        // */
        // private static final long serialVersionUID = 3082372924362828902L;
        //
        // @Override
        // public void select(final SelectionEvent event) {
        //
        // // updateSearchSelection(event);
        //
        // }
        // });

        tableList.getColumn("id").setRenderer(new EditDeleteButtonValueRenderer(new EditDeleteButtonClickListener() {

            @Override
            public void onEdit(final RendererClickEvent event) {
                Notification.show(event.getItemId().toString() + " want's to get edited", Type.HUMANIZED_MESSAGE);
            }

            @Override
            public void onDelete(final com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent event) {
                Notification.show(event.getItemId().toString() + " want's to get delete", Type.WARNING_MESSAGE);
            };

        })).setWidth(150);

        final Label h1 = new Label(getViewname());
        h1.addStyleName("h1");
        addComponent(UIHelper.wrapWithVertical(h1));

        final Component component = UIHelper.wrapWithVertical(UIHelper.wrapWithPanel(UIHelper.wrapWithVertical(tableList), "List:" + getViewname(), FontAwesome.LIST));

        addComponent(component);
        setExpandRatio(component, 1);
        setSizeFull();

    }

}
