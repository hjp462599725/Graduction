package hps.itf.dto;

/**
 * 
 * @name ItfRespone
 * @description 请求的返回信息
 * @author jianping.huo@hand-china.com  2016年8月19日下午2:43:11
 * @version 1.0
 */
public class ItfRespone {
	public static final String SUCCESS_CODE = "200";
	public static final String SUCCESS_DESCRIPTION = "itf.request_success";
	
	private String statusCode;
	private String response;
	private Object xmlData;
	
	public ItfRespone() {
		super();
	}
	public ItfRespone(String statusCode, String response, Object xmlData) {
		super();
		this.statusCode = statusCode;
		this.response = response;
		this.xmlData = xmlData;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Object getXmlData() {
		return xmlData;
	}
	public void setXmlData(Object xmlData) {
		this.xmlData = xmlData;
	}

}
