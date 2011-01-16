package org.d2.cse;

import Jama.Matrix;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.d2.cse.core.Clusterer;
import org.d2.cse.core.Fetcher;
import org.d2.cse.core.Odm.OdmAlgorithm;
import org.d2.cse.core.Parser;

import org.d2.cse.core.TermDocumentMatrix;
import org.d2.cse.dao.Database;
import org.d2.cse.dto.Query;
import org.d2.cse.dao.MySql;
import org.d2.cse.dao.Oracle;
import org.d2.cse.dto.CObject;
import org.d2.cse.dto.Document;

/**
 *This is the main Controller class which controls rest of the classes 
 * @author cSearch
 */
public class Controller {
    /*query forwarded by the user is set as the query of the controller class*/

    public String databaseUsed = "mysql";
    public Database databaseObj;
    public Fetcher fetcherObj = new Fetcher();
    public Clusterer clustererObj = new Clusterer();
    private CObject cobject = new CObject();
    private Query query = new Query();
    private Document document = new Document();
    public TermDocumentMatrix tdm = new TermDocumentMatrix();
    public OdmAlgorithm otdm=new OdmAlgorithm();

    /*
     * constructor
     * @param query
     * */
    public Controller(String query) throws FileNotFoundException {
        //initiates the database according to the database used
        if (this.databaseUsed.equalsIgnoreCase("mysql")) {
            this.databaseObj = new MySql();
        } else {
            this.databaseObj = new Oracle();
        }
        //sets the query name of the query object
        this.query.setQueryname(query);
        //calls the fetcher class to fetch the results from search engines for user query
        this.fetchResultsForQuery();
        //obtain cluster results from the database
        this.obtainClusterResults(this.query);
    }

    private Controller() {
        System.out.println("");
    }


    /*
     * check if query already in database
     * if exists shows the result else fetches the result from search engines
     * */
    public void fetchResultsForQuery() throws FileNotFoundException {


        MySql dat = new MySql();
       // Oracle dat = new Oracle();
        if (databaseObj.existQueryInDB(query)) {
            /* this block is run if the query exists in the database*/
            query.setQueryid(databaseObj.getQueryID(query));
        } else {
            /*this block of code is run if the query does not exist in the database*/
            try {
                /* fetch the result from standard Search Engines*/
                this.cobject = fetcherObj.getQueryResults(query);

                /* insert the results in the database*/
                this.cobject = dat.insertResults(this.cobject);
                /* cluster the results*/

                this.clusterResult();

            } catch (MalformedURLException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }

    /*
     * Sends the result(Searchresult) returned by the fetcher for clustering
     * real implementation of the project is done through this function
     * */
    public void clusterResult() throws FileNotFoundException {

        /* stopwords implemented here*/
        Parser parse = new Parser(cobject);
        cobject = parse.getCobj();
        /* stopword upto here*/

        /*term document matrix starts here*/
        this.cvsFileGeneration();
        otdm.TermFrequency(cobject);
        //Matrix mat = otdm.TermFrequency(cobject);
        /*term document matrix upto here*/
        /* StopWordsRemover swr=new StopWordsRemover();
        System.out.println("This Is a text"+cobject.getDocument().size());
        for(int i=0;i<cobject.getDocument().size();i++){
        String tempdoc=((Document)cobject.getDocument().get(i)).getDocumentsnippet();
        System.out.println(swr.removeStopWord(tempdoc));
        }*/
        /*upto here removes stop word*/

        //MODIFIED HERE
        /*
        clustererObj.setResult(this.cobject);
        this.cobject = clustererObj.implementAlgorithm(this.cobject);
        databaseObj.insertClusterResults(cobject);*/
        //MODIFIED HERE

    }

    /*
     * Obtains the clusterresults from the database
     * Returns clusterresult 
     * */
    public void obtainClusterResults(Query query) {
        this.cobject = this.databaseObj.getClusters(query);
    }


    /*
     * obtains the final clusterresult for display in the main.jsp page
     * */
    public CObject getFinalClusterForDisplay() {
        return this.cobject;
    }

    /*
     * @param clusterresult that is obtained from database
     * sets the clusterresult for display in main.jsp
     * @returns clusterresults
     * */
    public void setFinalClusterForDisplay(CObject cobject) {
        this.cobject = cobject;
    }

    /*
     * @param databaseUsed which could be Oracle or Mysql or other database
     * */
    public void setDatabaseUsed(String databaseUsed) {
        this.databaseUsed = databaseUsed;
    }

    /*
     * This is the testing ground whenever jsp Page shows errors that is confusing to handle
     * */
    public static void main(String arg[]) throws MalformedURLException, UnsupportedEncodingException, FileNotFoundException {
        /************* main program part here*************************/
        /* Sending the query through the constructor of controller */
        Controller control = new Controller("php");
        /* set Mysql or Oracle as the working database (for now only mysql)*/
        control.setDatabaseUsed("php");
    /* Final cluster to be displayed to the user*/
    //MODIFIED HERE
    //control.getFinalClusterForDisplay();
    //MODIFIED HERE
   /*  Controller c = new Controller();
    c.cvsFileGeneration();*/
//Matrix mat = otdm.TermFrequency(cobj);
    }

    private void cvsFileGeneration() throws FileNotFoundException {
        CObject cobj = new CObject();
        Document d1 = new Document();
        Document d2 = new Document();
        Document d3 = new Document();
        Document d4 = new Document();
         Document d5 = new Document();
        Document d6 = new Document();
        Document d7 = new Document();
        Document d8 = new Document();
        d1.setDocumenttitle("India friend country Nepal");
        d1.setDocumentid(1);
        d2.setDocumenttitle("Prabesh bad boy");
        d2.setDocumentid(2);
        d3.setDocumenttitle("Prabesh good");
        d3.setDocumentid(3);
        d4.setDocumenttitle("Prabesh bad");
        d4.setDocumentid(4);
        d5.setDocumenttitle("India beautiful country");
        d5.setDocumentid(5);
        d6.setDocumenttitle("Prabesh good boy");
        d6.setDocumentid(6);
        d7.setDocumenttitle("India large country");
        d7.setDocumentid(7);
        d8.setDocumenttitle("India country friends");
        d8.setDocumentid(8);

        Vector<Document> vd = new Vector<Document>();
        vd.add(d1);
        vd.add(d2);
        vd.add(d3);
        vd.add(d4);
        vd.add(d5);
        vd.add(d6);
        vd.add(d7);
        vd.add(d8);
        cobj.setDocument(vd);
        System.out.println("this is the START");
        Matrix mat = otdm.TermFrequency(cobj);
        System.out.println("this is the END");
    }
}
