package hps.fnd.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;

import hps.fnd.dto.MailConfig;
/**
 * @name MailConfigMapper
 * @description 
 * @author yanjie.zhang@hand-china.com	2016年9月13日下午7:23:51
 * @version 1.0
 */
public interface MailConfigMapper extends Mapper<MailConfig> {
	/**
	 * 查询消息配置
	 * @param mailConfig 邮件配置DTO
	 * @return 邮件配置集合
	 */
	List<MailConfig> selectMailConfig(MailConfig mailConfig);
	
	/**
	 * 验证消息配置配置代码是否唯一
	 * @param mailConfig 邮件配置DTO
	 * @return
	 */
	int selectMailConfigOnly(MailConfig mailConfig);

}
