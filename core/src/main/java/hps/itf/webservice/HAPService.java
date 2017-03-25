package hps.itf.webservice;

import java.io.IOException;
import java.util.Map;

import javax.xml.soap.SOAPException;

import hps.itf.dto.ItfDetail;
import hps.itf.dto.ItfRespone;
import hps.itf.exception.ItfLocalException;
import hps.itf.exception.ItfRemoteException;
import hps.itf.utils.HttpUtil;
import hps.itf.utils.parseXmlUtil;
import hps.itf.utils.warpXmlUtil;

/**
 * 
 * @name HAPService
 * @description 系统代码:HAP
 * @author jianping.huo@hand-china.com  2016年8月22日下午1:50:36
 * @version 1.0
 */
public class HAPService {
	/**
	 * 接口代码:SAYHI
	 * @param detail ItfDetail接口详细信息
	 * @param xml 用户发送的报文
	 * @return ItfRespone 统一的json数据
	 * @throws IOException 
	 * @throws ItfLocalException
	 * @throws SOAPException 
	 * @throws ItfRemoteException 
	 */
	public ItfRespone SAYHIRequest(ItfDetail detail, String xml) throws ItfLocalException, IOException, SOAPException, ItfRemoteException {
		// TODO Auto-generated method stub
		String url = null;
		if(detail.getHeader().getServiceType().equals("SOAP")){
			url =detail.getHeader().getDomainUrl();//soap请求接口地址
		}else if(detail.getHeader().getServiceType().equals("REST")){
			url =detail.getHeader().getDomainUrl()+detail.getLine().getItfUrl();//rest请求接口地址
		}
		String method = detail.getLine().getRequestMethod();
		String reqXml = null;
		reqXml = warpXmlUtil.warpXml(detail, xml);
		String contenttype = detail.getHeader().getRequestContenttype();
		//发送请求
		Map<String, Object> map = HttpUtil.net(url, reqXml, method, contenttype);
		//解析
		Map<String, String> result = parseXmlUtil.parseSoapMessage((String)map.get("result"));
		return new ItfRespone(ItfRespone.SUCCESS_CODE, ItfRespone.SUCCESS_DESCRIPTION, result);
	}

}
