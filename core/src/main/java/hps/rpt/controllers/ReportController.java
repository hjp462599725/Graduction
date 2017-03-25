package hps.rpt.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hps.rpt.dto.ReportColumn;
import hps.rpt.dto.RptReport;
import hps.rpt.service.IReportColumnService;
import hps.rpt.service.IRptReportService;
/**
 * 
 * @name ReportController
 * @description 
 * @author chengye.hu@hand-china.com	2016年8月31日下午2:39:46
 * @version 1.0
 */
@Controller
public class ReportController extends BaseController {
	//static DataSource dataSource;
	@Autowired
	private IRptReportService reportService;
	@Autowired
	private IReportColumnService reportColumService;

	public ReportController() {
		//dataSource = (DataSource) DataSourceManage.getConnection();
	}

	/**
	 * 复合条件的头表List
	 * 
	 * @param request
	 * @param report
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@RequestMapping(value = "/rpt/report/query", method = RequestMethod.POST) // 主表查询
	@ResponseBody
	public ResponseData reposrtQuery(HttpServletRequest request, RptReport report,
			@RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
		IRequest requestCtx = createRequestContext(request);
		return new ResponseData(reportService.select(requestCtx, report, page, pagesize));
	}

	/**
	 * 查询结果 行表
	 * 
	 * @param request
	 * @param reportId
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@RequestMapping(value = "/rpt/reportColum/query", method = RequestMethod.POST) // 行表查询
	public ResponseData reportLinesQuery(HttpServletRequest request, String reportId,
			@RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
		IRequest requestCtx = createRequestContext(request);
		ReportColumn reportColumn = new ReportColumn();
		reportColumn.setReportId(Long.parseLong(reportId));
		return new ResponseData(reportColumService.select(requestCtx, reportColumn, page, pagesize));
	}

	/**
	 * 构建 报表主体,生成结果列表，存入数据库
	 * 
	 * @param reposrts
	 *            前台传入的报表头部dto list
	 * @param request
	 *            上下文
	 * @param result
	 *            校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rpt/report/create", method = RequestMethod.POST) // 构建
	@ResponseBody
	public ResponseData submitReposrt(@RequestBody List<RptReport> reports, HttpServletRequest request,
			BindingResult result) throws Exception {
		IRequest iRequest = createRequestContext(request);
		getValidator().validate(reports, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
		reportService.submitReport(iRequest, reports);
		return new ResponseData(reports);
	}
	/**
	 * 保存结果列表
	 * 
	 * @param reportColumns
	 *            前台传入的报表列表dto list
	 * @param request
	 *            上下文
	 * @param result
	 *            校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rpt/report_col/submit")
	public ResponseData saveRptCol(HttpServletRequest request,@RequestBody List<ReportColumn> reportColumns, BindingResult result) {
		IRequest iRequest = createRequestContext(request);
		getValidator().validate(reportColumns, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
		return new ResponseData(reportColumService.batchUpdate(iRequest, reportColumns));
	}
}
