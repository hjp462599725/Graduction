package hps.fnd.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;

import hps.fnd.dto.MailConfig;
import hps.fnd.util.ValidateTableException;

/**
 * @name IMailConfigService
 * @description 邮件配置CRUD实现接口
 * @author yanjie.zhang@hand-china.com	2016年9月13日下午7:26:49
 * @version 1.0
 */
public interface IMailConfigService extends IBaseService<MailConfig>,ProxySelf<IMailConfigService>{
	/**
	 * 查询邮件
	 * @param mailConfig
	 * @param requestCtx
	 * @return
	 */
	List<MailConfig> selectMailConfig(MailConfig mailConfig,IRequest requestCtx,int page,int pageSize);
	
	/**
	 * 插入更新消息配置
	 * @param mailConfigs 
	 * @param requestCtx
	 * @return
	 * @throws ValidateTableException 
	 */
	List<MailConfig> batchUpdateMailConfig(@StdWho List<MailConfig> mailConfigs,IRequest requestCtx) throws ValidateTableException;
	
	/**
	 * 验证消息配置配置代码是否唯一
	 * @param mailConfig 邮件配置DTO
	 * @return
	 */
	int selectMailConfigOnly(MailConfig mailConfig);
}
