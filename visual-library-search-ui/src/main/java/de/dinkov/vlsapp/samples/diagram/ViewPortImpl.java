package de.dinkov.vlsapp.samples.diagram;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;


import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import de.dinkov.vlsapp.samples.interfaces.ViewPort;

import de.dinkov.vlsapp.samples.helpclasses.ConnectionLine;
import de.dinkov.vlsapp.samples.helpclasses.ImportantPoints;
import de.dinkov.vlsapp.samples.helpclasses.Path;
import de.dinkov.vlsapp.samples.viewtypes.DocumentView;
import de.dinkov.vlsapp.samples.viewtypes.PersonView;
import de.dinkov.vlsapp.samples.viewtypes.SearchFormPanel;
import de.dinkov.vlsapp.samples.viewtypes.TopicView;
import de.dinkov.vlsapp.samples.widgets.MyCustomDropHandler;
import de.dinkov.vlsapp.samples.backend.model.SearchModel;
import de.dinkov.vlsapp.samples.backend.model.Strategy;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultAbsoluteLayoutDropHandler;

public class ViewPortImpl extends CustomComponent implements ViewPort {

	private static final String NO_RESULTS_WARNING_MSG = "No results could be found.";
	private static final int WARNING_NOTIFICATON_DELAY = 3000;
	private static final int ratio = 16000;
	private static final int zero_coordinate = 5000;
	private static final int WAIT_FOR_CLICK_TIME = 0;

	public ArrayList<ConnectionLine> lines = new ArrayList<ConnectionLine>();
	int nodesIDGenerator = 0;

	// private ComponentPosition canvasPosition;

	public boolean linesUpdated = false;
	private int zoomLevel = 100;

	public DDAbsoluteLayout viewPortLayout;
	private ViewPort.ViewPortListener listener;

	public DDAbsoluteLayout viewHolder;
	AbsoluteLayout.ComponentPosition viewHolderPosition;

	private LayoutClickListener viewHolderClickListener;
	private boolean viewHolderToBeClicked = false;

	protected final List<ViewImpl> views = new ArrayList<ViewImpl>();

	// private Canvas canvas;

	public ViewPortImpl() {
		// canvas = c;

		buildViewPortLayout();
		setCompositionRoot(viewPortLayout);
	}

	private void buildViewPortLayout() {

		viewPortLayout = new DDAbsoluteLayout();
		viewPortLayout.setDragMode(LayoutDragMode.CLONE);
		viewPortLayout.setDropHandler(new DefaultAbsoluteLayoutDropHandler());

		viewHolder = new DDAbsoluteLayout();
		viewHolder.setDragMode(LayoutDragMode.CAPTION);
		ViewPortImpl v = this;
		viewHolder.addLayoutClickListener(new LayoutClickListener() {
			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (event.isDoubleClick() && (event.getClickedComponent() == null)) {
					nodesIDGenerator++;
					SearchFormPanel s = new SearchFormPanel(listener, nodesIDGenerator, v);
					viewHolder.addComponent(s,
							"left:" + event.getRelativeX() + "px; top:" + event.getRelativeY() + "px;");
				}
			}
		});

		viewHolder.setWidth(String.valueOf(ratio));
		viewHolder.setHeight(String.valueOf(ratio));

		// canvas = new Canvas();
		// canvas.setWidth(String.valueOf(ratio) + "px");
		// canvas.setHeight(String.valueOf(ratio) + "px");

		// viewPortLayout.addComponent(canvas,
		// "left:" + String.valueOf(-zero_coordinate) + "px; top: "
		// + String.valueOf(-zero_coordinate) + "px;");

		// canvasPosition = viewPortLayout.getPosition(canvas);

		viewPortLayout.addComponent(viewHolder,
				"left:" + String.valueOf(-zero_coordinate) + "px; top: " + String.valueOf(-zero_coordinate) + "px;");
		viewHolderPosition = viewPortLayout.getPosition(viewHolder);

		viewHolder.setDropHandler(new MyCustomDropHandler(viewHolderPosition, lines, this, viewHolder, views));

		// Zoom Extension not working after switch to MAVEN project
		// Problem with GWT compilation

		/*MWExtention mouseWheelExtension = MWExtention.enableFor(viewHolder);
		mouseWheelExtension.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelUp() {
				zoomIn();
			}

			@Override
			public void mouseWheelDown() {
				zoomOut();
			}
		});*/
	}

	ViewImpl tempView = null;

	public void createNewView(Strategy strategy, SearchModel searchModel, Point location,
			ImportantPoints startingPoints, int parentViewID) {

		Point endPoint;

		if (searchModel.getDocumentList().size() != 0 || searchModel.getPersonList().size() != 0
				|| searchModel.getTopicList().size() != 0) {

			nodesIDGenerator++;

			switch (strategy) {
			case PERSON:
				tempView = new PersonView(searchModel, listener, nodesIDGenerator, this, parentViewID);
				break;
			case TOPIC:
				tempView = new DocumentView(searchModel, listener, nodesIDGenerator, this, parentViewID);
				break;
			case DOCUMENTS:
				tempView = new DocumentView(searchModel, listener, nodesIDGenerator, this, parentViewID);
				break;
			case HIGHLY_CITED_AUTHOR:
				tempView = new DocumentView(searchModel, listener, nodesIDGenerator, this, parentViewID);
				break;
			case HIGHLY_REFERENCED_AUTHOR:
				tempView = new DocumentView(searchModel, listener, nodesIDGenerator, this, parentViewID);
				break;
			case CITED_BY_DOC:
				tempView = new DocumentView(searchModel, listener, nodesIDGenerator, this, parentViewID);
				break;
			case REFERENCED_DOC:
				tempView = new DocumentView(searchModel, listener, nodesIDGenerator, this, parentViewID);
				break;
			case MAIN_COAUTHORS_AUTHOR:
				tempView = new PersonView(searchModel, listener, nodesIDGenerator, this, parentViewID);

				break;
			case HIGHLY_CITED_TOPIC:
				tempView = new DocumentView(searchModel, listener, nodesIDGenerator, this, parentViewID);

				break;
			case IMPORTANT_AUTHORS_TOPIC:
				tempView = new PersonView(searchModel, listener, nodesIDGenerator, this, parentViewID);
				break;
			case MAIN_TOPICS_AUTHOR:
				tempView = new TopicView(searchModel, listener, nodesIDGenerator, this, parentViewID);
				break;

			case SIMILAR_TOPICS_DOC:
				tempView = new DocumentView(searchModel, listener, nodesIDGenerator, this, parentViewID);
				break;
			}

			views.add(tempView);
			endPoint = new Point(location.x, location.y);

			viewHolderToBeClicked = true;

			this.removeStyleName("loading");
			viewHolder.addStyleName("bg-click");

			viewHolderClickListener = new LayoutClickListener() {
				@Override
				public void layoutClick(LayoutClickEvent event) {
					viewHolder.addComponent(tempView, "left:" + String.valueOf(event.getRelativeX()) + "px; top:"
							+ String.valueOf(event.getRelativeY()) + "px;");

					viewHolderToBeClicked = false;

					viewHolder.removeStyleName("bg-click");
					viewHolder.removeLayoutClickListener(this);

					AbsoluteLayout.ComponentPosition cp = viewHolder.getPosition(tempView);

					endPoint.x = Math.round(cp.getLeftValue());
					endPoint.y = Math.round(cp.getTopValue());

					ImportantPoints newViewImpPoints = new ImportantPoints(endPoint, Math.round(tempView.getWidth()),
							Math.round(tempView.getHeight()));

					Path shortestPath = calculateShortestPath(startingPoints, newViewImpPoints);

					lines.add(new ConnectionLine(shortestPath, parentViewID, tempView.getViewID()));
					// linesUpdated = true;
					// redrawCanvas(lines);
				}

			};

			viewHolder.addLayoutClickListener(viewHolderClickListener);

			Timer timer = new Timer();
			timer.schedule(new WaitForClick(tempView, endPoint, searchModel, startingPoints, parentViewID, timer),
					WAIT_FOR_CLICK_TIME);

		} else {
			// If No results - Show Notifications
			showWarning();
		}
	}

	class WaitForClick extends TimerTask {
		ViewImpl tempView;
		SearchModel searchModel;
		Point endPoint;
		ImportantPoints startingPoints;
		int parentID;
		Path shortestPath;
		Timer timer;

		public WaitForClick(ViewImpl tempView, Point location, SearchModel searchModel, ImportantPoints startingPoints,
				int parentID, Timer t) {
			this.tempView = tempView;
			this.endPoint = location;
			this.searchModel = searchModel;
			this.startingPoints = startingPoints;
			this.parentID = parentID;
			this.timer = t;
		}

		@Override
		public void run() {
			if (viewHolderToBeClicked == true) {
				viewHolder.removeLayoutClickListener(viewHolderClickListener);

				int nextpanel_w = 0;
				int nextpanel_h = 0;
				if (tempView instanceof PersonView) {
					nextpanel_h = 130;
					nextpanel_w = 310;
				} else {
					nextpanel_h = 250;
					nextpanel_w = 450;
				}

				// Check if area occupied --- start here
				// to be developed a better area check

				while (true) {
					boolean toBeChecked = true;

					Iterator<Component> iterate = viewHolder.iterator();
					while (iterate.hasNext()) {
						Component viewHolderElement = iterate.next();
						AbsoluteLayout.ComponentPosition occupied = viewHolder.getPosition(viewHolderElement);

						Point topLeft = new Point(Math.round(occupied.getLeftValue()),
								Math.round(occupied.getTopValue()));
						Point topRight = new Point(Math.round(occupied.getLeftValue() + viewHolderElement.getWidth()),
								Math.round(occupied.getTopValue()));
						Point bottomLeft = new Point(Math.round(occupied.getLeftValue()),
								Math.round(occupied.getTopValue() + viewHolderElement.getHeight()));

						toBeChecked = false;
						if ((endPoint.x <= topRight.x + 10 && endPoint.x >= topLeft.x - 10
								&& endPoint.y <= bottomLeft.y + 10 && endPoint.y >= topLeft.y - 10) ||

						(endPoint.x + nextpanel_w <= topRight.x + 10 && endPoint.x + nextpanel_w >= topLeft.x - 10
								&& endPoint.y <= bottomLeft.y + 10 && endPoint.y + nextpanel_h >= topLeft.y - 10) ||

						(endPoint.x <= topRight.x + 10 && endPoint.x >= topLeft.x - 10
								&& endPoint.y + nextpanel_h <= bottomLeft.y + 10
								&& endPoint.y + nextpanel_h >= topLeft.y - 10) ||

						(endPoint.x + nextpanel_w <= topRight.x + 10 && endPoint.x + nextpanel_w >= topLeft.x - 10
								&& endPoint.y + nextpanel_h <= bottomLeft.y + 10
								&& endPoint.y + nextpanel_h >= topLeft.y - 10)) {

							if (viewHolderElement instanceof PersonView || viewHolderElement instanceof TopicView) {
								endPoint.setLocation(endPoint.x, occupied.getTopValue() + 125);
								toBeChecked = true;
								break;
							}
							if (viewHolderElement instanceof DocumentView) {
								endPoint.setLocation(endPoint.x, occupied.getTopValue() + 235);
								toBeChecked = true;
								break;
							}

						}
					}

					if (toBeChecked == false)
						break;
				}
				// Check if area occupied --- end here

				viewHolder.addComponent(tempView, "left:" + String.valueOf(endPoint.getX()) + "px; top:"
						+ String.valueOf(endPoint.getY()) + "px;");
				viewHolder.removeStyleName("bg-click");
				viewHolderToBeClicked = false;
				UI.getCurrent().push();
				ImportantPoints newViewImpPoints = new ImportantPoints(endPoint, nextpanel_w, nextpanel_h);
				shortestPath = calculateShortestPath(startingPoints, newViewImpPoints);

				lines.add(new ConnectionLine(shortestPath, parentID, tempView.getViewID()));
				// linesUpdated = true;
				// redrawCanvas(lines);

			}
			timer.cancel();
			timer.purge();
		}
	}

	private void showWarning() {
		Notification notif = new Notification("WARNING", NO_RESULTS_WARNING_MSG, Notification.Type.WARNING_MESSAGE);
		notif.setDelayMsec(WARNING_NOTIFICATON_DELAY);
		notif.setPosition(Position.MIDDLE_CENTER);
		notif.setStyleName("mystyle");
		notif.show(Page.getCurrent());
	}

	public void createInitialSearchFormPanel() {
		nodesIDGenerator++;
		SearchFormPanel s = new SearchFormPanel(listener, nodesIDGenerator, this);
		viewHolder.addComponent(s, "left:" + String.valueOf(zero_coordinate + 20) + "px; top:"
				+ String.valueOf(zero_coordinate + 30) + "px;");
	}

	public void removeConnectionLines(int viewID) {
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).getParentID() == viewID) {
				this.lines.remove(lines.get(i));
				i--;
			} else if (lines.get(i).getChildID() == viewID) {
				this.lines.remove(lines.get(i));
				i--;
			}
		}
		// linesUpdated = true;
		// redrawCanvas(lines);
	}

	private Path calculateShortestPath(ImportantPoints startingPoints, ImportantPoints newViewImpPoints) {
		Point shortStartPoint = new Point();
		shortStartPoint = startingPoints.bottom_middle;
		Point shortEndPoint = new Point();
		shortEndPoint = newViewImpPoints.top_middle;

		Point angleDef = new Point(shortStartPoint.x, shortEndPoint.y);

		double tempDistance = Math.sqrt((shortStartPoint.y - angleDef.y) * (shortStartPoint.y - angleDef.y)
				+ (shortEndPoint.x - angleDef.x) * (shortEndPoint.x - angleDef.x));

		for (Point startingPoint : startingPoints.points) {
			for (Point endPoint : newViewImpPoints.points) {

				Point angle = new Point(startingPoint.x, endPoint.y);

				double distance = Math.sqrt((startingPoint.y - angle.y) * (startingPoint.y - angle.y)
						+ (endPoint.x - angle.x) * (endPoint.x - angle.x));

				if (distance < tempDistance) {
					tempDistance = distance;
					shortStartPoint = startingPoint;
					shortEndPoint = endPoint;
				}

			}

		}
		return new Path(shortStartPoint, shortEndPoint);
	}

	public void zoomIn() {
		if (zoomLevel < 130) {
			this.removeStyleName("zoom" + String.valueOf(zoomLevel));
			zoomLevel = zoomLevel + 10;
			this.addStyleName("zoom" + String.valueOf(zoomLevel));
		}
	}

	public void zoomOut() {
		if (zoomLevel > 80) {
			this.removeStyleName("zoom" + String.valueOf(zoomLevel));
			zoomLevel = zoomLevel - 10;
			this.addStyleName("zoom" + String.valueOf(zoomLevel));
		}
	}

	@Override
	public void addListener(ViewPortListener listener) {
		this.listener = listener;
	}

	// public void redrawCanvas(ArrayList<ConnectionLine> lines) {
	//
	// WebBrowser browser = Page.getCurrent().getWebBrowser();
	// if (!browser.isFirefox()) {
	// if (linesUpdated) {
	// UI.getCurrent().access(new Runnable() {
	// @Override
	// public void run() {
	// canvas.saveContext();
	// canvas.clear();
	// for (ConnectionLine line : lines) {
	// canvas.beginPath();
	// canvas.setLineWidth(2);
	// canvas.setLineCap("round");
	// canvas.setMiterLimit(1);
	// canvas.setStrokeStyle("rgb(30, 0, 200)");
	//
	// canvas.moveTo(line.getPath().startPoint.x,
	// (line.getPath().startPoint.y));
	//
	// canvas.lineTo(line.getPath().endPoint.x,
	// (line.getPath().endPoint.y));
	// canvas.stroke();
	// canvas.closePath();
	// canvas.beginPath();
	// // canvas.saveContext();
	// canvas.moveTo(line.getPath().endPoint.x,
	// (line.getPath().endPoint.y));
	// canvas.arc(line.getPath().endPoint.x,
	// line.getPath().endPoint.y, 5, 0,
	// 2 * Math.PI, false);
	// canvas.fill();
	// canvas.stroke();
	// canvas.closePath();
	// // canvas.restoreContext();
	//
	// }
	// canvas.restoreContext();
	// }
	//
	// });
	// UI.getCurrent().push();
	// }
	// linesUpdated = false;
	// }
	// }

	// public void updateCanvasPosition(float left, float top) {
	// canvasPosition.setLeftValue(left);
	// canvasPosition.setTopValue(top);
	// }

	// public float getLeftCanvasPosition() {
	// return canvasPosition.getLeftValue();
	// }
	//
	// public float getTopCanvasPosition() {
	// return canvasPosition.getTopValue();
	// }
}
