/************************************************************* 
 *	DWD (Distance Weighted Discrimination)
 *	
 *	This software is developed by LCCC and Statistics at UNC
 *	It is supported by caBIG from NCI and is Open source 
 *
 *************************************************************/

 
package edu.unc.LCCC.caBIG.DWD.javaCode.processfile;

import java.io.*;

/************************************************************* 
 * JavaFilter.java
 * 
 * This file filter all files other than .txt and .xls
 * Filter to work with JFileChooser to select java file types.
 *  Adopted from JavaFilter.java
 * 
 * 	Version: v1.0
 *	Author: Everett Zhou
 *	Date: 11/16/2004
 *
 *************************************************************/

public class JavaFilter extends javax.swing.filechooser.FileFilter{

	public boolean accept (File f) {
		return f.getName ().toLowerCase ().endsWith (".txt")
    	  || f.getName ().toLowerCase ().endsWith (".xls")
          || f.isDirectory ();
	}
  
	public String getDescription () {
		return "Java files (*.java)";
	}
} // class JavaFilter