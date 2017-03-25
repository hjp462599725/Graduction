package hps.fnd.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import com.hand.hap.cache.impl.HashStringRedisCache;

public class FunctionShortcutCache extends HashStringRedisCache<List<Map<String, String>>> {

	@Autowired
	RedisConnectionFactory redisConnectionFactory;

	@Override
	public void init() {
		setType(Map.class);
		strSerializer = getRedisTemplate().getStringSerializer();
		initLoad();
	}

	@Override
	public List<Map<String, String>> getValue(String key) {
		return (List<Map<String, String>>) super.getValue(key);
	}

	@Override
	public void setValue(String key, List<Map<String, String>> list) {
		super.setValue(key, (List<Map<String, String>>) list);
	}

	@Override
	public void remove(String key) {
		super.remove(key);
	}

	protected byte[] getKey(String category, String cacheName) {
		return strSerializer.serialize(new StringBuilder(category).append(":").append(cacheName).toString());
	}

	protected byte[] getField(String key) {
		return strSerializer.serialize(key);
	}

	@Override
	protected void initLoad() {

	}

	public String getShortcutValue(String str) {
		byte[] key = getKey("hap:cache", "function_shortcut");
		byte[] field = getField(str.toString());
		byte[] bytes = redisConnectionFactory.getConnection().hGet(key, field);
		String jsonString = strSerializer.deserialize(bytes);
		return jsonString;
	}
}
