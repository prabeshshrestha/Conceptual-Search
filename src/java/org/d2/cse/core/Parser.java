package org.d2.cse.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.d2.cse.core.Lingo.StopWordsRemover;
import org.d2.cse.dto.CObject;
import org.d2.cse.dto.Document;

/**
 *
 * @author cSearch
 */
public class Parser {
    CObject cobj = null;
    public CObject getCobj() {
        return cobj;
    }




    /*Clears any HTML tags that may be present in any String
     * */
    public static String ClearHTMLTags(String strHTML, int intWorkFlow) {
        Pattern pattern = null;
        Matcher matcher = null;
        String regex;
        String strTagLess = null;
        strTagLess = strHTML;

        if (intWorkFlow == -1) {
            regex = "<[^>]*>";
            //removes all html tags
            pattern = Pattern.compile(regex);
            strTagLess = pattern.matcher(strTagLess).replaceAll(" ");
        }

        if (intWorkFlow > 0 && intWorkFlow < 3) {

            regex = "[<]";
            //matches a single <
            pattern = Pattern.compile(regex);
            strTagLess = pattern.matcher(strTagLess).replaceAll("<");

            regex = "[>]";
            //matches a single >	
            pattern = Pattern.compile(regex);
            strTagLess = pattern.matcher(strTagLess).replaceAll(">");
        }
        return strTagLess;
    }

    /*stop word upto here*/
    public CObject clearStopWords(CObject cobject) {
        StopWordsRemover swr = new StopWordsRemover();
        for (int i = 0; i < cobject.getDocument().size(); i++) {
            String tempdoc = ((Document) cobject.getDocument().get(1)).getDocumentsnippet();
            System.out.println(swr.removeStopWord(tempdoc));
        }
        return cobject;
    }
    /*stopword upto here*/
    public void setCobj(CObject cobj) {
        this.cobj = cobj;
    }


    public Parser(CObject cobject) {
        this.cobj = this.clearStopWords(cobject);
    }
    /*This is the function for future use to remove any unwanted characters or anything
     * from the URL
     * 
     * */
    public void parseUrl(String url) {
    }
    /*
     * Example code to run Parser
    public static void main(String args[]) {
    String strHTML = "<table><td>surveyco<de</td>asdfasdfasdf</table>< a href =\"\"> http://yahhoo.com</a> ";
    int intWorkFlow = -1;
    Parser p = new Parser();
    String prt;
    prt = Parser.ClearHTMLTags(strHTML, intWorkFlow);
    System.out.println(prt);
    }*/
}
