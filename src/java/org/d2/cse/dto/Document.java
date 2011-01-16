package org.d2.cse.dto;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author cSearch
 */
public class Document {

    private String documentlink;
    private String documenttitle;
    private String documentsnippet;
    private int documentid;
    private Map<String, Integer> termfreqMap = null;
    
    /*
     * default constructor
     * */
    public Document(){
        //termfreqMap = new HashMap<String, Integer>();
    }
 /*
  * @param link,title,snippet
     * parameterized constructor
     * */
    public Document(String link, String title, String snippet) {
        this.documentlink = link;
        this.documenttitle = title;
        this.documentsnippet = snippet;
       
    } 
 /*
  * @param link,title,snippet,documentid
     * parameterized constructor
     * */
    public Document(String link, String title, String snippet,int documentid) {
        this.documentlink = link;
        this.documenttitle = title;
        this.documentsnippet = snippet;
        this.documentid=documentid;
    }    
/*
 * gets the links of a document
 * @return link of a document
 * */
    public String getDocumentlink() {
        return documentlink;
    }
/*
 * @param documentlink string
 * sets the document link of a document
 * */
    public void setDocumentlink(String documentlink) {
        this.documentlink = documentlink;
    }
/*
 * gets the title of a document
 * @return title of a document
 * */
    public String getDocumenttitle() {
        return documenttitle;
    }
/*
 * @param documenttitle string
 * sets the document title of a document
 * */
    public void setDocumenttitle(String documenttitle) {
        this.documenttitle = documenttitle;
    }
/*
 * gets the snippet of a document
 * @return snippet of a document
 * */
    public String getDocumentsnippet() {
        return documentsnippet;
    }
/*
 * @param documentsnippet string
 * sets the document snippet of a document
 * */
    public void setDocumentsnippet(String documentsnippet) {
        this.documentsnippet = documentsnippet;
    }
/*
 * gets the documentid of a document
 * @return id of a document
 * */
    public int getDocumentid() {
        return documentid;
    }
/*
 * @param documentid string
 * sets the document id of a document
 * */
    public void setDocumentid(int documentid) {
        this.documentid = documentid;
    }

    public Map<String, Integer> getTermfreqMap() {
        return termfreqMap;
    }

    public void setTermfreqMap(Map<String, Integer> termfreqMap) {
        this.termfreqMap = termfreqMap;
    }
}
