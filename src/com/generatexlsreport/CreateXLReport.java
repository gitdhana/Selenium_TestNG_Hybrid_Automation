package com.generatexlsreport;

import java.util.ArrayList;

import com.config.CreatePropertiesObjects;
import com.logs.Logging;
import com.readxls.ReadingTestSuiteXLWithRunmode;
import com.readxls.XLReader;

public class CreateXLReport {
	
	public static int testCaseDataSetNumber=0;
	
	public static void insertResultSetInTestSteps(String testName,ArrayList<String> resultSet,XLReader xl, int lastRowExecuted){
		try{
			int resultSetIndex = resultSet.size();
			if(resultSetIndex > 0){
				// 5 columns are supposed to be present in teststeps sheet after this result columns will follow
				if(xl.getCellData(CreatePropertiesObjects.XL.getProperty("TEST_SUITE_TESTSTEPS_SHEET_NAME"), 5+testCaseDataSetNumber, 1).equals(""))
					xl.addColumn(CreatePropertiesObjects.XL.getProperty("TEST_SUITE_TESTSTEPS_SHEET_NAME"), "RESULT"+testCaseDataSetNumber);
				
				for(int rowNum=lastRowExecuted;rowNum >0; rowNum--){
					if(xl.getCellData(CreatePropertiesObjects.XL.getProperty("TEST_SUITE_TESTSTEPS_SHEET_NAME"), "TCID", rowNum).equals(testName) && resultSetIndex >= 0 ){
						xl.setCellData(CreatePropertiesObjects.XL.getProperty("TEST_SUITE_TESTSTEPS_SHEET_NAME"), "RESULT"+testCaseDataSetNumber, rowNum, resultSet.get(resultSetIndex));
						resultSetIndex--;
					}
						
				}
				
			}
		}catch(Exception e){
			Logging.log("Error occured while inserting result in TestSteps sheet for TestCase = " + testName);
			e.printStackTrace();
		}
		
		
	}

	public static void insertResultSetInTestStepsAsSkipped(String testName, XLReader xl){
		try{
			if(xl.getCellData(CreatePropertiesObjects.XL.getProperty("TEST_SUITE_TESTSTEPS_SHEET_NAME"), 5+testCaseDataSetNumber, 1).equals(""))
				xl.addColumn(CreatePropertiesObjects.XL.getProperty("TEST_SUITE_TESTSTEPS_SHEET_NAME"), "RESULT"+testCaseDataSetNumber);
			for(int rowNum=2; rowNum <= xl.getRowCount(CreatePropertiesObjects.XL.getProperty("TEST_SUITE_TESTSTEPS_SHEET_NAME"));rowNum++){			
				if(xl.getCellData(CreatePropertiesObjects.XL.getProperty("TEST_SUITE_TESTSTEPS_SHEET_NAME"), "TCID", rowNum).equals(testName))
					xl.setCellData(CreatePropertiesObjects.XL.getProperty("TEST_SUITE_TESTSTEPS_SHEET_NAME"), "RESULT"+testCaseDataSetNumber, rowNum, "SKIPPED");
			}
		}catch(Exception e){
			Logging.log("Error occured while inserting result in TestSteps sheet for TestCase = " + testName);
			e.printStackTrace();
		}
				
	}
	
	
	public static void deletePastResultSet(int lastRowExecuted,XLReader xl){
		if (lastRowExecuted==0){
			for(int i=1; i <= xl.getColumnCount(CreatePropertiesObjects.XL.getProperty("TEST_SUITE_TESTSTEPS_SHEET_NAME"));i++){
				if(xl.getCellData(CreatePropertiesObjects.XL.getProperty("TEST_SUITE_TESTSTEPS_SHEET_NAME"), i, 1).startsWith("RESU"))
					xl.removeColumn(CreatePropertiesObjects.XL.getProperty("TEST_SUITE_TESTSTEPS_SHEET_NAME"), i);
			}
			
		}
	}
}

