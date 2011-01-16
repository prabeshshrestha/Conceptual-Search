/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.d2.cse.core.Lingo;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

import java.util.*;
import org.d2.cse.dto.CObject;
import org.d2.cse.dto.Document;

/**
 *
 * @author sankalp
 */
/**
 * Uses Latent Semantic Indexing to find word associations between docs. 
 * Idea is to find the intersections of the words found in each document
 * and score them accordingly. We use SVD to accomplish this. We first
 * decompose the word frequency vector into the three parts, then multiply
 * the three components back to get our transformed matrix.
 */
public class SingularValueDecamposition {

    public Matrix transform(Matrix mat) {

        SingularValueDecomposition svd = new SingularValueDecomposition(mat);

        Matrix wordVector = svd.getU();
        Matrix sigma = svd.getS();
        Matrix documentVector = svd.getV();

        // compute the value of k (ie where to truncate)
        int k = (int) Math.floor(Math.sqrt(mat.getColumnDimension()));
        Matrix reducedWordVector = wordVector.getMatrix(
                0, wordVector.getRowDimension() - 1, 0, k - 1);
        Matrix reducedSigma = sigma.getMatrix(0, k - 1, 0, k - 1);
        Matrix reducedDocumentVector = documentVector.getMatrix(
                0, documentVector.getRowDimension() - 1, 0, k - 1);
        Matrix weights = reducedWordVector.times(
                reducedSigma).times(reducedDocumentVector.transpose());

        for(int r=0; r< weights.getRowDimension();r++){
            for(int c=0;c<weights.getColumnDimension();c++)
            {
                if(Double.isNaN(weights.get(r,c))){
                    System.out.print(weights.get(r,c));
                    weights.set(r, c, 0);
                    System.out.println("==>"+weights.get(r,c));
                }
                else{System.out.println(weights.get(r,c));}
            }
        }
        // Phase 2: normalize the word scrores for a single document
        for (int j = 0; j < weights.getColumnDimension(); j++) {
            double sum = sum(weights.getMatrix(
                    0, weights.getRowDimension() - 1, j, j));
            for (int i = 0; i < weights.getRowDimension(); i++) {
                
                weights.set(i, j, Math.abs((weights.get(i, j)) / sum));
            }
        }
        
            for(int r=0; r< weights.getRowDimension();r++){
            for(int c=0;c<weights.getColumnDimension();c++)
            {
                if(Double.isNaN(weights.get(r,c)))
                    weights.set(r, c, 0);
            }
        }

        System.out.println("Singular value decamposition started");
        double[][] d = weights.getArray();
        int k1 = 0, l = 0;
        System.out.println("");
        for (double[] d1 : d) {
            System.out.print("\t");
            for (double d2 : d1) {
                System.out.print("|" + d2 + "|");
                l++;
            }
            System.out.println("\t");
            k1++;
        }
          System.out.println("Singular value decamposition completed");
        return weights;

    }

    public double sum(Matrix colMatrix) {
        double sum = 0.0D;
        for (int i = 0; i < colMatrix.getRowDimension(); i++) {
            sum += colMatrix.get(i, 0);
        }
        //if(sum==0) sum=;
        return sum;
    }

   
}
 
