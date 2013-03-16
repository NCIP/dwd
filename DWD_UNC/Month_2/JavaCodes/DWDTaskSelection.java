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
import edu.unc.LCCC.caBIG.DWD.javaCode.Visualization.VisualizationInput;

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
 	    
 	   addComponent(coloredLable, 0, 4, 40, 1);

 	   /* Add the space line */
 	    String spaceLine = "<html>" + "<p>" +"<p>" + "</html>";
 	    JLabel emptyLine = new JLabel (spaceLine);
 	    addComponent(emptyLine, 0, 8, 40, 3);
		    
 //---------------------------------------------------------
 /* For Task Merge data */
        addLabel("Merge Data Files", 6,12,1,1);
        merge = addButton("DWD Merge", 10,12,5,1);
        merge.setActionCommand("MERGE");
        merge.addActionListener(this);
 	    addComponent(emptyLine, 0, 14, 40, 3);       
 
 /* Task For Visualization */
        addLabel("Visualize Data ", 6,18,1,1);
        visualization = addButton("Visualize and Diagnose",11,18,5,1);
        visualization.setActionCommand("VIS");
        visualization.addActionListener(this);
    }    
        

   /* Select one of two action to do one two tasks */
   public void actionPerformed (ActionEvent ae) {      
       myAction = ae.getActionCommand();
       
       	/* To Merge files */
       	if (myAction.equalsIgnoreCase("MERGE") ) {
       		 JFrame jframe = new UpLoadFile ();
             jframe.show(); 
       	} 
       	else if (myAction.equalsIgnoreCase("VIS") ){
           	JFrame jf = new VisualizationInput ();
     		jf.show(); 
       	}
   }
   
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

    /* Help function to add a button 
     * @param buttonName: the name of a button
     * @param x: the position on x-axis
     * @param y: the position on y-axis
     * @param w: width
     * @param h:height
     */
    public JButton addButton(String buttonName, int x, int y, int w,int h) {
        JButton button = new JButton(buttonName);
        button.setBackground(Color.gray);
        addComponent(button, x, y, w, h);
        return button;
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
         /*set gridx and gridy */
         gbc.gridx = x;
         gbc.gridy = y;

         /*set gridwidth and gridheight */
         gbc.gridwidth = w;
         gbc.gridheight = h;

         content.add(c,gbc );
      }
 
}


