package hps.alt.controllers;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hps.alt.dto.Recipient;
import hps.alt.service.IRecipientService;

/**
 * @name RecipientController
 * @description 
 * @author xing.gong@hand-china.com 2016年9月8日上午10:03:46
 * @version 1.0
 */
@Controller
public class RecipientController extends BaseController {
	
	
	@Autowired
	private IRecipientService recipientService;

	
	/**
	 * 查询所有收件人列表信息
	 * @param recipient
	 * @param page
	 * @param pagesize
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/alt/recipient/query", method = RequestMethod.POST)
    @ResponseBody    
    public ResponseData selectRecipient(@ModelAttribute Recipient recipient,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize,HttpServletRequest request) {		
		
		IRequest requestContext = createRequestContext(request);		
		return new ResponseData(recipientService.selectRecipient(recipient, page, pagesize, requestContext));
    }
	

	
	/**
	 * 批量更新收件人列表信息
	 * @param recipients
	 * @param result
	 * @param request
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "/alt/recipient/submit", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData submitRecipient(@RequestBody List<Recipient> recipients, BindingResult result, HttpServletRequest request)
			throws BaseException {
		/**
		 * description:对清除数据进行排查处理
		 * author:gongxing
		 */
		for(int i=0;i<recipients.size();i++){
			if(recipients.get(i).get__status() != null){
				if(recipients.get(i).get__status().equals("delete")){
					recipients.remove(i);
					i--;
				}
			}
		}
		  getValidator().validate(recipients, result);
		if (result.hasErrors()) {
			ResponseData rd = new ResponseData(false);
			rd.setMessage(getErrorMessage(result, request));
			return rd;
		} 
		IRequest requestCtx = createRequestContext(request);
		for(Recipient r:recipients){		
			if(null==r.getRecipientListId()){
				
				recipientService.insertRecipient(requestCtx,r);
				
			}else{
				recipientService.updateRecipient(requestCtx,r);
			}
		}
		return new ResponseData(recipients);
	}
	
	
	/**
	 * 批量删除收件人列表信息
	 * @param recipients
	 * @param result
	 * @param request
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "/alt/recipient/remove", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData submitRemove(@RequestBody List<Recipient> recipients, BindingResult result, HttpServletRequest request)
			throws BaseException {
		
		if (result.hasErrors()) {
			ResponseData rd = new ResponseData(false);
			rd.setMessage(getErrorMessage(result, request));
			return rd;
		} 
		for(Recipient r:recipients){
			recipientService.removeRecipient(r);
		}
		return new ResponseData(recipients);
	}
	
	@RequestMapping(value = "/alt/recipient/detail/query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData queryRecipientDetail(String recipientListId,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize,HttpServletRequest request){
		if(recipientListId == null || recipientListId == ""){
			recipientListId = "-1";
		}
		IRequest requestCtx = createRequestContext(request);
		return new ResponseData(recipientService.queryRecipientDatil(requestCtx, Long.parseLong(recipientListId), page, pagesize));
	}
}
