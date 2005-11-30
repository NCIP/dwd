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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.swing.*;

import edu.unc.LCCC.caBIG.DWD.javaCode.util.GUIDesignHelpFunctions;
import edu.unc.LCCC.caBIG.DWD.javaCode.SVD.Matrix;
import edu.unc.LCCC.caBIG.DWD.javaCode.SVD.SingularValueDecompositionEZ;
import edu.unc.LCCC.caBIG.DWD.javaCode.processfile.ListingTable;
import edu.unc.LCCC.caBIG.DWD.javaCode.processfile.OpenFileDir;
import edu.unc.LCCC.caBIG.DWD.javaCode.processfile.PopUpError;
import edu.unc.LCCC.caBIG.DWD.javaCode.processfile.PopUpTask;
import edu.unc.LCCC.caBIG.DWD.javaCode.processfile.ReadTextFile;
import edu.unc.LCCC.caBIG.DWD.javaCode.processfile.UpLoadFile;

/*************************************************************
*	DisplayVisualization.java
*	
*	It is used to enter all paramters for graphs and call SetUpPlotWindow class
*
*	Version: v1.1
*	Author: Everett Zhou
*	Date: 2/21/2005
*		5/30/2005
*
*************************************************************/


public class VisualizationInput extends JFrame implements ActionListener,Runnable {
	/* window container */
	private Container content;
    private GridBagLayout gbLayout;
    private GridBagConstraints gbc;
    
    /* The action value */
    private String myAction;
    
    /*Input the original file*/
    public static JTextField originalFilePath;    
    private JButton load3;
    private JButton where1;

    public static JTextField DWDVecFilePath;
    private JButton load4;
    
    /*Input the DWD merged file*/
    public static  JTextField DWDOutputFilePath;
    private JButton load5;
    private JButton where2;
    private JButton view;
    
    private JTextField row;
    private JTextField col;
    
    private String spaceLine = "<html>" + "<p>" +"<p>" + "</html>";
    
    private JButton visualization;
	
    private Thread eventThread;
    
    /*These parameters are public, because they are called by DisplayVisualizationTask*/ 
    public static int nOnes; 
	public static int nTwos;
	public static double originalAdjustProj[][];
	public static double newOriginalAdjustProj[][]; 
	public static double DWDAdjustProj1[][];
	public static double DWDAdjustProj2[][];
	public static double DWDAdjustProj3[][];
	public static double kde1[][];
	public static double kde2[][];
	public static double kde3[][];
	public static double kde4[][];
	public static double kde5[][];   
    
    public VisualizationInput() {

        setTitle("Do Visulization Diagnostics");


	/* set up the size and location of window */
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screenWidth = d.width;
 
        float w1 = (float) ((float)screenWidth / 1.2);        
        Float w2 = new Float (w1);       
        int w3 = w2.intValue();

        float h1 = (float) ((float)screenHeight / 1.3);        
        Float h2 = new Float (h1);       
        int h3 = h2.intValue();
 
        pack();
        setSize(w3,h3);
        setLocation(screenWidth / 7, screenHeight / 12);
       
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
       /* Set up all inputs*/
  	   ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "<html> <FONT COLOR=RED SIZE =4>Input Parameters</FONT><html>", 2,0,4,1);
  	   ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 1, 40, 1);
       
       	/* For original file*/
  	   ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "Original File Before DWD", 2,2,4,1);           
        originalFilePath = ((GUIDesignHelpFunctions) GDHF).addTextField(content, gbc, 20,true, 7,2,4,1);
        originalFilePath.setText("C:\\DWD\\DWDdata\\DWD_Input.txt");
        originalFilePath.setEditable(true);

        load3 = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "Load File", 13,2,4,1);
        load3.setActionCommand("LOAD3");
        load3.addActionListener(this);
         
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 5, 40, 1);
         
         /*Add the hint for the file*/
        where1 = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "Where to Find",2,8,5,1);
        where1.setActionCommand("WHERE1");
        where1.addActionListener(this);
         
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 9, 40, 1);
        
		/*DWD vector file*/
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "DWD Vector File", 2,11,4,1);           
        DWDVecFilePath = ((GUIDesignHelpFunctions) GDHF).addTextField(content, gbc, 20,true, 7,11,4,1);
        DWDVecFilePath.setText("C:\\DWD\\DWDdata\\DWD_Vec.txt");
        DWDVecFilePath.setEditable(true);

        load4 = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "Load File", 13,11,4,1);
        load4.setActionCommand("LOAD4");
        load4.addActionListener(this);
         
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 13, 40, 1);
         
         /*Add the hint for the file*/
        where1 = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "Where to Find",2,15,5,1);
        where1.setActionCommand("WHERE1");
        where1.addActionListener(this);
         
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 16, 40, 1);
        
        /*Output file*/
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "DWD Output File", 2,17,4,1);        
        DWDOutputFilePath = ((GUIDesignHelpFunctions) GDHF).addTextField(content, gbc, 20,true, 7,17,4,1);
        DWDOutputFilePath.setText("C:\\DWD\\DWDdata\\DWD_Std_Output.txt");
        DWDOutputFilePath.setEditable(true);

        load5 = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "Load File", 13,17,4,1);
        load5.setActionCommand("LOAD5");
        load5.addActionListener(this);
         
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 18, 40, 1);
         
        /*Add the hint for the file*/
        where2 = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "Where to Find",2,20,5,1);
        where2.setActionCommand("WHERE2");
        where2.addActionListener(this);
 
        view = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "View File",13,20,5,1);
        view.setActionCommand("VIEW");
        view.addActionListener(this);
        
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 21, 40, 1);
        
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "Data Row in DWD Output File Starts at", 2,22,4,1);  
        row = ((GUIDesignHelpFunctions) GDHF).addTextField(content, gbc, 10,true, 7,22,2,1);
  		row.setText("3");
  		row.setEditable(true);
  		((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 24, 40, 1);

  		((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "Data Column in DWD Output File Starts at", 2,25,4,1);  
        col = ((GUIDesignHelpFunctions) GDHF).addTextField(content, gbc, 10,true, 7,25,2,1);
  		col.setText("3");
  		col.setEditable(true);
  		((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 27, 40, 1);
  		((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 29, 40, 1);
  		
  		
        /* Task For Visualization */
        visualization = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "Visualize and Diagnose",8,31,5,1);
        visualization.setActionCommand("VIS");
        visualization.addActionListener(this);
    }    
        

    /* Select one of three actions */
    public void actionPerformed (ActionEvent ae)  {      
       myAction = ae.getActionCommand();
       
       	/* To load files */
       	if (myAction.equalsIgnoreCase("LOAD3") ) {
     		JFrame jframe = new OpenFileDir("Open File Directory","LOAD3");
       		jframe.show(); 
       	} 
      	else if (myAction.equalsIgnoreCase("LOAD4") ) {
     		JFrame jframe = new OpenFileDir("Open File Directory","LOAD4");
       		jframe.show();  
      	} 
      	else if (myAction.equalsIgnoreCase("LOAD5") ) {
     		JFrame jframe = new OpenFileDir("Open File Directory","LOAD5");
       		jframe.show();  
      	} 
      	else if (myAction.equalsIgnoreCase("WHERE1") ) {
       		JFrame jfr1 = new PopUpTask ("<html> This file is an intermediate one generated from DWD merge." + 
       				"<p> Should be in C:\\DWD\\DWDdata folder" +"<p>" + "</html>");
       		jfr1.show();
      	} 
      	else if (myAction.equalsIgnoreCase("WHERE2") ) {
       		JFrame jfr2 = new PopUpTask ("<html> This file is the final result generated from DWD merge." + 
       				"<p> Find that directory you saved before" +"<p>" + "</html>");
       		jfr2.show();
      	} else if (myAction.equalsIgnoreCase("VIEW") ) {
      		String DWDOutputFile = DWDOutputFilePath.getText();	
       		String [] columnName = UpLoadFile.buildHeader (DWDOutputFile);        
       		String [][] cells = UpLoadFile.buildCells (DWDOutputFile);	 
            JFrame listingTable = new ListingTable (DWDOutputFile,cells,columnName);
            listingTable.show();  
       	}
       	else if (myAction.equalsIgnoreCase("VIS") ){

       		eventThread = new Thread (this);	
    		eventThread.start();	
    		
    		JFrame displayVisualizationTask = new DisplayVisualizationTask();
    		displayVisualizationTask.show();
    		
		} /* End of Vis action*/
    }
 
    
    /* To display the content in the pop up task window, a seperate thread must be used
     * Thanks to camickr at http://forum.java.sun.com/thread.jspa?forumID=57&threadID=437592
     * 
     * @see java.lang.Runnable#run()
     */
	public void run(){	
   		final JFrame jfr1 = new PopUpTask ("<html>The program is <FONT COLOR=RED SIZE =4> <p><p>Running</FONT><p><p> it may take up to 60 minutes</html>");

   		SwingUtilities.invokeLater(new Runnable(){				
			public void run(){
	       		jfr1.show(); 
				}	
		});			
		
		// do long running task	
 		String origTextFile = originalFilePath.getText();
 		String DWDvecTextFile = "C:\\DWD\\DWDdata\\DWD_Vec.txt";
 		
 		File fDWDvec = new File (DWDvecTextFile); 		
 		if (!fDWDvec.exists ()) {
           	JFrame jf = new PopUpError ("<html><p>The file <FONT COLOR=RED SIZE =4>" +DWDvecTextFile +"</FONT>  " +
           			"<p> does not exist. <p> It is generated from DWD merge. <p> Copy it back to C:\\DWD\\DWDdata\\ " , "Exit");
     		jf.show();
 		}
 		
 		String kdeInputFile = "C:\\DWD\\DWDdata\\DWD_KDE_Input.txt";
 		String kdeOutputFile = "C:\\DWD\\DWDdata\\DWD_KDE_Output.txt";
    	
 		int ones[] = ReadTextFile.findOnes (origTextFile);
		
 		nOnes = ones[0];
 		nTwos = ones[1];
 		
 		String DWDOutputFile = DWDOutputFilePath.getText();
 		String temp;
		temp = row.getText().trim();
		int rowAt = Integer.valueOf(temp).intValue()-1;
		
		temp = col.getText().trim();   			
		int colAt = Integer.valueOf(temp).intValue()-1;
			
 		SingularValueDecompositionEZ SVDEZ = new SingularValueDecompositionEZ ();
 
 /*--------------------------------------------------*/		
 		/* For Raw Data - Raw PC View*/
 		double originalData [][] = SVDEZ.getData(origTextFile,1,0);
 		//System.out.println ("originalData " +originalData.length + " " +originalData[0].length);

 		if (originalData.length <4){
           	JFrame jf = new PopUpError ("<html><p>The merged file does not have enough data <p>for visialization </html>",
           			"Fix the File(s) and Terminate the Program");
     		jf.show(); 		
 			return;
 		}
 		double originalCenter [][] = SVDEZ.getCenteredData (originalData);
  		double originalRedEig [][] = SVDEZ.getReduecedEig (originalCenter,4);
  	   	
  		double originalProj [][] = SVDEZ.getProj(originalRedEig, originalCenter);

  		originalAdjustProj = SVDEZ.computeAdjustProj (originalProj); 
  		   		
  		/*Create kde data file*/
  		try {	
			excuteKDE (originalAdjustProj);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		kde1 = getKDE (kdeOutputFile);
		
  	//	SetUpPlotWindow setUpPlotWindow1 = new SetUpPlotWindow ("Raw Data - Raw PC View", 1, originalAdjustProj,kde1, nOnes, nTwos); 
  		//System.out.println ("Finishe Visualization");
 		//System.out.println ("originalRedEig " +originalRedEig.length + " " +originalRedEig[0].length); 		 		
 		//SVDEZ.printArray("originalRedEig ",originalRedEig);
		/*System.out.println ("originalAdjustProj " +originalAdjustProj.length + " " +originalAdjustProj[0].length);
		String originalAdjustProjFile = "C:\\DWD\\DWDdata\\originalAdjustProj.txt";
		try {
			saveArrayForFile (originalAdjustProj, originalAdjustProjFile);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
	       int j=originalAdjustProj[0].length-1;
        	
	       for (int i=0; i<originalAdjustProj.length; i++){
	       		System.out.print(originalAdjustProj [i][j] +"\t");
        	}
	       System.out.println();*/
	       
		//SVDEZ.printArray("originalAdjustProj in Vis",originalAdjustProj);

		/*System.out.println ("kde1 " +kde1.length + " " +kde1[0].length);
		String kde1File = "C:\\DWD\\DWDdata\\kde1.txt";
		try {
			saveArrayForFile (kde1, kde1File);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
/*--------------------------------------------------*/	 
 		/* For Raw Data - Raw PC & DWD View*/
 		double dwdVec [][]= SVDEZ.getData(DWDvecTextFile,0,0);
 		//System.out.println ("dwdVec " +dwdVec.length + " " +dwdVec[0].length);
 		
  		double newOriginalRedEig [][] = replaceEigenvector (originalRedEig,dwdVec);
 		//SVDEZ.printArray("newOriginalRedEig ",newOriginalRedEig );
 		//System.out.println ("newOriginalRedEig " +newOriginalRedEig.length + " " +newOriginalRedEig[0].length);
 		
 		
  		double newOriginalProj [][] = SVDEZ.getProj(newOriginalRedEig, originalCenter);

  		newOriginalAdjustProj = SVDEZ.computeAdjustProj (newOriginalProj); 	
  		
  		/*Create kde data file*/
  		try {
			excuteKDE (newOriginalAdjustProj);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		kde2 = getKDE (kdeOutputFile);
  		
		//SetUpPlotWindow setUpPlotWindow2 = new SetUpPlotWindow ("Raw Data - Raw PC & DWD View", 2, newOriginalAdjustProj, kde2,nOnes, nTwos); 

 	/*--------------------------------------------------*/	 
 		/* Adj. Data - Raw PC & DWD View*/
 		double DWDData [][] = SVDEZ.getData(DWDOutputFile,rowAt,colAt);
 		//System.out.println ("DWDData " +DWDData.length + " " +DWDData[0].length);

 		double DWDCenter [][] = SVDEZ.getCenteredData (DWDData);
 		//System.out.println ("DWDCenter " +DWDCenter.length + " " +DWDCenter[0].length);

		double DWDProj1 [][] = SVDEZ.getProj(newOriginalRedEig, DWDCenter);

  		DWDAdjustProj1 = SVDEZ.computeAdjustProj (DWDProj1); 	  		
		
  		/*Create kde data file*/
  		try {	
			excuteKDE (DWDAdjustProj1);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		kde3 = getKDE (kdeOutputFile);
		
    	//SetUpPlotWindow setUpPlotWindow3 = new SetUpPlotWindow ("Adj. Data - Raw PC & DWD View", 3, DWDAdjustProj1, kde3, nOnes, nTwos); 
  		//System.out.println ("Finishe Visualization");
 		
 	/*--------------------------------------------------*/	 
 		/* Adj. Data - Raw PC View*/
		double DWDProj2 [][] = SVDEZ.getProj(originalRedEig, DWDCenter);

 		DWDAdjustProj2 = SVDEZ.computeAdjustProj (DWDProj2); 	  		
		
  		/*Create kde data file*/
  		try {	
			excuteKDE (DWDAdjustProj2);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		kde4 = getKDE (kdeOutputFile);
		
		//SetUpPlotWindow setUpPlotWindow4 = new SetUpPlotWindow ("Adj. Data - Raw PC View", 4, DWDAdjustProj2, kde4, nOnes, nTwos); 
		
  				
 	/*--------------------------------------------------*/	 
 		/* Adj. Data - Adj. PC View*/
 
  		double DWDRedEig [][] = SVDEZ.getReduecedEig (DWDCenter,4);  		
		double DWDProj3 [][] = SVDEZ.getProj(DWDRedEig, DWDCenter);

  		DWDAdjustProj3 = SVDEZ.computeAdjustProj (DWDProj3); 	  		
		
  		/*Create kde data file*/
  		try {	
			excuteKDE (DWDAdjustProj3);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		kde5 = getKDE (kdeOutputFile);
		
    	//SetUpPlotWindow setUpPlotWindow5 = new SetUpPlotWindow ("Adj. Data - Adj. PC View", 5, DWDAdjustProj3, kde5, nOnes, nTwos); 
  		System.out.println ("Finished Computation of Visualization Data");
 
  		
 		/* Report the task is done*/
		SwingUtilities.invokeLater(new Runnable(){		
			public void run(){	
				jfr1.dispose();
	    		JFrame jfr2 = new PopUpTask ("<html>Finished Computation of<FONT COLOR=RED SIZE =4> <p><p> Visilization of Data</FONT>" +
	    				"<p> You can view each plot now.</html>");
	     		jfr2.show(); 	
			}		
		});	
		
		
		/*delete two kde related files*/
		File fKDEinput = new File (kdeInputFile);
		File fKDEoutput = new File (kdeOutputFile);
				
		if (fKDEinput.exists()||fKDEoutput.exists() ){
			fKDEinput.delete();
			fKDEoutput.delete();
		}
		
	}	/* End of run*/
	

  	/*
     * call kdeEZ to execute the KDE algorithm.
     * 
     * @param originalMatrix: original matrix
     * 
     */
	
	public static void excuteKDE (double originalMatrix [][])throws IOException {
    	Runtime rt = Runtime.getRuntime();   		
     	Process proc;
     	
		/* save the arry to a text file for KDE*/
  		try {
  			saveArrayForKDE (originalMatrix);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/* execute kde algorithm*/
     	try {
     		proc = rt.exec("C:\\DWD\\lib\\cCode\\kdeEZ.exe");
     		InputStream results = proc.getInputStream();
     		byte[] b = new byte[1024];
     		while (true) {
     		   int n = results.read(b);
     		  //System.out.println (n);
     		   if (n == -1)
     		       break;
     		   // use output data
     		}
     	 }	catch (Exception e) {
     		System.out.println(e); 
	     }  		
	}/* End of excuteKDE*/

 	/*
     * Save a matrix into a text file.
     * 
     * @param originalMatrix: original matrix
     * @param fileName: file name to be saved
     * 
     */	
  	public static void saveArrayForFile (double originalMatrix [][], String fileName)throws IOException {
  	    
  		PrintWriter writer = new PrintWriter(new FileWriter (fileName, false));		 	

        for (int j=0; j<originalMatrix[0].length; j++){
        	int i;
        	for (i=0; i<originalMatrix.length-1; i++){
        		writer.print(originalMatrix [i][j] +"\t");
        	}
        	
        	writer.print(originalMatrix [i][j]);
        	writer.println();
        }
        writer.close();
        writer.flush();
        
    }/* End of saveArrayForKDE*/ 
	
  	/*
     * Save a matrix into a text file for KDE computation.
     * 
     * @param originalMatrix: original matrix
     * 
     */
  	
  	public static void saveArrayForKDE (double originalMatrix [][])throws IOException {
 	    String adjustProjFile = "C:\\DWD\\DWDdata\\DWD_KDE_Input.txt";
 	    
  		PrintWriter writer = new PrintWriter(new FileWriter (adjustProjFile, false));		 	

        for (int j=0; j<originalMatrix[0].length; j++){
        	int i;
        	for (i=0; i<originalMatrix.length-1; i++){
        		writer.print(originalMatrix [i][j] +"\t");
        	}
        	
        	writer.print(originalMatrix [i][j]);
        	writer.println();
        }
        writer.close();
        writer.flush();
        
    }/* End of saveArrayForKDE*/ 
  
  	
  	/*
     * get the array from a text file of KDE.
     * 
     * @param kdeOutputFile: the output file from KDE algorithm
     * @return: a two dimension array
     * 
     */
  	
  	public static double [][] getKDE (String kdeOutputFile) {
  		double [][] kde11 = ReadTextFile.readDWDInputOutputToArrayArray(kdeOutputFile,0,0);
		Matrix temp = new Matrix (kde11);
		Matrix TSSVD = temp.transpose();
		
		double [][] kde12 = TSSVD.getArray();
        return kde12;
        
    }/* End of getKDE*/ 


 
	   /* replace the last column of originalEigenvec by dwdvec 
	    * @param originalEigenvec: the original Eigenvec with nx4 size
	    * @param dwdvec: DWD vector with nx1 size
	    * @return: new Eigenvector with last column replaced by DWD vector
	    */
	    public double [][] replaceEigenvector (double [][] originalEigenvec, double [][] dwdvec) {
	        double newEigenvec [][] = new double [originalEigenvec.length][originalEigenvec[0].length];
	        for (int i=0; i<originalEigenvec.length;i++){
	        	for (int j=0; j<originalEigenvec[0].length-1;j++){
	        		newEigenvec[i][j] = originalEigenvec[i][j];
	        	}
	        }
	        
	        for (int i=0; i<originalEigenvec.length;i++){
	        	newEigenvec[i][3] = dwdvec [i][0];
	        }
	        return newEigenvec;
	    }
    
}  