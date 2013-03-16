/************************************************************* 
 *	DWD (Distance Weighted Discrimination)
 *	
 *	This software is developed by LCCC and Statistics at UNC
 *	It is supported by caBIG from NCI and is Open source 
 *
 *************************************************************/

package edu.unc.LCCC.caBIG.DWD.javaCode.ImportMAGEMLFiles;

import java.io.* ;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import org.biomage.BioAssayData.BioAssayData_package;
import org.biomage.Common.MAGEJava;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


import edu.unc.LCCC.caBIG.DWD.javaCode.util.GUIDesignHelpFunctions;
import edu.unc.LCCC.caBIG.DWD.javaCode.processfile.*;


/************************************************************* 
*	UpLoadMAGEMLFile.java
*
*	Implement the GUI to retrieve all parameters for two input files.
*	Take the combined file as input and pass it to BatchAdjustSM.exe
*
*	Version: v1.0
*	Author: Everett Zhou
*	Date: 11/16/2005
*
*************************************************************/
public class UpLoadMAGEMLFile extends JFrame implements ActionListener {

    /*Set up window */
	private Container content;
    private GridBagLayout gbLayout;
    private GridBagConstraints gbc;
    /* The action value */
    private static String myAction;
    
    /*Value for the first file*/
    public static JTextField fileName1;
    public static JTextField filePath1;
    private JButton load1;
 

    /*Value for the second file*/
    public static JTextField fileName2;
    public static JTextField filePath2;
    private JButton load2;
    
    /*Dwd type*/
    private JList jDWDType;
    private String DWDTypeLabel [] ={"Standardized DWD (Default)", "Non-Standardized DWD"};
    public static int DWDType=1; 
    
    private JList jMeanAdjustType;
    private String DWDMeanAdjustLabel [] ={"Centered at 0 (Default)", "Centered at the First Mean", "Centered at the Second Mean"};
    public static int DWDMeanAdjust=0;    
    
    /* Store the value of DWD Mean Adjustmeant*/
    private String meanAdjustTypeFile = "C:\\DWD\\DWDdata\\DWD_MeanAdjustType.txt";
    
    public static JTextField outputfile1, outputfile2;
    
    private JButton save1, save2;
    
    /*Save file indicator or not*/
    private JList jSaveFileIndicator;
    private String SaveFileIndicatorLabel [] ={"Yes (Default)", "No"};
    public static String SaveFileIndicator="Yes";   
    
    /*Add task buttons*/
    private JButton dwd;
 
    private String spaceLine = "<html>" + "<p>" +"<p>" + "</html>";
    private static StringTokenizer st;    
    
    public UpLoadMAGEMLFile() {

        setTitle("DWD Merge MAGE-ML Format Data");

	/* set up the size and location of window */
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screenWidth = d.width;
        //System.out.println (screenWidth + " "+screenHeight);
        
        float w1 = (float) ((float)screenWidth / 1.5);        
        Float w2 = new Float (w1);       
        int w3 = w2.intValue();

        float h1 = (float) ((float)screenHeight / 1.4);        
        Float h2 = new Float (h1);       
        int h3 = h2.intValue();
        
        //System.out.println (w3 + " "+h3);
        if (w3<= 682) { 
        	w3=682;
        }
        if (h3<= 614) { 
        	h3=614;
        }
        pack();
        setSize(w3,h3);
        //System.out.println (w3 + " "+h3);
        setLocation(screenWidth / 20, screenHeight / 25);

       content = getContentPane();
       gbLayout = new  GridBagLayout();
       content.setLayout( gbLayout);
       gbc = new GridBagConstraints();

       gbc.weightx = 1;
       gbc.weighty = 1;
       gbc.fill = GridBagConstraints.NONE;
       //gbc.anchor = GridBagConstraints.CENTER;
       gbc.anchor = GridBagConstraints.CENTER;

       gbc.weightx = 1;
       gbc.weighty = 0;
       
       
 //---------------------------------------------------------
 /* For the First File */
  	    JFrame GDHF = new GUIDesignHelpFunctions();
  	    
  	  ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "<html><FONT COLOR=RED SIZE =4>First File</FONT><p></html>", 7,0,4,1);
        //addLabel(spaceLine, 0, 2, 40, 1);
        
  	  	((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "File Out Indicator", 2,3,4,1);
        fileName1 = ((GUIDesignHelpFunctions) GDHF).addTextField(content, gbc, 10,true, 7,3,4,1);
        fileName1.setText("Ex1");
    	fileName1.setEditable(true);
        
    	((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "File Path", 2,5,4,1);        
        filePath1 = ((GUIDesignHelpFunctions) GDHF).addTextField(content, gbc, 25,true, 7,5,4,1);
 		filePath1.setText("C:/DWD/DWDdata/Human_22K_expression_22k_MAGEML.xml");
 		filePath1.setEditable(true);

        load1 = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "Load File", 13,5,4,1);
        load1.setActionCommand("LOAD6");
        load1.addActionListener(this);        
		((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine,0, 8,40, 1);
        
        /* For Second File */
		((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "<html><FONT COLOR=RED SIZE =4>Second File</FONT><p></html>", 7,10,4,1);
        //addLabel(spaceLine,0, 20,20, 1);
        
		((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "File Out Indicator", 2,12,4,1);
        fileName2 = ((GUIDesignHelpFunctions) GDHF).addTextField(content, gbc, 10,true, 7,12,4,1);
        fileName2.setText("Ex2");
        fileName2.setEditable(true);
     
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "File Path", 2,14,4,1);        
        filePath2 = ((GUIDesignHelpFunctions) GDHF).addTextField(content, gbc, 25,true, 7,14,4,1);
 		filePath2.setText("C:/DWD/DWDdata/MPI_ColVIB_vs_LerVIB_1.xml");
 		filePath2.setEditable(true);
 
        load2 = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "Load File", 13,14,4,1);
        load2.setActionCommand("LOAD7");
        load2.addActionListener(this);		
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 18, 40, 1);
		
        /*DWD type*/
		jDWDType = new JList (DWDTypeLabel);
		jDWDType.setSelectedIndex(0);
		jDWDType.setVisibleRowCount(2);
		jDWDType.addListSelectionListener(new DWDTypeValueReporter());
        JScrollPane listPane1 = new JScrollPane (jDWDType);
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "DWD Type", 2,20,4,1);
        ((GUIDesignHelpFunctions) GDHF).addComponent(content, gbc, listPane1, 7,20,5,1);
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 22, 40, 1);
        
        /*DWD Mean Adjust type*/
        jMeanAdjustType = new JList (DWDMeanAdjustLabel);
        jMeanAdjustType.setSelectedIndex(0);
        jMeanAdjustType.setVisibleRowCount(3);
        jMeanAdjustType.addListSelectionListener(new DWDMeanAdjustTypeValueReporter());
        JScrollPane listPane2 = new JScrollPane (jMeanAdjustType);        
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "Mean Adjustment Type", 2,24,4,1);
        ((GUIDesignHelpFunctions) GDHF).addComponent(content, gbc, listPane2, 7,24,5,1); 
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 25, 40, 1);
        
        /* save the adjusted data into text file*/
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "Output Text File Path", 2,27,4,1);
        outputfile1 = ((GUIDesignHelpFunctions) GDHF).addTextField(content, gbc, 25,true, 7,27,4,1);
        outputfile1.setText("C:/DWD/DWDdata/DWD_Std_Output.txt");
        outputfile1.setEditable(true);
 
        save1 = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "Save Output to", 13,27,4,1);
        save1.setActionCommand("SAVE2");
        save1.addActionListener(this);        
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 29, 40, 1);

        /* save the adjusted data into MAGE-ML file*/
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "Output MAGE-ML File Path", 2,31,4,1);
        outputfile2 = ((GUIDesignHelpFunctions) GDHF).addTextField(content, gbc, 25,true, 7,31,4,1);
        outputfile2.setText("C:/DWD/DWDdata/DWD_Std_Output.xml");
        outputfile2.setEditable(true);
 
        save2 = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "Save Output to", 13,31,4,1);
        save2.setActionCommand("SAVE3");
        save2.addActionListener(this);
        
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 32, 40, 1);
	    /*Save file indicator or not*/
        /*DWD Mean Adjust type*/
		jSaveFileIndicator = new JList (SaveFileIndicatorLabel);
		jSaveFileIndicator.setSelectedIndex(0);
		jSaveFileIndicator.setVisibleRowCount(2);
		jSaveFileIndicator.addListSelectionListener(new SaveFileIndicatorReporter());
        JScrollPane listPane3 = new JScrollPane (jSaveFileIndicator);        
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, "Add File Indicator ?", 2,38,4,1);
        ((GUIDesignHelpFunctions) GDHF).addComponent(content, gbc, listPane3, 7,38,5,1); 
        ((GUIDesignHelpFunctions) GDHF).addLabel(content, gbc, spaceLine, 0, 40, 40, 1);
		//addLabel(spaceLine, 0, 47, 40, 1);
		
        /* Add in task buttons*/        
        dwd = ((GUIDesignHelpFunctions) GDHF).addButton(content, gbc, "Merge the files (DWD)", 8,42,3,1);
        dwd.setActionCommand("DWD");
        dwd.addActionListener(this);
        
    } // end of constructor    


    /*
     *  To do all jobs, View files, Load files, DWD, and Bind
     * 
     */
   public void actionPerformed (ActionEvent ae) {      
       myAction = ae.getActionCommand();
       
       /* To load the first file*/
       if (myAction.equalsIgnoreCase("LOAD6") ) {
        // System.out.println ("Do nothing for USP " +myAction);	 
       		JFrame jframe = new OpenFileDir("Open File Directory","LOAD6");
       		jframe.show();  
     	} 
       /* To load the second file*/
       else if (myAction.equalsIgnoreCase("LOAD7") ) {
        // System.out.println ("Do nothing for USP " +myAction);	 
       		JFrame jframe = new OpenFileDir("Open File Directory","LOAD7");
       		jframe.show();  
     	}       
       else if (myAction.equalsIgnoreCase("SAVE2") ) {
        // System.out.println ("Do nothing for USP " +myAction);	 
       		JFrame jframe = new OpenFileDir("Save Output File To","SAVE2");
       		jframe.show();  
     	}    else if (myAction.equalsIgnoreCase("SAVE3") ) {
            // System.out.println ("Do nothing for USP " +myAction);	 
       		JFrame jframe = new OpenFileDir("Save Output File To","SAVE3");
       		jframe.show(); 
     	}	
       	/* To get all parameters and read and save files */
       	else if (myAction.equalsIgnoreCase("DWD") ) {

       		//dwd.setEnabled( false );

		     File findDirc = new File ("C:/DWD/DWDdata");
		     if(!findDirc.exists()){
		       	JFrame jf1 = new PopUpError ("<html><p>There is No Such a  directory <p><p><FONT COLOR=RED SIZE =4> C:\\DWD\\DWDdata</FONT> </html>", 
		       				"Exit and Create the Directory ");
		       	jf1.show(); 
		       	return;
		      }
    		JFrame jfr = new PopUpTask ("<html>The program is <FONT COLOR=RED SIZE =4> <p><p>Running</FONT></html>");
    		//System.out.println ("The program is Running");
     		jfr.show(); 
			
       		/* Temp values for row, column and Id in the first file*/
       		String temp1;       		
       		
			String fName1= fileName1.getText().trim();
      					
			String fPath1 = filePath1.getText().trim(); 
			
			File fp1 = new File (fPath1);
			if (!fp1.exists()){
				jfr.dispose();
	             int response = JOptionPane.showConfirmDialog (null,
	             		fPath1 + " does not exist, please reload !" ,"Confirm Overwrite",
	 	                JOptionPane.CLOSED_OPTION );
	             return;	 	             
			}
					
    		/* Temp values for row, column and Id in the second file*/
			String temp2;

			String fName2 = fileName2.getText().trim();
			       					
			String fPath2 = filePath2.getText().trim(); 

			File fp2 = new File (fPath2);
			if (!fp2.exists()){
				jfr.dispose();
	             int response = JOptionPane.showConfirmDialog (null,
	             		fPath2 + " does not exist, please reload !" ,"Confirm Overwrite",
	 	                JOptionPane.CLOSED_OPTION );
	             return;	 	             
			}
			
			/* output to text file*/
			String fileNameOut1 = outputfile1.getText().trim();
			File fFile1 = new File (fileNameOut1);
	         if (fFile1.exists ()) {
	             int response = JOptionPane.showConfirmDialog (null,
	               "Overwrite existing file "+ fileNameOut1 +" ??" ,"Confirm Overwrite",
	                JOptionPane.OK_CANCEL_OPTION,
	                JOptionPane.QUESTION_MESSAGE);
	             if (response == JOptionPane.CANCEL_OPTION) {
	             	/* Kill the Task Report window and go back to reload the file*/
	             	jfr.dispose();
	    	       	return;	             	
	             }
	         }
	         
	         /* output to text file*/
			String fileNameOut2 = outputfile2.getText().trim();
			File fFile2 = new File (fileNameOut2);
	         if (fFile2.exists ()) {
	             int response = JOptionPane.showConfirmDialog (null,
	               "Overwrite existing file "+ fileNameOut2 +" ??" ,"Confirm Overwrite",
	                JOptionPane.OK_CANCEL_OPTION,
	                JOptionPane.QUESTION_MESSAGE);
	             if (response == JOptionPane.CANCEL_OPTION) {
	             	/* Kill the Task Report window and go back to reload the file*/
	             	jfr.dispose();
	    	       	return;	             	
	             }
	         }
	         
			/* To do all jobs: Load, combine files, call BatchAdjustSM.exe and put the keys back*/
 /*    		Thread FPT = new FileProcessThread (jfr,DWDType,fName1,fPath1,rowAt1,colAt1,IDCol1,
        			fName2 ,fPath2,rowAt2,colAt2, IDCol2, fileNameOut);
*/
	         jfr.dispose();
	         Thread FPT = new FileProcessThread (DWDType,fName1,fPath1,fName2 ,fPath2, fileNameOut1,fileNameOut2);

     		/* Kick it off*/
     		FPT.start();

       	} /* End of DWD Button */
   } /* End of actionPerformed*/
   
    

   /* This is the better way to use another thread 
	 * to consume the results generated from external .exe
	 */
    private class FileProcessThread extends Thread {
    	private String fName1,inputFile1,fName2,inputFile2, fileNameOut1, fileNameOut2;
    	private int DWDType;
    	
    	
 /*   	FileProcessThread (JFrame jfr,int DWDType,String fName1,String fPath1, int rowAt1, int colAt1, int IDCol1,
    			String fName2 ,String fPath2,int rowAt2, int colAt2, int IDCol2, String fileNameOut){
  */  
    	FileProcessThread (int DWDType,String fName1,String inputFile1, 
    			String fName2 ,String inputFile2, String fileNameOut1, String fileNameOut2){
    	
    		
    		this.DWDType= DWDType;
    		this.fName1 = fName1;
    		this.inputFile1 = inputFile1;
    		this.fName2 = fName2;
    		this.inputFile2 = inputFile2;		
    		this.fileNameOut1 = fileNameOut1;
    		this.fileNameOut2 = fileNameOut2;
    	}
    	
    	/* Put keys back and save file */
    	public void run(){
			if (fileNameOut1.equalsIgnoreCase("")){
              	JFrame jf = new PopUpError ("<html><p> <FONT COLOR=RED SIZE =4>Wrong Output File</FONT></html>", "Please Enter the Right Output File and Terminate the Program");
         		jf.show(); 
			} else if (fileNameOut2.equalsIgnoreCase("")){
              	JFrame jf = new PopUpError ("<html><p> <FONT COLOR=RED SIZE =4>Wrong Output File</FONT></html>", "Please Enter the Right Output File and Terminate the Program");
         		jf.show(); 
			} else { /* else 1 */
			/* Combine two data files */
		   		File inFile1 = new File (inputFile1);
		   		String fileBase1 = inFile1.getParent();
		   		
		   		File inFile2 = new File (inputFile2);
		   		String fileBase2 = inFile2.getParent();
		   		
		   		//String outputFile = "C:\\DWD\\DWDdata\\NewArrayDesign_package.xml";
		   		ReadMAGEMLFiles readMAGEMLFiles1=null;
				try {
					readMAGEMLFiles1 = new ReadMAGEMLFiles (fileBase1,inputFile1);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/* get the handler for the first file*/
		   		MAGEJava mageJava1 = readMAGEMLFiles1.mageJava;	 
	  			   		
		   		BioAssayData_package bioAssayDataP1 =mageJava1.getBioAssayData_package();
		   		
		   		List bioAssayDataList1 = new ArrayList();
		   		List derivedBioAssayDataList1 = new ArrayList();
		   		List measuredBioAssayDataList1 = new ArrayList();
				
		   		/* get the three lists*/
		        if (bioAssayDataP1==null){
		        	System.out.println("No BioAssayData_package");
		        	return;
		        	
		        }else {
		        	bioAssayDataList1 = readMAGEMLFiles1.getBioAssayDataList (bioAssayDataP1);	   			   		
		        	derivedBioAssayDataList1 = readMAGEMLFiles1.getDerivedBioAssayDataList (bioAssayDataList1); 
		        	measuredBioAssayDataList1 = readMAGEMLFiles1.getMeasuredBioAssayDataList (bioAssayDataList1);
		        }
	   	
		        
		   		/* get the handler for the second file*/
		        ReadMAGEMLFiles readMAGEMLFiles2=null;
				try {
					readMAGEMLFiles2 = new ReadMAGEMLFiles (fileBase2,inputFile2);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				MAGEJava mageJava2 = readMAGEMLFiles2.mageJava;	 

		   		BioAssayData_package bioAssayDataP2 =mageJava2.getBioAssayData_package();
		   		
		   		List bioAssayDataList2 = new ArrayList();
		   		List derivedBioAssayDataList2 = new ArrayList();
		   		List measuredBioAssayDataList2 = new ArrayList();
				
		   		/* get the three lists*/
		        if (bioAssayDataP2==null){
		        	System.out.println("No BioAssayData_package");
		        	return;
		        	
		        }else {
		        	
		        	bioAssayDataList2 = readMAGEMLFiles2.getBioAssayDataList (bioAssayDataP2);	   			   		
		        	derivedBioAssayDataList2 = readMAGEMLFiles2.getDerivedBioAssayDataList (bioAssayDataList2); 
		        	measuredBioAssayDataList2 = readMAGEMLFiles2.getMeasuredBioAssayDataList (bioAssayDataList2);
		        }

		        /*to DisplayBioAssayData*/
		        JFrame bioAssayDataJframe;
		        if (derivedBioAssayDataList1.size()>0) {
		        	
		        	if (derivedBioAssayDataList2.size()>0){
		        		bioAssayDataJframe = new DisplayBioAssayDataGUI(fileBase1, derivedBioAssayDataList1, fileBase2,derivedBioAssayDataList2);
		        		//jfr.dispose();
		        	} else {		        		
		        		bioAssayDataJframe = new DisplayBioAssayDataGUI(fileBase1, derivedBioAssayDataList1, fileBase2, measuredBioAssayDataList2);
		        		//jfr.dispose();
		        	}
		        }	
			    else {
		        	if (derivedBioAssayDataList2.size()>0){
		        		bioAssayDataJframe = new DisplayBioAssayDataGUI(fileBase1, measuredBioAssayDataList1, fileBase2, derivedBioAssayDataList2);
		        		//jfr.dispose();
		        	} else {
		        		bioAssayDataJframe = new DisplayBioAssayDataGUI(fileBase1, measuredBioAssayDataList1, fileBase2, measuredBioAssayDataList2);
		        		//jfr.dispose();
		        	}		    	
			   
		        	bioAssayDataJframe.show();
			    	
		        }	  		     			     	

	     	}    		
    	}
    }  	/*FileProcessThread*/
    
    
   
    /*	Inner class for JList to set up the value of DWDType
     * 
     */
    private class DWDTypeValueReporter implements ListSelectionListener {
    	public void valueChanged (ListSelectionEvent event){
    		if (!event.getValueIsAdjusting()){
     			//System.out.println("When Am I here and what is selected ");
    			if (jDWDType.isSelectedIndex(0)) {
    				DWDType =1;
    				outputfile1.setText("C:/DWD/DWDdata/DWD_Std_Output.txt");
    				outputfile2.setText("C:/DWD/DWDdata/DWD_Std_Output.xml");
    				//System.out.println ("DWDType in List "+DWDType);
    			} else if(jDWDType.isSelectedIndex(1)) {
    				DWDType =2;
    				outputfile1.setText("C:/DWD/DWDdata/DWD_Non_Std_Output.txt");
    				outputfile2.setText("C:/DWD/DWDdata/DWD_Non_Std_Output.xml");
    				//System.out.println ("DWDType in List "+DWDType);
    			}
    		}
    		//System.out.println ("DWDType in List "+DWDType);
    	}
    }  /* End of DWDTypeValueReporter*/
    

    /*	Inner class for JList to set up the value of DWDMeanAdjustValue
     *	save it to a file, then read by BatchAdjustSM.exe
     */
    private class DWDMeanAdjustTypeValueReporter implements ListSelectionListener {
       	public void valueChanged (ListSelectionEvent event){
    		if (!event.getValueIsAdjusting()){
     			
    			if (jMeanAdjustType.isSelectedIndex(0)) {
    				DWDMeanAdjust =0;
    				//System.out.println ("DWDMeanAdjust in List "+DWDMeanAdjust);
    			/* Adjusted towards to the first mean*/
    			} else if(jMeanAdjustType.isSelectedIndex(1)) {
    				DWDMeanAdjust =-1;
    				//System.out.println ("DWDMeanAdjust in List "+DWDMeanAdjust);
    				/* Adjusted towards to the second mean*/
    			} else if(jMeanAdjustType.isSelectedIndex(2)) {
    				DWDMeanAdjust =1;
    				//System.out.println ("DWDMeanAdjust in List "+DWDMeanAdjust);
    			}
    		 }
    		 //System.out.println ("Last DWDMeanAdjust "+DWDMeanAdjust);  		
       	}   
       	
    }/* End of DWDMeanAdjustTypeValueReporter*/

    
    /*	Inner class for JList to set up the value of  SaveFileIndicator
     * 
     */
    private class SaveFileIndicatorReporter implements ListSelectionListener {
    	public void valueChanged (ListSelectionEvent event){
    		if (!event.getValueIsAdjusting()){
     			//System.out.println("When Am I here and what is selected ");
    			if (jSaveFileIndicator.isSelectedIndex(0)) {
    				SaveFileIndicator ="Yes";
    				//System.out.println ("SaveFileIndicator "+SaveFileIndicator);
    			} else if(jSaveFileIndicator.isSelectedIndex(1)) {
    				SaveFileIndicator ="No";
    				//System.out.println ("SaveFileIndicator "+SaveFileIndicator);
    			}
    		}
    		//System.out.println ("DWDType in List "+DWDType);
    	}
    }  /* SaveFileIndicatorReporter*/
    
    
    /*
     * Progress bar class, would not work
     */
  
    private class BarThread extends Thread {
        private int DELAY = 300;
        JProgressBar progressBar;

        public BarThread(JProgressBar bar) {
        	//aJProgressBar=bar;
          progressBar = bar;
        }

        public void run() {
          int minimum = progressBar.getMinimum();
          int maximum = progressBar.getMaximum();
          Runnable runner = new Runnable() {
          public void run() {
              int value = progressBar.getValue();
              progressBar.setValue(value+1);
            }
          };
          
          for (int i=minimum; i<maximum; i++) {
            try {
              SwingUtilities.invokeAndWait(runner);

              Thread.sleep(DELAY);
            } catch (InterruptedException ignoredException) {
            } catch (InvocationTargetException ignoredException) {
            }
          }
        }
      } /*End of BarThread*/
   
   
    /* A quick test*/
     public static void main ( String [] args) throws Exception  {

     JFrame jframe = new UpLoadFile();
     jframe.show();       
          
    } // end of main
}


