package hps.fnd.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * @name MailInfoVo
 * @description  收件信息vo
 * @author yanjie.zhang@hand-china.com	2016年9月19日上午10:29:40
 * @version 1.0
 */

public class MailReceiver implements Serializable{

	/**
	 * 配置代码
	 */
	private String mailConfigCode;
	
	/**
	 * 收件人标题
	 */
	private String subject;
	 
	/**
		混合类型
		MailHelper.CONTENT_TYPE_MIXED
		html类型
		MailHelper.CONTENT_TYPE_HTML
		纯文本类型
		MailHelper.CONTENT_TYPE_TEXT
	 */
	private String contentType;
	
	/**
	 * 收件内容
	 */
	private String content;
	
	/**
	 * 附件标题
	 */
	private String fileName;
	
	/**
	 * 附件路径
	 */
	private String filePath;
	
	/**
	 * 收件人 key邮箱地址  value名称
	 */
	private Map<String,String> to;
	
	/**
	 * 抄送人 key邮箱地址  value名称
	 */
	private Map<String,String> cc;
		
	/**
	 * 密送人 key邮箱地址  value名称
	 */
	private Map<String,String> bcc;

	public String getMailConfigCode() {
		return mailConfigCode;
	}

	public void setMailConfigCode(String mailConfigCode) {
		this.mailConfigCode = mailConfigCode;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Map<String, String> getTo() {
		return to;
	}

	public void setTo(Map<String, String> to) {
		this.to = to;
	}

	public Map<String, String> getCc() {
		return cc;
	}

	public void setCc(Map<String, String> cc) {
		this.cc = cc;
	}

	public Map<String, String> getBcc() {
		return bcc;
	}

	public void setBcc(Map<String, String> bcc) {
		this.bcc = bcc;
	}
}
