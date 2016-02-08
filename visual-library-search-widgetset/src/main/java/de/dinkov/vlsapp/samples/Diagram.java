package de.dinkov.vlsapp.samples;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.HtmlRenderer;
import de.dinkov.vlsapp.samples.backend.Entities.Document;
import de.dinkov.vlsapp.samples.backend.elastic_search.DiagramStrategy;
import elemental.json.JsonArray;

import java.util.ArrayList;

@JavaScript({ "d3.min.js", "diagram_connector.js", "jquery-1.11.3.min.js" })
public class Diagram extends AbstractJavaScriptComponent {

    public Diagram() {
        // callback that will be called from the client-side
        addCallBacks();
    }
    public void addCallBacks(){
        addFunction("onPlotClick", (JavaScriptFunction) arguments -> {
            String authorName = arguments.getString(0);
            String strategy = arguments.getString(1);
            String field    = "name";

            DiagramStrategy diagramStrategy = new DiagramStrategy(strategy, authorName, field);
            ArrayList<Document> result = diagramStrategy.applySearchFormStrategySearch().getResult();

            displayPopUp(authorName, result);
        });
    }

    public void addTreeData(String data) {
        getState().treeData = data;
    }

    public void updateTree(String treedata) {
        // Returns an RPC proxy for a given server to client RPC interface for
        // this component
        //getRpcProxy(DiagramUpdateRpc.class).updateTree(treedata);
        // Invokes the "updateTree" function in the js_connctor.
        callFunction("updateTree", treedata);
    }

    @Override
    public DiagramState getState() {
        return (DiagramState) super.getState();
    }

    public void displayPopUp(String author, ArrayList<Document> result) {

        Grid grid = new Grid();
        VerticalLayout content  = new VerticalLayout();
        Window popup = new Window("Documents for: " + author);

        grid.addColumn("ID", String.class).setMaximumWidth(100);
        grid.addColumn("Title", String.class);
        grid.addColumn("URL", String.class).setRenderer(new HtmlRenderer());
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.setResponsive(true);

        content.addComponent(grid);
        content.setExpandRatio(grid,1);
        content.setMargin(true);
        content.setSpacing(true);
       // grid.addColumn("Cited from", int.class);
       // grid.addColumn("Referenced by", String.class);
       // grid.addColumn("URL", Link.class);

       // grid.setMargin(true);


        popup.center();

        //int i = 0;
        for (Document doc: result) {
            /*grid.addComponent(new Label("Id: " + doc.getId()));
            grid.addComponent(new Label("Title: " + doc.getTitle()));
            //grid.addComponent(new Label("Classification: " + doc.getTitle()));
            grid.addComponent(new Label("Cited from: " + doc.getCitedFrom()));
            grid.addComponent(new Label("Referenced by: " + doc.getReferencedCount()));
            String url = doc.getURL();
            grid.addComponent(new Link(url, new ExternalResource(url)));
            grid.addComponent(new Label("<hr>", ContentMode.HTML));
            if (i++ > 3) break;*/
            grid.addRow(doc.getId(), doc.getTitle(), "<a href='" + doc.getURL() + "' target='_blank'>Source</a>");
        }

        //grid.addComponent(getPagination(result));
        popup.setContent(content);

        // Open it in the UI
        UI.getCurrent().addWindow(popup);
    }

    /*private HorizontalLayout getPagination(ArrayList<Document> result) {

        HorizontalLayout paginationLayout = new HorizontalLayout();
        VerticalLayout vl = new VerticalLayout();
        int resultSize = result.size();
        DiagramElasticSearchResult res = new DiagramElasticSearchResult(result);

        // Visual controls (First, Previous, 1 2 ..., Next, Last)
        final PagingComponent<Integer> pagingComponent = PagingComponent.paginate(createItems(resultSize))
                .numberOfItemsPerPage(5)
                .numberOfButtonsPage(4)
                .addListener(new LazyPagingComponentListener<Document>(paginationLayout) {

                    @Override
                    protected Collection<Document> getItemsList(int startIndex, int endIndex) {
                        // Here we can load the items from the DB
                        return res.getDocumentsFromRange(startIndex, endIndex);
                    }

                    @Override
                    protected Component displayItem(int index, Document doc) {
                        return new Label(doc.getTitle());
                    }

                }).build();

        paginationLayout.addComponent(pagingComponent);

        return paginationLayout;
    }

    private List<Integer> createItems(int n) {
        List<Integer> items = new ArrayList<Integer>();
        for (Integer i = 1; i <= n; i++) {
            items.add(i);
        }

        return items;
    }*/

}