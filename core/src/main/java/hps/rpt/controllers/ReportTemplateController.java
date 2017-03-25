package hps.rpt.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.attachment.dto.SysFile;
import com.hand.hap.attachment.service.ISysFileService;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.IProfileService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import hps.rpt.dto.ReportColumn;
import hps.rpt.dto.ReportTemplate;
import hps.rpt.dto.RptReport;
import hps.rpt.dto.ReportTemplate.ReportType;
import hps.rpt.service.IReportTemplateService;
import hps.rpt.service.IRptReportService;
/**
 * 
 * @name ReportTemplateController
 * @description 
 * @author chengye.hu@hand-china.com	2016年8月31日下午2:44:18
 * @version 1.0
 */
@Controller
public class ReportTemplateController extends BaseController {
	@Autowired
	private IRptReportService reportService;
	@Autowired
	private IReportTemplateService reportTemplateService;
	@Autowired
    private ISysFileService sysFileService;
	@Autowired
	private IProfileService profileService;
	/**
	 * 创建一个模板
	 * 
	 * @param reportResultss
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rpt/createFormwork", method = RequestMethod.POST) // 模板创建
	@ResponseBody
	public ResponseData createFormwork(@RequestBody List<ReportColumn> reportColumns, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IRequest requestCtx = createRequestContext(request);
		List<ReportColumn> reList = new ArrayList<ReportColumn>();
		Map<String, Object> map = new HashMap<String, Object>();
		for (ReportColumn rl : reportColumns) {
			if (rl.getShowFlag().equals("Y")) {
				reList.add(rl);
			}
		}
		
		StringBuffer str = new StringBuffer();
		for (ReportColumn rls : reList) {
			str.append("<td><#list map?keys as itemKey><#if itemKey='" + rls.getSqlcolName()
					+ "'>${map[itemKey]?if_exists}</#if></#list></td>");
		}
		map.put("columnNameList", reList);
		map.put("str", "<#list list as map><#if map_index%2==0><tr class='even_tr'>" + str.toString() + "</tr><#else><tr class='odd_tr'>" + str.toString() + "</tr></#if></#list>");
		@SuppressWarnings("deprecation")
		Configuration cfg = new Configuration();
		Template template = new Template("name",
				new StringReader(
						"<html><head>"
						+ "<link href='"+request.getContextPath()+"/resources/css/skins/report_table.css' rel='stylesheet' type='text/css' />"
						+ "<title>表单</title><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /></head>"
						+ "<body>"
						+ "<table class='imagetable' width='900' border='1' cellspacing='0' cellpadding='0'>"
						+ "<tr height='80px'><#list columnNameList as clist><th width='120'>${clist.displayName}</th></#list>"
						+ "</tr>${str}</table>"
						+ "</body></html>"),
				cfg);
		StringWriter out = new StringWriter();
		template.process(map, out);
		RptReport report = new RptReport();
		report.setReportId(reportColumns.get(0).getReportId());
		String reportName = reportService.selectByPrimaryKey(requestCtx, report).getReportName();
		String reportCode = reportService.selectByPrimaryKey(requestCtx, report).getReportCode();
		String filePath = profileService.getProfileValue(requestCtx, "RPT_TEMPLATE_PATH");//request.getSession().getServletContext().getRealPath("/") + "files/rpt";
		String fileName = reportCode + ".ftl";
		ReportTemplate reportTemplate = new ReportTemplate();
		reportTemplate.setReportId(reportColumns.get(0).getReportId());
		reportTemplate.setTemplateType(ReportType.HTML.value);
		if (reportTemplateService.selectTemplateKey(requestCtx, reportTemplate) == null) {
			reportTemplate.setFileName(fileName);
			reportTemplate.setFilePath(filePath);
			reportTemplate.setTemplateName(reportName);
			reportTemplateService.insertSelective(requestCtx, reportTemplate);
		} else {
			reportTemplate.setFileName(fileName);
			reportTemplate.setFilePath(filePath);
			reportTemplate.setTemplateName(reportName);
			reportTemplate
					.setTemplateId(reportTemplateService.selectTemplateKey(requestCtx, reportTemplate).getTemplateId());
			reportTemplateService.updateByPrimaryKeySelective(requestCtx, reportTemplate);
		}
		File folder = new File(filePath);
		if (!folder.exists())
			folder.mkdirs();
		File file = new File(filePath + "/" + fileName);
		if (!file.exists())
			file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file, false);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);
		bw.write(out.toString());
		bw.flush();
		bw.close();
		osw.close();
		fos.close();
		return new ResponseData(true);
	}
	/**
	 * 保存上传的模板文件
	 * 
	 * @param reportTemplate
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rpt/template/submit", method = RequestMethod.POST) 
	@ResponseBody
	public ResponseData uploadTemplate(@RequestBody List<ReportTemplate> reportTemplates,HttpServletRequest request) {
		IRequest requestCtx = createRequestContext(request);
		ReportTemplate reportTemplate =reportTemplates.get(0);
		SysFile sysFile = sysFileService.selectByPrimaryKey(requestCtx, reportTemplate.getFileId());
		String filePath = sysFile.getFilePath();
		String temp = filePath; 
		String fileName;
		StringBuffer path = new StringBuffer();
		while(true){
			int i = temp.indexOf("\\");
			if(i<0){
				fileName = temp;
				break;
			}
			path.append(temp.substring(0,i+1));
			temp = temp.substring(i+1);
		}
		String str = path.toString();
		filePath = str.substring(0,str.length()-1);
		reportTemplate.setFileName(fileName);
		reportTemplate.setFilePath(filePath);
		reportTemplateService.insertSelective(requestCtx, reportTemplate);
		return new ResponseData(true);
	}
	
	/**
	 * 查找报表相对应的模板文件
	 * 
	 * @param reportTemplate
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rpt/template/query", method = RequestMethod.POST) 
	@ResponseBody
	public ResponseData Templatequery(ReportTemplate reportTemplate,HttpServletRequest request) {
		IRequest requestCtx = createRequestContext(request);
		return new ResponseData(reportTemplateService.select(requestCtx, reportTemplate, 1, 100));
	}
	
	/**
	 * 模板删除
	 * 
	 * @param reportTemplates
	 * @param result
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/rpt/template/delete")
	@ResponseBody
	public ResponseData parameterDelete(@RequestBody List<ReportTemplate> reportTemplates, BindingResult result,
			HttpServletRequest request) {
		reportTemplateService.batchDelete(reportTemplates);
		return new ResponseData();
	}
}
