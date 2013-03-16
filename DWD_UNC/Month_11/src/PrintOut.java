/************************************************************* 
 *	DWD (Distance Weighted Discrimination)
 *	
 *	This software is developed by LCCC and Statistics at UNC
 *	It is supported by caBIG from NCI and is Open source 
 *
 *************************************************************/


package edu.unc.LCCC.caBIG.DWD.javaCode.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/************************************************************* 
*	PrintOut.java
*
*	1. Print out arrays, list etc.	
*
*	Version: v1.0
*	Author: Everett Zhou
*	Date: 11/7/2005
*
*************************************************************/

public class PrintOut {

	   /* To print out a one dimensional array
	    * @param name: one dimensional array
	    */
	   public static void print(String [] name) {
			String temp ="";
			if (name.length >25) {
	   			for (int i=0; i<25; i++){
					temp = (String) name[i];
					System.out.println (temp);
				}
			} else {
				int i;
	   			for (i=0; i<name.length-1; i++){
					temp = (String) name[i];
					System.out.print (temp+"\t");
				}
	   			System.out.print ((String) name[i]);
	   			System.out.println();
	   		}
	   }
	   
	   /* To print out a one dimensional array
	    * @param label: label
	    * @param s: one dimensional array
	    */
	   public  static void printStringArray (String label, String s []){
	  	   	System.out.println (label);
	  	   	
	  	   	for (int i=0; i<s.length; i++) {
	  	   		  	   		
	  	   			System.out.print (s[i] +" ");  
	  	   			  	   	
	  	   	} 
	  	   	System.out.println ();
		  }

	   /* To print out a one dimensional array
	    * @param label: label
	    * @param s: two dimensional array
	    */		       
	   public static void printStringArray (String label, String s [][]){
	  	   	System.out.println (label);
	  	   	for (int i=0; i<s.length; i++) {
	  	   		for (int j=0; j<s[0].length; j++) {
	  	   		
	  	   			System.out.print (s[i][j] +" ");  
	  	   		}
	  	   		System.out.println ();
	  	   	} 
	  	   	System.out.println ();
	    }
	  

	   /* To print out a one dimensional array
	    * @param label: label
	    * @param list: array list
	    */	   
	   public static void printList(String label, List list) {
	   		System.out.println (label);
	   		String temp ="";
				for (int i=0; i<list.size(); i++){
					temp = (String) list.get(i);
					System.out.println (temp);
				}
	   }

	   /* To save a two dimensional array into a text file
	    * @param a: array
	    * @param outputFileName: output file
	    */	
	   public static void saveArrayAsFile (double a[][], String outputFileName ){
	 	   PrintWriter writer=null;
		try {
			writer = new PrintWriter(new FileWriter (outputFileName, false));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			/* Print out the title: numValues1 for 1s and numValues2 for 2s */
			for (int i=0; i<a.length;i++) {
				for (int j=0; j<a[0].length;j++){
					writer.print (a[i][j] +"\t");
				}
				writer.println ();
			}
			writer.close();
	   	
	   }
	   
}