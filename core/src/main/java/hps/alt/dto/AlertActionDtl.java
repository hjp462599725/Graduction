package hps.alt.dto;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.system.dto.BaseDTO;
/**
 * @name AlertActionDTL
 * @description 预警活动详情DTO
 * @author jie.yang03@hand-china.com 2016年9月19日16:50:53
 * @version 1.0
 */
@Table(name = "HPS_ALT_ALERT_ACTION_DTL")
public class AlertActionDtl extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4459982951245568889L;
	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long actionDtlId;
	@Column
	private Long actionId;
	@Column
	private Long alertId;
	@Column
	private String mailConfigCode;
	@Column
	private Long recipientListId;
	@Column
	private Long recEmployeeId;
	@Column
	private String recipient;
	@Column
	private String subject;
	@Column
	private String carbonCopy;
	@Column
	private String blindCarbonCopy;
	@Column
	private String alertMessage;
	
	@Transient
	private String actionName;
	@Transient
	private String description;
	@Transient
	private String noticeTypeCodeMeaning;
	@Transient
	private String listName;
	@Transient
	private String employeeName;
	@Transient
	private String noticeTypeCode;
	public Long getActionDtlId() {
		return actionDtlId;
	}
	public void setActionDtlId(Long actionDtlId) {
		this.actionDtlId = actionDtlId;
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
	public String getMailConfigCode() {
		return mailConfigCode;
	}
	public void setMailConfigCode(String mailConfigCode) {
		this.mailConfigCode = mailConfigCode;
	}
	public Long getRecipientListId() {
		return recipientListId;
	}
	public void setRecipientListId(Long recipientListId) {
		this.recipientListId = recipientListId;
	}
	public Long getRecEmployeeId() {
		return recEmployeeId;
	}
	public void setRecEmployeeId(Long recEmployeeId) {
		this.recEmployeeId = recEmployeeId;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getCarbonCopy() {
		return carbonCopy;
	}
	public void setCarbonCopy(String carbonCopy) {
		this.carbonCopy = carbonCopy;
	}
	public String getBlindCarbonCopy() {
		return blindCarbonCopy;
	}
	public void setBlindCarbonCopy(String blindCarbonCopy) {
		this.blindCarbonCopy = blindCarbonCopy;
	}
	public String getAlertMessage() {
		return alertMessage;
	}
	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNoticeTypeCodeMeaning() {
		return noticeTypeCodeMeaning;
	}
	public void setNoticeTypeCodeMeaning(String noticeTypeCodeMeaning) {
		this.noticeTypeCodeMeaning = noticeTypeCodeMeaning;
	}
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getNoticeTypeCode() {
		return noticeTypeCode;
	}
	public void setNoticeTypeCode(String noticeTypeCode) {
		this.noticeTypeCode = noticeTypeCode;
	}
	
	

}
