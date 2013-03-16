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

	public static void main(String[] args){
		/*Initiate the DWD tasks*/
		JFrame jframe = new DWDTaskSelection();
		jframe.show();

		}
	}
