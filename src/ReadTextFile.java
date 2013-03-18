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
 
package edu.unc.LCCC.caBIG.DWD.javaCode.processfile;

import java.io.*;
import java.util.*;

/************************************************************* 
 *	ReadTextFile.java
 *
 *	Will read the text file into array
 *	Version: v1.0
 *	Author: Everett Zhou
 *	Date: 9/29/2004
 *
 *************************************************************/

public class ReadTextFile {
	
	 private static StringTokenizer st;	 

	   /* 	Find the columns and rows of the text file  
	    *	
	    *  	@param textFile: the name of a file
	    *	@return: an array with two elements for the rows and columns of a text file
	    */
     public static int[] findFileDim (String textFile) {	          

   	     int rowCnt =0;
   	     int colCnt=1;
         String sRow, sCol, temp;
  		 int dim[] = new int[2];
  		 
         try {
  		
             FileReader fr = new FileReader (textFile);
             BufferedReader br = new BufferedReader (fr);
             
             while ((sRow=br.readLine()) != null) {
                rowCnt++;
             
              	st = new StringTokenizer (sRow,"\t");
                 
              /* each token from a line is an attribute of file */
              	while (st.hasMoreTokens()) {
                    temp = st.nextToken().trim();
                    colCnt++;
                 }
       		 }
             fr.close();
             colCnt = colCnt/rowCnt;
             
             //System.out.println ("In findFileDim");
             //System.out.println ("rowCnt= " + rowCnt);
             //System.out.println ("colCnt= " + colCnt);
          }
         
         catch (IOException ioe) {
             System.out.println ( "File not found or wrong file");
             System.out.println ( "IOException message = "+ ioe.getMessage() );
         }
         
         dim[0]=rowCnt;
         dim[1]=colCnt;
         return dim;
      }  /* End of findFileDim*/ 
 
     
	   /* 	Find how many 1 and 2 in the DWD_Input.txt 
	    *	
	    *  	@param textFile: the name of a file
	    *	@return: an array with two elements
	    */
     public static int[] findOnes (String textFile){
     	int ones[] = new int[2];
  	     int oneCnt =0;
   	     int twoCnt =0;
         String sRow, temp;
  
         try {  		
             FileReader fr = new FileReader (textFile);
             BufferedReader br = new BufferedReader (fr);
             
             if((sRow=br.readLine()) != null) {
               	st = new StringTokenizer (sRow,"\t");
                 
               	/* each token from a line is an attribute of file */ 
              	while (st.hasMoreTokens()) {
                    temp = st.nextToken().trim();
                    if (temp.equals ("1")){
                    	oneCnt++;                    	
                    }                   
                    else if (temp.equals ("2")){
                    	twoCnt++;                    	
                    }                   
                 }
       		 }
             fr.close();
             
             //System.out.println ("In findOnes");
             //System.out.println ("oneCnt= " + oneCnt);
             //System.out.println ("twoCnt= " + twoCnt);
          }
         
         catch (IOException ioe) {
             System.out.println ( "File not found or wrong file");
             System.out.println ( "IOException message = "+ ioe.getMessage() );
         }
         
         ones[0]=oneCnt;
         ones[1]=twoCnt;    	
     	return ones;
     	
     } /* End of findOnes*/ 

	   /* 	Read DWD input and output into array
	    * 	 and return them
	    *	
	    *  	@param textFile: the name of a file
	    * 	@param m: Skip the first m rows.
	    * 			 For DWD_Input.txt and DWD_Intermediate_Output.txt, m=1
	    * 			 For DWD_Std_Output.txtand DWD_Non_Std_Output.txt, m=2 
	    * 	@param n: Skip the first n columns for each row.
	    * 			For DWD_Input.txt and DWD_Intermediate_Output.txt, n=0
	    * 			   For DWD_Std_Output.txtand DWD_Non_Std_Output.txt, n=2
	    *	@return: an array with two dimensions
	    */
 
     public static double[][] readDWDInputOutputToArrayArray (String textFile,int m, int n) {
        String s,temp;
        int dim[] =new int[2];
        dim = findFileDim (textFile);
        int row = dim[0]-m; //Skip the first  n rows
        int col= dim[1]-n;
        
        double [][] arr = new double[row][col]; 

        try {

            FileReader fr = new FileReader (textFile);
            BufferedReader br2 = new BufferedReader (fr);
            
            int i=0; /*index for row*/
            int j=0; /*index for column*/
            
            /*for a new row, n columns are skipped */
            int colAt = n;
            
            /* Skip the first  n rows */
            while (m>0 ) {
                if ((s=br2.readLine()) != null){
                }  
                m--;
            }
 
            while ((s=br2.readLine()) != null) {
            	n=colAt;
             	st = new StringTokenizer (s,"\t");
                
             /* each token from a line is an attribute of array */
             	while (st.hasMoreTokens()) {
            		
                    while (n>0 ) {
                    	temp = st.nextToken().trim();
                        n--;
                    }
                   temp = st.nextToken().trim();
                   
                   if (i<row) {
                   
                   	if (j<col) {
                   		arr[i][j] = Double.valueOf(temp).doubleValue();
                   		//System.out.println ("i= "+i+" j= "+j +" " );
                   		j++;
           
           	        } else {
               	    	j=0;
                   		i++;
                   		//System.out.println ("i= "+i+" j= "+j +" " );
                   		arr[i][j] = Double.valueOf(temp).doubleValue();
                   		j++;
                  		 }
                  	}	  
                }               
      		 }
            fr.close();
         }        
        catch (IOException ioe) {
            System.out.println ( "File not found or wrong file");
            System.out.println ( "IOException message = "+ ioe.getMessage() );
        }
        return arr;
     }   /*End of readDWDInputOutputToArrayArray*/   

}