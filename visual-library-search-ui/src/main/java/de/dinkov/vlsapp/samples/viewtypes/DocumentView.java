package de.dinkov.vlsapp.samples.viewtypes;

import java.util.ArrayList;

import com.vaadin.ui.themes.ChameleonTheme;
import org.vaadin.hene.popupbutton.PopupButton;

import com.porotype.iconfont.FontAwesome.Icon;
import com.porotype.iconfont.FontAwesome.IconVariant;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.zipsoft.clicklabel.ClickLabelEvent;
import com.zipsoft.clicklabel.ClickLabelExtension;
import com.zipsoft.clicklabel.ClickLabelListener;

import de.dinkov.vlsapp.samples.widgets.DocDetailsWindow;
import de.dinkov.vlsapp.samples.widgets.PersonButton;
import de.dinkov.vlsapp.samples.backend.model.*;
import de.dinkov.vlsapp.samples.interfaces.*;
import de.dinkov.vlsapp.samples.diagram.*;
public class DocumentView extends ViewImpl {

	private static final long serialVersionUID = 1L;
	VerticalLayout vLayout;
	HorizontalLayout mainLayout;
	ArrayList<VerticalLayout> docLayouts = new ArrayList<VerticalLayout>();
	private VerticalLayout visibleDoc;

	public DocumentView(SearchModel searchModel, ViewPort.ViewPortListener listener, int id, ViewPortImpl viewPort,
			int parentViewID) {
		super(searchModel, listener, id, viewPort, parentViewID);
		buildDocumentViewLayout();
	}

	@SuppressWarnings("deprecation")
	private void buildDocumentViewLayout() {
		vLayout = new VerticalLayout();
		vLayout.setSpacing(true);
		vLayout.setMargin(true);

		int i = 0;
		for (Document doc : searchModel.getDocumentList()) {
			if (i < 4) {
				VerticalLayout v1 = new VerticalLayout();

				HorizontalLayout htitle = new HorizontalLayout();
				PopupButton moreDocsButton = new PopupButton();
				moreDocsButton.addStyleName(ChameleonTheme.BUTTON_BORDERLESS);
				moreDocsButton.setIcon(FontAwesome.ELLIPSIS_V);
				moreDocsButton.addStyleName("hideArrow");

				if (searchModel.getDocumentList().size() == 1)
					moreDocsButton.setVisible(false);

				if (searchModel.getDocumentList().size() > 1) {
					createMoreDocsButtonContent(moreDocsButton, i);
				}

				Label title = new Label(Icon.book.variant(IconVariant.PULL_LEFT, IconVariant.SIZE_2X)
						+ "<b><label style=\"color:blue;cursor: zoom-in;\">" + doc.getTitle()
						+ "</label></b>&nbsp;&nbsp;ID:&nbsp;" + doc.getId(), ContentMode.HTML);

				title.addStyleName("myfont");
				title.addStyleName("detailsCursor");
				title.setDescription("click for details");
				title.setWidth("350px");
				final ClickLabelExtension titleExtension = new ClickLabelExtension(title);
				titleExtension.addClickLabelListener(new ClickLabelListener() {
					// TODO open details screen
					@Override
					public void onLabelClick(ClickLabelEvent event) {
						DocDetailsWindow w = new DocDetailsWindow(doc);
						UI.getCurrent().addWindow(w);
					}

				});

				htitle.addComponent(title);
				htitle.addComponent(new Label("&nbsp;&nbsp;", ContentMode.HTML));
				htitle.addComponent(moreDocsButton);

				v1.addComponent(htitle);
				v1.addComponent(new Hr());

				Label from = new Label("<b>Authors:</b>", ContentMode.HTML);
				from.addStyleName("myfont");

				HorizontalLayout h1 = new HorizontalLayout();
				h1.setSpacing(true);
				h1.addComponent(from);
				int y = 0;
				for (String authorName : doc.getAuthors()) {
					if (y < 2) {
						Author author = new Author(authorName);
						final PersonButton authorButton = new PersonButton(authorName);
						authorButton.setContent(createAuthorsPopup(author, authorButton));
						h1.addComponent(authorButton);
						y++;
					}
				}

				if (doc.getAuthors().size() > 2) {
					PersonButton extraAuthors = new PersonButton("other");
					extraAuthors.setIcon(FontAwesome.USERS);
					h1.addComponent(extraAuthors);

					VerticalLayout extraAuthorsButtonsHolder = new VerticalLayout();

					for (int c = 2; c < doc.getAuthors().size(); c++) {
						String authorName = doc.getAuthors().get(c);
						Author author = new Author(authorName);
						PersonButton authorButton = new PersonButton(authorName);
						authorButton.setContent(createAuthorsPopup(author, authorButton));
						extraAuthorsButtonsHolder.addComponent(authorButton);
						c++;
					}
					extraAuthors.setContent(extraAuthorsButtonsHolder);
				}

				Label topicLabel = new Label("<b>Topic:</b>&nbsp;", ContentMode.HTML);
				topicLabel.addStyleName("myfont");
				String topicName = "";
				if (doc.getTopics().size() != 0) {
					topicName = doc.getTopics().get(0).getName();
				}

				PopupButton topicButton = new PopupButton(topicName);
				topicButton.setIcon(FontAwesome.SEARCH);
				topicButton.addStyleName(ChameleonTheme.BUTTON_BORDERLESS);
				topicButton.addStyleName("myfont");
				topicButton.addStyleName("hideArrow");
				topicButton.setContent(createTopicsPopup(topicName, topicButton));

				HorizontalLayout h2 = new HorizontalLayout();
				h2.addComponent(topicLabel);
				h2.addComponent(topicButton);

				if (doc.getTopics().size() > 1) {
					PersonButton extraTopics = new PersonButton("other");
					extraTopics.setIcon(FontAwesome.SEARCH);
					h2.addComponent(extraTopics);

					VerticalLayout extraTopicsButtonsHolder = new VerticalLayout();

					for (int c = 1; c < doc.getTopics().size(); c++) {
						String extraTopicName = doc.getTopics().get(c).getName();
						PersonButton extraTopicButton = new PersonButton(extraTopicName);
						extraTopicButton.setContent(createTopicsPopup(extraTopicName, extraTopicButton));
						extraTopicsButtonsHolder.addComponent(extraTopicButton);
						c++;
					}
					extraTopics.setContent(extraTopicsButtonsHolder);
				}

				Label citedLabel = new Label(
						"<b>Cited by:</b>&nbsp;" + doc.getCitedFrom() + "&nbsp;Papers&nbsp;&nbsp;&nbsp;",
						ContentMode.HTML);
				citedLabel.addStyleName("myfont");

				Label refLabel = new Label("&nbsp;<b>Referenced:</b>&nbsp;" + doc.getReferencedCount() + "&nbsp;Papers",
						ContentMode.HTML);
				refLabel.addStyleName("myfont");

				HorizontalLayout h3 = new HorizontalLayout();
				h3.addComponent(citedLabel);
				h3.addComponent(refLabel);

				PopupButton docStrategiesButton = new PopupButton("<b>search related documents</b> ");
				docStrategiesButton.setIcon(FontAwesome.SEARCH);
				docStrategiesButton.setCaptionAsHtml(true);
				docStrategiesButton.addStyleName(ChameleonTheme.BUTTON_BORDERLESS);
				docStrategiesButton.setContent(createDocStategiesPopup(doc, docStrategiesButton));
				docStrategiesButton.addStyleName("hideArrow");
				docStrategiesButton.addStyleName("myfont14");

				Label year = new Label("&nbsp;<b>Year:</b>&nbsp;&nbsp;&nbsp;" + doc.getYear(), ContentMode.HTML);
				year.addStyleName("myfont");

				HorizontalLayout h4 = new HorizontalLayout();
				h4.setSpacing(true);
				h4.addComponent(year);
				h4.addComponent(docStrategiesButton);

				v1.addComponent(h3);
				v1.addComponent(h1);
				v1.addComponent(h2);
				v1.addComponent(h4);

				docLayouts.add(v1);

			} else
				break;
			i++;
		}

		visibleDoc = docLayouts.get(0);
		vLayout.addComponent(visibleDoc);

		mainLayout = new HorizontalLayout();
		mainLayout.addComponent(vLayout);
		mainLayout.setSizeUndefined();
		mainLayout.setWidth("400px");

		this.setSizeUndefined();
		this.setContent(mainLayout);

	}

	private Component createDocStategiesPopup(Document doc, PopupButton docStrategiesButton) {
		VerticalLayout popupLayout = new VerticalLayout();
		Button citedByButton = new Button("Cited-by");
		Button refByButton = new Button("Referenced");
		Button similarTopicsButton = new Button("Similar Topics");

		citedByButton.addStyleName("hover");
		citedByButton.addStyleName(ChameleonTheme.BUTTON_BORDERLESS);
		citedByButton.setIcon(FontAwesome.SEARCH);

		refByButton.addStyleName(ChameleonTheme.BUTTON_BORDERLESS);
		refByButton.addStyleName("hover");
		refByButton.setIcon(FontAwesome.SEARCH);

		similarTopicsButton.addStyleName(ChameleonTheme.BUTTON_BORDERLESS);
		similarTopicsButton.addStyleName("hover");
		similarTopicsButton.setIcon(FontAwesome.SEARCH);

		citedByButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				listener.applyDocumentStategy(Strategy.CITED_BY_DOC, doc, getNewPanelPosition(), getStartingPoints(),
						viewID);
				docStrategiesButton.setPopupVisible(false);
			}
		});
		refByButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				listener.applyDocumentStategy(Strategy.REFERENCED_DOC, doc, getNewPanelPosition(), getStartingPoints(),
						viewID);
				docStrategiesButton.setPopupVisible(false);
			}
		});
		similarTopicsButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				listener.applyDocumentStategy(Strategy.SIMILAR_TOPICS_DOC, doc, getNewPanelPosition(),
						getStartingPoints(), viewID);
				docStrategiesButton.setPopupVisible(false);
			}
		});

		popupLayout.addComponent(citedByButton);
		popupLayout.addComponent(refByButton);
		popupLayout.addComponent(similarTopicsButton);
		return popupLayout;
	}

	private VerticalLayout createAuthorsPopup(final Author author, final PopupButton authorButton) {
		VerticalLayout popupLayout = new VerticalLayout();
		Button highlyCitedPapersButton = new Button("Highly-Cited Papers");
		Button highlyReferencedPapersButton = new Button("Highly-Referenced Papers");

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

		highlyReferencedPapersButton.addStyleName(ChameleonTheme.BUTTON_BORDERLESS);
		highlyReferencedPapersButton.addStyleName("hover");
		highlyReferencedPapersButton.setIcon(FontAwesome.SEARCH);

		highlyCitedPapersButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {

				listener.applyPersonStategy(Strategy.HIGHLY_CITED_AUTHOR, author, getNewPanelPosition(),
						getStartingPoints(), viewID);
				authorButton.setPopupVisible(false);
			}
		});
		highlyReferencedPapersButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				listener.applyPersonStategy(Strategy.HIGHLY_REFERENCED_AUTHOR, author, getNewPanelPosition(),
						getStartingPoints(), viewID);
				authorButton.setPopupVisible(false);
			}
		});

		mainCoAuthorsButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				listener.applyPersonStategy(Strategy.MAIN_COAUTHORS_AUTHOR, author, getNewPanelPosition(),
						getStartingPoints(), viewID);
				authorButton.setPopupVisible(false);
			}
		});
		mainResearchTopicsButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				listener.applyPersonStategy(Strategy.MAIN_TOPICS_AUTHOR, author, getNewPanelPosition(),
						getStartingPoints(), viewID);
				authorButton.setPopupVisible(false);
			}
		});
		popupLayout.addComponent(highlyCitedPapersButton);
		popupLayout.addComponent(highlyReferencedPapersButton);
		popupLayout.addComponent(mainCoAuthorsButton);
		popupLayout.addComponent(mainResearchTopicsButton);
		return popupLayout;
	}

	private VerticalLayout createTopicsPopup(String topicName, PopupButton topicButton) {
		VerticalLayout popupLayout = new VerticalLayout();
		Button highlyCitedPapersTopicButton = new Button("Highly-Cited Papers");
		Button impAuthorsButton = new Button("Important authors");

		highlyCitedPapersTopicButton.addStyleName("hover");
		highlyCitedPapersTopicButton.addStyleName(ChameleonTheme.BUTTON_BORDERLESS);
		highlyCitedPapersTopicButton.setIcon(FontAwesome.SEARCH);

		impAuthorsButton.addStyleName(ChameleonTheme.BUTTON_BORDERLESS);
		impAuthorsButton.addStyleName("hover");
		impAuthorsButton.setIcon(FontAwesome.SEARCH);

		highlyCitedPapersTopicButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				listener.applySearchFormStrategySearch(Strategy.HIGHLY_CITED_TOPIC, topicName, getNewPanelPosition(),
						getStartingPoints(), viewID);
				topicButton.setPopupVisible(false);
			}
		});
		impAuthorsButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				listener.applySearchFormStrategySearch(Strategy.IMPORTANT_AUTHORS_TOPIC, topicName,
						getNewPanelPosition(), getStartingPoints(), viewID);
				topicButton.setPopupVisible(false);
			}
		});

		popupLayout.addComponent(highlyCitedPapersTopicButton);
		popupLayout.addComponent(impAuthorsButton);
		return popupLayout;
	}

	class Hr extends Label {
		Hr() {
			super("<hr/>", Label.CONTENT_XHTML);
		}
	}

	private void createMoreDocsButtonContent(PopupButton moreDocsButton, int displayedDocCountInList) {

		VerticalLayout extraDocs = new VerticalLayout();
		extraDocs.addComponent(new Hr());
		int x = 0;
		for (Document extraDoc : searchModel.getDocumentList()) {
			if (x < 3 && x != displayedDocCountInList) {

				Label extraDocLabel = new Label(Icon.book.variant(IconVariant.PULL_LEFT, IconVariant.SIZE_2X)
						+ "<b><label style=\"color:blue;cursor: alias;\">" + extraDoc.getTitle()
						+ "</label></b>&nbsp;&nbsp;ID:&nbsp;" + extraDoc.getId(), ContentMode.HTML);
				extraDocLabel.setWidth("350px");

				HorizontalLayout hh = new HorizontalLayout();
				Label citedLabel = new Label(
						"<b>Cited by:</b>&nbsp;" + extraDoc.getCitedFrom() + "&nbsp;Papers&nbsp;&nbsp;&nbsp;",
						ContentMode.HTML);
				Label refLabel = new Label(
						"&nbsp;<b>Referenced:</b>&nbsp;" + extraDoc.getReferencedCount() + "&nbsp;Papers",
						ContentMode.HTML);

				extraDocLabel.addStyleName("myfont");
				extraDocLabel.addStyleName("handCursor");
				citedLabel.addStyleName("myfont");
				refLabel.addStyleName("myfont");

				hh.addComponent(citedLabel);
				hh.addComponent(refLabel);

				extraDocs.addComponent(extraDocLabel);
				extraDocs.addComponent(hh);
				extraDocs.addComponent(new Hr());

				final ClickLabelExtension extraDocExtension = new ClickLabelExtension(extraDocLabel);

				final int count = x;
				extraDocExtension.addClickLabelListener(new ClickLabelListener() {
					// TODO open details screen
					@Override
					public void onLabelClick(ClickLabelEvent event) {
						vLayout.replaceComponent(visibleDoc, docLayouts.get(count));
						visibleDoc = docLayouts.get(count);
						moreDocsButton.setPopupVisible(false);
					}

				});
			}
			x++;
		}
		moreDocsButton.setContent(extraDocs);

	}
}