package de.dinkov.vlsapp.samples.widgets;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.ui.AbsoluteLayout.ComponentPosition;
import com.vaadin.ui.Component;

import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.details.AbsoluteLayoutTargetDetails;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultAbsoluteLayoutDropHandler;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;
import de.dinkov.vlsapp.samples.diagram.ViewImpl;
import de.dinkov.vlsapp.samples.diagram.ViewPortImpl;
import de.dinkov.vlsapp.samples.helpclasses.ConnectionLine;
import de.dinkov.vlsapp.samples.helpclasses.ImportantPoints;
import de.dinkov.vlsapp.samples.helpclasses.Path;
import de.dinkov.vlsapp.samples.viewtypes.SearchFormPanel;

public class MyCustomDropHandler extends DefaultAbsoluteLayoutDropHandler {

	private ArrayList<ConnectionLine> lines;
	private DDAbsoluteLayout viewHolder;
	private ComponentPosition viewHolderPosition;
	private List<ViewImpl> views;
	private ViewPortImpl viewPort;

	public MyCustomDropHandler(ComponentPosition viewHolderPosition,
			ArrayList<ConnectionLine> lines, ViewPortImpl viewPort,
			DDAbsoluteLayout viewHolder, List<ViewImpl> views) {
		this.lines = lines;
		this.viewHolder = viewHolder;
		this.viewHolderPosition = viewHolderPosition;
		this.views = views;
		this.viewPort = viewPort;
	}

	@Override
	public void drop(DragAndDropEvent event) {
		AbsoluteLayoutTargetDetails details = (AbsoluteLayoutTargetDetails) event
				.getTargetDetails();
		LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
				.getTransferable();

		int xChange = details.getMouseEvent().getClientX()
				- transferable.getMouseDownEvent().getClientX();
		int yChange = details.getMouseEvent().getClientY()
				- transferable.getMouseDownEvent().getClientY();

		Component c = transferable.getComponent();

		if (c instanceof SearchFormPanel) {
			super.drop(event);

			SearchFormPanel sfp = (SearchFormPanel) c;

			moveChildElements(sfp, xChange, yChange);

		} else if (c instanceof ViewImpl) {
			super.drop(event);

			ViewImpl view = (ViewImpl) c;

			Iterator<Component> iterate = viewHolder.iterator();
			while (iterate.hasNext()) {
				Component viewHolderElement = iterate.next();

				if (viewHolderElement instanceof ViewImpl) {
					ViewImpl viewParent = (ViewImpl) viewHolderElement;

					if (viewParent.getViewID() == view.getParentViewID()) {

						for (ConnectionLine line : lines) {
							if (line.getChildID() == view.getViewID()) {
								Path newPath = recalculatePath(viewParent,
										line.getChildID());

								line.setPath(newPath);
								// viewPort.linesUpdated = true;
								// viewPort.redrawCanvas(lines);
							}
						}
					}
				} else if (viewHolderElement instanceof SearchFormPanel) {
					SearchFormPanel sfp = (SearchFormPanel) viewHolderElement;
					if (sfp.getViewID() == view.getParentViewID()) {
						for (ConnectionLine line : lines) {
							if (line.getChildID() == view.getViewID()) {
								Path newPath = recalculatePath(sfp,
										line.getChildID());

								line.setPath(newPath);
								// viewPort.linesUpdated = true;
								// viewPort.redrawCanvas(lines);
							}
						}
					}

				}
			}

			moveChildElements(view, xChange, yChange);

		} else if (c instanceof DDAbsoluteLayout) {
			moveMap(xChange, yChange);
		}
	}

	private void moveChildElements(Component parentC, int xChange, int yChange) {
		Iterator<Component> iterate = viewHolder.iterator();
		while (iterate.hasNext()) {

			Component viewHolderElement = iterate.next();
			if (viewHolderElement instanceof ViewImpl) {

				ViewImpl view = (ViewImpl) viewHolderElement;

				if (parentC instanceof SearchFormPanel) {
					SearchFormPanel sfp = (SearchFormPanel) parentC;

					if (view.getParentViewID() == sfp.getViewID()) {

						ComponentPosition viewPos = viewHolder
								.getPosition(view);
						viewPos.setLeftValue(viewPos.getLeftValue() + xChange);
						viewPos.setTopValue(viewPos.getTopValue() + yChange);

						for (ConnectionLine line : lines) {
							if (line.getParentID() == sfp.getViewID()) {
								Path newPath = recalculatePath(sfp,
										line.getChildID());

								line.setPath(newPath);
								// viewPort.linesUpdated = true;
								// viewPort.redrawCanvas(lines);
							}
						}

						moveChildElements(view, xChange, yChange);
					}
				} else if (parentC instanceof ViewImpl) {
					ViewImpl viewParent = (ViewImpl) parentC;

					if (view.getParentViewID() == viewParent.getViewID()) {

						ComponentPosition viewPos = viewHolder
								.getPosition(view);
						viewPos.setLeftValue(viewPos.getLeftValue() + xChange);
						viewPos.setTopValue(viewPos.getTopValue() + yChange);

						for (ConnectionLine line : lines) {
							if (line.getParentID() == viewParent.getViewID()) {
								Path newPath = recalculatePath(viewParent,
										line.getChildID());

								line.setPath(newPath);
								// viewPort.linesUpdated = true;
								// viewPort.redrawCanvas(lines);
							}

							else if (line.getChildID() == viewParent
									.getViewID()) {
								Iterator<Component> iterate2 = viewHolder
										.iterator();
								while (iterate2.hasNext()) {
									Component viewHolderEl = iterate2.next();
									if (viewHolderEl instanceof SearchFormPanel) {
										SearchFormPanel sfp = (SearchFormPanel) viewHolderEl;
										if (sfp.getViewID() == line
												.getParentID()) {
											Path newPath = recalculatePath(sfp,
													viewParent.getViewID());

											line.setPath(newPath);
											// viewPort.linesUpdated = true;
											// viewPort.redrawCanvas(lines);
										}
									} else if (viewHolderEl instanceof ViewImpl) {
										ViewImpl pV = (ViewImpl) viewHolderEl;
										if (pV.getViewID() == line
												.getParentID()) {
											Path newPath = recalculatePath(pV,
													viewParent.getViewID());

											line.setPath(newPath);
											// viewPort.linesUpdated = true;
											// viewPort.redrawCanvas(lines);
										}
									}
								}
							}
						}

						moveChildElements(view, xChange, yChange);
					}
				}
			}
		}

	}

	private Path recalculatePath(Component parentComponent, int childID) {
		if (parentComponent instanceof SearchFormPanel) {
			for (ViewImpl view : views) {
				if (view.getViewID() == childID) {
					Path newPath = calculateShortestPath(
							((SearchFormPanel) parentComponent)
									.getStartingPoints(),
							view.getStartingPoints());

					return newPath;
				}
			}
		} else if (parentComponent instanceof ViewImpl) {
			for (ViewImpl view : views) {
				if (view.getViewID() == childID) {
					Path newPath = calculateShortestPath(
							((ViewImpl) parentComponent).getStartingPoints(),
							view.getStartingPoints());
					return newPath;
				}
			}
		}

		return null;
	}

	private void moveMap(int xChange, int yChange) {

		viewHolderPosition.setLeftValue(viewHolderPosition.getLeftValue()
				+ xChange);
		viewHolderPosition.setTopValue(viewHolderPosition.getTopValue()
				+ yChange);

		// viewPort.updateCanvasPosition(viewPort.getLeftCanvasPosition()
		// + xChange, viewPort.getTopCanvasPosition() + yChange);

	}

	private Path calculateShortestPath(ImportantPoints startingPoints,
			ImportantPoints newViewImpPoints) {
		Point shortStartPoint = new Point();
		shortStartPoint = startingPoints.bottom_middle;
		Point shortEndPoint = new Point();
		shortEndPoint = newViewImpPoints.top_middle;

		Point angleDef = new Point(shortStartPoint.x, shortEndPoint.y);

		double tempDistance = Math.sqrt((shortStartPoint.y - angleDef.y)
				* (shortStartPoint.y - angleDef.y)
				+ (shortEndPoint.x - angleDef.x)
				* (shortEndPoint.x - angleDef.x));

		for (Point startingPoint : startingPoints.points) {
			for (Point endPoint : newViewImpPoints.points) {

				Point angle = new Point(startingPoint.x, endPoint.y);

				double distance = Math.sqrt((startingPoint.y - angle.y)
						* (startingPoint.y - angle.y) + (endPoint.x - angle.x)
						* (endPoint.x - angle.x));

				if (distance < tempDistance) {
					tempDistance = distance;
					shortStartPoint = startingPoint;
					shortEndPoint = endPoint;
				}

			}

		}

		return new Path(shortStartPoint, shortEndPoint);

	}
}
