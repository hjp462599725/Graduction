package hps.fnd.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;

import hps.fnd.dto.PlugVersions;
/**
 * 
 * @name PlugVersionsMapper
 * @description 插件行版本Mapper
 * @author jieyang03@hand-china.com 2016年9月2日09:49:59
 * @version 1.0
 */
public interface PlugVersionsMapper extends Mapper<PlugVersions> {
	/**
	 * 查询下载页面行信息
	 * @param plugVersions 实体
	 * @return 实体集合
	 */
	List<PlugVersions> selectFlugVersions(PlugVersions plugVersions);
	/**
	 * 查询上传页面信息
	 * @param plugVersions 实体
	 * @return 实体集合
	 */
	List<PlugVersions> selectUploadFlugVersions(PlugVersions plugVersions);
	/**
	 * 版本唯一性验证
	 * @param plugVersions 实体
	 * @return 个数
	 */
	int SelectVersionsOnlyValidate(PlugVersions plugVersions);
}
