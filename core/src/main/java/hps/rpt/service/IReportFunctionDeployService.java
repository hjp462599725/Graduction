package hps.rpt.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.function.dto.Function;
import com.hand.hap.function.dto.FunctionResource;
import com.hand.hap.function.dto.Resource;
import com.hand.hap.function.dto.RoleFunction;
/**
 * @name IReportFunctionDeployService
 * @description 报表功能部署Service
 * @author chengye.hu@hand-china.com	2016年8月25日下午4:23:08
 * @version 1.0
 */
public interface IReportFunctionDeployService {
	/**
	 * 插入资源功能关系表
	 * @param requestCtx
	 * @param functionResource
	 * @return
	 */
	void insertResourceFunctions(IRequest requestCtx, FunctionResource functionResource);
	/**
	 * 查找资源
	 * @param requestCtx
	 * @param resource
	 * @return
	 */
	Resource selectResourceKey(IRequest requestCtx, Resource resource);
	/**
	 * 查找功能
	 * @param requestCtx
	 * @param function
	 * @return
	 */
	Function selectFunctionKey(IRequest requestCtx, Function function);
	/**
	 * 插入角色功能关系表
	 * @param requestCtx
	 * @param roleFunctions
	 * @return
	 */
	void insertRoleFunction(IRequest requestCtx, List<RoleFunction> roleFunctions);
	/**
	 * 查找所有资源
	 * @param requestCtx
	 * @return
	 */
	List<Resource> selectResourceAll();
	/**
	 * 查找所有功能
	 * @param requestCtx
	 * @return
	 */
	List<Function> selectFunctionAll();
}
