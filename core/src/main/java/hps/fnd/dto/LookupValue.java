package hps.fnd.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.system.dto.BaseDTO;

@MultiLanguage
@Table(name = "HPS_FND_LOOKUP_VALUE_B")
public class LookupValue extends BaseDTO {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long lookupCodeId;
	@Column
	private Long lookupTypeId;
	@Column
	private String lookupType;
	@Column
	@NotEmpty
	private String lookupCode;
	@Column
	@NotEmpty
	@MultiLanguageField
	private String meaning;
	@Column
	@MultiLanguageField
	private String description;
	@Column
	@MultiLanguageField
	private String tag;
	@Column
	private String enabledFlag;
	@Column
	private Date startDateActive;
	@Column
	private Date endDateActive;
	
	public Long getLookupCodeId() {
		return lookupCodeId;
	}
	public void setLookupCodeId(Long lookupCodeId) {
		this.lookupCodeId = lookupCodeId;
	}
	public Long getLookupTypeId() {
		return lookupTypeId;
	}
	public void setLookupTypeId(Long lookupTypeId) {
		this.lookupTypeId = lookupTypeId;
	}
	public String getLookupType() {
		return lookupType;
	}
	public void setLookupType(String lookupType) {
		this.lookupType = lookupType;
	}
	public String getLookupCode() {
		return lookupCode;
	}
	public void setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
	}
	public String getMeaning() {
		return meaning;
	}
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
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
}