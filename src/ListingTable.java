/************************************************************* 
 *	DWD (Distance Weighted Discrimination)
 *	
 *	This software is developed by LCCC and Statistics at UNC
 *	It is supported by caBIG from NCI and is Open source 
 *
 *************************************************************/
 
package edu.unc.LCCC.caBIG.DWD.javaCode.processfile;

import java.awt.*;
import java.awt.event.*;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.StringTokenizer;

import javax.swing.*;

/************************************************************* 
 * ListingTable.java
 *
 * This file will do:
 *	
 *	show the file content: where to start reading data
 *	show the absolute path of the file
 *
 *	Version: v1.0
 *	Author: Everett Zhou
 *	Date: 12/10/2004
 *
 *************************************************************/
 
public class ListingTable extends JFrame implements ActionListener{

	private Container content;	
   	public ListingTable(String filePath,String[][] myCells,String[] myColumnNames ) {
       	
    	setTitle("See the Format of Text File");
    	
	/* set up the size and location of window */
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screenWidth = d.width;
        setSize(screenWidth / 4, screenHeight / 4);
        setLocation(screenWidth / 6, screenHeight / 6);
       
       	content = getContentPane();
       	content.setLayout(new BorderLayout ());
        
        JTable jtable = new JTable(myCells, myColumnNames); 
 		/* For scrollable both vertically and horizontally */
        jtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane scrollPane = new JScrollPane(jtable);
		content.add(scrollPane, BorderLayout.CENTER);
		
    	JTextField fTextField = new JTextField ("File Path");
    	fTextField.setText (filePath);
    	
    	content.add ( fTextField, "South");
    	setSize (600,400);
   	

	}

   	/* A quick test */
   public void actionPerformed (ActionEvent ae) {      
       String myAction = ae.getActionCommand();
       
    }
    
}