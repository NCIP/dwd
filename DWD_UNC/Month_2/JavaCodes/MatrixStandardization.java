/************************************************************* 
 *	DWD (Distance Weighted Discrimination)
 *	
 *	This software is developed by LCCC and Statistics at UNC
 *	It is supported by caBIG from NCI and is Open source 
 *
 *************************************************************/
 
package edu.unc.LCCC.caBIG.DWD.javaCode.standardization;

/************************************************************* 
 * MatrxStandardization.java
 *
 *	Compute the mean and std error for each column
 *	Standardize each column by (cij-mean)/std error 
 *
 *	Version: v1.0
 *	Author: Everett Zhou
 *	Date: 12/21/2004
 *
 *************************************************************/

public class MatrixStandardization {
	
	   /* compute the mean and std error for each column
	    * The first column (gene list) is used as the key
	    * The second column (gene name is skipped)
	    * The rest of columns are the value for each sample 
	    * @param arr[][]: the array
	    * @return: an array: the first row being means for each column
	    * 					 the second row being std error
	    */	      
	public static double [][] computeMeanStdError (double [][]arr) {
		int row = arr.length;
		int col = arr[0].length;
		double outputArr [][] = new double [2][col];
		int newColCount =0;
		
		for (int i=0; i<col;i++){
			double colSum = 0.0;
			double colSumSq = 0.0;
			double mean = 0.0;
			double stdError = 0.0;
			
			for (int j=0; j<row; j++) {
				colSum = arr[j][i]+colSum;
				colSumSq = arr[j][i]*arr[j][i]+colSumSq;
			}
			mean = colSum/row;
			stdError = Math.sqrt((colSumSq -(colSum*colSum)/row)/(row-1));
			outputArr [0][newColCount]=(Math.ceil(mean*1000))/1000.0;
			outputArr [1][newColCount]=(Math.ceil(stdError*1000))/1000.0;
			newColCount++;
		}
		return outputArr;				
	} /* end of computeMeanStdError */
	
	
	/*standardize each column by (cij-mean)/std error
	 * 
    * @param a1[][]: the original array
    * @param a2[] : the array returned from computeMeanStdError
    * @return: an array after standardization
    * 
    */	 
	public static double [][] standardization (double [][]a1, double [][]a2) {
		int row = a1.length;
		int col = a1[0].length;
		double outputArr [][] = new double [row][col];
		double temp;
		
		for (int i=0; i<row;i++){
			for (int j=0; j<col; j++) {
				temp = (a1[i][j]-a2[0][j])/a2[1][j];
				outputArr [i][j] = (Math.ceil(temp*1000))/1000.0;
			}
		}
		return outputArr;		
	} /* end of standardization */
}

