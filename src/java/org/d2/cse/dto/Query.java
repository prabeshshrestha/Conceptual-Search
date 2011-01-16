package org.d2.cse.dto;

import java.sql.Time;

/**
 *
 * @author cSearch
 */
public class Query {
    private String queryname=null;      //time at which last time the query was searched
    long queryid;
    String querytime=null;
/*
 * default constructor
 * */    
    public Query(){
        
    }
/*
 * @param queryid,queryname
 * parameterized constructor
 * */
     public Query(String queryname, long queryid, String querytime) {
        this.queryname = queryname;
        this.queryid = queryid;
        this.querytime = querytime;
    }   
/*
 * gets the queryname for a given query
 * @retirn queryname
 * */     
    public String getQueryname() {
        return queryname;
    }
/*
 * @param queryname
 * sets the queryname
 * */
    public void setQueryname(String queryname) {
        this.queryname = queryname;
    }
/*
 * gets the queryid for a given query
 * @retirn queryid
 * */  
    public long getQueryid() {
        return queryid;
    }
/*
 * @param queryid
 * sets the queryid
 * */
    public void setQueryid(long queryid) {
        this.queryid = queryid;
    }
/*
 * gets the querytime for a given query
 * @retirn queryname
 * */  
    public String getQuerytime() {
        return querytime;
    }
/*
 * @param querytime
 * sets the querytime
 * */
    public void setQuerytime(String querytime) {
        this.querytime = querytime;
    }

  
}
