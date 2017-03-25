package hps.fnd.dto;
/**
 * @name MailInfoVo
 * @description  收件信息vo
 * @author yanjie.zhang@hand-china.com	2016年9月19日上午10:29:40
 * @version 1.0
 */
public class MailInfoVo {
	//配置代码
	private String mailConfigCode;
	
	//收件人标题
	private String subject;
	
	//收件内容
	private String content;
	
	//附件标题
	private String fileName;
	
	//附件路径
	private String filePath;
	
	//收件人类型
	private String receiverType;
	
	//收件人
	private String receiverAddress;

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

	public String getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
}
