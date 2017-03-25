package hps.fnd.controllers;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageHelper;
import com.hand.hap.account.dto.Role;
import com.hand.hap.account.service.IRoleService;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hps.fnd.dto.AuthHeader;
import hps.fnd.dto.AuthLine;
import hps.fnd.dto.HpsResource;
import hps.fnd.dto.HpsResourceItem;
import hps.fnd.service.AuthCache;
import hps.fnd.service.IAuthHeaderService;
import hps.fnd.service.IAuthLineService;
import hps.fnd.service.IHpsResourceItemService;
import hps.fnd.service.IHpsResourceService;

/**
 * @name AuthController
 * @description 页面权限控制
 * @author hongan.dong@hand-china.com 2016年9月14日下午2:09:12
 * @version 1.0
 */
@Controller
public class AuthController extends BaseController {

	// 重复数据标识
	public static int FLAG = 0;
	@Autowired
	private IAuthHeaderService authHeaderService;
	@Autowired
	private IAuthLineService authLineService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IHpsResourceService resourceService;
	@Autowired
	private IHpsResourceItemService resourceItemService;
	@Autowired
	private AuthCache authCache;

	/**
	 * @description 角色权限头表查询
	 * @param authHeaders
	 *            DTO
	 * @param request
	 *            请求
	 * @param page
	 *            第几页
	 * @param pagesize
	 *            每页数据多少
	 * @return
	 */
	@RequestMapping(value = "/fnd/authHeader/query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData selectAuthHeaders(AuthHeader authHeaders, HttpServletRequest request,
			@RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(authHeaderService.selectAuthHeaders(authHeaders, requestContext, page, pagesize));
	}

	/**
	 * @description 查询角色
	 * @param role
	 *            DTO
	 * @param request
	 *            请求
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/authRole/query", method = RequestMethod.POST)
	public ResponseData selectRoles(Role role, HttpServletRequest request) throws BaseException {
		List<Role> roleData = roleService.selectAll();
		return new ResponseData(roleData);
	}

	/**
	 * @description 查询资源
	 * @param hpsResource
	 * @param request
	 *            请求
	 * @param page
	 *            第几页
	 * @param pagesize
	 *            每页数据多少
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/authResource/query", method = RequestMethod.POST)
	public ResponseData selectResources(HpsResource hpsResource, HttpServletRequest request,
			@RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws BaseException {
		IRequest requestContext = createRequestContext(request);
		List<HpsResource> resourceData = resourceService.selectResource(requestContext, hpsResource, page, pagesize);
		return new ResponseData(resourceData);
	}

	/**
	 * @description 保存角色权限头表
	 * @param authHeaders
	 *            DTO
	 * @param request
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/fnd/authHeader/submit", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData saveAuthHeaders(@RequestBody List<AuthHeader> authHeaders, HttpServletRequest request,
			BindingResult result) {
		for (int i = 0; i < authHeaders.size(); i++) {
			if (authHeaders.get(i).get__status() != null) {
				if (authHeaders.get(i).get__status().equals("delete")) {
					authHeaders.remove(i);
					i--;
				}
			}
		}
		IRequest iRequest = createRequestContext(request);
		getValidator().validate(authHeaders, result);
		ResponseData rd = new ResponseData(false);
		if (result.hasErrors()) {
			rd.setMessage(getErrorMessage(result, request));
			return rd;
		}
		FLAG = 0;
		List<AuthHeader> authHeaders2 = authHeaderService.insertOrUpdateHeaders(iRequest, authHeaders);
		if (FLAG == 1) {
			Locale locale = RequestContextUtils.getLocale(request);
			String errorMessage = this.getMessageSource().getMessage("hps.fnd.auth.same_data_found", null, locale);
			rd.setMessage(errorMessage);
			return rd;
		}
		return new ResponseData(authHeaders2);
	}

	/**
	 * @description 角色权限行表查询
	 * @param authHeaders
	 * @param request
	 * @param page
	 * @param pagesize
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/authLine/query", method = RequestMethod.POST)
	public ResponseData selectAuthLines(AuthHeader authHeaders, HttpServletRequest request,
			@RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws BaseException {
		IRequest requestContext = createRequestContext(request);
		PageHelper.startPage(page, pagesize);
		List<AuthLine> authLinesData = authLineService.selectAuthLines(requestContext, authHeaders);
		return new ResponseData(authLinesData);
	}

	/**
	 * @description 页面组件查询
	 * @param authLine
	 * @param request
	 * @return 权限明细中组件信息
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/authItems/query", method = RequestMethod.POST)
	public ResponseData selectItems(AuthLine authLine, HttpServletRequest request,
			@RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws BaseException {
		IRequest requestContext = createRequestContext(request);
		PageHelper.startPage(page, pagesize);
		List<HpsResourceItem> items = resourceItemService.selectItems(requestContext, authLine);
		return new ResponseData(items);
	}

	/**
	 * @description 提交角色权限行表
	 * @param authLines
	 * @param request
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/fnd/authLine/submit", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData saveAuthLines(@RequestBody List<AuthLine> authLines, HttpServletRequest request,
			BindingResult result) {
		for (int i = 0; i < authLines.size(); i++) {
			if (authLines.get(i).get__status() != null) {
				if (authLines.get(i).get__status().equals("delete")) {
					authLines.remove(i);
					i--;
				}
			}
		}
		IRequest iRequest = createRequestContext(request);
		getValidator().validate(authLines, result);
		ResponseData rd = new ResponseData(false);
		if (result.hasErrors()) {
			rd.setMessage(getErrorMessage(result, request));
			return rd;
		}
		FLAG = 0;
		List<AuthLine> authLines2 = authLineService.insertOrUpdateLines(iRequest, authLines);
		authCache.reLoad(authLines.get(0).getAuthHeaderId());
		if (FLAG == 1) {
			Locale locale = RequestContextUtils.getLocale(request);
			String errorMessage = this.getMessageSource().getMessage("hps.fnd.auth.same_data_found", null, locale);
			rd.setMessage(errorMessage);
			return rd;
		}
		return new ResponseData(authLines2);
	}

	/**
	 * @description 删除行
	 * @param authLines
	 * @param result
	 * @param request
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "/fnd/authLine/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData linesDelete(@RequestBody List<AuthLine> authLines, BindingResult result,
			HttpServletRequest request) throws BaseException {
		if (authLines.size() == 0) {
			return new ResponseData();
		} else {
			for (AuthLine a : authLines) {
				authLineService.deleteAuthLines(a);
			}
		}
		StringBuilder key = new StringBuilder();
		key.append(authLines.get(0).getRoleId());
		key.append(".");
		key.append(authLines.get(0).getResourceId());
		authCache.remove(key.toString(), authLines.get(0).getAuthHeaderId());
		return new ResponseData();
	}

	/**
	 * @description redis权限查询
	 * @param authLines
	 * @param resourceUrl
	 * @param request
	 * @return String
	 * @throws JsonProcessingException
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/authList/query", method = RequestMethod.POST, produces = "application/javascript;charset=utf8")
	public String selectAuthList(AuthLine authLines, String resourceUrl, HttpServletRequest request)
			throws JsonProcessingException {
		// IRequest requestContext = createRequestContext(request);
		// 获取resourceId
		Long resourceId = authCache.selectResourceId(resourceUrl);
		if (resourceId == null) {
			return "[1]";
		}
		// 验证页面权限启用标识
		String resourceEnableFlag = authCache.enableFlagResources(resourceId);
		if (resourceEnableFlag == null || "N".equals(resourceEnableFlag)) {
			return "[1]";
		}
		// 验证不同角色的页面权限启用标识
		String authEnableFlag = authCache.enableFlagAuthHeaders(String.valueOf(authLines.getRoleId()), resourceId);
		if ("N".equals(authEnableFlag)) {
			return "[1]";
		}
		if (authEnableFlag == null) {
			return "[]";
		}
		authLines.setResourceId(resourceId);
		String jsonString = authCache.getAuthValue(authLines);
		if (jsonString == null) {
			return "[]";
		}
		return jsonString;
	}

}
