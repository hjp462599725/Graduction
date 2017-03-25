package hps.org.controllers;


import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hps.org.dto.Employee;
import hps.org.service.IEmployeeService;


/**
 * @name EmployeeController
 * @description 
 * @author xing.gong@hand-china.com 2016年9月8日上午10:11:15
 * @version 1.0
 */
@Controller
public class EmployeeController extends BaseController {
	
	
	@Autowired
	private IEmployeeService employeeService;
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * 查询所有员工信息
	 * @param employees
	 * @param page
	 * @param pagesize
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/org/employees/query", method = RequestMethod.POST)
    @ResponseBody    
    public ResponseData selectEmployees(@ModelAttribute Employee employees,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize,HttpServletRequest request) {		
		
		IRequest requestContext = createRequestContext(request);		
		return new ResponseData(employeeService.selectEmployees(employees, page, pagesize, requestContext));
    }
	

	
	/**
	 * 批量更新员工信息
	 * @param empls
	 * @param result
	 * @param request
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "/org/employees/submit", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData submitEmpl(@RequestBody List<Employee> empls, BindingResult result, HttpServletRequest request)
	
			throws BaseException {
		/**
		 * description:对清除数据进行排查处理
		 * author:taotao
		 */
		
		
		for(int i=0;i<empls.size();i++){
			if(empls.get(i).get__status() != null){
				if(empls.get(i).get__status().equals("delete")){
					empls.remove(i);
					i--;
				}
			}
		}
		
		  getValidator().validate(empls, result);
		  
		  
		if (result.hasErrors()) {
			ResponseData rd = new ResponseData(false);
			rd.setMessage(getErrorMessage(result, request));
			return rd;
		} 
		IRequest requestCtx = createRequestContext(request);	
			for(Employee e:empls){		
				long a = employeeService.selectEmployeesUnique(e);
				long b = employeeService.selectEmployeesUniqueCertificate(e);
				if(a>0){
					//抛出自定义异常
					Locale locale = RequestContextUtils.getLocale(request);
					ResponseData rd = new ResponseData(false);
					//获取翻译后的消息
					rd.setMessage(messageSource.getMessage("hps.org.employee.employeeNumber.error", null, locale));
					return rd;

				}

				if(b>0){
					//抛出自定义异常
					Locale locale = RequestContextUtils.getLocale(request);
					ResponseData rd = new ResponseData(false);
					//获取翻译后的消息
					rd.setMessage(messageSource.getMessage("hps.org.employee.certificate.error",null, locale));
					return rd;

				}
				else{
					employeeService.batchSubmit(requestCtx,e);
				}


			}
			return new ResponseData(empls);
	}
		
	
	/**
	 * 批量删除员工信息
	 * @param empls
	 * @param result
	 * @param request
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "/org/employees/remove", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData submitRemove(@RequestBody List<Employee> empls, BindingResult result, HttpServletRequest request)
			throws BaseException {
		
		if (result.hasErrors()) {
			ResponseData rd = new ResponseData(false);
			rd.setMessage(getErrorMessage(result, request));
			return rd;
		} 
		for(Employee e:empls){
			employeeService.removeEmpl(e);
		}
		return new ResponseData(empls);
	}
	
	@RequestMapping(value = "/org/employees/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteEmpl(HttpServletRequest request, @RequestBody List<Employee> empls,BindingResult result) 
		throws BaseException {
			
		if (result.hasErrors()) {
			ResponseData rd = new ResponseData(false);
			rd.setMessage(getErrorMessage(result, request));
			return rd;
		} 
		for(Employee e:empls){
			employeeService.deleteEmpl(e);
		}
		return new ResponseData(empls);
	}
	 

}
