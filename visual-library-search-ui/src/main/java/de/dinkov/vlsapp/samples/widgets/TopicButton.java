package de.dinkov.vlsapp.samples.widgets;

import com.vaadin.server.FontAwesome;

public class TopicButton extends PersonButton {

	public TopicButton(String caption) {
		super(caption);
		setIcon(FontAwesome.FILE_TEXT);
	}

}
