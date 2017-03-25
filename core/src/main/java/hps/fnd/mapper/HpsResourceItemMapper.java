package hps.fnd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;

import hps.fnd.dto.AuthLine;
import hps.fnd.dto.HpsResourceItem;
/**
 * 
 * @name HpsResourceItemMapper
 * @description 资源页面控件Mapper
 * @author jie.yang03@hand-china.com  2016年7月20日14:46:42
 * @version 1.0
 */
public interface HpsResourceItemMapper extends Mapper<HpsResourceItem> {
	/**
	 * 查询资源对应行表
	 * @param resourceItem 资源页面控件对象
	 * @return List<HpsResourceItem> 资源页面控件对象集合
	 */
	List<HpsResourceItem> selectHpsResourceItem(HpsResourceItem resourceItem);
	/**
	 * 查询某资源下组件的启用维护标识
	 * @author hongan.dong@hand-china.com  2016年8月03日 16:33:44
	 * @param resourceId 资源页面ID
	 * @param itemCode 控件代码
	 * @return HpsResourceItem 资源页面控件对象
	 */
	HpsResourceItem selectEnableFlag(@Param("resourceId") Long resourceId, @Param("itemCode") String itemCode, @Param("itemRegion") String itemRegion);
	/**
	 * @description
	 * @param authLine
	 * @return
	 */
	List<HpsResourceItem> selectItems(AuthLine authLine);
}
