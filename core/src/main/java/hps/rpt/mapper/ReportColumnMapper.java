package hps.rpt.mapper;

import com.hand.hap.mybatis.common.Mapper;

import hps.rpt.dto.ReportColumn;

/**
 * @name ReportColumnMapper
 * @description 报表结果列Mapper
 * @author dezhi.shen@hand-china.com	2016年8月25日下午4:17:54
 * @version 1.0
 */
public interface ReportColumnMapper extends Mapper<ReportColumn>{
	/**
	 * 条件删除报表结果列
	 * @param reportColumn
	 * @return
	 */
	int deleteByReportId(ReportColumn reportColumn);

}
