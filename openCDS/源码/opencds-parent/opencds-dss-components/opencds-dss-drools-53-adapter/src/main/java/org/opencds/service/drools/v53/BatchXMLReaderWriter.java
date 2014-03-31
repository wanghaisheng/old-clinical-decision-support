/**
 * Testing package for running batches of vMR records that are stacked in a single file
 * against the same KM, and producing separate output files for each vMR.
 * 
 * This is a temporary throw-away module, not designed to be part of a production system.
 */
package org.opencds.service.drools.v53;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import org.omg.dss.DSSRuntimeExceptionFault;
import org.omg.dss.EvaluationExceptionFault;
import org.omg.dss.InvalidDriDataFormatExceptionFault;
import org.omg.dss.InvalidTimeZoneOffsetExceptionFault;
import org.omg.dss.RequiredDataNotProvidedExceptionFault;
import org.omg.dss.UnrecognizedLanguageExceptionFault;
import org.omg.dss.UnrecognizedScopedEntityExceptionFault;
import org.omg.dss.UnsupportedLanguageExceptionFault;
import org.opencds.common.structures.DSSRequestItem;
import org.opencds.common.structures.TimingData;
import org.opencds.common.utilities.FileUtility;
import org.opencds.common.utilities.StringToArrayUtility;
import org.opencds.common.utilities.WriteUtility;
import org.opencds.service.evaluate.DSSEvaluateAdapterShell;

/**
 * @author David Shields
 * 
 * @date 1/31/2012
 *
 */
public class BatchXMLReaderWriter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileUtility myFu = FileUtility.getInstance();
		String inputDataPath = "C:/OpenCDS/input/openCDSInputData.xml";
		String inputData = "";
		File inputFile = new File(inputDataPath); 
		try {
			inputData = myFu.getFileContentsAsString(inputFile);
		} catch (DSSRuntimeExceptionFault e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		WriteUtility myWu = WriteUtility.getInstance();
		StringToArrayUtility myS2au = StringToArrayUtility.getInstance();
	
		String START_TAG = "<in:cdsInput ";
		String END_TAG = "</in:cdsInput>";
		int beginIndex = 0;
		int endIndex = 0;
		int counter = 0;
		boolean lookForMore = true;
		do {
			beginIndex = inputData.indexOf(START_TAG, endIndex);
			if ( beginIndex >= 0) {
				endIndex = inputData.indexOf(END_TAG, beginIndex) + END_TAG.length();
				if ( endIndex > beginIndex) {
					String payloadString = inputData.substring(beginIndex, endIndex);
						
					DSSRequestItem	dSSRequestItem = new DSSRequestItem(); 
					dSSRequestItem.setInputPayloadString(payloadString);		
//					decodedDSSRequest.setRequestedKmId("org.opencds^bounce^1.5.3");
					dSSRequestItem.setRequestedKmId("org.opencds^Reportable_Disease^1.0.0");
					dSSRequestItem.setExternalFactModelSSId("org.opencds.vmr^VMR^1.0");
					dSSRequestItem.setEvalTime(new Date());
					dSSRequestItem.setClientLanguage("");
					dSSRequestItem.setClientTimeZoneOffset("");
					TimingData timingData = new TimingData();
					dSSRequestItem.setTimingData(timingData);
					timingData.setStartInferenceEngineAdapterTime(System.currentTimeMillis());
					
					try {
						DSSEvaluateAdapterShell.getInstance();  //initializes KR
						String structuredDroolsResult = DroolsAdapter.getInstance().getResponse( 
								dSSRequestItem);
						
//						System.out.println(structuredDroolsResult);
						ArrayList<String> linesOfResultXml = myS2au.getRegularArrayFromString(structuredDroolsResult, "\n");
						counter++;
						File outputData = new File("C:/OpenCDS/output/openCDSOutputData-" + counter + ".xml");
						myWu.addLinesToFile(linesOfResultXml, outputData);
						
						
					} catch (InvalidDriDataFormatExceptionFault e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					} catch (UnrecognizedLanguageExceptionFault e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					} catch (RequiredDataNotProvidedExceptionFault e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					} catch (UnsupportedLanguageExceptionFault e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					} catch (UnrecognizedScopedEntityExceptionFault e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					} catch (EvaluationExceptionFault e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					} catch (InvalidTimeZoneOffsetExceptionFault e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					} catch (DSSRuntimeExceptionFault e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					}	
					
					System.out.println("row # " + counter);
					
					if ( endIndex + START_TAG.length() + END_TAG.length() > inputData.length() ) {
						lookForMore = false;
					}
				} else {
					lookForMore = false;
				}
			} else {
				lookForMore = false;
			}
		} while (lookForMore); 
		
		System.out.println("BatchXMLReaderWriter finished");

	}

}
