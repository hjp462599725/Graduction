package hps.rpt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.rpt.controllers.ReportParameterController;
import hps.rpt.dto.ReportParameter;
import hps.rpt.mapper.ReportParameterMapper;
import hps.rpt.service.IReportParameterService;
import hps.rpt.util.RptException;

@Service
public class ReportParameterServiceImpl extends BaseServiceImpl<ReportParameter> implements IReportParameterService {
	@Autowired
	ReportParameterMapper mapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long selectId() {
		return mapper.selectId();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RptException.class)
	public List<ReportParameter> batchUpdateRptParam(IRequest arg0, List<ReportParameter> arg1) throws RptException {
		// TODO Auto-generated method stub
		for (ReportParameter reportParameter : arg1) {
			String key = reportParameter.get__status();
			switch (key) {
			case DTOStatus.ADD:
				mapper.insertExm(reportParameter);
				break;
			case DTOStatus.DELETE:
				mapper.deleteByPrimaryKey(reportParameter);
				break;
			case DTOStatus.UPDATE:
				mapper.updateByPrimaryKeySelective(reportParameter);
				break;
			default:
				break;
			}
			
		}
		if (this.isUniqueRowNum(arg1.get(0))) {
			throw new RptException(ReportParameterController.NOT_UNIQUE_ROWNUM, null);
		}
		return arg1;
	}

	@Override
	public boolean isUniqueRowNum(ReportParameter parameter) {
		boolean flag = false;
		List<Integer> counts = this.mapper.isUniqueRowNum(parameter.getReportId());
		for (Integer integer : counts) {
			if(integer>1){
				flag = true;
				break;
			}
		}
		return flag;
	}

}
