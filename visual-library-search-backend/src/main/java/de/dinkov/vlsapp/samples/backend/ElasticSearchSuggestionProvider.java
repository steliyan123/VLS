package de.dinkov.vlsapp.samples.backend;

import com.vaadin.ui.Notification;
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteQuery;
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteSuggestion;
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteSuggestionProvider;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.elasticsearch.search.SearchHit;

import java.util.*;

/**
 * Created by Steliyan Dinkov on 5/31/2016.
 */
public class ElasticSearchSuggestionProvider implements AutocompleteSuggestionProvider{


    // I'll assume the return value of nodeBuilder() is NodeBuilder
    private final NodeBuilder nodeBuilder;

    public ElasticSearchSuggestionProvider(NodeBuilder nodeBuilder) {
        this.nodeBuilder = nodeBuilder;
    }

    @Override
    public Collection<AutocompleteSuggestion> querySuggestions(AutocompleteQuery query) {

        // Get required information from the query
        String searchFieldInput = query.getTerm();
        int searchLimit = query.getLimit();

        Map<String, Object> result;
        Node node = nodeBuilder.client(true).node();
        Client client = node.client();
        SearchResponse response = null;
        try {
            response = client.prepareSearch("dlsnew")
                    .setTypes("person", "document")
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(QueryBuilders.multiMatchQuery(searchFieldInput,
                            "document.title^10",
                            "document.title.shingles^2",
                            "document.title.ngrams",
                            "person.name^10",
                            "person.name.shingles^2",
                            "person.name.ngrams",
                            "document.topics.name^10",
                            "document.topics.name.shingles^2",
                            "document.topics.name.ngrams")
                            .operator(MatchQueryBuilder.Operator.AND)
                            .type(MultiMatchQueryBuilder.Type.MOST_FIELDS))
                    .setFrom(0)
                    .setSize(50)
                   // .setSize(searchLimit)
                    .execute()
                    .actionGet();
        } catch (ElasticsearchException e){
            Notification notification = new Notification(
                    "Quering ES faieled!", Notification.Type.TRAY_NOTIFICATION);
            System.out.println("An Error occured while querying Elastic Search!");
        }

        if (response != null) {
            SearchHit[] results = response.getHits().getHits();

            List<AutocompleteSuggestion> suggestions = new ArrayList<>(results.length);

            for (SearchHit hit : results) {
                result = hit.getSource();
                if(result!=null)
                {

                    if (result.containsKey("name")){
                        String name = result.get("name").toString();
                        suggestions.add(new AutocompleteSuggestion(name));
                    }
                    if (result.containsKey("title")){
                        String title = result.get("title").toString();
                        suggestions.add(new AutocompleteSuggestion(title));
                    }
                }
            }
            // Return your suggestions
            return suggestions;
        } else {
            // Return an empty list
            return Collections.emptyList();
        }
    }

}