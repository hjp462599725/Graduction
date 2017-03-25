package hps.fnd.mapper;

import java.util.List;

import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.mybatis.common.Mapper;

import hps.fnd.dto.FlexVset;
/**
 * @name FlexVsetMapper
 * @description 值集头表Mapper
 * @author dezhi.shen@hand-china.com	2016年9月7日下午2:45:47
 * @version 1.0
 */
public interface FlexVsetMapper extends Mapper<FlexVset> {

	/**
	 * 通过id查询FlexValue
	 * 
	 * @param flexValueSetId
	 * @return
	 */
	public List<FlexVset> selectFlexValueById(Long flexValueSetId);

	/**
	 * 查询所有符合条件的值集头信息
	 * 
	 * @author jie.yang03@hand-china.com 2016年8月22日14:17:50
	 * @param flexVset
	 *            值集头对象
	 * @return List<FlexVset> 值集行集合
	 */
	public List<FlexVset> selectFlexValueByExm(FlexVset flexVset);

	/**
	 * 通过id 更新独立值集表的可修改的配置信息
	 * 
	 * @param flexVset
	 *            待更新的 flexVset 对象
	 * @return 影响的行数
	 * @date 2016年8月31日 上午9:48:34
	 * @author dezhi.shen@hand-china.com
	 * @returnType Integer
	 */
	Integer updateFlexValueSetValidateById(@StdWho FlexVset flexVset);

}
