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

package edu.unc.LCCC.caBIG.DWD.javaCode.ImportMAGEMLFiles;

import java.util.List;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.biomage.BioAssayData.BioAssayData;
import org.biomage.BioAssayData.DesignElementDimension;

import edu.unc.LCCC.caBIG.DWD.javaCode.util.GUIDesignHelpFunctions;

/************************************************************* 
*	DisplayBioAssayDataGUI.java
*
*	1. To display the BioAssayData from two different files
*	2. To choose BioAssayData from the lists 	
*
*	Version: v1.0
*	Author: Everett Zhou
*	Date: 10/17/2005
*
*************************************************************/

public class  DisplayBioAssayDataGUI extends JFrame implements ActionListener {
    /*Set up window */
	private Container content;
    private GridBagLayout gbLayout;
    private GridBagConstraints gbc;
    /* The action value */
    
    private String fileBase1, fileBase2;
    private JList displayDerivedBioAssayData1;
    private JList displayDerivedBioAssayData2;
    
    private List bioAssayDataList1, bioAssayDataList2;
    
    private static boolean selected = false;
    
    private static BioAssayData bioAssayData1, bioAssayData2;
     
    private String spaceLine = "<html>" + "<p>" +"<p>" + "</html>";
    private JButton getBioAssayData;
    private JButton reset;
    
    private static String bioAssayDataID1 [], bioAssayDataID2 [];
    
 /*   private String bioAssayNameList1 [];
    private String quantitationType1 [];
    private int numDesignElements1;

    private String bioAssayNameList2 [];
    private String quantitationType2 [];
    private int numDesignElements2;
*/
    /* The action value */
    private static String myAction;
    
    public DisplayBioAssayDataGUI (String fileBase1, final List bioAssayDataList1, String fileBase2, final List bioAssayDataList2) {
    	this.fileBase1 = fileBase1;
    	this.fileBase2 = fileBase2;
    	this.bioAssayDataList1 = bioAssayDataList1;
    	this.bioAssayDataList2 = bioAssayDataList2;
    	
    	
        setTitle("Step 1 of 2: Select BioAssayData");

	/* set up the size and location of window */
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screenWidth = d.width;
        //System.out.println (screenWidth + " "+screenHeight);
        
        
        float w1 = (float) ((float)screenWidth / 1.6);        
        Float w2 = new Float (w1);       
        int w3 = w2.intValue();

        float h1 = (float) ((float)screenHeight / 1.6);        
        Float h2 = new Float (h1);       
        int h3 = h2.intValue();
        
        setSize(w3, h3);
        setLocation(screenWidth / 6, screenHeight / 30);
        
 
        content = getContentPane();
		gbLayout = new GridBagLayout();
		content.setLayout(gbLayout);
		gbc = new GridBagConstraints();

		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.NONE;
		//gbc.anchor = GridBagConstraints.CENTER;
		gbc.anchor = GridBagConstraints.CENTER;

		gbc.weightx = 1;
		gbc.weighty = 0;
        
  	    JFrame GDHF = new GUIDesignHelpFunctions();
  	    
  	    /* For the first MAGE-ML file*/
  	    bioAssayDataID1 = new String [bioAssayDataList1.size()];
  	   
  	    for (int i=0; i<bioAssayDataList1.size();i++){
	    	
	    	BioAssayData bioAssayData = (BioAssayData) bioAssayDataList1.get(i); 
	    	
	    	if (bioAssayData.getIdentifier()!= null){
	    		bioAssayDataID1 [i] =bioAssayData.getIdentifier();
	    	}else {
	    		bioAssayDataID1 [i] =bioAssayData.getName();
	    	}
	    }
  	    
  	    int numDerivedBioAssayData = bioAssayDataID1.length;
  	    
  	    String fileLabel1="The first MAGE-ML file" + " contains " + numDerivedBioAssayData + " DerivedBioAssayData";
  	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, fileLabel1, 2,1,20,1);
  	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0,3,40,1);
  	   
 	    String selectBAD = "<html><FONT COLOR=RED SIZE =4>Please select one of the BioAssayDatas</FONT></html>"; 
 	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, selectBAD, 0,7,40,1);
  	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, " ", 0,9,40,1); 
  	    
 	    displayDerivedBioAssayData1 = new JList (bioAssayDataID1);
  	    displayDerivedBioAssayData1.setSelectedIndex(0);
  	    displayDerivedBioAssayData1.setVisibleRowCount(2); 
  	    
  	    MouseListener mouseListener1 = new MouseAdapter() {
   			public void mouseClicked(MouseEvent e) {
        		if (e.getClickCount() == 1) {	
        			int index = displayDerivedBioAssayData1.locationToIndex(e.getPoint());
        			//System.out.println(" Before selected is " + selected);
        			if (!selected ){
        				//System.out.println("selected is " + selected);
        				bioAssayData1 = (BioAssayData) bioAssayDataList1.get(index);
        				selected = true;
        				
        			} else {
        				//System.out.println("selected is " + selected);
        				bioAssayData2 = (BioAssayData) bioAssayDataList1.get(index);
        			}
        		}
   			 }
		};
 	    
		displayDerivedBioAssayData1.addMouseListener(mouseListener1);
  	    
  	   	JScrollPane listPane1 = new JScrollPane (displayDerivedBioAssayData1);
  	    ((GUIDesignHelpFunctions) GDHF).addComponent(content, gbc, listPane1, 5,14,40,5);
  	    
  	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0,20,40,1);

  	    
 		/* For the second MAGE-ML file*/
 	    bioAssayDataID2 = new String [bioAssayDataList2.size()];
 	  	  	
 	    for (int i=0; i<bioAssayDataList2.size();i++){
	    	
	    	BioAssayData bioAssayData = (BioAssayData) bioAssayDataList2.get(i); 
	    	
	    	if (bioAssayData.getIdentifier()!= null){
	    		bioAssayDataID2 [i] =bioAssayData.getIdentifier();
	    	}else {
	    		bioAssayDataID2 [i] =bioAssayData.getName();
	    	}
	    			    	
	    }
 	    
  	    numDerivedBioAssayData = bioAssayDataID2.length;
  	    
  	    String fileLabel2="The second MAGE-ML file" + " contains " + numDerivedBioAssayData + " DerivedBioAssayData";
  	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, fileLabel2, 2,25,30,1);
  	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0,26,40,1);

	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, selectBAD, 0,30,40,1);
  	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0,32,40,1); 
  
 	    displayDerivedBioAssayData2 = new JList (bioAssayDataID2);
  	    displayDerivedBioAssayData2.setSelectedIndex(0);
  	    displayDerivedBioAssayData2.setVisibleRowCount(2); 
  	    
  	    MouseListener mouseListener2 = new MouseAdapter() {
   			public void mouseClicked(MouseEvent e) {
        		if (e.getClickCount() == 1) {
            		int index = displayDerivedBioAssayData2.locationToIndex(e.getPoint());
            		//System.out.println(" Before selected is " + selected);
            		if (!selected ){
            			//System.out.println("selected is " + selected);
        				bioAssayData1 = (BioAssayData) bioAssayDataList2.get(index);
        				selected = true;
        			} else {
        				//System.out.println("selected is " + selected);
        				bioAssayData2 = (BioAssayData) bioAssayDataList2.get(index);
        			}
        		}
   			 }
		};
 	    
		displayDerivedBioAssayData2.addMouseListener(mouseListener2);
  	    
  	   	JScrollPane listPane2 = new JScrollPane (displayDerivedBioAssayData2);
  	    ((GUIDesignHelpFunctions) GDHF).addComponent(content, gbc, listPane2, 5,34,30,5);
  	    
  	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0,38,40,1);
  	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, " ", 0,40,40,1);
  	  
        /* Add in task buttons*/        
  	    getBioAssayData = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "Get BioAssayData", 4,43,3,1);
  	    getBioAssayData.setActionCommand("BAD");
  	    getBioAssayData.addActionListener(this);
 
	  	reset = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "Reset", 12,43,3,1);
	  	reset.setActionCommand("RESET");
	  	reset.addActionListener(this);
    }  /* end of constructor*/

    
    /* 	Build cells of JTable
     *  	@param textFile: the name of a file
    *		@return: an array for Jtable cells
    */
    public static String [][] buildCells (String bioAssayNameList[], int numDesignElements, String quantitationType []) {      
          
          String [][] cells = new String[bioAssayNameList.length][3];
          Integer designElementsCount = new Integer (numDesignElements);
          String value = designElementsCount.toString();
          
          String  quantitationTypeValue="";
          for (int i=0; i<quantitationType.length;i++){
          	quantitationTypeValue = quantitationTypeValue +quantitationType[i] + "\\";
          }
          
          for (int i=0; i<bioAssayNameList.length;i++){
          		cells[i][0]= bioAssayNameList[i];
          		cells[i][1]= value;
          		
          		cells[i][2]= quantitationTypeValue;          		
          }
  
          return (cells); 	
   	}  /* End of buildCells */  
 
       
    public void actionPerformed (ActionEvent ae) {      
        myAction = ae.getActionCommand();
    		           
        if (myAction.equalsIgnoreCase("BAD") ) {
        	    		        	
    		//BioAssayData bioAssayData = (BioAssayData) derivedBioAssayDataList.get(index);
    		/* for the first BioAssayData*/
    		String bioAssayNameList1 [] = ReadMAGEMLFiles.getBioAssayNameList(bioAssayData1);            		
    		
    		DesignElementDimension designElementDimension1 = bioAssayData1.getDesignElementDimension();		    
          	int numDesignElements1 = ReadMAGEMLFiles.getCountDesignElements(designElementDimension1);
          	
          	String quantitationType1 [] = ReadMAGEMLFiles.getQuantitationTypes (bioAssayData1);
          	
          	//String bioAssayInfo1 = buildBioAssayInfo (bioAssayNameList1, numDesignElements1);
          	
    		/* for the second BioAssayData*/
     		String bioAssayNameList2 [] = ReadMAGEMLFiles.getBioAssayNameList(bioAssayData2);            		
    		   		
    		DesignElementDimension designElementDimension2 = bioAssayData2.getDesignElementDimension();		    
          	int numDesignElements2 = ReadMAGEMLFiles.getCountDesignElements(designElementDimension2);
          	
          	String quantitationType2 [] = ReadMAGEMLFiles.getQuantitationTypes (bioAssayData2);
          	
          	//String bioAssayInfo2 = buildBioAssayInfo (bioAssayNameList2, numDesignElements2);
          	
          	
        	JFrame jfr = new DisplayBioAssayGUI (fileBase1, bioAssayData1, bioAssayNameList1, quantitationType1, numDesignElements1, 
        			fileBase2, bioAssayData2, bioAssayNameList2, quantitationType2, numDesignElements2);
    		jfr.show();
	
        } else if (myAction.equalsIgnoreCase("RESET") ){
        	selected = false;
        }
        
    } /* end of actionPerformed (ActionEvent ae)*/
    
}