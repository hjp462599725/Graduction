package hps.alt.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.annotation.Children;
import com.hand.hap.system.dto.BaseDTO;

/**
 * @name AltAlert
 * @description 预警信息DTO
 * @author zili.wang@hand-china.com 2016年9月8日上午9:59:20
 * @version 1.0
 */
@Table(name = "hps_alt_alert")
public class AltAlert extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2985379373280926285L;
	@Id
	@Column	
	@GeneratedValue(generator = GENERATOR_TYPE)
	private Long alertId;
	@Column
	private String alertName;
	@Column
	private String description;
	@Column
	private String alertTypeCode;
	@Column
	private String enabledFlag;
	@Column
	private String sqlStatementText;
	@Column
	private String tableOwner;
	@Column
	private String tableName;
	@Column
	private String insertFlag;
	@Column
	private String updateFlag;
	@Column
	private String deleteFlag;
	@Column
	private String frequencyTypeCode;
	@Column
	private Long intervalCycle;
	@Column
	private Long monthlyDayNum;
	@Column
	private String weeklyDayMon;
	@Column
	private String weeklyDayTues;
	@Column
	private String weeklyDayWed;
	@Column
	private String weeklyDayThur;
	@Column
	private String weeklyDayFri;
	@Column
	private String weeklyDaySat;
	@Column
	private String weeklyDaySun;
	@Column
	@JsonFormat(pattern="HH:mm:ss")
	private Date executionTime;
	@Column
	@JsonFormat(pattern="HH:mm:ss")
	private Date executionStartTime;
	@Column
	@JsonFormat(pattern="HH:mm:ss")
	private Date executionEndTime;
	@Column
	private Long continuousCycle;
	@Column
	@JsonFormat(pattern="HH:mm:ss")
	private Date cycleStartTime;
	@Column
	@JsonFormat(pattern="HH:mm:ss")
	private Date cycleEndTime;
	@Column
	private String cronExpression;
	@Children
	@Transient
	private List<AlertAction> alertActions;
	
	
	public List<AlertAction> getAlertActions() {
		return alertActions;
	}

	public void setAlertActions(List<AlertAction> alertActions) {
		this.alertActions = alertActions;
	}

	public Long getAlertId() {
		return alertId;
	}

	public void setAlertId(Long alertId) {
		this.alertId = alertId;
	}

	public String getAlertName() {
		return alertName;
	}

	public void setAlertName(String alertName) {
		this.alertName = alertName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAlertTypeCode() {
		return alertTypeCode;
	}

	public void setAlertTypeCode(String alertTypeCode) {
		this.alertTypeCode = alertTypeCode;
	}

	public String getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	public String getSqlStatementText() {
		return sqlStatementText;
	}

	public void setSqlStatementText(String sqlStatementText) {
		this.sqlStatementText = sqlStatementText;
	}

	public String getTableOwner() {
		return tableOwner;
	}

	public void setTableOwner(String tableOwner) {
		this.tableOwner = tableOwner;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getInsertFlag() {
		return insertFlag;
	}

	public void setInsertFlag(String insertFlag) {
		this.insertFlag = insertFlag;
	}

	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getFrequencyTypeCode() {
		return frequencyTypeCode;
	}

	public void setFrequencyTypeCode(String frequencyTypeCode) {
		this.frequencyTypeCode = frequencyTypeCode;
	}

	public Long getIntervalCycle() {
		return intervalCycle;
	}

	public void setIntervalCycle(Long intervalCycle) {
		this.intervalCycle = intervalCycle;
	}

	public Long getMonthlyDayNum() {
		return monthlyDayNum;
	}

	public void setMonthlyDayNum(Long monthlyDayNum) {
		this.monthlyDayNum = monthlyDayNum;
	}

	public String getWeeklyDayMon() {
		return weeklyDayMon;
	}

	public void setWeeklyDayMon(String weeklyDayMon) {
		this.weeklyDayMon = weeklyDayMon;
	}

	public String getWeeklyDayTues() {
		return weeklyDayTues;
	}

	public void setWeeklyDayTues(String weeklyDayTues) {
		this.weeklyDayTues = weeklyDayTues;
	}

	public String getWeeklyDayWed() {
		return weeklyDayWed;
	}

	public void setWeeklyDayWed(String weeklyDayWed) {
		this.weeklyDayWed = weeklyDayWed;
	}

	public String getWeeklyDayThur() {
		return weeklyDayThur;
	}

	public void setWeeklyDayThur(String weeklyDayThur) {
		this.weeklyDayThur = weeklyDayThur;
	}

	public String getWeeklyDayFri() {
		return weeklyDayFri;
	}

	public void setWeeklyDayFri(String weeklyDayFri) {
		this.weeklyDayFri = weeklyDayFri;
	}

	public String getWeeklyDaySat() {
		return weeklyDaySat;
	}

	public void setWeeklyDaySat(String weeklyDaySat) {
		this.weeklyDaySat = weeklyDaySat;
	}

	public String getWeeklyDaySun() {
		return weeklyDaySun;
	}

	public void setWeeklyDaySun(String weeklyDaySun) {
		this.weeklyDaySun = weeklyDaySun;
	}

	public Date getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}

	public Date getExecutionStartTime() {
		return executionStartTime;
	}

	public void setExecutionStartTime(Date executionStartTime) {
		this.executionStartTime = executionStartTime;
	}

	public Date getExecutionEndTime() {
		return executionEndTime;
	}

	public void setExecutionEndTime(Date executionEndTime) {
		this.executionEndTime = executionEndTime;
	}

	public Long getContinuousCycle() {
		return continuousCycle;
	}

	public void setContinuousCycle(Long continuousCycle) {
		this.continuousCycle = continuousCycle;
	}

	public Date getCycleStartTime() {
		return cycleStartTime;
	}

	public void setCycleStartTime(Date cycleStartTime) {
		this.cycleStartTime = cycleStartTime;
	}

	public Date getCycleEndTime() {
		return cycleEndTime;
	}

	public void setCycleEndTime(Date cycleEndTime) {
		this.cycleEndTime = cycleEndTime;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

}
