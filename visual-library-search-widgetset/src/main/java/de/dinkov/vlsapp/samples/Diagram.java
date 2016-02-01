package de.dinkov.vlsapp.samples;

import com.vaadin.annotations.JavaScript;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.vaadin.pagingcomponent.PagingComponent;
//import org.vaadin.pagingcomponent.listener.impl.SimplePagingComponentListener;

import de.dinkov.vlsapp.samples.backend.Entities.Document;
import de.dinkov.vlsapp.samples.backend.elastic_search.DiagramStrategy;

import elemental.json.JsonArray;
import org.vaadin.pagingcomponent.listener.impl.LazyPagingComponentListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JavaScript({ "d3.min.js", "diagram_connector.js", "jquery-1.11.3.min.js" })
public class Diagram extends AbstractJavaScriptComponent {

    public Diagram() {
        // callback that will be called from the client-side
        addCallBacks();
    }
    public void addCallBacks(){
        addFunction("onPlotClick", new JavaScriptFunction() {
            @Override
            public void call(JsonArray arguments) {
                String authorName = (String) arguments.getString(0);
                String strategy = (String) arguments.getString(1);
                String field    = "name";

                DiagramStrategy diagramStrategy = new DiagramStrategy(strategy, authorName, field);
                ArrayList<Document> result = diagramStrategy.applySearchFormStrategySearch().getResult();

                displayPopUp(authorName, result);
            }
        });
    }

    public void addTreeData(String data) {
        getState().treeData = data;
    }

    public void displayMsg(String newName) {
        callFunction("displayMsg", newName);
    }

    @Override
    public DiagramState getState() {
        return (DiagramState) super.getState();
    }

    public void displayPopUp(String author, ArrayList<Document> result) {

        Window popup = new Window(author);
        //VerticalLayout subContent = new VerticalLayout();
        Grid subContent = new Grid();
        subContent.addColumn("ID", String.class);
        subContent.addColumn("Title", String.class);
       // subContent.addColumn("Cited from", int.class);
       // subContent.addColumn("Referenced by", String.class);
       // subContent.addColumn("URL", Link.class);

       // subContent.setMargin(true);
      //  popup.setWidth(500, Unit.PIXELS);
        popup.center();

        int i = 0;
        for (Document doc: result) {
            /*subContent.addComponent(new Label("Id: " + doc.getId()));
            subContent.addComponent(new Label("Title: " + doc.getTitle()));
            //subContent.addComponent(new Label("Classification: " + doc.getTitle()));
            subContent.addComponent(new Label("Cited from: " + doc.getCitedFrom()));
            subContent.addComponent(new Label("Referenced by: " + doc.getReferencedCount()));
            String url = doc.getURL();
            subContent.addComponent(new Link(url, new ExternalResource(url)));
            subContent.addComponent(new Label("<hr>", ContentMode.HTML));
            if (i++ > 3) break;*/
            String url = doc.getURL();
            Link link = new Link(url, new ExternalResource(url));
            subContent.addRow(doc.getId(),doc.getTitle());

        }

        //subContent.addComponent(getPagination(result));
        popup.setContent(subContent);



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