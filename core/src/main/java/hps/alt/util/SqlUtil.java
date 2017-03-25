package hps.alt.util;

import java.util.ArrayList;
import java.util.List;

import hps.alt.dto.AlertInput;
import hps.alt.dto.AlertOutput;

public class SqlUtil {
		
	
	public List getInputOutput(String sql){
		List list = new ArrayList();
		
		List<AlertInput> inputList = new ArrayList<AlertInput>();
		List<AlertOutput> outputList = new ArrayList<AlertOutput>();
		AlertOutput alertOutput = null;
		AlertInput alertInput = null;
		String[] sql2 = sql.split("&");
		for (int i = 1; i < sql2.length-1; i++) {
			String[] sql3 = sql2[i].split(",");
			alertOutput = new AlertOutput();
			alertOutput.setOutputName(sql3[0]);
			outputList.add(alertOutput);
		}
		String[] sql4 = sql2[sql2.length-1].split(":");
		String[] sql5 = sql4[0].split("(?i)from");
		alertOutput = new AlertOutput();
		alertOutput.setOutputName(sql5[0]);
		outputList.add(alertOutput);
		if (sql.length() > 1) {
			for (int i = 1; i < sql4.length-1; i++) {
				String[] sql6 = sql4[i].split("(?i)and");
				alertInput = new AlertInput();
				alertInput.setInputName(sql6[0]);
				inputList.add(alertInput);
			}
		}
		alertInput = new AlertInput();
		alertInput.setInputName(sql4[sql4.length-1]);
		inputList.add(alertInput);
		
		list.add(0, inputList);
		list.add(1, outputList);
		return list;		
	}
	public List getVariable(String sql){
		List variables = new ArrayList();
		String[] sql2 = sql.split(" INTO");
		String[] sql3 = sql2[0].split("SELECT ");
		if(sql3[1].indexOf(",") != -1){
			String[] tempVariable = sql3[1].split(",");
			for(int i=0;i<tempVariable.length;i++){
				variables.add(tempVariable[i]);
			}
			
		}else{
			variables.add(sql3[1]);
		}
		
		
		return variables;
	}

}
