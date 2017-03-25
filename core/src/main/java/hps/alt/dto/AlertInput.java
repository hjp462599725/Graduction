package hps.alt.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.system.dto.BaseDTO;

/**
 * @name AlertInput
 * @description 预警输入参数DTO
 * @author zili.wang@hand-china.com 2016年9月8日上午9:56:06
 * @version 1.0
 */
@Table(name = "HPS_ALT_ALERT_INPUT")
public class AlertInput extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6045046013873744984L;
	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long alertInputId;
	@Column
	@NotEmpty
	private Long alertId;
	@Column
	private String inputName;
	@Column
	private String inputTitle;
	@Column
	private String inputTypeCode;
	@Column
	private String dataTypeCode;
	@Column
	private String defaultValue;
	@Column
	private String sqlStatement;
	public Long getAlertInputId() {
		return alertInputId;
	}
	public void setAlertInputId(Long alertInputId) {
		this.alertInputId = alertInputId;
	}
	public Long getAlertId() {
		return alertId;
	}
	public void setAlertId(Long alertId) {
		this.alertId = alertId;
	}
	public String getInputName() {
		return inputName;
	}
	public void setInputName(String inputName) {
		this.inputName = inputName;
	}
	public String getInputTitle() {
		return inputTitle;
	}
	public void setInputTitle(String inputTitle) {
		this.inputTitle = inputTitle;
	}
	public String getInputTypeCode() {
		return inputTypeCode;
	}
	public void setInputTypeCode(String inputTypeCode) {
		this.inputTypeCode = inputTypeCode;
	}
	public String getDataTypeCode() {
		return dataTypeCode;
	}
	public void setDataTypeCode(String dataTypeCode) {
		this.dataTypeCode = dataTypeCode;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getSqlStatement() {
		return sqlStatement;
	}
	public void setSqlStatement(String sqlStatement) {
		this.sqlStatement = sqlStatement;
	}

	

}
