package de.dinkov.vlsapp.samples.backend.Entities;

/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.backend.Entities.
 * Created by Steliyan Dinkov on 3/14/2016.
 */
import java.util.ArrayList;

public class Author {
    private String db_id = "";
    private String name = "";
    private ArrayList<Document> hisDocuments = new ArrayList<>();
    private ArrayList<Document> referenced = new ArrayList<>();
    private ArrayList<CoAuthor> coAuthors = new ArrayList<>();
    private ArrayList<Topic> topics = new ArrayList<>();

    public Author() {
    }

    public ArrayList<Topic> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<Topic> topics) {
        this.topics = topics;
    }

    public ArrayList<CoAuthor> getCoAuthors() {
        return coAuthors;
    }

    public void setCoAuthors(ArrayList<CoAuthor> coAuthorsList) {
        this.coAuthors = coAuthorsList;
    }

    public ArrayList<Document> getHisDocuments() {
        return hisDocuments;
    }

    public void setHisDocuments(ArrayList<Document> hisDocuments) {
        this.hisDocuments = hisDocuments;
    }

    public ArrayList<Document> getReferrenced() {
        return referenced;
    }

    public void setReferrenced(ArrayList<Document> referrenced) {
        this.referenced = referrenced;
    }

    public Author(String authorName) {
        name = authorName;
    }

    public String getEs_id() {
        return db_id;
    }

    public void setEs_id(String es_id) {
        this.db_id = es_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
