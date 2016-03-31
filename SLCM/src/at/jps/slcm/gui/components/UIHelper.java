package at.jps.slcm.gui.components;

import com.vaadin.server.Resource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class UIHelper {

    public static Component wrapWithPanel(final Component component, final String caption, final Resource icon) {

        final Panel panel = new Panel(caption);
        if (icon != null) {
            panel.setIcon(icon);
        }
        panel.setContent(component);
        panel.setSizeFull();

        return panel;
    }

    public static Component wrapWithVertical(final Component component) {

        final VerticalLayout vlayout = new VerticalLayout();
        vlayout.setMargin(true);
        vlayout.setSizeFull();
        vlayout.addComponent(component);

        return vlayout;
    }

    public static Component wrapWithMargin(final Component component) {

        final VerticalLayout vlayout = new VerticalLayout();
        vlayout.setMargin(new MarginInfo(true));
        vlayout.setSizeFull();
        vlayout.addComponent(component);

        return vlayout;
    }

    public static Component wrapWithVerticalBottomMargin(final Component component) {

        final VerticalLayout vlayout = new VerticalLayout();
        vlayout.setMargin(new MarginInfo(false, false, true, false));
        vlayout.setSizeFull();
        vlayout.addComponent(component);

        return vlayout;
    }

    public static Component wrapWithVerticalTopMargin(final Component component) {

        final VerticalLayout vlayout = new VerticalLayout();
        vlayout.setMargin(new MarginInfo(true, false, false, false));
        vlayout.setSizeFull();
        vlayout.addComponent(component);

        return vlayout;
    }

}
