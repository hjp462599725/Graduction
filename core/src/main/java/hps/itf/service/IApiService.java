package hps.itf.service;

import hps.itf.dto.ItfRespone;
import hps.itf.exception.ItfLocalException;
import hps.itf.exception.ItfRemoteException;

/**
 * 
 * @name IApiService
 * @description 请求的分发处理
 * @author jianping.huo@hand-china.com  2016年8月22日下午1:18:23
 * @version 1.0
 */
public interface IApiService {
	/**
	 * 
	 * 利用headerCode和lineCode进行请求的处理分发
	 * @param headerCode 系统代码
	 * @param lineCode 接口代码
	 * @param xml 报文数据
	 * @return ItfRespone 统一json信息
	 * @throws ItfLocalException 自定义异常
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws ItfRemoteException 
	 */
	public ItfRespone distribute(String sysCode, String itfCode, String url, String xml) throws ItfLocalException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, ItfRemoteException;
}
