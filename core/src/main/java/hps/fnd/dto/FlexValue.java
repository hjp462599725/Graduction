package hps.fnd.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.system.dto.BaseDTO;

/**
 * @name FlexValue
 * @description 值集行表DTO
 * @author dezhi.shen@hand-china.com 2016年9月5日下午2:31:31
 * @version 1.0
 */
@Table(name = "HPS_FND_FLEX_VALUE_B")
@MultiLanguage
public class FlexValue extends BaseDTO {
	private static final long serialVersionUID = -2479434057638856422L;
	@Id
	@GeneratedValue(generator = GENERATOR_TYPE)
	@Column
	private Long flexValueId;
	@Column
	private Long flexValueSetId;
	@Column
	private String flexValue;
	@Column
	@MultiLanguageField
	private String flexValueMeaning;
	@Column
	@MultiLanguageField
	private String description;
	@Column
	private String summaryFlag;
	@Column
	private String enabledFlag;
	@Column
	private Date startDateActive;
	@Column
	private Date endDateActive;
	@Column
	private Long objectVersionNumber;

	public Long getFlexValueId() {
		return flexValueId;
	}

	public void setFlexValueId(Long flexValueId) {
		this.flexValueId = flexValueId;
	}

	public Long getFlexValueSetId() {
		return flexValueSetId;
	}

	public void setFlexValueSetId(Long flexValueSetId) {
		this.flexValueSetId = flexValueSetId;
	}

	public String getFlexValue() {
		return flexValue;
	}

	public void setFlexValue(String flexValue) {
		this.flexValue = flexValue;
	}

	public String getFlexValueMeaning() {
		return flexValueMeaning;
	}

	public void setFlexValueMeaning(String flexValueMeaning) {
		this.flexValueMeaning = flexValueMeaning;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSummaryFlag() {
		return summaryFlag;
	}

	public void setSummaryFlag(String summaryFlag) {
		this.summaryFlag = summaryFlag;
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

	public Long getObjectVersionNumber() {
		return objectVersionNumber;
	}

	public void setObjectVersionNumber(Long objectVersionNumber) {
		this.objectVersionNumber = objectVersionNumber;
	}

}
