package de.dinkov.vlsapp.samples.viewtypes;

import de.dinkov.vlsapp.samples.diagram.ViewImpl;
import de.dinkov.vlsapp.samples.diagram.ViewPortImpl;
import de.dinkov.vlsapp.samples.widgets.PersonButton;
import de.dinkov.vlsapp.samples.widgets.TopicButton;

import de.dinkov.vlsapp.samples.backend.model.SearchModel;
import de.dinkov.vlsapp.samples.backend.model.Topic;
import de.dinkov.vlsapp.samples.backend.model.Strategy;
import de.dinkov.vlsapp.samples.interfaces.ViewPort;

import org.vaadin.jouni.restrain.Restrain;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ChameleonTheme;

public class TopicView extends ViewImpl {
	HorizontalLayout viewLayout;
	private TopicButton topicButton;

	public TopicView(SearchModel searchModel,
			ViewPort.ViewPortListener listener, int id, ViewPortImpl viewPort,
			int parentViewID) {
		super(searchModel, listener, id, viewPort, parentViewID);
		buildPersonViewLayout();
		this.setContent(viewLayout);
	}

	private void buildPersonViewLayout() {
		this.setSizeUndefined();

		viewLayout = new HorizontalLayout();
		viewLayout.setSpacing(true);
		viewLayout.setMargin(true);

		HorizontalLayout h = new HorizontalLayout();
		h.setSpacing(true);

		HorizontalLayout h1 = new HorizontalLayout();
		h1.setSpacing(true);

		int i = 0;
		for (Topic t : searchModel.getTopicList()) {
			if (i < 1) {
				topicButton = new TopicButton(t.getName());
				topicButton.addStyleName(ChameleonTheme.BUTTON_ICON_ON_TOP);

				h1.addComponent(topicButton);
				h1.addComponent(new Label("    "));
				h.addComponent(h1);

				topicButton.setComponent(createTopicsPopup(t.getName(),
						topicButton));
				i++;
			}
		}

		if (searchModel.getTopicList().size() > 1) {
			TopicButton extraAuthors = new TopicButton("more");
			extraAuthors.setIcon(FontAwesome.FILES_O);
			extraAuthors.addStyleName(ChameleonTheme.BUTTON_ICON_ON_TOP);

			h1.addComponent(extraAuthors);

			VerticalLayout extraButtonsHolder = new VerticalLayout();
			extraButtonsHolder.setSpacing(true);

			Restrain maxHeight = new Restrain(extraButtonsHolder);
			maxHeight.setMaxHeight("190px");

			for (int c = 1; c < searchModel.getTopicList().size() && c < 6; c++) {
				Topic topic = searchModel.getTopicList().get(c);

				Button topicSimpleButton = new Button(topic.getName());
				topicSimpleButton.setIcon(FontAwesome.FILE_TEXT);
				topicSimpleButton
						.addStyleName(ChameleonTheme.BUTTON_BORDERLESS);

				topicSimpleButton.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						Button clicked = event.getButton();

						TopicButton tButtonNew = new TopicButton(clicked
								.getCaption());
						tButtonNew
								.addStyleName(ChameleonTheme.BUTTON_ICON_ON_TOP);
						tButtonNew.setComponent(createTopicsPopup(
								topic.getName(),
								tButtonNew));
						h1.replaceComponent(topicButton, tButtonNew);

						clicked.setCaption(topicButton.getCaption());

						topicButton = tButtonNew;
					}
				});

				extraButtonsHolder.addComponent(topicSimpleButton);
			}
			extraAuthors.setContent(extraButtonsHolder);
		}
		viewLayout.addComponent(h);
	}

 

	private VerticalLayout createTopicsPopup(String topicName,
			PersonButton topicButton) {
		VerticalLayout popupLayout = new VerticalLayout();
		Button highlyCitedPapersTopicButton = new Button("Highly-Cited Papers");
		Button impAuthorsButton = new Button("Important authors");

		highlyCitedPapersTopicButton.addStyleName("hover");
		highlyCitedPapersTopicButton
				.addStyleName(ChameleonTheme.BUTTON_BORDERLESS);
		highlyCitedPapersTopicButton.setIcon(FontAwesome.SEARCH);

		impAuthorsButton.addStyleName(ChameleonTheme.BUTTON_BORDERLESS);
		impAuthorsButton.addStyleName("hover");
		impAuthorsButton.setIcon(FontAwesome.SEARCH);

		highlyCitedPapersTopicButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				listener.applySearchFormStrategySearch(
						Strategy.HIGHLY_CITED_TOPIC, topicName,
						getNewPanelPosition(), getStartingPoints(), viewID);
				topicButton.setPopupVisible(false);
			}
		});
		impAuthorsButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				listener.applySearchFormStrategySearch(
						Strategy.IMPORTANT_AUTHORS_TOPIC, topicName,
						getNewPanelPosition(), getStartingPoints(), viewID);
				topicButton.setPopupVisible(false);
			}
		});
		popupLayout.addComponent(highlyCitedPapersTopicButton);
		popupLayout.addComponent(impAuthorsButton);
		return popupLayout;
	}
}