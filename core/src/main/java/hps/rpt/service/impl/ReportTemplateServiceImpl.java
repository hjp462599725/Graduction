package hps.rpt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.rpt.dto.ReportTemplate;
import hps.rpt.mapper.ReportTemplateMapper;
import hps.rpt.service.IReportTemplateService;

@Service
public class ReportTemplateServiceImpl extends BaseServiceImpl<ReportTemplate> implements IReportTemplateService {
	@Autowired
	private ReportTemplateMapper reportTemplateMapper;
	@Override
	public ReportTemplate selectTemplateKey(IRequest requestCtx, ReportTemplate reportTemplate) {
		return reportTemplateMapper.selectTemplateKey(reportTemplate);
	}

}
