package hps.itf.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;

import hps.itf.dto.ItfServiceHeader;

/**
 * 
 * @name IItfServiceHeaderService
 * @description 接口头表的CRUD
 * @author jianping.huo@hand-china.com  2016年8月22日下午1:20:00
 * @version 1.0
 */
public interface IItfServiceHeaderService extends IBaseService<ItfServiceHeader>, ProxySelf<IItfServiceHeaderService> {
	/**
	 * 批量操作接口头表
	 * @param request 请求
	 * @param headers 接口头集合
	 * @return List<ItfServiceHeader> 接口头集合
	 */
	List<ItfServiceHeader> myBatchUpdate(IRequest request,@StdWho List<ItfServiceHeader> headers);
	/**
	 * 停止系统使用
	 * @param request 请求
	 * @param headers 接口头集合
	 * @return List<ItfServiceHeader> 接口头集合
	 */
	List<ItfServiceHeader> pasue(IRequest request,@StdWho List<ItfServiceHeader> headers);
	/**
	 * 恢复系统使用
	 * @param request 请求
	 * @param headers 接口头集合
	 * @return List<ItfServiceHeader> 接口头集合
	 */
	List<ItfServiceHeader> resume(IRequest request,@StdWho List<ItfServiceHeader> headers);
}