/************************************************************* 
 *	DWD (Distance Weighted Discrimination)
 *	
 *	This software is developed by LCCC and Statistics at UNC
 *	It is supported by caBIG from NCI and is Open source 
 *
 *************************************************************/
 
package edu.unc.LCCC.caBIG.DWD.javaCode.ImportMAGEMLFiles;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


import org.biomage.BioAssayData.*;
import org.biomage.Common.MAGEJava;
import org.biomage.DesignElement.DesignElement;

import edu.unc.LCCC.caBIG.DWD.javaCode.util.GUIDesignHelpFunctions;
import edu.unc.LCCC.caBIG.DWD.javaCode.SVD.MatrixStandardization;
import edu.unc.LCCC.caBIG.DWD.javaCode.processfile.*;
import edu.unc.LCCC.caBIG.DWD.javaCode.util.*;

/************************************************************* 
 * DisplayBioAssayGUI.java
 *
 * This file will do:
 *	
 *	1. display the BioAsssay of a BioAssayData
 *	2. display the QuantitationType for selection 
 *
 *	Version: v1.0
 *	Author: Everett Zhou
 *	Date: 10/19/2005
 *
 *************************************************************/
 
public class DisplayBioAssayGUI extends JFrame implements ActionListener{

    /*Set up window */
	private Container content;
    private GridBagLayout gbLayout;
    private GridBagConstraints gbc;
    
    private String fileBase1, fileBase2;
    private BioAssayData bioAssayData1, bioAssayData2;
    private String bioAssayNameList1 [],bioAssayNameList2 [];
    private String quantitationType1 [], quantitationType2 [];
    private int numDesignElements1, numDesignElements2;
    
 
    private String spaceLine = "<html>" + "<p>" +"<p>" + "</html>";
    
    private JTextArea fTextArea1;
    private JTextArea fTextArea2;
	private JList displayQT1;
    private JList displayQT2;
    
    private static boolean selected = false;
    private int index1=-1;
    private int index2=-1;
    private static String selectedQT1;
    private static String selectedQT2;
	
    //private static boolean selected = false;
    
    private JButton mergeBioAssayData;
    private JButton reset;
	private static String myAction;
 	
	private static String [][] data ; 
	
	private static List commonDesignElement;
	private static MAGEJava mageJava;
	   
   	public DisplayBioAssayGUI (String fileBase1, BioAssayData bioAssayData1, String bioAssayNameList1 [] ,String quantitationType1 [], int numDesignElements1,
   			String fileBase2, BioAssayData bioAssayData2, String bioAssayNameList2 [], String quantitationType2 [], int numDesignElements2) {
       	
    	setTitle("Step 2 of 2: Select QuantitationType");
    	
    	this.fileBase1 = fileBase1; 
    	this.fileBase2 = fileBase2;
    	this.bioAssayData1= bioAssayData1;
    	this.bioAssayData2= bioAssayData2;
    	
    	this.bioAssayNameList1 = bioAssayNameList1;
    	this.bioAssayNameList2 = bioAssayNameList2;
    	
    	this.quantitationType1 = quantitationType1;
    	this.quantitationType2 = quantitationType2;
    	
    	this.numDesignElements1 = numDesignElements1;
    	this.numDesignElements2 = numDesignElements2;
    	
	/* set up the size and location of window */
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screenWidth = d.width;
        
        float w1 = (float) ((float)screenWidth / 1.4);        
        Float w2 = new Float (w1);       
        int w3 = w2.intValue();

        float h1 = (float) ((float)screenHeight / 1.1);        
        Float h2 = new Float (h1);       
        int h3 = h2.intValue();
        
        setSize(w3, h3);
        setLocation(screenWidth / 6, screenHeight /30);
       
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
       	
   	    /* For the first BioAssayData*/
   	    String bioAssayDataName1 =bioAssayData1.getIdentifier();
   	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "BioAssayData: " + bioAssayDataName1 + " has the following BioAssays", 2,0,20,1);     	
  	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0,1,40,1);
  	    
  	    /*bioAssayInfo*/
  	    String bioAssayInfo1 = buildBioAssayInfo (bioAssayNameList1, numDesignElements1);
		fTextArea1 = ((GUIDesignHelpFunctions) GDHF).addTextArea(content, gbc, bioAssayInfo1 ,false, 0,3,40,1);
 	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0,8,40,1);

 	   /*For QT*/
 	    String selectQT = "<html><FONT COLOR=RED SIZE =4>Please select one of the QTs</FONT></html>";
 	    //((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "Please select one of the QTs", 0,10,40,1);
 	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, selectQT, 0,10,40,1);
  	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0,12,40,1);
  	  	displayQT1 = new JList (quantitationType1);
	  	displayQT1.setSelectedIndex(0);
	  	displayQT1.setVisibleRowCount(2); 
  	    
  	    MouseListener mouseListener1 = new MouseAdapter() {
   			public void mouseClicked(MouseEvent e) {
        		if (e.getClickCount() == 1) {
        			
           			if (!selected ){
           				index1 = displayQT1.locationToIndex(e.getPoint());
           				selectedQT1= (String) displayQT1.getSelectedValue();
           				//System.out.println("selected QT is " + selectedQT1);
           				selected = true;
        				
        			} else {
        				index2 = displayQT1.locationToIndex(e.getPoint());
          				selectedQT2= (String) displayQT1.getSelectedValue();
           				System.out.println("selected QT is " + selectedQT2);
  
        			}
         		 }
   			 }
		};
 	    
		displayQT1.addMouseListener(mouseListener1); 	    
  	   	JScrollPane listPane1 = new JScrollPane (displayQT1); 
	    ((GUIDesignHelpFunctions) GDHF).addComponent(content, gbc, listPane1, 0,15,30,5);
	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0,20,40,1);
	    
	    /* For the second BioAssayData*/
	    String bioAssayDataName2 =bioAssayData2.getIdentifier();
   	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "BioAssayData: " + bioAssayDataName2 + " has the following BioAssays", 2,24,20,1);     	
  	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0,25,40,1);
  	    
  	    /*bioAssayInfo*/
  	    String bioAssayInfo2 = buildBioAssayInfo (bioAssayNameList2, numDesignElements2);
		fTextArea2 = ((GUIDesignHelpFunctions) GDHF).addTextArea(content, gbc, bioAssayInfo2 ,false, 0,26,40,1);
 	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0,32,40,1);

 	   /*For QT*/
  	    //((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "Please select one of the QTs", 0,34,40,1);
  	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, selectQT, 0,34,40,1);
 
	  	displayQT2 = new JList (quantitationType2);
	  	displayQT2.setSelectedIndex(0);
	  	displayQT2.setVisibleRowCount(2); 
  	    
  	    MouseListener mouseListener2 = new MouseAdapter() {
   			public void mouseClicked(MouseEvent e) {
        		if (e.getClickCount() == 1) {
           			if (!selected ){
           				index1 = displayQT2.locationToIndex(e.getPoint());
           				selectedQT1= (String) displayQT2.getSelectedValue();
           				System.out.println("selected QT is " + selectedQT1);
           				selected = true;
        				
        			} else {
        				index2 = displayQT2.locationToIndex(e.getPoint());
          				selectedQT2= (String) displayQT2.getSelectedValue();
           				//System.out.println("selected QT is " + selectedQT2);
  
        			}
        			//System.out.println("Second BAD on Item QT" + index2);
        		 }
   			 }
		};
 	    
		displayQT2.addMouseListener(mouseListener2); 	    
  	   	JScrollPane listPane2 = new JScrollPane (displayQT2); 
	    ((GUIDesignHelpFunctions) GDHF).addComponent(content, gbc, listPane2, 0,36,30,5);
 	    ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0,40,40,1);

	    
        /* Add in task buttons*/        
	    mergeBioAssayData = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "Merge BioAssayData", 2,45,3,1);
	    mergeBioAssayData.setActionCommand("MERGEBAD");
	    mergeBioAssayData.addActionListener(this);
 
	  	reset = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "Reset", 8,45,3,1);
	  	reset.setActionCommand("RESET");
	  	reset.addActionListener(this);
	}


    
   	/* A quick test */
   public void actionPerformed (ActionEvent ae) {      
       
       	myAction = ae.getActionCommand();
       	//int bioAssayIndex=1;
		
       	if (myAction.equalsIgnoreCase("MERGEBAD") ) {
            JFrame jfr1 = new PopUpTask ("<html>Merging data, it may takes a few minutes</html>");
            jfr1.show(); 
            
       		Thread FPT = new RetrieveBioAssayDataCubeThread (jfr1);
     		/* Kick it off*/
     		FPT.start();
  						        	
		} else if (myAction.equalsIgnoreCase("RESET") ){
			//selected = false;
			index1=-1;
			index2=-1;
		}

    }

   
   /* Build BioAssay Info
    * 
    * @param bioAssayNameList:  an array of BioAssay Names 
    * @param numDesignElements: the number of DesignElements
    * @return: the string of BioAssay Info
   */
   private static String buildBioAssayInfo (String bioAssayNameList[], int numDesignElements) {      
         
         Integer designElementsCount = new Integer (numDesignElements);
         String value = designElementsCount.toString();
         
         String bioAssayInfo ="";
         for (int i=0; i<bioAssayNameList.length;i++){
         		bioAssayInfo = bioAssayInfo+ ".   " +	bioAssayNameList[i] +  " has " +value + " DesignElements \n" ;    		
         }
 
         return bioAssayInfo; 	
  	}  /* End of buildCells */ 


   /* This is the better way to use another thread 
	 * to retrieve all BioAssayData cube data
	 */
   private class RetrieveBioAssayDataCubeThread extends Thread {
  		private JFrame jfr;
  		RetrieveBioAssayDataCubeThread (JFrame jfr){
  		this.jfr = jfr;
  		}
  		public void run(){
      		//System.out.println ("bioDataCube1");
			BioDataCube bioDataCube1 =  (BioDataCube) bioAssayData1.getBioDataValues();
			
           int numQuantitationTypes1 = quantitationType1.length;
           int numBioAssays1 = bioAssayNameList1.length;

           DesignElementDimension designElementDimension1 = bioAssayData1.getDesignElementDimension();	          	
         	List designElementlist1 = ReadMAGEMLFiles.getDesignElementsList(designElementDimension1);

/*         	System.out.println("bioAssay " +numBioAssays1 + " numQuantitationTypes " +numQuantitationTypes1 +
           		" numDesignElements " +numDesignElements1);
         	System.out.println("QT index= "+ index1);
 */   
         	String cubeFileName1 = "C:\\DWD\\DWDdata\\CubeFileName1.txt";
         	
         	data = new String [numDesignElements1][numBioAssays1+1];
         	for (int i=0; i<numBioAssays1; i++){
     			createData (bioDataCube1,numDesignElements1, numQuantitationTypes1, index1, numBioAssays1, i, designElementlist1, fileBase1, cubeFileName1);
          		
    		}
         	
	        String newData1 [][] = getData(designElementlist1);	
	        QuickSortBinarySearch qsbs = new QuickSortBinarySearch();
/*	        
	        try {	        	
	        	saveArray (newData1,cubeFileName1);
				 
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
*/			
	        qsbs.quicksort(newData1, 0, newData1.length - 1);
			
			System.out.println ();
			//System.out.println ("bioDataCube2");
         	BioDataCube bioDataCube2 =  (BioDataCube) bioAssayData2.getBioDataValues();
           int numQuantitationTypes2 = quantitationType2.length;
           int numBioAssays2 = bioAssayNameList2.length;

           DesignElementDimension designElementDimension2 = bioAssayData2.getDesignElementDimension();	          	
         	List designElementlist2 = ReadMAGEMLFiles.getDesignElementsList(designElementDimension2);

/*         	System.out.println("bioAssay " +numBioAssays2 + " numQuantitationTypes " +numQuantitationTypes2 +
           		" numDesignElements " +numDesignElements2);
         	System.out.println("QT index= "+ index2);
  */ 
         	String cubeFileName2 = "C:\\DWD\\DWDdata\\CubeFileName2.txt";

        	data = new String [numDesignElements2][numBioAssays2+1];
         	for (int i=0; i<numBioAssays2; i++){          		
          		createData (bioDataCube2,numDesignElements2, numQuantitationTypes2, index2, numBioAssays2, i, designElementlist2, fileBase2, cubeFileName2);
      		}

	        String newData2 [][] = getData(designElementlist2);	
/*	        
	        try {
	        	
	        	saveArray (newData2,cubeFileName2);
				 
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
*/
			qsbs.quicksort(newData2, 0, newData2.length - 1);
	
			String temp;
			int returnNum;

			String dwdInput = "c:/DWD/DWDdata/DWD_Input.txt";
	
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(new FileWriter(dwdInput, false));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			for (int i=0; i<numBioAssays1;i++) {
				writer.print ("1" +"\t");
			}
			
			for (int i=0; i<numBioAssays2-1;i++) {
				writer.print ("2" +"\t");
			}
			writer.println("2");
			
			
			commonDesignElement =new ArrayList();
			
			for (int i = 0; i < newData1.length; i++) {
				temp = newData1[i][0];
				returnNum = qsbs.binarySearch(newData2, temp);
				if (returnNum != -1) {
					//System.out.println("i= " + i);
					//System.out.println("returnNum= " + returnNum);
					commonDesignElement.add(temp);
					
					for (int m = 1; m < newData1[0].length; m++) {
						writer.print(newData1[i][m] + "\t");
						//newA[r][c] = newData1[i][m];
						//c++;
					}
	
					for (int n = 1; n < newData2[0].length-1; n++) {
						writer.print(newData2[returnNum][n] + "\t");
						//newA[r][c] = newData2[returnNum][n];
						//c++;
					}
					writer.println(newData2[returnNum][newData2[0].length-1]);
					//r++;
					//c = 0;
	
				}
	
			}
	
			writer.close();
			
			//System.out.println ("commonDesignElement size " +commonDesignElement.size() );
			//PrintOut.printStringArray("after merger", newA);
			
			//PrintOut.printList ("commonDesignElement",commonDesignElement);
    		
			/* Call BatchAdjust.exe here*/
			doAdjustment (jfr, bioAssayNameList1, bioAssayNameList2);
						
			jfr.dispose();
    		     	    		
  		}
   } /* end of RetrieveBioAssayDataCubeThread*/
 
 
   /* To call BatchAdjustSM.exe and save the adjusted data 
    * 	into text file and MAGE-ML file
    * @param jfr: JFrame
    * @param bioAssayNameList1: the first array for BioAssayName
    * @param bioAssayNameList2: the second array for BioAssayName
    */
   	private static void doAdjustment (JFrame jfr, String bioAssayNameList1[], String bioAssayNameList2[]){

   		/*To test whether the combined file DWD_Input.txt is empty
		 if it is, flag the error, or else flag the job is done*/		 
   		if (commonDesignElement.size() <1) {
		 	jfr.dispose();
		 	JFrame jf = new PopUpError ("<html><p>There is <FONT COLOR=RED SIZE =4>No Common Genes</FONT> in these two files</html>",
		 			"Fix the File(s) and Terminate the Program");
		 	jf.show(); 
		 	return;
		 } else {  
		 	/*To save the value of mean adjustment type into the text file
		 	 * The file will be read by BatchAdjustSM.exe*/
		 	   String meanAdjustTypeFile = "C:/DWD/DWDdata/DWD_MeanAdjustType.txt";
		 	   try {
		 	   		PrintWriter writer = new PrintWriter(new FileWriter (meanAdjustTypeFile, false));
		 	   		writer.println (UpLoadMAGEMLFile.DWDMeanAdjust);
		 	   		writer.flush();
		 	   		writer.close();
		 	   } catch (IOException ioe){
		 	   		System.out.println (ioe);
		 	   }   	 	   	     	
		 	 /*To do DWD merge */
		 	Runtime rt = Runtime.getRuntime();   		
		 	Process proc;
		
		 	try {
		 		proc = rt.exec("C:/DWD/lib/cCode/BatchAdjustSM.exe");
		 		InputStream results = proc.getInputStream();
		 		byte[] b = new byte[1024];
		 		while (true) {
		 		   int n = results.read(b);
		 		   if (n == -1)
		 		       break;
		 		}
		     	
		 		 /*Delete the DWD_MeanAdjustType.txt*/
		     	File f2 = new File ("C:/DWD/DWDdata/DWD_MeanAdjustType.txt");
		     	if (f2.exists()){
		     		//System.out.println("Test files 2 "+f2.exists());
		     		f2.delete();
		     		//System.out.println("Test files 2 "+f2.exists());
		     	}
		     	
		 	} catch (Exception e) {
		 		System.out.println(e); 
		 	} 
		} /*End of  else 1 */
   		
   		/*To post process the result*/
		String fileNameIn ="C:/DWD/DWDdata/DWD_Intermediate_Output.txt";
		String source1 =UpLoadMAGEMLFile.fileName1.getText().trim();
		String source2 =UpLoadMAGEMLFile.fileName2.getText().trim();
		String fileNameOut1 =UpLoadMAGEMLFile.outputfile1.getText().trim();
		String fileNameOut2 =UpLoadMAGEMLFile.outputfile2.getText().trim();

   		File outFile = new File (fileNameOut2);
   		String fileBase = outFile.getParent();
   		
   		String externalFileName ="ExternalAdjustedDataFile.txt";
   		String externalFileNamePath =fileBase +"/" +externalFileName;
   		/*	This is th standardized DWD
		 Need to standardize each column by (cij-mean)/std error
		*/
		if (UpLoadMAGEMLFile.DWDType ==1){
			// First read the DWD output
			double in [][] = ReadTextFile.readDWDInputOutputToArrayArray(fileNameIn,1,0);
		
			//compute the mean and std error for each column
			double meanStdError [][] = MatrixStandardization.computeMeanStdError (in);
		
			// To do the standization
			double standardizedArr [][] = MatrixStandardization.standardization(in,meanStdError );
		
			//To put back keys
			try {
				//CombineFiles.filePostProcess (UpLoadMAGEMLFile.SaveFileIndicator, fName1,colAt1, IDCol1,fName2, standardizedArr, ones ,fileNameOut);
				CombineFiles.filePostProcess (UpLoadMAGEMLFile.SaveFileIndicator, source1, bioAssayNameList1, source2, bioAssayNameList2, commonDesignElement, standardizedArr ,fileNameOut1);
			}	catch (IOException ioe) {
				System.out.println ( "File not found or wrong file");
				System.out.println ( "IOException message = "+ ioe.getMessage() );
			}	
			
			/* save as MAGE-ML file*/
	   		ExportMAGEFiles exportMAGEFiles =new ExportMAGEFiles( selectedQT1, selectedQT2, 
	   		 		bioAssayNameList1,bioAssayNameList2, commonDesignElement, externalFileName);
	   		PrintOut.saveArrayAsFile (standardizedArr, externalFileNamePath);
	   		try {
	   			
				exportMAGEFiles.writeToMAGEML (fileNameOut2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*Delete DWD_Intermediate_Output.txt intermidiate file*/
			File f1 = new File (fileNameIn);
			if (f1.exists()){		     		
				f1.delete();
			}

			 /*Close the window*/
			jfr.dispose();
			/*Report the job is done*/
			JFrame jf3 = new PopUpTask ("<html>Finished <FONT COLOR=RED SIZE =4> Standardized DWD</FONT>" +
				"<p> <p>You are ready to click <p><FONT COLOR=RED SIZE =4>Visualize and Diagnose</FONT></html>");
			jf3.show();    		
			System.out.println ("Finished Standardized DWD"); 
		}
		/*For non standardized DWD just put the keys and titles back*/
		else if (UpLoadMAGEMLFile.DWDType ==2){

			try {
				CombineFiles.filePostProcess (UpLoadMAGEMLFile.SaveFileIndicator, source1, bioAssayNameList1, 
						source2, bioAssayNameList2, commonDesignElement,fileNameIn,fileNameOut1);
			}	catch (IOException ioe) {
				System.out.println ( "File not found or wrong file");
				System.out.println ( "IOException message = "+ ioe.getMessage() );
			} 
			
			/* save as MAGE-ML file*/
	   		ExportMAGEFiles exportMAGEFiles =new ExportMAGEFiles( selectedQT1, selectedQT2, 
	   		 		bioAssayNameList1,bioAssayNameList2, commonDesignElement, externalFileName);
	   		double in [][] = ReadTextFile.readDWDInputOutputToArrayArray(fileNameIn,1,0);
	   		PrintOut.saveArrayAsFile (in, externalFileNamePath);
	   		try {
	   			
				exportMAGEFiles.writeToMAGEML (fileNameOut2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 /*Delete DWD_Intermediate_Output.txt intermidiate file*/
			File f1 = new File (fileNameIn);
			if (f1.exists()){
				f1.delete();
			}
			 /*Close the window*/
			jfr.dispose();
			/*Report the job is done*/
			JFrame jf4 = new PopUpTask ("<html>Finished <FONT COLOR=RED SIZE =4> Non-Standardized DWD</FONT> " +
				"<p> <p>You are ready to click <p><FONT COLOR=RED SIZE =4>Visualize and Diagnose</FONT></html>");
			jf4.show();    		
			System.out.println ("Finished Non-Standardized DWD");         	    		
			
		}
	}


   
   /* To read data into a cube from external file
    * @param bioDataCube: BioDataCube dat array
    * @param numDesignElements: the number of DesignElements
    * @param numQuantitationTypes : the number of QuantitationTypes
    * @param QTIndex: the selected QuantitationType
    * @param numBioAssays : the number of BioAssays
    * @param bioAssayIndex: the selected BioAssay
    * @param designElementlist : the list of DesignElements
    * @param fileBase : the file base
    * @param cubeFileName: the file name
    */	
	
   private static void createData (BioDataCube bioDataCube,int numDesignElements, int numQuantitationTypes, int QTIndex, 
   		int numBioAssays, int bioAssayIndex,List designElementlist, String fileBase, String cubeFileName) {
    	   
 		DataInternal dataInternal =bioDataCube.getDataInternal();
    	
        if (dataInternal !=null){
        	//System.out.println(" DataInternal exists");
        	//System.out.println();
        	
        	StringReader sr = new StringReader(dataInternal.getPcData().getContent());
            BufferedReader br = new BufferedReader(sr);
            String orderName = bioDataCube.getNameOrder();
            //System.out.println("BioDataCube name order is "+ orderName);
            if (orderName==null){
            	orderName="DQB";
            }
            //System.out.println("BioDataCube name order after assignment is "+ orderName);
          	
            String [][][] cube = parseDataCube(orderName, bioDataCube, numBioAssays, numQuantitationTypes, numDesignElements, br);
            
            //System.out.println ("cube.length " +cube.length +" "+cube[0].length +" "+cube[0][0].length );
        	//System.out.println("cube[0][0][0]: " + cube[0][0][0]);
        	//System.out.println("cube[0][1][0]: " + cube[0][1][0]);
            readDataCube(orderName, cube, numDesignElements, bioAssayIndex, QTIndex);
        	
        }else {
        	//System.out.println("No DataInternal");
        	DataExternal dataExternal =bioDataCube.getDataExternal();
        	if (dataExternal!=null){
        		String uri =dataExternal.getFilenameURI();
        		File uriFile = new File (fileBase+"/"+uri);
        		//System.out.println("The external file " + uriFile.getAbsolutePath());
        		if (!uriFile.exists()){
        			System.out.println("The external file " +uriFile.getName()+" does not exist");
        			return;
        		}
        		
        		//String [][][] cube =null;
        		String orderName = bioDataCube.getNameOrder();
	            if (orderName==null){
	            	orderName="DQB";
	            }
		        //System.out.println("BioDataCube name order is "+ orderName);
		        //System.out.println("ModelClassName " +bioDataCube.getModelClassName());
		        
		        BufferedReader br = null;
	            try {
	                 br = new BufferedReader(new FileReader(uriFile));
	            } catch(java.io.FileNotFoundException fnfe) {
	            	System.out.println("Unable to open file " + uriFile);
	            }
        
	            String [][][] cube = parseDataCube(orderName, bioDataCube, numBioAssays, numQuantitationTypes, numDesignElements, br);
	            //System.out.println ("cube.length " +cube.length +" "+cube[0].length +" "+cube[0][0].length );
	        	
	        	readDataCube(orderName, cube, numDesignElements, bioAssayIndex, QTIndex);
	        	//System.out.println("bioAssayIndex here is "+bioAssayIndex);
				//System.out.println("Finished bioAssayIndex "+ bioAssayIndex);

        	}
        }
        
   }
   
   
   /* To read data into a cube from external file
    * @param orderName: the order for BioAssay, DesignElement and QuantitationType
    * @param bioDataCube: BioDataCube
    * @param numBioAssays: the number of BioAssays
    * @param numQuantitationTypes:the number of QuantitationTypes
    * @param numDesignElements: the number of DesignElements
    * @param br: read data handler
    * @return: a three dimesion array for BioAssay, DesignElement and QuantitationType
    */	 	   
   private static String[][][] parseDataCube(String orderName, BioDataCube bioDataCube, int numBioAssays, int numQuantitationTypes, 
   		int numDesignElements, BufferedReader br) {
      try {
         int dim1=0;
         int dim2=0;
         int dim3=0;

         if(orderName.equals("BDQ")) {
            dim1 = numBioAssays;
            dim2 = numDesignElements;
            dim3 = numQuantitationTypes;
         } else if(orderName.equals("BQD")) {
            dim1 = numBioAssays;
            dim2 = numQuantitationTypes;
            dim3 = numDesignElements;
         } else if(orderName.equals("DBQ")) {
            dim1 = numDesignElements;
            dim2 = numBioAssays;
            dim3 = numQuantitationTypes;
         } else if(orderName.equals("DQB")) {
            dim1 = numDesignElements;
            dim2 = numQuantitationTypes;
            dim3 = numBioAssays;
         } else if(orderName.equals("QBD")) {
            dim1 = numQuantitationTypes;
            dim2 = numBioAssays;
            dim3 = numDesignElements;
         } else if(orderName.equals("QDB")) {
            dim1 = numQuantitationTypes;
            dim2 = numDesignElements;
            dim3 = numBioAssays;
         } 

         String[][][] cube = readExternalDataCubeFromFile(dim1, dim2, dim3, br);
         return cube;
      } catch(java.io.IOException ioe) {
         System.out.println("An error occurred while reading the data.");
         return null;
      } finally {
         if(br != null) {
            try {
               br.close();
            } catch(java.io.IOException x) {}
         }
      }
   }

   
   /* To read data into a cube from external file
    * @param dim1: dimesion for either BioAssay, or DesignElement or QuantitationType
    * @param dim2: dimesion for either BioAssay, or DesignElement or QuantitationType
    * @param dim3: dimesion for either BioAssay, or DesignElement or QuantitationType
    * @param br: read data handler
    * @return: a three dimesion array for BioAssay, DesignElement and QuantitationType
    */	 	   
   private static String[][][] readExternalDataCubeFromFile(int dim1,int dim2,int dim3,BufferedReader br)
					          throws IOException {

	      String[][][] ret = new String[dim1][dim2][dim3];

	      String line = "";
	      String[] tmp = null;
	      String[] dtmp = null;
	      
	      int count =0;
	      
	      //while ((line=br.readLine()) != null) {
	      for(int i = 0; i < dim1; i++) {
	         for(int j = 0; j < dim2; j++) {
	            if ((line = br.readLine()) !=null){
	            //System.out.println("Line " +line);
	            // To make up for the added empty line between the first
	            // dimension in the write method.
	            /*if(line.length() < 1) {
	               line = br.readLine();
	            }*/

	            //Assume tab is the delimiter.
	            tmp = line.split("\t");
	            //tmp = line.split("\\s");
	            //System.out.println("temp length " +tmp.length);
	            dtmp = new String[tmp.length];
	            
	            /*if (j<3){
	            	System.out.println("tmp");
	            	print (tmp);
	            	
	            }*/
	            for(int k = 0; k < tmp.length; k++) {
	               dtmp[k] = tmp[k];
	            }
	            /*if (j<3){
	            	System.out.println("dtmp");
	            	print (dtmp);
	            	
	            }*/
	            // Check loaded dimension and trow exception if not the
	            // same as the input parameter.
	            if(dtmp.length == dim3) {
	            	/*if (j<3){
	            		System.out.println("dtmp.length == dim3");			            	
		            }*/
	            	
	                ret[i][j] = dtmp;
	            } else if(dtmp.length == dim2 * dim3) {
	            	/*if (j<3){
	            		System.out.println("dtmp.length == dim2 * dim3");			            	
		            }*/
	            	
	                for(int k = 0; k < dim2; k++) {
	                  for(int l = 0; l < dim3; l++) {
	                     ret[i][k][l] = dtmp[k * dim3 + l];
	                  }
	               }

	               // force return of j-loop, both dim 2 and 3 was on the same line
	               j = dim2;
	            } else {
	               throw new ArrayIndexOutOfBoundsException(dtmp.length);
	            }

	            } else {
	            	break;
	            }
	         }
	      }
	  // }
	      return ret;
   }
   
   
   /* To read data into a cube from external file
    * @param orderName: the order for BioAssay, DesignElement and QuantitationType
    * @param cube: BioDataCube dat array
    * @param numDesignElements: the number of DesignElements
    * @param bioAssayIndex: the selected BioAssay
    * @param quantitationTypeIndex: the selected QuantitationType
    */	 
   private static void readDataCube(String orderName, String[][][] cube, int numDesignElements, 
   		int bioAssayIndex, int quantitationTypeIndex) {
   		/*int i = bioAssayIndex;
   		int k = quantitationTypeIndex;*/
   		
   		if(orderName.equals("BDQ")) {
	         int i = bioAssayIndex;
	         int k = quantitationTypeIndex;
	         for(int j = 0; j < numDesignElements; j++) {	         	
	         	data[j][i] = cube[i][j][k];
	         }
      } else if(orderName.equals("BQD")) {
	         int i = bioAssayIndex;
	         int j = quantitationTypeIndex;
	         for(int k = 0; k < numDesignElements; k++) {
	      	 //for(int j = 0; j < numDesignElements; j++) {
	            data[k][i] = cube[i][j][k];
	         	//data[j][i] = cube[0][k][j];
	         }

      } else if(orderName.equals("DBQ")) {
	         int j = bioAssayIndex;
	         int k = quantitationTypeIndex;
	         for(int i = 0; i < numDesignElements; i++) {
	         //for(int j = 0; j < numDesignElements; j++) {
	            data[i][j] = cube[i][j][k];
	         	//data[j][i] = cube[j][i-1][k];
	         }
      } else if(orderName.equals("DQB")) {
	         int j = quantitationTypeIndex;
	         int k = bioAssayIndex;
	         for(int i = 0; i < numDesignElements; i++) {
	         //for(int j = 0; j < numDesignElements; j++) {		        
	         	//data[j][i] = cube[j][k][i-1];
	         	data[i][k] = cube[i][j][k];
	         }

      } else if(orderName.equals("QBD")) {
	         int j = bioAssayIndex;
	         int i = quantitationTypeIndex;
	         for(int k = 0; k < numDesignElements; k++) {
	         //for(int j = 0; j < numDesignElements; j++) {	
	         	//data[j][i] = cube[k][0][j];
	            data[k][j] = cube[i][j][k];
	         }
      } else if(orderName.equals("QDB")) {
	         int k = bioAssayIndex;
	         int i = quantitationTypeIndex;
	         for(int j = 0; j < numDesignElements; j++) {
	         //for(int j = 0; j < numDesignElements; j++) {
	         	//data[j][i] = cube[k][j][0];
	            data[j][k] = cube[i][j][k];
	         }
      	}
   }

   
   /* To return data array after adding DesignElement
    * @param designElementList: list of DesignElement
    * @return: the data array with DesignElement
    */
   public static String [][] getData (List designElementList){
   		DesignElement designElement =null;
   		//int arrayColumn = data[0].length;
   		for (int i=0; i<data.length;i++){
   			for (int j=data[0].length -1; j>0;j--){
   				data[i][j]= data[i][j-1];
   			}
   			designElement = (DesignElement)designElementList.get(i);
   			data[i][0]= (String) designElement.getIdentifier();
   		}
   		return data;
   }

   
   
   /* To save two dimensional data array into a file
    * @param name: two dimensional data array
    * @param cubeFileName: the file name
    */
   public static void saveArray(String [][] name, String cubeFileName)throws IOException {
 	   //String cubeFileName = "C:\\DWD\\DWDdata\\CubeFileName.txt";
 	   PrintWriter writer = new PrintWriter(new FileWriter (cubeFileName, false));
 	   System.out.println ("In saveArray " +name.length +" " +name[0].length);
		/* Print out the title: numValues1 for 1s and numValues2 for 2s */
		for (int i=0; i<name.length;i++) {
			for (int j=0; j<name[0].length;j++){
				writer.print (name[i][j] +"\t");
			}
			writer.println ();
		}
		writer.close();
   }

   /* To save three dimensional data array into a file
    * @param name: three dimensional data array
    * @param cubeFileName: the file name
    */
   public static void saveCubeArray(String [][][] name, String cubeFileName)throws IOException {
 	   //String cubeFileName = "C:\\DWD\\DWDdata\\CubeFileName.txt";
 	   PrintWriter writer = new PrintWriter(new FileWriter (cubeFileName, false));
 	   System.out.println ("In saveCubeArray " +name.length +" " +name[0].length+" " +name[0][0].length);
		/* Print out the title: numValues1 for 1s and numValues2 for 2s */
		for (int i=0; i<name.length;i++) {
			for (int j=0; j<name[0].length;j++){
				for (int k=0; k<name[0][0].length;k++){
					writer.print (name[i][j][k] +"\t");
				}
				
			}
			writer.println ();
		}
		writer.close();
   }

 
}