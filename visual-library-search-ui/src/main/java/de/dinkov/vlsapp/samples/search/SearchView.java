package de.dinkov.vlsapp.samples.search;

import com.google.gwt.user.server.rpc.core.java.util.HashMap_ServerCustomFieldSerializer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import de.dinkov.vlsapp.samples.Diagram;
import de.dinkov.vlsapp.samples.backend.Entities.DiagramMainSearchStrategies;
import de.dinkov.vlsapp.samples.backend.Entities.SearchModel;
import de.dinkov.vlsapp.samples.backend.SearchSession;
import de.dinkov.vlsapp.samples.backend.elastic_search.DiagramStrategy;
import de.dinkov.vlsapp.samples.backend.util.ElasticToJsonConverter;

import java.util.HashMap;

/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.search.
 * Created by Steliyan Dinkov on 2/7/2016.
 */
public class SearchView extends VerticalLayout implements View {

    private static final long serialVersionUID = 1L;

    private HorizontalLayout searchContent;
    private Diagram diagram;
    private HashMap<String, String> instanceCache = new HashMap<>();
    private SearchSession searchSession = null;

    public SearchView(Diagram diagram, SearchSession searchSession) {
        this.diagram = diagram;
        this.searchSession = searchSession;

        searchContent = new HorizontalLayout();
       // searchContent.setSpacing(true);
        //searchContent.setMargin(true);
        // create search types drop-down
        ComboBox searchStrategies = new ComboBox();
        searchStrategies.addItem(DiagramMainSearchStrategies.PERSON);
        searchStrategies.addItem(DiagramMainSearchStrategies.DOCUMENTS);
        searchStrategies.addItem(DiagramMainSearchStrategies.TOPIC);
        searchStrategies.setTextInputAllowed(false);

        TextField searchInput = new TextField();
        Label label= new Label(diagram.getSanitizedTreeData());
        Button searchBtn = new Button("Search");
        searchBtn.addClickListener((Button.ClickListener) event -> {
            String searchTerm = searchInput.getValue();
            DiagramMainSearchStrategies strategy = (DiagramMainSearchStrategies) searchStrategies.getValue();

            SearchModel searchModel = new SearchModel();
            searchModel.setKeywords(searchTerm);
            searchModel.setStrategy(strategy);
            searchSession.add(searchModel);

            diagram.updateTree(getJsonResult(strategy.toString(), searchTerm, "name"));
        });

        searchContent.addComponent(searchStrategies);
        searchContent.addComponent(searchInput);
        searchContent.addComponent(searchBtn);
    }

    public HorizontalLayout getSearchBar() {
        return searchContent;
    }

    public SearchModel getLastSearch() {
        return searchSession.get(searchSession.size() - 1);
    }

    public String getJsonResult(String strategy, String term, String field) {
        String keyHash = getKeyHash(strategy, term, field);
        String result = "";
        if (!instanceCache.containsKey(keyHash)) {
            DiagramStrategy diagramStrategy = new DiagramStrategy(strategy, term, field);
            ElasticToJsonConverter converter = new ElasticToJsonConverter(
                    strategy.toLowerCase(),
                    term,
                    diagramStrategy.applySearchFormStrategySearch().getResult()
            );
            result = converter.getResult();
            instanceCache.put(keyHash, result);
        }
        return result;
    }

    public void setTreeData(String treeData) {
        if (diagram != null) {
            diagram.updateTree(treeData);
        }
    }

    private String getKeyHash(String strategy, String term, String field) {
        return strategy + ':' + term + ':' + field;
    }

    @Override
    public void enter(ViewChangeEvent event) { }

}

