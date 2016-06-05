package de.dinkov.vlsapp.samples.authentication;

import java.io.Serializable;

import com.vaadin.data.Property;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * UI content when the user is not logged in yet.
 */
public class RegisterScreen extends CssLayout {

    private TextField username;
    private PasswordField password;
    private PasswordField retypePassword;
    private Button register;
    private Button cancel;
    private AccessControl accessControl;
    private SignUpListener signUpListener;


    public RegisterScreen(AccessControl accessControl, SignUpListener signUpListener) {
        this.accessControl = accessControl;
        this.signUpListener = signUpListener;
        buildUI();
        username.focus();
    }

    private void buildUI() {
        addStyleName("login-screen");

        Component registerFormForm = buildRegisterForm();

        VerticalLayout centeringLayout = new VerticalLayout();
        centeringLayout.setStyleName("centering-layout");
        centeringLayout.addComponent(registerFormForm);
        centeringLayout.setComponentAlignment(registerFormForm,
                Alignment.MIDDLE_CENTER);

        CssLayout loginInformation = buildRegisterInformation();

        addComponent(centeringLayout);
        addComponent(loginInformation);
    }

    private Component buildRegisterForm() {
        FormLayout registerForm = new FormLayout();

        registerForm.addStyleName("login-form");
        registerForm.setSizeUndefined();
        registerForm.setMargin(false);

        registerForm.addComponent(username = new TextField("Username", ""));
        username.setWidth(15, Unit.EM);
        registerForm.addComponent(password = new PasswordField("Password"));
        password.setWidth(15, Unit.EM);
        password.setDescription("Please enter your password!");
        registerForm.addComponent(retypePassword = new PasswordField("Confirm password"));
        retypePassword.setWidth(15, Unit.EM);
        retypePassword.setDescription("Please re-enter your password!");
        CssLayout buttons = new CssLayout();
        buttons.setStyleName("buttons");

        registerForm.addComponent(buttons);

        buttons.addComponent(register = new Button("Sign Up!"));
        buttons.addComponent(cancel = new Button("Cancel"));
        register.setDisableOnClick(true);
        register.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (!username.isEmpty()){
                    if (!password.isEmpty() && !retypePassword.isEmpty()){
                        if (password.getValue().equals(retypePassword.getValue())){
                                signUp();
                        }else {
                            Notification.show("Passwords do not match! Please try again.");
                            password.clear();
                            retypePassword.clear();
                            password.focus();
                            register.setEnabled(true);
                        }
                    }else {
                        Notification.show("Passwords cannot be empty fields! Please try again!");
                        password.focus();
                        register.setEnabled(true);
                    }
                }else{
                    Notification.show("User name cannot be empty! Please try again!");
                    username.focus();
                    register.setEnabled(true);
                }
            }

        });
        register.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        register.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        cancel.addStyleName(ValoTheme.BUTTON_LINK);
        cancel.addClickListener( new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                username.clear();
                password.clear();
                retypePassword.clear();
                //UI.getCurrent().getNavigator().navigateTo("");
            }
        });

        return registerForm;
    }
    private CssLayout buildRegisterInformation() {
        CssLayout registerInformation = new CssLayout();
        registerInformation.setStyleName("login-information");
        Label registerInfoText = new Label(
                "<h1>Register</h1>"
                        + "Register in to the Visual Library Search Application to search and explore Digital Libraries",
                ContentMode.HTML);
        registerInformation.addComponent(registerInfoText);
        return registerInformation;
    }

    private void signUp() {
        if (accessControl.signUp(username.getValue(), password.getValue())) {
            signUpListener.signUpSuccessful();
        } else {
            showNotification(new Notification("Sign up failed",
                    "Please try again.",
                    Notification.Type.HUMANIZED_MESSAGE));
            username.focus();
        }
    }

    private void showNotification(Notification notification) {
        // keep the notification visible a little while after moving the
        // mouse, or until clicked
        notification.setDelayMsec(2000);
        notification.show(Page.getCurrent());
    }


    public interface SignUpListener extends Serializable {
        void signUpSuccessful();
    }
}