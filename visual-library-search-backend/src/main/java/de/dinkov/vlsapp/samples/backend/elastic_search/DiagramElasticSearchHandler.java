/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.backend.elastic_search.
 * Created by Steliyan Dinkov on 1/23/2016.
 */
package de.dinkov.vlsapp.samples.backend.elastic_search;

import de.dinkov.vlsapp.samples.backend.Entities.Document;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.Map;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

public class DiagramElasticSearchHandler {

    private Node node = nodeBuilder().client(true).build().start();
    private Client client = node.client();
    private String index = "dlsnew";

    public DiagramElasticSearchHandler() { }

    public ArrayList<Document> searchDocument(String type, String name, String field)
    {
        SearchResponse response = client.prepareSearch(index).setTypes(type)
                .setSearchType(SearchType.QUERY_AND_FETCH)
                .setQuery(QueryBuilders.multiMatchQuery(name, field))
                .execute()
                .actionGet();

        SearchHit[] response_results = response.getHits().getHits();
        ArrayList<Document> results_list = new ArrayList<Document>();

        for (SearchHit hit : response_results) {
            Map<String, Object> result = hit.getSource();
            Document d = getDocFromJSON(result);
            results_list.add(d);
        }

        return results_list;
    }

    private Document getDocFromJSON(Map<String, Object> result) {
        Document doc = new Document();
        for (Map.Entry<String, Object> entry : result.entrySet()) {
            String key = entry.getKey();
            switch (key) {
                case "title": doc.setTitle(entry.getValue().toString()); break;
                case "id": doc.setId(entry.getValue().toString()); break;
                case "citedFrom": doc.setReferencedCount(new Integer(entry.getValue().toString())); break;
                case "classification": doc.setClassification(entry.getValue().toString()); break;
                case "cited":
                    ArrayList<Map<String, Object>> citedDocs = (ArrayList<Map<String, Object>>) entry.getValue();
                    doc.setCitedFrom(citedDocs.size());
                    break;
                case "authors":
                    ArrayList<Map<String, Object>> authors = (ArrayList<Map<String, Object>>) entry.getValue();
                    ArrayList<String> authors_list = new ArrayList<String>();

                    for (Map<String, Object> author : authors) {
                        authors_list.add(author.get("name").toString());
                    }
                    doc.setAuthors(authors_list);
                    break;
                case "url": doc.setURL(entry.getValue().toString()); break;
            }
        }
        return doc;
    }

    public void destroy() {
        client.close();
        node.stop();
        node.close();
    }

}