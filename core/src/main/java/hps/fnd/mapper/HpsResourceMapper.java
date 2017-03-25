package hps.fnd.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import hps.fnd.dto.HpsResource;
/**
 * 
 * @name HpsResourceMapper
 * @description 页面资源Mapper
 * @author jie.yang03@hand-china.com  2016年7月20日15:31:07
 * @version 1.0
 */
public interface HpsResourceMapper extends Mapper<HpsResource> {
	/**
	 * 查询所有资源文件
	 * @param resource 资源页面对象
	 * @return List<HpsResource> 资源页面对象集合
	 */
	List<HpsResource> selectResource(HpsResource resource);
	/**
	 * 查询某资源启用维护标识
	 * @author hongan.dong@hand-china.com  2016年8月03日 16:03:44
	 * @param hpsResource 资源页面对象
	 * @return HpsResource 资源页面对象集合
	 */
	HpsResource selectEnabledFlag(HpsResource hpsResource);
}
