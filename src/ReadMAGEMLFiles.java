/************************************************************ 
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

import javax.swing.JFrame;

import org.biomage.BioAssayData.*;
import org.biomage.BioAssay.BioAssay;
import org.biomage.Common.*;
import org.biomage.tools.xmlutils.*;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import edu.unc.LCCC.caBIG.DWD.javaCode.processfile.PopUpTask;

/************************************************************* 
*	ReadMAGEMLFiles.java
*	This class lifted lots of code from MAGEMLImporter.java at MIT.
*	The author sincerely gave those cridits to the original author.
*
*	This class does:
*	1. Read in a MAGE-ML format file
*	2. Help funstions to get BioAssayData etc.
*
*	Version: v1.0
*	Author: Everett Zhou
*	Date: 8/11/2005
*
*************************************************************/
public class ReadMAGEMLFiles {
		public MAGEJava mageJava;

	   private String lsidAuthority = "ImportMAGEMLFiles.javaCode.DWD.caBIG.LCCC.unc.edu";

	   
	   /** The MAGE-ML input file */
	   private File baseDir;
	   private String fileBase;
	   private String inputFile;

	   private List bioAssayDataList ;
	   private List derivedBioAssayDataIDList ;
	   private List measuredBioAssayDataList ;
	   private List measuredBioAssayDataIDList ;
	   private List bioAssayList ;
	   private List bioDataCubeList ;
	   private List designElementList;
	   private List quantitationTypeList;
	   
	   public static int designElementType=2;
	   private String [][] data ; 
	   
	   
	   public ReadMAGEMLFiles (String fileBase, String inputFile) throws FileNotFoundException {
	   		this.fileBase = fileBase;
	   		this.inputFile =inputFile;
	   		
	        XMLReader parser = null;
	        MAGEContentHandler cHandler = null;

	        final String dtdFile = "C:/DWD/MAGE-ML.dtd";
	        
	        //System.out.println("The input file is "+ inputFile);
	        //System.out.println("The input file base "+ fileBase);
	        JFrame jfr = new PopUpTask ("<html>Parsing data, it may takes a few minutes</html>");
	        
	        try {
	    		
	     		jfr.show(); 

	        	parser = org.xml.sax.helpers.XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
	        	cHandler = new MAGEContentHandler();
      	        
	        	
 /* Valification massed up the reading
  				parser.setEntityResolver(
	 	               new org.xml.sax.EntityResolver() {
	 	                  public org.xml.sax.InputSource resolveEntity(String publicId, String systemId) {
	 	                  	System.out.println("Here31"); 
	 	                  	System.out.println ("systemId "+ systemId);
	 	                  	
	 	                  	if(systemId.toLowerCase().endsWith(".dtd")) {
	 	                        java.io.FileInputStream fis = null;
	 	                        try {
	 	                           fis = new java.io.FileInputStream(dtdFile);
	 	                           System.out.println("Here32");
	 	                        } catch(Exception e) {
	 	                        	System.out.println("Unable to load the dtd");
	 	                           System.exit(0);
	 	                        }
	 	                        return new org.xml.sax.InputSource(fis);
	 	                     }
	 	                     return null;
	 	                  }
		 	         });
*/		
	            parser.setContentHandler(cHandler);
	            parser.parse(new InputSource(new FileReader(inputFile)));


	         } catch(OutOfMemoryError e) {
	         	System.out.println("Not enough memory available to read the MAGE-ML file.");
	            System.exit(0);
	         } catch(Throwable t) {
	         	System.out.println("An error occurred while parsing the MAGE-ML file");
	         	System.out.println(t);
	         	System.exit(0);
	         }	
	         
	        mageJava = ((MAGEContentHandler) cHandler).getMAGEJava();
	        jfr.dispose();
	        
	        System.out.println("Parsing data is finished successfully");

	   }/*End of the constructor*/

	   
/*	    To get QuantitationTypeDimension from BioAssayData_package
	    * @param bioAssayDataP: BioAssayData_package
	    * @return: a string array with QuantitationTypeDimension
	    
	   private String[] getQuantitationTypeDimension(BioAssayData_package bioAssayDataP){
	   		System.out.println("QuantitationTypeDimension ="
				+ bioAssayDataP.getQuantitationTypeDimension_list().size());
	   		String[] QTD = new String[bioAssayDataP.getBioAssayData_list().size()];
	   		for (int i = 0; i < bioAssayDataP.getBioAssayData_list().size(); i++) {
	   			QTD[i] =bioAssayDataP.getFromBioAssayData_list(i)
					.getQuantitationTypeDimension().getIdentifier();
			}
	   		return QTD;
		}
*/
	   
/*	    To get QuantitationTypes from QuantitationType_package
	    * @param quantitationTypeP: QuantitationType_package
	    
	   private void getQuantitationTypes(QuantitationType_package quantitationTypeP){
	   		quantitationTypeList = new ArrayList();
			for (int i = 0; i < quantitationTypeP.getQuantitationType_list().size(); i++) {
				quantitationTypeList.add(quantitationTypeP.getFromQuantitationType_list(i).getIdentifier());
			}
	    }
*/
	   
/*	    To get QuantitationTypes from the list of bioAssayDataList
	    * @param bioAssayDataList: the list of BioAssayData
	    
	   private void getQuantitationTypes(List bioAssayDataList){
	   		quantitationTypeList = new ArrayList();
	   		BioAssayData bioAssayData;
			for (int i = 0; i < bioAssayDataList.size(); i++) {
				bioAssayData = (BioAssayData)bioAssayDataList.get(i);
				quantitationTypeList.add (bioAssayData.getQuantitationTypeDimension().getFromQuantitationTypes(i).getIdentifier());
			}
	    }*/
	   

	   /* To get QuantitationTypes from a BioAssayData
	    * @param bioAssayDataList: a BioAssayData
	    * @return : An array of QuantitationTypes
	    */
	   public static String []  getQuantitationTypes( BioAssayData bioAssayData){
		   	int numQuantitationTypes= bioAssayData.getQuantitationTypeDimension().getQuantitationTypes().size();
	   		String quantitationType [] = new String [numQuantitationTypes];
	   		
			for (int i = 0; i < numQuantitationTypes; i++) {
				quantitationType[i]= bioAssayData.getQuantitationTypeDimension().getFromQuantitationTypes(i).getIdentifier();
			}
			return quantitationType;
			
	    }

	   /* To get the list for BioAssayData from BioAssayData_package
	    * @param bioAssayDataP: BioAssayData_packag
	    * @return : list of BioAssayData
	    */
	   public List getBioAssayDataList (BioAssayData_package bioAssayDataP){
	   		
	       	List bioAssayDataList = new ArrayList() ;
	       	BioAssayData bioAssayData = null;

	   		for (int i = 0; i < bioAssayDataP.getBioAssayData_list().size(); i++) {
	   			bioAssayData = bioAssayDataP.getFromBioAssayData_list(i);
				bioAssayDataList.add(bioAssayData);
	   		}	   	
	   		return bioAssayDataList;
	   	}

	   /* To get the list for DerivedBioAssayData from BioAssayData_package
	    * @param bioAssayDataP: BioAssayData list
	    * @return : list of DerivedBioAssayData
	    */
	   public List getDerivedBioAssayDataList (List bioAssayDataList){
	   		
	       	List derivedBioAssayDataList = new ArrayList() ;
	       	BioAssayData bioAssayData = null;

	   		for (int i = 0; i < bioAssayDataList.size(); i++) {
	   			bioAssayData =  (BioAssayData)bioAssayDataList.get(i);
	          	if((bioAssayData instanceof DerivedBioAssayData)) {
	          	 	derivedBioAssayDataList.add(bioAssayData);	          	 	//bioAssayDataIdentifier
	           } 
	   		}	   	
	   		return derivedBioAssayDataList;
	   	}
	   
	   /* To get the list for MeasuredBioAssayData from BioAssayData_package
	    * @param bioAssayDataP: BioAssayData list
	    * @return : list of MeasuredBioAssayData
	    */
	   public List getMeasuredBioAssayDataList (List bioAssayDataList){
	   		
	       	List measuredBioAssayDataList = new ArrayList() ;
	       	BioAssayData bioAssayData = null;

	   		for (int i = 0; i < bioAssayDataList.size(); i++) {
	   			bioAssayData =  (BioAssayData)bioAssayDataList.get(i);
	          	if ((bioAssayData instanceof MeasuredBioAssayData)){
	             	measuredBioAssayDataList.add(bioAssayData);
	            }
	   		}	   	
	   		return measuredBioAssayDataList;
	   	}

	   
	   
	   /* To get the array of names of BioAssay for a particular BioAssayData
	    * @param bioAssayData: BioAssayData
	    * @return: the string array of of names of BioAssay
	    */
	   public static String [] getBioAssayNameList (BioAssayData bioAssayData){
	   			    	       	
	       	BioAssay bioAssay =null;
	       	String bioAssayName;
	       	
			BioAssayDimension bioAssayDimension = bioAssayData.getBioAssayDimension();
			String bioAssayNameList[] = new String [bioAssayDimension.getBioAssays().size()];
	        for(int j = 0; j < bioAssayDimension.getBioAssays().size(); j++) {
	            bioAssay = bioAssayDimension.getFromBioAssays(j);
	            if (bioAssay.getName()!=null){
	            	bioAssayNameList[j] = (String) bioAssay.getName();
	            } else if (bioAssay.getIdentifier()!=null){
	            	bioAssayNameList[j] = (String) bioAssay.getIdentifier();
	            } else {
	            	bioAssayNameList[j] = "This BioAssay";
	            }	           
	         }	  	   	
	   		return bioAssayNameList;
	   	}

	   
	   /* To get DesignElements list and its size from BioAssayData_package
	    * @param designElementDimension: DesignElementDimension
	    * @return: the list of DesignElements
	    */	 
	   public static List getDesignElementsList(DesignElementDimension designElementDimension) {
	      List designElements;
	      if(designElementDimension instanceof ReporterDimension) {
	      	 designElementType=1;
	      	 //System.out.println("DesignElement is ReporterDimension");
	         designElements = ((ReporterDimension) designElementDimension).getReporters();
	      } else if(designElementDimension instanceof FeatureDimension) {
	      	 designElementType=2;
	      	 //System.out.println("DesignElement is FeatureDimension");
	      	 designElements = ((FeatureDimension) designElementDimension).getContainedFeatures();
	      } else {
	      	 designElementType=3;
	      	 //System.out.println("DesignElement is CompositeSequenceDimension");
	         designElements = ((CompositeSequenceDimension) designElementDimension).getCompositeSequences();
	      }
	      return designElements;
	   }


	   /* To get DesignElements list and its size from BioAssayData_package
	    * @param designElementDimension: DesignElementDimension
	    * @return: the number of DesignElements
	    */	 
	   public static int getCountDesignElements(DesignElementDimension designElementDimension) {
	      int designElements;
	      if(designElementDimension instanceof ReporterDimension) {
	         designElements = ((ReporterDimension) designElementDimension).getReporters().size();
	      } else if(designElementDimension instanceof FeatureDimension) {
	         designElements = ((FeatureDimension) designElementDimension).getContainedFeatures().size();
	      } else {
	         designElements = ((CompositeSequenceDimension) designElementDimension).getCompositeSequences().size();
	      }
	      return designElements;
	   }

	   
	   public void postProcessMAGEMLFile (String outputFile){
		   	FileReader fr = null;
		   	String s;
			try {
				fr = new FileReader (outputFile);
				BufferedReader br = new BufferedReader (fr);
				System.out.println("postprocess 1");
				String ch;
				if ((s=br.readLine()) != null) {
					//System.out.println("postprocess 2");
					//s=s+ "damn";
					s.replaceAll(">",">\n");
					//char ch;
/*					for (int i=0; i<s.length()-1;i++){
						ch =s.substring(i,i+1);
						if (ch.equalsIgnoreCase(">")){
							System.out.println("postprocess 2");
							//System.out.println ();
							
						}
					}*/
				}
				System.out.println("postprocess 3");
				
				fr.close();	
				
			    PrintWriter writer = new PrintWriter(new FileWriter (outputFile+"1", false));		 	
				
		 	    /* For the title first */
				/* Store key name */
				writer.print(s);
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	   }

	   	   
	   
	   public void setMAGEMLFile(String inputFile) {
	   		this.inputFile =inputFile;   
	   }
	   
	   public String getMAGEMLFile() {
	   		return inputFile;   
	   }
	   
}

