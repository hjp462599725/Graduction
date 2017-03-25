package hps.alt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.alt.dto.Recipient;
import hps.alt.mapper.RecipientMapper;
import hps.alt.service.IRecipientService;
import hps.org.dto.Employee;

/**
 * @name RecipientServiceImpl
 * @description 
 * @author xing.gong@hand-china.com 2016年9月8日上午10:08:58
 * @version 1.0
 */
@Service
@Transactional
public class RecipientServiceImpl extends BaseServiceImpl<Recipient> implements IRecipientService {

	

	@Autowired
	private RecipientMapper recipientMapper;

	@Override
	public List<Recipient> selectRecipient(Recipient recipient, int page, int pagesize, IRequest request) {
		PageHelper.startPage(page, pagesize);
		return recipientMapper.selectRecipient(recipient);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void insertRecipient(IRequest request,Recipient r) {		
		recipientMapper.insertRecipient(r);

	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void updateRecipient(IRequest request,Recipient r) {
		recipientMapper.updateRecipient(r);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void removeRecipient(Recipient r) {
		recipientMapper.removeRecipient(r);

	}

	@Override
	public List<Recipient> selectRecipientById(Long recipientListId) {
		return recipientMapper.selectRecipientById(recipientListId);
	}

	@Override
	public List<Employee> queryRecipientDatil(IRequest request, Long recipientListId,int page, int pagesize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, pagesize);
		return recipientMapper.queryRecipientDatil(recipientListId);
	}


}
