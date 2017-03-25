package hps.fnd.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;

import hps.fnd.dto.LookupType;

/**
 * 
 * @name ILookupTypeService
 * @description 快码头CURD
 * @author jieyang03@hand-china.com  2016年8月30日15:34:22
 * @version 1.0
 */
public interface ILookupTypeService extends IBaseService<LookupType>,ProxySelf<ILookupTypeService>{
	/**
	 * 快码维护的批量操作
	 * @param request 请求
	 * @param lookupTypes 快码头集合
	 * @return List<LookupType> 快码头集合
	 */
	List<LookupType> myBatchUpdate(IRequest request, @StdWho List<LookupType> lookupTypes);
	
	/**
	 * 根据Type查询快码
	 * @param request 请求
	 * @param lookupType 快码头DTO
	 * @param page 页
	 * @param pageSize 页条数
	 * @return List<LookupType> 快码头集合
	 */
	List<LookupType> selectLookupTypes(IRequest request, LookupType lookupType, int page, int pageSize);
	/**
	 * 唯一性验证
	 * @param lookupType
	 * @return 个数
	 */
	int SelectOnlyValidate(String lookupType);
	
}
