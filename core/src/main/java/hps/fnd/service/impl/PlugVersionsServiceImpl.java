package hps.fnd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.fnd.dto.PlugVersions;
import hps.fnd.mapper.PlugVersionsMapper;
import hps.fnd.service.IPlugVersionsService;
@Service
public class PlugVersionsServiceImpl extends BaseServiceImpl<PlugVersions> implements IPlugVersionsService{
	@Autowired
	private PlugVersionsMapper plugVersionMapper;
	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void savePlugVersions(IRequest request, PlugVersions plugVersions) {
		// TODO Auto-generated method stub
		self().insertSelective(request, plugVersions);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void updataPlugVersions(IRequest request, PlugVersions plugVersions) {
		// TODO Auto-generated method stub
		self().updateByPrimaryKeySelective(request, plugVersions);
	}

	@Override
	public List<PlugVersions> queryPlugVersions(IRequest request, PlugVersions plugVersions, int page, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, pageSize);
		return plugVersionMapper.selectFlugVersions(plugVersions);
	}

	@Override
	public List<PlugVersions> queryUploadPlugVersions(IRequest request, PlugVersions plugVersions, int page,
			int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, pageSize);
		return plugVersionMapper.selectUploadFlugVersions(plugVersions);
	}

	@Override
	public int SelectVersionsOnlyValidate(PlugVersions plugVersions) {
		// TODO Auto-generated method stub
		return plugVersionMapper.SelectVersionsOnlyValidate(plugVersions);
	}

}
