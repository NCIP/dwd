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
import java.awt.image.BufferedImage;
import java.io.*;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import edu.unc.LCCC.caBIG.DWD.javaCode.processfile.OpenFileDir;


/************************************************************* 
*
*	SetUpPlotWindow.java
*	To do: Slice the JPanel into 16 cells, 4 columns and 4 rows
*			Assign each chart into one cell  
*
*	Version: v1.0
*	Author: Everett Zhou
*	Date: 2/15/2005
*
*************************************************************/

public class SetUpPlotWindow extends JFrame implements ActionListener {
	
	/* The title of visualization*/
	private String title;
	
  	/* Split the panel hotlizontally into four parts*/
  	private JSplitPane H11, H12, H13;
  	private JSplitPane H21, H22, H23;
  	private JSplitPane H31, H32, H33;
  	private JSplitPane H41, H42, H43;
  	private JSplitPane H51, H52, H53;

	/* Split the panel vertically into five parts*/
  	private JSplitPane V1, V2, V3, V4;
  	
  	private  JLabel outputFile;
    public static  JTextField JpegOutputFilePath;
    
    private JButton load;
    private JButton save;
  	
    /* The action value */
    private String myAction;
    
    private int view;
    
    public static String JPEGFileName1 ="";
    public static String JPEGFileName2 ="";
    public static String JPEGFileName3 ="";
    public static String JPEGFileName4 ="";
    public static String JPEGFileName5 ="";
    
    public static Container content;
 
    /* Constructor
  	 * 
  	 * @param title: the title of frame
  	 * @param view: if view =1 plot is "Raw Data - Raw PC View"
  	 * 				if view =2 plot is "Raw Data - Raw PC & DWD View"
  	 * 				if view =3 plot is "Adj. Data - Raw PC & DWD View"
  	 * 				if view =4 plot is "Adj. Data - Raw PC View"
  	 * 				if view =5 plot is "Adj. Data - Adj. PC View"
  	 * @param originalMatrix: the original data matrix
  	 * @param kde: the kde matrix for the original data matrix
  	 * @param itemCount1: the number of items from the first source
  	 * @param itemCount2: the number of items from the second source
  	 */

  	public SetUpPlotWindow ( String title, int view, double originalMatrix [][], double kde [][], int itemCount1,int itemCount2) {
    	//super(title);
    	this.title=title;
    	this.view = view;
    	
    	setTitle (title);
    	
    	setLocation(20,10);
    	setSize(450,450); 
 
    	/*The size of each JPanel*/
    	int w = 160;
    	int h = 140;
    	    	
    	/* Set up the value for PCs*/
    	String pc1 =" ";
    	String pc2 =" ";
    	String pc3 =" ";
    	String pc4 =" ";
    	if (view ==1 || view ==4|| view ==5){
    		pc1 ="PC1";
       		pc2 ="PC2";
       		pc3 ="PC3";
       		pc4 ="PC4";
    	}else if (view ==2 || view ==3){
    		pc1 ="PC1";
       		pc2 ="PC2";
       		pc3 ="PC3";
       		pc4 ="DWD";
    	}
    	
    	/*initialize the XYDataMatrix */
       	XYDataMatrix XYDM = new XYDataMatrix(kde);
       	
    	/*The first layer*/
    	H13 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    	H13.setResizeWeight(0.5);
    	H13.setOneTouchExpandable(true);
    	
 
		XYDataset data1 =  XYDM.createXYScatterPlotDataset (originalMatrix,3, 0, itemCount1, itemCount2); 
    	H13.setRightComponent(createXYScatterPlotChart( data1, pc4, pc1, w,h));   	
     	
		XYDataset data2 = XYDM.createXYScatterPlotDataset (originalMatrix,2, 0, itemCount1, itemCount2);
		H13.setLeftComponent(createXYScatterPlotChart( data2, pc3, pc1, w,h));
		
		H12 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		H12.setResizeWeight(0.33);
		H12.setOneTouchExpandable(true);
		

		XYDataset data3 = XYDM.createXYScatterPlotDataset (originalMatrix,1, 0, itemCount1, itemCount2);
		H12.setLeftComponent(createXYScatterPlotChart( data3, pc2, pc1, w,h));	
    	H12.setRightComponent(H13);
    	
		H11 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		H11.setResizeWeight(0.25);
		H11.setOneTouchExpandable(true);

		XYDataset data4 = XYDM.createXYLineAndShapeDataset (originalMatrix, kde, 0, itemCount1, itemCount2);
		H11.setLeftComponent(createXYLineAndShapeRendererChart( data4, pc1, "KDE", w,h));
		H11.setRightComponent(H12);    
		
		/*The second layer*/
    	H23 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    	H23.setResizeWeight(0.5);
    	H23.setOneTouchExpandable(true);
    	
		XYDataset data5 = XYDM.createXYScatterPlotDataset (originalMatrix, 3, 1, itemCount1, itemCount2);
		H23.setRightComponent(createXYScatterPlotChart( data5, pc4, pc2, w,h));
		
		XYDataset data6 = XYDM.createXYScatterPlotDataset (originalMatrix, 2, 1, itemCount1, itemCount2);
		H23.setLeftComponent(createXYScatterPlotChart( data6, pc3, pc2, w,h));
		
		H22 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		H22.setResizeWeight(0.33);
		H22.setOneTouchExpandable(true);
		
		XYDataset data7 = XYDM.createXYLineAndShapeDataset (originalMatrix, kde, 1, itemCount1, itemCount2);
		H22.setLeftComponent(createXYLineAndShapeRendererChart( data7, pc2, "KDE", w,h));

    	H22.setRightComponent(H23);

		H21 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		H21.setResizeWeight(0.25);
		H21.setOneTouchExpandable(true);
		
		H21.setLeftComponent (createEmptyPanel(w,h));		
    	H21.setRightComponent(H22); 
    
    	/*The Third layer*/
    	H33 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    	H33.setResizeWeight(0.5);
    	H33.setOneTouchExpandable(true);

		XYDataset data9 = XYDM.createXYScatterPlotDataset (originalMatrix, 3, 2, itemCount1, itemCount2);
		H33.setRightComponent(createXYScatterPlotChart( data9, pc4, pc3, w,h));

		XYDataset data10 = XYDM.createXYLineAndShapeDataset (originalMatrix, kde, 2, itemCount1, itemCount2);
		H33.setLeftComponent(createXYLineAndShapeRendererChart( data10, pc3, "KDE", w,h));

		H32 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		H32.setResizeWeight(0.33);
		H32.setOneTouchExpandable(true);
		
		H32.setLeftComponent(createEmptyPanel(w,h));  
    	H32.setRightComponent(H33);

		H31 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		H31.setResizeWeight(0.25);
		H31.setOneTouchExpandable(true);
			
		H31.setLeftComponent(createEmptyPanel(w,h));		    	
   		H31.setRightComponent(H32);
    
    	/*The fourth layer*/ 
    	H43 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    	H43.setResizeWeight(0.5);
    	H43.setOneTouchExpandable(true);

		XYDataset data13 = XYDM.createXYLineAndShapeDataset (originalMatrix, kde, 3, itemCount1, itemCount2);
		H43.setRightComponent(createXYLineAndShapeRendererChart( data13, pc4, "KDE", w,h));
    	H43.setLeftComponent(createEmptyPanel(w,h));
 
		H42 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		H42.setResizeWeight(0.33);
		H42.setOneTouchExpandable(true);
		
		H42.setLeftComponent(createEmptyPanel(w,h));		
    	H42.setRightComponent(H43);

		H41 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		H41.setResizeWeight(0.25);
		H41.setOneTouchExpandable(true);
		
    	H41.setLeftComponent(createEmptyPanel(w,h));
    	H41.setRightComponent(H42);   
    
    	/*The fifth layer, just a button*/ 
		//H53 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		//H53.setResizeWeight(0.6);

		//load = new JButton ("Save Frame as .jpeg File To");
        //load.setActionCommand("SAVE2");
        //load.addActionListener(this);
        
		save = new JButton ("Save Frame as .jpeg File To");
        save.setActionCommand("SAVEJPAG");
        save.addActionListener(this);	

		//H53.setLeftComponent(load);		
    	//H53.setRightComponent(save);

    	/*
		H52 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		H52.setResizeWeight(0.5);
		
		//JpegOutputFilePath = new JTextField("C:\\DWD\\DWDdata\\" + title +".jpeg" ,30);
		JpegOutputFilePath = new JTextField("" ,30);
		H52.setLeftComponent(JpegOutputFilePath);		
    	H52.setRightComponent(H53);
    	
    	outputFile = new JLabel ("Output File");
		H51 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		H51.setResizeWeight(0.33);

    	H51.setLeftComponent(outputFile);
    	H51.setRightComponent(H52);   
    	*/
 
    	/*Set H31 to the third layer and H41 to the fourth layer*/
    	V4 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    	V4.setResizeWeight(0.95);
    	
       	V4.setTopComponent(H41);
    	//V4.setBottomComponent(H51); 
    	//V4.setBottomComponent(H53);
    	V4.setBottomComponent(save);
    	
    	V3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    	V3.setResizeWeight(0.5);
    	
    	V3.setTopComponent(H31);
    	V3.setBottomComponent(V4); 
     
     	/*Set H21 to the second layer and V3 to the third layer*/
    	V2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    	V2.setResizeWeight(0.3);
    	
    	V2.setTopComponent(H21);
    	V2.setBottomComponent(V3);
     
     	/*Set H11 to the first layer and V2 to the second layer*/
    	V1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    	V1.setResizeWeight(0.25);
    	
    	V1.setTopComponent(H11);
    	V1.setBottomComponent(V2);
    
    	setContentPane(V1);

    	pack();
    	setVisible(true);
  	}/* End of SetUpPlotWindow*/

	
  	/* Create an JPanel for scater plot with w and h size
  	 * 
  	 * @param data: the data matrix with XYDataset format
  	 * @param x: label for x-xis
  	 * @param y: label for y-xis
  	 * @param w : width of the JPanel
  	 * @param h : height of the JPanel
  	 * @return : a JPanel with a chart of JFreeChart type
  	 */
  	public static JPanel createXYScatterPlotChart(XYDataset data, String x, String y, int w, int h) {
        //XYDataset data = new SampleXYDataset2();
        JFreeChart chart =
        	ChartFactory.createScatterPlot(
                null,    // the title of the chart
                x,     // the label for the X axis
                y,     // the label for the Y axis
                data,    // the dataset for the chart
                PlotOrientation.VERTICAL, // the orientation of the chart
                true,                     // a flag specifying whether or not a legend is required
                true,                     // a flag specifying whether or not tooltips should be generated
                false);                   // a flag specifying whether or not the chart should generate URLs
       
        XYPlot plot = (XYPlot)chart.getXYPlot();
  
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        /*
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);
             
        ValueAxis xAxis = plot.getDomainAxis();
        xAxis.setFixedDimension(50);

        ValueAxis yAxis = plot.getRangeAxis();
        */
        //StandardXYItemRenderer renderer = (StandardXYItemRenderer) plot.getRenderer();
        //renderer.setSeriesPaint(0, java.awt.Color.red);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(w, h));
    
        
        return chartPanel;
    } /* End of createXYScatterPlotChart*/

  	

  	/* Create an JPanel for scater plot XY Line and Shape of w and h size
  	 * 
  	 * @param data: the data matrix with XYDataset format
  	 * @param x: label for x-xis
  	 * @param y: label for y-xis
  	 * @param w : width of the JPanel
  	 * @param h : height of the JPanel
  	 * @return : a JPanel with a chart of JFreeChart type
  	 */
  	public static JPanel createXYLineAndShapeRendererChart(XYDataset data, String x, String y, int w, int h) {
        //XYDataset data = new SampleXYDataset2();
        JFreeChart chart =
        	ChartFactory.createScatterPlot(
                null,    // the title of the chart
                x,     // the label for the X axis
                y,     // the label for the Y axis
                data,    // the dataset for the chart
                PlotOrientation.VERTICAL, // the orientation of the chart
                true,                     // a flag specifying whether or not a legend is required
                true,                     // a flag specifying whether or not tooltips should be generated
                false);                   // a flag specifying whether or not the chart should generate URLs
  
        
        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0,true );
        
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);
        
        renderer.setSeriesLinesVisible(2, true);
        renderer.setSeriesShapesVisible(2, false);
                
        renderer.setToolTipGenerator(new StandardXYToolTipGenerator());
        renderer.setDefaultEntityRadius(6);
        plot.setRenderer(renderer);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(w, h));
 
        return chartPanel;
    } /* End of createXYLineAndShapeRendererChart*/
  	
 	
  	/* Create an empty JPanel with w and h size
  	 * @param w : width of the JPanel
  	 * @param h : height of the JPanel
  	 * @return : a JPanel
  	 */
  	public static JPanel createEmptyPanel(int w, int h) {
  		JPanel jp = new JPanel();
  		jp.setPreferredSize(new java.awt.Dimension(w,h));
        return jp;
    } /* End of createEmptyPanel*/
  	

    /* Load and save the whole panel */
    public void actionPerformed (ActionEvent ae) {      
       myAction = ae.getActionCommand();
      
       	if (myAction.equalsIgnoreCase("SAVEJPAG") ) {
       		//Container content = getContentPane();
       		content = getContentPane();
       		JFrame jframe = new OpenFileDir("Open File Directory","SAVEJPAG");
       		jframe.show(); 
       		
       		/* Modified here on 5/31/2005*/
       
       		//Container content = getContentPane();	
/*       		Component c = V4.getBottomComponent(); 
       		content.remove(c);

	
       		String fileName = OpenFileDir.JPEGFileName;
       		
       		//System.out.println ("JPG filename in SetPlot= " +fileName);
       		
       		File fFile = new File (fileName);
       		if (fFile.exists ()) {
       			int response = JOptionPane.showConfirmDialog (null,
       					"Overwrite existing file "+ fileName +" ??" ,"Confirm Overwrite",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);
       			if (response == JOptionPane.CANCEL_OPTION) {
       				 go back to reload the file
       				return;	             	
       			}
       		}
       		saveComponentAsJPEG(content, fileName);*/
       		
       	}  /*else if (myAction.equalsIgnoreCase("SAVEJPAG") ) {
       		Container content = getContentPane();
     		       		
       		Component c = V4.getBottomComponent(); 
       		content.remove(c);
  
       		
       		String fileName = OpenFileDir.JPEGFileName;
        		
			File fFile = new File (fileName);
	        if (fFile.exists ()) {
	             int response = JOptionPane.showConfirmDialog (null,
	               "Overwrite existing file "+ fileName +" ??" ,"Confirm Overwrite",
	                JOptionPane.OK_CANCEL_OPTION,
	                JOptionPane.QUESTION_MESSAGE);
	             if (response == JOptionPane.CANCEL_OPTION) {
	             	 go back to reload the file
	    	       	return;	             	
	             }
	         }
       		saveComponentAsJPEG(content, fileName);
       	} */
       	
    } /* End of actionPerformed*/

    
 	/* Save the content as jpeg file
 	 * 
  	 * @param myComponent : a component to be saveed
  	 * @param filename : image file name
  	 */
    
    public static void saveComponentAsJPEG(Component myComponent, String filename) {
        Dimension size = myComponent.getSize();
        BufferedImage myImage = new BufferedImage(size.width, size.height,
        		BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = myImage.createGraphics();
        myComponent.paint(g2);
        try {
          OutputStream out = new FileOutputStream(filename);
          JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
          encoder.encode(myImage);
          out.close();
        } catch (Exception e) {
          System.out.println(e); 
        }
      }  /* End of saveComponentAsJPEG*/

    
  /* Implements the WindowListener interface - only windowClosing() is non-trivial 
   * 
   */

  	public void windowClosing ( WindowEvent e ) {
    	Window w = e.getWindow();
    	w.setVisible(false);
    	w.dispose();
    	System.exit(0);
  	}
  	
  	public void windowActivated ( WindowEvent e ) {}
  	public void windowClosed ( WindowEvent e ) {}
  	public void windowDeactivated ( WindowEvent e ) {}
  	public void windowDeiconified ( WindowEvent e ) {}
  	public void windowIconified ( WindowEvent e ) {}
  	public void windowOpened ( WindowEvent e ) {}
	
}