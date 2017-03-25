package hps.org.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hps.org.dto.HpsUser;
import hps.org.service.IHpsUserService;
/**
 * 
 * @name HpsUserController
 * @description 用户管理的controller层
 * @author jieyang03@hand-china.com  2016年10月9日15:12:00
 * @version 1.0
 */

@Controller
public class HpsUserController extends BaseController{
	
	@Autowired
	private IHpsUserService hpsUserService;
	
	/**
	 * 查询用户
	 * @param hpsUser 实体类
	 * @param page 分页
	 * @param pagesize 分页
	 * @param request 请求
	 * @return 实体类集合
	 */
	@ResponseBody
	@RequestMapping(value = "/org/hpsUser/query", method = RequestMethod.POST)
	public ResponseData selectUser(HpsUser hpsUser,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request){
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(hpsUserService.selectHpsUser(hpsUser, page, pagesize, requestContext));
	}
	
	@ResponseBody
	@RequestMapping(value = "/org/hpsUser/submit", method = RequestMethod.POST)
	public ResponseData submitHpsUser (@RequestBody List<HpsUser> hpsUsers,BindingResult result, HttpServletRequest request){
		getValidator().validate(hpsUsers, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        for(int i=0;i<hpsUsers.size();i++){
        	hpsUsers.get(i).setAttribute1(hpsUsers.get(i).getEmpId());
        }
        IRequest requestContext = createRequestContext(request);
        hpsUserService.batchUpdate(requestContext, hpsUsers);
		return new ResponseData(hpsUsers);
	}
}
