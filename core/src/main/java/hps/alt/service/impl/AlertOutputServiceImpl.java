package hps.alt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.alt.dto.AlertOutput;
import hps.alt.mapper.AlertOutputMapper;
import hps.alt.service.IAlertOutputService;

@Service
public class AlertOutputServiceImpl extends BaseServiceImpl<AlertOutput> implements IAlertOutputService{

	@Autowired
	private AlertOutputMapper alertOutputMapper;
	
	@Override
	public int insertAlertOutput(AlertOutput alertOutput) {
		// TODO Auto-generated method stub
		return alertOutputMapper.insertAltOutputName(alertOutput);
	}

	@Override
	public int deleteAlertOutput(Long alertId) {
		// TODO Auto-generated method stub
		return alertOutputMapper.deleteAltOutputName(alertId);
		
	}

	@Override
	public List<AlertOutput> queruyAlertOutputs(IRequest request, AlertOutput alertOutput) {
		// TODO Auto-generated method stub
		return alertOutputMapper.queruyAlertOutputs(alertOutput);
	}


	

}
