package hps.rpt.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.core.IRequest;
import com.hand.hap.function.dto.Function;
import com.hand.hap.function.dto.FunctionResource;
import com.hand.hap.function.dto.Resource;
import com.hand.hap.function.dto.RoleFunction;
import com.hand.hap.function.service.IFunctionService;
import com.hand.hap.function.service.IResourceService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hps.rpt.dto.RptReport;
import hps.rpt.service.IReportFunctionDeployService;
/**
 * 
 * @name ReportFunctionDeployController
 * @description 
 * @author chengye.hu@hand-china.com	2016年8月31日下午2:41:33
 * @version 1.0
 */
@Controller
public class ReportFunctionDeployController extends BaseController {
	@Autowired
	private IReportFunctionDeployService reportFunctionDeployService;
	@Autowired
	private IResourceService resourceService;
	@Autowired
	private IFunctionService functionService;

	/**
	 * 功能部署,将指定模板放入左边功能栏
	 * 
	 * @param reports
	 * @param roleId
	 * @param parentFunctionId
	 * @param request
	 * @return 
	 */
	@RequestMapping(value = "/rpt/report/deploy", method = RequestMethod.POST) // 功能部署
	@ResponseBody
	public ResponseData deploy(@RequestBody List<RptReport> reports, String roleId, String parentFunctionId,
			HttpServletRequest request) {
		IRequest requestCtx = createRequestContext(request);
		boolean sss = false;
		boolean ccc = false;
		List<Long> reportId = new ArrayList<Long>();
		List<Resource> res = reportFunctionDeployService.selectResourceAll();
		for (Resource re : res) {
			for (RptReport rep : reports) {
				if (rep.getReportName().equals(re.getName())) {
					sss = true;
					reportId.add(re.getResourceId());
				}
			}
		}
		if (sss == true) {
			List<Function> fun = reportFunctionDeployService.selectFunctionAll();
			for (Function funn : fun) {
				for (int i = 0; i < reportId.size(); i++) {
					if (funn.getResourceId() != null && funn.getParentFunctionId() != null) {
						if (funn.getResourceId().equals(reportId.get(i))
								&& funn.getParentFunctionId().equals(Long.parseLong(parentFunctionId))) {
							ccc = true;
						}
					}
				}
			}
		}
		if (sss == false) {
			List<Resource> resources = new ArrayList<Resource>();
			for (int i = 0; i < reports.size(); i++) {
				Resource resource = new Resource();
				resource.setUrl("rpt/report_header.html?reportId=" + reports.get(i).getReportId());
				resource.setName(reports.get(i).getReportName());
				resource.setDescription(reports.get(i).getReportName());
				resource.setLoginRequire("Y");
				resource.setAccessCheck("Y");
				resource.setType("SERVICE");
				resources.add(resource);
			}
			resourceService.batchUpdate(requestCtx, resources);
			List<Function> functions = new ArrayList<Function>();
			for (int i = 0; i < reports.size(); i++) {
				Function function = new Function();
				function.setFunctionCode("RPT_" + System.currentTimeMillis());
				function.setModuleCode("RPT");
				function.setFunctionName(reports.get(i).getReportName());
				function.setFunctionIcon("icon-edit");
				function.setFunctionSequence(Long.parseLong("30"));
				function.setFunctionDescription(reports.get(i).getReportName());
				function.setParentFunctionId(Long.parseLong(parentFunctionId));
				Resource resource = new Resource();
				resource.setName(reports.get(i).getReportName());
				resource = reportFunctionDeployService.selectResourceKey(requestCtx, resource);
				function.setResourceId(resource.getResourceId());
				functions.add(function);
			}
			functionService.batchUpdate(requestCtx, functions);
			for (int i = 0; i < reports.size(); i++) {
				FunctionResource functionResource = new FunctionResource();
				Resource resource = new Resource();
				resource.setName(reports.get(i).getReportName());
				resource = reportFunctionDeployService.selectResourceKey(requestCtx, resource);
				functionResource.setResourceId(resource.getResourceId());
				Function function = new Function();
				function.setParentFunctionId(Long.parseLong(parentFunctionId));
				function.setFunctionName(reports.get(i).getReportName());
				function = reportFunctionDeployService.selectFunctionKey(requestCtx, function);
				functionResource.setFunctionId(function.getFunctionId());
				reportFunctionDeployService.insertResourceFunctions(requestCtx, functionResource);
			}
			List<RoleFunction> roleFunctions = new ArrayList<RoleFunction>();
			for (int i = 0; i < reports.size(); i++) {
				RoleFunction roleFunction = new RoleFunction();
				Function function = new Function();
				function.setParentFunctionId(Long.parseLong(parentFunctionId));
				function.setFunctionName(reports.get(i).getReportName());
				function = reportFunctionDeployService.selectFunctionKey(requestCtx, function);
				roleFunction.setFunctionId(function.getFunctionId());
				roleFunction.setRoleId(Long.parseLong(roleId));
				roleFunctions.add(roleFunction);
			}
			reportFunctionDeployService.insertRoleFunction(requestCtx, roleFunctions);
			return new ResponseData(true);
		} else if (ccc == false) {
			List<Function> functions = new ArrayList<Function>();
			for (int i = 0; i < reports.size(); i++) {
				Function function = new Function();
				function.setFunctionCode("Report" + System.currentTimeMillis());
				function.setModuleCode("Report" + System.currentTimeMillis());
				function.setFunctionName(reports.get(i).getReportName());
				function.setFunctionIcon("icon-edit");
				function.setFunctionSequence(Long.parseLong("30"));
				function.setFunctionDescription(reports.get(i).getReportName());
				function.setParentFunctionId(Long.parseLong(parentFunctionId));
				Resource resource = new Resource();
				resource.setName(reports.get(i).getReportName());
				resource = reportFunctionDeployService.selectResourceKey(requestCtx, resource);
				function.setResourceId(resource.getResourceId());
				functions.add(function);
			}
			functionService.batchUpdate(requestCtx, functions);
			for (int i = 0; i < reports.size(); i++) {
				FunctionResource functionResource = new FunctionResource();
				Resource resource = new Resource();
				resource.setName(reports.get(i).getReportName());
				resource = reportFunctionDeployService.selectResourceKey(requestCtx, resource);
				functionResource.setResourceId(resource.getResourceId());
				Function function = new Function();
				function.setParentFunctionId(Long.parseLong(parentFunctionId));
				function.setFunctionName(reports.get(i).getReportName());
				function = reportFunctionDeployService.selectFunctionKey(requestCtx, function);
				functionResource.setFunctionId(function.getFunctionId());
				reportFunctionDeployService.insertResourceFunctions(requestCtx, functionResource);
			}
			List<RoleFunction> roleFunctions = new ArrayList<RoleFunction>();
			for (int i = 0; i < reports.size(); i++) {
				RoleFunction roleFunction = new RoleFunction();
				Function function = new Function();
				function.setParentFunctionId(Long.parseLong(parentFunctionId));
				function.setFunctionName(reports.get(i).getReportName());
				function = reportFunctionDeployService.selectFunctionKey(requestCtx, function);
				roleFunction.setFunctionId(function.getFunctionId());
				roleFunction.setRoleId(Long.parseLong(roleId));
				roleFunctions.add(roleFunction);
			}
			reportFunctionDeployService.insertRoleFunction(requestCtx, roleFunctions);
			return new ResponseData(true);
		} else {
			return new ResponseData(false);
		}
	}
}
