/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.

package org.d2.cse.core.Lingo;

import java.util.StringTokenizer;
import java.util.Vector;
import org.d2.cse.dto.Document;
import org.d2.cse.dto.SearchResults;
import java.net.URL;
import java.util.Arrays;
import java.util.List;



import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.POS;

/**
 *
 * @author sankalp

/**
 * A recognizer that recognizes common stop words. Special stopwords may

public class Stemming {

    private IDictionary dictionary;
    private List<POS> allowablePartsOfSpeech = Arrays.asList(new POS[]{
        POS.NOUN, POS.VERB, POS.ADJECTIVE, POS.ADVERB
    });

    public Stemming() {

    }

    public void init() throws Exception {
        this.dictionary = new Dictionary(
                new URL("file", null, "/opt/wordnet-3.0/dict"));
        dictionary.open();
    }

    public void doStemming(SearchResults srcRes) {
        Vector<Document> doc = srcRes.getDocuments();
        for (Document d : doc) {
            d.setTitle(this.stemming(d.getTitle()));
        }
    }

    public String stemming(String srcRes) {
        String retStr = new String();
        String temp = null;

        StringTokenizer st = new StringTokenizer(srcRes);
        while (st.hasMoreTokens()) {
            temp = st.nextToken();

            for (POS allowablePartOfSpeech : allowablePartsOfSpeech) {
                IIndexWord indexWord = dictionary.getIndexWord(temp, allowablePartOfSpeech);

                if (indexWord != null) {
                    retStr = retStr.concat(" " + indexWord);
                    break;
                }

            }


        }
        return retStr.trim();
    }

    public static void main(String[] args) {
        System.out.println((new Stemming()).stemming("successfully"));
    }
}
 */