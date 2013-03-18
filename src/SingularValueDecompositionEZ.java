/*L
 * Copyright LCCC at University of North Carolina
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/dwd/LICENSE.txt for details.
 */

/************************************************************* 
 *	DWD (Distance Weighted Discrimination)
 *	
 *	This software is developed by LCCC and Statistics at UNC
 *	It is supported by caBIG from NCI and is Open source 
 *
 *************************************************************/

package edu.unc.LCCC.caBIG.DWD.javaCode.SVD;
import edu.unc.LCCC.caBIG.DWD.javaCode.processfile.ReadTextFile;

/*************************************************************
*	SingularValueDecompositionEZ.java
*	
*	It is the supplementary class for SingularValueDecomposition.java
*
* 	SingularValueDecomposition.java computes left singular vectors and 
* 	right singular vectors on the original matrix.  But for DWD, 
*	Steve Marron made some modifications first on the matrix, then submit it 
*	for SingularValueDecomposition
*
*	Version: v1.1
*	Author: Everett Zhou
*	Date: 11/19/2004
*
*************************************************************/


public class SingularValueDecompositionEZ {
	// Defualt vale for the number of SVD vectors
	private static int k=4; 

  	/* To read a textfile into an array by skipping the first m rows 
  	 * 	and n columns in each row
  	 * 
  	 * @param textFile: a tab delimited text file
  	 * @param m: the first m rows of text file
  	 * @param n: the first n columns in each row of text file
     * return: an array read from text file
  	 */

	public double [][] getData (String textFile, int m,int n){
  	   //System.out.println ("Reading the file");
  	   
  	   double [][] A = ReadTextFile.readDWDInputOutputToArrayArray(textFile,m,n);
  	   
  	   return A;		
	}	

	
	 /* To compute the mean vector of each row 
     * @param a: two dimension array
     * return: nx1 mean vector for each row 
     */
  	public static double [][] matrixRowMean (double [][] a) {
  		double [][] vMean = new double [a.length][1];
  		for (int i=0; i<a.length; i++) {
  			
  			double sum=0;
  			int j;  			
  			for (j=0; j<a[0].length; j++) {
  				sum += a[i][j];
  			}
  			vMean[i][0] = sum/j;
  		} 
		//printArray ("vector mean", vMean )
  		return vMean;
  	}	
  
 
  	/* To creat a row vector with 1s by 1xn 
  	 * @param n: the dimension of array
     * return: 1xn array with all elements as 1
  	 */
  	public static int [][] onesMatrix (int n){
  		int [][] ones = new int [1][n];
  		
  		for (int i=0; i<n; i++) {
  						  			
  				ones[0][i]=1;  			
  		}   
  		return ones;	
  		
  	}
  	
  	
  	/* Multiply the mean vector by ones row vector 
  	 * @param vMean: nx1 mean vector
  	 * @param ones: 1xn
     * return: nxn array with each row being the same element
  	 */
  	public static double [][] multiMatrix (double [][] vMean, int [][] ones){
  		int m =vMean.length;
  		int n = ones[0].length;
  		
  		if (vMean[0].length != ones.length) {
         	throw new IllegalArgumentException("Matrix inner dimensions must agree.");
      	}
      	
  		double [][] result = new double [m][n];
  		
  		for (int i=0; i<m; i++) {
  			 			
  			for (int j=0; j<n; j++) {
  				result[i][j]=vMean[i][0];
  			}
  		} 
  		return result;  		 		
  	}
  	

  	/* Substract b from a 
  	 * @param a: the first matrix
  	 * @param b: the second matrix
     * return: a[][]-b[][]
  	 */
  	public static double [][] subMatrix (double [][] a, double [][] b) {
  		if (a.length != b.length && a[0].length != b[0].length ) {
         	throw new IllegalArgumentException("Matrix inner dimensions must agree.");
      	}        	
   		int m =a.length;
  		int n = a[0].length;
  		     	
  		double [][] result = new double [m][n];
  		
  		for (int i=0; i<m; i++) {
  			 			
  			for (int j=0; j<n; j++) {
  				result[i][j]=a[i][j] - b[i][j];
  			}
  		} 
  		return result;       	      			 		
  	}

  	/* To center the matrix 
  	 * 
  	 * @param data: a two dimension array
     * return: a centered array
  	 */
	public double [][] getCenteredData (double [][] data){
  	   double [][] vMean = matrixRowMean(data);
  	   int ones [][]=onesMatrix(data[0].length);
  	   
  	   double [][] multiplied= multiMatrix (vMean, ones);
  	   
  	   double [][] center = subMatrix (data, multiplied);		
	   
	   return center;	
	}
	
 
	
  	/* To reduce the Eignvector to numPC columns 
  	 * 
  	 * @param center: a centered array
  	 * @param numPC: number of PCA needed to keep
     * return: reduced columns of Eignvector
  	 */
	public double [][] getReduecedEig (double [][] center, int numPC){
  		Matrix mt = new Matrix(center);
 
  	   SingularValueDecomposition svd = new SingularValueDecomposition (mt);
	   
  	   /* To get left singular vector*/
  	   Matrix LVec = svd.getU ();
  	   double [][] LArray = LVec.getArray();
  	   //printArray ("Original SVD", LArray);
  	   
  	   double [][] SlicedLVec = sliceSVD (LArray, numPC);
  	   //printArray ("Unadjusted eigenvator", SlicedLVec);
  	   
  	   return SlicedLVec;		
	}

  	/* To retrieve the first n columns of SVD vector, default is 4 
  	 * @param svd: the vector
  	 * @param n: the number of colummns needed
     * return: svd[?][n]
  	 */
  	public static double [][] sliceSVD (double [][] svd, int n){
  		double newSVD [][];
  		if (svd[0].length <=k) {
  			return svd;
  		}
  		else if (n>k) {
  			newSVD = new double[svd.length][n] ;
  			for (int i=0; i<svd.length; i++){
  				for (int j=0; j<n;j++){
  					newSVD[i][j] = svd[i][j];
  				}
  			}
  			return newSVD;
  			
  		}
  		else {
  			newSVD = new double[svd.length][k] ;
  			for (int i=0; i<svd.length; i++){
  				for (int j=0; j<k;j++){
  					newSVD[i][j] = svd[i][j];
  				}
  			}
  			return newSVD;  			
  		}
  	}
  	
 
 
  	/* 
  	 * To get the projection of a centered matrix into a vector is just to
  	 * multiply the transposed vector by the centered matrix
  	 * A centered matrix is the matrix with substraction of row mean
  	 * 
  	 * @param slicedSVD: the vector
  	 * @param center: center matrix
     * return: projection of center matrix on sliced vector
  	 */
  	public static double [][] computeProjection (double [][] slicedSVD, double [][] center){
  		Matrix temp = new Matrix (slicedSVD);
  		Matrix TSSVD = temp.transpose();
  		
  		double [][] TSlicedSVD = TSSVD.getArray();
  		
  		if (TSlicedSVD[0].length != center.length ) {
  			throw new IllegalArgumentException("Matrix dimensions must agree.");
  		}
  		
  		double [][] proj = new double [TSlicedSVD.length][center[0].length];
  		 for (int i = 0; i <TSlicedSVD.length; i++) {
         	for (int j = 0; j <center[0].length; j++) { 
         		for (int k = 0; k <TSlicedSVD[0].length; k++){        	
            		proj[i][j] += TSlicedSVD[i][k] * center[k][j];
            	}	
         	}
         	
      	 }
  		 		
  		return proj; 		  			  		
  	} 	
  	
  	
  /* To find the correct projection matrix, the following adjustment is needed.
   * The following comment is from Steve Marron
   * 	"Right, the sign issue is that the +- direction of the eigenvector is
	arbitrary, and you never know how it will come out.  E.g. I have seen
	different executions of eigen-analysis, for slightly different data, give
	flips of this sign, using Matlab.

	This issue can be a pain for the types of visualization that I like to do.
	For this reason, my curent approach is the following: project the data
	onto the eigenvector, look at the projection which is largest in absolute
	value, then flip the sign as needed to make this positive.  This gives
	much more stable visualizations, so I routinely use that to break this
	type of tie".
	The way to achieve that is to find the row max (absolute(a[i][j]))
	if the original of that max value is negative, then flip the sign of that row
	
	*  @param proj: a projection matrix
    * return: an array with correct sign for each row
	*
   */	
  	public double [][] computeAdjustProj (double [][] proj) {
  	  	double [] maxVul = computeMaxValue (proj);
  	  	  		
  		double [][] newProj = new double [proj.length][proj[0].length]; 
  	 		
   		for (int i=0; i<proj.length; i++) {		
  			for (int j=0; j<proj[0].length; j++) {
  				if (maxVul[i] <0) {
  					newProj[i][j] = -1 *proj[i][j];	
  				}
  				else {
  					newProj[i][j] = proj[i][j];
  				}  				
  			}
  		}  		
  		
  		return newProj;
  			
  	}


  	
    /*	To adjust eigenvec based on the projection matrix
     *  @param proj: a projection matrix
     * 	@param eigenvec: eigenvector matrix
     *  return: an array with correct sign for each row
     * 
     */	
  	public static double [][] computeAdjustEiginvec (double [][] proj, double [][] eigenvec) {
  	  	double [] maxVul = computeMaxValue (proj);
  	  	 
  		if (maxVul.length != eigenvec[0].length ) {
  			throw new IllegalArgumentException("Matrix dimensions must agree.");
  		}
  
    	Matrix eig1 = new Matrix (eigenvec);
  		Matrix eig2 = eig1.transpose();
  		
  		double [][] eig3 = eig2.getArray();	
  			
  		double [][] eig4 = new double [eig3.length][eig3[0].length]; 
  		
   		for (int i=0; i<eig3.length; i++) {		
  			for (int j=0; j<eig3[0].length; j++) {
  				if (maxVul[i] <0) {
  					eig4[i][j] = -1 *eig3[i][j];	
  				}
  				else {
  					eig4[i][j] = eig3[i][j];
  				}  				
  			}
  		}  		
  		return eig4;	
  	}


  	/* To find the max value for each row
  	 * will be used for adjustProj (double [][] proj) and
  	 * adjustEigenvec (double [][] proj, double [][] eiginvec)
  	 * 
  	 * @param proj: a projection matrix
     * return: an array with each element being the max value from each row
  	 */
 	public static double [] computeMaxValue (double [][] proj) {
  	  	double [] maxVal = new double [proj.length]; 

  		for (int i=0; i<proj.length; i++) {
  			double max = Math.abs(proj[i][0]); 			
  			for (int j=0; j<proj[0].length; j++) {
  				if (max < Math.abs(proj[i][j])) {
  					max = Math.abs(proj[i][j]);
  					maxVal[i]= proj[i][j];
  				}
  			
  			}
  			//System.out.println (i+" ");	
  		} 
 				
 		return maxVal;
 	}
	


  	/* To get projection matrix
  	 * 
  	 * @param redEig: reduced Eignvector matrix
  	 * @param center: a centered array
     * return: projection matrix
  	 */ 
   	public double [][] getProj (double [][] redEig,double [][] center ){  		
  	   //projection of centered matrix onto vector
  	   double [][] proj = computeProjection (redEig, center);
  	   //printArray ("Unadjusted Projection",proj);
  	   return proj;			
  	}


  	// Print out a 1-D array
  	public void printArray (String s, double [] a) {
  	  	System.out.println (s);
  	   	for (int i=0; i<a.length; i++) {
  	   	
  	   		System.out.print (Math.round (a[i]*1000)/1000.0 +" ");  
  	   	}	
  	   System.out.println (); 
  		
  	}
  	
  	// Print out a 2-D array
  	public void printArray (String s, double [][] a) {
  	  	System.out.println (s);
  	   	for (int i=0; i<a.length; i++) {
  	   		for (int j=0; j<a[0].length; j++) {
  	   		
  	   			System.out.print (Math.round (a[i][j]*1000)/1000.0 +" ");  
  	   		}
  	   		System.out.println ();
  	   	}	
  	   System.out.println (); 
  		
  	}
  	
  /*	
  	public static void main(String[] args) {		
 		System.out.println ("In SVD");
  		SingularValueDecompositionEZ.getAdjustProjEig();
  		SingularValueDecompositionEZ.printArray("Adjusted Projection",adjustProj);
  		SingularValueDecompositionEZ.printArray("Adjusted Eig",redEig);
  }
  */
}  