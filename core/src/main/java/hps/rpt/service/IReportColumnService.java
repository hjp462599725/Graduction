package hps.rpt.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.IBaseService;

import hps.rpt.dto.ReportColumn;

/**
 * @name IReportColumnService
 * @description 报表结果列Service
 * @author dezhi.shen@hand-china.com	2016年8月25日下午4:23:08
 * @version 1.0
 */
public interface IReportColumnService extends IBaseService<ReportColumn>{

	/**
	 * 批量插入 结果列
	 * @param requestContext
	 * @param list
	 * @return
	 */
	int insertReportColumns(IRequest requestContext, List<ReportColumn> list);

	/**
	 * 根据报表头Id批量删除 结果列
	 * @param reportColumn
	 * @return
	 */
	int deleteByReportId(ReportColumn reportColumn);

}
