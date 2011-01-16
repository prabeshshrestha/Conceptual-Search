package org.d2.cse.dao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.d2.cse.core.Parser;
import org.d2.cse.dto.CObject;
import org.d2.cse.dto.Cluster;

import org.d2.cse.dto.Query;
import org.d2.cse.dto.Document;

/**
 *
 * @author cSearch
 */
public class MySql implements Database {

    private final String HOSTURL = "jdbc:mysql://localhost/clustering_search";
    private final String USERNAME = "root";
    private final String PASSWORD = "vertrigo";
    private Statement stmt = null;
    public Vector clusterArray = new Vector();
    private ResultSet rs = null;
    private ResultSet rs1 = null;
    private PreparedStatement pstmt;
    private final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    public Cluster clu;

    /*
     * @param results without the documentId
     * Stores the results of the searchresult in the database 
     * Adds respective documentId to each documents (element of SearchResults)
     * @returns results with document id added to each document
     * 
     * */
    public CObject insertResults(CObject cobject) {
        Time time;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = (Connection) DriverManager.getConnection(HOSTURL, USERNAME, PASSWORD);
            stmt = (Statement) conn.createStatement();
            Vector documentArray = cobject.getDocument();
            Query query = cobject.getQuery();
            query = this.insertQuery(query);
            for (int i = 0; i < documentArray.size(); i++) {
                Document doc = (Document) documentArray.get(i);
                doc.setDocumentid(i + 1);

                //clear <b> and </b> tags before entering into the database
                doc.setDocumentlink(Parser.ClearHTMLTags(doc.getDocumentlink(), -1));
                doc.setDocumentsnippet(Parser.ClearHTMLTags(doc.getDocumentsnippet(), -1));
                doc.setDocumenttitle(Parser.ClearHTMLTags(doc.getDocumenttitle(), -1));
                String queryForDocumentTable = "insert into result (link,snippet,title,queryId,resultId) values ('" + doc.getDocumentlink() + "','" + doc.getDocumentsnippet() + "','" + doc.getDocumenttitle() + "','" + query.getQueryid() + "','" + doc.getDocumentid() + "')";
                stmt.executeUpdate(queryForDocumentTable);
            }
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cobject;
    }

    /*Implement some method to generate unique QueryID
     * For now it returns the default queryId
     * */
    public int generateQueryId() {
        return 1;
    //put auto-increment in QueryId in Database
    }

    /*  
     * Checks if the Query entered by the user already exists in Database or not
     * @param query Query Object to be checked
     * @returns existInDB true if exists in the database else false
     */
    public boolean existQueryInDB(Query query) {
        String existInDB = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = (Connection) DriverManager.getConnection(HOSTURL, USERNAME, PASSWORD);
            stmt = (Statement) conn.createStatement();
            String sqlQuery = "SELECT queryName FROM query WHERE queryName='" + query.getQueryname() + "'";
            rs = (ResultSet) stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                existInDB = rs.getString("queryName");
            }
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (existInDB == null) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * gets the queryId for each new query the funtion gets
     * @param query(Query) without queryId
     * @returns query(Query) with queryId
     * */
    public Query getQueryId(Query query) {
        String existInDB = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = (Connection) DriverManager.getConnection(HOSTURL, USERNAME, PASSWORD);
            stmt = (Statement) conn.createStatement();
            String sqlQuery = "SELECT queryId FROM query WHERE queryName='" + query.getQueryname() + "'";
            rs = (ResultSet) stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                query.setQueryid(rs.getInt("queryId"));
                existInDB = rs.getString("queryName");
            }
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

    /*
     * Inserts new Query into database
     * @param query  Keyword to be searched (Entered by the GUI user)
     * @returns query after inserting the query into the database new queryId is added to the Query Object
     * */
    public synchronized Query insertQuery(Query query) {
        long queryId = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = (Connection) DriverManager.getConnection(HOSTURL, USERNAME, PASSWORD);
            stmt = (Statement) conn.createStatement();
            //queryId = generateQueryId();
            queryId = 1;
            String queryForQueryId = " select max(queryId) queryId from query ";
            ResultSet res = stmt.executeQuery(queryForQueryId);
            if (res.next()) {
                queryId = res.getLong("queryId");
                queryId += 1;
                query.setQueryid(queryId);
            }
            String queryForQueryTable = "insert into query (queryId,queryName,queryTime) values ('" + queryId + "','" + query.getQueryname() + "','" + now() + "')";
            stmt.executeUpdate(queryForQueryTable);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        query.setQuerytime(this.now());
        return query;
    }

    /*
     * gets the time that will be used for Query TimeStamp
     * @returns current time
     * */
    public String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    /*
     * @param user query
     * obtains the clusterresults from the database
     * @return clusterresults
     * */
    public CObject getClusters(Query query) {
        String queryName = query.getQueryname();
        Long queryId = query.getQueryid();
        Vector<Document> docarray = new Vector();

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = (Connection) DriverManager.getConnection(HOSTURL, USERNAME, PASSWORD);
            stmt = (Statement) conn.createStatement();
            Statement stmt1 = (Statement) conn.createStatement();
            String sqlQuery = "SELECT cluster.clusterID,cluster.clusterName FROM cluster,query WHERE query.queryid=cluster.queryid and cluster.queryid=" + queryId + ";";
            rs = (ResultSet) stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                int cid = rs.getInt("clusterID");
                String cName = rs.getString("clusterName");
                Cluster cluster = new Cluster();
                cluster.setClusterid(cid);
                cluster.setClustername(cName);
                String getdocuments = "SELECT result.* FROM result,cluster WHERE result.clusterId=cluster.clusterID and cluster.clusterID=" + cid + " and result.queryId=" + queryId + " and result.queryId=cluster.queryID;";
                rs1 = (ResultSet) stmt1.executeQuery(getdocuments);
                while (rs1.next()) {
                    String title = rs1.getString("title");
                    String link = rs1.getString("link");
                    String snippet = rs1.getString("snippet");
                    Document doc = new Document(link, title, snippet);
                    cluster.addDocuments(doc);
                }

                clusterArray.add(cluster);
            }

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        CObject cobject = new CObject();
        cobject.setCluster(clusterArray);
        return cobject;
    }

    /*
     * @param queryobj Query Object
     * @return queryId respective queryId for the query Object
     * */
    public long getQueryID(Query queryObj) {
        long queryId = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = (Connection) DriverManager.getConnection(HOSTURL, USERNAME, PASSWORD);
            stmt = (Statement) conn.createStatement();
            String sqlQuery = "SELECT queryId FROM query WHERE queryName='" + queryObj.getQueryname() + "'";
            rs = (ResultSet) stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                queryId = rs.getLong("queryId");
            }
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return queryId;
    }

    /*
     * @param clusterresults
     * insert the clusterresult into the database
     * */
    public void insertClusterResults(CObject cobject) {
        Vector clusterList = cobject.getCluster();
        Vector documentList = cobject.getDocument();
        long qID = cobject.getQuery().getQueryid();
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = (Connection) DriverManager.getConnection(HOSTURL, USERNAME, PASSWORD);
            stmt = (Statement) conn.createStatement();

            for (Cluster clus : cobject.getCluster()) {
                String insertinclustertable = "INSERT INTO cluster (clusterID,clusterName,queryId) values ('" + clus.getClusterid() + "','" + clus.getClustername() + "','" + qID + "')";
                stmt.executeUpdate(insertinclustertable);
                for (Document doc : clus.getDocuments()) {
                    String queryToAssignCluster = "UPDATE result set clusterid='" + clus.getClusterid() + "' where queryid='" + qID + "' and resultid='" + doc.getDocumentid() + "'";
                    stmt.executeUpdate(queryToAssignCluster);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
