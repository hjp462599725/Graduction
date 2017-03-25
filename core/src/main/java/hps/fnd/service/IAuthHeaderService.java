package hps.fnd.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;

import hps.fnd.dto.AuthHeader;

/**
 * @name IAuthHeaderService
 * @description 权限头表Service接口
 * @author hongan.dong@hand-china.com 2016年9月13日下午1:58:36
 * @version 1.0
 */
public interface IAuthHeaderService extends IBaseService<AuthHeader>, ProxySelf<IAuthHeaderService> {

	public List<AuthHeader> selectAuthHeaders(AuthHeader authHeader, IRequest requestContext, int page, int pagesize);

	public AuthHeader selectAuthHeader(AuthHeader authHeader);

	public List<AuthHeader> insertOrUpdateHeaders(IRequest iRequest, @StdWho List<AuthHeader> authHeader);
}
