package hps.fnd.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.system.dto.BaseDTO;
/**
 * 
 * @name Plugs
 * @description 插件头DTO
 * @author jieyang03@hand-china.com 2016年9月2日09:49:59
 * @version 1.0
 */
@Table(name = "HPS_FND_PLUGS")
public class Plugs extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7687824881069148054L;
	//插件ID
	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long plugId;
	@Column
	@NotEmpty
	//插件类型
	private String plugTypeCode;
	@Column
	@NotEmpty
	//插件名称
	private String plugName;
	@Column
	//插件描述
	private String plugDescription;
	@Column
	//插件作者
	private String authorName;
	@Column
	//插件作者邮箱
	private String authorEmail;
	@Column
	//启用标示
	private String enabledFlag;
	public Long getPlugId() {
		return plugId;
	}
	public void setPlugId(Long plugId) {
		this.plugId = plugId;
	}
	public String getPlugTypeCode() {
		return plugTypeCode;
	}
	public void setPlugTypeCode(String plugTypeCode) {
		this.plugTypeCode = plugTypeCode;
	}
	public String getPlugName() {
		return plugName;
	}
	public void setPlugName(String plugName) {
		this.plugName = plugName;
	}
	public String getPlugDescription() {
		return plugDescription;
	}
	public void setPlugDescription(String plugDescription) {
		this.plugDescription = plugDescription;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getAuthorEmail() {
		return authorEmail;
	}
	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}
	public String getEnabledFlag() {
		return enabledFlag;
	}
	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
	
	
}
