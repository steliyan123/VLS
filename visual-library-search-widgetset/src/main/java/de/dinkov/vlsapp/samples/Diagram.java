package de.dinkov.vlsapp.samples;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.*;
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
        addFunction("onPlotClick", new JavaScriptFunction() {
            @Override
            public void call(JsonArray arguments) {
                String nodeName = (String) arguments.getString(0);
                String nodeType = (String) arguments.getString(1);
                String field    = "name";

                DiagramStrategy strategy = new DiagramStrategy(nodeType, nodeName, field);
                ArrayList<Document> result = strategy.applySearchFormStrategySearch().getResult();

                displayPopUp(nodeName, nodeType);
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

    public void displayPopUp(String name, String type) {

        Window subWindow = new Window("Node Name:" + name);
        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        subWindow.setWidth(500, UNITS_PIXELS);
        subWindow.setContent(subContent);

        // Put some components in it
        subContent.addComponent(new Label("Name: " + name));
        subContent.addComponent(new Label("Type: " + type));
        // subContent.addComponent(new Label("Id: " + id));
        // subContent.addComponent(new Button("Awlright"));

        // Center it in the browser window
        subWindow.center();

        // Open it in the UI
        UI.getCurrent().addWindow(subWindow);
    }

}