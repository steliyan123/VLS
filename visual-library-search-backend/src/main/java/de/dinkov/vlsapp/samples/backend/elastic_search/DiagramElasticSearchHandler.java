/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.backend.elastic_search.
 * Created by Steliyan Dinkov on 1/23/2016.
 */
package de.dinkov.vlsapp.samples.backend.elastic_search;

import java.lang.String;
import de.dinkov.vlsapp.samples.backend.Entities.Document;
//import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.node.Node;
//import static org.elasticsearch.node.NodeBuilder.nodeBuilder;
import org.elasticsearch.search.SearchHit;
import java.util.ArrayList;
import java.util.Map;

public class DiagramElasticSearchHandler {

    //Node node;
    Client client;

    public DiagramElasticSearchHandler() {
        //node = nodeBuilder().client(true).node();
        //client = node.client();
        Settings settings = ImmutableSettings.settingsBuilder().put("client.transport.sniff", true).build();
        client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost", 9200));
    }

    public ArrayList<Document> searchDocument(String index, String type, String name, String field)
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
        //node.close();
        return results_list;
    }

    private Document getDocFromJSON(Map<String, Object> result) {
        Document doc = new Document();
        for (Map.Entry<String, Object> entry : result.entrySet()) {
            String key = entry.getKey();
            if (key.equals("title")) {
                doc.setTitle(entry.getValue().toString());
            }
        }
        return doc;
    }

    public void destroy()
    {
        client.close();
    }

    /*public Document getDocument(String index, String type, String id) {

        Client client = node.client();
        GetResponse getResponse = client.prepareGet(index, type, id).execute().actionGet();
        Map<String, Object> source = getResponse.getSource();
        // node.close();

        if (source != null)
            return getDocFromJSON(source);
        else
            return new Document();
    }*/

    /*public void closeNode() {
        node.close();
    }*/
}