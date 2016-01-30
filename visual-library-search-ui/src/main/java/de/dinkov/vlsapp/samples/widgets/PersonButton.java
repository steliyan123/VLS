package de.dinkov.vlsapp.samples.widgets;

import org.vaadin.hene.popupbutton.PopupButton;
import org.vaadin.marcus.MouseEvents;
import org.vaadin.marcus.MouseEvents.MouseOutListener;
import org.vaadin.marcus.MouseEvents.MouseOverListener;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.themes.ChameleonTheme;

public class PersonButton extends PopupButton implements MouseOverListener,
		MouseOutListener {

	public PersonButton(String caption) {

		setCaption(caption);
		setIcon(FontAwesome.USER);

		MouseEvents mouseEvents = MouseEvents.enableFor(this);
		mouseEvents.addMouseOverListener(this);
		mouseEvents.addMouseOutListener(this);

		this.addStyleName("myfont");
		this.addStyleName("changeArrow");
		this.addStyleName(ChameleonTheme.BUTTON_BORDERLESS);
	}

	@Override
	public void mouseOut() {
		this.removeStyleName("showArrow");
		this.addStyleName("changeArrow");
	}

	@Override
	public void mouseOver() {
		this.removeStyleName("changeArrow");
		this.addStyleName("showArrow");
	}
}
