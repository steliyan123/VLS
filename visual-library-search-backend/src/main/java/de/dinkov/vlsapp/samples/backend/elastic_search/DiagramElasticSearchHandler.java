package de.dinkov.vlsapp.samples.backend.elastic_search;

import de.dinkov.vlsapp.samples.backend.Entities.Document;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;

import java.util.ArrayList;
import java.util.Map;

/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.backend.elastic_search.
 * Created by Steliyan Dinkov on 1/23/2016.
 */
public class DiagramElasticSearchHandler {

    Node node = nodeBuilder().client(true).node();

    public DiagramElasticSearchHandler() { }

    public ArrayList<Document> searchDocument(String index, String type, String field,
                                              String value, String sortByField)
    {
        Client client = node.client();
        SearchResponse response;

        if (sortByField == null) {
            response = client.prepareSearch(index).setTypes(type)
                    .setSearchType(SearchType.QUERY_AND_FETCH)
                    .setQuery(QueryBuilders.matchQuery(field, value))
                    .setFrom(0).setSize(5).setExplain(true).execute()
                    .actionGet();
        } else {
            response = client.prepareSearch(index).setTypes(type)
                    .setSearchType(SearchType.QUERY_AND_FETCH).setFrom(0)
                    .setSize(5)
                    .setQuery(QueryBuilders.matchQuery(field, value))
                    .addSort(sortByField, SortOrder.DESC).execute().actionGet();
        }

        SearchHit[] response_results = response.getHits().getHits();
        ArrayList<Document> results_list = new ArrayList<Document>();

        System.out.println("Documents count: " + response_results.length);

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
            if (entry.getKey().equals("title")) {
                doc.setTitle(entry.getValue().toString());
            }
        }
        return doc;
    }

    public Document getDocument(String index, String type, String id) {

        Client client = node.client();
        GetResponse getResponse = client.prepareGet(index, type, id).execute().actionGet();
        Map<String, Object> source = getResponse.getSource();
        // node.close();

        if (source != null)
            return getDocFromJSON(source);
        else
            return new Document();
    }

    public void closeNode() {
        node.close();
    }
}