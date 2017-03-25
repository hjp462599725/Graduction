package hps.alt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.alt.dto.AlertInput;
import hps.alt.mapper.AlertInputMapper;
import hps.alt.service.IAlertInputService;

@Service
public class AlertInputServiceImpl extends BaseServiceImpl<AlertInput> implements IAlertInputService {

	@Autowired
	private AlertInputMapper alertInputMapper;
	
	@Override
	public int insertAlertInput(AlertInput alertInput) {
		// TODO Auto-generated method stub
		return alertInputMapper.insertAlertInputName(alertInput);
	}

	@Override
	public int deleteAlertInput(Long alertId) {
		// TODO Auto-generated method stub
		return alertInputMapper.deleteAlertInputName(alertId);
		
	}

	@Override
	public int updateAltInput(AlertInput alertInput) {
		// TODO Auto-generated method stub
		return alertInputMapper.updateAltInput(alertInput);
	}



	

	

	
	
	
	
}
