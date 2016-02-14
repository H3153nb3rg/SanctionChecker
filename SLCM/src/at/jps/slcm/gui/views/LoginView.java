package at.jps.slcm.gui.views;

import com.ejt.vaadin.loginform.LoginForm;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class LoginView extends LoginForm implements View {

    // public class LoginView extends VerticalLayout implements View {

    /**
     *
     */
    private static final long serialVersionUID = 7239205107469113417L;

    // @Override
    @Override
    protected Component createContent(final TextField userNameField, final PasswordField passwordField, final Button loginButton) {

        final HorizontalLayout layout = new HorizontalLayout();

        final Panel loginPanel = new Panel("Login...");

        loginPanel.setWidth("350px");
        loginPanel.setHeight("250px");

        final FormLayout loginForm = new FormLayout();
        loginForm.setMargin(true);
        loginForm.setStyleName("loginForm");

        loginButton.setEnabled(false);
        userNameField.setRequired(true);
        passwordField.setRequired(true);

        userNameField.addValidator(new StringLengthValidator("Must be between 2 and 10 characters in length", 2, 10, false));
        passwordField.addValidator(new StringLengthValidator("Must be between 4 and 10 characters in length", 4, 10, false));

        passwordField.addValueChangeListener(new ValueChangeListener() {

            @Override
            public void valueChange(final ValueChangeEvent event) {

                loginButton.setEnabled(userNameField.isValid() && passwordField.isValid());

            }
        });
        userNameField.addValueChangeListener(new ValueChangeListener() {

            @Override
            public void valueChange(final ValueChangeEvent event) {

                loginButton.setEnabled(userNameField.isValid() && passwordField.isValid());

            }
        });

        loginForm.addComponent(userNameField);
        loginForm.addComponent(passwordField);
        loginForm.addComponent(loginButton);

        loginPanel.setContent(loginForm);
        loginPanel.setWidth(null);

        layout.addComponent(loginPanel);
        layout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
        layout.setSizeFull();

        // HorizontalLayout layout = new HorizontalLayout();
        // layout.setSpacing(true);
        // layout.setMargin(true);
        //
        // layout.addComponent(userNameField);
        // layout.addComponent(passwordField);
        // layout.addComponent(loginButton);
        // layout.setComponentAlignment(loginButton, Alignment.BOTTOM_LEFT);
        return layout;
    }

    @Override
    public String getCaption() {

        return "Who the hell are you ?";
    }

    // // You can also override this method to handle the login directly, instead of using the event mechanism
    @Override
    protected void login(final String userName, final String password) {
        System.err.println("Logged in with user name " + userName + " and password of length " + password.length());

        // getUI().getNavigator().navigateTo(HitHandlingView.ViewName);
        getUI().getNavigator().navigateTo(ListSearchView.ViewName);
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
        Notification.show("Howdy Fella.");
    }

    // private Button loginButton() {
    // Button button = new Button("Log In", new Button.ClickListener() {
    // @Override
    // public void buttonClick(ClickEvent event) {
    // getUI().getNavigator().navigateTo(HitHandlingView.ViewName);
    // }
    // });
    // return button;
    // }

}
