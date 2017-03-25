package hps.fnd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.fnd.dto.PlugCount;
import hps.fnd.dto.Plugs;
import hps.fnd.mapper.PlugsMapper;
import hps.fnd.service.IPlugsService;
@Service
public class PlugsServiceImpl extends BaseServiceImpl<Plugs> implements IPlugsService{
	@Autowired
	private PlugsMapper plugsMapper; 
	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void savePlugs(IRequest request, Plugs plug) {
		// TODO Auto-generated method stub
		self().insertSelective(request, plug);
	}


	@Override
	public List<Plugs> queryUploadPlugs(IRequest request, Plugs plug, int page, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, pageSize);
		return plugsMapper.select(plug);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void updataPlugs(IRequest request, Plugs plug) {
		// TODO Auto-generated method stub
		self().updateByPrimaryKeySelective(request, plug);
	}


	@Override
	public Long selectFileId(IRequest request, Long sourceKey) {
		// TODO Auto-generated method stub
		return plugsMapper.selectFileId(sourceKey);
	}


	@Override
	public List<Plugs> queryDownloadPlugs(IRequest request, Plugs plug, int page, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, pageSize);
		return plugsMapper.queryDownloadPlugs(plug);
	}


	@Override
	public List<PlugCount> queryPlugCount(IRequest request, PlugCount plugCount) {
		return plugsMapper.queryPlugCount(plugCount);
	}

}
