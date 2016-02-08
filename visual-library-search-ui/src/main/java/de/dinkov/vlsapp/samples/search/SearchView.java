package de.dinkov.vlsapp.samples.search;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import de.dinkov.vlsapp.samples.Diagram;
import de.dinkov.vlsapp.samples.backend.Entities.DiagramMainSearchStrategies;
import de.dinkov.vlsapp.samples.backend.elastic_search.DiagramStrategy;
import de.dinkov.vlsapp.samples.backend.util.ElasticToJsonConverter;

/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.search.
 * Created by Steliyan Dinkov on 2/7/2016.
 */
public class SearchView extends VerticalLayout implements View {

    private static final long serialVersionUID = 1L;

    private HorizontalLayout searchContent;
    private Diagram diagram;

    public SearchView(Diagram diagram) {
        this.diagram = diagram;

        searchContent = new HorizontalLayout();

        // create search types drop-down
        ComboBox searchStrategies = new ComboBox();
        searchStrategies.addItem(DiagramMainSearchStrategies.PERSON);
        searchStrategies.addItem(DiagramMainSearchStrategies.DOCUMENTS);
        searchStrategies.addItem(DiagramMainSearchStrategies.TOPIC);
        searchStrategies.setTextInputAllowed(false);

        TextField searchInput = new TextField();

        Button searchBtn = new Button("Search");
        searchBtn.addClickListener((Button.ClickListener) event -> {
            String searchTerm = searchInput.getValue();
            DiagramMainSearchStrategies strategy = (DiagramMainSearchStrategies) searchStrategies.getValue();
            DiagramStrategy diagramStrategy = new DiagramStrategy(strategy.toString(), searchTerm, "name");
            ElasticToJsonConverter converter = new ElasticToJsonConverter(
                    strategy.toString().toLowerCase(),
                    searchTerm,
                    diagramStrategy.applySearchFormStrategySearch().getResult()
            );
            diagram.updateTree(converter.getResult());
        });

        searchContent.addComponent(searchStrategies);
        searchContent.addComponent(searchInput);
        searchContent.addComponent(searchBtn);
    }

    public HorizontalLayout getSearchBar() {
        return searchContent;
    }

    @Override
    public void enter(ViewChangeEvent event) { }

}

