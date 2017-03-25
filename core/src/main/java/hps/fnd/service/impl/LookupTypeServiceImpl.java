package hps.fnd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.fnd.dto.LookupType;
import hps.fnd.dto.LookupValue;
import hps.fnd.mapper.LookupTypeMapper;
import hps.fnd.mapper.LookupValueMapper;
import hps.fnd.service.ILookupTypeService;

@Service
public class LookupTypeServiceImpl extends BaseServiceImpl<LookupType> implements ILookupTypeService{
	@Autowired
	private LookupTypeMapper lookupTypeMapper;
	@Autowired
	private LookupValueMapper lookupValueMapper;
	
	@Transactional(propagation = Propagation.SUPPORTS)
	private void processLookupValues(LookupType lookupType) {
		for (LookupValue lookupValue : lookupType.getLookupValues()) {
			if (lookupValue.getLookupTypeId() == null) {
				lookupValue.setLookupTypeId(lookupType.getLookupTypeId()); // 设置头ID跟行ID一致
				lookupValue.setLookupType(lookupType.getLookupType());
				lookupValueMapper.insertSelective(lookupValue);
			} else if (lookupValue.getLookupTypeId() != null) {
	            lookupValueMapper.updateByPrimaryKeySelective(lookupValue);
	        }
		}
    }
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<LookupType> myBatchUpdate(IRequest request, List<LookupType> lookupTypes) {
		// TODO Auto-generated method stub
		for (LookupType lookupType : lookupTypes) {
			if(lookupType.getLookupTypeId() == null){
				self().insertSelective(request, lookupType);
				if(lookupType.getLookupValues()!=null){
					processLookupValues(lookupType);
				}
			}else if(lookupType.getLookupTypeId() != null){
				self().updateByPrimaryKeySelective(request, lookupType);
				if(lookupType.getLookupValues()!=null){
					processLookupValues(lookupType);
				}
			}
		}
		return lookupTypes;
	}

	@Override
	public List<LookupType> selectLookupTypes(IRequest request, LookupType condition, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		return lookupTypeMapper.selectLookupTypes(condition);
	}

	@Override
	public int SelectOnlyValidate(String lookupType) {
		// TODO Auto-generated method stub
		return lookupTypeMapper.SelectOnlyValidate(lookupType);
	}

	

}