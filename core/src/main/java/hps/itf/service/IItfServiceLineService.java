package hps.itf.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;

import hps.itf.dto.ItfServiceLine;

/**
 * 
 * @name IItfServiceLineService
 * @description 接口行表的CRUD
 * @author jianping.huo@hand-china.com  2016年8月22日下午1:26:34
 * @version 1.0
 */
public interface IItfServiceLineService extends IBaseService<ItfServiceLine>, ProxySelf<IItfServiceLineService> {
	/**
	 * 批量操作接口行DTO
	 * @param request 请求
	 * @param lines 接口行集合
	 * @return List<ItfServiceLine> 接口行集合
	 */
	List<ItfServiceLine> myBatchUpdate(IRequest request, List<ItfServiceLine> lines);
}