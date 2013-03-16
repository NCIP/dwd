/************************************************************* 
 *	DWD (Distance Weighted Discrimination)
 *	
 *	This software is developed by LCCC and Statistics at UNC
 *	It is supported by caBIG from NCI and is Open source 
 *
 *************************************************************/
 
package edu.unc.LCCC.caBIG.DWD.javaCode.ImportMAGEMLFiles;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


import org.biomage.BioAssay.BioAssay;
import org.biomage.BioAssay.BioAssay_package;
import org.biomage.BioAssay.DerivedBioAssay;
import org.biomage.BioAssayData.*;
import org.biomage.Common.MAGEJava;
import org.biomage.DesignElement.CompositeSequence;
import org.biomage.DesignElement.DesignElement_package;
import org.biomage.DesignElement.FeatureReporterMap;
import org.biomage.DesignElement.Reporter;
import org.biomage.QuantitationType.QuantitationType;
import org.biomage.QuantitationType.QuantitationType_package;
import org.biomage.QuantitationType.SpecializedQuantitationType;

/************************************************************* 
 * ExportMAGEFiles.java
 *
 * This file will do:
 *	
 *	1. Export adjusted data into MAGE-ML file
 *
 *	Version: v1.0
 *	Author: Everett Zhou
 *	Date: 10/19/2005
 *
 *************************************************************/
 
public class ExportMAGEFiles {
	
	 private String selectedQT1, selectedQT2;
	 
	 private String	bioAssayNameList1[],bioAssayNameList2[];
	 private List commonDesignElement;
	 private String externalFileName;
	 
	 public ExportMAGEFiles (String selectedQT1, String selectedQT2, 
	 		String bioAssayNameList1 [],String bioAssayNameList2 [], List commonDesignElement, String externalFileName){
	 	
	 	this.selectedQT1 = selectedQT1;
	 	this.selectedQT2 = selectedQT2; 
	 	this.bioAssayNameList1 = bioAssayNameList1;
	 	this.bioAssayNameList2 = bioAssayNameList2;
	 	this.commonDesignElement = commonDesignElement;
	 	this.externalFileName=externalFileName;
	 }
	 
	   /* To set up MAGE-ML packages for exporting 
	    * @param out: PrintStream
	    */	
	 private void exportAdjustedDataToMageML (PrintStream out) throws IOException{
	
	 		MAGEJava mageJava = new MAGEJava ();
	   		
	   		/* find the mergerd QuantitationType*/
	   		String mergedQT;
	   		if (selectedQT1.equalsIgnoreCase(selectedQT2)){
	   			mergedQT = selectedQT1;
	   		} else {
	   			mergedQT = "DWDMergedQT";
	   		}
	   	
	   		/* set up QuantitationType_package*/
	       QuantitationType_package newQuantitationTypeP = new QuantitationType_package ();
	       QuantitationType newQuantitationType = new SpecializedQuantitationType ();
	       newQuantitationType.setIdentifier(mergedQT);
	       newQuantitationTypeP.addToQuantitationType_list(newQuantitationType);
	       mageJava.setQuantitationType_package(newQuantitationTypeP);
	      
	       BioAssay_package newBioAssayP = new BioAssay_package();
	       
	       BioAssayData_package newBioAssayDataP = new BioAssayData_package();
	       
	       BioAssayDimension bioAssayDimension = new BioAssayDimension ();
	       BioAssayData bioAssayData =new DerivedBioAssayData(); 
	
	       DataExternal dataExternal =new DataExternal();
	       String uri= externalFileName;
	       dataExternal.setFilenameURI(uri);
	       
	       BioDataCube bioDataCube = new BioDataCube();
	       
	       bioDataCube.setDataExternal(dataExternal);
	       bioDataCube.setNameOrder("BDQ");
	       
	       bioAssayData.setBioDataValues(bioDataCube);
	       
	       DesignElement_package designElementP = new DesignElement_package();
	       
	       if (ReadMAGEMLFiles.designElementType ==1) {
	       		for (int i=0; i<commonDesignElement.size();i++){
	       			Reporter reporter = new Reporter();
	       			reporter.setIdentifier((String)commonDesignElement.get(i));
	       			designElementP.addToReporter_list(reporter);
	       		}
	
	       }else if (ReadMAGEMLFiles.designElementType ==2) {
	 		   	for (int i=0; i<commonDesignElement.size();i++){
	 		   		FeatureReporterMap featureReporterMap = new FeatureReporterMap();
	 		   		//Feature feature = new Feature();
		   			//feature.setIdentifier((String)commonDesignElement.get(i));
	 		   		featureReporterMap.setIdentifier ((String)commonDesignElement.get(i));
		   			//Reporter reporter = (Reporter)featureReporterMap.getReporter();
		   			//designElementP.addToFeature_list (feature);
		   			designElementP.addToFeatureReporterMap_list(featureReporterMap);
		   		}
		   		
	       } else if (ReadMAGEMLFiles.designElementType ==3) {
		   		for (int i=0; i<commonDesignElement.size();i++){
		   			CompositeSequence compositeSequence = new CompositeSequence();
		   			compositeSequence.setIdentifier((String)commonDesignElement.get(i));
		   			designElementP.addToCompositeSequence_list(compositeSequence);
		   		}
	 		}
		   
	       
	       
	       //BioAssay newBioAssay = new DerivedBioAssay();
	       
	       //Vector newBioAssay = new HasBioAssays.BioAssays_list();
	       
	       for (int i=0; i<bioAssayNameList1.length; i++ ){
	       		BioAssay newBioAssay = new DerivedBioAssay();
	       		newBioAssay.setIdentifier(bioAssayNameList1[i]);
	       		//System.out.println("1 " +bioAssayNameList1[i]);
	       		//bioAssayDimension.addToBioAssays(newBioAssay);
	       		newBioAssayP.addToBioAssay_list(newBioAssay);
	       }
	       
	       //bioAssayDimension.addToBioAssays(newBioAssay);
	       
	       //newBioAssayP.addToBioAssay_list(newBioAssay);
	       
	       for (int i=0; i<bioAssayNameList2.length; i++ ){
	       		BioAssay newBioAssay = new DerivedBioAssay();
	   			newBioAssay.setIdentifier(bioAssayNameList2[i]);
	   			//System.out.println("2 " + bioAssayNameList2[i]);
	   			newBioAssayP.addToBioAssay_list(newBioAssay);
	   			/*bioAssayDimension.addToBioAssays(newBioAssay);
	   			newBioAssayDataP.addToBioAssayData_list(bioAssayData);*/
	       }
	       
	       //bioAssayDimension.addToBioAssays(newBioAssay);
	       
	       //bioAssayData.setBioAssayDimension(bioAssayDimension);
	       newBioAssayDataP.addToBioAssayData_list(bioAssayData);
	       
	       //newBioAssayP.addToBioAssay_list(newBioAssay);
	       //mageJava.setBioAssay_package(newBioAssayP);
	       mageJava.setBioAssayData_package(newBioAssayDataP);
	       mageJava.setDesignElement_package(designElementP);
	       mageJava.setBioAssay_package(newBioAssayP);
			OutputStreamWriter writer = new OutputStreamWriter(out);

			mageJava.writeMAGEML(writer);
			writer.flush();
	 	} /*end of exportAdjustedDataToMageML*/


	   /* To write MAGE-ML packages to MAGE-ML file 
	    * @param outputFileName: output file
	    */	  
	   public void writeToMAGEML (String outputFileName)throws IOException{
        File outputFile = new File (outputFileName);
    	PrintStream out = new PrintStream(new BufferedOutputStream(
        		new FileOutputStream(outputFile)));
    	
        try {
        	exportAdjustedDataToMageML (out);
			//OutputStreamWriter writer = new OutputStreamWriter(out);
	
			//String dtd = getDtd();
			//if (dtd != null) {
			//	System.out.println("<!DOCTYPE MAGE-ML SYSTEM \"" + dtd + "\">");
			//}
/*			mageJava.writeMAGEML(writer);
			writer.flush();*/
		} finally {
			out.flush();
			out.close();			
		}
  	
	   }

	   /* To test export package 
	    * @param args: online input
	    */	
	   public static void main(String[] args) throws FileNotFoundException {
		   	String outputFile = "C:\\DWD\\DWDdata\\NewArrayDesign_package.xml";
	   		
	   		File outFile = new File (outputFile);
	   		String fileBase = outFile.getParent();
	   		String  externalFileName =fileBase+"ExternalAdjustedDataFile.txt";
	   		
	   		String selectedQT1 ="QT";
	   		String selectedQT2 ="QT"; 
		 	String bioAssayNameList1 [] = {"A1", "A2", "A3"};
		 	String bioAssayNameList2 [] = {"B1", "B2", "B3"};
		 	List commonDesignElement = new ArrayList ();
		 	commonDesignElement.add("c1");
		 	commonDesignElement.add("c2");
		 	commonDesignElement.add("c3");
		 	commonDesignElement.add("c4");

	   		ExportMAGEFiles exportMAGEFiles =new ExportMAGEFiles( selectedQT1, selectedQT2, 
	   		 		bioAssayNameList1,bioAssayNameList2, commonDesignElement,externalFileName);

	   		try {
				/*	   		exportMAGEFiles.exportAdjustedDataToMageML(selectedQT1, selectedQT2, 
				 		bioAssayNameList1,bioAssayNameList2, commonDesignElement, fileBase1);
*/	   		
				exportMAGEFiles.writeToMAGEML (outputFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   		
	   }

}