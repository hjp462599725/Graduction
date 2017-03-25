package hps.rpt.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.system.dto.BaseDTO;
/**
 * @name RptReport
 * @description 报表DTO
 * @author dezhi.shen@hand-china.com	2016年8月25日下午4:19:38
 * @version 1.0
 */
@Table(name="hps_rpt_reports")
public class RptReport extends BaseDTO {
	private static final long serialVersionUID = 6072398342504170372L;
	@Id
	@GeneratedValue(generator = GENERATOR_TYPE)
	@Column
	private Long reportId;
	@Column
	@NotEmpty
	private String reportCode;
	@Column
	@NotEmpty
	private String reportName;
	@Column
	@NotEmpty
	private String sql;
	@Column
	private String discription;
	@Column
	@NotEmpty
	private String enabledFlag;

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getReportCode() {
		return reportCode;
	}

	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

}
