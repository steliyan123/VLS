/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.backend.elastic_search.
 * Created by Steliyan Dinkov on 1/23/2016.
 */
package de.dinkov.vlsapp.samples.backend.elastic_search;

import de.dinkov.vlsapp.samples.backend.Entities.Document;
import java.util.ArrayList;

public class DiagramStrategy {

    private String keywords, field;
    private ArrayList<Document> result;
    private String strategy;
    private DiagramElasticSearchHandler handler;

    public DiagramStrategy(String strategy, String keywords, String field) {
        this.keywords = keywords;
        this.field = field;
        this.strategy = strategy;
        this.result = new ArrayList<>();
        this.handler = new DiagramElasticSearchHandler();
    }

    public DiagramStrategy applySearchFormStrategySearch() {
        switch (strategy) {
            case "document": result = handler.searchDocument(strategy, keywords, field);
                break;
            default: result = handler.searchDocument("document", "Khriplovich", "name");
                break;
        }
        handler.destroy();
        return this;
    }

    public ArrayList<Document> getResult() { return result; }

}
