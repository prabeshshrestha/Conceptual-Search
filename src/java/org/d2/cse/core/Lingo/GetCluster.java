/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.d2.cse.core.Lingo;

import Jama.Matrix;
import org.d2.cse.dto.CObject;

import org.d2.cse.dto.Document;
import java.util.*;
import java.util.Collections;
import org.d2.cse.dto.Cluster;

/**
 *
 * @author sankalp
 */
public class GetCluster {

    TermDocumentMatrix tdm = null;
    TermDocumentMatrix tdm1 = null;

    public void createCluster(CObject cobj) {
        tdm = new TermDocumentMatrix();
        Matrix mat = tdm.TermFrequency(cobj);
        Matrix svdMat = new SingularValueDecamposition().transform(mat);
        Matrix coSiMat = new CosineSimilarity().transform(svdMat);
        cobj.setCluster(this.generateClusters(coSiMat));
        
    }
    
    public void demo() {
        CObject cobj = new CObject();
        Vector<Document> vd = new Vector<Document>();
        Document d = new Document();
        d.setDocumenttitle("sansdf a gasdfdsfad bsdfoy But he sucks");
        d.setDocumentid(1);
        vd.add(d);
        d = new Document();
        d.setDocumenttitle("susdfraj is a badsfad dsfboy and ssdafo he is");
        d.setDocumentid(2);
        vd.add(d);
        d = new Document();
        d.setDocumenttitle("susdfbodh has gosdafod fasdfasdriends andasdf he is good too");
        d.setDocumentid(3);
        vd.add(d);
        d = new Document();
        d.setDocumenttitle("sankad friendsfds");
        d.setDocumentid(4);
        vd.add(d);
        d = new Document();
        d.setDocumenttitle("ssdfankd fowever ssdfasdfuraj godsfod");
        d.setDocumentid(4);
        vd.add(d);
        d = new Document();
        d.setDocumenttitle("sursdfadsfj is hesdfre with subsdfsfodh and sanksdfalp");
        d.setDocumentid(4);
        vd.add(d);

        cobj.setDocument(vd);

        tdm = new TermDocumentMatrix();
        
        Matrix mat = tdm.TermFrequency(cobj);
        Matrix svdMat = new SingularValueDecamposition().transform(mat);
        Matrix coSiMat = new CosineSimilarity().transform(svdMat);
        cobj.setCluster(this.generateClusters(coSiMat));

        
    }

    private Vector generateClusters(Matrix coSiMat) {
        double REF_VAL = 0.9;
        Set<Integer> done = new HashSet<Integer>();
        Document[] dArr = tdm.DocOrder.toArray(new Document[1]);
        Vector<Cluster> Clusters = new Vector<Cluster>();
        Cluster cluster = new Cluster();
        int i=0,next_i=0;
        System.out.println("##############################");
        
      /*  for(int r=0; r< coSiMat.getRowDimension();i++){
            for(int c=0;c<coSiMat.getColumnDimension();c++)
            {
                if(coSiMat.get(r,c)==Double.NaN)
                    coSiMat.set(r, c, 0);
            }
        }
*/        while (i < coSiMat.getRowDimension()) {
            for (int j = 0; j < coSiMat.getColumnDimension(); j++) {
                
                if (coSiMat.get(i, j) >= REF_VAL) {
                    System.out.print("| 1 |");
                    if(done.add(j))
                     cluster.addDocuments(dArr[j]);
                } else{
                    System.out.print("| 0 |");
                    if (next_i==i && j>i) next_i=j;
                }
            }
            cluster.setClusterid(i);
        //    cluster.setClustername("Cluster-"+i);
            i=next_i;
            System.out.println(" Generating Clusters ");
            if (cluster.getDocuments().size()!=0) Clusters.add(cluster);
            cluster = new Cluster();
            if (done.size()==coSiMat.getColumnDimension()) break;
        }
        System.out.println("****************************");
        System.out.println(Clusters.size());
        System.out.println("");
       
        
        ////////////////////////
        Iterator<Cluster> iter= Clusters.iterator();
        Cluster c;
        while(iter.hasNext()){
            c=iter.next();
            System.out.println("Cluster: "+c.getClusterid());
            String docTitle=null;
            Map<String, Integer> termfreqMap ;
            StringTokenizer st;
            Vector v=new Vector();
            for(Document d: c.getDocuments().toArray(new Document[1])){
            System.out.println("\t"+d.getDocumenttitle());
            docTitle=d.getDocumenttitle();
            String temp = null;
            termfreqMap = new HashMap<String, Integer>();
            st = new StringTokenizer(docTitle);
            int freq = 0;
            while (st.hasMoreTokens()) {
            temp = st.nextToken().toUpperCase();
            if (!termfreqMap.containsKey(temp)) {
                termfreqMap.put(temp, 0);
            }
            freq = termfreqMap.get(temp) + 1;
            termfreqMap.remove(temp);
            termfreqMap.put(temp, freq);
            System.out.println(termfreqMap.size());    
              
 
           
            }     
            
         ArrayList<Integer> values = new ArrayList<Integer>();
         values.addAll(termfreqMap.values());
         // and sorting it (in reverse order)
         Collections.sort(values, Collections.reverseOrder());
          int last_i = -1;
        // Now, for each value 
         for (Integer x : values) {
            if (last_i == i) // without dublicates 
                continue;
            last_i = i;
            // we print all hash keys 
            for (String s : termfreqMap.keySet()) {
                if (termfreqMap.get(s) == x) // which have this value 
                    System.out.println(s + ":" + x);
                c.setClustername(s);
            }
          
        }

 

         }
        }
        ///////////////////////
        
        return Clusters;
    }

}
