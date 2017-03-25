package hps.fnd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.fnd.dto.PlugDownloads;
import hps.fnd.mapper.PlugDownloadsMapper;
import hps.fnd.service.IPlugDownloadsService;
@Service
public class PlugDownloadsServiceImpl extends BaseServiceImpl<PlugDownloads> implements IPlugDownloadsService{
	
	@Autowired
	private PlugDownloadsMapper plugDownloadMapper;
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public void savePlugDownloads(IRequest request, PlugDownloads plugDownloads) {
		// TODO Auto-generated method stub
		self().insertSelective(request, plugDownloads);
	}

}
