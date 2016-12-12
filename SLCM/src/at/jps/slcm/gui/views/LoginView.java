package at.jps.slcm.gui.views;

import com.ejt.vaadin.loginform.LoginForm;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class LoginView extends LoginForm implements View {

    // public class LoginView extends VerticalLayout implements View {

    /**
     *
     */
    private static final long  serialVersionUID = 7239205107469113417L;

    public final static String USERNAME         = "USERNAME";

    private String             nextViewName;

    public void setPostLoginViewName(final String viewNameToGoTo) {
        nextViewName = viewNameToGoTo;
    }

    // @Override
    @Override
    protected Component createContent(final TextField userNameField, final PasswordField passwordField, final Button loginButton) {

        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setSpacing(true);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent(buildLabels());
        loginPanel.addComponent(buildFields(userNameField, passwordField, loginButton));
        loginPanel.addComponent(new CheckBox("Remember me", true));
        return loginPanel;

        // final HorizontalLayout layout = new HorizontalLayout();
        //
        // final Panel loginPanel = new Panel("Login...");
        //
        // loginPanel.setWidth("350px");
        // loginPanel.setHeight("250px");
        //
        // final FormLayout loginForm = new FormLayout();
        // loginForm.setMargin(true);
        // loginForm.setStyleName("loginForm");
        //
        // loginButton.setEnabled(false);
        // userNameField.setRequired(true);
        // passwordField.setRequired(true);
        //
        // userNameField.addValidator(new StringLengthValidator("Must be between 2 and 10 characters in length", 2, 10, false));
        // passwordField.addValidator(new StringLengthValidator("Must be between 4 and 10 characters in length", 4, 10, false));
        //
        // passwordField.addValueChangeListener(new ValueChangeListener() {
        //
        // @Override
        // public void valueChange(final ValueChangeEvent event) {
        //
        // loginButton.setEnabled(userNameField.isValid() && passwordField.isValid());
        //
        // }
        // });
        // userNameField.addValueChangeListener(new ValueChangeListener() {
        //
        // @Override
        // public void valueChange(final ValueChangeEvent event) {
        //
        // loginButton.setEnabled(userNameField.isValid() && passwordField.isValid());
        //
        // }
        // });
        //
        // loginForm.addComponent(userNameField);
        // loginForm.addComponent(passwordField);
        // loginForm.addComponent(loginButton);
        //
        // loginPanel.setContent(loginForm);
        // loginPanel.setWidth(null);
        //
        // layout.addComponent(loginPanel);
        // layout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
        // layout.setSizeFull();

        // HorizontalLayout layout = new HorizontalLayout();
        // layout.setSpacing(true);
        // layout.setMargin(true);
        //
        // layout.addComponent(userNameField);
        // layout.addComponent(passwordField);
        // layout.addComponent(loginButton);
        // layout.setComponentAlignment(loginButton, Alignment.BOTTOM_LEFT);
        // return layout;
    }

    private Component buildLabels() {
        final CssLayout labels = new CssLayout();
        labels.addStyleName("labels");

        final Label welcome = new Label("Welcome");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(welcome);

        // final Label title = new Label("Sanction Analyzer Case Management");
        // title.setSizeUndefined();
        // title.addStyleName(ValoTheme.LABEL_H3);
        // title.addStyleName(ValoTheme.LABEL_LIGHT);
        // labels.addComponent(title);
        return labels;
    }

    private Component buildFields(final TextField userNameField, final PasswordField passwordField, final Button loginButton) {
        final HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.addStyleName("fields");

        // final TextField username = new TextField("Username");
        userNameField.setIcon(FontAwesome.USER);
        userNameField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        // final PasswordField password = new PasswordField("Password");
        passwordField.setIcon(FontAwesome.LOCK);
        passwordField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        // final Button signin = new Button("Sign In");
        loginButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        loginButton.setClickShortcut(KeyCode.ENTER);
        loginButton.focus();

        fields.addComponents(userNameField, passwordField, loginButton);
        fields.setComponentAlignment(loginButton, Alignment.BOTTOM_LEFT);

        // loginButton.addClickListener(new ClickListener() {
        // @Override
        // public void buttonClick(final ClickEvent event) {
        //// DashboardEventBus.post(new UserLoginRequestedEvent(username
        //// .getValue(), password.getValue()));
        // }
        // });

        return fields;
    }

    @Override
    public String getCaption() {

        return "Who the hell are you ?";
    }

    // // You can also override this method to handle the login directly, instead of using the event mechanism
    @Override
    protected void login(final String userName, final String password) {
        System.err.println("Logged in with user name " + userName + " and password of length " + password.length());

        getUI().getSession().setAttribute(USERNAME, userName);

        if (nextViewName != null) {
            getUI().getNavigator().navigateTo(nextViewName);
        }
        else {
            // nix good but....
            getUI().getNavigator().navigateTo(ListSearchView.ViewName);
        }
    }

    // public LoginView() {
    // setSizeFull();
    // setSpacing(true);
    // setMargin(true);
    //
    // Label label = new Label("Enter your information below to log in.");
    // TextField username = new TextField("Username");
    // TextField password = new TextField("Password");
    //
    // addComponent(label);
    // addComponent(username);
    // addComponent(password);
    // addComponent(loginButton());
    // }

    @Override
    public void enter(final ViewChangeEvent event) {
        Notification.show("Login", "Login to play with this magnificent fundamental great application", Type.ASSISTIVE_NOTIFICATION);
    }

    // private Button loginButton() {
    // Button button = new Button("Log In", new Button.ClickListener() {
    // @Override
    // public void buttonClick(ClickEvent event) {
    // getUI().getNavigator().navigateTo(HitHandlingCoreView.ViewName);
    // }
    // });
    // return button;
    // }

}
