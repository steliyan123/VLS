package de.dinkov.vlsapp.samples.backend.Entities;

import java.util.Comparator;

/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.backend.Entities.
 * Created by Steliyan Dinkov on 3/14/2016.
 */
public class CoAuthor {
    private String name;
    private int count;

    public CoAuthor(String n, int c) {
        name = n;
        count = c;
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




    public static Comparator<CoAuthor> compareCount = new Comparator<CoAuthor>() {

        @Override
        public int compare(CoAuthor d1, CoAuthor d2) {
            int doc1 = d1.getCount();
            int doc2 = d2.getCount();

            return doc2 - doc1;
        }
    };
}
