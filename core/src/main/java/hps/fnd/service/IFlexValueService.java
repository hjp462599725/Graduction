package hps.fnd.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;

import hps.fnd.dto.FlexValue;


/**
 * @author 作者:fuchun.hu@hand-china.com
 * @version 创建时间：2016年7月2日 下午12:41:16 类说明
 */
public interface IFlexValueService extends IBaseService<FlexValue>,ProxySelf<IFlexValueService>{

	/**
	 * 根据条件查询出有效的独立值集行表  不 支持分页
	 * selectEnableFlexValuesByExm
	 * @param iRequest
	 * @param flexValue
	 * @return
	 * @date 2016年9月7日 下午1:47:52
	 * @author dezhi.shen@hand-china.com
	 * @returnType List<FlexValue>
	 */
	public List<FlexValue> selectEnableFlexValuesByExm(IRequest iRequest,FlexValue flexValue);
	/**
	 * 根据条件查询出有效的独立值集行表 支持分页
	 * selectEnableFlexValuesByExm
	 * @param iRequest
	 * @param flexValue
	 * @param page
	 * 	页码
	 * @param pageSize
	 * 页面Size
	 * @return
	 * @date 2016年9月7日 下午1:49:23
	 * @author dezhi.shen@hand-china.com
	 * @returnType List<FlexValue>
	 */
	public List<FlexValue> selectEnableFlexValuesByExm(IRequest iRequest,FlexValue flexValue,int page,int pageSize);

	
	/**
	 * 根据条件查询出所有的独立值集(包括失效的和不启用的) 不支持分页
	 * selectFlexValuesByExm
	 * @param iRequest
	 * @param flexValue
	 * @return
	 * @date 2016年9月7日 下午1:47:55
	 * @author dezhi.shen@hand-china.com
	 * @returnType List<FlexValue>
	 */
	public List<FlexValue> selectFlexValuesByExm(IRequest iRequest,FlexValue flexValue);
	
	/**
	 * 根据条件查询出所有的独立值集(包括失效的和不启用的) 支持分页
	 * selectFlexValuesByExm
	 * @param iRequest
	 * @param flexValue
	 * @param page
	 * @param pageSize
	 * @return
	 * @date 2016年9月7日 下午1:50:23
	 * @author dezhi.shen@hand-china.com
	 * @returnType List<FlexValue>
	 */
	public List<FlexValue> selectFlexValuesByExm(IRequest iRequest,FlexValue flexValue,int page ,int pageSize);

}
