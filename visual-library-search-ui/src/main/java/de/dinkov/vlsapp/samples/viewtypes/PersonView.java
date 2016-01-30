package de.dinkov.vlsapp.samples.viewtypes;


import com.vaadin.ui.themes.ChameleonTheme;
import org.vaadin.hene.popupbutton.PopupButton;

import com.vaadin.server.FontAwesome;

import de.dinkov.vlsapp.samples.widgets.PersonButton;
import de.dinkov.vlsapp.samples.backend.model.*;
import de.dinkov.vlsapp.samples.interfaces.*;
import de.dinkov.vlsapp.samples.diagram.*;
import org.vaadin.jouni.restrain.Restrain;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class PersonView extends ViewImpl {
	HorizontalLayout viewLayout;
	private PersonButton authorButton;

	public PersonView(SearchModel searchModel,
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
		for (Author author : searchModel.getPersonList()) {
			if (i < 1) {
				authorButton = new PersonButton(author.getName());
				authorButton.addStyleName(ChameleonTheme.BUTTON_ICON_ON_TOP);

				h1.addComponent(authorButton);
				h1.addComponent(new Label("    "));
				h.addComponent(h1);

				authorButton.setComponent(createAuthorsPopup(author,
						authorButton, null));
				i++;
			}
		}

		if (searchModel.getPersonList().size() > 1) {
			PersonButton extraAuthors = new PersonButton("more");
			extraAuthors.setIcon(FontAwesome.USERS);
			extraAuthors.addStyleName(ChameleonTheme.BUTTON_ICON_ON_TOP);

			h1.addComponent(extraAuthors);

			VerticalLayout extraButtonsHolder = new VerticalLayout();
			extraButtonsHolder.setSpacing(true);

			Restrain maxHeight = new Restrain(extraButtonsHolder);
			maxHeight.setMaxHeight("240px");

			extraAuthors.setContent(extraButtonsHolder);

			for (int c = 1; c < searchModel.getPersonList().size() && c < 6; c++) {
				String authorName = searchModel.getPersonList().get(c)
						.getName();
				Author author = new Author(authorName);

				Button authorSimpleButton = new Button(author.getName());
				authorSimpleButton.setIcon(FontAwesome.USER);
				authorSimpleButton
						.addStyleName(ChameleonTheme.BUTTON_BORDERLESS);
				authorSimpleButton.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						Button clicked = event.getButton();

						PersonButton auButtonNew = new PersonButton(clicked
								.getCaption());
						auButtonNew
								.addStyleName(ChameleonTheme.BUTTON_ICON_ON_TOP);
						auButtonNew.setComponent(createAuthorsPopup(author,
								auButtonNew, extraAuthors));
						h1.replaceComponent(authorButton, auButtonNew);
						clicked.setCaption(authorButton.getCaption());
						authorButton = auButtonNew;
					}
				});
				extraButtonsHolder.addComponent(authorSimpleButton);
			}
		}
		viewLayout.addComponent(h);
	}

	private VerticalLayout createAuthorsPopup(final Author author,
			final PopupButton authorButton, final PersonButton extraAuthors) {
		VerticalLayout popupLayout = new VerticalLayout();
		Button highlyCitedPapersButton = new Button("Highly-Cited Papers");
		
		Button highlyReferencedPapersButton = new Button(
				"Highly-Referenced Papers");

		Button mainCoAuthorsButton = new Button("Main co-authors");
		Button mainResearchTopicsButton = new Button("Main research topics");

		highlyCitedPapersButton.addStyleName("hover");
		highlyCitedPapersButton.addStyleName(ChameleonTheme.BUTTON_BORDERLESS);
		highlyCitedPapersButton.setIcon(FontAwesome.SEARCH);

		mainCoAuthorsButton.addStyleName("hover");
		mainCoAuthorsButton.addStyleName(ChameleonTheme.BUTTON_BORDERLESS);
		mainCoAuthorsButton.setIcon(FontAwesome.SEARCH);

		mainResearchTopicsButton.addStyleName("hover");
		mainResearchTopicsButton.addStyleName(ChameleonTheme.BUTTON_BORDERLESS);
		mainResearchTopicsButton.setIcon(FontAwesome.SEARCH);

		highlyReferencedPapersButton
				.addStyleName(ChameleonTheme.BUTTON_BORDERLESS);
		highlyReferencedPapersButton.addStyleName("hover");
		highlyReferencedPapersButton.setIcon(FontAwesome.SEARCH);

		highlyCitedPapersButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				listener.applyPersonStategy(Strategy.HIGHLY_CITED_AUTHOR, author,
						getNewPanelPosition(), getStartingPoints(), viewID);
				authorButton.setPopupVisible(false);
				if (extraAuthors != null) {
					extraAuthors.setPopupVisible(false);
				}
			}
		});
		highlyReferencedPapersButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				listener.applyPersonStategy(Strategy.HIGHLY_REFERENCED_AUTHOR,
						author, getNewPanelPosition(),
						getStartingPoints(), viewID);
				authorButton.setPopupVisible(false);
				if (extraAuthors != null) {
					extraAuthors.setPopupVisible(false);
				}
			}
		});
		mainCoAuthorsButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				listener.applyPersonStategy(Strategy.MAIN_COAUTHORS_AUTHOR,
						author, getNewPanelPosition(), getStartingPoints(),
						viewID);
				authorButton.setPopupVisible(false);
				if (extraAuthors != null) {
					extraAuthors.setPopupVisible(false);
				}
			}
		});
		mainResearchTopicsButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				listener.applyPersonStategy(Strategy.MAIN_TOPICS_AUTHOR,
						author, getNewPanelPosition(), getStartingPoints(),
						viewID);
				authorButton.setPopupVisible(false);
				if (extraAuthors != null) {
					extraAuthors.setPopupVisible(false);
				}
			}
		});

		popupLayout.addComponent(highlyCitedPapersButton);
		popupLayout.addComponent(highlyReferencedPapersButton);
		popupLayout.addComponent(mainCoAuthorsButton);
		popupLayout.addComponent(mainResearchTopicsButton);
		return popupLayout;
	}
}