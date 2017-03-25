package hps.fnd.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hps.fnd.dto.FlexVset;
import hps.fnd.dto.ValidateTable;
import hps.fnd.service.IFlexVsetService;
import hps.fnd.service.IValidateTableService;

@Controller
public class FlexRequestProcessController extends BaseController {
	// 日志
	private static Logger logger = LoggerFactory.getLogger(FlexValueController.class);
	private static String loggerHeader = "FlexValueQueryLovData ";
	@Autowired
	private IFlexVsetService flexVsetService;
	@Autowired
	private IValidateTableService validationTableService;
	@Autowired
	private BeanFactory beanFactory;

	/**
	 * 查询自定义Lov中的数据源
	 * 
	 * @param FlexVsetId
	 *            自定义值集Id
	 * @param request
	 *            上下文请求
	 * @param page
	 *            页码
	 * @param pagesize
	 *            页面Size
	 * @return 符合条件的LOV数据源
	 * @date 2016年8月30日 下午8:03:42
	 * @author dezhi.shen@hand-china.com
	 * @returnType ResponseData
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "/fnd/validationtable/queryLovData")
	@ResponseBody
	public ResponseData queryLovData(Long flexValueSetId, HttpServletRequest request,
			@RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
		logger.info(loggerHeader + "start query lov data...");
		IRequest iRequest = createRequestContext(request);
		com.github.pagehelper.Page<Map<String, Object>> result = null;
		logger.info(loggerHeader + "start query FlexVset info...");
		FlexVset conditionFlexVset = new FlexVset();
		conditionFlexVset.setFlexValueSetId(flexValueSetId);
		List<FlexVset> sets = this.flexVsetService.select(iRequest, conditionFlexVset, 1, 1);
		String tableName = sets.get(0).getTableName();
		logger.info(loggerHeader + "tableNames:" + tableName);
		StringBuffer whereCondition = new StringBuffer();
		if (sets.get(0).getWhereCondition() != null) {
			whereCondition.append(sets.get(0).getWhereCondition());
		}
		ValidateTable condition = new ValidateTable();
		condition.setFlexValueSetId(sets.get(0).getFlexValueSetId());
		List<ValidateTable> tables = validationTableService.select(iRequest, condition, 1, 1000);
		StringBuffer sql = new StringBuffer("select rownum as row_num ");
		Map<String, String[]> requestParameterMap = request.getParameterMap();
		if (!(whereCondition != null && whereCondition.toString().trim() != "")) {
			whereCondition.append(" WHERE 1 = 1 ");
		} else if ((whereCondition.toString().toUpperCase()).indexOf("WHERE") < 0) {
			whereCondition.append(" WHERE 1 = 1 ");
		}
		for (int i = 0; i < tables.size(); i++) {
			ValidateTable validationTable = tables.get(i);
			if ("Y".equals(validationTable.getEnabledFlag())) {
				// 拼列名
				sql.append(" ," + validationTable.getColumnName());
				if (validationTable.getColumnAlias() != null && !("").equals(validationTable.getColumnAlias().trim())) {
					//如果有别名
					sql.append(" as " + validationTable.getColumnAlias().trim());
				}
				if ("Y".equals(validationTable.getConditionFlag())) {
					String value[] = requestParameterMap.get(validationTable.getValueField());
					if (value != null && value.length != 0)
						whereCondition.append(" AND " + validationTable.getColumnName() + " like '" + value[0] + "' ");
				}
			}
		}
		sql.append(" from " + tableName + " " + whereCondition.toString());
		// 获得联动的参数
		logger.info(loggerHeader + "getonLoadParams...");
		Map<String, String> onLoadDataParamMap = new HashMap<String, String>();
		if (requestParameterMap.get("paramColNames") != null) {
			String paramColNames[] = requestParameterMap.get("paramColNames")[0].split(",");
			for (String string : paramColNames) {
				if (requestParameterMap.get(string) != null) {
					onLoadDataParamMap.put(string, requestParameterMap.get(string)[0]);
					logger.info("\t" + string + ":" + onLoadDataParamMap.get(string));
				}
			}
		}
		onLoadDataParamMap.put("request.locale", iRequest.getLocale());
		logger.info("\t" + "request.locale" + ":" + onLoadDataParamMap.get("request.locale"));
		onLoadDataParamMap.put("request.userId", iRequest.getUserId() + "");
		logger.info("\t" + "request.userId" + ":" + onLoadDataParamMap.get("request.userId"));
		onLoadDataParamMap.put("request.roleId", iRequest.getRoleId() + "");
		logger.info("\t" + "request.roleId" + ":" + onLoadDataParamMap.get("request.roleId"));
		logger.info(loggerHeader + "finnished get onLoadParams.");
		logger.info(loggerHeader + "processSql with the unProcessedSql:" + sql.toString());
		String sqlString = this.processSql(sql.toString(), onLoadDataParamMap);
		logger.info(loggerHeader + "Finnish process sql and the processedSql:" + sqlString);
		StringBuffer sqlCount = new StringBuffer("select count(0) from (");
		sqlCount.append(sqlString);
		sqlCount.append(")");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rSet = null;
		DataSource dataSource = (DataSource) beanFactory.getBean("dataSource");
		try {
			con = dataSource.getConnection();
			ps = con.prepareCall(sqlCount.toString());
			rSet = ps.executeQuery();
			int count = 0;
			while (rSet.next()) {
				count = rSet.getInt(1);
			}
			logger.info(loggerHeader + "Get the total with sql :" + sqlCount.toString() + " and the total :" + count);
			if (count != 0) {
				result = new com.github.pagehelper.Page<Map<String, Object>>();
				result.setPageNum(page);
				result.setEndRow(pagesize);
				result.setPageSize(pagesize);
				result.setTotal(count);
				result.setCount(true);
				// ArrayList<Map<String, Object>>();
				StringBuffer finallySql = new StringBuffer("select * from (");
				finallySql.append(sqlString);
				if (finallySql.toString().toUpperCase().indexOf("WHERE") < 0) {
					finallySql.append(" WHERE 1= 1 ");
				}
				finallySql.append(" AND rownum <= " + pagesize * page + ") ");
				finallySql.append(" WHERE row_num > " + pagesize * (page - 1));
				ps = con.prepareCall(finallySql.toString());
				logger.info(loggerHeader + "start to excute sql:" + finallySql.toString());
				rSet = ps.executeQuery();
				while (rSet.next()) {
					Map<String, Object> map = new HashMap<String, Object>();
					StringBuffer loggerInfo = new StringBuffer("[");
					for (int i = 0; i < tables.size(); i++) {
						ValidateTable validationTable = tables.get(i);
						if ("Y".equals(validationTable.getEnabledFlag())) {
							String key = validationTable.getValueField();
							String colName;
							if (validationTable.getColumnAlias() == null
									|| validationTable.getColumnAlias().trim() == "") {
								colName = validationTable.getColumnName();
							} else {
								colName = validationTable.getColumnAlias();
							}
							int beginIndex = colName.indexOf(".");
							if (beginIndex > 0) {
								colName = colName.substring(beginIndex + 1);
							}
							map.put(key, rSet.getObject(colName.trim()));
							loggerInfo.append(key + ":" + map.get(key) + ",");
						}
					}
					loggerInfo.append("]");
					logger.info(loggerHeader + loggerInfo.toString());
					result.add(map);
				}
				logger.info(loggerHeader + "finnish excute sql");
			} else {
				result = new com.github.pagehelper.Page<Map<String, Object>>();
				result.setTotal(count);
				result.setCount(true);
			}
			// con.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rSet != null) {
					rSet.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return new ResponseData(result);
	}

	protected String processSql(String sql, Map<String, String> value) {
		StringBuffer sb = new StringBuffer();
		String pars[] = sql.split("\\{");
		for (int i = 0; i < pars.length; i++) {
			String temp = pars[i];
			if (i == 0) {
				// 第一个参数前面的SQL语句
				sb.append(temp);
			} else {
				String pars2[] = temp.split("}");
				for (int j = 0; j < pars2.length; j++) {
					if (j == 0) {
						String pars3[] = pars2[j].split(",");
						String key = pars3[0].trim();
						String type = pars3[1].trim();
						String valueString = value.get(key);
						if (valueString == null||valueString.equals("")) {
							sb.append(" null ");
						} else if ("DATE".equals(type)) {
							sb.append(" to_date('" + valueString + "','YYYY-MM-DD HH24:MI:SS') ");
						} else if ("TEXT".equals(type)) {
							sb.append(" '" + valueString + "' ");
						} else if ("NUMBER".equals(type)) {
							sb.append(valueString);
						}

					} else {
						sb.append(pars2[j]);
					}
				}
			}
		}
		return sb.toString();
	}

}
