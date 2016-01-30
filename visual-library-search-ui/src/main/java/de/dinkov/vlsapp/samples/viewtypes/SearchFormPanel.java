package de.dinkov.vlsapp.samples.viewtypes;




import com.vaadin.ui.themes.ChameleonTheme;

import com.vaadin.server.FontAwesome;
import de.dinkov.vlsapp.samples.helpclasses.ImportantPoints;
import de.dinkov.vlsapp.samples.interfaces.*;
import de.dinkov.vlsapp.samples.backend.model.Strategy;
import de.dinkov.vlsapp.samples.diagram.*;

import com.ejt.vaadin.sizereporter.ComponentResizeEvent;
import com.ejt.vaadin.sizereporter.ComponentResizeListener;
import com.ejt.vaadin.sizereporter.SizeReporter;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.MouseEvents;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbsoluteLayout.ComponentPosition;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import de.dinkov.vlsapp.samples.widgets.OnEnterKeyHandler;

import java.awt.*;


public class SearchFormPanel extends Panel {
	private static final long serialVersionUID = 1L;

	private int viewID;
	private HorizontalLayout h1;
	private HorizontalLayout mainLayout;
	private Button searchButton;
	private TextField keywordsTextField;
	private ComboBox strategyComboBox;
	private final ViewPort.ViewPortListener listener;
	private int zoomLevel = 100;
	private ViewPortImpl viewPort;

	private int width = 0;
	private int height = 0;

	public SearchFormPanel(ViewPort.ViewPortListener listener, int id,
			ViewPortImpl v) {
		this.listener = listener;
		this.viewID = id;
		this.viewPort = v;
		this.setCaption("&nbsp;Hold ctrl + right mouse click to hide panels");
		this.setCaptionAsHtml(true);

		buildMainLayout();
		setContent(mainLayout);
	}

	private void buildMainLayout() {

		strategyComboBox = new ComboBox();
		strategyComboBox.addItem(Strategy.PERSON);
		strategyComboBox.addItem(Strategy.TOPIC);
		strategyComboBox.addItem(Strategy.DOCUMENTS);

		strategyComboBox.setNullSelectionAllowed(false);
		strategyComboBox.setTextInputAllowed(false);
		strategyComboBox.setHeight("30px");
		strategyComboBox.setWidth("140px");
		strategyComboBox.addStyleName(ChameleonTheme.COMBOBOX_SELECT_BUTTON);
		strategyComboBox.setValue(strategyComboBox.getItemIds().iterator()
				.next());

		keywordsTextField = new TextField();
		keywordsTextField.focus();
		keywordsTextField.setHeight("30px");
		keywordsTextField.setInputPrompt(" Input search keywords here");
		keywordsTextField.addStyleName(ChameleonTheme.TEXTFIELD_SEARCH);

		OnEnterKeyHandler onEnterHandler = new OnEnterKeyHandler() {
			@Override
			public void onEnterKeyPressed() {
				searchButton.click();
			}
		};
		onEnterHandler.installOn(keywordsTextField);

		searchButton = new Button("search");
		searchButton.addStyleName(ChameleonTheme.BUTTON_DEFAULT);
		searchButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (event.getButton() == searchButton) {
					listener.applySearchFormStrategySearch(
							(Strategy) strategyComboBox.getValue(),
							keywordsTextField.getValue(),
							getNewPanelPosition(), getStartingPoints(), viewID);
					keywordsTextField.clear();
				}
			}
		});

		h1 = new HorizontalLayout();
		h1.addComponent(strategyComboBox);
		h1.addComponent(keywordsTextField);
		h1.addComponent(searchButton);
		h1.setImmediate(false);
		h1.setSpacing(true);
		h1.setDefaultComponentAlignment(Alignment.TOP_CENTER);

		h1.addLayoutClickListener(new LayoutClickListener() {
			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (event.getClickedComponent() instanceof TextField) {
					keywordsTextField.focus();
				}
			}
		});

		mainLayout = new HorizontalLayout();
		mainLayout.setMargin(true);
		mainLayout.addComponent(h1);
		mainLayout.setSpacing(true);
		mainLayout.addLayoutClickListener(new LayoutClickListener() {
			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (MouseEvents.ClickEvent.BUTTON_RIGHT == event.getButton()
						&& event.isCtrlKey()) {
					setVisible(false);
					viewPort.removeConnectionLines(viewID);
				}
			}
		});

		this.setSizeUndefined();

		SizeReporter sizeReporter = new SizeReporter(this);
		sizeReporter.addResizeListener(new ComponentResizeListener() {
			@Override
			public void sizeChanged(ComponentResizeEvent event) {
				width = sizeReporter.getWidth();
				height = sizeReporter.getHeight();
			}
		});

		this.addStyleName(ChameleonTheme.PANEL_BUBBLE);
		this.addStyleName(ChameleonTheme.PANEL_BORDERLESS);

		this.addStyleName("myPanelCaption");
		this.addStyleName("dragCursor");

		this.setIcon(FontAwesome.ARROWS_ALT);

	}

	public ImportantPoints getStartingPoints() {
		ComponentPosition cp = ((AbsoluteLayout) this.getParent())
				.getPosition(this);
		Point topLeft = new Point(Math.round(cp.getLeftValue()), Math.round(cp
				.getTopValue()));
		
		ImportantPoints startingPoints = new ImportantPoints(topLeft,
				Math.round(getWidth()), Math.round(getHeight()));
		return startingPoints;
	}

	public void zoomIn() {
		if (zoomLevel < 130) {
			this.removeStyleName("zoom" + String.valueOf(zoomLevel));
			zoomLevel += 10;
			this.addStyleName("zoom" + String.valueOf(zoomLevel));
		}
	}

	public void zoomOut() {
		if (zoomLevel > 80) {
			this.removeStyleName("zoom" + String.valueOf(zoomLevel));
			zoomLevel -= 10;
			this.addStyleName("zoom" + String.valueOf(zoomLevel));
		}
	}


	private Point getNewPanelPosition() {

		ComponentPosition cp = ((AbsoluteLayout) this.getParent())
				.getPosition(this);
		Point newPanelLocation = new Point(Math.round(cp.getLeftValue()),
				Math.round(cp.getTopValue() + height + 20));
		return newPanelLocation;
	}

	public int getViewID() {
		return viewID;
	}

	public void setViewID(int viewID) {
		this.viewID = viewID;
	}

}
