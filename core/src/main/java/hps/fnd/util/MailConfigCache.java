package hps.fnd.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import com.hand.hap.cache.impl.HashStringRedisCache;

import hps.fnd.dto.MailConfig;
import hps.fnd.mapper.MailConfigMapper;

/**
 * @name MailConfigCache
 * @description
 * @author yanjie.zhang@hand-china.com	2016年9月13日下午9:27:35
 * @version 1.0
 */
@Component
public class MailConfigCache extends HashStringRedisCache<MailConfig>{

	@Autowired
	RedisConnectionFactory redisConnectionFactory;
	
	@Autowired
	private MailConfigMapper mailConfigMapper;
	
	/**
	 * 初始化
	 */
	@Override
	public void init() {
		setType(Map.class);
		strSerializer = getRedisTemplate().getStringSerializer();
		initLoad();
	}
	
	
	/**
	 * 初始化加载数据到redis
	 */
	protected void initLoad() {
		MailConfig mailConfig = new MailConfig();
		List<MailConfig> mails = mailConfigMapper.selectMailConfig(mailConfig);
		for(MailConfig m:mails){
			setValue(m.getMailConfigCode(), m);
		}
	}
	
	/**
	 * 重新加载数据到redis
	 * @param message  messageDTO
	 */
	public void reLoad(MailConfig mailConfig) {
		List<MailConfig> mails = mailConfigMapper.selectMailConfig(mailConfig);
		for(MailConfig m:mails){
			setValue(m.getMailConfigCode(), m);
		}
	}
	
	/**
	 * 获取redis的Key
	 * @param category
	 * @param cacheName
	 * @return
	 */
	protected byte[] getKey(String category, String cacheName) {
		return strSerializer.serialize(new StringBuilder(category).append(":").append(cacheName).toString());
	}
	
	/**
	 * 获取redis的Field
	 * @param key
	 * @return
	 */
	protected byte[] getField(String key) {
		return strSerializer.serialize(key);
	}
	
	/**
	 * 通过邮件配置代码获取消息配置
	 * @param mailConfigCode 邮件配置代码
	 * @return
	 */
	public MailConfig getMailConfig(String mailConfigCode){
		
		Map<String,String> map = new HashMap<String,String>();
		
		byte[] key = getKey("hap:cache", "mailConfig");
		byte[] field = getField(mailConfigCode);
		byte[] bytes = redisConnectionFactory.getConnection().hGet(key, field);
		String jsonString = strSerializer.deserialize(bytes);
		Object obj =  stringToObject(jsonString);
		jsonString = jsonString.substring(1, jsonString.length()-2);
		String[] jsonArray = jsonString.split(",");
		for(String s:jsonArray){
			String[] mapStr = s.split(":");
			if(mapStr[1].length()>1){
				
			}
			map.put(mapStr[0].substring(1, mapStr[0].length()-1), mapStr[1].substring(1, mapStr[1].length()-1));
		}
		MailConfig mailConfig = new MailConfig();
		mailConfig.setHost(map.get("host"));
		mailConfig.setUserName(map.get("userName"));
		mailConfig.setPassword(map.get("password"));
		mailConfig.setMailAddress(map.get("mailAddress"));

		return mailConfig;
	}
	
}
