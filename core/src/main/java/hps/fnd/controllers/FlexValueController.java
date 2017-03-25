package hps.fnd.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hps.fnd.dto.FlexValue;
import hps.fnd.service.IFlexValueService;

@Controller
public class FlexValueController extends BaseController {
	// 违反唯一columnFlag
	public final static String NOT_UNIQUE_COLUMNFLAG = "hps.fnd.validationTable.not_unique_column_flag";
	// 没有columnFlag
	public final static String NOT_FOUND_COLUMNFLAG = "hps.fnd.validationTable.not_found_column_flag";
	// 违反唯一别名
	public final static String NOT_UNIQUE_COLUMNALIAS = "hps.fnd.validationTable.not_unique_column_alias";
	// 违反唯一valueField
	public final static String NOT_UNIQUE_VALUEFIELDS = "hps.fnd.validationTable.not_unique_valuefields";
	@Autowired
	private IFlexValueService flexValueService;

	/**
	 * 查询值集行表
	 * 
	 * @param flexValue
	 * @param page
	 * @param pagesize
	 * @return
	 * 
	 * @author tianle.liu@hand-china.com
	 * @date
	 */
	@RequestMapping(value = "/fnd/flexValue/queryLookupValues", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData selectFlexValues(HttpServletRequest request, FlexValue flexValue,
			@RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
		IRequest iRequest = createRequestContext(request);
		return new ResponseData(flexValueService.selectFlexValuesByExm(iRequest, flexValue, page, pageSize));
	}

	/*
	 * 查询对应的行数据
	 * 
	 * @author jie.yang03@hand-china.com
	 * 
	 * @param FlexVsetId 值集头ID
	 * 
	 * @param page 分页
	 * 
	 * @param pagesize 分页
	 * 
	 * @return 值集行集合
	 *//*
		 * 
		 * @RequestMapping(value = "/fnd/flexvalue/queryFlexValue", method =
		 * RequestMethod.POST)
		 * 
		 * @ResponseBody public ResponseData selectFlexValue(HttpServletRequest
		 * Long FlexVsetId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
		 * 
		 * @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
		 * return new
		 * ResponseData(flexValueService.selectFlexValuesByExm(iRequest,
		 * flexValue, page, pageSize)); }
		 */

}
