package hps.fnd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;

import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.fnd.dto.AuthLine;
import hps.fnd.dto.HpsResourceItem;
import hps.fnd.mapper.HpsResourceItemMapper;
import hps.fnd.service.IHpsResourceItemService;

/**
 * @author jie.yang03@hand-china.com
 *
 *        2016年7月20日15:29:50
 */
@Service
public class HpsResourceItemServiceImpl extends BaseServiceImpl<HpsResourceItem> implements IHpsResourceItemService {

	@Autowired
	private HpsResourceItemMapper hpsResourceMapper;

	@Override
	public List<HpsResourceItem> selectHpsResourceItem(IRequest request, HpsResourceItem resourceItem, int page,
			int pageSize) {
		// TODO Auto-generated method stub
		//PageHelper.startPage(page, pageSize);
		return hpsResourceMapper.selectHpsResourceItem(resourceItem);
	}

	@Override
	public List<HpsResourceItem> selectItems(IRequest requestContext, AuthLine authLine) {
		// TODO Auto-generated method stub
		return hpsResourceMapper.selectItems(authLine);
	}

}
