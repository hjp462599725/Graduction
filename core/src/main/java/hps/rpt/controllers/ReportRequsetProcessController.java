package hps.rpt.controllers;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hps.rpt.dto.ReportColumn;
import hps.rpt.dto.ReportParameter;
import hps.rpt.dto.ReportTemplate;
import hps.rpt.dto.ReportTemplate.ReportType;
import hps.rpt.dto.RptReport;
import hps.rpt.service.IReportColumnService;
import hps.rpt.service.IReportParameterService;
import hps.rpt.service.IReportTemplateService;
import hps.rpt.service.IRptReportService;
import hps.rpt.util.ExcelHelper;
import hps.rpt.util.ReportUtil;

/**
 * 生成报表请求处理类 包括: 1.报表的生成 2.报表的Form 中lov select的 数据源的生成
 * 
 * @name ReportRequsetProcessController
 * @description
 * @author dezhi.shen@hand-china.com 2016年8月31日下午2:43:03
 * @version 1.0
 */
@Controller
public class ReportRequsetProcessController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ReportRequsetProcessController.class);
	@Autowired
	private IRptReportService reportService;
	@Autowired
	private IReportColumnService reportColumnService;
	@Autowired
	private IReportParameterService reportParameterService;
	@Autowired
	private IReportTemplateService reportTemplateService;
	// 找不到选择的模板
	private String NO_TEMPLATE = "hps.rpt.no_found_template";
	// 输入不合法
	private String INVALIDAT_INPUT = "hps.rpt.invalidate_input";
	// 没有选择对应的模板
	private String NO_REPORTTYPE = "hps.rpt.no_report_type";
	private static String logHeader = "RPT ";
	private String NO_TEMPLATEID = "hps.rpt.no_report_type_no_templateId";

	/**
	 * 查询lov select List数据源
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rpt/filed/query")
	public ResponseData getLovSelectData(HttpServletRequest request) throws Exception {
		IRequest iRequest = createRequestContext(request);
		Map<String, String[]> map = request.getParameterMap();
		Map<String, Object> value = new HashMap<String, Object>();
		@SuppressWarnings("unused")
		int page = Integer.parseInt(map.get("page")[0]);
		@SuppressWarnings("unused")
		int pageSize = Integer.parseInt(map.get("pagesize")[0]);
		Long reporstParamId = Long.parseLong(map.get("parameterId")[0]);
		ReportParameter rp = new ReportParameter();
		rp.setParameterId(reporstParamId);
		ReportParameter temp = reportParameterService.selectByPrimaryKey(iRequest, rp);
		String sql = temp.getParameterSql();
		if (map.get("valueNames") != null) {
			// LOV
			String[] valueNames = map.get("valueNames")[0].split(",");
			for (String string : valueNames) {
				if (map.get(string) == null || map.get(string).length == 0)
					value.put(string, null);
				else
					value.put(string, map.get(string)[0]);
			}
			List<ReportParameter> params = ReportUtil.getParametersAndType(sql);
			String rpSql = ReportUtil.processSql(sql, value, params);
			logger.debug(rpSql);
			return new ResponseData(ReportUtil.executeSql(rpSql));
		} else {
			// SELECT
			return new ResponseData(ReportUtil.executeSql(sql));
		}

	}

	/**
	 * 处理报表传入参数，生成报表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/rpt/filed/generate_report")
	public ResponseData submit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("找到URL：/rpt/filed/generate_report并进入submit方法");
		request.setCharacterEncoding("utf-8");
		ResponseData rd;
		IRequest iRequest = createRequestContext(request);
		String message;
		// 获得语言
		Locale locale = RequestContextUtils.getLocale(request);
		Map<String, String[]> paramMap = request.getParameterMap();
		String reportTypes[] = paramMap.get("reportType");
		if (reportTypes == null) {
			logger.info(logHeader+"没有获得模板类型");
			// return errMsg 没有选择报表模板种类
			rd = new ResponseData(false);
			message = this.getMessageSource().getMessage(NO_REPORTTYPE, null, locale);
			rd.setMessage(message);
			return rd;
		}
		// 获得报表的种类
		String reportType = reportTypes[0];
		// 获得传入参数名称
		String paramNames[] = paramMap.get("paramNames");
		Map<String, Object> valueMap = new HashMap<String, Object>();
		if (paramNames != null && paramNames.length != 0) {
			// 将参数的name作为key，将前台传入的value作为value封装进入valueMap中
			logger.info(logHeader+"start set params");
			for (String key : paramNames) {
				String args1 =new String(paramMap.get(key)[0].getBytes(),"utf-8");
				String args2[] = args1.split(" ");
				for (String string2 : args2) {
					if (string2.trim() == "or") {
						// return 输入不合法
						rd = new ResponseData(false);
						message = this.getMessageSource().getMessage(INVALIDAT_INPUT, null, locale);
						rd.setMessage(message);
						return rd;
					}
				}
				// 转义'
				args1.replaceAll("'", "''");
				valueMap.put(key, args1);
				logger.info(logHeader+key+":"+valueMap.get(key));
			}
			logger.info(logHeader+"完成valueMap的封装");
		}
		RptReport record = new RptReport();
		if (paramMap.get("reportId") != null) {
			Long repostId = Long.parseLong("" + paramMap.get("reportId")[0]);
			record.setReportId(repostId);
			logger.info(logHeader+"取到报表ID:"+repostId);
		}
		if (paramMap.get("reportCode") != null) {
			record.setReportCode(paramMap.get("reportCode")[0]);
			logger.info(logHeader+"取到报表Code:"+paramMap.get("reportCode")[0]);
		}
		List<RptReport> reports = this.reportService.select(iRequest, record, 1, 100);
		if (reports == null || reports.size() != 1) {
			// return Id 或Code有误
			logger.info(logHeader+"没有获得结果，Id 或Code有误");
		}
		// 获得对应的报表头表信息
		record = reports.get(0);
		// 获得对应报表的参数列表信息
		ReportParameter condition = new ReportParameter();
		condition.setReportId(record.getReportId());
		List<ReportParameter> reportParameters = this.reportParameterService.select(iRequest, condition, 1, 200);
		// 获得待处理的SQL
		String sql = record.getSql();
		logger.info(logHeader+"get unProcessedSql:"+sql);
		// 处理SQL 变为可执行SQL
		logger.info(logHeader+"start to process sql...");
		String reportSql = ReportUtil.processSql(sql, valueMap, reportParameters);
		// 执行SQL获得数据源
		//reportTemplate.setTemplateName(paramMap.get("reportTemplateId")[0]);
		logger.info(logHeader+"Finnished process sql。");
		logger.info(logHeader+"processSql:"+reportSql);
		List<Map<String, Object>> lists = ReportUtil.executeSql(reportSql);
		
		if (ReportType.HTML.value.equals(reportType)) {
			logger.info(logHeader+"模板类型匹配成功");
			Map<String, Object> dataSource = new HashMap<String, Object>();
			dataSource.put("list", lists);
			Long templateId=-1L;
			logger.info(logHeader+"start query templateInfo...");
			if(paramMap.get("templateId")!=null&&paramMap.get("templateId").length==1){
				templateId = Long.parseLong(paramMap.get("templateId")[0]);
				logger.info(logHeader+"templateId:"+templateId);
			}else{
				logger.info(logHeader+"have no found the templateId");
				rd = new ResponseData(false);
				rd.setMessage(this.getMessageSource().getMessage(NO_TEMPLATEID, null, locale));
				return rd;
			}
			ReportTemplate reportTemplate = new ReportTemplate();
			reportTemplate.setReportId(record.getReportId());
			reportTemplate.setTemplateType(reportType);
			reportTemplate.setTemplateId(templateId);
			List<ReportTemplate> reportTemplates = reportTemplateService.select(iRequest, reportTemplate, 1, 100);
			if (reportTemplates == null || reportTemplates.size() != 1) {
				logger.info(logHeader+"没有找到对应的模板with TEMPLATEID : "+reportTemplate.getTemplateId()
				+" AND reportId : "+reportTemplate.getReportId());
				// return没有找到对应的模板
				message = this.getMessageSource().getMessage(NO_TEMPLATE, null, locale);
				rd = new ResponseData(false);
				rd.setMessage(message);
				return rd;
			}
			ReportTemplate template = reportTemplates.get(0);
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			try{
				logger.info(logHeader+"start to create Html With Template...");
				byteOut = ReportUtil.createHtmlWithTemplate(dataSource, template.getFilePath(),
					template.getFileName());
				logger.info(logHeader+"finnish create Html With Template");
				
			}catch(Exception e){
				logger.info(logHeader+e.getMessage());
				rd = new ResponseData(false);
				rd.setMessage(e.getMessage());
				return rd;
			}
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			ServletOutputStream outs = response.getOutputStream();
			outs.write(byteOut.toByteArray());
			logger.info(logHeader+"finnished");
			outs.close();
			logger.info(logHeader+"close the outputStream.");
			return null;
		}
		if (ReportType.EXCEL.value.endsWith(reportType)) {
			ReportColumn condition1 = new ReportColumn();
			condition1.setReportId(record.getReportId());
			List<ReportColumn> results = this.reportColumnService.select(iRequest, condition1, 1, 100);
			Map<String, String> colName = new HashMap<String, String>();
			for (ReportColumn e : results) {
				if ("Y".equals(e.getShowFlag()))
					colName.put(e.getSqlcolName(), e.getDisplayName());
			}
			ByteArrayOutputStream out = ExcelHelper.exportExcle(record.getReportName(), 3000, lists, colName);
			response.setHeader("content-disposition",
					"attachment;filename=" + URLEncoder.encode(record.getReportName() + ".xls", "UTF-8"));
			ServletOutputStream outs = response.getOutputStream();
			outs.write(out.toByteArray());
			outs.flush();
			outs.close();
			return null;
		}
		return null;
	}

	/**
	 * 参数界面Grid渲染
	 * 
	 * @param reportParameter
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/rpt/report_header.html") // 参数表单渲染
	public ModelAndView attachmentCategoryList(ReportParameter reportParameter, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		ModelAndView view = new ModelAndView(getViewPath() + "/rpt/report_header");
		List<ReportParameter> reportParameters = new ArrayList<ReportParameter>();
		reportParameter.setSortname("row_num");
		reportParameters = reportParameterService.select(requestContext, reportParameter, 1, 100);
		for (ReportParameter reportParameter2 : reportParameters) {
			reportParameter2.setFiledCode(
					reportParameter2.getFiledCode().replace("base.contextPath", request.getContextPath()));
		}
		view.addObject("parametersList", reportParameters);
		// view.addObject("reposrtHeaderId",
		// reportParameter.getReposrtHeaderId());
		return view;
	}
}
