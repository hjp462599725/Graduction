package hps.fnd.dto;

import javax.persistence.Column;
import javax.persistence.Transient;

import com.hand.hap.system.dto.BaseDTO;

/**@name PlugCount
 * @description 插件统计DTO
 * @author xianzhi.chen@hand-china.com
 * @version 1.0
 */
public class PlugCount extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2363661448400077375L;
	
	@Transient
	@Column
	private String plugTypeName;
	
	@Transient
	@Column
	private Long plugTypeQty;

	public String getPlugTypeName() {
		return plugTypeName;
	}

	public void setPlugTypeName(String plugTypeName) {
		this.plugTypeName = plugTypeName;
	}

	public Long getPlugTypeQty() {
		return plugTypeQty;
	}

	public void setPlugTypeQty(Long plugTypeQty) {
		this.plugTypeQty = plugTypeQty;
	}


}
