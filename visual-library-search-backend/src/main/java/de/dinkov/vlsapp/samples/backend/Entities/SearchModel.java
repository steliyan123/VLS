package de.dinkov.vlsapp.samples.backend.Entities;

/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.backend.Entities.
 * Created by Steliyan Dinkov on 2/28/2016.
 */
import java.util.ArrayList;

public class SearchModel {

    DiagramMainSearchStrategies strategy;
    String keywords;
    ArrayList<Document> documentList = new ArrayList<>();
    //ArrayList<Author> personList = new ArrayList<>();
    ArrayList<Topic> topicList = new ArrayList<>();

    int level;

    public ArrayList<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(ArrayList<Topic> topicList) {
        this.topicList = topicList;
    }

    /*public ArrayList<Author> getPersonList() {
        return personList;
    }

    public void setPersonList(ArrayList<Author> personList) {
        this.personList = personList;
    }*/

    public DiagramMainSearchStrategies getStrategy() {
        return strategy;
    }

    public void setStrategy(DiagramMainSearchStrategies strategy) {
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
