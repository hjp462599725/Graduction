package hps.fnd.controllers;

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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.dto.ResponseData;

import hps.fnd.dto.LookupType;
import hps.fnd.dto.LookupValue;
import hps.fnd.service.ILookupTypeService;
import hps.fnd.service.ILookupValueService;

/**
 * 
 * @name LookupController
 * @description 快码维护的controller层
 * @author jieyang03@hand-china.com  2016年8月30日15:34:22
 * @version 1.0
 */
@Controller
public class LookupController extends BaseController {
	@Autowired 
	private ILookupTypeService lookupTypeService;
	@Autowired
	private ILookupValueService lookupValueService;
	@Autowired
    private ObjectMapper objectMapper;

	/**
	 * 快码头的查询
	 * @param lookupType 快码头DTO
	 * @param page 页
	 * @param pagesize 页条数
	 * @param request 请求
	 * @return ResponseData
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/lookupType/query", method = RequestMethod.POST)
	public ResponseData selectLookupTypes(LookupType lookupType, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request){
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(lookupTypeService.selectLookupTypes(requestContext, lookupType, page, pagesize));
	}
	
	/**
	 * 快码头的批量增删改
	 * @param lookupTypes 快码头集合
	 * @param result 绑定的结果
	 * @param request 请求
	 * @return ResponseData
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/lookupType/submit", method = RequestMethod.POST)
	public ResponseData saveLookUpType(@RequestBody List<LookupType> lookupTypes, BindingResult result, HttpServletRequest request) 
			throws BaseException {
		checkToken(request, lookupTypes);
		/**
		 * description:对清除数据进行排查处理
		 * author:taotao
		 */
		for (int i = 0; i < lookupTypes.size(); i++) {
			if (DTOStatus.DELETE.equals(lookupTypes.get(i).get__status())) {
				lookupTypes.remove(i);
				i--;
			} else {
				if(lookupTypes.get(i).getLookupValues() != null){
					List<LookupValue> values = lookupTypes.get(i).getLookupValues();
					for (int j = 0; j < values.size(); j++) {
						if (DTOStatus.DELETE.equals(values.get(j).get__status())) {
							values.remove(j);
							j--;
						}
					}
				}
			}
		}
		getValidator().validate(lookupTypes, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(lookupTypeService.myBatchUpdate(requestContext, lookupTypes));
	}
	
	/**
	 * 快码行的查询
	 * @param lookupValue 快码行DTO
	 * @param request 请求
	 * @return ResponseData
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/lookupValue/query", method = RequestMethod.POST)
	public ResponseData selectLookupValues(LookupValue lookupValue,HttpServletRequest request){
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(lookupValueService.selectLookupValues(requestContext, lookupValue));
	}
	
	/**
	 * 快码行数据根据Type和Tag进行查询
	 * @param lookupValue 快码行DTO
	 * @param request 请求
	 * @return ResponseData
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/lookupValue/query", method = RequestMethod.GET)
	public ResponseData selectLookupValuesByTypeandTag(LookupValue lookupValue,HttpServletRequest request){
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(lookupValueService.selectLookupValues(requestContext, lookupValue));
	}
	
	/**
	 * 快码行数据根据Type查询
	 * @param lookupValue 快码行DTO
	 * @param request 请求
	 * @return ResponseData
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/lookupValue/queryByType", method = RequestMethod.POST)
	public ResponseData selectLookupValuesByType(LookupValue lookupValue,HttpServletRequest request){
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(lookupValueService.selectLookupValuesByType(requestContext, lookupValue));
	}

	/**
	 * 公用的快码查询方法
	 * @author taotao.xu@hand-china.com
	 * @param type 快码类型
	 * @param request 请求
	 * @return String
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/fnd/code", produces = "application/javascript;charset=utf8")
    @ResponseBody
    public String getLookupValue(String type,HttpServletRequest request) throws JsonProcessingException {
        IRequest requestContext = createRequestContext(request);
        String queryString = request.getQueryString();//得到请求的URL地址中附带的参数
        String string[]=queryString.split("="); //字符串截取    
        LookupValue lookupValue = new LookupValue();
        lookupValue.setLookupType(string[1]);//参数的值
        List<LookupValue> data = lookupValueService.queryLookupValues(requestContext, lookupValue);
        if(data == null){
            return "[]";
        }   
        String var=string[0];//参数名
        StringBuilder sb = new StringBuilder();//字符串拼接
        sb.append("var ").append(var).append('=');
        sb.append(objectMapper.writeValueAsString(data));//调整json串格式
        sb.append(';');
        return sb.toString();
    }
	
	/**
	 * 将数据封装成json
	 * @author taotao.xu@hand-china.com
	 * @param sb StringBuilder
	 * @param var String
	 * @param data 数据源
	 * @throws JsonProcessingException
	 */
	protected void toJson(StringBuilder sb, String var, Object data) throws JsonProcessingException {
	    boolean hasVar = var != null && var.length() > 0;
	    if (hasVar) {
	        sb.append("var ").append(var).append('=');
	    }
	    sb.append(objectMapper.writeValueAsString(data));
	    if (hasVar) {
	        sb.append(';');
	    }
	}
	/**
	 * 快码头唯一性验证
	 * @param lookupType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/lookup/onlyValidate", method = RequestMethod.POST)
	public int onlyValidate(String lookupType){
		
		return lookupTypeService.SelectOnlyValidate(lookupType);
	}
	/**
	 * 快码行唯一性验证
	 * @param lookupValue
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/lookupValue/onlyValidate", method = RequestMethod.POST)
	public int onlyValidateValue(LookupValue lookupValue){
		return lookupValueService.SelectValueOnlyValidate(lookupValue);
	}
}