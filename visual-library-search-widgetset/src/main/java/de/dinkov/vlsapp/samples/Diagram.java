package de.dinkov.vlsapp.samples;

import com.vaadin.annotations.JavaScript;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.HtmlRenderer;
import de.dinkov.vlsapp.samples.backend.Entities.Document;
import de.dinkov.vlsapp.samples.backend.elastic_search.DiagramStrategy;

import java.util.ArrayList;

@JavaScript({ "d3.min.js", "diagram_connector.js", "jquery-1.11.3.min.js" })
public class Diagram extends AbstractJavaScriptComponent {

    public Diagram() {
        // callback that will be called from the client-side
        getPlotClickData();
    }
    public void getPlotClickData(){
        addFunction("onPlotClick", (JavaScriptFunction) arguments -> {
            String authorName = arguments.getString(0);
            String strategy = arguments.getString(1);
            String field    = "name";
            String theWholeThing = arguments.toJson();
            Notification not = new Notification(theWholeThing);
          //  DiagramStrategy diagramStrategy = new DiagramStrategy(strategy, authorName, field);
          //  ArrayList<Document> result = diagramStrategy.applySearchFormStrategySearch().getResult();
            not.show(Page.getCurrent());
           // displayPopUp(authorName, result);
            //displayPopUp(authorName, result);
        });
    }

    public String getSanitizedTreeData(){
        final String[] theWholeThing = {new String()};
        addFunction("returnSanitizedData", (JavaScriptFunction) arguments -> {
           theWholeThing[0] = arguments.toJson();

        });
        return theWholeThing[0];
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

   /* public void displayPopUp(String author, ArrayList<Document> result) {

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
            *//*grid.addComponent(new Label("Id: " + doc.getId()));
            grid.addComponent(new Label("Title: " + doc.getTitle()));
            //grid.addComponent(new Label("Classification: " + doc.getTitle()));
            grid.addComponent(new Label("Cited from: " + doc.getCitedFrom()));
            grid.addComponent(new Label("Referenced by: " + doc.getReferencedCount()));
            String url = doc.getURL();
            grid.addComponent(new Link(url, new ExternalResource(url)));
            grid.addComponent(new Label("<hr>", ContentMode.HTML));
            if (i++ > 3) break;*//*
            grid.addRow(doc.getId(), doc.getTitle(), "<a href='" + doc.getURL() + "' target='_blank'>Source</a>");
        }

        popup.setContent(content);

        // Open it in the UI
        UI.getCurrent().addWindow(popup);
    }*/
}