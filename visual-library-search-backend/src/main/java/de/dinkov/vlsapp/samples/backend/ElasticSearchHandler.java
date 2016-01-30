package de.dinkov.vlsapp.samples.backend;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import de.dinkov.vlsapp.samples.backend.model.*;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

public class ElasticSearchHandler {

    Node node = nodeBuilder().client(true).node();

    public ElasticSearchHandler() {

    }

    public ArrayList<Author> searchPerson(String index, String type,
                                          String field, String value, String sortByField) {

        Client client = node.client();

        ArrayList<Author> personList = new ArrayList<Author>();

        SearchResponse response = null;
        try {
            if (sortByField == null) {
                response = client.prepareSearch(index).setTypes(type)
                        .setSearchType(SearchType.QUERY_AND_FETCH).setFrom(0)
                        .setSize(5)
                        .setQuery(QueryBuilders.matchQuery(field, value))
                        .execute().actionGet();
            } else {
                response = client.prepareSearch(index).setTypes(type)
                        .setSearchType(SearchType.QUERY_AND_FETCH).setFrom(0)
                        .setSize(5)
                        .setQuery(QueryBuilders.matchQuery(field, value))
                        .addSort(sortByField, SortOrder.DESC).execute()
                        .actionGet();
            }

        } catch (ElasticsearchException e) {
            showError();
            return personList;
        }

        if (response != null) {
            SearchHit[] response_results = response.getHits().getHits();
            for (SearchHit hit : response_results) {
                Map<String, Object> result = hit.getSource();
                Author r;
                if (result != null) {
                    r = getPersonFromJSON(result);
                    r.setEs_id(hit.getId());
                    personList.add(r);
                }
            }
        }
        System.out.println("Persons count: " + personList.size());
        return personList;
    }

    private void showError() {
        Notification notif = new Notification("ERROR",
                "Elasticsearch is not running", Notification.Type.ERROR_MESSAGE);
        notif.setDelayMsec(4000);
        notif.setPosition(Position.MIDDLE_CENTER);
        notif.show(Page.getCurrent());
        notif.setStyleName("mystyle");
    }

    private Author getPersonFromJSON(Map<String, Object> result) {
        Author author = new Author();

        for (Map.Entry<String, Object> entry : result.entrySet()) {
            if (entry.getKey().equals("name")) {
                author.setName(entry.getValue().toString());
            } else if (entry.getKey().equals("documents")) {
                ArrayList<Map<String, Object>> hisDocs = (ArrayList<Map<String, Object>>) entry
                        .getValue();
                ArrayList<Document> hisDocsList = new ArrayList<Document>();

                for (Map<String, Object> doc : hisDocs) {
                    Document d = new Document(doc.get("id"));
                    d.setCitedFrom(Integer.valueOf(doc.get("citedFrom")
                            .toString()));
                    hisDocsList.add(d);
                }
                Collections.sort(hisDocsList, Document.compareCitedFromCount);
                author.setHisDocuments(hisDocsList);
            } else if (entry.getKey().equals("referenced")) {
                ArrayList<Map<String, Object>> referenced = (ArrayList<Map<String, Object>>) entry
                        .getValue();
                ArrayList<Document> referencedList = new ArrayList<Document>();

                for (Map<String, Object> refDoc : referenced) {
                    Document d = new Document(refDoc.get("id"));
                    d.setReferencedCount(Integer.valueOf(refDoc.get("count")
                            .toString()));
                    referencedList.add(d);
                }
                Collections.sort(referencedList, Document.compareReferenceCount);
                author.setReferrenced(referencedList);
            } else if (entry.getKey().equals("coAuthors")) {
                ArrayList<Map<String, Object>> coAuthors = (ArrayList<Map<String, Object>>) entry
                        .getValue();
                ArrayList<CoAuthor> coAuthorsList = new ArrayList<CoAuthor>();
                for (Map<String, Object> coAuthor : coAuthors) {
                    CoAuthor d = new CoAuthor(coAuthor.get("name").toString(),
                            Integer.valueOf(coAuthor.get("count").toString()));
                    coAuthorsList.add(d);
                }
                Collections.sort(coAuthorsList, CoAuthor.compareCount);
                author.setCoAuthors(coAuthorsList);
            } else if (entry.getKey().equals("topics")) {
                ArrayList<Map<String, Object>> topics = (ArrayList<Map<String, Object>>) entry
                        .getValue();
                ArrayList<Topic> topicsList = new ArrayList<Topic>();

                for (Map<String, Object> topic : topics) {
                    Topic d = new Topic(topic.get("name").toString(),
                            Integer.valueOf(topic.get("count").toString()));
                    topicsList.add(d);
                }
                Collections.sort(topicsList, Topic.compareCount);
                author.setTopics(topicsList);
            }
        }
        return author;
    }

    public ArrayList<Document> searchDocument(String index, String type,
                                              String field, String value, String sortByField) {

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
            if (entry.getKey().equals("authors")) {
                ArrayList<Map<String, Object>> authors = (ArrayList<Map<String, Object>>) entry
                        .getValue();
                ArrayList<String> authors_list = new ArrayList<String>();

                for (Map<String, Object> author : authors) {
                    authors_list.add(author.get("name").toString());
                }
                doc.setAuthors(authors_list);
            } else if (entry.getKey().equals("classification")) {
                doc.setClassification(entry.getValue().toString());
            } else if (entry.getKey().equals("url")) {
                doc.setURL(entry.getValue().toString());
            } else if (entry.getKey().equals("title")) {
                doc.setTitle(entry.getValue().toString());
            } else if (entry.getKey().equals("rawsource")) {
                doc.setRawsource(entry.getValue().toString());
            } else if (entry.getKey().equals("source")) {
                doc.setSource(entry.getValue().toString());
            } else if (entry.getKey().equals("pubyear")) {
                doc.setYear(entry.getValue().toString());
            } else if (entry.getKey().equals("abstract")) {
                doc.setAbstractt(entry.getValue().toString());
            } else if (entry.getKey().equals("id")) {
                doc.setId(entry.getValue().toString());
            } else if (entry.getKey().equals("citedFrom")) {
                doc.setCitedFrom((Integer.valueOf(entry.getValue().toString())));
            } else if (entry.getKey().equals("cited")) {
                ArrayList<Map<String, Object>> citedDocs = (ArrayList<Map<String, Object>>) entry
                        .getValue();
                ArrayList<Document> citedDocs_list = new ArrayList<Document>();

                for (Map<String, Object> citedDoc : citedDocs) {
                    citedDocs_list.add(new Document(citedDoc.get("id")
                            .toString()));
                }
                Collections
                        .sort(citedDocs_list, Document.compareCitedFromCount);
                doc.setReferencedDocs(citedDocs_list);
                doc.setReferencedCount(citedDocs_list.size());
            } else if (entry.getKey().equals("topics")) {
                ArrayList<Map<String, Object>> topics = (ArrayList<Map<String, Object>>) entry
                        .getValue();
                ArrayList<Topic> topicsList = new ArrayList<Topic>();

                for (Map<String, Object> topic : topics) {
                    Topic d = new Topic(topic.get("name").toString());
                    topicsList.add(d);
                }
                Collections.sort(topicsList, Topic.compareCount);
                doc.setTopics(topicsList);
            }
        }
        return doc;
    }

    public Document getDocument(String index, String type,
                                String id) {

        Client client = node.client();

        GetResponse getResponse = client.prepareGet(index, type, id).execute()
                .actionGet();
        Map<String, Object> source = getResponse.getSource();

        // node.close();

        if (source != null) {
            return getDocFromJSON(source);
        } else
            return null;
    }

    public Author getPerson(String index, String type, String id) {

        Client client = node.client();

        GetResponse getResponse = client.prepareGet(index, type, id).execute()
                .actionGet();
        Map<String, Object> source = getResponse.getSource();

        return getPersonFromJSON(source);
    }

    public void closeNode() {
        node.close();
    }
}