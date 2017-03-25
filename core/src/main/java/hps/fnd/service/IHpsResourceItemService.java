package hps.fnd.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;

import com.hand.hap.system.service.IBaseService;

import hps.fnd.dto.AuthLine;
import hps.fnd.dto.HpsResourceItem;
/**
 * @name IHpsResourceItemService
 * @description 页面资源控件Service
 * @author jie.yang03@hand-china.com  2016年8月19日下午1:14:58
 * @version 1.0
 */
public interface IHpsResourceItemService extends IBaseService<HpsResourceItem>, ProxySelf<IHpsResourceItemService> {
	/**
	 * 查询资源对应行表
	 * @param request 请求
	 * @param resourceItem 资源页面控件对象
	 * @param page 分页
	 * @param pageSize 分页
	 * @return List<HpsResourceItem> 资源页面控件对象集合
	 */
	List<HpsResourceItem> selectHpsResourceItem(IRequest request,HpsResourceItem resourceItem , int page, int pageSize);
	/**
	 * @description 页面组件查询
	 * @param requestContext
	 * @param authHeaders
	 * @return List<HpsResourceItem>
	 * @author hongan.dong@hand-china.com 2016年8月30日下午14:38:35
	 */
	List<HpsResourceItem> selectItems(IRequest requestContext, AuthLine authLine);
}
