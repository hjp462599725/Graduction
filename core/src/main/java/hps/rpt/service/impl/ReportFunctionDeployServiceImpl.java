package hps.rpt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand.hap.cache.impl.RoleFunctionCache;
import com.hand.hap.cache.impl.RoleResourceCache;
import com.hand.hap.core.IRequest;
import com.hand.hap.function.dto.Function;
import com.hand.hap.function.dto.FunctionResource;
import com.hand.hap.function.dto.Resource;
import com.hand.hap.function.dto.RoleFunction;
import com.hand.hap.function.mapper.FunctionResourceMapper;
import com.hand.hap.function.mapper.RoleFunctionMapper;

import hps.rpt.mapper.ReportFunctionDeployMapper;
import hps.rpt.service.IReportFunctionDeployService;

@Service
public class ReportFunctionDeployServiceImpl implements IReportFunctionDeployService {
	@Autowired
	private ReportFunctionDeployMapper reportFunctionDeployMapper;
	@Autowired
	private FunctionResourceMapper functionResourceMapper;
	@Autowired
	private RoleFunctionMapper roleFunctionMapper;
	@Autowired
	private RoleFunctionCache roleFunctionCache;
	@Autowired
	private RoleResourceCache roleResourceCache;

	@Override
	public void insertResourceFunctions(IRequest requestCtx, FunctionResource functionResource) {
		functionResourceMapper.insertSelective(functionResource);

	}

	@Override
	public Resource selectResourceKey(IRequest requestCtx, Resource resource) {
		return reportFunctionDeployMapper.selectResourceKey(resource);
	}

	@Override
	public Function selectFunctionKey(IRequest requestCtx, Function function) {
		return reportFunctionDeployMapper.selectFunctionKey(function);
	}

	@Override
	public void insertRoleFunction(IRequest requestCtx, List<RoleFunction> roleFunctions) {
		if (roleFunctions != null && !roleFunctions.isEmpty()) {
			for (RoleFunction rolefunction : roleFunctions) {
				roleFunctionMapper.insertSelective(rolefunction);
			}
			RoleFunction rf = new RoleFunction();
			Long roleId = roleFunctions.get(0).getRoleId();
			rf.setRoleId(roleId);
			List<RoleFunction> functions = new ArrayList<RoleFunction>();
			functions = roleFunctionMapper.select(rf);
			int i = 0;
			Long[] ids = new Long[functions.size()];
			for (RoleFunction rfn : functions) {
				ids[i++] = rfn.getFunctionId();
			}
			clearRoleFunctionCacheByRoleId(roleId);
			roleFunctionCache.setValue(roleId.toString(), ids);
			reloadRoleResourceCache(roleId);
		}
	}

	public void clearRoleFunctionCacheByRoleId(Long roleId) {
		roleFunctionCache.remove(roleId.toString());
		roleResourceCache.remove(roleId.toString());
	}

	public void reloadRoleResourceCache(Long roleId) {
		roleResourceCache.remove(roleId.toString());
		roleResourceCache.loadRoleResource(roleId);

	}

	@Override
	public List<Resource> selectResourceAll() {
		return reportFunctionDeployMapper.selectResourceAll();
	}

	@Override
	public List<Function> selectFunctionAll() {
		return reportFunctionDeployMapper.selectFunctionAll();
	}

}
