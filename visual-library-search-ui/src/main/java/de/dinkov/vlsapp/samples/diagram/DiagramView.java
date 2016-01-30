package de.dinkov.vlsapp.samples.diagram;

/**
 * Created by Steliyan on 1/21/2016.
 */

import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.json.simple.JSONObject;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.VerticalLayout;

import de.dinkov.vlsapp.samples.Diagram;

public class DiagramView extends VerticalLayout implements View {

    private static final long serialVersionUID = 1L;

    public static final String VIEW_NAME = "Diagram";
    private Diagram diagram;
    private DiagramViewPresenter viewPresenter = new DiagramViewPresenter(this);

    public DiagramView() {
        CustomLayout diagramContent = new CustomLayout("diagramview");
        diagramContent.setStyleName("diagram-content");

        /*******************************************************************/
        LinkedHashMap childrenObj = new LinkedHashMap();
        childrenObj.put("name", "The Hamburget Postulate");
        childrenObj.put("type", "document");
        childrenObj.put("nodeId", new Integer(3534));

        LinkedHashMap childrenObj1 = new LinkedHashMap();
        childrenObj1.put("name", "Mendeleev");
        childrenObj1.put("type", "author");
        childrenObj1.put("nodeId", new Integer(7734));

        LinkedList listChildNodes = new LinkedList();
        listChildNodes.add(childrenObj);
        listChildNodes.add(childrenObj1);
        JSONObject obj = new JSONObject();
        obj.put("name", "Science");
        obj.put("type", "topic");
        childrenObj.put("nodeId", new Integer(534));
        obj.put("children", listChildNodes);
        obj.put("children", listChildNodes);
        StringWriter out = new StringWriter();
        try {
            obj.writeJSONString(out);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String jsonText = out.toString();
        /*******************************************************************************/
        diagram = new Diagram(jsonText);
        diagramContent.addComponent(diagram, "diagram");

        setSizeFull();
        setStyleName("diagram-view");
        addComponent(diagramContent);
        setComponentAlignment(diagramContent, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

}