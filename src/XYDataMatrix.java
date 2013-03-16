/************************************************************* 
 *	DWD (Distance Weighted Discrimination)
 *	
 *	This software is developed by LCCC and Statistics at UNC
 *	It is supported by caBIG from NCI and is Open source 
 *
 *************************************************************/

package edu.unc.LCCC.caBIG.DWD.javaCode.visualization;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/************************************************************* 
*
*	XYDataMatrix.java
*	Prepare the data matrix for Scatter Plot and Line and Shape plot
*
*	Version: v1.0
*	Author: Everett Zhou
*	Date: 2/15/2005
*
*************************************************************/


public class XYDataMatrix  {

	/*the original matrix for display*/
	private double originalMatrix [][];
	
	/*the kde matrix for diagonal display*/
	private double kde [][];
	
	/* The rows of the original matrix used to create x and y vlaues*/
    private int xDimesion;
    private int yDimesion;
    
    /** The number of items. */
    private int itemCount1;
    private int itemCount2;

    /*the minimum and maxium values for kde matrix*/
    private static double [][] minMaxValueOfKde;
    
 
    /*
     * constructor.
     * 
     * @param kde: kde matrix
     * 
     */
 
    public XYDataMatrix(double kde[][]) {
    	/*initilize the minMaxValueOfKde*/
       	getminMaxValueFromKde (kde); 
    }

    
    /*
     * Creates a dataset only  for scatter plot.
     * 
     * @param originalMatrix: original matrix
     * @param PCDimension1: the row in the originalMatrix is used to create the x values
     * @param PCDimension2: the row in the originalMatrix is used to create the y values
     * @param itemCount1: the number of samples from the first source
     * @param itemCount2: the number of samples from the second source
     * 
     */
  	
  	public XYDataset createXYScatterPlotDataset(double originalMatrix [][],int PCDimesion1,int PCDimesion2, int itemCount1, int itemCount2) {
        XYSeries series1 = new XYSeries("Source 1");
        for (int i=0; i<itemCount1; i++){
        	series1.add (originalMatrix [PCDimesion1][i],originalMatrix [PCDimesion2][i] );
        }

        XYSeries series2 = new XYSeries("Source 2");
        for (int i=itemCount1; i<originalMatrix [0].length; i++){
        	series2.add (originalMatrix [PCDimesion1][i],originalMatrix [PCDimesion2][i] );
        }
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
       
        return dataset;
    } /* End of createXYScatterPlotDataset*/   

  	
    /*
     * Creates a dataset only  for line and shape plot.
     * 
     * @param originalMatrix: original matrix
     * @param kde: the kde matrix
     * 				The first row is xgrid and the second row is its corresponding kde 
     * @param PCDimension: the row in the originalMatrix is used to create the x values
     * @param itemCount1: the number of samples from the first source
     * @param itemCount2: the number of samples from the second source
     * 
     */
  	
  	public XYDataset createXYLineAndShapeDataset(double originalMatrix [][],double [][] kde, int PCDimesion, int itemCount1, int itemCount2) {
  		XYSeries series1 = new XYSeries("Series 1");
        for (int i=0; i<itemCount1; i++){
        	series1.add (originalMatrix [PCDimesion][i], getRandomValue (kde,PCDimesion) );
        }
       
        XYSeries series2 = new XYSeries("Series 2");
        for (int i=itemCount1; i<originalMatrix [0].length; i++){
        	series2.add (originalMatrix [PCDimesion][i], getRandomValue (kde,PCDimesion) );
        }
 
        XYSeries series3 = new XYSeries("Combined");
        for (int i=0; i< kde[0].length; i++){
        	series3.add (kde [PCDimesion*2][i],kde [PCDimesion*2+1][i] );
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        return dataset;
    } /* End of createXYLineAndShapeDataset*/
 
  	
    /*
     * get a rondom value within the kde range.
     * 
     * @param originalMatrix: original matrix
     * @param kde: the kde matrix
     * 				The first row is xgrid and the second row is its corresponding kde 
     * @return: rondom value with the kde range
     * 
     */
  	public static double  getRandomValue (double [][] kde, int PCDimesion) {
  		
  		double min = minMaxValueOfKde [PCDimesion*2 +1][0];
  		double max = minMaxValueOfKde [PCDimesion*2 +1][1];
  		double myMin = (min+max)/5;
  		double myMax = max -max *0.5;
  		double temp;
  		if (myMin >myMax){
  			temp =myMax;
  			myMax = myMin;
  			myMin=temp;  			
  		}
  		
  		
  		double x = Math.random();
  		if (myMax!=0.00 && myMin !=0.00) {
  			while (x>= myMax && x>=myMin){
  	  			x =x-myMax;
  	  		}
  	  		while (x<=myMax){
  	  			x =x+myMin;
  	  		} 			
  		}
  		
  		return x;
  		
  	}/* End of getRandomValue*/
  
  	
    /*
     * get a min and max values fromeach kde row.
     * 
     * @param kde: the kde matrix
     * 				The first row is xgrid and the second row is its corresponding kde 
     * @return: rondom value with the kde range
     * 
     */
  	public static void getminMaxValueFromKde (double [][] kde) {
  		minMaxValueOfKde = new double[8][2];
  		double min, max;
  		for (int i =0; i<kde.length ; i++){
  	 		min = 0;
  	  		max = 0;
  	 		for (int j =0; j<kde[0].length ; j++){
  	 			if (min > kde [i][j]) {
  	 				min = kde [i][j];
  	 			}
  	 			if (max <kde [i][j]) {
  	 				max = kde [i][j];
  	 			}
  	 		}
  	 		minMaxValueOfKde [i][0] = min;
  	 		minMaxValueOfKde [i][1] = max;
  		}

  	}/* End of getRandomValue*/ 	
 
 
    public static void printXYData (String xisLabel, double xyData [][]){
  	   	System.out.println (xisLabel);
  	   	for (int i=0; i<xyData.length; i++) {
  	   		for (int j=0; j<xyData[0].length; j++) {
  	   		
  	   			System.out.print (Math.ceil (xyData[i][j]*1000)/1000.0 +" ");  
  	   		}
  	   		System.out.println ();
  	   	} 
  	   	System.out.println ();
    }
  	
    public static void printXYData (String xisLabel, Double xyData [][]){
  	   	System.out.println (xisLabel);
  	   	for (int i=0; i<xyData.length; i++) {
  	   		for (int j=0; j<xyData[0].length; j++) {
  	   		
  	   			System.out.print (Math.ceil (xyData[i][j].doubleValue()*1000)/1000.0 +" ");  
  	   		}
  	   		System.out.println ();
  	   	} 
  	   	System.out.println ();
    }
 
}
