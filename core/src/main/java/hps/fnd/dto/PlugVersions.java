package hps.fnd.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.system.dto.BaseDTO;

/**
 * 
 * @name PlugVersions
 * @description 插件版本
 * @author jieyang03@hand-china.com 2016年9月2日09:49:59
 * @version 1.0
 */
@Table(name = "HPS_FND_PLUG_VERSIONS")
public class PlugVersions extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7603068521027562220L;
	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	//版本ID
	private Long plugVersionId;
	@Column
	//插件ID
	private Long plugId;
	@Column
	@NotEmpty
	//版本号
	private String versionNumber;
	@Column
	//上传时间
	private Date uploadDate;
	@Column
	//下载次数
	private Long downloadCount;
	@Column
	//启用标示
	private String enabledFlag;
	
	@Transient
	private Long fileSize;
	
	public Long getPlugVersionId() {
		return plugVersionId;
	}
	public void setPlugVersionId(Long plugVersionId) {
		this.plugVersionId = plugVersionId;
	}
	public Long getPlugId() {
		return plugId;
	}
	public void setPlugId(Long plugId) {
		this.plugId = plugId;
	}
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public Long getDownloadCount() {
		return downloadCount;
	}
	public void setDownloadCount(Long downloadCount) {
		this.downloadCount = downloadCount;
	}
	public String getEnabledFlag() {
		return enabledFlag;
	}
	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
}
