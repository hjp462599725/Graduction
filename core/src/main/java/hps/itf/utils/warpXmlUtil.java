package hps.itf.utils;

import hps.itf.dto.ItfDetail;
import hps.itf.exception.ItfLocalException;

/**
 * 
 * @name warpXmlUtil
 * @description 封装报文的工具类 
 * @author jianping.huo@hand-china.com  2016年8月22日下午1:43:07
 * @version 1.0
 */
public class warpXmlUtil {
	/**
	 * sb.append ("<titlehtml><![CDATA[");
	 * sb.append (titlehtml);
	 * sb.append ("]]></titlehtml>");   <![CDATA[]]>代表在这中间的字符串，会忽略所有的符号
	 * @param detail
	 * @param reqXml
	 * @return
	 * @throws ItfLocalException
	 */
	public static String warpXml(ItfDetail detail, String reqXml) throws ItfLocalException { 
		if(reqXml == null){
			throw new ItfLocalException(ItfLocalException.XML_WRAP_ERROR, ItfLocalException.LOCAL_ERROR_CODE, 
					ItfLocalException.XML_WRAP_ERROR, null);
		}
		if(detail.getHeader().getNamespace() == null){
			throw new ItfLocalException(ItfLocalException.XML_NO_NAMESPACE, ItfLocalException.LOCAL_ERROR_CODE, 
					ItfLocalException.XML_NO_NAMESPACE, null);
		}
		if(detail.getLine().getItfUrl() == null){
			throw new ItfLocalException(ItfLocalException.XML_NO_METHOD, ItfLocalException.LOCAL_ERROR_CODE, 
					ItfLocalException.XML_NO_METHOD, null);
		}
		StringBuilder sb = new StringBuilder();  
		sb.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");  
		sb.append("<soap:Header/>");  
		sb.append("<soap:Body>");  
		//sb.append("<ns2:sayByeBye xmlns:ns2=\"http://server.cxf.zgf.org/\">");  
		sb.append("<ns2:"+detail.getLine().getItfUrl()+" xmlns:ns2=\""+detail.getHeader().getNamespace()+"\">");
		sb.append(reqXml.trim());     
		sb.append("</ns2:sayHi>");  
		sb.append("</soap:Body>");  
		sb.append("</soap:Envelope>");
		return sb.toString();
	}  
	
	/**
	 * 封装Rest报文的例子
	 * @param detail
	 * @param reqXml
	 * @return
	 * @throws ItfLocalException
	 */
	public static String warpRESTXml(ItfDetail detail, String reqXml) throws ItfLocalException {
		if(reqXml == null){
			throw new ItfLocalException(ItfLocalException.XML_WRAP_ERROR, ItfLocalException.LOCAL_ERROR_CODE, 
					ItfLocalException.XML_WRAP_ERROR, null);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		sb.append (reqXml);
		return sb.toString();
	}
}
