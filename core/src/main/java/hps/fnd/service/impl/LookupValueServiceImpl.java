package hps.fnd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.fnd.dto.LookupValue;
import hps.fnd.mapper.LookupValueMapper;
import hps.fnd.service.ILookupValueService;
@Service
public class LookupValueServiceImpl extends BaseServiceImpl<LookupValue> implements ILookupValueService{
	
	@Autowired
	private LookupValueMapper lookupValueMapper;
	@Override
	public List<LookupValue> selectLookupValues(IRequest request, LookupValue lookupValue) {
		// TODO Auto-generated method stub
		return lookupValueMapper.selectLookupValues(lookupValue);
	}

	@Override
	public List<LookupValue> selectLookupValuesByType(IRequest request, LookupValue lookupValue) {
		// TODO Auto-generated method stub
		return lookupValueMapper.selectLookupValuesByType(lookupValue);
	}

	@Override
	public List<LookupValue> queryLookupValues(IRequest request, LookupValue lookupValue) {
		// TODO Auto-generated method stub
		return lookupValueMapper.queryLookupValues(lookupValue);
	}

	@Override
	public int SelectValueOnlyValidate(LookupValue lookupValue) {
		// TODO Auto-generated method stub
		return lookupValueMapper.SelectValueOnlyValidate(lookupValue);
	}

}
