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

package edu.unc.LCCC.caBIG.DWD.javaCode;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import edu.unc.LCCC.caBIG.DWD.javaCode.processfile.*;
import edu.unc.LCCC.caBIG.DWD.javaCode.ImportMAGEMLFiles.*;
import edu.unc.LCCC.caBIG.DWD.javaCode.util.GUIDesignHelpFunctions;
import edu.unc.LCCC.caBIG.DWD.javaCode.visualization.VisualizationInput;

/************************************************************* 
*
*	DWDTaskSelection.java
*	It is called by DWD.java to select the tasks
*
*	Version: v1.0
*	Author: Everett Zhou
*	Date: 11/29/2004
*
*************************************************************/

public class DWDTaskSelection extends JFrame implements ActionListener {

	/* window container */
	private Container content;
    private GridBagLayout gbLayout;
    private GridBagConstraints gbc;
    
    /* The action value */
    private String myAction;
    
    /* Two buttons for two tasks */
    private JButton merge; 
    private JButton visualization;
	
    public DWDTaskSelection() {

        setTitle("Select a Task");

        /* Close window function */
        addWindowListener( new WindowAdapter()
            {    public void windowClosing(WindowEvent e)
                 {
                     System.exit(0);
                 }
             } );

	/* set up the size and location of window */
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screenWidth = d.width;
        
        setSize(screenWidth / 2, screenHeight / 2);
        setLocation(screenWidth / 7, screenHeight / 6);
       
       content = getContentPane();
       gbLayout = new  GridBagLayout();
       content.setLayout( gbLayout);
       
       gbc = new GridBagConstraints();
       gbc.weightx = 1;
       gbc.weighty = 1;
       gbc.fill = GridBagConstraints.NONE;
       gbc.anchor = GridBagConstraints.CENTER;

       gbc.weightx = 1;
       gbc.weighty = 0;
       
 		/* Add in the explanation first */ 	  
 	   String labelText = "<html><FONT COLOR=RED SIZE =6>Note:</FONT><p>" +
 	    	"Before you visualize and diagnose the data,<p>" + 
 	    	"you have to merge two data files first.<p><p><p></html>";
 	   JLabel coloredLable = new JLabel (labelText,JLabel.CENTER ); 
 
 	  JFrame GDHF = new GUIDesignHelpFunctions();
 	  
 	  ((GUIDesignHelpFunctions) GDHF).addComponent(content, gbc, coloredLable, 0, 4, 40, 1);

 	   /* Add the space line */
 	    String spaceLine = "<html>" + "<p>" +"<p>" + "</html>";
 	    JLabel emptyLine = new JLabel (spaceLine);
 	   ((GUIDesignHelpFunctions) GDHF).addComponent(content, gbc, emptyLine, 0, 8, 40, 3);
		    
 //---------------------------------------------------------
 /* For Task Merge data for Stanford data*/
 	  ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc,"Merge Data Files", 6,12,1,1);
        merge = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc,"DWD Merge Stanford Format Data", 10,12,5,1);
        merge.setActionCommand("MERGESTD");
        merge.addActionListener(this);
        ((GUIDesignHelpFunctions) GDHF).addComponent(content, gbc, emptyLine, 0, 14, 40, 3); 
  
        /* For Task Merge for MAGE-ML data*/
   	  	((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc," ", 6,20,1,1);
   	  	((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc,"Merge Data Files", 6,26,1,1);
          merge = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc,"DWD Merge MAGE-ML Format Data", 10,26,5,1);
          merge.setActionCommand("MERGEMAGE");
          merge.addActionListener(this);
          ((GUIDesignHelpFunctions) GDHF).addComponent(content, gbc, emptyLine, 0, 30, 40, 3);       

          
 /* Task For Visualization */
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc,"Visualize Data ", 6,36,1,1);
        visualization = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc,"Visualize and Diagnose",11,36,5,1);
        visualization.setActionCommand("VIS");
        visualization.addActionListener(this);
    }    
        

   /* Select one of two action to do one two tasks */
   public void actionPerformed (ActionEvent ae) {      
       myAction = ae.getActionCommand();
       
       	/* To Merge Stanford data files */
       	if (myAction.equalsIgnoreCase("MERGESTD") ) {
       		 JFrame jframe = new UpLoadFile ();
             jframe.show(); 
             /* To Merge MAGE-ML data files */
       	} else if (myAction.equalsIgnoreCase("MERGEMAGE") ) {
      		 JFrame jframe = new UpLoadMAGEMLFile ();
             jframe.show(); 
       	} 
       	else if (myAction.equalsIgnoreCase("VIS") ){
           	JFrame jf = new VisualizationInput ();
     		jf.show(); 
       	}
   }
 
}


