package de.dinkov.vlsapp.samples.widgets;

import de.dinkov.vlsapp.samples.backend.model.Document;
import de.dinkov.vlsapp.samples.backend.model.Topic;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ChameleonTheme;

public class DocDetailsWindow extends Window {

	public DocDetailsWindow(Document doc) {
		this.center();
		this.setResizable(false);
		this.setModal(true);
		this.setCaption("Document Details");

		this.setWidth("500px");
		this.setHeight("400px");
		this.addStyleName(ChameleonTheme.PANEL_BUBBLE);
		VerticalLayout v = new VerticalLayout();
		
		Label title = new Label("<b>Title:</b>&nbsp" + doc.getTitle(),
				ContentMode.HTML);
		
		Label from = new Label("<b>Authors:&nbsp</b>", ContentMode.HTML);
		
		for (String authorName : doc.getAuthors()) {
			from.setValue(from.getValue() + authorName
					+ "&nbsp&nbsp<b>;</b>&nbsp&nbsp");
		}
		
		Label source = new Label("<b>Source:</b>&nbsp" + doc.getSource(),
				ContentMode.HTML);

		Label abstractt = new Label("<b>Abstract:</b>&nbsp"
				+ doc.getAbstractt(), ContentMode.HTML);

		Label rawsource = new Label("<b>Rawsource:</b>&nbsp"
				+ doc.getRawsource(), ContentMode.HTML);

		Label classification = new Label("<b>Classification:</b>&nbsp"
				+ doc.getClassification(), ContentMode.HTML);

		Label url = new Label("<b>URL:</b>&nbsp"
				+ "<a target=\"_blank\" href =" + doc.getURL()
				+ ">" + doc.getURL() + "</a>", ContentMode.HTML);

		Label citedFrom = new Label("<b>Cited by:</b>&nbsp"
				+ doc.getCitedFrom(), ContentMode.HTML);

		Label referencedFrom = new Label("<b>Referenced:</b>&nbsp"
				+ doc.getReferencedCount(), ContentMode.HTML);

		Label topic = new Label("<b>Topic:&nbsp</b>", ContentMode.HTML);

		for (Topic t : doc.getTopics()) {
			topic.setValue(topic.getValue() + t.getName()
					+ "&nbsp&nbsp<b>;</b>&nbsp&nbsp");
		}

		v.addComponent(title);
		v.addComponent(citedFrom);
		v.addComponent(referencedFrom);
		v.addComponent(from);
		v.addComponent(topic);
		v.addComponent(source);
		v.addComponent(abstractt);
		v.addComponent(rawsource);
		v.addComponent(classification);
		v.addComponent(url);

		v.setSpacing(true);

		v.setMargin(true);
		this.setContent(v);
	}
	
	
	
}
