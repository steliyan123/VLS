/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.backend.model.
 * Created by Steliyan Dinkov on 1/23/2016.
 */
package de.dinkov.vlsapp.samples.backend.model;

import java.util.ArrayList;
import java.util.Comparator;

public class Document {
    private String db_id;
    private String URL = "";
    private String classification = "";
    private ArrayList<String> authors = new ArrayList<String>();
    private ArrayList<Topic> topics = new ArrayList<Topic>();
    private ArrayList<Document> referencedDocs = new ArrayList<Document>();
    private String title = "";
    private String source = "";
    private String abstractt = "";
    private String rawsource = "";
    private String year = "";
    private int referencedCount = 0;
    private int citedFrom = 0;

    public Document(Object id) {
        this.db_id = id.toString();
    }

    public Document() {
    }

    public ArrayList<Topic> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<Topic> topics) {
        this.topics = topics;
    }

    public static Comparator<Document> getDocCitedFromCount() {
        return compareCitedFromCount;
    }

    public static void setDocCitedFromCount(
            Comparator<Document> docCitedFromCount) {
        Document.compareCitedFromCount = docCitedFromCount;
    }

    public static Comparator<Document> getDocReferenceCount() {
        return compareReferenceCount;
    }

    public static void setDocReferenceCount(
            Comparator<Document> docReferenceCount) {
        Document.compareReferenceCount = docReferenceCount;
    }

    public int getReferencedCount() {
        return referencedCount;
    }

    public void setReferencedCount(int referencedC) {
        this.referencedCount = referencedC;
    }

    public int getCitedFrom() {
        return citedFrom;
    }

    public void setCitedFrom(int citedFrom) {
        this.citedFrom = citedFrom;
    }

    public String getId() {
        return db_id;
    }

    public void setId(String id) {
        this.db_id = id;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String uRL) {
        URL = uRL;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAbstractt() {
        return abstractt;
    }

    public void setAbstractt(String abstractt) {
        this.abstractt = abstractt;
    }

    public String getRawsource() {
        return rawsource;
    }

    public void setRawsource(String rawsource) {
        this.rawsource = rawsource;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public static Comparator<Document> compareCitedFromCount = new Comparator<Document>() {

        @Override
        public int compare(Document d1, Document d2) {
            int doc1 = d1.getCitedFrom();
            int doc2 = d2.getCitedFrom();

            return doc2 - doc1;
        }
    };

    /*Comparator for sorting the list by roll no*/
    public static Comparator<Document> compareReferenceCount = new Comparator<Document>() {

        @Override
        public int compare(Document d1, Document d2) {

            int doc1 = d1.getReferencedCount();
            int doc2 = d2.getReferencedCount();

            return doc2 - doc1;
        }
    };

    public ArrayList<Document> getReferencedDocs() {
        return referencedDocs;
    }

    public void setReferencedDocs(ArrayList<Document> rDocs_list) {
        this.referencedDocs = rDocs_list;
    }
}
