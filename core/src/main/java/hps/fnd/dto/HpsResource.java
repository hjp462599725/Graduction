package hps.fnd.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.hand.hap.core.annotation.Children;
import com.hand.hap.system.dto.BaseDTO;

/**
 * 
 * @name HpsResource
 * @description 页面资源DTO
 * @author jie.yang03@hand-china.com  2016年7月20日14:46:42
 * @version 1.0
 */
@Table(name = "HPS_FND_RESOURCE")
public class HpsResource extends BaseDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6032354840653578959L;
	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long fndResourceId;
	@Column
	@NotNull
	private Long resourceId;
	@Column
	private String comments;
	@Column
	private String enabledFlag;
	@Children
	@Transient
	List<HpsResourceItem> resourceItems;
	@Transient
	private String resourceUrl;
	@Transient
	private String resourceName;
	public Long getFndResourceId() {
		return fndResourceId;
	}
	public void setFndResourceId(Long fndResourceId) {
		this.fndResourceId = fndResourceId;
	}
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getEnabledFlag() {
		return enabledFlag;
	}
	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
	
	public List<HpsResourceItem> getResourceItems() {
		return resourceItems;
	}
	public void setResourceItems(List<HpsResourceItem> resourceItems) {
		this.resourceItems = resourceItems;
	}
	public String getResourceUrl() {
		return resourceUrl;
	}
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	
}
