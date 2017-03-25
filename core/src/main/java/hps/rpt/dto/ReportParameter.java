package hps.rpt.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.system.dto.BaseDTO;

import hps.rpt.util.ReportUtil;

/**
 * @name ReportParameter
 * @description 报表参数列DTO
 * @author dezhi.shen@hand-china.com 2016年8月25日下午4:19:08
 * @version 1.0
 */
@Table(name = "hps_rpt_report_parameters")
public class ReportParameter extends BaseDTO {
	private static final long serialVersionUID = 1681940033881322621L;

	public static enum DATATYPE {
		DATE, TEXT, NUMBER;
		public String value;

		private DATATYPE() {
			this.value = this.toString();
		}
	}

	public static enum FILEDTYPE {
		DATE, TEXT, LOV, SELECT;
		public String value;

		private FILEDTYPE() {
			this.value = this.toString();
		}
	}

	@Id
	@Column
	private Long parameterId;
	@Column
	@NotNull
	private Long reportId;
	@Column
	@NotEmpty
	private String sqlcolName;
	@Column
	@NotEmpty
	private String dataType;
	@Column
	private String parameterSql;
	@Column
	@NotEmpty
	private String filedType;
	@Column
	@NotEmpty
	private String filedDisplay;
	@Column
	@NotEmpty
	private String requiredFlag;
	@Column
	@NotNull
	private Double rowNum;
	@Column
	private String dateFormat;
	@Column
	private String filedCode;
	@Column
	private String textFiled;
	@Column
	private String valueFiled;

	@Transient
	private String newLine;

	public String getNewLine() {
		return newLine;
	}

	public void setNewLine(String newLine) {
		this.newLine = newLine;
	}

	public ReportParameter() {
		// TODO Auto-generated constructor stub
	}

	public Long getParameterId() {
		return parameterId;
	}

	public void setParameterId(Long parameterId) {
		this.parameterId = parameterId;
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

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getParameterSql() {
		return parameterSql;
	}

	public void setParameterSql(String parameterSql) {
		this.parameterSql = parameterSql;
	}

	public String getFiledType() {
		return filedType;
	}

	public void setFiledType(String filedType) {
		this.filedType = filedType;
	}

	public String getFiledDisplay() {
		return filedDisplay;
	}

	public void setFiledDisplay(String filedDisplay) {
		this.filedDisplay = filedDisplay;
	}

	public String getRequiredFlag() {
		return requiredFlag;
	}

	public void setRequiredFlag(String requiredFlag) {
		this.requiredFlag = requiredFlag;
	}

	public Double getRowNum() {
		return rowNum;
	}

	public void setRowNum(Double rowNum) {
		this.rowNum = rowNum;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getFiledCode() {
		return filedCode;
	}

	public void setFiledCode(String filedCode) {
		this.filedCode = filedCode;
	}

	public String getTextFiled() {
		return textFiled;
	}

	public void setTextFiled(String textFiled) {
		this.textFiled = textFiled;
	}

	public String getValueFiled() {
		return valueFiled;
	}

	public void setValueFiled(String valueFiled) {
		this.valueFiled = valueFiled;
	}

	public String createFileds() {
		StringBuffer sb = new StringBuffer();
		sb.append("{ display : '" + this.filedDisplay + "',\n");
		sb.append("newline: " + ("Y".equals(newLine) ? "true" : "false") + ",\n");
		sb.append("validate : { \nrequired: " + ("Y".equals(this.requiredFlag) ? "true" : "false") + "\n},\n");
		sb.append("name :'" + this.sqlcolName + "',\n");
		if (FILEDTYPE.DATE.value.equals(this.filedType)) {
			sb.append("type : 'date' ,\n");
			if (this.dateFormat.toUpperCase().indexOf("HH") != -1) {
				sb.append(" options   : { \n showTime    : false,\n");
				sb.append("format : '" + this.dateFormat + "'");
			} else {
				sb.append(" options   : { \n showTime    :true\n");
			}
			sb.append("}");
			setFiledCode(sb.toString());
			return sb.toString();
		}
		List<ReportColumn> rs = new ArrayList<ReportColumn>();
		List<ReportParameter> rps = new ArrayList<ReportParameter>();
		try {
			if (parameterSql != null) {
				rs = ReportUtil.getResults(parameterSql);
				rps = ReportUtil.getParametersAndType(parameterSql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (FILEDTYPE.LOV.value.equals(filedType)) {
			sb.append("type : 'popup',\n");
			sb.append("textFiled : '" + this.textFiled + "',\n");
			sb.append("options  : {\n");
			sb.append("valueField : '" + this.valueFiled + "',\n");
			sb.append("textField: '" + this.textFiled + "',\n");
			sb.append("condition : { \n fields: [");
			// conditions:
			for (int i = 0; i < rps.size(); i++) {
				ReportParameter rpa = rps.get(i);
				sb.append("{display : '" + rpa.sqlcolName + "',\n");
				sb.append("name:'" + rpa.sqlcolName + "',\n");
				sb.append("newline:" + ((i + 1) % 2 == 0 && i != 0 ? "true" : "false") + ",\n");
				sb.append("type :'text' }");
				if (i != rps.size() - 1) {
					sb.append(",");
				}
			}
			sb.append("]},");
			sb.append("grid: { url: 'base.contextPath/rpt/filed/query?parameterId=" + this.parameterId + "',\n");

			sb.append("parms:{ ");
			// for (int i = 0; i < rps.size(); i++) {
			// ReportParameter rpa = rps.get(i);
			// sb.append(rpa.parameterCode+"
			// :getLigerValue('"+rpa.parameterCode+"')");
			// sb.append(",\n");
			// }
			sb.append("valueNames :[");
			for (int i = 0; i < rps.size(); i++) {
				ReportParameter rpa = rps.get(i);
				sb.append("'" + rpa.sqlcolName + "'");
				if (i != rps.size() - 1) {
					sb.append(",");
				}
			}
			sb.append("]},\n");

			sb.append("columns  : [");
			// columns
			for (int i = 0; i < rs.size(); i++) {
				ReportColumn ReportColumn = rs.get(i);
				sb.append("{display: '" + ReportColumn.getSqlcolName() + "',\n");
				sb.append("name: '" + ReportColumn.getSqlcolName() + "',\n");
				sb.append("width:200,\n");
				sb.append("}");
				if (i != rs.size() - 1) {
					sb.append(",");
				}
			}
			sb.append("]}");
			sb.append("}");
			sb.append("}");
			this.setFiledCode(sb.toString());
			return sb.toString();
		}
		if (FILEDTYPE.SELECT.value.equals(filedType)) {
			sb.append("type : 'select',\n");
			sb.append("textFiled : '" + this.textFiled + "',\n");
			sb.append("options  : {\n");
			sb.append("valueField : '" + this.valueFiled + "',\n");
			sb.append("textField: '" + this.textFiled + "',\n");
			sb.append("url: 'base.contextPath/rpt/filed/query?parameterId=" + this.parameterId + "',\n");

			sb.append("}");// options {
			sb.append("}");// fileds {
			this.setFiledCode(sb.toString());
			return sb.toString();
		}

		if (FILEDTYPE.TEXT.value.equals(this.filedType)) {
			sb.append("type : 'text' }");
			return sb.toString();
		}
		return null;
	}
}
