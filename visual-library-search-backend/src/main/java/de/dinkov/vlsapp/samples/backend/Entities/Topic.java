package de.dinkov.vlsapp.samples.backend.Entities;

import java.util.Comparator;

public class Topic {
	private String name;
	private int count;

	public Topic(String n, int c) {
		name = n;
		count = c;
	}

	public Topic(String n) {
		name = n;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public static Comparator<Topic> compareCount = new Comparator<Topic>() {

		@Override
		public int compare(Topic d1, Topic d2) {
			int doc1 = d1.getCount();
			int doc2 = d2.getCount();

			return doc2 - doc1;
		}
	};
}
