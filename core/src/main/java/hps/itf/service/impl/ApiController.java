package hps.itf.service.impl;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.spi.http.HttpContext;

import org.apache.commons.lang3.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import hps.itf.dto.ItfRespone;
import hps.itf.exception.ItfLocalException;
import hps.itf.exception.ItfRemoteException;
import hps.itf.service.IApiService;

/**
 * 
 * @name ApiController
 * @description 用户请求WebService的统一入口
 * @author jianping.huo@hand-china.com 2016年8月22日下午1:15:45
 * @version 1.0
 */

@Path("")
@Component("example")
public class ApiController {
	@Autowired
	private IApiService api;
	@Autowired
	private MessageSource messageSource;
	
	@POST
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
	@Path("/Hap")
	public String transfers(@RequestBody String xml, @QueryParam(value = "name") String name, @Context HttpServletRequest request) {
		String url = "";
		url = request.getScheme() +"://" + request.getServerName()  
		+ ":" +request.getServerPort() 
		+ request.getServletPath();
		String u = request.getRequestURL().toString();
		System.out.println("===================="+name+"-----"+xml+url + "\n" + u);
		return "hhp"+name;
	}
	
	@POST
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
	@Path("/Hap-Webservice")	
	public ItfRespone transfer(@RequestBody(required = false) String requestXml, @QueryParam(value = "sysCode") String sysCode, 
			@QueryParam(value = "itfCode") String itfCode, @QueryParam(value = "lang") String lang, @Context HttpServletRequest request) {
		Locale locale = LocaleUtils.toLocale(lang);
		String url = request.getRequestURL().toString();
		return handle(sysCode, itfCode, url, requestXml, locale);
	}

	/**
	 * 获取用户url上的headerCode和lineCode进行请求的分发
	 * 
	 * @param headerCode
	 *            系统代码 
	 * @param lineCode
	 *            接口代码
	 * @param xml
	 *            报文信息
	 * @return ItfRespone 统一的json数据
	 */
	@SuppressWarnings("finally")
	private ItfRespone handle(String sysCode, String itfCode, String url, String xml, Locale locale) {
		ItfRespone msg = new ItfRespone();
		String error = null;
		try { 
			msg = api.distribute(sysCode, itfCode, url, xml);
			String respone = messageSource.getMessage(msg.getResponse(), null, locale);
			msg.setResponse(respone);
		} catch (ItfLocalException e) {
			// TODO Auto-generated catch block
			error = messageSource.getMessage(e.getDescriptionKey(), e.getParameters(), locale);
			msg.setStatusCode(e.getCode());
			msg.setResponse(error);
			msg.setXmlData(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			error = messageSource.getMessage(ItfLocalException.NO_WEBSERVICE_IMPL, null, locale);
			msg.setStatusCode(ItfLocalException.LOCAL_ERROR_CODE);
			msg.setResponse(error);
			msg.setXmlData(e.getMessage());
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			error = messageSource.getMessage(ItfLocalException.NO_INTERFACE_METHOD, null, locale);
			msg.setStatusCode(ItfLocalException.LOCAL_ERROR_CODE);
			msg.setResponse(error);
			msg.setXmlData(e.getMessage());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			error = messageSource.getMessage(ItfLocalException.NOT_HAVE_ENOUGH_ACCESS, null, locale);
			msg.setStatusCode(ItfLocalException.LOCAL_ERROR_CODE);
			msg.setResponse(error);
			msg.setXmlData(e.getMessage());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			error = messageSource.getMessage(ItfLocalException.CAN_NOT_CREATE_NEWINSTANCE, null, locale);
			msg.setStatusCode(ItfLocalException.LOCAL_ERROR_CODE);
			msg.setResponse(error);
			msg.setXmlData(e.getMessage());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			error = messageSource.getMessage(ItfLocalException.NOT_HAVE_ENOUGH_ACCESS, null, locale);
			msg.setStatusCode(ItfLocalException.LOCAL_ERROR_CODE);
			msg.setResponse(error);
			msg.setXmlData(e.getMessage());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			error = messageSource.getMessage(ItfLocalException.NO_MATCHING_ARGUMENT, null, locale);
			msg.setStatusCode(ItfLocalException.LOCAL_ERROR_CODE);
			msg.setResponse(error);
			msg.setXmlData(e.getMessage());
		} catch (ItfRemoteException e) {
			// TODO: handle exception
			error = messageSource.getMessage(e.getDescriptionKey(), null, locale);
			msg.setStatusCode(e.getCode());
			msg.setResponse(error);
			msg.setXmlData(e.getMap().get("exception"));
		} finally {
			return msg;
		}
	}
}
