package hps.fnd.util;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import hps.fnd.dto.MailConfig;
import hps.fnd.dto.MailConfigSender;
import hps.fnd.dto.MailReceiver;

/**
 * @name MailHelper
 * @description
 * @author yanjie.zhang@hand-china.com	2016年9月14日上午9:21:29
 * @version 1.0
 */
public class MailHelper {
	@Autowired
	private MailConfigCache mailConfigCache;
	 
	//普通的收件人类型
	public static final String TO = "to";
	
	//抄送的收件人类型
	public static final String CC = "cc";
	
	//密送的收件人类型
	public static final String BCC = "bcc";
	
	//混合类型
	public static final String CONTENT_TYPE_MIXED = "mixed";
	
	//html类型
	public static final String CONTENT_TYPE_HTML = "html";
	
	//纯文本类型
	public static final String CONTENT_TYPE_TEXT = "text";
	
	
	
	/**
	 * 发送邮件
	 * @param mailConfigCode 邮件配置代码
	 * @param recerverInfo 接收信息  包括 ( 收件标题subject 收件内容content   附件fileName   附件路径filePath)  收件人receivers(包括收件人类别receiverType,收件人地址receiverAddress)
	 * @return
	 * @throws Exception
	 */
	public boolean sendMessage(MailReceiver mailReceiver) throws Exception {
		
		
		//获取发件信息
		MailConfig senderConfig = mailConfigCache.getMailConfig(mailReceiver.getMailConfigCode());
		
		
		MailConfigSender sender = new MailConfigSender();
		sender.setHost(senderConfig.getHost());
		sender.setUsername(senderConfig.getMailAddress());
		sender.setPassword(senderConfig.getPassword());
		
		//发送简单的邮件
/*		SimpleMailMessage smm = new SimpleMailMessage();
		
		smm.setFrom(sender.getUsername());
		smm.setTo(receiverInfo.get("receiver"));
		smm.setSubject(receiverInfo.get("subject"));
		smm.setText(receiverInfo.get("contend"));*/
		
		
		//使用JavaMail的MimeMessage，支付更加复杂的邮件格式和内容
		MimeMessage msg = sender.createMimeMessage();
		
		//创建MimeMessageHelper对象，处理MimeMessage的辅助类
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		// 发送者地址，必须填写正确的邮件格式，否者会发送失败
		helper.setFrom(sender.getUsername());
		
		//设置邮件主题
		helper.setSubject(mailReceiver.getSubject());
	
		//设置文本
		if(mailReceiver.getContentType() == "html"){
			
			
			
			   // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			   Multipart mainPart = new MimeMultipart();
			   // 创建一个包含HTML内容的MimeBodyPart
			   BodyPart html = new MimeBodyPart();
			   // 设置HTML内容
			   html.setContent(mailReceiver.getContent(), "text/html; charset=UTF-8");
			   mainPart.addBodyPart(html);
			   // 将MiniMultipart对象设置为邮件内容
			   //mailMessage.setContent(mainPart);
			   helper.setText(mainPart.toString(), true);
			
			
		}else if(mailReceiver.getContentType() =="text"){
			helper.setText(mailReceiver.getContent());
		}
		
		
		//附件
		if(mailReceiver.getFileName()!=null){
			File file = new File(mailReceiver.getFilePath());
	        if (file.isFile()) {
	            helper.addAttachment(FilenameUtils.getName(mailReceiver.getFileName()), file);
	        }
		}
		
		//普通收件人
		 if (mailReceiver.getTo() !=null) {      
			 for (Map.Entry<String, String> entry : mailReceiver.getTo().entrySet()) {    
				 // 名称不为空   
				 if (entry.getValue()!=null) {     
					 	helper.addTo(entry.getKey(), entry.getValue());        
				 } else {          
					 	helper.addTo(entry.getKey());   
				 }	    
			 } 
		 }
		 
		//抄送
		 if (mailReceiver.getCc() !=null) {      
			 for (Map.Entry<String, String> entry : mailReceiver.getCc().entrySet()) {    
				 // 名称不为空   
				 if (entry.getValue()!=null) {     
					 	helper.addCc(entry.getKey(), entry.getValue());        
				 } else {          
					 	helper.addCc(entry.getKey());   
				 }  
		    
			 } 
		 }
		 
		//密送
		 if (mailReceiver.getBcc() !=null) {      
			 for (Map.Entry<String, String> entry : mailReceiver.getBcc().entrySet()) {    
				 // 名称不为空   
				 if (entry.getValue()!=null) {     
					 	helper.addBcc(entry.getKey(), entry.getValue());        
				 } else {          
					 	helper.addBcc(entry.getKey());   
				 }  
		    
			 } 
		 }
	
		sender.send(msg);
		
		
		return false;
	}
	
	public boolean sendMail(MailReceiver mailReceiver) throws Exception{
		//获取发件信息
		MailConfig senderConfig = mailConfigCache.getMailConfig(mailReceiver.getMailConfigCode());
		
		Properties props = new Properties();
		props.put("mail.smtp.host", senderConfig.getHost());
		props.put("mail.smtp.auth", "true");
				
		Session mailSession = Session.getInstance(props,new javax.mail.Authenticator() {
	         protected PasswordAuthentication getPasswordAuthentication() {
	             return new PasswordAuthentication(senderConfig.getUserName(),
	            		 senderConfig.getPassword());
	         }
	    });
		//Session mailSession = Session.getDefaultInstance(props);
			
		Message msg = new MimeMessage(mailSession);
		
		// 发送者地址，必须填写正确的邮件格式，否者会发送失败
		msg.setFrom(new InternetAddress(senderConfig.getMailAddress()));
				
		//设置邮件主题
		msg.setSubject(mailReceiver.getSubject());
		
		//普通收件人
		 if (mailReceiver.getTo() !=null) {      
			 for (Map.Entry<String, String> entry : mailReceiver.getTo().entrySet()) {    
				 // 名称不为空   
				 if (entry.getValue()!=null) {     
					 msg.addRecipient(Message.RecipientType.TO,new InternetAddress(entry.getKey(),entry.getValue()));
				 } else {          
					 msg.addRecipient(Message.RecipientType.TO,new InternetAddress(entry.getKey()));  
				 }	    
			 } 
		 }
		
		//抄送
		 if (mailReceiver.getCc() !=null) {      
			 for (Map.Entry<String, String> entry : mailReceiver.getCc().entrySet()) {    
				 // 名称不为空   
				 if (entry.getValue()!=null) {     
					 msg.addRecipient(Message.RecipientType.CC,new InternetAddress(entry.getKey(),entry.getValue()));       
				 } else {          
					 msg.addRecipient(Message.RecipientType.CC,new InternetAddress(entry.getKey()));  
				 }  
		    
			 } 
		 }
		 
		 
		//密送
		 if (mailReceiver.getBcc() !=null) {      
			 for (Map.Entry<String, String> entry : mailReceiver.getBcc().entrySet()) {    
				 // 名称不为空   
				 if (entry.getValue()!=null) {     
					 msg.addRecipient(Message.RecipientType.BCC,new InternetAddress(entry.getKey(),entry.getValue()));       
				 } else {          
					 msg.addRecipient(Message.RecipientType.BCC,new InternetAddress(entry.getKey()));  
				 }  
			 } 
		 }
		 
		 
		 
		if(MailHelper.CONTENT_TYPE_MIXED.equals(mailReceiver.getContentType())){
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		    Multipart mainPart = new MimeMultipart();
		    // 创建一个包含HTML内容的MimeBodyPart
		    BodyPart html = new MimeBodyPart();
		    // 设置HTML内容
		    html.setContent(mailReceiver.getContent(), "text/html; charset=UTF-8");
		    
		    //文件附件
		    BodyPart file = new MimeBodyPart();
		    DataSource source = new FileDataSource(mailReceiver.getFilePath());
		    file.setDataHandler(new DataHandler(source));
		    file.setFileName(MimeUtility.encodeText(mailReceiver.getFileName()));
		    
		    
		    mainPart.addBodyPart(html);
		    mainPart.addBodyPart(file);
		    // 将MiniMultipart对象设置为邮件内容
		    //mailMessage.setContent(mainPart);
		    msg.setContent(mainPart);
			
		}else if(MailHelper.CONTENT_TYPE_HTML.equals(mailReceiver.getContentType())){
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		    Multipart mainPart = new MimeMultipart();
		    // 创建一个包含HTML内容的MimeBodyPart
		    BodyPart html = new MimeBodyPart();
		    // 设置HTML内容
		    html.setContent(mailReceiver.getContent(), "text/html; charset=UTF-8");
		    mainPart.addBodyPart(html);
		    // 将MiniMultipart对象设置为邮件内容
		    //mailMessage.setContent(mainPart);
		    msg.setContent(mainPart);
		}else{
			msg.setText(mailReceiver.getContent());
		}
		 
	    Transport transport = mailSession.getTransport("smtp");
	    transport.connect(senderConfig.getHost(), senderConfig.getMailAddress(), senderConfig.getPassword());
	    transport.sendMessage(msg, msg.getAllRecipients());
	    transport.close();
		return false;
	}
}
