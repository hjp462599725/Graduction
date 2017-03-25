package hps.fnd.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;

import hps.fnd.dto.AuthHeader;
import hps.fnd.dto.AuthLine;

/**
 * @name IAuthLineService
 * @description 权限行表Service接口
 * @author hongan.dong@hand-china.com 2016年9月13日下午2:08:35
 * @version 1.0
 */
public interface IAuthLineService extends IBaseService<AuthLine>, ProxySelf<IAuthLineService> {

	public List<AuthLine> selectAuthLines(IRequest requestContext, AuthHeader authHeader);

	public List<AuthLine> insertOrUpdateLines(IRequest iRequest, @StdWho List<AuthLine> authLine);

	public List<AuthLine> selectAuthList(AuthLine authLine);

	public void deleteAuthLines(AuthLine authLine);

}
