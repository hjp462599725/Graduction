package hps.fnd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.mybatis.common.Mapper;

import hps.fnd.dto.ValidateTable;

/**
 * @name ValidateTableMapper
 * @description 表验证值集行Mapper
 * @author tianle.liu@hand-china.com 2016年8月22日下午4:01:58
 * @version 1.0
 */
public interface ValidateTableMapper extends Mapper<ValidateTable> {

	/**
	 * 查询某一条件下的columnFlag数量
	 * 
	 * @param conditions
	 *            查询的条件，不包含Where 关键字
	 * @return 当前条件下的columnFlag总数
	 * @date 2016年8月30日 上午10:04:55
	 * @author dezhi.shen@hand-china.com
	 * @returnType Integer
	 */
	Integer columnFlagCount(@Param(value = "conditions") String conditions);

	/**
	 * 根据主键更新字段
	 * 
	 * @param table
	 *            待更新的 ValidateTable 对象
	 * @return 影响的行数
	 * @date 2016年8月31日 上午9:32:06
	 * @author dezhi.shen@hand-china.com
	 * @returnType Integer
	 */
	Integer updateValidateTableById(@StdWho ValidateTable table);

	/**
	 * selectByIdAndLoacle
	 * 
	 * @param condition
	 *            查询的条件
	 * @param locale
	 *            zh_CN,en_GB
	 * @return 符合条件的表值集
	 * @date 2016年9月12日 下午2:11:53
	 * @author dezhi.shen@hand-china.com
	 * @returnType List<ValidateTable>
	 */
	List<ValidateTable> selectByIdAndLoacle(@Param(value = "validateTable") ValidateTable condition,
			@Param(value = "locale") String locale);
}
