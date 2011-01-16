/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
*/
package org.d2.cse.core;

import java.util.StringTokenizer;
import java.util.Vector;
import org.d2.cse.dto.Document;
import org.d2.cse.dto.CObject;


/*
 * @author sankalp
*/

 
public class StopWordsRemover {

    private String stopwords =
            "a about above across add ago after afterwards again against all almost alone along already also although always am among amongst amoungst amount an and another any anyhow anyone anything anyway anywhere around are as at back be " +
            "became become becomes becoming because been before beforehand behind being below beside besides beyond bill between big both but bottom but by call came can cannot cant co con come " +
            "could couldnt cry de describe detail done down during did do does due each eg eight either eleven else elsewhere empty enough etc even ever every everyone everything everywhere except end far few fifteen fify fill find fire first five for former formerly forty found four from front full further get give go got had " +
            "had has hasnt have he hence her here hereafter hereby herein hereupon hers herself him himself his how however i ie if in inc indeed interest into is it its itself" +
            "just last latter latterly least ltd let lie like low may me many make many me meanwhile might mill mine more moreover most mostly move much must " +
            "my myself name namely neither never nevertheless next nine no nobody none noone nor not nothing now nowhere of off often old on once one only onto or other others otherwise our ours ourselves out over own" +
            "part perhaps please put per pre put rather re said same see seem seemed seeming seems serious several she should show side since since sincere six sixty so some somehow someone something sometime sometimes somewhere still such system" +
            "take ten than that the their them themselves then thence there these thereafter thereby therefore therein thereupon they this thick thin third this those " +
            "through three through throughout thru thus to together too top toward towards twelve twenty two un under unless untill up upon use very via want was way we well were " +
            "what whatever when whence whenever where whereafter whereas whereby wherein whereupon wherever whether which while whither who whoever whole whom whose why will with within without would yes yet you your yourself yourselves , .  &AMP; ... - :: &GT; WELCOMEÂ€” | 6.5 5.2";
    private Vector<String> stopwordsvector = null;

    public StopWordsRemover() {
        StringTokenizer st = new StringTokenizer(stopwords, " ");
        this.stopwordsvector = new Vector<String>();
        while (st.hasMoreTokens()) {
            this.stopwordsvector.add(st.nextToken());
        }
    }

    public Vector<String> getVector() {
        return stopwordsvector;
    }

    public void removeStopWord(CObject srcRes) {
        Vector<Document> doc = srcRes.getDocument();
        for (Document d : doc) {            
            d.setDocumenttitle(this.removeStopWord(d.getDocumenttitle()));
        }
    }

    public String removeStopWord(String srcRes) {
        String retStr=new String();
        String temp=null;
        
        StringTokenizer st = new StringTokenizer(srcRes);
        while (st.hasMoreTokens()) {
            temp = st.nextToken();
            //For each word, if the word in not a stop word add it to return str
             if (!this.stopwordsvector.contains(temp)) {
                retStr = retStr.concat(" " + temp);
                   }
        }
        return retStr.trim();
    }
     public static void main(String[] args) {
        System.out.println((new StopWordsRemover()).removeStopWord("Sankalp is a good boy"));
    }

}
