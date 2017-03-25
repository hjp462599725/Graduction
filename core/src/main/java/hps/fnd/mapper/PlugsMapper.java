package hps.fnd.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;

import hps.fnd.dto.PlugCount;
import hps.fnd.dto.Plugs;
/**
 * 
 * @name PlugsMapper
 * @description 插件头Mapper
 * @author jieyang03@hand-china.com 2016年9月2日09:49:59
 * @version 1.0
 */
public interface PlugsMapper extends Mapper<Plugs> {
	/**
	 * 查询插件头信息
	 * @param request 请求
	 * @param plug 实体
	 * @param page 分页
	 * @param pageSize 分页
	 * @return 实体集合
	 */
	List<Plugs> queryUploadPlugs(Plugs plug);
	/**
	 * 查询插件头信息
	 * @param request 请求
	 * @param plug 实体
	 * @param page 分页
	 * @param pageSize 分页
	 * @return 实体集合
	 */
	List<Plugs> queryDownloadPlugs(Plugs plug);
	/**
	 * 查询出文件ID
	 * @param sourceKey 文件关联外键
	 * @return ID
	 */
	Long selectFileId(Long sourceKey);
	
	/**
	 * 
	 * @param plugCount
	 * @return 插件统计实例集合
	 * @author xianzhi.chen@hand-china.com
	 */
	List<PlugCount> queryPlugCount(PlugCount plugCount);
}
