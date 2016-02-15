package at.jps.slcm;

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
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import at.jps.sl.gui.AdapterHelper;
import at.jps.sl.gui.util.GUIConfigHolder;
import at.jps.slcm.gui.views.HitHandlingView;
import at.jps.slcm.gui.views.ListSearchView;
import at.jps.slcm.gui.views.LoginView;

@SuppressWarnings("serial")
@Theme("slcm")
public class SlcmUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = SlcmUI.class, widgetset = "at.jps.slcm.SlcmWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    // private Grid tableTXWithHits = new Grid();
    // private Grid tableResults = new Grid();
    // private Grid tableWordHits = new Grid();
    // private Grid tableEntityNameDetails = new Grid();
    // private Grid tableEntityRelations = new Grid();
    //
    // private TextField textField_AnalysisTime;
    // private TextArea textPane_Comment;
    // private ComboBox textPane_LegalBack;
    // private TextField textField_Type;
    // private TextField textField_ListDescription;
    // private TextArea textPane_Remark;
    //
    // private ComboBox comboBox_Category;

    private static AdapterHelper guiAdapter  = new AdapterHelper();

    private static boolean       initialized = false;

    public static String         newline     = System.getProperty("line.separator");
                                             // -----------------------------

    // private DisplayResultService displayResultService = new DisplayResultService();
    // private DisplayMessageService displayMessageService = new DisplayMessageService();
    // private DisplayNameDetailsService displayNameDetailsService = new DisplayNameDetailsService();
    // private DisplayWordHitService displayWordHitService = new DisplayWordHitService();
    // private DisplayRelationService displayRelationService = new DisplayRelationService();
    //
    // private String selectedToken = "";
    // private String selectedFieldContent = "";

    Navigator                    navigator;

    String                       loggedInUser;

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

        // left slider
        // final VerticalLayout leftDummyContent = dummyContent("Oke", 3);
        //
        // leftDummyContent.setWidth(200, Unit.PIXELS);
        // final SliderPanel leftSlider = new SliderPanelBuilder(leftDummyContent, "Menu").mode(SliderMode.LEFT).tabPosition(SliderTabPosition.MIDDLE).autoCollapseSlider(true).animationDuration(500)
        // .flowInContent(true).style(SliderPanelStyles.COLOR_WHITE).build();

        final VerticalLayout layout = new VerticalLayout();
        // layout.setMargin(true);
        // layout.setSpacing(true);
        layout.setSizeFull();
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        final HorizontalLayout contentLayout = new HorizontalLayout();
        contentLayout.setSpacing(false);
        contentLayout.setSizeFull();

        final VerticalLayout container = new VerticalLayout();
        container.setSizeFull();
        container.setMargin(true);
        container.setSpacing(true);

        // contentLayout.addComponent(leftSlider);
        contentLayout.addComponent(container);
        contentLayout.setExpandRatio(container, 1);

        layout.addComponent(contentLayout);

        setContent(layout);

        final ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(container);

        navigator = new Navigator(UI.getCurrent(), viewDisplay);
        navigator.addView("", new LoginView());
        navigator.addView(HitHandlingView.ViewName, new HitHandlingView(guiAdapter));
        navigator.addView(ListSearchView.ViewName, new ListSearchView(guiAdapter));

        setErrorHandler(layout);

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
