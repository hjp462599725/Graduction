package hps.fnd.dto;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.Children;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.system.dto.BaseDTO;

@Table(name = "HPS_FND_LOOKUP_TYPE_B")
@MultiLanguage
public class LookupType extends BaseDTO {
	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long lookupTypeId;
	@Column
	@NotEmpty
	private String lookupType;
	@Column
	@NotEmpty
	@MultiLanguageField
	private String meaning;
	@Column
	@MultiLanguageField
	private String description;
	@Children
	@Transient
	private List<LookupValue> lookupValues;
	
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
	public List<LookupValue> getLookupValues() {
		return lookupValues;
	}
	public void setLookupValues(List<LookupValue> lookupValues) {
		this.lookupValues = lookupValues;
	}
}