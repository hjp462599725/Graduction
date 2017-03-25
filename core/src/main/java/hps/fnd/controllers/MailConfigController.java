package hps.fnd.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hps.fnd.dto.MailConfig;
import hps.fnd.dto.MailReceiver;
import hps.fnd.service.IMailConfigService;
import hps.fnd.util.MailHelper;
import hps.fnd.util.ValidateTableException;

/**
 * @name MailConfigController
 * @description
 * @author yanjie.zhang@hand-china.com	2016年9月13日下午7:33:37
 * @version 1.0
 */
@Controller
public class MailConfigController extends BaseController{
	
	public final static String NOT_UNIQUE_MAILCONFIGCODE = "hps.fnd.mail.not_unique_mailconfigcode";  
	
	@Autowired
	private IMailConfigService mailConfigService;
	
	@Autowired
	private MailHelper mailHelper;
	
	/**
	 * 查询消息配置
	 * @param mailConfig 消息配置DTO
	 * @param request 
	 * @param page 页
	 * @param pageSize 页条数
	 * @return 消息配置集合
	 */
	@RequestMapping(value="/fnd/mailConfig/query",method = RequestMethod.POST)
	@ResponseBody
	public ResponseData queryMailConfig(MailConfig mailConfig,HttpServletRequest request,
			@RequestParam(defaultValue=DEFAULT_PAGE)int page,@RequestParam(defaultValue=DEFAULT_PAGE_SIZE)int pageSize){
		IRequest requestCtx = createRequestContext(request);
		return new ResponseData(mailConfigService.selectMailConfig(mailConfig, requestCtx, page, pageSize));
	}
	
	/**
	 * 插入更新消息配置
	 * @param mailConfigs 需要更新的消息配置集合
	 * @param result
	 * @param request
	 * @return 消息配置集合
	 */
	@RequestMapping(value="/fnd/mailConfig/submit")
	public ResponseData submitMailConfig(@RequestBody List<MailConfig> mailConfigs,BindingResult result,HttpServletRequest request){
		Locale locale = RequestContextUtils.getLocale(request);
		for (int i = 0; i < mailConfigs.size(); i++) {
			if (mailConfigs.get(i).get__status() != null) {
				if (mailConfigs.get(i).get__status().equals("delete")) {
					mailConfigs.remove(i);
					i--;
				}
			}
		}
		ResponseData rd = null;
		IRequest requestCtx = createRequestContext(request);
		getValidator().validate(mailConfigs, result);
		if (result.hasErrors()) {
			rd = new ResponseData(false);
			rd.setMessage(getErrorMessage(result, request));
			return rd;
		}
		try {
			mailConfigService.batchUpdateMailConfig(mailConfigs, requestCtx);
		} catch (ValidateTableException e) {
			e.printStackTrace();
			rd = new ResponseData(false);
			rd.setMessage(this.getMessageSource().getMessage(e.getCode(), e.getArgs(), locale));
			return rd;
		}
		return new ResponseData(mailConfigs);
	}
	
	
	@RequestMapping(value="/fnd/mailConfig/sendMail")
	public void sendMail(MailConfig mailConfig) throws Exception{
		//邮件配置代码
		String mailConfigCode = "Test1";
		String sendMessage = "111111111";
		//设置发件信息  包括发件标题  发件内容  发件附件
		Map<String,String> sendInfo = new HashMap<String, String>();
		//设置收件人信息   包括收件人类型 和 收件人地址
		List<Map<String,String>> receivers = new ArrayList<>();
		Map<String,String> receiverInfo1 =  new HashMap<String, String>();
		receiverInfo1.put("receiverType", MailHelper.TO);
		receiverInfo1.put("receiverAddress", "597959095@qq.com");
		Map<String,String> receiverInfo2 =  new HashMap<String, String>();
		receiverInfo2.put("receiverType", MailHelper.CC);
		receiverInfo2.put("receiverAddress", "1391224474@qq.com");
		Map<String,String> receiverInfo3 =  new HashMap<String, String>();
		receiverInfo3.put("receiverType", MailHelper.BCC);
		receiverInfo3.put("receiverAddress", "jumptiger11@163.com");
		receivers.add(receiverInfo1);
		receivers.add(receiverInfo2);
		receivers.add(receiverInfo3);
		//sendInfo.put("receiver","597959095@qq.com");
		sendInfo.put("subject", "测试邮件");
		sendInfo.put("contend", "<a href='www.baidu.com'>测试邮件</a>");
		sendInfo.put("fileName", "验证按钮.jpg");
		sendInfo.put("filePath", "C:\\Users\\Light\\Desktop\\验证按钮.jpg");
		
		
		//测试
		Map<String,String> to = new HashMap<String, String>();
		to.put("597959095@qq.com", "测试测试");
		MailReceiver mailReceiver = new MailReceiver();
		mailReceiver.setMailConfigCode("Test1");
		mailReceiver.setTo(to);
		mailReceiver.setSubject("测试邮件");
		mailReceiver.setFilePath("C:\\Users\\Light\\Desktop\\验证按钮.jpg");
		mailReceiver.setFileName("验证按钮.jpg");
		String html = "<!DOCTYPE html>";    
			 html += "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">";   
			 html += "<title>test title</title>";    
			 html += "</head><body>";    
			 html += "<div style=\"width:600px;height:400px;margin:50px auto;color:red\">";    
			 html += "<h1>是否成功发送邮件</h1><br/><br/>";    
			 html += "<p>testtest</p><br/>";   
			 html += "<a href=\"http://www.baidu.com\">发送邮件</a>";    
			 html += "</div>"; 
			 html += "</body></html>";   
			 
		mailReceiver.setContent(html);
		mailReceiver.setContentType(MailHelper.CONTENT_TYPE_HTML);
		
		boolean flag = mailHelper.sendMail(mailReceiver);
	}

}
