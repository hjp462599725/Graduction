package hps.rpt.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import hps.rpt.dto.ReportColumn;
import hps.rpt.dto.ReportParameter;
import hps.rpt.service.DataSourceManage;

public class ReportUtil {
	private static Logger logger = LoggerFactory.getLogger(ReportUtil.class);
	/*public static DataSource dataSource;
	private static Connection con;

	static {
		Context context;
		try {
			context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/hps_dev");
			con = dataSource.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	/**
	 * 获得结果列表
	 * 
	 * @param sql
	 *            需要解析的sql
	 * @return List<ReportColumn>
	 * @throws Exception
	 */
	public static List<ReportColumn> getResults(String sql) throws Exception {
		List<ReportColumn> cols = new ArrayList<ReportColumn>();
		Connection con = DataSourceManage.getConnection();
		PreparedStatement ps = con.prepareCall(sql.toUpperCase().split("WHERE")[0] + " Where 1 = 0");
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			ReportColumn e = new ReportColumn();
			String col = rsmd.getColumnName(i);
			e.setSqlcolName(col);
			e.setDisplayName(getProName(col));
			e.setDataType(rsmd.getColumnTypeName(i));
			e.setShowFlag("Y");
			cols.add(e);
		}
		rs.close();
		ps.close();
		return cols;
	}

	/**
	 * 内部类 ，存储参数Code和参数类型
	 * 
	 * @author dezhi.shen@hand-china.com
	 *
	 */
	private static class ParamAndType {
		private String param;
		private String type;

		/*public String getParam() {
			return param;
		}*/

		public void setParam(String param) {
			this.param = param;
		}

		/*public String getType() {
			return type;
		}
*/
		public void setType(String type) {
			this.type = type;
		}

		@Override
		public int hashCode() {
			int result = 1;
			return result;
		}

	}

	/**
	 * 根据sql获得参数和数据类型
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static List<ReportParameter> getParametersAndType(String sql) throws Exception {
		List<ReportParameter> parames = new ArrayList<ReportParameter>();
		String conditions = sql;
		String s[] = conditions.split("\\{");
		// 存储列名
		HashSet<ParamAndType> a = new HashSet<ParamAndType>();
		for (int i = 0; i < s.length; i++) {
			if (i == 0) {
				continue;
			}
			String string = s[i].split("}")[0];
			String result = string.trim();
			String paAndType[] = result.split(",");
			ParamAndType e = new ParamAndType();
			e.setParam(paAndType[0].trim());
			e.setType(paAndType[1].trim());
			// HashSet去重
			a.add(e);
		}
		for (ParamAndType paramAndType : a) {
			ReportParameter e = new ReportParameter();
			e.setSqlcolName(paramAndType.param);
			e.setDataType(paramAndType.type);
			parames.add(e);
		}
		return parames;
	}

	/**
	 * 根据sql获得条件中的参数
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static List<ReportParameter> getParameters(String sql) throws Exception {
		List<ReportParameter> parames = new ArrayList<ReportParameter>();
		String conditions = sql;
		String s[] = conditions.split("\\{");
		// 存储列名
		HashSet<String> a = new HashSet<String>();
		for (int i = 0; i < s.length; i++) {
			if (i == 0) {
				continue;
			}
			String string = s[i].split("}")[0];
			String result = string.trim();
			// HashSet去重
			a.add(result);
		}
		for (String string : a) {
			ReportParameter e = new ReportParameter();
			e.setSqlcolName(string);
			parames.add(e);
		}
		return parames;
	}

	/**
	 * 将列名转化 例如 table_name/TABLE_NAME => tableName
	 * 
	 * @param colName
	 * @return
	 */
	protected static String getProName(String colName) {
		StringBuffer proName = new StringBuffer();
		String cols[] = colName.toLowerCase().split("_");
		for (int i = 0; i < cols.length; i++) {
			String string = cols[i];
			if (i != 0) {
				proName.append(string.toUpperCase().charAt(0));
				proName.append(string.substring(1));
			} else {
				proName.append(string);
			}
		}
		return proName.toString();
	}

	/**
	 * 将处理好的sql运行，并且取值
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> executeSql(String sql) throws Exception {
		logger.info("进入executeSql()方法");
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		Connection con = DataSourceManage.getConnection();
		PreparedStatement ps = con .prepareCall(sql);
		ResultSet rSet = ps.executeQuery();
		ResultSetMetaData rsmd = rSet.getMetaData();
		while (rSet.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				map.put(rsmd.getColumnName(i), rSet.getObject(rsmd.getColumnLabel(i)));
			}
			maps.add(map);
		}
		logger.info("离开executeSql()方法");
		return maps;
	}

	/**
	 * 将待处理的SQL 加工成可以运行的sql
	 * 
	 * @param sql
	 * @param value
	 *            key : 参数代码 Object :参数值
	 * @param reportParameters
	 * @return
	 */
	public static String processSql(String sql, Map<String, Object> value, List<ReportParameter> reportParameters) {
		logger.info("进入processSql()方法");
		StringBuffer sb = new StringBuffer();
		String pars[] = sql.split("\\{");
		for (int i = 0; i < pars.length; i++) {
			String temp = pars[i];
			if (i == 0) {
				sb.append(temp);
			} else {
				String pars2[] = temp.split("}");
				for (int j = 0; j < pars2.length; j++) {
					if (j == 0) {
						if (pars2[j].indexOf(",") != -1) {
							String pars3[] = pars2[j].split(",");
							String key = pars3[0].trim();
							String type = pars3[1].trim();
							if (ReportParameter.DATATYPE.DATE.value.equals(type)) {
								sb.append(" to_date('" + value.get(key) + "','yyyy/mm/dd hh24:mi:ss') ");
							} else if (ReportParameter.DATATYPE.TEXT.value.equals(type)) {
								sb.append(" '" + value.get(key) + "' ");
							} else if (ReportParameter.DATATYPE.NUMBER.value.equals(type)) {
								sb.append(value.get(key));
							}
						} else {
							String key = pars2[j].trim();
							String type = null;
							if (value.get(key) == null) {
								sb.append("NULL");
							} else {
								for (ReportParameter rp : reportParameters) {
									if (rp.getSqlcolName().equals(key)) {
										type = rp.getDataType();
									}
								}
								if (ReportParameter.DATATYPE.DATE.value.equals(type)) {
									sb.append(" to_date('" + value.get(key) + "','yyyy/mm/dd hh24:mi:ss') ");
								} else if (ReportParameter.DATATYPE.TEXT.value.equals(type)) {
									sb.append(" '" + value.get(key) + "' ");
								} else if (ReportParameter.DATATYPE.NUMBER.value.equals(type)) {
									sb.append(value.get(key));
								}
							}
						}
					} else {
						sb.append(pars2[j]);
					}
				}
			}
		}
		logger.info("出processSql()方法");
		return sb.toString();
	}

	public static List<ReportParameter> setNewLines(List<ReportParameter> reportParameters) {
		List<ReportParameter> result = new ArrayList<ReportParameter>();
		for (int i = 0; i < reportParameters.size(); i++) {
			ReportParameter e;
			for (int j = i + 1; j < reportParameters.size(); j++) {
				if (reportParameters.get(i).getRowNum() > reportParameters.get(j).getRowNum()) {
					e = reportParameters.get(i);
					reportParameters.set(i, reportParameters.get(j));
					reportParameters.set(j, e);
				}
			}
			result.add(reportParameters.get(i));
		}
		for (int i = 0; i < result.size(); i++) {
			if (i == 0) {
				result.get(i).setNewLine("N");
			} else {
				int a = result.get(i).getRowNum().intValue();
				int b = result.get(i - 1).getRowNum().intValue();
				if (a > b) {
					result.get(i).setNewLine("Y");
				} else {
					result.get(i).setNewLine("N");
				}
			}
		}
		reportParameters = result;
		return result;
	}

	/**
	 * 动态创建 HTML
	 * 
	 * @param map
	 *            数据源
	 * @param basePath
	 *            当前环境路径
	 * @param tempPath
	 *            模板 所在文件夹(相对路径)
	 * @param tempName
	 *            模板名称
	 * @return 生成的HTML 文件流
	 * @throws Exception 
	 * 
	 */
	public static ByteArrayOutputStream createHtmlWithTemplate(Map<String, Object> map, String tempPath,
			String tempName) throws Exception {
			logger.info("进入createHtmlWithTemplate()方法");
			@SuppressWarnings("deprecation")
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(tempPath));
			Template t = cfg.getTemplate(tempName);
			StringWriter out = new StringWriter();
			t.process(map, out);
			out.flush();
			ByteArrayOutputStream outs = new ByteArrayOutputStream();
			outs.write(out.toString().getBytes());
			out.close();
			logger.info("离开createHtmlWithTemplate()方法");
			return outs;
	}

}
