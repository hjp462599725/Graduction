package hps.rpt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.rpt.dto.ReportColumn;
import hps.rpt.mapper.ReportColumnMapper;
import hps.rpt.service.IReportColumnService;

@Service
public class ReportColumnServiceImpl extends BaseServiceImpl<ReportColumn> implements IReportColumnService {
	@Autowired ReportColumnMapper mapper;
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int insertReportColumns(IRequest requestContext, List<ReportColumn> list) {
		// TODO Auto-generated method stub
		int i = 0;
		for (ReportColumn reportColumn : list) {
			i+=mapper.insertSelective(reportColumn);
		}
		return i;
	}
	
	@Override
	public int deleteByReportId(ReportColumn reportColumn) {
		// TODO Auto-generated method stub
		
		
		
		return mapper.deleteByReportId(reportColumn);
	}
}
