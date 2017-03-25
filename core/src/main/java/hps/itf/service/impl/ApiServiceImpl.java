package hps.itf.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.xml.soap.SOAPException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.dom4j.DocumentException;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand.hap.cache.Cache;
import com.hand.hap.cache.CacheManager;
import com.hand.hap.cache.impl.HashStringRedisCache;
import com.hand.hap.system.dto.ProfileValue;
import com.hand.hap.system.mapper.ProfileValueMapper;

import hps.itf.dto.ItfDetail;
import hps.itf.dto.ItfRespone;
import hps.itf.dto.ItfServiceHeader;
import hps.itf.dto.ItfServiceLine;
import hps.itf.exception.ItfLocalException;
import hps.itf.exception.ItfRemoteException;
import hps.itf.mapper.ItfServiceHeaderMapper;
import hps.itf.mapper.ItfServiceLineMapper;
import hps.itf.service.IApiService;

@Service
public class ApiServiceImpl implements IApiService {

	@Autowired
	private ItfServiceHeaderMapper headerMapper;
	@Autowired
	private ItfServiceLineMapper lineMapper;
	@Autowired
	private ProfileValueMapper profileValueMapper;
	@Autowired
	private CacheManager cacheManager;
	
	/**
	 * WebService详细信息
	 */
	private String packageName = "has.itf.webservice.";
	
	/**
	 * 从配置维护中获取实现类所在的包名
	 * @throws ItfLocalException
	 */
	private void initPackage() throws ItfLocalException {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		SqlSessionFactory factory = null;
		try {
			factory = factoryBean.getObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SqlSession session = factory.openSession();
		String stmt = "com.hand.hap.system.mapper.ProfileValueMapper.selectPriorityValues";
		List<ProfileValue> profileValues = session.selectList(stmt, "ITF_SERVICE_PACKAGE");
		if (profileValues.size() != 0) {
			ProfileValue profileValue = profileValues.get(0);
			packageName = profileValue.getProfileValue();
		} else {
			throw new ItfLocalException(ItfLocalException.NO_WEBSERVICE_IMPL, ItfLocalException.LOCAL_ERROR_CODE, 
					ItfLocalException.NO_WEBSERVICE_IMPL, null);
		}
	}
	
	/**
	 * 初始化WebService头信息
	 * @param header 接口头DTO
	 * @return ItfServiceHeader 接口头DTO
	 * @throws ItfLocalException
	 */
	private ItfServiceHeader initHeader(ItfServiceHeader header) throws ItfLocalException {
		header = headerMapper.selectByCodeAndEnableFlag(header);
		if (header == null) {
			throw new ItfLocalException(ItfLocalException.NO_SUITABLE_SYS, ItfLocalException.LOCAL_ERROR_CODE, 
					ItfLocalException.NO_SUITABLE_SYS, null);
		}
		return header;
	}

	/**
	 * 初始化Webservice行信息
	 * @param line 接口行DTO
	 * @return ItfServiceLine 接口行DTO
	 * @throws ItfLocalException
	 */
	private ItfServiceLine initLine(ItfServiceLine line) throws ItfLocalException {
		line = lineMapper.selectByHeaderIdAndCode(line);
		if (line == null) {
			throw new ItfLocalException(ItfLocalException.NO_SUITABLE_IFS, ItfLocalException.LOCAL_ERROR_CODE, 
					ItfLocalException.NO_SUITABLE_IFS, null);		
		}
		if(line.getEnableFlag().equals("N")){
			throw new ItfLocalException(ItfLocalException.IFS_IS_NOT_ACTIVE, ItfLocalException.LOCAL_ERROR_CODE, 
					ItfLocalException.IFS_IS_NOT_ACTIVE, null);
		}
		return line;
	}

	/**
	 * 初始化Ws信息,将其放入detail
	 * @param headerCode 系统代码
	 * @param lineCode 接口代码
	 * @throws ItfLocalException
	 */
	private ItfDetail initFromOrcl(String headerCode, String lineCode) throws ItfLocalException {
		ItfServiceHeader sys = new ItfServiceHeader();
		ItfServiceLine itf = new ItfServiceLine();
		ItfDetail detail = new ItfDetail();
		// 获取头信息
		sys.setEnableFlag("Y");
		sys.setServiceCode(headerCode);
		// 如果没有查到相关信息应该抛出异常
		sys = this.initHeader(sys);
		// 获取行信息
		itf.setServiceHeaderId(sys.getServiceHeaderId());
		itf.setLineCode(lineCode);
		// 如果没有查到相关信息应该抛出异常
		itf = this.initLine(itf);
		detail.setHeader(sys);
		detail.setLine(itf);
		//获取具体实现类所在的包
		this.initPackage();
		return detail;
	}
	
	private ItfDetail initFromCache(String sysCode, String itfCode, String url) throws ItfLocalException {
		ItfServiceHeader sys = new ItfServiceHeader();
		ItfServiceLine itf = new ItfServiceLine();
		ItfDetail detail = new ItfDetail();
		StringBuilder headerKey = new StringBuilder();
		headerKey.append(sysCode);
		headerKey.append('.');
		headerKey.append(url);
		Cache<?> headerCache = cacheManager.getCache("itfHeader");
		if (headerCache instanceof HashStringRedisCache) {
            sys = (ItfServiceHeader) headerCache.getValue(headerKey.toString());
        }
		if(sys == null){
			throw new ItfLocalException(ItfLocalException.NO_SUITABLE_SYS, ItfLocalException.LOCAL_ERROR_CODE, 
					ItfLocalException.NO_SUITABLE_SYS, null);
		}else if("N".equals(sys.getEnableFlag())){
			throw new ItfLocalException(ItfLocalException.SYS_IS_NOT_ACTIVE, ItfLocalException.LOCAL_ERROR_CODE, 
					ItfLocalException.SYS_IS_NOT_ACTIVE, null);
		}else if("Y".equals(sys.getEnableFlag())){
			StringBuilder lineKey = new StringBuilder();
			lineKey.append(sys.getServiceHeaderId().toString());
			lineKey.append('.');
			lineKey.append(itfCode);
			Cache<?> lineCache = cacheManager.getCache("itfLine");
			if (lineCache instanceof HashStringRedisCache) {
				itf = (ItfServiceLine) lineCache.getValue(lineKey.toString());
			}
			if(itf == null){
				throw new ItfLocalException(ItfLocalException.NO_SUITABLE_IFS, ItfLocalException.LOCAL_ERROR_CODE, 
						ItfLocalException.NO_SUITABLE_IFS, null);
			}else if("N".equals(itf.getEnableFlag())){
				throw new ItfLocalException(ItfLocalException.IFS_IS_NOT_ACTIVE, ItfLocalException.LOCAL_ERROR_CODE, 
						ItfLocalException.IFS_IS_NOT_ACTIVE, null);
			}else if("Y".equals(itf.getEnableFlag())){
				detail.setHeader(sys);
				detail.setLine(itf);
			}
		}	
		return detail;
	}
	
	@Override
	public ItfRespone distribute(String sysCode, String itfCode, String url, String xml) throws ItfLocalException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, ItfRemoteException {
		// TODO Auto-generated method stub
		initPackage();
		ItfDetail detail = initFromCache(sysCode, itfCode, url);
		//反射构造具体实现类
		Class<?> clazz = Class.forName(packageName + detail.getHeader().getServiceCode() + "Service");
		//拿到分发对应的方法
		Method method = clazz.getMethod(detail.getLine().getLineCode() + "Request", ItfDetail.class, String.class);
		Object o = clazz.newInstance();
		//返回经过解析以后的数据结果
		ItfRespone respone = null;
		try {
			respone = (ItfRespone) method.invoke(o, detail, xml == null ? null : xml);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			Throwable cause = e.getCause();
			if(cause instanceof ItfLocalException){
				ItfLocalException e1 = (ItfLocalException) cause;
				throw new ItfLocalException(e1.getMessage(), ItfLocalException.LOCAL_ERROR_CODE, 
						ItfLocalException.XML_WRAP_ERROR, null);
			}else if(cause instanceof ItfRemoteException){
				ItfRemoteException e1 = (ItfRemoteException) cause;
				throw e1;
			}else if(cause instanceof SOAPException){
				SOAPException e1 = (SOAPException) cause;
				throw new ItfLocalException(e1.getMessage(), ItfLocalException.LOCAL_ERROR_CODE, 
						ItfLocalException.ILLEGAL_SOAP_MESSAGE, null);
			}else if(cause instanceof DocumentException){
				DocumentException e1 = (DocumentException) cause;
				throw new ItfLocalException(e1.getMessage(), ItfLocalException.LOCAL_ERROR_CODE, 
						ItfLocalException.ILLEGAL_XML_MESSAGE, null);
			}else if (cause instanceof IOException){
				IOException e1 = (IOException) cause;
				throw new ItfLocalException(e1.getMessage(), ItfLocalException.LOCAL_ERROR_CODE, 
						ItfLocalException.IN_OUT_STREAM_ERROR, null);
			}
		}
		return respone;
	}
}
