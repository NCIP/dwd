/************************************************************* 
 *	DWD (Distance Weighted Discrimination)
 *	
 *	This software is developed by LCCC and Statistics at UNC
 *	It is supported by caBIG from NCI and is Open source 
 *
 *************************************************************/

package edu.unc.LCCC.caBIG.DWD.javaCode.processfile;

import java.io.* ;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.lang.reflect.*;
import java.util.StringTokenizer;


import edu.unc.LCCC.caBIG.DWD.javaCode.standardization.MatrixStandardization;

/************************************************************* 
*	UpLoadFile.java
*
*	Implement the GUI to retrieve all parameters for two input files.
*	Take the combined file as input and pass it to BatchAdjustSM.exe
*
*	Version: v1.0
*	Author: Everett Zhou
*	Date: 11/19/2004
*
*************************************************************/
public class UpLoadFile extends JFrame implements ActionListener {

    /*Set up window */
	private Container content;
    private GridBagLayout gbLayout;
    private GridBagConstraints gbc;
    /* The action value */
    private static String myAction;
    
    /*Value for the first file*/
    private JTextField fileName1;
    public static JTextField filePath1;
    private JButton load1;
    private JTextField row1;
    private JTextField col1;
    private JTextField ID1;
    private JButton format1;

    /*Value for the second file*/
    private JTextField fileName2;
    public static JTextField filePath2;
    private JButton load2;
    private JTextField row2;
    private JTextField col2;
    private JTextField ID2;
    private JButton format2;
    
    /*Dwd type*/
    private JList jDWDType;
    private String DWDTypeLabel [] ={"Standardized DWD (Default)", "Non-Standardized DWD"};
    private static int DWDType=1; 
    
    private JList jMeanAdjustType;
    private String DWDMeanAdjustLabel [] ={"Centered at 0 (Default)", "Centered at the First Mean", "Centered at the Second Mean"};
    private static int DWDMeanAdjust=0;    
    
    /* Store the value of DWD Mean Adjustmeant*/
    private String meanAdjustTypeFile = "C:\\DWD\\DWDdata\\DWD_MeanAdjustType.txt";
    
    public static JTextField outputfile;
    private JButton save;
    
    /*Add task buttons*/
    private JButton dwd;
 
    private String spaceLine = "<html>" + "<p>" +"<p>" + "</html>";
    private static StringTokenizer st;    
    
    public UpLoadFile() {

        setTitle("DWD Merge");

	/* set up the size and location of window */
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screenWidth = d.width;
        //System.out.println (screenWidth + " "+screenHeight);
        
        float w1 = (float) ((float)screenWidth / 1.5);        
        Float w2 = new Float (w1);       
        int w3 = w2.intValue();

        float h1 = (float) ((float)screenHeight / 1.25);        
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
        setLocation(screenWidth / 5, screenHeight / 10);

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
  
        addLabel("<html><FONT COLOR=RED SIZE =4>First File</FONT><p></html>", 6,1,4,1);
        addLabel(spaceLine, 0, 2, 40, 1);
        
        addLabel("File Out Indicator", 2,5,4,1);
        fileName1 = addTextField(10,true, 7,5,4,1);
        fileName1.setText("R");
    	fileName1.setEditable(true);
        
        addLabel("File Path", 2,7,4,1);        
        filePath1 = addTextField(20,true, 7,7,4,1);
 		filePath1.setText("C:\\DWD\\DWDdata\\Example1.txt");
 		filePath1.setEditable(true);

        load1 = addButton("Load File", 13,7,4,1);
        load1.setActionCommand("LOAD1");
        load1.addActionListener(this);
        
        addLabel("Data Row Starts at", 2,9,4,1);        
        row1 = addTextField(10,true, 7,9,2,1);
 		row1.setText("2");
 		row1.setEditable(true);
 		
        format1 = addButton("View File", 13,9,4,1);
        format1.setActionCommand("VIEW1");
        format1.addActionListener(this);
 		
        addLabel("Data Column Starts at", 2,11,4,1);        
        col1 = addTextField(10,true, 7,11,2,1);
 		col1.setText("3");
 		col1.setEditable(true);
 		
        addLabel("Identifier Column", 2,13,4,1);        
        ID1 = addTextField(10,true, 7,13,2,1);
		ID1.setText("1");
		ID1.setEditable(true);
		        
        addLabel(spaceLine,0, 17,40, 1);
        
        /* For Second File */
        addLabel("<html><FONT COLOR=RED SIZE =4>Second File</FONT><p></html>", 7,18,4,1);
        addLabel(spaceLine,0, 20,20, 1);
        
        addLabel("File Out Indicator", 2,22,4,1);
        fileName2 = addTextField(10,true, 7,22,4,1);
        fileName2.setText("S");
        fileName2.setEditable(true);
     
        addLabel("File Path", 2,24,4,1);        
        filePath2 = addTextField(20,true, 7,24,4,1);
 		filePath2.setText("C:\\DWD\\DWDdata\\Example2.txt");
 		filePath2.setEditable(true);
 
        load2 = addButton("Load File", 13,24,4,1);
        load2.setActionCommand("LOAD2");
        load2.addActionListener(this);
        
        addLabel("Data Row Starts at", 2,26,4,1);        
        row2 = addTextField(10,true, 7,26,2,1);
 		row2.setText("2");
 		row2.setEditable(true);

        format2 = addButton("View File", 13,26,4,1);
        format2.setActionCommand("VIEW2");
        format2.addActionListener(this);
       		 		
        addLabel("Data Column Starts at", 2,28,4,1);        
        col2 = addTextField(10,true, 7,28,2,1);
		col2.setText("2");
		col2.setEditable(true);
		        
        addLabel("Identifier Column", 2,30,4,1);        
        ID2 = addTextField(10,true, 7,30,2,1);
		ID2.setText("1");
		ID2.setEditable(true);		
		addLabel(spaceLine, 0, 33, 40, 1);
		
        /*DWD type*/
		jDWDType = new JList (DWDTypeLabel);
		jDWDType.setSelectedIndex(0);
		jDWDType.setVisibleRowCount(2);
		jDWDType.addListSelectionListener(new DWDTypeValueReporter());
        JScrollPane listPane1 = new JScrollPane (jDWDType);
        addLabel("DWD Type", 2,35,4,1);
        addComponent(listPane1, 7,35,5,1);
        addLabel(spaceLine, 0, 36, 40, 1);
        
        /*DWD Mean Adjust type*/
        jMeanAdjustType = new JList (DWDMeanAdjustLabel);
        jMeanAdjustType.setSelectedIndex(0);
        jMeanAdjustType.setVisibleRowCount(3);
        jMeanAdjustType.addListSelectionListener(new DWDMeanAdjustTypeValueReporter());
        JScrollPane listPane2 = new JScrollPane (jMeanAdjustType);        
        addLabel("Mean Adjustment Type", 2,37,4,1);
        addComponent(listPane2, 7,37,5,1); 
        addLabel(spaceLine, 0, 38, 40, 1);
        
        addLabel("Output File Path", 2,39,4,1);
        outputfile = addTextField(25,true, 7,39,4,1);
        outputfile.setText("C:\\DWD\\DWDdata\\DWD_Std_Output.txt");
        outputfile.setEditable(true);
 
        save = addButton("Save Output to", 13,39,4,1);
        save.setActionCommand("SAVE");
        save.addActionListener(this);
        
		addLabel(spaceLine, 0, 41, 40, 1);
		addLabel(spaceLine, 0, 43, 40, 1);
		
        /* Add in task buttons*/        
        dwd = addButton("Merge the files (DWD)", 8,45,3,1);
        dwd.setActionCommand("DWD");
        dwd.addActionListener(this);
        
    } // end of constructor    


    /*
     *  To do all jobs, View files, Load files, DWD, and Bind
     * 
     */
   public void actionPerformed (ActionEvent ae) {      
       myAction = ae.getActionCommand();
   		
       /* To see first file formats  */     
       if (myAction.equalsIgnoreCase("VIEW1") ) {
       		String textFile =UpLoadFile.filePath1.getText();	
       		String [] columnName = buildHeader (textFile);        
       		String [][] cells = buildCells (textFile);	 
            JFrame jframe = new ListingTable (textFile,cells,columnName);
       		jframe.show();  
       	} 
       /* To see second file formats  */
       else if (myAction.equalsIgnoreCase("VIEW2") ) {
        // System.out.println ("Do nothing for USP " +myAction);	 
       		//JFrame jframe = new ListingTable("View File","VIEW2");
       		//jframe.show(); 
       		String textFile =UpLoadFile.filePath2.getText();	
       		String [] columnName = buildHeader (textFile);        
       		String [][] cells = buildCells (textFile);	 
            JFrame jframe = new ListingTable (textFile,cells,columnName);
       		jframe.show(); 
     	} 
       /* To load the first file*/
       else if (myAction.equalsIgnoreCase("LOAD1") ) {
        // System.out.println ("Do nothing for USP " +myAction);	 
       		JFrame jframe = new OpenFileDir("Open File Directory","LOAD1");
       		jframe.show();  
     	} 
       /* To load the second file*/
       else if (myAction.equalsIgnoreCase("LOAD2") ) {
        // System.out.println ("Do nothing for USP " +myAction);	 
       		JFrame jframe = new OpenFileDir("Open File Directory","LOAD2");
       		jframe.show();  
     	}       
       else if (myAction.equalsIgnoreCase("SAVE") ) {
        // System.out.println ("Do nothing for USP " +myAction);	 
       		JFrame jframe = new OpenFileDir("Save Output File To","SAVE");
       		jframe.show();  
     	} 
       	/* To get all parameters and read and save files */
       	else if (myAction.equalsIgnoreCase("DWD") ) {

       		//dwd.setEnabled( false );

		     File findDirc = new File ("C:\\DWD\\DWDdata");
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
			
			temp1 = row1.getText().trim();
			int rowAt1 = Integer.valueOf(temp1).intValue();
    		
   			temp1 = col1.getText().trim();   			
   			int colAt1 = Integer.valueOf(temp1).intValue();
   			
   			temp1 = ID1.getText().trim();
    		int IDCol1 = Integer.valueOf(temp1).intValue();
			
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
			
			temp2 = row2.getText().trim();
			int rowAt2 = Integer.valueOf(temp2).intValue();
    		
   			temp2 = col2.getText().trim();   			
   			int colAt2 = Integer.valueOf(temp2).intValue();
   			
   			temp2 = ID2.getText().trim();
    		int IDCol2 = Integer.valueOf(temp2).intValue();

			String fileNameOut = outputfile.getText().trim();
			File fFile = new File (fileNameOut);
	         if (fFile.exists ()) {
	             int response = JOptionPane.showConfirmDialog (null,
	               "Overwrite existing file "+ fileNameOut +" ??" ,"Confirm Overwrite",
	                JOptionPane.OK_CANCEL_OPTION,
	                JOptionPane.QUESTION_MESSAGE);
	             if (response == JOptionPane.CANCEL_OPTION) {
	             	/* Kill the Task Report window and go back to reload the file*/
	             	jfr.dispose();
	    	       	return;	             	
	             }
	         }
			
			/* To do all jobs: Load, combine files, call BatchAdjustSM.exe and put the keys back*/
     		Thread FPT = new FileProcessThread (jfr,DWDType,fName1,fPath1,rowAt1,colAt1,IDCol1,
        			fName2 ,fPath2,rowAt2,colAt2, IDCol2, fileNameOut);
     		/* Kick it off*/
     		FPT.start();

       	} /* End of DWD Button */
   } /* End of actionPerformed*/
   

   /* Help function to add a label 
    * @param labelName: the name of a label
    * @param x: the position on x-axis
    * @param y: the position on y-axis
    * @param w: width
    * @param h:height
    */
    public void addLabel(String labelName, int x, int y, int w, int h) {
        JLabel label = new JLabel(labelName);
        addComponent(label, x, y, w, h);
    }

    /* Help function to add a JTextField 
     * @param n: the width of JTextField
     * @param flag: make it editable or not
     * @param x: the position on x-axis
     * @param y: the position on y-axis
     * @param w: width
     * @param h:height
     */
    public JTextField addTextField(int n, boolean flag,
                               int x, int y, int w, int h) {
        JTextField text = new JTextField(n);
        text.setEditable(flag);
        addComponent(text, x, y, w, h);
        text.setBackground(Color.white);
	    text.setForeground(Color.black);
        return text;

    }

    /* Help function to add a button 
     * @param buttonName: the name of a button
     * @param x: the position on x-axis
     * @param y: the position on y-axis
     * @param w: width
     * @param h:height
     */
    public JButton addButton(String buttonName, int x, int y, int w,int h) {
        JButton button = new JButton(buttonName);
        //button.setBackground(Color.gray);
        addComponent(button, x, y, w, h);
        return button;

    }

    /* Help function to add a radiobutton 
     * @param buttonName: the name of a button
     * @param x: the position on x-axis
     * @param y: the position on y-axis
     * @param w: width
     * @param h:height
     */
    public JRadioButton addRadioButton(String buttonName, int x, int y, int w,int h) {
        JRadioButton button = new JRadioButton(buttonName);
        //button.setBackground(Color.gray);
        addComponent(button, x, y, w, h);
        return button;

    }
 
    /* Help function to add a JProgressBar 
     * @param x: the position on x-axis
     * @param y: the position on y-axis
     * @param w: width
     * @param h:height
     */
    public JProgressBar addProgressBar (int x, int y, int w,int h) {
    	JProgressBar jpb = new JProgressBar (0,100);
    	jpb.setBackground(Color.white);
        addComponent(jpb, x, y, w, h);
        return jpb;
    }
    
    
    /* Help function to add a component 
     * @param c: the component
     * @param x: the position on x-axis
     * @param y: the position on y-axis
     * @param w: width
     * @param h:height
     */
    public void addComponent( Component c, int x, int y,
                                int w, int h) {
         //set gridx and gridy
         gbc.gridx = x;
         gbc.gridy = y;

         //set gridwidth and gridheight
         gbc.gridwidth = w;
         gbc.gridheight = h;

         //set contraints
        // gbLayout.setConstraints(c, gbc);
         content.add(c,gbc );
      }
    
    
    /* Build a header of JTable
     *
     *	@param textFile: the name of a file
     *	@return: an array for Jtable title
     */
    
    public String [] buildHeader (String textFile) {
    	       
           int dim[] =new int[2];
           dim = ReadTextFile.findFileDim (textFile);
           //int row = dim[0];
           int colSize= dim[1]; 
           
           String [] header = new String[colSize+1];
           header[0]="\t";
           for (int i=1; i<colSize+1; i++){
           	header[i] = "Col "+i;
           } 
           return header; 	
    	} /* End of buildHeader */
   
     /* 	Build cells of JTable
      *  	@param textFile: the name of a file
     *		@return: an array for Jtable cells
     */
     public String [][] buildCells (String textFile) {      
           int dim[] =new int[2];
           dim = ReadTextFile.findFileDim (textFile);
           int rowSize = dim[0];
           int colSize= dim[1]; 
           
           String [][] cells = new String[rowSize][colSize+1];
           
           /* Initialize the first column
            * Row 0 in array labeled as Row 1
            */
           for (int i=1; i<rowSize+1; i++){
           	cells[i-1][0]= "Row " +i;
           }
           
           String s, temp;
           
           try {
               FileReader fr = new FileReader (textFile);
               BufferedReader br = new BufferedReader (fr);
               
               int i=0; //i is row track index
               int j=1; //j is column track index
               		// j is initialized to 1, because column 0 is already initilized
               while ((s=br.readLine()) != null) {

                	st = new StringTokenizer (s,"\t");
                   
                /* each token from a line is an attribute of cells */
                	while (st.hasMoreTokens()) {
                      temp = st.nextToken().trim();
                      if (temp==null) {
                      	temp =" ";
                      }
                     // System.out.print (temp +"\t");
                      
                      if (i<rowSize) {
                      
                      	if (j<colSize+1) {
                      		cells[i][j]=temp;
                      		//System.out.println ("i= "+i+" j= "+j +" " );
                      		j++;
              
              	        } else {
                  	    	j=1;
                      		i++;
                      		//System.out.println ("i= "+i+" j= "+j +" " );
                      		cells[i][j]=temp;
                      		j++;
                     		 }
                     	}	 
                     
                   }
                   
         		 }
               fr.close();
            }         
           catch (IOException ioe) {
               System.out.println ( "File not found or wrong file");
               System.out.println ( "IOException message = "+ ioe.getMessage() );
           }
           return (cells); 	
    	}  /* End of buildCells */

    /* This is the better way to use another thread 
	 * to consume the results generated from external .exe
	 */
    private class FileProcessThread extends Thread {
    	private String fName1,fPath1,fName2,fPath2, fileNameOut;
    	private int DWDType;
    	private int rowAt1, colAt1, IDCol1,rowAt2, colAt2, IDCol2;
    	private JFrame jfr;
    	
    	FileProcessThread (JFrame jfr,int DWDType,String fName1,String fPath1, int rowAt1, int colAt1, int IDCol1,
    			String fName2 ,String fPath2,int rowAt2, int colAt2, int IDCol2, String fileNameOut){
    		
    		this.jfr=jfr;
    		this.DWDType= DWDType;
    		this.fName1 = fName1;
    		this.fPath1 = fPath1;
    		this.rowAt1 = rowAt1;
    		this.colAt1 = colAt1;
    		this.IDCol1 = IDCol1;
    		
    		this.fName2 = fName2;
    		this.fPath2 = fPath2;
       		this.rowAt2 = rowAt2;
    		this.colAt2 = colAt2;
    		this.IDCol2 = IDCol2;   		
    		
    		this.fileNameOut = fileNameOut;
    	}
    	
    	/* Put keys back and save file */
    	public void run(){
			if (fileNameOut.equalsIgnoreCase("")){
              	JFrame jf = new PopUpError ("<html><p> <FONT COLOR=RED SIZE =4>Wrong Output File</FONT></html>", "Please Enter the Right Output File and Terminate the Program");
         		jf.show(); 
			} else { /* else 1 */
			/* Combine two data files */
				try {
					CombineFiles.filePreProcess (fName1,fPath1, rowAt1, colAt1, IDCol1,
							fName2, fPath2,rowAt2, colAt2, IDCol2);
					}	catch (IOException ioe) {
						System.out.println ( "File not found or wrong file");
						System.out.println ( "IOException message = "+ ioe.getMessage() );
					}
					
					/*
					 * To test whether the combined file DWD_Input.txt is empty
					 * if it is, flag the error, or else flag the job is done
					 */
			     int dim[] =new int[2];
			     dim = ReadTextFile.findFileDim ("C:\\DWD\\DWDdata\\DWD_Input.txt");
			     int numRows = dim[0];
			     if (numRows <2) {
			     	jfr.dispose();
			     	JFrame jf = new PopUpError ("<html><p>There is <FONT COLOR=RED SIZE =4>No Common Genes</FONT> in these two files</html>",
			     			"Fix the File(s) and Terminate the Program");
			     	jf.show(); 
			     	return;
			     } else { /* else 2*/
			     	 /*To save the value of mean adjustment type into the text file
			     	  * The file will be read by BatchAdjustSM.exe
			     	   */
		    	 	   String meanAdjustTypeFile = "C:\\DWD\\DWDdata\\DWD_MeanAdjustType.txt";
		    	 	   try {
		    	 	   		PrintWriter writer = new PrintWriter(new FileWriter (meanAdjustTypeFile, false));
		    	 	   		writer.println (DWDMeanAdjust);
		    	 	   		writer.flush();
		    	 	   		writer.close();
		    	 	   } catch (IOException ioe){
		    	 	   		System.out.println (ioe);
		    	 	   }   
		    	 	   
			     	/* To do DWD merge */
			     	Runtime rt = Runtime.getRuntime();   		
			     	Process proc;

			     	try {
			     		proc = rt.exec("C:\\DWD\\lib\\cCode\\BatchAdjustSM.exe");
			     		InputStream results = proc.getInputStream();
			     		byte[] b = new byte[1024];
			     		while (true) {
			     		   int n = results.read(b);
			     		  //System.out.println (n);
			     		   if (n == -1)
			     		       break;
			     		   // use output data
			     		}
				     	
			     		/* Delete the DWD_MeanAdjustType.txt*/
				     	File f2 = new File ("C:\\DWD\\DWDdata\\DWD_MeanAdjustType.txt");
				     	if (f2.exists()){
				     		//System.out.println("Test files 3 "+f2.exists());
				     		f2.delete();
				     		//System.out.println("Test files 4 "+f2.exists());
				     	}
				     	
			     	} catch (Exception e) {
			     		System.out.println(e); 
			     	} 
			    }/* End of  else 2 */
          }/* End of  else 1 */
    		/* This is th standardized DWD
    		* Need to standardize each column by (cij-mean)/std error
    		*/	
			String fileNameIn ="C:\\DWD\\DWDdata\\DWD_Intermediate_Output.txt";
	     	if (DWDType ==1){
	     		// First read the DWD output
	     		double in [][] = ReadTextFile.readDWDInputOutputToArrayArray(fileNameIn,1,0);
	     		//Find how many -1 and 1				
	     		int ones[] = ReadTextFile.findOnes("C:\\DWD\\DWDdata\\DWD_Input.txt");
		
	     		//compute the mean and std error for each column
	     		double meanStdError [][] = MatrixStandardization.computeMeanStdError (in);

	     		// To do the standization
	     		double standardizedArr [][] = MatrixStandardization.standardization(in,meanStdError );
	
	     		//To put back keys
	     		try {
	     			CombineFiles.filePostProcess (fName1,colAt1, IDCol1,fName2, standardizedArr, ones ,fileNameOut);
	     		}	catch (IOException ioe) {
	     			System.out.println ( "File not found or wrong file");
	     			System.out.println ( "IOException message = "+ ioe.getMessage() );
	     		}	
	     		/* Delete DWD_Intermediate_Output.txt intermidiate file*/
		     	File f1 = new File (fileNameIn);
		     	if (f1.exists()){		     		
		     		f1.delete();
		     	}
		     	/* Close the window*/
		     	jfr.dispose();
		     	/*Report the job is done*/
	     		JFrame jf3 = new PopUpTask ("<html>Finished <FONT COLOR=RED SIZE =4> Standardized DWD</FONT>" +
				"<p> <p>You are ready to click <p><FONT COLOR=RED SIZE =4>Visualize and Diagnose</FONT></html>");
	     		jf3.show();    		
	     		System.out.println ("Finished Standardized DWD"); 
	     	}
	     	/* For non standardized DWD just put the keys and titles back*/
	     	else if (DWDType ==2){
	     		try {
	     			CombineFiles.filePostProcess (fName1,colAt1, IDCol1, fName2, fileNameIn, fileNameOut);
	     		}	catch (IOException ioe) {
	     			System.out.println ( "File not found or wrong file");
	     			System.out.println ( "IOException message = "+ ioe.getMessage() );
	     		} 
	     		/* Delete DWD_Intermediate_Output.txt intermidiate file*/
		     	File f1 = new File (fileNameIn);
		     	if (f1.exists()){
		     		f1.delete();
		     	}
		     	/* Close the window*/
		     	jfr.dispose();
		     	/*Report the job is done*/
	     		JFrame jf4 = new PopUpTask ("<html>Finished <FONT COLOR=RED SIZE =4> Non-Standardized DWD</FONT> " +
	     			"<p> <p>You are ready to click <p><FONT COLOR=RED SIZE =4>Visualize and Diagnose</FONT></html>");
	     		jf4.show();    		
	     		System.out.println ("Finished Non-Standardized DWD");         	    		
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
    				outputfile.setText("C:\\DWD\\DWDdata\\DWD_Std_Output.txt");
    				//System.out.println ("DWDType in List "+DWDType);
    			} else if(jDWDType.isSelectedIndex(1)) {
    				DWDType =2;
    				outputfile.setText("C:\\DWD\\DWDdata\\DWD_Non_Std_Output.txt");
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


