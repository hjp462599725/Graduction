package hps.alt.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.system.dto.BaseDTO;




/**
 * @name Recipient
 * @description 
 * @author xing.gong@hand-china.com 2016年9月8日上午9:52:37
 * @version 1.0
 */
@Table(name="HPS_ALT_RECIPIENT_LIST")
public class Recipient extends BaseDTO {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long  recipientListId;

	@Column
	private String  listName;
	
	@Column
	private String  listDescription;
	
	@Column
	private String  enabledFlag;
	
	@Transient
	private String employeeName;
	

	
	@Transient
	private String lang;

	public Long getRecipientListId() {
		return recipientListId;
	}

	public void setRecipientListId(Long recipientListId) {
		this.recipientListId = recipientListId;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public String getListDescription() {
		return listDescription;
	}

	public void setListDescription(String listDescription) {
		this.listDescription = listDescription;
	}

	public String getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	
	
}
