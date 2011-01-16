package org.d2.cse.core;

import java.util.Vector;
import org.d2.cse.dto.CObject;
import org.d2.cse.dto.Cluster;

import org.d2.cse.dto.Document;

/**
 *
 * @author cSearch
 */
public class Clusterer {
/*
 * @param clusterresult 
 * real implementation of the clustering algorithms 
 * @returns clusterresult
 * */
    public  CObject implementAlgorithm(CObject cobject) {
        Vector documentArray = this.cobject.getDocument();
        long gueryid=cobject.getQuery().getQueryid();
        String gueryname=cobject.getQuery().getQueryname();
       Cluster c=new Cluster();
       int cid=1;
       for(Document d: cobject.getDocument()){
           c.addDocuments(d);
           if (c.getDocuments().size()==10){
               cobject.addCluster(c);
               c.setClusterid(cid);
               c.setClustername(gueryname+"-"+cid);
               cid++;
               c=new Cluster();
           }           
       }
       if(c.getDocuments().size()!=0){ cobject.addCluster(c);
               c.setClusterid(cid);
               c.setClustername(gueryname+"-"+cid);
       }
        return cobject;
    }
/*
 * get the clusterresult
 * @returns clusterresult
 * */
    public CObject getResult() {
        return cobject;
    }

/*
 * @param clusterresult
 * sets the clusterresult
 * */
    public void setResult(CObject cobject) {
        this.cobject = cobject;
    }
    public CObject cobject;
    public Parser parse;
    
   
}
