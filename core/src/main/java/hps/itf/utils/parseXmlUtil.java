package hps.itf.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 
 * @name parseXmlUtil
 * @description 用于解析报文的工具类
 * @author jianping.huo@hand-china.com  2016年8月22日下午1:40:28
 * @version 1.0
 */
public class parseXmlUtil {
	/**
	 * 解析soapXML的例子
	 * @param soapXML
	 * @return
	 * @throws IOException 
	 * @throws SOAPException 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseSoapMessage(String soapXML) throws SOAPException, IOException {
		Map<String, String> map = new HashMap<>();
		SOAPMessage msg = formatSoapString(soapXML);
		SOAPBody body = msg.getSOAPBody();
		Iterator<SOAPElement> iterator = body.getChildElements();
		parseTest(iterator, map);
		return map;
	}

	/*public static void main(String[] args) {
		System.out.println("开始解析soap...");

		String deptXML =  "<SOAP:Envelope xmlns:SOAP=\"http://schemas.xmlsoap.org/soap/envelope/\">"
						+ "<SOAP:Header/>"
						+ "<SOAP:Body>"
							+ "<ns:OP_SDMS_Consume_Material_SynResponse xmlns:ns=\"http://toSDMS.material.service.ffcs.com\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
								+ "	<ns:return>"
									+ "<ns:BASEINFO>"
										+ "<MSGID>?</MSGID>"
										+ "<PMSGID>?</PMSGID>"
										+ "<SENDTIME>20140212094609</SENDTIME>"
										+ "<S_PROVINCE>?</S_PROVINCE>"
										+ "<S_SYSTEM>?</S_SYSTEM>"
										+ "<SERVICENAME>?</SERVICENAME>"
										+ "<T_PROVINCE>?</T_PROVINCE>"
										+ "<T_SYSTEM>?</T_SYSTEM>"
										+ "<RETRY>?</RETRY>"
									+ "</ns:BASEINFO>"
									+ "<ns:MESSAGE>"
										+ "<RESULT>E</RESULT>"
										+ "<REMARK/>"
										+ "<XMLDATA>"
										+ "<![CDATA[<response><RESULT>E</RESULT><MESSAGE>平台系统处理时发生异常!保存接口接收数据出错!</MESSAGE></response>]]>"
										+ "</XMLDATA>"
									+ "</ns:MESSAGE>"
								+ "</ns:return>"
							+ "</ns:OP_SDMS_Consume_Material_SynResponse>"
						+ "</SOAP:Body>"
						+ "</SOAP:Envelope>";
		String xml = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:sayHiResponse xmlns:ns2=\"http://service.cxf.com/\"><return>hello, hjpaddress, xuancheng</return></ns2:sayHiResponse></soap:Body></soap:Envelope>";
		ItfRespone respone = parseSoapMessage(xml);
		try {
			SOAPMessage msg = formatSoapString(xml);
			SOAPBody body = msg.getSOAPBody();
			Iterator<SOAPElement> iterator = body.getChildElements();
			PrintBody(iterator, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("解析soap结束...");
	}*/

	/**
	 * 把soap字符串格式化为SOAPMessage
	 * @param soapString
	 * @return
	 * @throws SOAPException 
	 * @throws UnsupportedEncodingException 
	 * @throws IOException 
	 * @see [类、类#方法、类#成员]
	 */
	private static SOAPMessage formatSoapString(String soapString) throws SOAPException, UnsupportedEncodingException, IOException {
		MessageFactory msgFactory = MessageFactory.newInstance();
		SOAPMessage reqMsg = msgFactory.createMessage(new MimeHeaders(),
					new ByteArrayInputStream(soapString.getBytes("UTF-8")));
		reqMsg.saveChanges();
		return reqMsg;
	}

	/**
	 * 解析soap xml
	 * @param iterator
	 * @param resultBean
	 */
	/*private static void parse(Iterator<SOAPElement> iterator, ItfRespone respone) {
		while (iterator.hasNext()) {
			SOAPElement element = iterator.next();
			//            System.out.println("Local Name:" + element.getLocalName());
			//            System.out.println("Node Name:" + element.getNodeName());
			//            System.out.println("Tag Name:" + element.getTagName());
			//            System.out.println("Value:" + element.getValue());
			if ("ns:BASEINFO".equals(element.getNodeName())) {
				continue;
			} else if ("ns:MESSAGE".equals(element.getNodeName())) {
				Iterator<SOAPElement> it = element.getChildElements();
				SOAPElement el = null;
				while (it.hasNext()) {
					el = it.next();
					if ("RESULT".equals(el.getLocalName())) {
						respone.setRespone(el.getValue());
						System.out.println("#### " + el.getLocalName() + "  ====  " + el.getValue());
					} else if ("REMARK".equals(el.getLocalName())) {
						respone.setRemark(null != el.getValue() ? el.getValue() : "");
						System.out.println("#### " + el.getLocalName() + "  ====  " + el.getValue());
					} else if ("XMLDATA".equals(el.getLocalName())) {
						respone.setXmlData(el.getValue());
						System.out.println("#### " + el.getLocalName() + "  ====  " + el.getValue());
					}
				}
			} else if (null == element.getValue()
					&& element.getChildElements().hasNext()) {
				parse(element.getChildElements(), respone);
			}
		}
	}*/
	
	/**
	 * 解析报文的例子
	 * @param iterator
	 * @param respone
	 */
	@SuppressWarnings("unchecked")
	private static void parseTest(Iterator<SOAPElement> iterator, Map<String, String> map) {
		while (iterator.hasNext()) {
			SOAPElement element = iterator.next();
			Iterator<SOAPElement> it = element.getChildElements();
			SOAPElement el = null;
			while (it.hasNext()) {
				el = it.next();
				map.put(el.getLocalName(), el.getValue());
			}
		}
	}

	/*private static void PrintBody(Iterator<SOAPElement> iterator, String side) {
		while (iterator.hasNext()) {
			SOAPElement element = (SOAPElement) iterator.next();
			System.out.println("Local Name:" + element.getLocalName());
			System.out.println("Node Name:" + element.getNodeName());
			System.out.println("Tag Name:" + element.getTagName());
			System.out.println("Value:" + element.getValue());
			if (null == element.getValue()
					&& element.getChildElements().hasNext()) {
				PrintBody(element.getChildElements(), side + "-----");
			}
		}
	}*/
	
	/**
	 * 解析报文的测试用例
	 * @param xmlInput
	 * @return  
	 * @throws DocumentException 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> resolveXml(String xmlInput) throws DocumentException {  
		Map<String, String> data = new HashMap<String, String>();
		Document document = DocumentHelper.parseText(xmlInput);
		Element root = document.getRootElement(); 
		List<Element> list = root.elements();
		for (Element element : list) {
			data.put(element.getName(), element.getText());
		}
		return data;  
	}  
}
