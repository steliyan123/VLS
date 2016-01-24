package de.dinkov.vlsapp.samples.backend.elastic_search;

import de.dinkov.vlsapp.samples.backend.Entities.Document;
import java.util.ArrayList;

import org.elasticsearch.*;
/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.backend.elastic_search.
 * Created by Steliyan Dinkov on 1/23/2016.
 */
public class DiagramStrategy {

    private String strategy, keywords;
    private ArrayList<Document> result;

    public DiagramStrategy(String strategy, String keywords) {
        this.strategy=strategy;
        this.keywords=keywords;
        result = new ArrayList<Document>();
    }

    public DiagramStrategy() {}

    public DiagramStrategy applySearchFormStrategySearch() {

        DiagramElasticSearchHandler eshandler = new DiagramElasticSearchHandler();

        switch (strategy) {
            case "document":
                result = eshandler.searchDocument("dlsnew", "document", "topics.name", keywords, "citedFrom");
                break;
        }

        return this;
    }

    public ArrayList<Document> getResult() { return result; }

}
