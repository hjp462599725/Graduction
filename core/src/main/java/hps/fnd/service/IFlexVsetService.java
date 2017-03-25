package hps.fnd.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;

import hps.fnd.dto.FlexVset;
import hps.fnd.util.ValidateTableException;

/**
 * @author 作者:fuchun.hu@hand-china.com
 * @version 创建时间：2016年7月2日 下午4:43:30 类说明
 */
public interface IFlexVsetService extends IBaseService<FlexVset> {

	/**
	 * 根据flexValueSetId 查询 值集头表 selectFlexValueById
	 * 
	 * @param flexValueSetId
	 *            值集头表Id
	 * @return
	 * @date 2016年9月7日 上午11:07:53
	 * @author dezhi.shen@hand-china.com
	 * @returnType List<FlexVset>
	 */
	public List<FlexVset> selectFlexValueById(Long flexValueSetId);

	/**
	 * 查询 符合条件的值集行信息
	 * 
	 * @author jie.yang03@hand-china.com 2016年8月22日14:17:50
	 * @param request
	 *            请求
	 * @param flexValueSet
	 *            值集行条件对象
	 * @param page
	 *            分页
	 * @param pageSize
	 *            分页
	 * @return List<FlexValueSet> 值集行集合
	 */
	public List<FlexVset> selectFlexValueByExm(IRequest iRequest, FlexVset flexVset, int page, int pageSize);

	/**
	 * 保存值集头信息
	 * 
	 * @author jie.yang03@hand-china.com 2016年8月22日14:17:50
	 * @param flexValueSet
	 *            值集头对象
	 */
	public Integer saveFlexVset(@StdWho FlexVset flexValueSet);

	/**
	 * 更新值集头信息
	 * 
	 * @author jie.yang03@hand-china.com 2016年8月22日14:17:50
	 * @param flexValueSet
	 *            值集头对象
	 */
	public Integer updataFlexVset(@StdWho FlexVset flexValueSet);

	/**
	 * 批量保存头行
	 * 
	 * @author jie.yang03@hand-china.com 2016年8月22日14:17:50
	 * @param flexValueSets
	 *            值集集合
	 * @param request
	 *            请求
	 * @return List<FlexValueSet> 值集集合
	 */
	public List<FlexVset> batchUpdateFlexVset(@StdWho List<FlexVset> flexValueSets, IRequest iRequest)
			throws ValidateTableException;

	
	
	/**
	 * 批量更新独立值集
	 * @param iRequest
	 * @param flexVsets
	 * @return
	 * @throws ValidateTableException
	 * @date 2016年10月13日 下午5:25:05
	 * @author dezhi.shen@hand-china.com
	 * @returnType List<FlexVset>
	 */
	public List<FlexVset> batchUpdateF(IRequest iRequest,List<FlexVset> flexVsets) throws ValidateTableException;
	/**
	 * 查询值集头
	 * 
	 * @param flexValueSets
	 *            值集头
	 * @param request
	 *            请求
	 * @return List<FlexValueSet>
	 */
	public List<FlexVset> selectSets(FlexVset flexValueSets, IRequest iRequest);

}
