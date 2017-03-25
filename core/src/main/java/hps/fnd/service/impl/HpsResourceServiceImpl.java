package hps.fnd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;

import com.hand.hap.system.service.impl.BaseServiceImpl;


import hps.fnd.dto.HpsResource;
import hps.fnd.dto.HpsResourceItem;
import hps.fnd.mapper.HpsResourceItemMapper;
import hps.fnd.mapper.HpsResourceMapper;
import hps.fnd.service.IHpsResourceService;
/**
 * @author jie.yang03@hand-china.com
 *
 *        2016年7月20日15:29:50
 */
@Service
public class HpsResourceServiceImpl extends BaseServiceImpl<HpsResource> implements IHpsResourceService{
	@Autowired
	private HpsResourceMapper resourceMapper;
	@Autowired
	private HpsResourceItemMapper resourceItemMapper;
	@Override
	public List<HpsResource> selectResource(IRequest request, HpsResource resource, int page, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, pageSize);
		return resourceMapper.selectResource(resource);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<HpsResource> BatchUpdateHpsResource(IRequest request, List<HpsResource> hpsResources) {
		// TODO Auto-generated method stub
		for(HpsResource hr : hpsResources){
			if(hr.getFndResourceId() == null){
				self().insertSelective(request, hr);
				if(hr.getResourceItems()!=null){
					processHpsResourceItem(hr);
				}
			}else if(hr.getFndResourceId() != null){
				self().updateByPrimaryKeySelective(request, hr);
				if(hr.getResourceItems()!=null){
					processHpsResourceItem(hr);
				}
			}
		}
		return hpsResources;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void processHpsResourceItem(HpsResource hpsResource){
		for(HpsResourceItem hri : hpsResource.getResourceItems()){
			if(hri.getResourceItemId()==null){
				hri.setFndResourceId(hpsResource.getFndResourceId());//设置行ID和头ID一样
				resourceItemMapper.insertSelective(hri);
			}else if(hri.getResourceItemId()!=null){
				resourceItemMapper.updateByPrimaryKeySelective(hri);
			}
		}
	}

}
