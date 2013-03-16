/************************************************************* 
 *	DWD (Distance Weighted Discrimination)
 *	
 *	This software is developed by LCCC and Statistics at UNC
 *	It is supported by caBIG from NCI and is Open source 
 *
 *************************************************************/

package edu.unc.LCCC.caBIG.DWD.javaCode.processfile;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import edu.unc.LCCC.caBIG.DWD.javaCode.ImportMAGEMLFiles.UpLoadMAGEMLFile;
import edu.unc.LCCC.caBIG.DWD.javaCode.visualization.*;

/************************************************************* 
 * OpenFileDir.java
 *
 * This file will do:
 *	Find the file directory and open/or save the text file
 *
 *	Adopted from FileChooseApp.java
 
 *	Version: v1.0
 *	Author: Everett Zhou
 *	Date: 11/16/2004
 *
 *************************************************************/
public class OpenFileDir extends JFrame implements ActionListener {
	
	private static JMenuItem fMenuLoad = null;
	private static JMenuItem fMenuSave  = null;
	private static JMenuItem fMenuClose = null;

	private static String fileNo =null;
 
	private static String filePath =null;
	private static StringTokenizer st;

	JavaFilter fJavaFilter = new JavaFilter ();
	File fFile = new File ("default.txt");
	
	/*can be called by SetUpPlotWindow.java*/
	public static String JPEGFileName ="";

	/* constructor
	 * Create a frame with JTextArea and a menubar
	 * with a "File" dropdown menu.
	 * 
	 * @param title: the title for the frame
	 * @param whichFile: to indicate which load or save buuton
	 */
	public OpenFileDir (String title,String whichFile) {
		super (title);
		fileNo = whichFile;
		Container content_pane = getContentPane ();

		// Create a user interface.
		content_pane.setLayout ( new BorderLayout () );
    
		// Use the helper method makeMenuItem
		// for making the menu items and registering
		// their listener.
		JMenu m = new JMenu ("File");

		// Modify task names to something relevant to
		// the particular program.
		m.add (fMenuLoad  = makeMenuItem ("Load"));
		m.add (fMenuSave  = makeMenuItem ("Save"));    
		m.add (fMenuClose = makeMenuItem ("Quit"));

		JMenuBar mb = new JMenuBar ();
		mb.add (m);

		setJMenuBar (mb);
		setSize (400,200);
	} /* end of the constructor */

	/** Process events from the chooser. **/
	public void actionPerformed ( ActionEvent e ) {
		boolean status = false;

		String command = e.getActionCommand ();

		if  (command.equals ("Load")) {
			// Open a file
			status = loadFile ();
         
      		if (!status)
      			JOptionPane.showMessageDialog (
      					null,
    					"Error loading file!", "File Load Error",
    					JOptionPane.ERROR_MESSAGE
      		);
		} else if (command.equals ("Save")) {
			// Save a file
			status = saveFile ();
			if (!status)
				JOptionPane.showMessageDialog (
					null,
					"IO error in saving file!!", "File Save Error",
					JOptionPane.ERROR_MESSAGE
            );
   
		} else if (command.equals ("Quit") ) {
			dispose ();
		}
	} /* end of  actionPerformed */

	
	/* This "helper method" makes a menu item and then
	 * registers this object as a listener to it.
	 *
	 * @param name: name of the menu
	 */
   
	private JMenuItem makeMenuItem (String name) {
		JMenuItem m = new JMenuItem (name);
		m.addActionListener (this);
		return m;
	} /* end of  makeMenuItem */



	/*
	 * This function just finds and loads the file 
	 */
	private boolean loadFile () {
		JFileChooser fc = new JFileChooser ();
		fc.setDialogTitle ("Load File");

		// Choose only files, not directories
		fc.setFileSelectionMode ( JFileChooser.FILES_ONLY);

		// Start in current directory
		fc.setCurrentDirectory (new File ("."));

		// Set filter for Java source files.
		fc.setFileFilter (fJavaFilter);

	    // Now open chooser
	    int result = fc.showOpenDialog (this);
	
	    if (result == JFileChooser.CANCEL_OPTION) {
	       // return true;
	    } else if (result == JFileChooser.APPROVE_OPTION) {
	
	        fFile = fc.getSelectedFile ();
	        String textFile =fFile.toString();
	        
	        if (fileNo.equalsIgnoreCase("LOAD1")){
	        	UpLoadFile.filePath1.setText(textFile);
	        }
	        else if (fileNo.equalsIgnoreCase("LOAD2")){
	        	UpLoadFile.filePath2.setText(textFile);
	        }
	        else if (fileNo.equalsIgnoreCase("LOAD3")){
	        	VisualizationInput.originalFilePath.setText(textFile);
	        }
	        else if (fileNo.equalsIgnoreCase("LOAD4")){
	        	VisualizationInput.DWDVecFilePath.setText(textFile);
	        }
	        else if (fileNo.equalsIgnoreCase("LOAD5")){
	        	VisualizationInput.DWDOutputFilePath.setText(textFile);
	        }
	        else if (fileNo.equalsIgnoreCase("LOAD6")){
	        	UpLoadMAGEMLFile.filePath1.setText(textFile);
	        }
	        else if (fileNo.equalsIgnoreCase("LOAD7")){
	        	UpLoadMAGEMLFile.filePath2.setText(textFile);
	        }
	       
        //Get the absolute path for the file being opened
        filePath =fFile.getAbsolutePath();
                
        if (filePath == null) {
            //fTextField.setText (filePath);
       	  return false;
	    }  
	            
	   } else {
	        return false;
	   }
	    return true;
	} /*End of loadFile*/

	  /**
	    * Use a JFileChooser in Save mode to select files
	    * to open. Use a filter for FileFilter subclass to select
	    * for "*.java" files. If a file is selected, then this file 
	    * will be used as final output 
	   **/
	   
	   boolean saveFile () {
	     File file = null;
	     JFileChooser fc = new JFileChooser ();
	
	     // Start in current directory
	     fc.setCurrentDirectory (new File ("."));
	
	     // Set filter for Java source files.
	     fc.setFileFilter (fJavaFilter);
	
	     // Set to a default name for save.
	     fc.setSelectedFile (fFile);
	
	     // Open chooser dialog
	     int result = fc.showSaveDialog (this);
	
	     if (result == JFileChooser.CANCEL_OPTION) {
	         return true;
	     } else if (result == JFileChooser.APPROVE_OPTION) {
	        fFile = fc.getSelectedFile ();
	        String textFile =fFile.toString();
	        if (fileNo.equalsIgnoreCase("SAVE")){
	        	UpLoadFile.outputfile.setText(textFile);
	        }
	        else if (fileNo.equalsIgnoreCase("SAVE2")){
	        	UpLoadMAGEMLFile.outputfile1.setText(textFile);
	        }
	        else if (fileNo.equalsIgnoreCase("SAVE3")){
	        	UpLoadMAGEMLFile.outputfile2.setText(textFile);
	        }
	        else if (fileNo.equalsIgnoreCase("SAVEJPAG")){
	        	JPEGFileName =textFile;
	        	//System.out.println ("JPG filename OpenFileDir= " +JPEGFileName);
	       		File fFile = new File (JPEGFileName);
	       		if (fFile.exists ()) {
	       			int response = JOptionPane.showConfirmDialog (null,
	       					"Overwrite existing file "+ JPEGFileName +" ??" ,"Confirm Overwrite",
							JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
	       			if (response == JOptionPane.CANCEL_OPTION) {
	       				/* go back to reload the file*/
	       				return false;	             	
	       			}
	       		}
	       		SetUpPlotWindow.saveComponentAsJPEG(SetUpPlotWindow.content, JPEGFileName);
	        }	        
	        return true;
	        
	     } else {
	       return false;
	     }
	  } // saveFile
	 
	   
	  /* return the file path */
	  
	  public static String getFilePath () {
	  		System.out.println ("In getFilePath " + filePath);
	  		return filePath;
	  }
  
 
	  /** Create the framed application and show it. **/
	  
	  public static void main (String [] args) {
	    // Can pass frame title in command line arguments
	    String title="Frame Test";
	    String file="No1";
	    if (args.length != 0) title = args[0];
	    OpenFileDir f = new OpenFileDir (title, file);
	
	    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    f.setVisible (true);
	    //System.out.println (f.getFilePath ());
	    
	  } // main

}// class OpenFile 