/************************************************************* 
 *	DWD (Distance Weighted Discrimination)
 *	
 *	This software is developed by LCCC and Statistics at UNC
 *	It is supported by caBIG from NCI and is Open source 
 *
 *************************************************************/

package edu.unc.LCCC.caBIG.DWD.javaCode.util;


import java.io.*;
import java.util.*;

/************************************************************* 
*	QuickSortBinarySearch.java
*
*	1. Quick Sort a two dimesion array
*	2. Binary search 	
*
*	Version: v1.0
*	Author: Everett Zhou
*	Date: 11/3/2005
*
*************************************************************/

public class QuickSortBinarySearch {

	
   /* To sort a two dimensional array with quick sort algorithm
    * @param a: two dimensional array
    * @param low: starting point for sorting
    * @param high: end point for sorting
    */
	public void quicksort(String [][] a, int low, int high) {
		  // If there are fewer than two elements, do nothing.
		  if (low < high) {
		    int pivotIndex = (low + high)/2;
		    String pivot = a[pivotIndex][0];
		    
		    String pivotRow [] = getRow(a, pivotIndex);
		    
		    //a[pivotIndex] = a[high];                       
		    //a[high] = pivot;
		    
		    swap (a, pivotIndex, high );
		    
		    //printStringArray("After assign", a);
		    
		    int i = low - 1;
		    int j = high;
		    do {
			      do { i++; } while (a[i][0].compareTo(pivot) < 0);
			      
		/*	      System.out.println ("i " +i); 
			      System.out.println ("pivot " +pivot);
			      System.out.println ("a[i][0] " +a[i][0]);
			      System.out.println ("a[i][0].compareTo(pivot) "+a[i][0].compareTo(pivot));
			*/      
			      do { j--; } while ((pivot.compareTo(a[j][0]) < 0) && (j > low));
			      //System.out.println ("j " +j); 	      
			      if (i < j) {
			        swap(a, i, j);
			        //printStringArray("after swap", a);
			      }
		      
		    } while (i < j);
		    /*System.out.println (" Before Conqer");
		    System.out.println ("i " +i);
		    System.out.println ("j " +j);
		    */
		    //a[high] = a[i];
		    assignRow ( a, high, i);
		    //a[i] = pivot;  					// Put pivot in the middle where it belongs
		    assignRow (a, i, pivotRow);
		    
		    
		    quicksort(a, low, i - 1);          // Recursively sort left list
		    quicksort(a, i+1, high);         // Recursively sort right list
		  }
		}


	   /* To get a row from a two dimensional array
	    * @param a: two dimensional array
	    * @param rowIndex: the row index
	    * @return: a row from a two dimensional array with the row index of rowIndex
	    */
	 private String [] getRow (String [][] a, int rowIndex) {
	 	 int col = a[0].length;
	 	 String row [] = new String [col] ;
	 	 for (int j=0; j<col; j++){
	 	 	row[j] =a[rowIndex][j];
	 	 }
	 	 return row;
	 }

	   /* To assign a row from a two dimensional array to another row
	    * @param a: two dimensional array
	    * @param rowIndex1: the first row index
	    * @param rowIndex2: the second row index
	    */
	 private void assignRow (String [][] a, int rowIndex1, int rowIndex2) {
		 	 
	 	 for (int j=0; j<a[0].length; j++){
	 	 	a[rowIndex1][j] =a[rowIndex2][j];
	 	 }
	 	 
	 }
	 
	   /* To assign a one dimensional array to a two dimensional 
	    * 		array at the particular row index
	    * @param a: two dimensional array
	    * @param rowIndex: the first row index
	    * @param assigndArray: a one dimensional array to be assigned to another array
	    */
	 private void assignRow (String [][] a, int rowIndex, String assigndArray []) {
	 	 
		 for (int j=0; j<a[0].length; j++){
		 	a[rowIndex][j] =assigndArray[j];
		 }	 
	 }

	 
	   /* To swap a two dimensional at indeies i and j
	    * 		array at the particular row index
	    * @param a: two dimensional array
	    * @param i: the first row index
	    * @param j: the second row index
	    */
	  private void swap(String a[][], int i, int j) {
	      
	      for (int k=0; k<a[0].length; k++) {
	     
		      String tmp;
		      tmp = a[i][k]; 
		      a[i][k] = a[j][k];
		      a[j][k] = tmp;
	      }
	   }
	   
 
	   /* To search a sorted array
	    * @param sArray: two dimensional string array
	    * @param s: the string being searched
	    * @return: an integer
	    * 		if return number =-1, not found
	    * 		else found 
	    */
	   public int binarySearch(String [][] sArray, String s) {
              // Searches the array A for the integer N.
              // A is assumed to be sorted into increasing order.
              
            int lowestPossibleLoc = 0;
            int highestPossibleLoc = sArray.length - 1;
            String temp ="";
            
            while (highestPossibleLoc >= lowestPossibleLoc) {
               int middle = (lowestPossibleLoc + highestPossibleLoc) / 2;
	           temp = sArray[middle][0]; 
	             
               if (temp.compareTo(s) ==0) {
                  // s has been found at this index!
                  return middle;
               }
               else if (temp.compareTo(s)>0) {
                  // eliminate locations >= middle
                  highestPossibleLoc = middle - 1;
               }
               else {
                  // eliminate locations <= middle
                  lowestPossibleLoc = middle + 1;   
               }
            }
            
            // At this point, highestPossibleLoc < LowestPossibleLoc,
            // which means that s is known to be not in the array.  Return
            // a -1 to indicate that s could not be found in the array.
         
            return -1;

        }

 
       
       /* testing quick sort and binary search functions
        * */
	   
	   public static void main(String[] args) throws IOException {
	   	
		   	QuickSortBinarySearch qsbs = new QuickSortBinarySearch();
	
			String a[][] = { { "annieA", "BobA", "1" }, { "Centra", "cendra","2" },
					{ "Annie", "bob","3" }, { "David", "BobB","4" }, { "fox", "BobC","5" },
					{ "Wolf", "BobD" ,"6"}, { "Sai", "BobE","7" } };
			PrintOut.printStringArray("before sort", a);
	
			qsbs.quicksort(a, 0, a.length - 1);
			PrintOut.printStringArray("after sort", a);
	
			System.out.println();
			System.out.println("Second");
	
			String b[][] = { { "Steve", "BobA" }, { "Centra", "cendra" },
					{ "Maggi", "bob" }, { "Hunter", "BobB" }, { "fox", "BobC" },
					{ "David", "BobD" }, { "Sai", "BobE" } };
			PrintOut.printStringArray("before sort", b);
	
			qsbs.quicksort(b, 0, b.length - 1);
			PrintOut.printStringArray("after sort", b);
	
			
			String newA[][];
	
			if (a.length >= b.length) {
				newA = new String[a.length][a[0].length + b[0].length];
			} else {
				newA = new String[b.length][a[0].length + b[0].length];
			}
	
			String temp;
			int returnNum;
			int r = 0;
			int c = 0;
	
			String dwdInput = "c:/temp/DWD_Input.txt";
	
			PrintWriter writer = new PrintWriter(new FileWriter(dwdInput, false));
			List commonDesignElement =new ArrayList();
			
			for (int i = 0; i < a.length; i++) {
				temp = a[i][0];
				returnNum = qsbs.binarySearch(b, temp);
				if (returnNum != -1) {
					//System.out.println("i= " + i);
					//System.out.println("returnNum= " + returnNum);
					commonDesignElement.add(temp);
					
					for (int m = 0; m < a[0].length; m++) {
						writer.print(a[i][m] + "\t");
						newA[r][c] = a[i][m];
						c++;
					}
	
					for (int n = 0; n < b[0].length; n++) {
						writer.print(b[returnNum][n] + "\t");
						newA[r][c] = b[returnNum][n];
						c++;
					}
					writer.println();
					r++;
					c = 0;
	
				}
	
			}
	
			writer.close();
			PrintOut.printStringArray("after merger", newA);
			
			PrintOut.printList ("commonDesignElement",commonDesignElement);
	   }

}