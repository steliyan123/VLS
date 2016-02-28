/** * Created by Steliyan on 1/21/2016.
 * @package de.dinkov.vlsapp.samples.diagram
 */

package de.dinkov.vlsapp.samples.diagram;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import de.dinkov.vlsapp.samples.Diagram;
import de.dinkov.vlsapp.samples.backend.SearchSession;
import de.dinkov.vlsapp.samples.search.SearchView;

public class DiagramView extends VerticalLayout implements View {

    private static final long serialVersionUID = 1L;
    public static final String VIEW_NAME = "Diagram";
    private HorizontalLayout searchContent = null;
    private SearchView searchView = null;
    private Diagram diagram = null;

    public DiagramView(SearchSession newSearchSession) {
        diagram = new Diagram();
        diagram.addTreeData("");
        diagram.setStyleName("diagram-content");

        searchView = new SearchView(diagram, newSearchSession);
        searchContent = searchView.getSearchBar();

        CustomLayout diagramView = new CustomLayout("diagramview");
        diagramView.setStyleName("diagram-content");
        diagramView.addComponent(searchContent, "searchbar");
        diagramView.addComponent(diagram, "diagram");

        setSizeFull();
        setStyleName("diagram-view");
        addComponent(diagramView);
        setComponentAlignment(diagramView, Alignment.MIDDLE_CENTER);
    }

    public HorizontalLayout getSearchViewComponent() {
        return searchContent;
    }

    public SearchView getSearchView() {
        return searchView;
    }

    @Override
    public void enter(ViewChangeEvent event) { }
}