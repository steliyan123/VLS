/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.search.
 * Created by Steliyan Dinkov on 2/7/2016.
 */
package de.dinkov.vlsapp.samples.search;

import de.dinkov.vlsapp.samples.backend.Entities.Document;
import de.dinkov.vlsapp.samples.backend.elastic_search.DiagramElasticSearchHandler;

import java.util.ArrayList;

public class DiagramMainSearchViewLogic {

    private String searchTerm;
    private int searchType = 0; // author search by default
    private String strategy;
    DiagramElasticSearchHandler handler;

    public DiagramMainSearchViewLogic(String searchTerm, String strategy){
        this.searchTerm = searchTerm;
        this.strategy = strategy;
        this.handler = new DiagramElasticSearchHandler();
    }

    public ArrayList<Document> getResult () {
        return handler.searchDocument(strategy.toLowerCase(), searchTerm, getField());
    }

    protected String getField() {
        switch (searchType) {
            case 1: return "document"; // @todo to be implemented
            case 2: return "topic"; // @todo implement me!
            case 0:
            default: return "name"; // author search
        }
    }
}
