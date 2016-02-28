package de.dinkov.vlsapp.samples.backend;

/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.backend.
 * Created by Steliyan Dinkov on 2/28/2016.
 */
import de.dinkov.vlsapp.samples.backend.Entities.DiagramMainSearchStrategies;
import de.dinkov.vlsapp.samples.backend.Entities.SearchModel;

import java.util.ArrayList;

public class SearchSession extends ArrayList<SearchModel> {

    public SearchSession getDefaultSearchSession() {
        SearchSession searchSession = new SearchSession();
        searchSession.add(getDefaultSearchModel());
        return searchSession;
    }

    private SearchModel getDefaultSearchModel() {
        SearchModel model = new SearchModel();
        model.setKeywords("");
        model.setStrategy(DiagramMainSearchStrategies.DOCUMENTS);
        return model;
    }
}