/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.d2.cse.core.Lingo;

import Jama.Matrix;



/**
 *
 * @author sankalp
 */
public abstract class AbstractSimilarity {

    public Matrix transform(Matrix termDocumentMatrix) {
        int numDocs = termDocumentMatrix.getColumnDimension();
        Matrix similarityMatrix = new Matrix(numDocs, numDocs);
        for (int i = 0; i < numDocs; i++) {
            Matrix sourceDocMatrix = termDocumentMatrix.getMatrix(
                    0, termDocumentMatrix.getRowDimension() - 1, i, i);
            for (int j = 0; j < numDocs; j++) {
                Matrix targetDocMatrix = termDocumentMatrix.getMatrix(
                        0, termDocumentMatrix.getRowDimension() - 1, j, j);
                similarityMatrix.set(i, j,
                        computeSimilarity(sourceDocMatrix, targetDocMatrix));
            }
        }
        
          for(int r=0; r< similarityMatrix.getRowDimension();r++){
            for(int c=0;c<similarityMatrix.getColumnDimension();c++)
            {
                if(Double.isNaN(similarityMatrix.get(r,c))){
                    System.out.print(similarityMatrix.get(r,c));
                    similarityMatrix.set(r, c, 0);
                    System.out.println("==>"+similarityMatrix.get(r,c));
                }
                else{System.out.println(similarityMatrix.get(r,c));}
            }
        }
        
                
          for(int r=0; r< similarityMatrix.getRowDimension();r++){
            for(int c=0;c<similarityMatrix.getColumnDimension();c++)
            {
               if(r==c)
                   if(similarityMatrix.get(r, c)==0)
                       similarityMatrix.set(r, c, 1);
            }
        }
        
        double[][] d = similarityMatrix.getArray();
        int k1 = 0, l = 0;
        System.out.println("Cosine similarity started");
        for (double[] d1 : d) {
            System.out.print("\t");
            for (double d2 : d1) {
                System.out.print("|" + d2 + "|");
                l++;
            }
            System.out.println("\t");
            k1++;
        }
        System.out.println("Cosine similarity completed");
        return similarityMatrix;
    }

    protected abstract double computeSimilarity(Matrix sourceDoc, Matrix targetDoc);

   
}

