package hps.alt.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.system.dto.BaseDTO;

/**
 * @name AlertAction
 * @description 预警活动DTO
 * @author zili.wang@hand-china.com 2016年9月13日下午7:50:28
 * @version 1.0
 */
@Table(name = "HPS_ALT_ALERT_ACTION")
public class AlertAction extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3217236379142804002L;
	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long actionId;
	@Column
	@NotEmpty
	private Long alertId;
	@Column
	private String actionName;
	@Column
	private String description;
	@Column
	private String actionLevelCode;
	@Column
	private String noticeTypeCode;

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public Long getAlertId() {
		return alertId;
	}

	public void setAlertId(Long alertId) {
		this.alertId = alertId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getActionLevelCode() {
		return actionLevelCode;
	}

	public void setActionLevelCode(String actionLevelCode) {
		this.actionLevelCode = actionLevelCode;
	}

	public String getNoticeTypeCode() {
		return noticeTypeCode;
	}

	public void setNoticeTypeCode(String noticeTypeCode) {
		this.noticeTypeCode = noticeTypeCode;
	}

}
