package hps.itf.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import hps.itf.exception.ItfRemoteException;

/**
 * 
 * @name HttpUtil
 * @description 用于请求WebService的工具类
 * @author jianping.huo@hand-china.com  2016年8月22日下午1:37:32
 * @version 1.0
 */
public class HttpUtil {
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	private static String CONTENT_TYPE = "application/xml";
	
	/**
	 * 与WebService建立连接，发送请求，得到返回信息
	 * @param strUrl URL地址
	 * @param params 参数
	 * @param method 请求类型
	 * @param contenttype 请求内容格式
	 * @return Map<String, Object> 返回信息和statusCode
	 * @throws IOException  
	 * @throws ItfRemoteException 
	 */
	public static Map<String, Object> net(String strUrl, String params, String method, String contenttype) 
			throws IOException, ItfRemoteException {
		Map<String, Object> map = new HashMap<>();
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		StringBuffer sb = new StringBuffer();
		URL url = new URL(strUrl);
		conn = (HttpURLConnection) url.openConnection();
		if (method == null || method.equals("GET")) {
			conn.setRequestMethod("GET");
		} else {
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
		}
		conn.setRequestProperty("Content-Type", contenttype != null ? contenttype : CONTENT_TYPE);
		conn.setUseCaches(false);
		conn.setConnectTimeout(DEF_CONN_TIMEOUT);
		conn.setReadTimeout(DEF_READ_TIMEOUT);
		conn.setInstanceFollowRedirects(false);
		conn.connect();
		if (params != null && method.equals("POST")) {
			try {
				DataOutputStream out = new DataOutputStream(conn.getOutputStream());
				// Java中的char是16位的，一个char存储一个中文字符，直接用writeBytes方法转换会变为8位，直接导致高8位丢失。从而导致中文乱码。
				out.write(params.getBytes());
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		InputStream is = null;
		try {
			is = conn.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			map.put("statusCode", conn.getResponseCode());
			map.put("exception", e.getMessage());
			throw new ItfRemoteException(map);
		}
		reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
		String strRead = null;
		while ((strRead = reader.readLine()) != null) {
			sb.append(strRead);
		}
		rs = sb.toString();
		map.put("result", rs);
		
		 if (reader != null) {
			 reader.close();
		 }
		 if (conn != null) {
			 conn.disconnect();
		 }
		return map;
	}

	/*
	 * //将map型转为请求参数型 public static String urlencode(Map<String,String> data) {
	 * StringBuilder sb = new StringBuilder(); for (Map.Entry i :
	 * data.entrySet()) { try {
	 * sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+
	 * "","UTF-8")).append("&"); } catch (UnsupportedEncodingException e) {
	 * e.printStackTrace(); } } return sb.toString(); }
	 */
}
