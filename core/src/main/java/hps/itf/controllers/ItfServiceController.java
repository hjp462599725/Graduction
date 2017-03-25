package hps.itf.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.TokenException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hps.itf.dto.ItfServiceHeader;
import hps.itf.dto.ItfServiceLine;
import hps.itf.service.IItfServiceHeaderService;
import hps.itf.service.IItfServiceLineService;

/**
 * 
 * @name ItfServiceController
 * @description 接口管理controller
 * @author jianping.huo@hand-china.com  2016年8月19日下午2:52:44
 * @version 1.0
 */
@Controller
public class ItfServiceController extends BaseController {
	@Autowired
	private IItfServiceHeaderService headerService;
	@Autowired
	private IItfServiceLineService lineService;
	
	/**
	 * 系统查询
	 * @param header 接口头DTO
	 * @param page 页
	 * @param pagesize 页条数
	 * @param request 请求
	 * @return ResponseData
	 */
	@RequestMapping(value = "/itf/header/query", method = RequestMethod.POST)
	public ResponseData headerQuery(ItfServiceHeader header,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request){
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(headerService.select(requestContext, header, page, pagesize));
	}
	/**
	 * 根据id查询系统
	 * @param header 接口头DTO
	 * @param page 页
	 * @param pagesize 页条数
	 * @param request 请求
	 * @return ResponseData
	 */
	@RequestMapping(value = "/itf/header/queryById", method = RequestMethod.POST)
	public ResponseData serachHeader(@RequestBody ItfServiceHeader header,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request){
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(headerService.select(requestContext, header, page, pagesize));
	}
	/**
	 * 批量操作系统信息
	 * @param headers 接口头集合
	 * @param result 绑定的结果
	 * @param request 请求
	 * @return ResponseData
	 * @throws TokenException 
	 */
	@RequestMapping(value = "/itf/header/submit", method = RequestMethod.POST)
	public ResponseData myBatchUpdate(@RequestBody List<ItfServiceHeader> headers, BindingResult result, HttpServletRequest request) 
			throws TokenException{
		checkToken(request, headers);
		getValidator().validate(headers, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(headerService.myBatchUpdate(requestContext, headers));
	}
	/**
	 * 停止系统使用
	 * @param headers 接口头集合
	 * @param request 请求
	 * @return ResponseData
	 * @throws TokenException 
	 */
	@RequestMapping(value = "/itf/header/pause", method = RequestMethod.POST)
	public ResponseData pause(@RequestBody List<ItfServiceHeader> headers, HttpServletRequest request) 
			throws TokenException{
		checkToken(request, headers);
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(headerService.pasue(requestContext, headers));
	}
	/**
	 * 恢复系统使用
	 * @param headers 接口头集合
	 * @param request 请求
	 * @return ResponseData
	 * @throws TokenException 
	 */
	@RequestMapping(value = "/itf/header/resume", method = RequestMethod.POST)
	public ResponseData resume(@RequestBody List<ItfServiceHeader> headers, HttpServletRequest request) 
			throws TokenException{
		checkToken(request, headers);
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(headerService.resume(requestContext, headers));
	}
	/**
	 * 在服务配置页面显示目录
	 * @param request 请求
	 * @param itf 接口头DTO
	 * @return
	 */
	@RequestMapping("/itf/interface_line.html")
	public ModelAndView getItfHeaderName(HttpServletRequest request, @ModelAttribute ItfServiceHeader itf) {
		ModelAndView view = new ModelAndView(getViewPath() + "/itf/interface_line");
		IRequest requestContext = createRequestContext(request);
	    ItfServiceHeader headers = headerService.selectByPrimaryKey(requestContext, itf);
	    view.addObject("breadCrumb", headers);
	    return view;
	}
	/**
	 * 查询接口信息
	 * @param line 接口行DTO
	 * @param page 页
	 * @param pagesize 页条数
	 * @param request 请求
	 * @return ResponseData
	 */
	@RequestMapping(value = "/itf/line/query", method = RequestMethod.POST)
	public ResponseData lineQuery(ItfServiceLine line,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request){
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(lineService.select(requestContext, line, page, pagesize));
	}
	/**
	 * 批量操作接口信息
	 * @param lines 接口行集合
	 * @param result 绑定结果
	 * @param request 请求
	 * @return ResponseData
	 * @throws TokenException 
	 */
	@RequestMapping(value = "/itf/line/submit", method = RequestMethod.POST)
	public ResponseData lineBatchUpdate(@RequestBody List<ItfServiceLine> lines, BindingResult result, HttpServletRequest request) 
			throws TokenException{
		checkToken(request, lines);
		getValidator().validate(lines, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
		return new ResponseData(lineService.myBatchUpdate(requestContext, lines));
	}
	
}