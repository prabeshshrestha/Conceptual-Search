/*
To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.d2.cse.core.Lingo;

import java.util.*;
import Jama.Matrix;

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

    public Matrix TermFrequency(CObject cobj) {
        Matrix mat=null;
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
        mat=this.createTermDocumentMatrix(cobj);
        return mat;
    }

    
    public Matrix createTermDocumentMatrix(CObject cobj) {
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
        System.out.println("Term document Matrix");
        for (double[] d1 : d) {
            System.out.print(termArr[k1] + "\t");
            for (double d2 : d1) {
                System.out.print("|" + d2 + "|");
                l++;
            }
            System.out.println("=" + TDM.get(termArr[k1]));
            k1++;
        }
        return mat;
    }

   
}
