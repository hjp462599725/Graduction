package hps.fnd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.fnd.controllers.AuthController;
import hps.fnd.dto.AuthHeader;
import hps.fnd.dto.AuthLine;
import hps.fnd.mapper.AuthLineMapper;
import hps.fnd.service.IAuthLineService;

/**
 * @name AuthLineServiceImpl
 * @description 权限行表Service实现
 * @author hongan.dong@hand-china.com 2016年9月13日下午2:39:08
 * @version 1.0
 */
@Service
public class AuthLineServiceImpl extends BaseServiceImpl<AuthLine> implements IAuthLineService {

	@Autowired
	private AuthLineMapper authLineMapper;

	@Override
	public List<AuthLine> selectAuthLines(IRequest requestContext, AuthHeader authHeader) {
		// TODO Auto-generated method stub
		return authLineMapper.selectAuthLines(authHeader);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<AuthLine> insertOrUpdateLines(IRequest iRequest, List<AuthLine> authLine) {
		// TODO Auto-generated method stub
		for (int i = 0; i < authLine.size(); i++) {
			AuthLine a = authLine.get(i);
			if (a.get__status() != null) {
				if ("add".equals(a.get__status())) {
					// 过滤录入重复数据
					AuthLine authLines1 = authLineMapper.selectAuthLine(a);
					if (authLines1 != null) {
						AuthController.FLAG = 1;
						break;
					}
					authLineMapper.insertAuthLines(a);
				}
				if ("update".equals(a.get__status())) {
					// 更新不会出现重复数据
					authLineMapper.updateAuthLines(a);
				}
			}
		}
		return authLine;
	}

	@Override
	public List<AuthLine> selectAuthList(AuthLine authLine) {
		// TODO Auto-generated method stub
		return authLineMapper.selectAuthList(authLine);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteAuthLines(AuthLine authLine) {
		// TODO Auto-generated method stub
		authLineMapper.deleteAuthLines(authLine);
	}
}
