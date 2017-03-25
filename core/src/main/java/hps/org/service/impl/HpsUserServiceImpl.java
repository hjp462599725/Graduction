package hps.org.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.org.dto.HpsUser;
import hps.org.mapper.HpsUserMapper;
import hps.org.service.IHpsUserService;
@Service
public class HpsUserServiceImpl extends BaseServiceImpl<HpsUser> implements IHpsUserService {

	@Autowired
	private HpsUserMapper hpsUserMapper;

	@Override
	public List<HpsUser> selectHpsUser(HpsUser hpsUser, int page, int pagesize, IRequest request) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, pagesize);
		return hpsUserMapper.selectHpsUser(hpsUser);
	}

	@Override
	public List<HpsUser> selectHpsUserById(HpsUser hpsUser, IRequest request) {
		// TODO Auto-generated method stub
		return hpsUserMapper.selectHpsUser(hpsUser);
	}

}
