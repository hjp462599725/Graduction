package hps.fnd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.fnd.controllers.AuthController;
import hps.fnd.dto.AuthHeader;
import hps.fnd.mapper.AuthHeaderMapper;
import hps.fnd.service.IAuthHeaderService;

/**
 * @name AuthHeaderServiceImpl
 * @description 权限头表Service实现
 * @author hongan.dong@hand-china.com 2016年9月13日下午2:30:12
 * @version 1.0
 */
@Service
public class AuthHeaderServiceImpl extends BaseServiceImpl<AuthHeader> implements IAuthHeaderService {

	@Autowired
	private AuthHeaderMapper authHeaderMapper;

	@Override
	public List<AuthHeader> selectAuthHeaders(AuthHeader authHeader, IRequest requestContext, int page, int pagesize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, pagesize);
		return authHeaderMapper.selectAuthHeaders(authHeader);
	}

	@Override
	public AuthHeader selectAuthHeader(AuthHeader authHeader) {
		// TODO Auto-generated method stub
		return authHeaderMapper.selectAuthHeader(authHeader);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<AuthHeader> insertOrUpdateHeaders(IRequest iRequest, List<AuthHeader> authHeader) {
		// TODO Auto-generated method stub
		for (int i = 0; i < authHeader.size(); i++) {
			if (authHeader.get(i).get__status() != null) {
				if (authHeader.get(i).get__status().equals("add")) {
					// 过滤录入重复数据
					AuthHeader authHeader1 = selectAuthHeader(authHeader.get(i));
					if (authHeader1 != null) {
						AuthController.FLAG = 1;
						break;
					}
					authHeaderMapper.insertAuthHeaders(authHeader.get(i));
				}
				if (authHeader.get(i).get__status().equals("update")) {
					// 由于页面控制某些字段不可输，因此更新不会出现保存重复数据的情况。不需要做判断。
					authHeaderMapper.updateAuthHeaders(authHeader.get(i));
				}
			}
		}
		return authHeader;
	}
}
