package hps.alt.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.system.dto.BaseDTO;

/**
 * @name AlertOutput
 * @description 预警输出参数DTO
 * @author zili.wang@hand-china.com 2016年9月8日上午9:58:21
 * @version 1.0
 */
@Table(name = "HPS_ALT_ALERT_OUTPUT")
public class AlertOutput extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1537101808133754824L;
	@Id
	@Column
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long alertOutputId;
	@Column
	@NotEmpty
	private Long alertId;
	@Column
	private String outputName;
	@Column
	private String outputTitle;
	@Column
	private Long detailMaxLen;
	@Column
	private Long summaryMaxLen;

	public Long getAlertOutputId() {
		return alertOutputId;
	}

	public void setAlertOutputId(Long alertOutputId) {
		this.alertOutputId = alertOutputId;
	}

	public Long getAlertId() {
		return alertId;
	}

	public void setAlertId(Long alertId) {
		this.alertId = alertId;
	}

	public String getOutputName() {
		return outputName;
	}

	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

	public String getOutputTitle() {
		return outputTitle;
	}

	public void setOutputTitle(String outputTitle) {
		this.outputTitle = outputTitle;
	}

	public Long getDetailMaxLen() {
		return detailMaxLen;
	}

	public void setDetailMaxLen(Long detailMaxLen) {
		this.detailMaxLen = detailMaxLen;
	}

	public Long getSummaryMaxLen() {
		return summaryMaxLen;
	}

	public void setSummaryMaxLen(Long summaryMaxLen) {
		this.summaryMaxLen = summaryMaxLen;
	}

}
