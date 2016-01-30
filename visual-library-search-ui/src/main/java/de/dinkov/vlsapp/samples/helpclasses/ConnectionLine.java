package de.dinkov.vlsapp.samples.helpclasses;


public class ConnectionLine {
	Path path = new Path(null, null);;
	int parentID;
	int childID;

	public ConnectionLine(Path path, int p, int c) {
		this.path = path;
		parentID = p;
		childID = c;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

	public int getChildID() {
		return childID;
	}

	public void setChildID(int childID) {
		this.childID = childID;
	}

}
