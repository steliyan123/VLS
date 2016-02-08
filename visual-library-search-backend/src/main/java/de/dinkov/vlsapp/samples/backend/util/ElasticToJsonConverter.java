/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.backend.util.
 * Created by Steliyan Dinkov on 2/7/2016.
 */

package de.dinkov.vlsapp.samples.backend.util;

import de.dinkov.vlsapp.samples.backend.Entities.Document;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

public class ElasticToJsonConverter {

    private ArrayList<Document> elasticSearchResults;
    private String strategy = "", term = "";
    private JSONObject obj;

    public ElasticToJsonConverter(String strategy, String searchTerm, ArrayList<Document> results){
        this.elasticSearchResults = results;
        this.strategy = strategy;
        this.term = searchTerm;
        this.obj = new JSONObject();

        LinkedList listChildNodes = new LinkedList();
        int i = 1;
        for (Document d: elasticSearchResults) {
            for (String authorName : d.getAuthors()) {
                LinkedHashMap childrenObj = new LinkedHashMap();
                childrenObj.put("name", authorName);
                childrenObj.put("type", strategy);
                childrenObj.put("nodeId", i++);
                listChildNodes.add(childrenObj);
            }
        }

        obj.put("name", term);
        obj.put("type", "topic");
        obj.put("children", listChildNodes);
    }

    public String getResult() {
        StringWriter out = new StringWriter();
        try {
            obj.writeJSONString(out);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return out.toString();
    }
}
