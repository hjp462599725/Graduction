package hps.fnd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.fnd.controllers.AuthController;
import hps.fnd.controllers.FlexValueController;
import hps.fnd.controllers.MailConfigController;
import hps.fnd.dto.AuthLine;
import hps.fnd.dto.MailConfig;
import hps.fnd.dto.ValidateTable;
import hps.fnd.mapper.MailConfigMapper;
import hps.fnd.service.IMailConfigService;
import hps.fnd.util.ValidateTableException;

/**
 * @name MailConfigServiceImpl
 * @description
 * @author yanjie.zhang@hand-china.com	2016年9月13日下午7:30:13
 * @version 1.0
 */
@Service
@Transactional
public class MailConfigServiceImpl extends BaseServiceImpl<MailConfig> implements IMailConfigService{

	@Autowired
	private MailConfigMapper mailConfigMapper;
	
	@Override
	public List<MailConfig> selectMailConfig(MailConfig mailConfig,IRequest requestCtx,int page,int pageSize) {
		PageHelper.startPage(page, pageSize);
		return mailConfigMapper.selectMailConfig(mailConfig);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ValidateTableException.class)
	public List<MailConfig> batchUpdateMailConfig(List<MailConfig> mailConfigs, IRequest requestCtx) throws ValidateTableException {
		for (MailConfig m:mailConfigs) {
			switch(m.get__status()){
				case DTOStatus.ADD:
				{
					self().insertSelective(requestCtx, m);
					int count = self().selectMailConfigOnly(m);
					if(count>1){
						throw new ValidateTableException(MailConfigController.NOT_UNIQUE_MAILCONFIGCODE, null);
					}
					break;
				}
				case DTOStatus.UPDATE:
				{
					self().updateByPrimaryKeySelective(requestCtx, m);
					int count = self().selectMailConfigOnly(m);
					if(count > 1){
						throw new ValidateTableException(MailConfigController.NOT_UNIQUE_MAILCONFIGCODE, null);
					}
					break;
				}
				case DTOStatus.DELETE:
					self().deleteByPrimaryKey(m);
					break;
				default:
					break;
			}
		}
		return mailConfigs;
	}

	@Override
	public int selectMailConfigOnly(MailConfig mailConfig) {
		return mailConfigMapper.selectMailConfigOnly(mailConfig);
	}

}
