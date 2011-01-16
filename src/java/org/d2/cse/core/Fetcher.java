package org.d2.cse.core;

import org.d2.cse.dto.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import java.util.Vector;
import org.d2.cse.dao.Database;
import org.d2.cse.dao.MySql;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author cSearch
 */
public class Fetcher {

    public Database db = new MySql();
    public Vector documentArray = new Vector();
    private final String HTTP_REFERER = "http://www.yahoo.com/";
    public URL googleUrl;
    public URL yahooUrl;

    /* 
     * @param query
     * takes user query and returns search engine results 
     * @returns searchResults
     * */
    public CObject getQueryResults(Query query) throws UnsupportedEncodingException, MalformedURLException {
        String encodedStr = URLEncoder.encode(query.getQueryname(), "UTF-8");
        /* for now 5*8=40 queries are kept in the database*/
        for (int searchStart = 0; searchStart < 5; searchStart++) {

            this.setYahooUrl(new URL("http://ajax.googleapis.com/ajax/services/search/web?start=" + (searchStart * 8) + "&rsz=large&v=1.0&q=" + encodedStr));
            try {
                URLConnection connection = this.yahooUrl.openConnection();
                connection.addRequestProperty("Referer", HTTP_REFERER);
                // Get the JSON response
                String line;
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                String response = builder.toString();
                JSONObject json = new JSONObject(response);
                JSONArray ja = json.getJSONObject("responseData").getJSONArray("results");

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject j = ja.getJSONObject(i);
                    String URL = j.getString("url");
                    String TITLE = j.getString("title");
                    String CONTENT = j.getString("content");
                    Document d = new Document(URL, TITLE, CONTENT);
                    documentArray.add(d);
                }
            } catch (Exception e) {
                System.err.println("Something went wrong...");
                e.printStackTrace();
            }
        }
        CObject cobject = new CObject();
        cobject.setDocument(documentArray);
        cobject.setQuery(query);
        return cobject;
    }
/*
 * default constructor
 * */
    public Fetcher() {
    }

    /*
     * gets the yahoourl
     * @return URL
     * */
    public URL getYahooUrl() {
        return yahooUrl;
    }

    /*
     * @param URL
     * sets the yahoo URL
     * */
    public void setYahooUrl(URL yahooUrl) {
        this.yahooUrl = yahooUrl;
    }
}
