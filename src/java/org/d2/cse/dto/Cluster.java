package org.d2.cse.dto;

import java.util.Vector;


/**
 *
 * @author cSearch
 */
public class Cluster {

    private long clusterid;
    private String clustername;
    private Vector<Document> documents;
    
    /*
     * default constructor
     * */
    public Cluster(){
         documents=new Vector<Document>();
    }
/*
 * paramaterized constructor
 * 
 * */
    public Cluster(long clusterid, String clustername) {
        this.clusterid = clusterid;
        this.clustername = clustername;
    }
    
   
/*
 * gets clusterid for cluster
 * @return clusterid
 * */
    public long getClusterid() {
        return clusterid;
    }

/*
 * @param clusterid 
 * set the clusterid for cluster
 * */
    public void setClusterid(long clusterid) {
        this.clusterid = clusterid;
    }
/*
 * gets clusterid for clustername
 * @return clustername
 * */
    public String getClustername() {
        return clustername;
    }
/*
 * @param clustername 
 * set the clustername for cluster
 * */
    public void setClustername(String clustername) {
        this.clustername = clustername;
    }
/*
 * gets documents for a cluster in the vector form
 * @return document
 * */
    public Vector<Document> getDocuments() {
        return documents;
    }
/*
 * @param Document in vector form 
 * set documents for a cluster in the vector form
 * */
    public void setDocuments(Vector<Document> documents) {
        this.documents = documents;
    }
    
 /*
  * @param document
  * adds the document in the documentlist
  * */
    public void addDocuments(Document doc) {
        this.documents.add(doc);
    }
   
}