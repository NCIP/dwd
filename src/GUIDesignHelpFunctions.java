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

package edu.unc.LCCC.caBIG.DWD.javaCode.util;

import java.awt.*;
import javax.swing.*;


/************************************************************* 
*	GUIDesignHelpFunctions.java
*
*	A set of functions to add JLabel, JButton etc.
*
*	Version: v1.0
*	Author: Everett Zhou
*	Date: 7/19/2005
*
*************************************************************/
public class GUIDesignHelpFunctions extends JFrame  {

    /*Set up window */
	private Container content;
    private GridBagLayout gbLayout;
    private GridBagConstraints gbc;
     
 
    public GUIDesignHelpFunctions() {

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
    }    


   /* Help function to add a label 
    * @param labelName: the name of a label
    * @param x: the position on x-axis
    * @param y: the position on y-axis
    * @param w: width
    * @param h:height
    */
    public void addLabel(Container content, GridBagConstraints gbc,String labelName, int x, int y, int w, int h) {
        JLabel label = new JLabel(labelName);
        addComponent(content, gbc, label, x, y, w, h);
    }

    /* Help function to add a JTextField 
     * @param n: the width of JTextField
     * @param flag: make it editable or not
     * @param x: the position on x-axis
     * @param y: the position on y-axis
     * @param w: width
     * @param h:height
     */
    public JTextField addTextField(Container content, GridBagConstraints gbc, int n, boolean flag,
                               int x, int y, int w, int h) {
        JTextField text = new JTextField(n);
        text.setEditable(flag);
        addComponent(content, gbc, text, x, y, w, h);
        text.setBackground(Color.white);
	    text.setForeground(Color.black);
        return text;

    }
    /* Help function to add a JTextArea 
     * @param n: the width of JTextField
     * @param flag: make it editable or not
     * @param x: the position on x-axis
     * @param y: the position on y-axis
     * @param w: width
     * @param h:height
     */
    public JTextArea addTextArea(Container content, GridBagConstraints gbc, String info, boolean flag,
                               int x, int y, int w, int h) {
    	JTextArea text = new JTextArea(info);
        text.setEditable(flag);
        addComponent(content, gbc, text, x, y, w, h);
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
    public JButton addButton(Container content, GridBagConstraints gbc, String buttonName, int x, int y, int w,int h) {
        JButton button = new JButton(buttonName);
        //button.setBackground(Color.gray);
        addComponent(content, gbc, button, x, y, w, h);
        return button;

    }

    /* Help function to add a radiobutton 
     * @param buttonName: the name of a button
     * @param x: the position on x-axis
     * @param y: the position on y-axis
     * @param w: width
     * @param h:height
     */
    public JRadioButton addRadioButton(Container content, GridBagConstraints gbc, String buttonName, int x, int y, int w,int h) {
        JRadioButton button = new JRadioButton(buttonName);
        //button.setBackground(Color.gray);
        addComponent(content, gbc, button, x, y, w, h);
        return button;

    }
 
    /* Help function to add a JProgressBar 
     * @param x: the position on x-axis
     * @param y: the position on y-axis
     * @param w: width
     * @param h:height
     */
    public JProgressBar addProgressBar (Container content, GridBagConstraints gbc, int x, int y, int w,int h) {
    	JProgressBar jpb = new JProgressBar (0,100);
    	jpb.setBackground(Color.white);
        addComponent(content, gbc, jpb, x, y, w, h);
        return jpb;
    }
    
    
    /* Help function to add a component 
     * @param c: the component
     * @param x: the position on x-axis
     * @param y: the position on y-axis
     * @param w: width
     * @param h:height
     */
    public void addComponent( Container content, GridBagConstraints gbc, Component c, int x, int y,
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
 

}


