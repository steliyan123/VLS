package de.dinkov.vlsapp.samples;

import de.dinkov.vlsapp.samples.backend.Entities.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Project name: VLS
 * Package name: de.dinkov.vlsapp.samples.backend.Entities.
 * Created by Steliyan Dinkov on 1/31/2016.
 */
public class DiagramElasticSearchResult implements Serializable {

    private static final long serialVersionUID = -2364764071377555924L;

    private ArrayList<Document> elasticResult;
    public DiagramElasticSearchResult(ArrayList<Document> resultSet) {
        this.elasticResult = resultSet;
    }

    /** Returns the IDs of all the products in the "DB" */
    public List<String> getDocumentIds(){
        List<String> listIds=new ArrayList<String>();

        for(Document document:elasticResult){
            listIds.add(document.getId());
        }

        return listIds;
    }

    /** Returns all the products of the "DB" */
    public List<Document> getAllDocuments(){
        return elasticResult;
    }

    /** Returns the number of products of the "DB" */
    public int countAllDocument() {
        return elasticResult.size();
    }

    /** Returns a "page" of the resultSet (if we had a DB) */
    public Collection<Document> getDocumentsFromRange(int indexStart, int indexEnd){
        Collection<Document> dList = elasticResult.subList(indexStart, indexEnd);
        return dList;
    }
}
