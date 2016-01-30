package de.dinkov.vlsapp.samples.backend.model;

/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.presenters.
 * Created by Steliyan Dinkov on 1/23/2016.
 */

public enum Strategy {
    PERSON("Author"), TOPIC("Topic"), DOCUMENTS(
            "Document"), HIGHLY_CITED_AUTHOR("Highly-Cited Papers"), HIGHLY_REFERENCED_AUTHOR(
            "Highly-Referrenced Papers"), MAIN_COAUTHORS_AUTHOR(
            "Main co-authors"), MAIN_TOPICS_AUTHOR("Main topics"), HIGHLY_CITED_TOPIC(
            "Highly-Cited Papers"), IMPORTANT_AUTHORS_TOPIC("Important authors"), CITED_BY_DOC(
            "Cited-by"), REFERENCED_DOC("Referenced-by"), SIMILAR_TOPICS_DOC(
            "Similar topics");

    private final String name;

    private Strategy(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}