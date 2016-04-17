package de.dinkov.vlsapp;

import javax.servlet.annotation.WebServlet;

import com.vaadin.ui.Notification;
import de.dinkov.vlsapp.samples.MainScreen;
import de.dinkov.vlsapp.samples.authentication.AccessControl;
import de.dinkov.vlsapp.samples.authentication.BasicAccessControl;
import de.dinkov.vlsapp.samples.authentication.LoginScreen;
import de.dinkov.vlsapp.samples.authentication.LoginScreen.LoginListener;
import de.dinkov.vlsapp.samples.authentication.RegisterScreen;
import de.dinkov.vlsapp.samples.authentication.RegisterScreen.SignUpListener;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import de.dinkov.vlsapp.samples.authentication.RegisterScreen;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Main UI class of the application that shows either the login screen or the
 * main view of the application depending on whether a user is signed in.
 *
 * The @Viewport annotation configures the viewport meta tags appropriately on
 * mobile devices. Instead of device based scaling (default), using responsive
 * layouts.
 */
@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("mytheme")
@Widgetset("de.dinkov.vlsapp.VLSAppWidgetset")
public class VlsAppUI extends UI {
    private static final transient Logger log = LoggerFactory.getLogger(VlsAppUI.class);
    private AccessControl accessControl = new BasicAccessControl();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        log.info("vls");
        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("Visual Library Search");
        if (!accessControl.isUserSignedIn()) {
            setContent(new LoginScreen(accessControl, new LoginListener() {
                @Override
                public void loginSuccessful() {
                    showMainView();
                }
            }));
        } else {
            showMainView();

        }
    }

    protected void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(new MainScreen(VlsAppUI.this));
        getNavigator().navigateTo(getNavigator().getState());
    }

    public static VlsAppUI get() {
        return (VlsAppUI) UI.getCurrent();
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }

    @WebServlet(urlPatterns = "/*", name = "VlsAppUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = VlsAppUI.class, productionMode = false)

    public static class VlsAppUIServlet extends VaadinServlet {
    }
}
