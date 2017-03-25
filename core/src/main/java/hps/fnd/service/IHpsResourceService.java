package hps.fnd.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;

import hps.fnd.dto.HpsResource;

/**
 * @name IHpsResourceService
 * @description 页面资源Service
 * @author jie.yang03@hand-china.com  2016年8月19日下午1:12:08
 * @version 1.0
 */
public interface IHpsResourceService extends IBaseService<HpsResource>,ProxySelf<IHpsResourceService>{
	/**
	 * 查询所有资源文件
	 * @param request 请求
	 * @param resource 页面资源对象
	 * @param page 分页
	 * @param pageSize 分页
	 * @return List<HpsResource> 页面资源对象集合
	 */
	List<HpsResource> selectResource(IRequest request,HpsResource resource , int page, int pageSize);
	/**
	 * 批量更新
	 * @param request 请求
	 * @param hpsResources 页面资源对象集合
	 * @return List<HpsResource> 页面资源对象集合
	 */
	List<HpsResource> BatchUpdateHpsResource(IRequest request, @StdWho List<HpsResource> hpsResources);
	
}
