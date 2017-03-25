package hps.fnd.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.system.dto.BaseDTO;

/**
 * 
 * @name PlugDownloads
 * @description 插件下载记录
 * @author jieyang03@hand-china.com 2016年9月2日09:49:59
 * @version 1.0
 */
@Table(name = "HPS_FND_PLUG_DOWNLOADS")
public class PlugDownloads extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7019621891090407401L;
	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	//下载记录ID
	private Long plugDownloadId;
	@Column
	//插件ID
	private Long plugId;
	@Column
	//插件版本ID
	private Long plugVersionId;
	@Column
	//下载人
	private String downloadBy;
	@Column
	@NotEmpty
	//下载目的
	private String downloadPurpose;
	
	public Long getPlugDownloadId() {
		return plugDownloadId;
	}
	public void setPlugDownloadId(Long plugDownloadId) {
		this.plugDownloadId = plugDownloadId;
	}
	public Long getPlugId() {
		return plugId;
	}
	public void setPlugId(Long plugId) {
		this.plugId = plugId;
	}
	public Long getPlugVersionId() {
		return plugVersionId;
	}
	public void setPlugVersionId(Long plugVersionId) {
		this.plugVersionId = plugVersionId;
	}
	public String getDownloadBy() {
		return downloadBy;
	}
	public void setDownloadBy(String downloadBy) {
		this.downloadBy = downloadBy;
	}
	public String getDownloadPurpose() {
		return downloadPurpose;
	}
	public void setDownloadPurpose(String downloadPurpose) {
		this.downloadPurpose = downloadPurpose;
	}
	
}
