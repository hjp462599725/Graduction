package hps.alt.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hand.hap.core.IRequest;
import com.hand.hap.job.AbstractJob;
import com.hand.hap.message.IMessagePublisher;

import hps.alt.dto.AlertAction;
import hps.alt.dto.AlertActionDtl;
import hps.alt.service.IAlertActionDtlService;
import hps.alt.service.IAlertActionService;
import hps.fnd.dto.MailReceiver;
import hps.fnd.dto.Message;
import hps.fnd.service.MessageConsumer;
import hps.fnd.util.MailHelper;
import hps.org.dto.HpsUser;
import hps.org.service.IHpsUserService;

public class MailJob extends AbstractJob{
	@Autowired
	private MailHelper mailHelper;
	@Autowired
	private IAlertActionService alertActionService;
	@Autowired
	private IAlertActionDtlService alertActionDtlService;
	@Autowired
	private IMessagePublisher messagePublisher;
	@Autowired
	private IHpsUserService hpsUserService;
	public HttpServletRequest request;

	@Override
	public boolean isRefireImmediatelyWhenException() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void safeExecute(JobExecutionContext context) throws Exception {
		// TODO Auto-generated method stub
		IRequest requestCtx = createRequestContext(request);
		String tempAlertId = context.getMergedJobDataMap().getString("alertId");
		String content = context.getMergedJobDataMap().getString("content");
		String count = context.getMergedJobDataMap().getString("count");
		
		
		
		Long alertId = Long.parseLong(tempAlertId);
		//查询alert下对用的a活动
		AlertAction alertAction = new AlertAction();
		alertAction.setAlertId(alertId);
		List<AlertAction> alertActions = alertActionService.selectAlertActions(requestCtx, alertAction);
		
		//发送所有的预警活动
		for(AlertAction a : alertActions){
			//查询预警活动详情配置信息
			AlertActionDtl tempActionDtl = new AlertActionDtl();
			tempActionDtl.setAlertId(alertId);
			tempActionDtl.setActionId(a.getActionId());
			AlertActionDtl alertActionDtl = alertActionDtlService.queryAlertActionDtl(requestCtx, tempActionDtl);
			//获取预警接收运功的用户ID
			Long empId = alertActionDtl.getRecEmployeeId();
			HpsUser tempUser = new HpsUser();
			tempUser.setAttribute1(Long.toString(empId));
			List<HpsUser> users = hpsUserService.selectHpsUserById(tempUser, requestCtx);
			
			MailReceiver mailReceiver = new MailReceiver();
			Map<String,String> to  = new HashMap();
			to.put(alertActionDtl.getRecipient(), "recipient");
			//设置邮件参数
			mailReceiver.setSubject(alertActionDtl.getSubject());
			mailReceiver.setMailConfigCode("Test2");
			mailReceiver.setContentType(MailHelper.CONTENT_TYPE_TEXT);
			mailReceiver.setTo(to);
			//设置站内信参数
			Message message = new Message();
			message.setTitle(alertActionDtl.getSubject());
			message.setContent(alertActionDtl.getAlertMessage());
			message.setStatus("N");
			message.setMessageTypeCode(MessageConsumer.messageTypeCodeHigh);
			//判断预警消息什么类型
			if(a.getNoticeTypeCode().equals("S")){
				for(HpsUser u : users){
					if(a.getActionLevelCode().equals("D")){
						for(int i=0;i<Integer.parseInt(count);i++){
							message.setReceiverId(u.getUserId());
							message.setContent("<Font color = Red>"+context.getMergedJobDataMap().getString(Integer.toString(i))+"<br></font>");
							messagePublisher.rPush("messageQueue",message);
						}
						
					}else{
						message.setReceiverId(u.getUserId());
						message.setContent("<Font color = Red>"+content+"</font>");
						messagePublisher.rPush("messageQueue",message);
					}
				}
				
			}else if(a.getNoticeTypeCode().equals("E")){
				if(a.getActionLevelCode().equals("D")){
					for(int i=0;i<Integer.parseInt(count);i++){
						mailReceiver.setContent(context.getMergedJobDataMap().getString(Integer.toString(i)));
						mailHelper.sendMail(mailReceiver);
					}
					
				}else{
					mailReceiver.setContent(alertActionDtl.getAlertMessage());
					mailHelper.sendMail(mailReceiver);
				}
			}
		}
	}

	private IRequest createRequestContext(HttpServletRequest request2) {
		// TODO Auto-generated method stub
		return null;
	}

}
