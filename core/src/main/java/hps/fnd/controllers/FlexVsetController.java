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

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.dto.ResponseData;

import hps.fnd.dto.FlexVset;
import hps.fnd.dto.ValidateTable;
import hps.fnd.service.IFlexVsetService;
import hps.fnd.service.IValidateTableService;
import hps.fnd.util.ValidateTableException;

/**
 * @name FlexVsetController
 * @description 值集头处理Controller
 * @author dezhi.shen@hand-china.com 2016年9月5日下午3:40:20
 * @version 1.0
 */
@Controller
public class FlexVsetController extends BaseController {
	@Autowired
	private IFlexVsetService flexVsetService;

	@Autowired
	private IValidateTableService validationTableService;


	public final static String NOT_UNIQUE_FLEX_VALUE = "hfs.fnd.flex_value.not_unique_flex_value";
	public final static String NOT_UNIQUE_FLEX_SET_NAME = "hfs.fnd.flex_value.not_unique_flex_set_name";
/*	// 违反唯一columnFlag
	public final static String NOT_UNIQUE_COLUMNFLAG = "hfs.fnd.validationTable.not_unique_column_flag";
	// 没有columnFlag
	public final static String NOT_FOUND_COLUMNFLAG = "hfs.fnd.validationTable.not_found_column_flag";
*/
	/**
	 * 查询所有值集头表
	 * 
	 * @author jie.yang03@hand-china.com
	 * @param FlexVset
	 *            值集头信息对象
	 * @param page
	 *            分页
	 * @param pagesize
	 *            分页
	 * @param request
	 *            请求
	 * @return ResponseData 值集头集合
	 */
	@RequestMapping(value = "/fnd/flexVset/queryFlexVset", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData selectFlexVset(FlexVset flexVset, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(flexVsetService.selectFlexValueByExm(requestContext, flexVset, page, pagesize));
	}

	/**
	 * 批量保存值集头表信息 saveFlexVset
	 * 
	 * @param flexVsets
	 * @param result
	 * @param request
	 * @return
	 * @date 2016年9月5日 下午5:32:19
	 * @author dezhi.shen@hand-china.com
	 * @returnType ResponseData
	 */
	@RequestMapping(value = "/fnd/flexVset/saveFlexVset", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData saveFlexVset(@RequestBody List<FlexVset> flexVsets, BindingResult result,
			HttpServletRequest request) {
		IRequest iRequest = createRequestContext(request);

		Locale locale = RequestContextUtils.getLocale(request);
		for (int i = 0; i < flexVsets.size(); i++) {
			if (flexVsets.get(i).get__status() != null) {
				if (flexVsets.get(i).get__status().equals("delete")) {
					flexVsets.remove(i);
					i--;
				}
			}
		}
		getValidator().validate(flexVsets, result);
		if (result.hasErrors()) {
			ResponseData rd = new ResponseData(false);
			rd.setMessage(getErrorMessage(result, request));
			return rd;
		}
		try {
			flexVsetService.batchUpdateF(iRequest, flexVsets);
		} catch (ValidateTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ResponseData rd = new ResponseData(false);
			rd.setMessage(getMessageSource().getMessage(NOT_UNIQUE_FLEX_SET_NAME, null, locale));
			return rd;
		}
		return new ResponseData(flexVsets);
	}

	/**
	 * 批量保存头和行信息
	 * 
	 * @author jie.yang03@hand-china.com
	 * @param FlexVsets
	 *            值集头集合
	 * @param result
	 *            验证
	 * @param request
	 *            请求
	 * @return ResponseData 值集头集合
	 */
	@RequestMapping(value = "/fnd/flexVset/sumitFlexVset", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData sumitFlexValuesSet(@RequestBody List<FlexVset> flexVsets, BindingResult result,
			HttpServletRequest request) {
		getValidator().validate(flexVsets, result);
		Locale locale = RequestContextUtils.getLocale(request);
		if (result.hasErrors()) {
			ResponseData rd = new ResponseData(false);
			rd.setMessage(getErrorMessage(result, request));
			return rd;
		}
		IRequest requestContext = createRequestContext(request);
		try {
			flexVsetService.batchUpdateFlexVset(flexVsets, requestContext);
		} catch (ValidateTableException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			ResponseData rd = new ResponseData(false);
			rd.setMessage(this.getMessageSource().getMessage(e.getCode(), e.getArgs(), locale));
			return rd;
		}
		return new ResponseData(flexVsets);
	}

	/**
	 * 表验证值集 头行保存
	 * 
	 * @param FlexVsets
	 *            值集头数据
	 * @param result
	 *            验证
	 * @param request
	 *            请求
	 * @return ResponseData
	 * 
	 * @author tianle.liu@hand-china.com
	 * @date 2016.8.22
	 * @version 1.0
	 * @author dezhi.shen@hand-china.com
	 * @date 2016.8.30
	 * @version 1.1 修改columnFlag标识验证
	 */
	@RequestMapping(value = "/fnd/validationTable/save")
	public ResponseData updateValidationTable(@RequestBody @StdWho List<FlexVset> flexVsets, BindingResult result,
			HttpServletRequest request) {
		ResponseData rd;
		Locale locale = RequestContextUtils.getLocale(request);
		IRequest iRequest = createRequestContext(request);
		getValidator().validate(flexVsets, result);
		if (result.hasErrors()) {
			rd = new ResponseData(false);
			rd.setMessage(getErrorMessage(result, request));
			return rd;
		}
		for (FlexVset flexVset : flexVsets) {
			List<ValidateTable> tables = flexVset.getValidationTables();
			int i = 0;
			for (ValidateTable validateTable : tables) {
				//validateTable.setCreatedBy(iRequest.getUserId());
				//validateTable.setLastUpdateDate(new Date());
				if ("Y".equals(validateTable.getColumnFlag())) {
					i++;
				}
			}
			if (i > 1) {
				rd = new ResponseData(false);
				rd.setMessage(this.getMessageSource().getMessage(FlexValueController.NOT_UNIQUE_COLUMNFLAG, null, locale));
				return rd;
			}
		}
		try {
			validationTableService.updateValidateTables(flexVsets, iRequest);
		} catch (ValidateTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rd = new ResponseData(false);
			rd.setMessage(this.getMessageSource().getMessage(e.getCode(), e.getArgs(), locale));
			return rd;
		}
		return new ResponseData(flexVsets);
	}

}
