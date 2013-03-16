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
*	PopUpTask.java
*	Just remind that a task is running or done
*
*	Version: v1.0
*	Author: Everett Zhou
*	Date: 11/30/2004
*
*************************************************************/

public class PopUpTask extends JFrame implements ActionListener {

	private Container content;
	private String myMessage;
	    
    public PopUpTask(String message) {
    	
    	setTitle("Task Status");
    	
		myMessage = message;
		
		/* set up the size and location of window */
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screenWidth = d.width;
        setSize(screenWidth / 4, screenHeight / 4);
        setLocation(screenWidth / 6, screenHeight / 6);
       
       	content = getContentPane();
       	content.setLayout(new BorderLayout ());
        
 		/* Add in the explanation first */
 	  
 	    String labelText = message;
 	    JLabel coloredLable = new JLabel (labelText,JLabel.CENTER ); 
    	content.add (coloredLable,BorderLayout.CENTER);
    
	}


   public void actionPerformed (ActionEvent ae) {      
       String myAction = ae.getActionCommand();

   } 
}


