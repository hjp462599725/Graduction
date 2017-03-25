package hps.rpt.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.system.dto.BaseDTO;

/**
 * @name ReportColumn
 * @description 报表结果列DTO
 * @author dezhi.shen@hand-china.com	2016年8月25日下午4:18:44
 * @version 1.0
 */
@Table(name = "hps_rpt_report_columns")
public class ReportColumn extends BaseDTO {
	private static final long serialVersionUID = -944081609000208555L;
	@Id
	@GeneratedValue(generator = GENERATOR_TYPE)
	@Column
	private Long columnId;
	@Column
	@NotNull
	private Long reportId;
	@Column
	@NotEmpty
	private String sqlcolName;
	@Column
	@NotEmpty
	private String displayName;
	@Column
	@NotEmpty
	private String dataType;
	@Column
	private Long precision;
	@Column
	private Long scale;
	@Column
	private String dateFormat;
	@Column
	private String timeFormat;
	@Column
	@NotEmpty
	private String showFlag;
	public Long getColumnId() {
		return columnId;
	}
	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public String getSqlcolName() {
		return sqlcolName;
	}
	public void setSqlcolName(String sqlcolName) {
		this.sqlcolName = sqlcolName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Long getPrecision() {
		return precision;
	}
	public void setPrecision(Long precision) {
		this.precision = precision;
	}
	public Long getScale() {
		return scale;
	}
	public void setScale(Long scale) {
		this.scale = scale;
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public String getTimeFormat() {
		return timeFormat;
	}
	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}
	public String getShowFlag() {
		return showFlag;
	}
	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}

}
