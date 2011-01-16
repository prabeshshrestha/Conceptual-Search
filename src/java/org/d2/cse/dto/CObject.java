/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.d2.cse.dto;

import java.util.Vector;

/**
 *
 * @author sub
 */
public class CObject {
    private Query query;
    private Vector<Document> document;
    private Vector<Cluster> cluster;
    
    public CObject(){
        document=new Vector<Document>();
        cluster=new Vector<Cluster>();
    }

    public CObject(Query query, Vector document, Vector cluster)
    {
        this.query=query;
        this.document=document;
        this.cluster=cluster;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Vector<Document> getDocument() {
        return document;
    }

    public void setDocument(Vector<Document> document) {
        this.document = document;
    }

    public Vector<Cluster> getCluster() {
        return cluster;
    }

    public void setCluster(Vector<Cluster> cluster) {
        this.cluster = cluster;
    }
    
    public void addCluster(Cluster cluster) {
        this.cluster.add(cluster);
    }
    
    public void setQueryObj(Query query) {
        this.query = query;
    }
    
    public void addQueryObj(Query query) {
        this.query=query;
    }
   
    public void Clusterer(CObject cobj){
        for (Document doc: cobj.getDocument()){
            String str=doc.getDocumenttitle();
            
            
        }
    }
    
    @Override
    public CObject clone(){
        CObject cobj=new CObject();
        cobj.setQuery(this.getQuery());
        cobj.setDocument(((Vector<Document>)this.getDocument().clone()));
        //cobj.setCluster(this.getCluster().clone());
        return cobj;
    }
}
