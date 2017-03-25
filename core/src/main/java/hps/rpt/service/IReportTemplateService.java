package hps.rpt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.IBaseService;

import hps.rpt.dto.ReportTemplate;

/**
 * @name IReportTemplateService
 * @description 报表模板Service
 * @author dezhi.shen@hand-china.com	2016年8月25日下午4:19:57
 * @version 1.0
 */
public interface IReportTemplateService extends IBaseService<ReportTemplate>{
	/**
	 * 根据条件查找报表模板
	 * @param requestCtx
	 * @param reportTemplate
	 * @return
	 */
	ReportTemplate selectTemplateKey(IRequest requestCtx, ReportTemplate reportTemplate);

}
