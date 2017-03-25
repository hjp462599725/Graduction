package hps.rpt.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.IBaseService;

import hps.rpt.dto.RptReport;
/**
 * @name IReportParameterService
 * @description 报表参数Service
 * @author dezhi.shen@hand-china.com	2016年8月25日下午4:23:08
 * @version 1.0
 */
public interface IRptReportService extends IBaseService<RptReport> {
	/**
	 * 批量保存报表
	 * @param iRequest
	 * @param reports
	 * @return
	 */
	List<RptReport> submitReport(IRequest iRequest,List<RptReport> reports) throws Exception;
}
