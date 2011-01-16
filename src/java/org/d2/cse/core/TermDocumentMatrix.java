/*
To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.d2.cse.core;

import java.io.FileNotFoundException;
import java.util.*;
import Jama.Matrix;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import org.d2.cse.dto.CObject;

import org.d2.cse.dto.Document;

/*
 * @author sankalp
 */
public class TermDocumentMatrix {

    //Should be deleted soon
    private Map<String, Integer> termfreqMap = new HashMap<String, Integer>();
    private Map<String, Integer> TDM = new HashMap<String, Integer>();
    private Map<String, Integer> documentfreqMap = new HashMap<String, Integer>();
    private int documentFrequency = 0;
    public LinkedHashSet<Document> DocOrder = null;

    public Matrix TermFrequency(CObject cobj) throws FileNotFoundException {
        Matrix mat = null;
        String temp = null, docTitle = null;
        HashMap<String, Integer> DM = null;

        int freq = 0;
        for (Document doc : cobj.getDocument()) {
            DM = new HashMap<String, Integer>();
            docTitle = new String(doc.getDocumenttitle());
            StringTokenizer st = new StringTokenizer(docTitle);
            while (st.hasMoreTokens()) {
                temp = st.nextToken().toUpperCase();
                if (!DM.containsKey(temp)) {
                    DM.put(temp, 0);
                }
                freq = DM.get(temp) + 1;
                DM.remove(temp);
                DM.put(temp, freq);

                //TDM
                if (!TDM.containsKey(temp)) {
                    TDM.put(temp, 0);
                }
                freq = TDM.get(temp) + 1;
                TDM.remove(temp);
                TDM.put(temp, freq);
            //freqMap;
            }
            doc.setTermfreqMap(DM);
        }
        mat = this.createTermDocumentMatrix(cobj);
        return mat;
    }

    public Matrix createTermDocumentMatrix(CObject cobj) throws FileNotFoundException {
        FileOutputStream out; // declare a file output object
        PrintStream p; // declare a print stream object

        System.out.println("Terms; " + TDM.keySet().size() + "Docs: " + cobj.getDocument().size());

        double[][] arr = new double[this.TDM.keySet().size()][cobj.getDocument().size()];
        DocOrder = new LinkedHashSet<Document>();
        for (Document d : cobj.getDocument()) {
            DocOrder.add(d);
        }

        Document[] docArr = DocOrder.toArray(new Document[DocOrder.size()]);
        String[] termArr = TDM.keySet().toArray(new String[TDM.keySet().size()]);

        for (int j = 0; j < DocOrder.size(); j++) {      //Column
            Map<String, Integer> DM = docArr[j].getTermfreqMap();
            for (int i = 0; i < TDM.size(); i++) {    //Row
                if (!DM.containsKey(termArr[i])) {
                    arr[i][j] = 0;
                } else {
                    arr[i][j] = DM.get(termArr[i]);
                }
            }
        }
        Matrix mat = new Matrix(arr);
        //To be deleted later 

        //  double[][] d=arr;
        double[][] d = mat.getArray();
        int k1 = 0, l = 0;
        System.out.println("");
        // Create a new file output stream
        // connected to "myfile.txt"
        out = new FileOutputStream("test.csv");
        // Connect print stream to the output stream
        p = new PrintStream(out);
        //queryid=cobj.getQuery().getQueryid();
        int somenumber = 1;
        String sqlQuery = "create table " + "ora_cluster_" + somenumber + "(" + "query_id Number," + "doc_id Number,";
        String sql_insert_query = "insert into " + "ora_cluster_" + somenumber + "("+"doc_id"+",";
        for (double[] d1 : d) {
            p.print(termArr[k1] + ",");
            /*this outputs the term names */
            sqlQuery += termArr[k1] + " Number ,";
            sql_insert_query += termArr[k1] + ",";
            System.out.print(termArr[k1] + "\t");
            k1++;
        }
        int count = sqlQuery.lastIndexOf(",");
        sql_insert_query = sql_insert_query.substring(0, sql_insert_query.lastIndexOf(","));
        sql_insert_query += ") values (";
        System.out.println(sql_insert_query);
        String sql_create_table = sqlQuery.substring(0, count - 1);
        sql_create_table += ")";
        /*database connection*/
        try {
            Connection con = null;
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = (Connection) DriverManager.getConnection("jdbc:oracle:thin:@dhaubaji:1521:search", "system", "password");
            Statement s = (Statement) con.createStatement();
            s.execute(sql_create_table);
            s.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*end of table creation database connection*/
        System.out.println(sql_create_table);
        System.out.println("");
        p.println();
        System.out.println("ROW:" + mat.getRowDimension());
        System.out.println("Column" + mat.getColumnDimension());
        String temp_sql_insert_query;
        for (int row = 0; row < mat.getColumnDimension(); row++) {
            temp_sql_insert_query = sql_insert_query;
            temp_sql_insert_query+=(row+1)+",";
            for (int column = 0; column < mat.getRowDimension(); column++) {
                p.print(mat.get(column, row) + ",");
                /*this outputs the term frequencies */
                temp_sql_insert_query += mat.get(column, row) + ",";
                System.out.print(mat.get(column, row) + "\t");
            }
            temp_sql_insert_query = temp_sql_insert_query.substring(0, temp_sql_insert_query.lastIndexOf(","));
            temp_sql_insert_query += ")";
            System.out.println("");
            System.out.println("insert query:" + temp_sql_insert_query);
            try {
                Connection con = null;
                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = (Connection) DriverManager.getConnection("jdbc:oracle:thin:@dhaubaji:1521:search", "system", "password");
                Statement st = (Statement) con.createStatement();
                st.execute(temp_sql_insert_query);
                st.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("");
            p.println();
        }
        /*
        System.out.println("here");
        System.out.println(""+mat.getColumnDimension());
        System.out.println(mat.getRowDimension());
        System.out.println("there");
        k1=0;
        for (double[] d1 : d) {
        System.out.print(termArr[k1] + "\t");
        for (double d2 : d1) {
        System.out.print("|" + d2 + "|");
        l++;
        }
        System.out.println("=" + TDM.get(termArr[k1]));
        k1++;
        }*/

        p.close();
        return mat;
    }
}

