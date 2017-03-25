package hps.rpt.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.dto.ResponseData;

import hps.rpt.dto.ReportParameter;
import hps.rpt.service.IReportParameterService;
import hps.rpt.util.ReportUtil;
import hps.rpt.util.RptException;
/**
 * 
 * @name ReportParameterController
 * @description 
 * @author dezhi.shen@hand-china.com	2016年8月31日下午2:42:14
 * @version 1.0
 */
@Controller
public class ReportParameterController extends BaseController {
	@Autowired
	private IReportParameterService reportParameterService;

	public final static String NOT_UNIQUE_ROWNUM = "hps.rpt.not_unique_rownum";

	/**
	 * 参数界面 查询按钮，根据FORM条件查询 参数List
	 * 
	 * @param reportParameter
	 * @param page
	 * @param pagesize
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/rpt/parameter/query") // 参数查询
	@ResponseBody
	public ResponseData parameterQuery(ReportParameter reportParameter,
			@RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(reportParameterService.select(requestContext, reportParameter, page, pagesize));
	}

	/**
	 * 参数删除
	 * 
	 * @param reportParameters
	 * @param result
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/rpt/parameter/delete")
	@ResponseBody
	public ResponseData parameterDelete(@RequestBody List<ReportParameter> reportParameters, BindingResult result,
			HttpServletRequest request) {
		reportParameterService.batchDelete(reportParameters);
		return new ResponseData();
	}

	/**
	 * 保存SQL参数
	 * 
	 * @param reportParameters
	 * @param result
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/rpt/parameter/submit")
	@ResponseBody
	public ResponseData submitReportParams(@RequestBody List<ReportParameter> reportParameters, BindingResult result,
			HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		ResponseData rd;
		Locale locale = RequestContextUtils.getLocale(request);
		String message;
		getValidator().validate(reportParameters, result);
		if (result.hasErrors()) {
			rd = new ResponseData(false);
			rd.setMessage(getErrorMessage(result, request));
			return rd;
		}
		// set 校验 rowNum是否重复
		Set<Double> set = new HashSet<Double>();
		for (ReportParameter reportParameter : reportParameters) {
			set.add(reportParameter.getRowNum());
		}
		if (set.size() != reportParameters.size()) {
			rd = new ResponseData(false);
			message = this.getMessageSource().getMessage(NOT_UNIQUE_ROWNUM, null, locale);
			rd.setMessage(message);
			return rd;
		}

		// newline渲染
		List<ReportParameter> lists = ReportUtil.setNewLines(reportParameters);
		for (ReportParameter reportParameter : lists) {
			if (reportParameter.get__status().equals(DTOStatus.ADD)) {
				Long Id = this.reportParameterService.selectId();
				reportParameter.setParameterId(Id);
			}
			// 生成渲染表单的前台代码
			reportParameter.setFiledCode(reportParameter.createFileds());
		}
		// 数据库增删改操作
		List<ReportParameter> reportParameters2;
		try {
			reportParameters2 = reportParameterService.batchUpdateRptParam(requestContext, lists);
		} catch (RptException e) {
			//违反唯一约束
			e.printStackTrace();
			rd = new ResponseData(false);
			message = this.getMessageSource().getMessage(e.getCode(), null, locale);
			rd.setMessage(message);
			return rd;
		}
		if (reportParameters2 == null) {
			rd = new ResponseData(false);
			message = this.getMessageSource().getMessage(NOT_UNIQUE_ROWNUM, null, locale);
			rd.setMessage(message);
			return rd;
		}
		return new ResponseData(reportParameters2);
	}

}
