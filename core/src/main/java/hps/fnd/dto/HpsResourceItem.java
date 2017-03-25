package hps.fnd.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.hand.hap.system.dto.BaseDTO;

/**
 * 
 * @name HpsResourceItem
 * @description 页面资源项目DTO
 * @author jie.yang03@hand-china.com  2016年7月20日14:46:42
 * @version 1.0
 */
@Table(name = "HPS_FND_RESOURCE_ITEM")
public class HpsResourceItem extends BaseDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6511473125503524030L;
	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long resourceItemId;
	@Column
	private Long fndResourceId;
	@Column
	private String itemCode;
	@Column
	private String itemDescription;
	@Column
	private String enabledFlag;
	@Column
	private Date startDateActive;
	@Column
	private Date endDateActive;
	@Column
	private String itemRegion;
	@Column
	private String itemType;
	public Long getResourceItemId() {
		return resourceItemId;
	}
	public void setResourceItemId(Long resourceItemId) {
		this.resourceItemId = resourceItemId;
	}
	public Long getFndResourceId() {
		return fndResourceId;
	}
	public void setFndResourceId(Long fndResourceId) {
		this.fndResourceId = fndResourceId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getEnabledFlag() {
		return enabledFlag;
	}
	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
	public Date getStartDateActive() {
		return startDateActive;
	}
	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}
	public Date getEndDateActive() {
		return endDateActive;
	}
	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
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
	
	
}
