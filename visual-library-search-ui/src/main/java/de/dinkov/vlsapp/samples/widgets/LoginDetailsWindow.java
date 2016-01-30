package de.dinkov.vlsapp.samples.widgets;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ChameleonTheme;

import de.dinkov.vlsapp.samples.helpclasses.DBConnection;

public class LoginDetailsWindow extends Window {

	/**
	 * 
	 */
	LoginDetailsWindow refToThis;
	
	private static final long serialVersionUID = 1L;

	public LoginDetailsWindow() {
		refToThis = this;
		this.center();
		this.setResizable(false);
		this.setModal(true);
		this.setCaption("Login");

		this.setWidth("400px");
		this.setHeight("400px");
		this.addStyleName(ChameleonTheme.PANEL_BUBBLE);
		VerticalLayout v = new VerticalLayout();

		Label unamelabel = new Label("Username");
		TextField uname = new TextField();
		PasswordField pass = new PasswordField("Password");
		Button logingbutton = new Button("Login");
		logingbutton.setClickShortcut(KeyCode.ENTER);
		// logingbutton.addStyleName(ChameleonTheme.BUTTON_PRIMARY);
		Button cancelbutton = new Button("Cancel");

		logingbutton.addClickListener(new Button.ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				DBConnection dbConn = new DBConnection();
				
				int validUser = -1; 
				validUser =		dbConn.validateLogin(uname.getValue(), pass.getValue());
				if (validUser > 0) {
					Notification.show("Success");
					refToThis.setVisible(false);
					UI.getCurrent().getSession().setAttribute("UserID", validUser);
				} else
					Notification.show("Error", Type.ERROR_MESSAGE);
			}
		});

		cancelbutton.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				// close the window

			}
		});
		v.addComponent(unamelabel);
		v.addComponent(uname);
		
		v.addComponent(pass);
		v.addComponent(logingbutton);
		v.addComponent(cancelbutton);

		v.setSpacing(true);

		v.setMargin(true);
		this.setContent(v);
	}

}
