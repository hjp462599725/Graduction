package hps.fnd.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.IRequest;
import com.hand.hap.message.IMessagePublisher;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hps.fnd.dto.Message;
import hps.fnd.service.IMessageManageService;
import hps.fnd.service.MessageCache;
import hps.fnd.service.MessageConsumer;

/**
 * @name MessageManageController
 * @description 消息
 * @author yanjie.zhang@hand-china.com	2016年9月9日下午3:57:24
 * @version 1.0
 */
@Controller
public class MessageManageController extends BaseController{
	@Autowired
	private IMessagePublisher messagePublisher;
	
	@Autowired
	private MessageCache messageCache;
	
	

	@Autowired
	private IMessageManageService messageManageService;

	/**
	 * 测试消息发送
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/fnd/message/consumer",method = RequestMethod.GET)
	public ModelAndView messageSend(HttpServletRequest request){
		Message m = new Message();
		IRequest requestCtx = createRequestContext(request);
	/*	UserV u = new UserV();
		u.setUserId(requestCtx.getUserId());
		UserV userV = userService.queryUserAndEmpById(requestCtx,u); */
		m.setReceiverId(requestCtx.getUserId());
		m.setTitle("test");
		m.setContent("<br><Font color = Red>测试红色<BR></FONT><a href='http://www.baidu.com' target='_blank'>百度一下</a>");
		m.setStatus("N");
		m.setMessageTypeCode(MessageConsumer.messageTypeCodeHigh);

		//把消息推送到消息队列
		messagePublisher.rPush("messageQueue",m);
		ModelAndView view = new ModelAndView(getViewPath() + "/fnd/message");
		return view;
	}
	
	/**
	 * 显示消息到页面
	 * @param message
	 * @param request
	 * @param page
	 * @param pagesize
	 * @return
	 *
	 * */
	@RequestMapping(value="/fnd/message/query")
	public ResponseData queryMessage(Message message, HttpServletRequest request,
                                     @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize){
		IRequest requestCtx = createRequestContext(request);
/*		UserV u = new UserV();
		u.setUserId(requestCtx.getUserId());
		UserV userV = userService.queryUserAndEmpById(requestCtx,u);*/
		message.setReceiverId(requestCtx.getUserId());
		//String messages = messageCache.getMessage(message,requestCtx);

        com.github.pagehelper.Page<Map<String,String>> redisPage = null;
		List<Map<String,String>> mess = messageCache.getMessagePage(message,(page - 1) * pagesize , page * pagesize - 1);

		if (mess.size()!= 0){
            redisPage= new com.github.pagehelper.Page<Map<String,String>>();
			redisPage.setPageNum(page);

            redisPage.setEndRow(pagesize);
            redisPage.setPageSize(pagesize);
            redisPage.setTotal(messageManageService.queryMessageCount(message));
            redisPage.setCount(false);
			for(Map<String,String> m:mess){
				redisPage.add(m);
			}
		}else{
            redisPage= new com.github.pagehelper.Page<Map<String,String>>();
            redisPage.setTotal(0);
            redisPage.setCount(false);
        }
		return new ResponseData(redisPage);
	}

	/**
	 * 显示消息详细
	 * @param message
	 * @param request
	 * @return
	 * */
	@RequestMapping(value="/fnd/message/queryDetail")
	@ResponseBody
	public ResponseData queryMessageExit(Message message, HttpServletRequest request){
		IRequest requestCtx = createRequestContext(request);

		List<Map<String,String>> mes = messageCache.getMessageDetail(message);
		if ("N".equals(message.getStatus())){
			messageManageService.updataStatus(message);
		}
		return new ResponseData(mes);
	}

	/**
	 * 批量查看
	 * @param message 消息DTO
	 */
	@RequestMapping(value="/fnd/message/queryBatch",method = RequestMethod.POST, produces = "application/javascript;charset=utf8")
	public String queryMessageDetail(Message message){
		if (message.getMessageId()!=null){
			messageManageService.updataStatus(message);
		}
		return "[0]";
	}

}
