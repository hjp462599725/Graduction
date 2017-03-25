package hps.fnd.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.system.dto.BaseDTO;

/**
 * @name Message
 * @description 消息 
 * @author yanjie.zhang@hand-china.com	2016年9月9日下午1:47:42
 * @version 1.0
 */
@Table(name="hps_fnd_message")
public class Message extends BaseDTO{
	
	private static final long serialVersionUID = -6228335733268809641L;
	
	/**
	 * 表ID，主键，供其他表做外键
	 */
	@Id
	@Column
	@GeneratedValue(generator=GENERATOR_TYPE)
	private Long messageId;
	
	/**
	 * 
	 */
	private String messageTypeCode;
	
	/**
	 * 接收者
	 */
	@Column
	private Long receiverId;
	
	/**
	 * 消息标题
	 */
	@Column
	private String title;
	
	/**
	 * 消息内容
	 */
	@Column
	private String content;
	
	/**
	 * 状态，N:新建，Y:处理
	 */
	@Column
	private String status;

	
	/**
	 * 消息类型快码
	 */
	@Transient
	private String messageTypeCodeMeaning;

	/**
	 *	消息状态
	 */
	@Transient
	private String messageStatus;

	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public Long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessageTypeCode() {
		return messageTypeCode;
	}
	public void setMessageTypeCode(String messageTypeCode) {
		this.messageTypeCode = messageTypeCode;
	}

	public String getMessageTypeCodeMeaning() {
		return messageTypeCodeMeaning;
	}

	public void setMessageTypeCodeMeaning(String messageTypeCodeMeaning) {
		this.messageTypeCodeMeaning = messageTypeCodeMeaning;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	@Override
	public String toString() {
		return "{" + '"' +
				"messageId" + '"'+":"+ '"'+ messageId + '"'+
				","+ '"' +"messageTypeCode" +'"'+ ":"+'"'+ messageTypeCode + '"' +
				","+ '"'+"receiverId" + '"' +":"+ '"'+ receiverId + '"'+
				"," + '"' + "title" +'"' +":"+ '"' + title + '"' +
				"," + '"'+"content" + '"'+":" + '"' + content + '"' +
				","+'"'+ "status" +'"'+ ":"+'"'+status + '"' +
				","+'"'+ "messageTypeCodeMeaning" +'"'+":" +'"'+messageTypeCodeMeaning + '"' +
				","+'"'+ "messageStatus" +'"'+":"+'"'+ messageStatus + '"' +
				'}';
	}

}
