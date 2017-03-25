package hps.fnd.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;

import hps.fnd.dto.FlexVset;
import hps.fnd.dto.ValidateTable;
import hps.fnd.util.ValidateTableException;

/**
 * @name IValidateTableService
 * @description 表验证值集服务接口
 * @author tianle.liu@hand-china.com 2016年8月22日下午4:02:35
 * @version 1.0
 */
public interface IValidateTableService extends IBaseService<ValidateTable>, ProxySelf<IValidateTableService> {

	/**
	 * 查询表验证值集验证行数据
	 * 
	 * @param validationTable
	 *            表验证值集验证行DTO
	 * @param request
	 *            上下文请求
	 * @param page
	 *            页码
	 * @param pagesize
	 *            页大小
	 * @return List<ValidationTable>
	 */
	public List<ValidateTable> selectValidateTables(ValidateTable validateTable, IRequest request, int page,
			int pagesize);

	/**
	 * 更新表验证值集
	 * 
	 * @param flexValueSets
	 *            值集头
	 * @param request
	 *            上下文请求
	 * @return List<FlexValueSet>
	 * @version 1.0
	 * @author dezhi.shen@hand-china.com
	 * @date 2016.8.30
	 * @version 1.1 修改columnFlag标识验证
	 */
	public List<FlexVset> updateValidateTables(@StdWho List<FlexVset> flexValueSets, IRequest request)
			throws ValidateTableException;

	/**
	 * getLov
	 * 
	 * @param contextPath
	 *            项目路径
	 * @param locale
	 *            当前语言环境
	 * @param params
	 *            联动参数
	 * @param flexVsetName
	 *            值集名称
	 * @param type
	 *            控件类型 grid or form
	 * @param filedName
	 *            控件管理变量的变量名
	 * @return Lov前台代码
	 * @date 2016年8月30日 上午9:51:40
	 * @author dezhi.shen@hand-china.com
	 * @returnType String
	 */
	public String getLov(String contextPath, String locale, String params, String flexVsetName, String type,
			String filedName);

	/**
	 * 根据传入的 ValidationTable 对象形成查询条件进入数据库查询 columnFlagCount 总数
	 * 
	 * @param iRequest
	 *            上下文请求
	 * @param validationTable
	 *            ValidationTable对象，用于形成查询条件
	 * @return columnFlagCount 总数
	 * @date 2016年8月30日 上午10:17:06
	 * @author dezhi.shen@hand-china.com
	 * @returnType Integer
	 */
	public Integer columnFlagCount(IRequest iRequest, ValidateTable validateTable);
}
