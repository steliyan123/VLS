package de.dinkov.vlsapp.samples.backend.model;

/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.backend.model.
 * Created by Steliyan Dinkov on 1/29/2016.
 */
import java.util.ArrayList;

public class SearchModel {

    Strategy strategy;
    String keywords;
    ArrayList<Document> documentList = new ArrayList<Document>();
    ArrayList<Author> personList = new ArrayList<Author>();
    ArrayList<Topic> topicList = new ArrayList<Topic>();

    int level;

    public ArrayList<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(ArrayList<Topic> topicList) {
        this.topicList = topicList;
    }

    public ArrayList<Author> getPersonList() {
        return personList;
    }

    public void setPersonList(ArrayList<Author> personList) {
        this.personList = personList;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public ArrayList<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(ArrayList<Document> documentList) {
        this.documentList = documentList;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
