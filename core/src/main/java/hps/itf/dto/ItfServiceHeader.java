package hps.itf.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.system.dto.BaseDTO;

/**
 * 
 * @name ItfServiceHeader
 * @description 接口Header
 * @author jianping.huo@hand-china.com  2016年8月19日下午2:38:10
 * @version 1.0
 */
@Table(name = "HPS_ITF_SERVICE_HEADERS_B")
@MultiLanguage
public class ItfServiceHeader extends BaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long serviceHeaderId;
	@Column
	@NotEmpty
	private String serviceCode;
	@Column
	@NotEmpty
	private String serviceType;
	@Column
	private String bodyHeader;
	@Column
	private String bodyTail;
	@Column
	private String namespace;
	@Column
	@NotEmpty
	private String domainUrl;
	@Column
	@NotEmpty
	private String requestFormat;
	@Column
	private String requestContenttype;
	@Column
	private String requestAccept;
	@Column
	private String authFlag;
	@Column
	private String authUsername;
	@Column
	private String authPassword;
	@Column
	private String enableFlag;
	@Column
	@MultiLanguageField
	private String meanning;
	@Column
	@MultiLanguageField
	private String description;
	
	public Long getServiceHeaderId() {
		return serviceHeaderId;
	}
	public void setServiceHeaderId(Long serviceHeaderId) {
		this.serviceHeaderId = serviceHeaderId;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getBodyHeader() {
		return bodyHeader;
	}
	public void setBodyHeader(String bodyHeader) {
		this.bodyHeader = bodyHeader;
	}
	public String getBodyTail() {
		return bodyTail;
	}
	public void setBodyTail(String bodyTail) {
		this.bodyTail = bodyTail;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getDomainUrl() {
		return domainUrl;
	}
	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
	}
	public String getRequestFormat() {
		return requestFormat;
	}
	public void setRequestFormat(String requestFormat) {
		this.requestFormat = requestFormat;
	}
	public String getRequestContenttype() {
		return requestContenttype;
	}
	public void setRequestContenttype(String requestContenttype) {
		this.requestContenttype = requestContenttype;
	}
	public String getRequestAccept() {
		return requestAccept;
	}
	public void setRequestAccept(String requestAccept) {
		this.requestAccept = requestAccept;
	}
	public String getAuthFlag() {
		return authFlag;
	}
	public void setAuthFlag(String authFlag) {
		this.authFlag = authFlag;
	}
	public String getAuthUsername() {
		return authUsername;
	}
	public void setAuthUsername(String authUsername) {
		this.authUsername = authUsername;
	}
	public String getAuthPassword() {
		return authPassword;
	}
	public void setAuthPassword(String authPassword) {
		this.authPassword = authPassword;
	}
	public String getEnableFlag() {
		return enableFlag;
	}
	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}
	public String getMeanning() {
		return meanning;
	}
	public void setMeanning(String meanning) {
		this.meanning = meanning;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}