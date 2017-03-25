package hps.fnd.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.FreeMarkerBean;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hps.fnd.controllers.FlexValueController;
import hps.fnd.dto.FlexVset;
import hps.fnd.dto.ValidateTable;
import hps.fnd.mapper.FlexVsetMapper;
import hps.fnd.mapper.ValidateTableMapper;
import hps.fnd.service.IValidateTableService;
import hps.fnd.util.ValidateTableException;

/**
 * @name ValidateTableServiceImpl
 * @description 表验证值集服务实现类
 * @author tianle.liu@hand-china.com 2016年8月22日下午4:04:53
 * @version 1.0
 */
@Service
@FreeMarkerBean
public class ValidateTableServiceImpl extends BaseServiceImpl<ValidateTable> implements IValidateTableService {

	private static Logger logger = LoggerFactory.getLogger(ValidateTableServiceImpl.class);
	private static String loggerHeader = "ValiDationLov ";
	@Autowired
	private ValidateTableMapper validateTableMapper;
	@Autowired
	private FlexVsetMapper flexValueSetMapper;
	private static String FORM = "form";
	private static String GRID = "grid";

	@Override
	public List<ValidateTable> selectValidateTables(ValidateTable validateTable, IRequest request, int page,
			int pagesize) {
		PageHelper.startPage(page, pagesize);
		return validateTableMapper.select(validateTable);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ValidateTableException.class)
	public List<FlexVset> updateValidateTables(@StdWho List<FlexVset> flexValueSets, IRequest iRequest)
			throws ValidateTableException {
		for (FlexVset flexVset : flexValueSets) {
			flexValueSetMapper.updateFlexValueSetValidateById(flexVset);
			for (ValidateTable vt : flexVset.getValidationTables()) {
				switch (vt.get__status()) {
				case DTOStatus.ADD:
					validateTableMapper.insertSelective(vt);
					break;
				case DTOStatus.UPDATE:
					validateTableMapper.updateValidateTableById(vt);
					break;
				/*case DTOStatus.DELETE:
					validateTableMapper.deleteByPrimaryKey(vt);*/
				default:
					break;
				}
				StringBuffer conditions = new StringBuffer();
				// 校验column_flag =="Y"唯一
				conditions.append(
						" 1 = 1 AND  column_flag = 'Y' AND flex_value_set_id = " + flexVset.getFlexValueSetId());
				Integer count = validateTableMapper.columnFlagCount(conditions.toString());
				if (count == 0) {
					throw new ValidateTableException(FlexValueController.NOT_FOUND_COLUMNFLAG, null);
				}
				if (count > 1) {
					throw new ValidateTableException(FlexValueController.NOT_UNIQUE_COLUMNFLAG, null);
				}
			}

			//校验列名(别名)和ValueField 唯一
			ValidateTable  validateTable = new ValidateTable();
			validateTable.setFlexValueSetId(flexVset.getFlexValueSetId());
			List<ValidateTable> tables = validateTableMapper.select(validateTable);
			Set<String> aliasSet = new HashSet<String>();
			Set<String> valueField = new HashSet<String>();
			for (ValidateTable table : tables) {
				String string ;
				if(table.getColumnAlias()==null||("").equals(table.getColumnAlias().trim())){
					string = table.getColumnName().trim().toUpperCase();
					Integer index = string.indexOf(".");
					if (index >= 0) {
						string = string.substring(index+1).trim();
					}
				}else{
					string = table.getColumnAlias().trim().toUpperCase();
				}
				aliasSet.add(string);
				valueField.add(table.getValueField().trim());
			}
			if(tables.size()!=aliasSet.size()){
				throw new ValidateTableException(FlexValueController.NOT_UNIQUE_COLUMNALIAS, null);
			}else if(tables.size()!=valueField.size()){
				throw new ValidateTableException(FlexValueController.NOT_UNIQUE_VALUEFIELDS, null);
			}	
		}
		return flexValueSets;
	}

	@Override
	public String getLov(String contextPath, @RequestParam(defaultValue = "zh_CN") String locale, String params,
			String flexVsetName, String type, String filedName) {
		// TODO Auto-generated method stub
		boolean paramsIsNotNullFlag = false;
		logger.info(loggerHeader + "start to getLov...");
		if (flexVsetName == null || flexVsetName.trim() == "") {
			logger.info(loggerHeader + "Have not found flexValueSetName");
			return "'Have not found flexValueSetName'";
		}
		FlexVset set = new FlexVset();
		set.setFlexValueSetName(flexVsetName);
		set.setEnabledFlag("Y");
		List<FlexVset> sets = flexValueSetMapper.select(set);
		logger.info(loggerHeader + "query FlexVset with flexValueSetName:" + flexVsetName);
		if (sets == null || sets.size() != 1) {
			logger.info(loggerHeader + "the FlexValueSetName[" + flexVsetName + "] Error");
			return "'the FlexValueSetName[" + flexVsetName + "] Error'";
		}
		if (!"F".equals(sets.get(0).getValidationType())) {
			logger.info(loggerHeader + "the ValidationType[" + sets.get(0).getValidationType() + "] Error");
			return "'the ValidationType[" + sets.get(0).getValidationType() + "] Error'";
		}
		String tableName = sets.get(0).getTableName();
		if (tableName == null || tableName.trim() == "") {
			logger.info(loggerHeader + "Have not found tableName in the flexValueSet");
			return "'Have not found tableName in the flexValueSet'";
		}
		if (!(params == null || ("").equals(params.trim()))) {
			logger.info(loggerHeader + "linkage params is not null");
			paramsIsNotNullFlag = true;
		}
		StringBuffer parm = new StringBuffer();
		StringBuffer onloadBuffer = new StringBuffer();
		if (paramsIsNotNullFlag) {
			logger.info(loggerHeader + "outPut linkage params :[");
			String args0[] = params.split(",");
			parm.append("parms:{\nparamColNames: '");
			// 联动函数
			if (params != null && params.trim() != "") {
				onloadBuffer.append("onLoadData: function() {");
				for (int i = 0; i < args0.length; i++) {
					String string = args0[i];
					String args1[] = string.split(":");
					onloadBuffer.append("this.setParm('" + args1[0] + "', " + args1[1] + ");\n");
					parm.append(args1[0]);
					if (i != args0.length - 1) {
						parm.append(",");
					}
					logger.info(loggerHeader + args1[0] + " : " + args1[1]);
				}
				onloadBuffer.append("},");
				parm.append("'},\n");
			}
			logger.info(loggerHeader + "]");
		}
		StringBuffer sb = new StringBuffer();
		ValidateTable condition = new ValidateTable();
		condition.setFlexValueSetId(sets.get(0).getFlexValueSetId());
		List<ValidateTable> tables = this.validateTableMapper.selectByIdAndLoacle(condition, locale);
		sb.append("{\n");
		sb.append("title :'" + sets.get(0).getTitle() + "',\n");
		logger.info(loggerHeader + "Lov title :" + sets.get(0).getTitle());
		String textField = "";
		String valueField = "";
		StringBuffer onSelectedBuffer = new StringBuffer("onSelected : function (obj){\n var data = obj.data[0];\n");
		// 获得前台控件中的原始键值对 对象
		if (FORM.equals(type)) {
			onSelectedBuffer.append("var editData = " + filedName + ".getData();\n");
		} else if (GRID.equals(type)) {
			onSelectedBuffer.append("var editData = " + filedName + ".lastEditRow;\n");
			sb.append("type : 'popup',\n");
		} else {
			return "'the FieldType Error'";
		}
		// gridCode
		StringBuffer gridBuffer = new StringBuffer("grid :{\n\tcolumns:[");
		boolean delayLoad = false;
		StringBuffer conditionstrBuffer = new StringBuffer("condition:{\nfields: [");
		for (int i = 0; i < tables.size(); i++) {
			ValidateTable validateTable = tables.get(i);
			if ("Y".equals(validateTable.getConditionFlag())) {
				// 如果是查询条件行
				delayLoad = true;
				conditionstrBuffer.append("{display : '" + validateTable.getDescription() + "',\n");
				conditionstrBuffer.append("name:'" + validateTable.getValueField().trim() + "',\n");
				conditionstrBuffer.append("width:" + validateTable.getWidth() + ",\n");
				conditionstrBuffer.append("type : 'text'\n},");
			}
			if ("Y".equals(validateTable.getColumnFlag())) {
				// 如果是结果列
				textField = validateTable.getTextField();
				valueField = validateTable.getValueField();
			}
			if (validateTable.getTextField() != null) {
				// onSelected方法拼接
				onSelectedBuffer.append("editData." + validateTable.getTextField() + " = data."
						+ validateTable.getValueField() + ";\n");
			}
			// LOV中表格代码 拼接
			gridBuffer.append("{");
			gridBuffer.append("display : '" + validateTable.getDescription() + "',\n");
			gridBuffer.append("name : '" + validateTable.getValueField().trim() + "',\n");
			gridBuffer.append("width:" + validateTable.getWidth() + ",\n");
			gridBuffer.append("align:'" + validateTable.getAlgin() + "',\n");
			gridBuffer.append("hide : " + ("Y".equals(validateTable.getHiddenFlag())) + "\n");
			gridBuffer.append("}");
			if (i != tables.size() - 1) {
				gridBuffer.append(",");
			}
		}
		if (sets.get(0).getExpandMethod() != null) {
			// 拓展方法拼接进入到onSelected方法中
			onSelectedBuffer.append(sets.get(0).getExpandMethod());
		}
		// 添加数据写会控件代码
		if (FORM.equals(type)) {
			onSelectedBuffer.append(filedName + ".setData(editData);},\n");
		} else if (GRID.equals(type)) {
			onSelectedBuffer.append(filedName + ".reRender()},\n");
		}
		conditionstrBuffer.append("]},\n");
		gridBuffer.append("],\n\turl: '" + contextPath + "/fnd/validationtable/queryLovData?flexValueSetId="
				+ sets.get(0).getFlexValueSetId() + "',\n");
		if (delayLoad) {
			gridBuffer.append("delayLoad:" + ("Y".equals(sets.get(0).getDelayedLoadingFlag())) + ",\n");
		} else {
			gridBuffer.append("delayLoad: false ,\n");
		}
		gridBuffer.append(parm);
		gridBuffer.append("width:" + (sets.get(0).getWidth() == null ? 500 : sets.get(0).getWidth()) + ",\n");
		gridBuffer.append("height:" + (sets.get(0).getHeight() == null ? 260 : sets.get(0).getHeight()) + ",\n");
		sb.append(onSelectedBuffer);
		sb.append(onloadBuffer);
		sb.append(conditionstrBuffer);
		sb.append("textField:'" + textField + "',\n");
		sb.append("valueField:'" + valueField + "',\n");
		sb.append(gridBuffer);
		sb.append("}}");
		return sb.toString();
	}

	@Override
	public Integer columnFlagCount(IRequest iRequest, ValidateTable validateTable) {
		// TODO Auto-generated method stub
		StringBuffer conditions = new StringBuffer(
				" 1 = 1 AND column_flag = 'Y' AND flex_value_set_id = " + validateTable.getFlexValueSetId());
		if (DTOStatus.UPDATE.equals(validateTable.get__status())) {
			conditions.append(" AND validate_table_id <> " + validateTable.getValidateTableId());
		}
		return validateTableMapper.columnFlagCount(conditions.toString());
	}
}
