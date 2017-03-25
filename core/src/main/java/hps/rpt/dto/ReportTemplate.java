package hps.rpt.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.system.dto.BaseDTO;
/**
 * @name ReportTemplate
 * @description 报表模板DTO
 * @author dezhi.shen@hand-china.com	2016年8月25日下午4:19:38
 * @version 1.0
 */
@Table(name="hps_rpt_report_templates")
public class ReportTemplate extends BaseDTO {
	private static final long serialVersionUID = 6379819263465019476L;

		// 报表种类
		public static enum ReportType {
			HTML, EXCEL, PDF, XML;
			public String value;

			private ReportType() {
				// TODO Auto-generated constructor stub
				this.value = this.toString();
			}
		}

	@Id
	@GeneratedValue(generator = GENERATOR_TYPE)
	@Column
	private Long templateId;
	@Column
	@NotNull
	private Long reportId;
	@Column
	@NotEmpty
	private String templateType;
	@Column
	@NotEmpty
	private String filePath;
	@Column
	@NotEmpty
	private String fileName;
	@Column
	@NotEmpty
	private String templateName;
	@Transient
	private Long fileId;
	public ReportTemplate() {
		// TODO Auto-generated constructor stub
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	
}
