package hps.fnd.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;

import hps.fnd.dto.LookupValue;

/**
 * 
 * @name ILookupValueService
 * @description 快码行CURD
 * @author jieyang03@hand-china.com  2016年8月30日15:34:22
 * @version 1.0
 */
public interface ILookupValueService extends IBaseService<LookupValue>,ProxySelf<ILookupValueService>{
	/**
	 * 查询快码行数据
	 * @param request 请求
	 * @param lookupValue 快码行DTO
	 * @return List<LookupValue> 快码行集合
	 */
	List<LookupValue> selectLookupValues(IRequest request,LookupValue lookupValue);
	/**
	 * 根据type查询快码行数据
	 * @param request 请求
	 * @param lookupValue 快码行DTO
	 * @return List<LookupValue> 快码行集合
	 */
	List<LookupValue> selectLookupValuesByType(IRequest request,LookupValue lookupValue);
	/**
	 * 公用快码行查询
	 * @author taotao.xu@hand-china.com
	 * @param request 请求
	 * @param lookupValue 快码行DTO
	 * @return List<LookupValue> 快码行集合
	 */
	List<LookupValue> queryLookupValues(IRequest request,LookupValue lookupValue);
	/**
	 * 行唯一性验证
	 * @param lookupCode
	 * @return 个数
	 */
	int SelectValueOnlyValidate(LookupValue lookupValue);
}