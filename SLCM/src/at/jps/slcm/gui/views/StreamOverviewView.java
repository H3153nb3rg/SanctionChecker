package at.jps.slcm.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import at.jps.sl.gui.AdapterHelper;
import at.jps.slcm.gui.components.StreamDetails;
import at.jps.slcm.gui.components.UIHelper;

public class StreamOverviewView extends VerticalLayout implements View {

    /**
     *
     */
    private static final long  serialVersionUID = -3580013880310888927L;

    public final static String ViewName         = "Streams";

    AdapterHelper              guiAdapter;

    public StreamOverviewView(final AdapterHelper guiAdapter) {
        super();

        this.guiAdapter = guiAdapter;

        buildPage();
    }

    public String getViewname() {
        return ViewName;
    }

    @Override
    public void enter(ViewChangeEvent event) {
        Notification.show("Overview " + getViewname());
    }

    private void buildPage() {

        if (guiAdapter.getConfig().getStreamConfigs().size() > 1) {
            setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        }
        setSpacing(true);

        // final Component component = new Label("confirmed!!");// QueueSizeComponent.createGraphicsComponent();

        for (final String streamName : guiAdapter.getConfig().getStreamConfigs().keySet()) {
            addComponent(UIHelper.wrapWithMargin(new StreamDetails(guiAdapter, streamName)));
        }

        // setExpandRatio(component, 1);
        setSizeFull();

    }

}
