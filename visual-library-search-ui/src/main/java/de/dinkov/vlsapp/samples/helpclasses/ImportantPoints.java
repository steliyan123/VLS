package de.dinkov.vlsapp.samples.helpclasses;

import java.awt.Point;
import java.util.ArrayList;

public class ImportantPoints {

	public ArrayList<Point> points = new ArrayList<Point>();
	public Point left_middle;
	public Point right_middle;
	public Point bottom_middle;
	public Point top_middle;
	int x1;
	int x2;
	int y1;
	int y2;

	public ImportantPoints(Point topLeft, int width, int height) {

		x1 = topLeft.x;
		y1 = topLeft.y;
		x2 = x1 + width;
		y2 = y1 + height;
		
		top_middle = new Point(x1 + Math.round(width / 2), y1);
		bottom_middle = new Point(x1 + Math.round(width / 2), y2);
		left_middle = new Point(x1, y1 + Math.round(height) / 2);
		right_middle = new Point(x2, y1 + Math.round(height) / 2);

		points.add(bottom_middle);
		points.add(left_middle);
		points.add(right_middle);
		points.add(top_middle);
	}
}
