package de.dinkov.vlsapp.samples.diagram;

import de.dinkov.vlsapp.samples.helpclasses.ImportantPoints;

import java.awt.Point;

import de.dinkov.vlsapp.samples.backend.model.SearchModel;
import  de.dinkov.vlsapp.samples.interfaces.ViewPort;

import com.ejt.vaadin.sizereporter.ComponentResizeEvent;
import com.ejt.vaadin.sizereporter.ComponentResizeListener;
import com.ejt.vaadin.sizereporter.SizeReporter;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbsoluteLayout.ComponentPosition;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.ChameleonTheme;

public class ViewImpl extends Panel implements ClickListener {

	private static final long serialVersionUID = 1L;
	protected ViewPort.ViewPortListener listener;
	protected SearchModel searchModel;
	private int zoomLevel = 100;
	public int viewID;
	public int parentViewID;
	public ViewPortImpl viewPort;
	
	protected int width = 0;
	protected int height = 0;

	public ViewImpl(SearchModel sm, ViewPort.ViewPortListener listener, int id,
			ViewPortImpl vp, int pvID) {
		this.searchModel = sm;
		this.listener = listener;
		this.viewID = id;
		this.viewPort = vp;
		this.parentViewID = pvID;


		switch (searchModel.getStrategy()) {
		case HIGHLY_CITED_AUTHOR:
			this.setCaption(" " + searchModel.getStrategy()
					+ " from: "
					+ searchModel.getKeywords());
			break;
		case HIGHLY_REFERENCED_AUTHOR:
			this.setCaption(" " + searchModel.getStrategy()
					+ " from: "
					+ searchModel.getKeywords());
			break;
		default:
			this.setCaption(" " + searchModel.getStrategy() + ": "
					+ searchModel.getKeywords());
		}

		this.addStyleName(ChameleonTheme.PANEL_BUBBLE);
		this.addStyleName("myPanelCaption");
		this.addStyleName("dragCursor");
		this.setIcon(FontAwesome.ARROWS_ALT);
		this.addClickListener(this);

		SizeReporter sizeReporter = new SizeReporter(this);
		sizeReporter.addResizeListener(new ComponentResizeListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void sizeChanged(ComponentResizeEvent event) {
				width = sizeReporter.getWidth();
				height = sizeReporter.getHeight();
			}
		});
	}
	



	public int getParentViewID() {
		return parentViewID;
	}

	public void setParentViewID(int parentViewID) {
		this.parentViewID = parentViewID;
	}

	public int getViewID() {
		return viewID;
	}

	public void setViewID(int id) {
		this.viewID = id;
	}

	public SearchModel getSearchModel() {
		return searchModel;
	}

	public void setSearchModel(SearchModel searchModel) {
		this.searchModel = searchModel;
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


	@Override
	public void click(ClickEvent event) {
		if (ClickEvent.BUTTON_RIGHT == event.getButton()
				&& event.isCtrlKey()) {
			this.setVisible(false);
			viewPort.views.remove(this);
			viewPort.removeConnectionLines(viewID);
		}
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
 
	public Point getNewPanelPosition() {
		ComponentPosition cp = ((AbsoluteLayout) this.getParent())
				.getPosition(this);
		Point newPanelLocation = new Point(Math.round(cp.getLeftValue()),
				Math.round(cp.getTopValue() + height + 20));
		return newPanelLocation;
	}
}
