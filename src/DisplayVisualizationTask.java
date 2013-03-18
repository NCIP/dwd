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

package edu.unc.LCCC.caBIG.DWD.javaCode.visualization;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import edu.unc.LCCC.caBIG.DWD.javaCode.util.GUIDesignHelpFunctions;

/*************************************************************
*	DisplayVisualizationTask.java
*	
*	To select the each visualization plot and display it
*
*	Version: v1.1
*	Author: Everett Zhou
*	Date: 2/21/2005
*
*************************************************************/


public class DisplayVisualizationTask extends JFrame implements ActionListener{
	/* window container */
	private Container content;
    private GridBagLayout gbLayout;
    private GridBagConstraints gbc;
    
    /* The action value */
    private String myAction;
    
    /*General info*/  
    private String label ="<html> <FONT COLOR=RED SIZE =6>Click each button to view each visualization plot</FONT> </html>";
    		
	/*Input five plot lables*/ 
	private String label1= "<html><FONT COLOR=BLUE SIZE =5>View 1 </FONT>is for the raw data and PC directions of raw data</html>" ;
    private String label2= "<html><FONT COLOR=BLUE SIZE =5>View 2 </FONT>is for the raw data, the first three PC directions of raw data<p> and fourth direction is replace by DWD vector</html>" ;
    private String label3= "<html><FONT COLOR=BLUE SIZE =5>View 3 </FONT>is for the adjusted data, the first three PC directions of raw data<p> and fourth direction is replace by DWD vector</html>" ;
    private String label4= "<html><FONT COLOR=BLUE SIZE =5>View 4 </FONT>is for the adjusted data and PC directions of raw data</html>" ;
    private String label5= "<html><FONT COLOR=BLUE SIZE =5>View 5 </FONT>is for the adjusted data and PC directions of adjusted data</html>" ;

    /* Five buttons for above file plots*/
    private JButton viewPlot1, viewPlot2,viewPlot3,viewPlot4,viewPlot5;

    //
    private JButton where1;
    
    /*Input the DWD merged file*/
    public static  JTextField DWDOutputFilePath;
    private JButton load4;
    private JButton where2;
    private JButton view;
    
    private JTextField row;
    private JTextField col;
    
    private String spaceLine = "<html>" + "<p>" +"<p>" + "</html>";
    
    private JButton visualization;
	
    private Thread eventThread;
    
    
    public DisplayVisualizationTask() {

        setTitle("Select Visulization Plot");


	/* set up the size and location of window */
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screenWidth = d.width;
 
        float w1 = (float) ((float)screenWidth / 1.5);        
        Float w2 = new Float (w1);       
        int w3 = w2.intValue();

        float h1 = (float) ((float)screenHeight / 1.5);        
        Float h2 = new Float (h1);       
        int h3 = h2.intValue();
 
        pack();
        setSize(w3,h3);
        setLocation(screenWidth /4, screenHeight / 8);
       
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
       
       JFrame GDHF = new GUIDesignHelpFunctions();
       
       /* General info*/
       ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, label, 2,0,20,1);
       ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 1, 40, 1);
       	
       	/*First visualization*/ 
       ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, label1, 2,2,4,1);           
       	viewPlot1 = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "View Plot", 13,2,4,1);
       	viewPlot1.setActionCommand("VP1");
       	viewPlot1.addActionListener(this);       	
       	((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 7, 40, 1);
 
      	/*Second visualization*/ 
       	((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, label2, 2,8,4,1);           
       	viewPlot1 = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "View Plot", 13,8,4,1);
       	viewPlot1.setActionCommand("VP2");
       	viewPlot1.addActionListener(this);       	
       	((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 10, 40, 1);
        
     	/*Third visualization*/ 
       	((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, label3, 2,12,4,1);           
       	viewPlot1 = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "View Plot", 13,12,4,1);
       	viewPlot1.setActionCommand("VP3");
       	viewPlot1.addActionListener(this);       	
       	((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 14, 40, 1);
        
     	/*Fourth visualization*/ 
       	((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, label4, 2,16,4,1);           
       	viewPlot1 = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "View Plot", 13,16,4,1);
       	viewPlot1.setActionCommand("VP4");
       	viewPlot1.addActionListener(this);       	
       	((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 18, 40, 1);
        
     	/*Fifth visualization*/ 
       	((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, label5, 2,20,4,1);           
       	viewPlot1 = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "View Plot", 13,20,4,1);
       	viewPlot1.setActionCommand("VP5");
       	viewPlot1.addActionListener(this);       	
        //addLabel(spaceLine, 0, 10, 40, 1);
 
    }    
        

    /* Select one of three actions */
    public void actionPerformed (ActionEvent ae)  {      
       myAction = ae.getActionCommand();
       int nOnes = VisualizationInput.nOnes;
       int nTwos = VisualizationInput.nTwos;
 		 
       /* For Raw Data - Raw PC View*/
       	if (myAction.equalsIgnoreCase("VP1") ) {
       		//SVDEZ.printArray("originalAdjustProj in Display",VisualizationInput.originalAdjustProj);
       		//SVDEZ.printArray("kde1 ",VisualizationInput.kde1);
     		SetUpPlotWindow setUpPlotWindow1 = new SetUpPlotWindow ("Raw Data - Raw PC View", 1,VisualizationInput.originalAdjustProj,VisualizationInput.kde1, nOnes, nTwos); 
      	  	
      	} /* For Raw Data - Raw PC & DWD View*/
       	else if (myAction.equalsIgnoreCase("VP2") ) {
     	 	SetUpPlotWindow setUpPlotWindow2 = new SetUpPlotWindow ("Raw Data - 3 Raw PC & DWD View", 2, VisualizationInput.newOriginalAdjustProj, VisualizationInput.kde2,nOnes, nTwos);  
		} /* Adj. Data - Raw PC & DWD View*/
      	else if (myAction.equalsIgnoreCase("VP3") ) {
    	  	SetUpPlotWindow setUpPlotWindow3 = new SetUpPlotWindow ("Adj. Data - 3 Raw PC & DWD View", 3, VisualizationInput.DWDAdjustProj1, VisualizationInput.kde3, nOnes, nTwos); 
		} /* Adj. Data - Raw PC View*/
		else if (myAction.equalsIgnoreCase("VP4") ) {
			SetUpPlotWindow setUpPlotWindow4 = new SetUpPlotWindow ("Adj. Data - Raw PC View", 4, VisualizationInput.DWDAdjustProj2, VisualizationInput.kde4, nOnes, nTwos);
		} /* Adj. Data - Adj. PC View*/
		else if (myAction.equalsIgnoreCase("VP5") ) {
     	  	SetUpPlotWindow setUpPlotWindow5 = new SetUpPlotWindow ("Adj. Data - Adj. PC View", 5, VisualizationInput.DWDAdjustProj3, VisualizationInput.kde5, nOnes, nTwos); 
		}       	      	      	
    }/* End of action*/

 
}  