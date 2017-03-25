package hps.fnd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;

import hps.fnd.dto.FlexValue;

/**
 * @name FlexValueMapper
 * @description 独立值集的行表Mapper
 * @author dezhi.shen@hand-china.com 2016年9月7日下午1:45:25
 * @version 1.0
 */
public interface FlexValueMapper extends Mapper<FlexValue> {
	/**
	 * selectCount 根据传入的条件，查询行数
	 * @param conditions
	 * @return
	 * @date 2016年10月10日 下午5:58:41
	 * @author dezhi.shen@hand-china.com
	 * @returnType Integer
	 */
	Integer selectCountF(@Param(value = "conditions") String conditions);

	/**
	 * 根据条件查询出有效的独立值集行表 selectEnableFlexValuesByExm
	 * 
	 * @param flexValue
	 * @return
	 * @date 2016年9月7日 下午1:46:01
	 * @author dezhi.shen@hand-china.com
	 * @returnType List<FlexValue>
	 */
	public List<FlexValue> selectEnableFlexValuesByExm(FlexValue flexValue);

	/**
	 * 根据条件查询出所有的独立值集(包括失效的和不启用的)
	 * selectFlexValuesByExm
	 * 
	 * @param flexValue
	 * @return
	 * @date 2016年9月7日 下午1:46:33
	 * @author dezhi.shen@hand-china.com
	 * @returnType List<FlexValue>
	 */
	public List<FlexValue> selectFlexValuesByExm(FlexValue flexValue);

}
