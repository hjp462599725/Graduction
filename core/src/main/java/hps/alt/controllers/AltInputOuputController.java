package hps.alt.controllers;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hps.alt.dto.AlertInput;
import hps.alt.dto.AlertOutput;
import hps.alt.dto.AltAlert;
import hps.alt.service.IAlertInputService;
import hps.alt.service.IAlertOutputService;
import hps.alt.service.IAlertService;
import hps.alt.util.SqlUtil;



@Controller
public class AltInputOuputController extends BaseController{
	
	@Autowired
	private IAlertService alertService;
	
	@Autowired
	private IAlertInputService alertInputService;
	
	@Autowired
	private IAlertOutputService alertOutputService;

	
	
	/**
	 * @description  SELECT语句保存
	 * @param altAlert
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/alt/detail/save")
	@ResponseBody
	public ResponseData submitAlert(AltAlert altAlert,HttpServletRequest request){
			
			String sql = null;		
			sql = altAlert.getSqlStatementText();
			SqlUtil sqlUtil = new SqlUtil();
			List list = sqlUtil.getInputOutput(sql);
			
			alertService.insertAlert(altAlert);			
			Long alertId = altAlert.getAlertId();
			if (alertId != null) {
				insertAlertInputName(alertId, list);
				insertAlertOutputName(alertId, list);
			}
			List<AltAlert> alertList = new ArrayList<AltAlert>();
			alertList.add(altAlert);
		return new ResponseData(alertList);
	}
	
	/**
	 * @description SELECT语句修改
	 * @param altAlert
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/alt/detail/update")
	public ResponseData updateAlert(AltAlert altAlert,HttpServletRequest request){
		
		IRequest requestCtx = createRequestContext(request);
		String sql = altAlert.getSqlStatementText();
		AltAlert alt = alertService.selectByPrimaryKey(requestCtx, altAlert);
		
		alertService.updateAlert(altAlert);
		
		if (!(sql.equals(alt.getSqlStatementText()))) {
			SqlUtil sqlUtil = new SqlUtil();
			List list = sqlUtil.getInputOutput(sql);
			Long alertId = altAlert.getAlertId();
			
			updateAlertInputName(alertId, list);
			updateAlertOutputName(alertId,list);
		} 
		List<AltAlert> alertList = new ArrayList<AltAlert>();
		alertList.add(altAlert);
		return new ResponseData(alertList);
	}
	
	public void updateAlertInputName(Long alertId, List list){
		alertInputService.deleteAlertInput(alertId);
		insertAlertInputName(alertId, list);
	}
	
	public void updateAlertOutputName(Long alertId, List list){
		alertOutputService.deleteAlertOutput(alertId);
		insertAlertOutputName(alertId, list);
	}
	
	public void insertAlertInputName(Long alertId, List list){
		List<AlertInput> inputList = (List<AlertInput>) list.get(0);
		for (AlertInput alertInput : inputList) {
			alertInput.setAlertId(alertId);
			alertInputService.insertAlertInput(alertInput);
		}
	}
	public void insertAlertOutputName(Long alertId, List list){
		List<AlertOutput> outputList = (List<AlertOutput>) list.get(1);
		for (AlertOutput alertOutput : outputList) {
			alertOutput.setAlertId(alertId);
			alertOutputService.insertAlertOutput(alertOutput);
		}
	}
	
	@RequestMapping(value = "/alt/output/query")
	public ResponseData altOutputQuery(AlertOutput alertOutput ,HttpServletRequest request,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize){
	
		return new ResponseData(alertOutputService.select(null, alertOutput, page, pagesize));
		
	}
	@RequestMapping(value = "/alt/input/query")
	public ResponseData altInputQuery(AlertInput alertInput,HttpServletRequest request,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize){
		
		return new ResponseData(alertInputService.select(null, alertInput, page, pagesize));
		
	}
	
	@RequestMapping(value = "alt/input/submit")
	public ResponseData submitInput(@RequestBody List<AlertInput> alertInputs, HttpServletRequest request){
		for (AlertInput alertInput : alertInputs) {
			alertInputService.updateAltInput(alertInput);
		}
		return new ResponseData();
	}
	
	@RequestMapping(value = "alt/output/submit")
	public ResponseData submitOutput(@RequestBody List<AlertOutput> alertOutputs, HttpServletRequest request){
		IRequest requestCtx = createRequestContext(request);
		return new ResponseData(alertOutputService.batchUpdate(requestCtx, alertOutputs));
	}
	
	
	
	
	
	
	
	
	
	
	
}
