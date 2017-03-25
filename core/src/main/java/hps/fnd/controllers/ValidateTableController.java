package hps.fnd.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hps.fnd.dto.ValidateTable;
import hps.fnd.service.IValidateTableService;
@Controller
public class ValidateTableController extends BaseController{

	@Autowired
	private IValidateTableService validationTableService;
	/**
	 * 查询表验值集行字段
	 * 
	 * @param validationTable
	 *            查询条件，表验证值集行对象DTO
	 * @param request
	 *            上下文请求
	 * @param page
	 *            页码
	 * @param pagesize
	 *            页大小
	 * @return ResponseData
	 * 
	 * @author tianle.liu@hand-china.com
	 * @date 2016.8.22
	 */
	@ResponseBody
	@RequestMapping(value = "/fnd/validationTable/query")
	public ResponseData selectValidationTable(ValidateTable validationTable, HttpServletRequest request,
			@RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(
				validationTableService.select(requestContext,validationTable, page, pagesize));
	}
}
