package hps.fnd.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;

import hps.fnd.dto.LookupValue;

/**
 * 
 * @name LookupValueMapper
 * @description 快码行数据CURD
 * @author hand  2016年8月19日下午1:07:16
 * @version 1.0
 */
public interface LookupValueMapper extends Mapper<LookupValue> {
	/**
	 * 查询快码行数据
	 * @param lookupValue 快码行DTO
	 * @return List<LookupValue> 快码行集合
	 */
	List<LookupValue> selectLookupValues(LookupValue lookupValue);
	/**
	 * 根据Type查询快码行数据
	 * @param lookupValue 快码行DTO
	 * @return List<LookupValue> 快码行集合
	 */
	List<LookupValue> selectLookupValuesByType(LookupValue lookupValue);
	/**
	 * 公用查询快码行数据
	 * @param lookupValue 快码行DTO
	 * @return List<LookupValue> 快码行集合
	 */
	List<LookupValue> queryLookupValues(LookupValue lookupValue);
	/**
	 * 行唯一性验证
	 * @param lookupCode
	 * @return 个数
	 */
	int SelectValueOnlyValidate(LookupValue lookupValue);
	
}