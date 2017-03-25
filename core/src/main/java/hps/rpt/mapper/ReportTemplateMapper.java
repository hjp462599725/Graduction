package hps.rpt.mapper;

import com.hand.hap.mybatis.common.Mapper;

import hps.rpt.dto.ReportTemplate;

/**
 * @name ReportTemplateMapper
 * @description 报表模板管理Mapper
 * @author dezhi.shen@hand-china.com	2016年8月25日下午4:17:40
 * @version 1.0
 */
public interface ReportTemplateMapper  extends Mapper<ReportTemplate>{
	/**
	 * 根据条件查找报表模板
	 * @param reportTemplate
	 * @return
	 */
	ReportTemplate selectTemplateKey(ReportTemplate reportTemplate);

}
