/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.d2.cse.core.Lingo;

import Jama.Matrix;
import java.util.*;


/**
 *
 * @author sankalp
 */
public class CosineSimilarity extends AbstractSimilarity {

    

    @Override
    protected double computeSimilarity(Matrix sourceDoc, Matrix targetDoc) {
        double dotProduct = sourceDoc.arrayTimes(targetDoc).norm1();
        double eucledianDist = sourceDoc.normF() * targetDoc.normF();
        return dotProduct / eucledianDist;
    }

   
   
}
