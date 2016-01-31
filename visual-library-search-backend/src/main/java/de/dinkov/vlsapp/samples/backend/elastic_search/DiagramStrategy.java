/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.backend.elastic_search.
 * Created by Steliyan Dinkov on 1/23/2016.
 */
package de.dinkov.vlsapp.samples.backend.elastic_search;

import de.dinkov.vlsapp.samples.backend.Entities.Document;
import java.util.ArrayList;

public class DiagramStrategy {

    private String strategy, keywords, field;
    private ArrayList<Document> result;
    private DiagramElasticSearchHandler handler;

    public DiagramStrategy(String strategy, String keywords, String field) {
        this.strategy=strategy;
        this.keywords=keywords;
        this.field=field;
        this.result = new ArrayList<Document>();
        this.handler = new DiagramElasticSearchHandler();
    }

    public DiagramStrategy applySearchFormStrategySearch() {
        switch (strategy) {
            case "document": result = handler.searchDocument("dlsnew", strategy, keywords, field);
                //eshandler.closeNode();
                break;
            default: result = handler.searchDocument("dlsnew", "document", "Khriplovich", "name");
                break;
        }
        return this;
    }

    public ArrayList<Document> getResult() { return result; }

}
