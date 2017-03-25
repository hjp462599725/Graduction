package hps.fnd.dto;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;
/**
 * @name MailConfig
 * @description 消息配置DTO 
 * @author yanjie.zhang@hand-china.com	2016年9月13日下午9:54:26
 * @version 1.0
 */
@Table(name="HPS_FND_MAIL_CONFIG")
public class MailConfig extends BaseDTO{
	
	private static final long serialVersionUID = -6554389855779566767L;

	/**
	 * 表ID，主键，供其他表做外键
	 */
	@Id
	@GeneratedValue(generator=GENERATOR_TYPE)
	@Column
	private Long mailConfigId;
	
	/**
	 * 邮箱配置代码
	 */
	@Column
	private String mailConfigCode;
	
	/**
	 * 服务器
	 */
	@Column
	private String host;
	
	/**
	 * 端口
	 */
	@Column
	private Integer port;
	
	/**
	 * 发件地址
	 */
	@Column
	private String mailAddress;
	
	/**
	 * 用户
	 */
	@Column
	private String userName;
	
	/**
	 * 密码
	 */
	@Column
	private String password;
	
	/**
	 *启用标识
	 */
	@Column
	private String enabledFlag;
	

	public Long getMailConfigId() {
		return mailConfigId;
	}
	public void setMailConfigId(Long mailConfigId) {
		this.mailConfigId = mailConfigId;
	}
	public String getMailConfigCode() {
		return mailConfigCode;
	}
	public void setMailConfigCode(String mailConfigCode) {
		this.mailConfigCode = mailConfigCode;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEnabledFlag() {
		return enabledFlag;
	}
	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}

}
