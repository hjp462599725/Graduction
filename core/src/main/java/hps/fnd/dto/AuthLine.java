package hps.fnd.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.system.dto.BaseDTO;

/**
 * @name AuthLine
 * @description 角色权限设置行表DTO
 * @author hongan.dong@hand-china.com 2016年9月13日上午10:35:53
 * @version 1.0
 */
@Table(name = "HPS_FND_AUTH_LINE")
public class AuthLine extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long authLineId;
	@Column
	private Long authHeaderId;
	@Column
	private Long resourceItemId;
	@Column
	private String isRead;
	@Column
	private String isEdit;
	@Column
	private String isHide;
	@Column
	private String isRequired;
	@Column
	private String isOperate;
	/**
	 * 版本号
	 */
	@Column
	private Long objectVersionNumber;
	@Transient
	private String itemDescription;
	@Transient
	private Long resourceId;
	@Transient
	private Long roleId;
	/**
	 * 组件代码
	 */
	@Transient
	private String itemCode;
	/**
	 * 组件来源
	 */
	@Transient
	private String itemRegion;
	/**
	 * 组件类型
	 */
	@Transient
	private String itemType;

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

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public Long getAuthLineId() {
		return authLineId;
	}

	public void setAuthLineId(Long authLineId) {
		this.authLineId = authLineId;
	}

	public Long getAuthHeaderId() {
		return authHeaderId;
	}

	public void setAuthHeaderId(Long authHeaderId) {
		this.authHeaderId = authHeaderId;
	}

	public Long getResourceItemId() {
		return resourceItemId;
	}

	public void setResourceItemId(Long resourceItemId) {
		this.resourceItemId = resourceItemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemRegion() {
		return itemRegion;
	}

	public void setItemRegion(String itemRegion) {
		this.itemRegion = itemRegion;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public String getIsHide() {
		return isHide;
	}

	public void setIsHide(String isHide) {
		this.isHide = isHide;
	}

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	public String getIsOperate() {
		return isOperate;
	}

	public void setIsOperate(String isOperate) {
		this.isOperate = isOperate;
	}

	public Long getObjectVersionNumber() {
		return objectVersionNumber;
	}

	public void setObjectVersionNumber(Long objectVersionNumber) {
		this.objectVersionNumber = objectVersionNumber;
	}

}
