package at.jps.slcm;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.servlet.annotation.WebServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.vaadin.sliderpanel.SliderPanel;
import org.vaadin.sliderpanel.SliderPanelBuilder;
import org.vaadin.sliderpanel.SliderPanelStyles;
import org.vaadin.sliderpanel.client.SliderMode;
import org.vaadin.sliderpanel.client.SliderTabPosition;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import at.jps.sanction.model.listhandler.ReferenceListHandler;
import at.jps.sanction.model.listhandler.ValueListHandler;
import at.jps.sl.gui.AdapterHelper;
import at.jps.sl.gui.util.GUIConfigHolder;
import at.jps.slcm.gui.views.HitHandlingView;
import at.jps.slcm.gui.views.ListSearchView;
import at.jps.slcm.gui.views.LoginView;
import at.jps.slcm.gui.views.OptiTxListView;
import at.jps.slcm.gui.views.ReferenceListView;
import at.jps.slcm.gui.views.ValueListView;

@SuppressWarnings("serial")
@Theme("slcm")
public class SlcmUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = SlcmUI.class, widgetset = "at.jps.slcm.SlcmWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    private static AdapterHelper                guiAdapter      = new AdapterHelper();

    private static boolean                      initialized     = false;

    public static String                        newline         = System.getProperty("line.separator");

    Navigator                                   navigator;

    String                                      loggedInUser;

    ComponentContainer                          viewDisplay;

    CssLayout                                   menu            = new CssLayout();
    CssLayout                                   menuItemsLayout = new CssLayout();

    private final LinkedHashMap<String, String> menuItems       = new LinkedHashMap<String, String>();

    private boolean                             testMode        = false;

    UI getMainWindow() {
        return this;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String user) {
        loggedInUser = user;
    }

    private static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet,consetetur sadipscing elitr, "
            + "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.";

    private VerticalLayout dummyContent(final String title, final int length) {
        String text = "";
        for (int x = 0; x <= length; x++) {
            text += LOREM_IPSUM + " ";
        }
        final Label htmlDummy = new Label(String.format("<h3>%s</h3>%s", title, text.trim()), ContentMode.HTML);
        final VerticalLayout component = new VerticalLayout(htmlDummy);
        component.setExpandRatio(htmlDummy, 1);
        component.addComponent(new Button(title, new Button.ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                Notification.show("clicked: " + title, Type.HUMANIZED_MESSAGE);
            }
        }));
        component.setMargin(true);
        component.setSpacing(true);
        return component;
    }

    @Override
    protected void init(VaadinRequest request) {

        if (!initialized) {
            final ApplicationContext context = new ClassPathXmlApplicationContext("SanctionChecker.xml");
            initialized = true;

            context.getBean("EntityManagement");

            final GUIConfigHolder config = (GUIConfigHolder) context.getBean("GUIConfig");

            guiAdapter.initialize(config);  // TODO: here ??!

        }

        if (request.getParameter("test") != null) {
            testMode = true;
        }
        if (!testMode) {
            Responsive.makeResponsive(this);
        }

        getPage().setTitle("Sanction Analysis Case Management");

        // left slider
        // final VerticalLayout leftDummyContent = dummyContent("Oke", 3);
        //
        // leftDummyContent.setWidth(200, Unit.PIXELS);
        // final SliderPanel leftSlider = new SliderPanelBuilder(leftDummyContent,
        // "Menu").mode(SliderMode.LEFT).tabPosition(SliderTabPosition.MIDDLE).autoCollapseSlider(true).animationDuration(500)
        // .flowInContent(true).style(SliderPanelStyles.COLOR_WHITE).build();

        final VerticalLayout layout = new VerticalLayout();
        // layout.setMargin(true);
        // layout.setSpacing(true);
        layout.setSizeFull();
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        final CssLayout menuArea = new CssLayout();
        menuArea.setPrimaryStyleName("valo-menu");

        final HorizontalLayout contentLayout = new HorizontalLayout();
        contentLayout.setSpacing(false);
        contentLayout.setSizeFull();

        final VerticalLayout container = new VerticalLayout();
        container.setSizeFull();
        // container.setMargin(true);
        container.addStyleName("v-scrollable");
        container.setSpacing(true);
        container.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        // contentLayout.addComponent(leftSlider);
        contentLayout.addComponent(menuArea);
        contentLayout.addComponent(container);
        contentLayout.setExpandRatio(container, 1);

        layout.addComponent(contentLayout);
        layout.setExpandRatio(contentLayout, 1);

        setContent(layout);

        final ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(container);

        navigator = new Navigator(UI.getCurrent(), viewDisplay);
        navigator.addView("", new LoginView());

        navigator.addView(HitHandlingView.ViewName, new HitHandlingView(guiAdapter));
        navigator.addView(ListSearchView.ViewName, new ListSearchView(guiAdapter));

        menuItems.put(HitHandlingView.ViewName, "TX Handling");
        menuItems.put(ListSearchView.ViewName, "Search in Watchlists");

        // add valueLists
        for (final String lname : guiAdapter.getConfig().getValueLists().keySet()) {

            final ValueListHandler lh = guiAdapter.getConfig().getValueLists().get(lname);

            final ValueListView lv = new ValueListView(guiAdapter, lh);
            navigator.addView(lv.getViewname(), lv);

            menuItems.put(lv.getViewname(), "view " + lh.getListName());

        }

        // addReferenceLists
        for (final String lname : guiAdapter.getConfig().getReferenceLists().keySet()) {

            final ReferenceListHandler lh = guiAdapter.getConfig().getReferenceLists().get(lname);

            final ReferenceListView lv = new ReferenceListView(guiAdapter, lh);
            navigator.addView(lv.getViewname(), lv);

            menuItems.put(lv.getViewname(), "view " + lh.getListName());
        }

        OptiTxListView lv = new OptiTxListView(guiAdapter, guiAdapter.getConfig().getTxHitOptimizationListHandler());
        navigator.addView(lv.getViewname(), lv);

        menuItems.put(lv.getViewname(), "view " + guiAdapter.getConfig().getTxHitOptimizationListHandler().getListName());

        lv = new OptiTxListView(guiAdapter, guiAdapter.getConfig().getTxNoHitOptimizationListHandler());
        navigator.addView(lv.getViewname(), lv);

        menuItems.put(lv.getViewname(), "view " + guiAdapter.getConfig().getTxNoHitOptimizationListHandler().getListName());

        navigator.addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(final ViewChangeEvent event) {
                return true;
            }

            @Override
            public void afterViewChange(final ViewChangeEvent event) {
                for (final Iterator<Component> it = menuItemsLayout.iterator(); it.hasNext();) {
                    it.next().removeStyleName("selected");
                }
                for (final Entry<String, String> item : menuItems.entrySet()) {
                    if (event.getViewName().equals(item.getKey())) {
                        for (final Iterator<Component> it = menuItemsLayout.iterator(); it.hasNext();) {
                            final Component c = it.next();
                            if ((c.getCaption() != null) && c.getCaption().startsWith(item.getValue())) {
                                c.addStyleName("selected");
                                break;
                            }
                        }
                        break;
                    }
                }
                menu.removeStyleName("valo-menu-visible");
            }
        });

        menuArea.addComponent(buildMenu());

        setErrorHandler(layout);

    }

    private Component buildMenu() {

        final CssLayout menu = new CssLayout();
        menu.addStyleName("valo-menu-part");

        final HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        top.addStyleName("valo-menu-title");
        menu.addComponent(top);

        final Label title = new Label("<h3>CM <strong>Sanction Analyzer</strong></h3>", ContentMode.HTML);
        title.setSizeUndefined();
        top.addComponent(title);
        top.setExpandRatio(title, 1);

        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");

        final MenuItem settingsItem = settings.addItem("Jim" + " " + "Fandango", new ThemeResource("img/profile-pic-300px.jpg"), null);
        settingsItem.addItem("Edit Profile", null);
        settingsItem.addItem("Preferences", null);
        settingsItem.addSeparator();
        settingsItem.addItem("Sign Out", null);
        menu.addComponent(settings);

        menuItemsLayout.setPrimaryStyleName("valo-menuitems");
        menu.addComponent(menuItemsLayout);

        Label label = null;

        int i = 0;
        for (final Entry<String, String> item : menuItems.entrySet()) {

            if (i == 0) {

                label = new Label("Transactions", ContentMode.HTML);
                label.setPrimaryStyleName("valo-menu-subtitle");
                label.addStyleName("h4");
                label.setSizeUndefined();
                menuItemsLayout.addComponent(label);
            }
            if (i == 2) {
                label = new Label("Listhandling", ContentMode.HTML);
                label.setValue("Listhandling" + " <span class=\"valo-menu-badge\">" + 4 + "</span>");
                label.setPrimaryStyleName("valo-menu-subtitle");
                label.addStyleName("h4");
                label.setSizeUndefined();
                menuItemsLayout.addComponent(label);

            }
            final Button b = new Button(item.getValue(), new ClickListener() {
                @Override
                public void buttonClick(final ClickEvent event) {
                    navigator.navigateTo(item.getKey());
                }
            });
            b.setHtmlContentAllowed(true);
            b.setPrimaryStyleName("valo-menu-item");
            // b.setIcon(testIcon.get());
            menuItemsLayout.addComponent(b);

            i++;
        }

        return menu;
    }

    private void setErrorHandler(final Layout layout) {
        // Configure the error handler for the UI
        UI.getCurrent().setErrorHandler(new DefaultErrorHandler() {
            @Override
            public void error(com.vaadin.server.ErrorEvent event) {
                // Find the final cause
                String cause = "<b>Application Error:</b><br/>";
                for (Throwable t = event.getThrowable(); t != null; t = t.getCause()) {
                    if (t.getCause() == null) {
                        cause += t.getClass().getName() + "<br/>";
                    }
                }

                final SliderPanel sliderPanel = new SliderPanelBuilder(new Label(cause, ContentMode.HTML)).caption("Error").mode(SliderMode.BOTTOM).tabPosition(SliderTabPosition.END)
                        .style(SliderPanelStyles.COLOR_RED).build();

                // Display the error message in a custom fashion
                layout.addComponent(sliderPanel);

                sliderPanel.scheduleToggle(300);

                // Do the default error handling (optional)
                doDefault(event);
            }
        });

    }
}
