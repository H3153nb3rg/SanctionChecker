package at.jps.slcm.gui.components;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import at.jps.sanction.core.StreamConfig;
import at.jps.sanction.model.queue.Queue;
import at.jps.sl.gui.AdapterHelper;
import at.jps.slcm.gui.models.DisplayReferenceListRecord;
import at.jps.slcm.gui.util.SessionInfo;

public class StreamDetails extends VerticalLayout {

    /**
     *
     */
    private static final long   serialVersionUID = -2044233805158767654L;

    private final AdapterHelper guiAdapter;
    private final String        streamName;
    private final String        nextViewName;
    private final Grid          tableList        = new Grid();

    public StreamDetails(final AdapterHelper guiAdapter, final String streamName, final String nextViewName) {
        super();
        this.guiAdapter = guiAdapter;
        this.streamName = streamName;
        this.nextViewName = nextViewName;
        buildPage();
    }

    private void buildPage() {

        tableList.setContainerDataSource(new BeanItemContainer<>(DisplayReferenceListRecord.class));
        tableList.setColumnOrder("key", "value");

        tableList.getColumn("key").setHeaderCaption("Queue Name");
        tableList.getColumn("value").setHeaderCaption("Size");

        tableList.removeColumn("id");
        tableList.setSelectionMode(Grid.SelectionMode.SINGLE);
        tableList.setSizeFull();

        final List<DisplayReferenceListRecord> queueInfos = getQueueInfo();

        tableList.setContainerDataSource(new BeanItemContainer<>(DisplayReferenceListRecord.class, getQueueInfo()));

        tableList.addSelectionListener(new SelectionListener() {

            /**
             *
             */
            private static final long serialVersionUID = 3082372924362828902L;

            @Override
            public void select(final SelectionEvent event) {

                // updateSearchSelection(event);

                // VaadinService.getCurrentRequest().getWrappedSession()
                // .setAttribute("activeStream", event);

            }
        });

        final Button buttonSelect = new Button("Select");
        buttonSelect.addClickListener(new Button.ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = 5263732114286684471L;

            @Override
            public void buttonClick(final ClickEvent event) {

                // The quickest way to confirm
                ConfirmDialog.show(UI.getCurrent(), "Switch to Stream '" + streamName + "'", new ConfirmDialog.Listener() {

                    /**
                     *
                     */
                    private static final long serialVersionUID = -6223820393594660850L;

                    @Override
                    public void onClose(final ConfirmDialog dialog) {
                        if (dialog.isConfirmed()) {

                            // Confirmed to continue
                            // feedback(dialog.isConfirmed());
                            // layout.addComponent(new Label("confirmed!!"));

                            SessionInfo si = (SessionInfo) getUI().getSession().getAttribute(SessionInfo.SESSION_INFO);

                            if (si == null) {
                                si = new SessionInfo();

                                getUI().getSession().setAttribute(SessionInfo.SESSION_INFO, si);
                            }
                            si.setActiveStream(streamName);

                            // switch to work
                            if (nextViewName != null) {
                                getUI().getNavigator().navigateTo(nextViewName);
                            }

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

        final VerticalLayout vl = new VerticalLayout();

        vl.addComponent(tableList);
        vl.addComponent(UIHelper.wrapWithVerticalTopMargin(buttonSelect));

        final HorizontalLayout hl = new HorizontalLayout();

        hl.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        hl.addComponent(vl);
        // TODO: NAAAAAAAAAAASTYYYYYY TEST
        final int input = Integer.parseInt(queueInfos.get(0).getValue());
        final int h = Integer.parseInt(queueInfos.get(1).getValue());
        final int nh = Integer.parseInt(queueInfos.get(2).getValue());

        hl.addComponent(QueueSizeComponent.getChart(input, h, nh));
        hl.setSizeFull();

        addComponent(UIHelper.wrapWithMargin(UIHelper.wrapWithPanel(UIHelper.wrapWithMargin(hl), streamName, FontAwesome.LIST)));

    }

    private List<DisplayReferenceListRecord> getQueueInfo() {
        final StreamConfig streamConfig = guiAdapter.getConfig().getStreamConfig(streamName);

        final List<DisplayReferenceListRecord> drlrl = new ArrayList<DisplayReferenceListRecord>(streamConfig.getQueues().size());

        for (final String queueName : streamConfig.getQueues().keySet()) {
            final DisplayReferenceListRecord drlr = new DisplayReferenceListRecord();

            drlr.setKey(queueName);

            final Queue<?> queue = streamConfig.getQueues().get(queueName);
            drlr.setValue(Long.toString(queue.size()));
            drlrl.add(drlr);
        }
        return drlrl;
    }

}
