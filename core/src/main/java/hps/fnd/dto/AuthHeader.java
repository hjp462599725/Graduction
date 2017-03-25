package hps.fnd.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.system.dto.BaseDTO;

/**
 * @name AuthHeader
 * @description 角色权限设置头表DTO
 * @author hongan.dong@hand-china.com 2016年9月13日上午10:33:15
 * @version 1.0
 */
@Table(name = "HPS_FND_AUTH_HEADER")
public class AuthHeader extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long authHeaderId;
	@Column
	private Long roleId;
	@Column
	private Long resourceId;
	@Column
	private String enabledFlag;
	@Column
	private Long objectVersionNumber;
	@Transient
	private String roleDesc;
	@Transient
	private String resourceDesc;

	public Long getAuthHeaderId() {
		return authHeaderId;
	}

	public void setAuthHeaderId(Long authHeaderId) {
		this.authHeaderId = authHeaderId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	public Long getObjectVersionNumber() {
		return objectVersionNumber;
	}

	public void setObjectVersionNumber(Long objectVersionNumber) {
		this.objectVersionNumber = objectVersionNumber;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

}
