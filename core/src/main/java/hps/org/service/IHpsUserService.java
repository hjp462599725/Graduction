package hps.org.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;

import hps.org.dto.HpsUser;

public interface IHpsUserService extends IBaseService<HpsUser>, ProxySelf<IHpsUserService> {
	public List<HpsUser> selectHpsUser(HpsUser hpsUser,int page,int pagesize,IRequest request);
	public List<HpsUser> selectHpsUserById(HpsUser hpsUser,IRequest request);
}	
