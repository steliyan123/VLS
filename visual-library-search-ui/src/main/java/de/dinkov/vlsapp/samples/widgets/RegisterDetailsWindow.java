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

import de.dinkov.vlsapp.samples.helpclasses.DBConnection;

public class RegisterDetailsWindow extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	RegisterDetailsWindow refToThis;

	public RegisterDetailsWindow() {

		refToThis = this;
		this.center();
		this.setResizable(false);
		this.setModal(true);
		this.setCaption("Login");

		this.setWidth("400px");
		this.setHeight("400px");
		// this.addStyleName(ChameleonTheme.PANEL_BUBBLE);
		VerticalLayout v = new VerticalLayout();

		Label unamelabel = new Label("Username");
		TextField uname = new TextField();
		PasswordField pass = new PasswordField("Password");
		Label emailable = new Label("Email");
		TextField email = new TextField();
		Button registerbutton = new Button("Register");
		registerbutton.setClickShortcut(KeyCode.ENTER);
		// logingbutton.addStyleName(ChameleonTheme.BUTTON_PRIMARY);
		Button cancelbutton = new Button("Cancel");

		registerbutton.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				String name = uname.getValue();
				// String pass = pass.getValue();
				// String email = txt_email.getText();
				DBConnection dbConn = new DBConnection();

				int validUser = -1;
				// validUser = dbConn.validateLogin(uname.getValue(),
				// pass.getValue());
				if (validUser > 0) {
					Notification.show("Success");
					refToThis.setVisible(false);
					UI.getCurrent().getSession().setAttribute("UserID", validUser);
				} else
					Notification.show("Error", Type.ERROR_MESSAGE);
			}
		});

		cancelbutton.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				// close the window

			}
		});

		v.addComponent(unamelabel);
		v.addComponent(uname);

		v.addComponent(pass);
		v.addComponent(registerbutton);
		v.addComponent(cancelbutton);

		v.setSpacing(true);

		v.setMargin(true);
		this.setContent(v);

	}
}
