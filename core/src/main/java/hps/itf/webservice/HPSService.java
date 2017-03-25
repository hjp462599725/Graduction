package hps.itf.webservice;

import java.io.IOException;
import java.util.Map;

import org.dom4j.DocumentException;

import hps.itf.dto.ItfDetail;
import hps.itf.dto.ItfRespone;
import hps.itf.exception.ItfLocalException;
import hps.itf.exception.ItfRemoteException;
import hps.itf.utils.HttpUtil;
import hps.itf.utils.parseXmlUtil;
import hps.itf.utils.warpXmlUtil;

/**
 * 
 * @name EBSService
 * @description 系统代码:EBS
 * @author jianping.huo@hand-china.com  2016年8月22日下午1:45:24
 * @version 1.0
 */
public class HPSService {
	/**
	 * 接口代码:SAY
	 * @param detail ItfDetail接口详细信息
	 * @param xml 用户发送的报文
	 * @return ItfRespone 统一的json数据
	 * @throws IOException 
	 * @throws ItfRemoteException 
	 */
	public ItfRespone SAYRequest(ItfDetail detail, String xml) throws IOException, ItfRemoteException {
		// TODO Auto-generated method stub
		Map<String, Object> map = null;
		String url = null;
		if(detail.getHeader().getServiceType().equals("SOAP")){
			url =detail.getHeader().getDomainUrl();//soap请求接口地址
		}else if(detail.getHeader().getServiceType().equals("REST")){
			url =detail.getHeader().getDomainUrl()+detail.getLine().getItfUrl();//rest请求接口地址
		}
		String method = detail.getLine().getRequestMethod();
		String contenttype = detail.getHeader().getRequestContenttype();
		map = HttpUtil.net(url, xml, method, contenttype);
		return new ItfRespone(ItfRespone.SUCCESS_CODE, ItfRespone.SUCCESS_DESCRIPTION, map.get("result"));
	}
	/**
	 * 接口代码:SENDXML
	 * @param detail ItfDetail接口详细信息
	 * @param xml 用户发送的报文
	 * @return ItfRespone 统一的json数据
	 * @throws ItfLocalException
	 * @throws IOException 
	 * @throws ItfRemoteException 
	 */
	public ItfRespone SENDXMLRequest(ItfDetail detail, String xml) throws ItfLocalException, IOException, ItfRemoteException {
		// TODO Auto-generated method stub
		Map<String, Object> map = null;
		String url = null;
		if(detail.getHeader().getServiceType().equals("SOAP")){
			url =detail.getHeader().getDomainUrl();//soap请求接口地址
		}else if(detail.getHeader().getServiceType().equals("REST")){
			url =detail.getHeader().getDomainUrl()+detail.getLine().getItfUrl();//rest请求接口地址
		}
		String method = detail.getLine().getRequestMethod();
		String reqXml = null;
		reqXml = warpXmlUtil.warpRESTXml(detail, xml);
		String contenttype = detail.getHeader().getRequestContenttype();
		map = HttpUtil.net(url, reqXml, method, contenttype);
		return new ItfRespone(ItfRespone.SUCCESS_CODE, ItfRespone.SUCCESS_DESCRIPTION, map.get("result"));
	}
	/**
	 * 接口代码:SENDBEAN
	 * @param detail ItfDetail接口详细信息
	 * @param xml 用户发送的报文
	 * @return ItfRespone 统一的json数据
	 * @throws ItfLocalException 
	 * @throws IOException  
	 * @throws DocumentException 
	 * @throws ItfRemoteException 
	 */
	public ItfRespone SENDBEANRequest(ItfDetail detail, String xml) throws ItfLocalException, IOException, DocumentException, ItfRemoteException {
		Map<String, Object> map = null;
		String url = null;
		if(detail.getHeader().getServiceType().equals("SOAP")){
			url =detail.getHeader().getDomainUrl();//soap请求接口地址
		}else if(detail.getHeader().getServiceType().equals("REST")){
			url =detail.getHeader().getDomainUrl()+detail.getLine().getItfUrl();//rest请求接口地址
		}
		String method = detail.getLine().getRequestMethod();
		String reqXml = null;
		reqXml = warpXmlUtil.warpRESTXml(detail, xml);
		String contenttype = detail.getHeader().getRequestContenttype();
		map = HttpUtil.net(url, reqXml, method, contenttype);
		Map<String, String> result = parseXmlUtil.resolveXml((String)map.get("result"));
		return new ItfRespone(ItfRespone.SUCCESS_CODE, ItfRespone.SUCCESS_DESCRIPTION, result);
	}
	/**
	 * 接口代码:SENDXML2
	 * @param detail ItfDetail接口详细信息
	 * @param xml 用户发送的报文
	 * @return ItfRespone 统一的json数据
	 * @throws ItfLocalException 
	 * @throws IOException 
	 * @throws DocumentException 
	 * @throws ItfRemoteException 
	 */
	public ItfRespone SENDXML2Request(ItfDetail detail, String xml) throws ItfLocalException, IOException, DocumentException, ItfRemoteException {
		String url = null;
		if(detail.getHeader().getServiceType().equals("SOAP")){
			url =detail.getHeader().getDomainUrl();//soap请求接口地址
		}else if(detail.getHeader().getServiceType().equals("REST")){
			url =detail.getHeader().getDomainUrl()+detail.getLine().getItfUrl();//rest请求接口地址
		}
		String method = detail.getLine().getRequestMethod();
		String reqXml = null;
		reqXml = warpXmlUtil.warpRESTXml(detail, xml);
		String contenttype = detail.getHeader().getRequestContenttype();
		Map<String, Object> map = HttpUtil.net(url, reqXml, method, contenttype);
		Map<String, String> result = parseXmlUtil.resolveXml((String)map.get("result"));
		return new ItfRespone(ItfRespone.SUCCESS_CODE, ItfRespone.SUCCESS_DESCRIPTION, result);
	}
}
