package de.dinkov.vlsapp.samples.presenters;

/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.presenters.
 * Created by Steliyan Dinkov on 1/30/2016.
 */
import com.vaadin.ui.UI;
import de.dinkov.vlsapp.samples.backend.ElasticSearchHandler;
import de.dinkov.vlsapp.samples.backend.model.Strategy;
import de.dinkov.vlsapp.samples.helpclasses.ImportantPoints;
import de.dinkov.vlsapp.samples.interfaces.ViewPort;
import de.dinkov.vlsapp.samples.backend.model.*;
import de.dinkov.vlsapp.samples.diagram.ViewPortImpl;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class ViewPortPresenter implements ViewPort.ViewPortListener {
    ViewPortImpl viewPort;
    SearchSession searchSession;

    public ViewPortPresenter(ViewPortImpl viewport, SearchSession newSS) {
        viewPort = viewport;
        viewPort.addListener(this);
        searchSession = newSS;
    }

    @Override
    public void applySearchFormStrategySearch(Strategy strategy,
                                              String keywords,
                                              Point location, ImportantPoints startingPoints, int parentID) {
        viewPort.addStyleName("loading");
        UI.getCurrent().push();

        ElasticSearchHandler eshandler = new ElasticSearchHandler();
        String index = "dlsnew";
        String type = "";
        String field = "";
        String value = keywords;
        String sortByField = "citedFrom";

        SearchModel searchModel = new SearchModel();
        searchModel.setStrategy(strategy);
        searchModel.setKeywords(keywords);
        searchSession.add(searchModel);

        ArrayList<Document> docs;
        ArrayList<Author> authors;

        switch (strategy) {
            case PERSON:
                type = "person";
                field = "name";

                authors = eshandler.searchPerson(index, type,
                        field, value, null);
                searchModel.setPersonList(authors);
                break;

            case TOPIC:
                type = "document";
                field = "topics.name";

                docs = eshandler
                        .searchDocument(index,
                                type, field, value, sortByField);
                searchModel.setDocumentList(docs);
                break;

            case DOCUMENTS:
                type = "document";
                field = "title";

                docs = eshandler.searchDocument(index,
                        type, field, value, null);
                searchModel.setDocumentList(docs);
                break;

            case HIGHLY_CITED_TOPIC:
                type = "document";
                field = "topics.name";

                docs = eshandler.searchDocument(index,
                        type, field, value, sortByField);
                searchModel.setDocumentList(docs);
                break;

            case IMPORTANT_AUTHORS_TOPIC:
                type = "person";
                field = "topics.name";
                authors = eshandler.searchPerson(index, type, field, value,
                        "topics.count");
                searchModel.setPersonList(authors);
                break;

        }
        eshandler.closeNode();

        viewPort.createNewView(strategy, searchModel, location, startingPoints,
                parentID);
    }


    @Override
    public void applyPersonStategy(Strategy strategy, Author author,
                                   Point location, ImportantPoints startingPoints, int parentID) {

        viewPort.addStyleName("loading");
        UI.getCurrent().push();

        ElasticSearchHandler eshandler = new ElasticSearchHandler();
        String index = "dlsnew";

        // when person strategy is applied from a Document View, the author
        // object has only name
        // with this if-statement, the author object gets the related
        // document-ids
        if (author.getHisDocuments().size() == 0) {
            author = eshandler.searchPerson(index, "person", "name",
                    "\"" + author.getName() + "\"", null).get(0);
        }

        ArrayList<Document> author_docs_list = new ArrayList<Document>();
        ArrayList<CoAuthor> author_coAuthors_list = new ArrayList<CoAuthor>();
        ArrayList<Document> newDocs = new ArrayList<Document>();
        ArrayList<Author> newPersons = new ArrayList<Author>();
        ArrayList<Topic> topics_list = new ArrayList<Topic>();

        SearchModel sm = new SearchModel();
        if (strategy == Strategy.MAIN_TOPICS_AUTHOR) {
            topics_list = author.getTopics();
            if (topics_list.size() != 0) {
                sm.setTopicList(topics_list);
                sm.setStrategy(strategy);
                sm.setKeywords(author.getName());
            }
        } else if (strategy == Strategy.MAIN_COAUTHORS_AUTHOR) {
            author_coAuthors_list = author.getCoAuthors();

            for (CoAuthor coAname : author_coAuthors_list) {
                Author coAuthor = null;
                if (coAname.getName().length() > 2) {
                    coAuthor = eshandler.searchPerson(index, "person", "name",
                            "\"" + coAname.getName() + "\"", null).get(0);
                }

                if (coAuthor != null) {
                    newPersons.add(coAuthor);
                }
            }
            if (newPersons.size() != 0) {
                sm.setPersonList(newPersons);
                sm.setStrategy(strategy);
                sm.setKeywords(author.getName());
            }
        } else if (strategy == Strategy.HIGHLY_CITED_AUTHOR) {
            author_docs_list = author.getHisDocuments();
        } else if (strategy == Strategy.HIGHLY_REFERENCED_AUTHOR) {
            author_docs_list = author.getReferrenced();
        }

        if (strategy == Strategy.HIGHLY_CITED_AUTHOR
                || strategy == Strategy.HIGHLY_REFERENCED_AUTHOR) {
            for (Document doc : author_docs_list) {
                int i = 0;
                if (i < 5) {
                    Document d = eshandler.getDocument(index, "document",
                            String.valueOf(doc.getId()));
                    if (d != null) {
                        newDocs.add(d);
                    }
                    i++;
                }
            }
            if (newDocs.size() != 0) {
                sm.setDocumentList(newDocs);
                sm.setStrategy(strategy);
                sm.setKeywords(author.getName());
            }

        }
        eshandler.closeNode();
        searchSession.add(sm);
        viewPort.createNewView(strategy, sm, location, startingPoints, parentID);

        viewPort.removeStyleName("loading");
    }


    @Override
    public void applyDocumentStategy(Strategy strategy, Document mainDoc,
                                     Point location, ImportantPoints startingPoints, int parentID) {

        viewPort.addStyleName("loading");
        UI.getCurrent().push();

        ElasticSearchHandler eshandler = new ElasticSearchHandler();
        String index = "dlsnew";
        String type = "document";
        String value = "";

        ArrayList<Document> newDocs = new ArrayList<Document>();
        SearchModel sm = new SearchModel();

        switch (strategy) {
            case REFERENCED_DOC:
                for (Document docId : mainDoc.getReferencedDocs()) {
                    int i = 0;
                    if (i < 5) {
                        Document d = eshandler.getDocument(index, type,
                                docId.getId());
                        if (d != null)
                            newDocs.add(d);
                        i++;
                    }
                }
                Collections.sort(newDocs, Document.compareCitedFromCount);
                eshandler.closeNode();
                break;

            case CITED_BY_DOC:
                String field = "cited.id";
                value = mainDoc.getId();

                newDocs = eshandler.searchDocument(index, type, field, value, null);

                Collections.sort(newDocs, Document.compareCitedFromCount);
                eshandler.closeNode();
                break;

            case SIMILAR_TOPICS_DOC:
                type = "document";
                field = "topics.name";
                value = mainDoc.getTopics().get(0).getName();

                newDocs = eshandler.searchDocument(index, type, field, value,
                        "citedFrom");

                for (Document newDoc : newDocs) {
                    if (newDoc.getId().equals(mainDoc.getId())) {
                        newDocs.remove(newDoc);
                        break;
                    }
                }
                break;

        }
        if (newDocs.size() != 0) {
            sm.setDocumentList(newDocs);
            sm.setStrategy(strategy);
            sm.setKeywords(mainDoc.getId());
            searchSession.add(sm);
        }
        viewPort.createNewView(strategy, sm, location, startingPoints, parentID);
        viewPort.removeStyleName("loading");
    }
}
