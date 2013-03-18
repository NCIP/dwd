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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/************************************************************* 
*
*	PopUpError.java
*	Just report whether there is a missing value 
*		or non digital value in the data sets
*
*	Version: v1.0
*	Author: Everett Zhou
*	Date: 11/30/2004
*
*************************************************************/
public class PopUpError extends JFrame implements ActionListener {

    private Container content;
    private String myMessage1;
    private String myMessage2;
    private JButton exit; 
    
    public PopUpError(String message1, String message2) {
    	setTitle("Error Report");
		myMessage1 = message1;
		myMessage2 = message2;
		
	/* set up the size and location of window */
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screenWidth = d.width;
        setSize(screenWidth / 3, screenHeight / 5);
        setLocation(screenWidth / 6, screenHeight / 6);
       
       	content = getContentPane();
       	content.setLayout(new BorderLayout ());
        
 		/* Add in the explanation first */
 	  
 	    String labelText = message1;
 	    JLabel coloredLable = new JLabel (labelText,JLabel.CENTER ); 
    	content.add (coloredLable,BorderLayout.CENTER);
    	
    	/* Add exit button*/
    	exit =new JButton(message2);
    	content.add (exit,BorderLayout.SOUTH);
    	exit.setActionCommand("EXIT");
        exit.addActionListener(this);

	}


   public void actionPerformed (ActionEvent ae) {      
       String myAction = ae.getActionCommand();      
       	// To exit
       	if (myAction.equalsIgnoreCase("EXIT") ) {
       		System.exit(0);
       	} 

   }    
}


