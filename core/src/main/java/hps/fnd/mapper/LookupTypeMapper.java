package hps.fnd.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;

import hps.fnd.dto.LookupType;

/**
 * 
 * @name LookupTypeMapper
 * @description 快码头数据的CURD
 * @author jianping.huo@hand-china.com  2016年8月19日上午11:25:48
 * @version 1.0
 */
public interface LookupTypeMapper extends Mapper<LookupType> {
	/**
	 * 查询快码头数据
	 * @param lookupType 快码头DTO
	 * @return List<LookupType> 快码头集合
	 */
	List<LookupType> selectLookupTypes(LookupType lookupType);
	
	/**
	 * 唯一性验证
	 * @param lookupType
	 * @return 个数
	 */
	int SelectOnlyValidate(String lookupType);
	
}
