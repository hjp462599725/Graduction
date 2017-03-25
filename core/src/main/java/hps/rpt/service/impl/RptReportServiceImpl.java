package hps.rpt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.rpt.dto.ReportColumn;
import hps.rpt.dto.RptReport;
import hps.rpt.mapper.ReportColumnMapper;
import hps.rpt.mapper.RptReportMapper;
import hps.rpt.service.IRptReportService;
import hps.rpt.util.ReportUtil;

@Service
public class RptReportServiceImpl extends BaseServiceImpl<RptReport> implements IRptReportService {
	@Autowired
	private RptReportMapper reportMapper;
	@Autowired
	private ReportColumnMapper columnMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<RptReport> submitReport(IRequest iRequest, List<RptReport> reports) throws Exception {
		for (RptReport report : reports) {
			if (report.getReportId() == null) {
				reportMapper.insertSelective(report);
			} else {
				ReportColumn reportColumn = new ReportColumn();
				reportColumn.setReportId(report.getReportId());
				reportMapper.updateByPrimaryKeySelective(report);
				columnMapper.deleteByReportId(reportColumn);
			}
			List<ReportColumn> list = ReportUtil.getResults(report.getSql());
			for (ReportColumn reportColumn : list) {
				reportColumn.setReportId(report.getReportId());
				columnMapper.insertSelective(reportColumn);
			}
		}
		return reports;
	}

}
