package hps.alt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.alt.dto.AlertAction;
import hps.alt.dto.AltAlert;
import hps.alt.mapper.AlertActionMapper;
import hps.alt.mapper.AlertMapper;
import hps.alt.service.IAlertService;

@Service
public class AlertServiceImpl extends BaseServiceImpl<AltAlert> implements IAlertService{
	
	@Autowired
	private AlertMapper alertMapper;
	@Autowired
	private AlertActionMapper alertActionMapper;

	@Override
	public List<AltAlert> selectAlert(AltAlert altAlert, int page, int pagesize) {
		// TODO Auto-generated method stub
		return alertMapper.selectAlert(altAlert);
	}

	@Override
	public int insertAlert(AltAlert altAlert) {
		// TODO Auto-generated method stub
		return alertMapper.insertAlert(altAlert);
	}

	@Override
	public int updateAlert(AltAlert altAlert) {
		// TODO Auto-generated method stub
		return alertMapper.updateAlert(altAlert);
	}

	@Override
	public List<AltAlert> selectTableOwner(AltAlert altAlert) {
		// TODO Auto-generated method stub
		return alertMapper.selectTable(altAlert);
		
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void processAlertAction(AltAlert altAlert){
		for (AlertAction alertAction : altAlert.getAlertActions()) {
			if (alertAction.getActionId() == null) {
				alertAction.setAlertId(altAlert.getAlertId());
				alertActionMapper.insertSelective(alertAction);
			}else if (alertAction.getActionId() != null) {
				alertActionMapper.updateByPrimaryKeySelective(alertAction);
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<AltAlert> myBatchUpdate(IRequest request, List<AltAlert> altAlerts) {
		// TODO Auto-generated method stub
		for (AltAlert altAlert : altAlerts) {
			if (altAlert.getAlertId() == null) {
				self().insertSelective(request, altAlert);
				if (altAlert.getAlertActions() != null) {
					processAlertAction(altAlert);
				}
			}else if (altAlert.getAlertId() != null) {
				self().updateByPrimaryKeySelective(request, altAlert);
				if (altAlert.getAlertActions() != null) {
					processAlertAction(altAlert);
					
				}
			}
		}
		
		return altAlerts;
	}

	

	

}
