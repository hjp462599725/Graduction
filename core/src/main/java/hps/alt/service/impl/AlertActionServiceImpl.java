package hps.alt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.alt.dto.AlertAction;
import hps.alt.dto.AlertActionDtl;
import hps.alt.mapper.AlertActionMapper;
import hps.alt.service.IAlertActionService;

@Service
public class AlertActionServiceImpl extends BaseServiceImpl<AlertAction> implements IAlertActionService{
	@Autowired
	private AlertActionMapper alertActionMapper;
	
	@Override
	public List<AlertAction> selectAlertActions(IRequest request, AlertAction alertAction) {
		// TODO Auto-generated method stub
		return alertActionMapper.select(alertAction);
	}

	
	
	

}
