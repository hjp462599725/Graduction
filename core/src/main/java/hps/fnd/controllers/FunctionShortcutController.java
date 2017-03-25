package hps.fnd.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.hap.core.IRequest;
import com.hand.hap.function.dto.Function;
import com.hand.hap.function.dto.Resource;
import com.hand.hap.function.dto.RoleFunction;
import com.hand.hap.function.mapper.RoleFunctionMapper;
import com.hand.hap.function.service.IFunctionService;
import com.hand.hap.function.service.IResourceService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hps.fnd.service.FunctionShortcutCache;

/**
 * @name FunctionShortcutController
 * @description 首页快捷功能
 * @author hongan.dong@hand-china.com 2016年9月27日下午9:21:59
 * @version 1.0
 */
@Controller
public class FunctionShortcutController extends BaseController {
	@Autowired
	private IFunctionService functionService;
	@Autowired
	private RoleFunctionMapper roleFunctionMapper;
	@Autowired
	private IResourceService resourceService;
	@Autowired
	private FunctionShortcutCache functionShortcutCache;
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * @description 查询当前角色拥有的功能
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/roleFunction/query", method = RequestMethod.POST)
	public ResponseData selectRoleFunctions(HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		RoleFunction roleFunction = new RoleFunction();
		HttpSession session = request.getSession();
		roleFunction.setRoleId(Long.parseLong(session.getAttribute("roleId").toString()));
		List<RoleFunction> roleFunctionData = roleFunctionMapper.selectRoleFunctions(roleFunction);
		List<Function> functionList = new ArrayList<Function>();
		if (roleFunctionData.size() == 0) {
			return null;
		}

		for (int i = 0; i < roleFunctionData.size(); i++) {
			Function function = new Function();
			function.setFunctionId(roleFunctionData.get(i).getFunctionId());
			Function f = functionService.selectByPrimaryKey(requestContext, function);
			functionList.add(f);
		}

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < functionList.size(); i++) {
			Resource res = resourceService.selectResourceById(functionList.get(i).getResourceId());
			Map<String, String> map = new HashMap<String, String>();
			map.put("functionIcon", functionList.get(i).getFunctionIcon());
			map.put("functionCode", functionList.get(i).getFunctionCode());
			map.put("functionName", String.valueOf(functionList.get(i).getFunctionName()));
			map.put("resourceId", String.valueOf(res.getResourceId()));
			map.put("url", res.getUrl());
			list.add(map);
		}
		return new ResponseData(list);
	}

	/**
	 * @description 快捷功能查询及更新
	 * @param flag
	 *            查询和更新标志
	 * @param array
	 *            json字符串
	 * @param request
	 * @return String
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping(value = "/fnd/roleFunction/update", method = RequestMethod.POST, produces = "application/javascript;charset=utf8")
	public String updateRoleFunctions(String flag, String array, HttpServletRequest request)
			throws JsonMappingException, IOException {
		IRequest requestContext = createRequestContext(request);
		HttpSession session = request.getSession();
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, Map.class);
		List<Map<String, String>> list = objectMapper.readValue(array, javaType);
		StringBuffer key = new StringBuffer();
		key.append(session.getAttribute("userId").toString());
		key.append(".");
		key.append(session.getAttribute("roleId").toString());
		key.append(".");
		key.append(requestContext.getLocale());
		String jsonString = "[]";
		if ("[]".equals(array) && flag.isEmpty()) {
			jsonString = functionShortcutCache.getShortcutValue(key.toString());
		} else {
			functionShortcutCache.setValue(key.toString(), list);
			jsonString = functionShortcutCache.getShortcutValue(key.toString());
		}
		return jsonString;
	}
}
