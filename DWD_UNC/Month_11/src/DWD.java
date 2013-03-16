/************************************************************* 
 *	DWD (Distance Weighted Discrimination)
 *	
 *	This software is developed by LCCC and Statistics at UNC
 *	It is supported by caBIG from NCI and is Open source 
 *
 *************************************************************/

package edu.unc.LCCC.caBIG.DWD.javaCode;

import javax.swing.*;

/************************************************************* 
 * 
 *	DWD.java file
 *	This is the leading file
 *	
 *	Version: v1.0
 *	Author: Everett Zhou
 *	Date: 9/29/2004
 *
 *************************************************************/
public class DWD{
	// Declaration of the Native (C) function
	/*
	private native void matfuc();
	
	static{
		// The runtime system executes a class's static initializer 
		// when it loads the class.
		System.loadLibrary("CJavaInterface");
		}
	*/
		
	/*	The main program
	 * 	To select the tasks
	 * 
	 */ 
	public static void main(String[] args){
		JFrame jframe = new DWDTaskSelection();
		jframe.show();

	 /*  
		DWD dwd = new DWD();
		dwd.matfuc();	
		System.out.println("Back from call matlab fun ");
	  */	
		}
	}
