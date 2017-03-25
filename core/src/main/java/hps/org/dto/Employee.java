package hps.org.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.system.dto.BaseDTO;

/**
 * @name Employee
 * @description 
 * @author xing.gong@hand-china.com 2016年9月8日上午10:11:35
 * @version 1.0
 * 类说明:员工表
 */
@Table(name="HPS_ORG_EMPLOYEE_B")
@MultiLanguage
public class Employee extends BaseDTO {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long  employeeId;

	@Column
	private String  employeeTypeCode;
	
	@Column
	private String  employeeNumber;
	
	@Column
	@MultiLanguageField
	private String  employeeName;
	
	@Column
	private String email;
	
	@Column
	private String  mobilePhone;
	
	@Column
	private String  wechat;
	
	@Column
	private String qq;
	
	@Column
	private String  certificate;
	
	@Column
	@MultiLanguageField
	private String  comments;
	
	@Column
	@MultiLanguageField
	private String  bankName;
	
	@Column
	private String  bankAccountNum;
	
	@Column
	private String  enabledFlag;
	
	@Column
	private Long  objectVersionNumber;
	
	@Transient
	private String meaning;
	
	@Transient
	private String lookupCode;
	
	@Transient
	private String lang;
	
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getLookupCode() {
		return lookupCode;
	}

	public void setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
	}



	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeTypeCode() {
		return employeeTypeCode;
	}

	public void setEmployeeTypeCode(String employeeTypeCode) {
		this.employeeTypeCode = employeeTypeCode;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccountNum() {
		return bankAccountNum;
	}

	public void setBankAccountNum(String bankAccountNum) {
		this.bankAccountNum = bankAccountNum;
	}

	public String getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	public Long getObjectVersionNumber() {
		return objectVersionNumber;
	}

	public void setObjectVersionNumber(Long objectVersionNumber) {
		this.objectVersionNumber = objectVersionNumber;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}


	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}


	
	

}
