package de.dinkov.vlsapp.samples.search;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import de.dinkov.vlsapp.samples.Diagram;
import de.dinkov.vlsapp.samples.backend.Entities.DiagramMainSearchStrategies;
import de.dinkov.vlsapp.samples.backend.util.ElasticToJsonConverter;

import java.util.ArrayList;

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

        TextField searchInput = new TextField("type something here to search");

        Button searchBtn = new Button("Search");
        searchBtn.addClickListener((Button.ClickListener) event -> {
            String searchTerm = searchInput.getValue();
            DiagramMainSearchStrategies strategy = (DiagramMainSearchStrategies) searchStrategies.getValue();
            DiagramMainSearchViewLogic logic = new DiagramMainSearchViewLogic(searchTerm, strategy.toString());
            ElasticToJsonConverter converter = new ElasticToJsonConverter(
                    strategy.toString().toLowerCase(),
                    searchTerm,
                    logic.getResult()
            );
            String result = converter.parse().getResult();
            diagram.updateTree(result);
        });

        ArrayList<Component> components = new ArrayList<Component>();
        components.add(searchStrategies);
        components.add(searchInput);
        components.add(searchBtn);

        for (Component c : components) {
            searchContent.addComponent(c);
        }
    }

    public HorizontalLayout getSearchBar() {
        return searchContent;
    }

    @Override
    public void enter(ViewChangeEvent event) { }

}

