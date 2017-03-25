package hps.alt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.alt.dto.AlertActionDtl;
import hps.alt.mapper.AlertActionDtlMapper;
import hps.alt.service.IAlertActionDtlService;
@Service
public class AlertActionDtlServiceImpl extends BaseServiceImpl<AlertActionDtl> implements IAlertActionDtlService{
	@Autowired
	private AlertActionDtlMapper alertActionDtlMapper;
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void saveAction(IRequest request, AlertActionDtl alertActionDtls) {
		// TODO Auto-generated method stub
		self().insertSelective(request, alertActionDtls);
		
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void updateAction(IRequest request, AlertActionDtl alertActionDtls) {
		// TODO Auto-generated method stub
		self().updateByPrimaryKeySelective(request, alertActionDtls);
	}

	@Override
	public AlertActionDtl queryAlertActionDtl(IRequest request, AlertActionDtl alertActionDtl) {
		// TODO Auto-generated method stub
		return alertActionDtlMapper.queryAlertActionDtl(alertActionDtl);
	}

	@Override
	public List<AlertActionDtl> queryAlertActionDtls(IRequest request, AlertActionDtl alertActionDtls) {
		// TODO Auto-generated method stub
		return alertActionDtlMapper.queryAlertActionDtls(alertActionDtls);
	}

}
