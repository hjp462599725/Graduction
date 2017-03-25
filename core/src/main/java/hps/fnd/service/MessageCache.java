package hps.fnd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.hand.hap.cache.impl.HashStringRedisCache;

import hps.fnd.dto.Message;
import hps.fnd.mapper.MessageManageMapper;


/**
 * @name MessageCache
 * @description 消息存储redis 
 * @author yanjie.zhang@hand-china.com	2016年9月9日下午2:01:46
 * @version 1.0
 */
@Component
public class MessageCache extends HashStringRedisCache<List<Message>>{

	@Autowired
	private MessageManageMapper messageManageMapper;
	
	@Autowired
	RedisConnectionFactory redisConnectionFactory;

	private RedisTemplate<String, String> redisTemplate;

	private StringRedisSerializer stringSerializer = new StringRedisSerializer();

	@Override
	public void setValue(String key, List<Message> list) {
		super.setValue(key, (List<Message>) list);
	}

	//总共的数量
	private Integer total;


	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * 初始化
	 */
	@Override
	public void init() {
		setType(Map.class);
		initLoadHash();
		initLoadSortedSet();
	}


	/**
	 * 初始化站内消息到redis
	 */
	public void initLoadHash(){
		Message message = new Message();
		List<Message> messages = messageManageMapper.queryMessage(message);
		Map<String,List<Message>> receiverMap = new HashMap<>();
		for(Message mes:messages){
			String field = "message:"+mes.getReceiverId().toString() + mes.getStatus();
			this.getRedisTemplate().execute((RedisCallback)(connection) -> {
				connection.del(new byte[][]{this.stringSerializer.serialize(field)});
				return null;
			});
			if (receiverMap.containsKey(field)){
				List<Message> mess= receiverMap.get(field);
				mess.add(mes);
				receiverMap.put(field,mess);
			}else{
				List<Message> mess = new ArrayList<>();
				mess.add(mes);
				receiverMap.put(field,mess);
			}
		}
		for(Map.Entry<String,List<Message>> entry:receiverMap.entrySet()){
			this.getRedisTemplate().execute((RedisCallback)(connection) -> {
				byte[] keyBytes = stringSerializer.serialize(entry.getKey());
				List<Message> messageList = entry.getValue();
				for(Message m:messageList){
					byte[] fieldBytes = stringSerializer.serialize(m.getMessageId().toString());
					byte[] valueKeyBytes = this.stringSerializer.serialize(m.toString());
					connection.hSet(keyBytes,fieldBytes,valueKeyBytes);
				}

				return null;
			});
		}
	}

	/**
	 * 初始化站内消息到redis(存到sortedSet用于分页)
	 */
	public void initLoadSortedSet(){
		Message message = new Message();
		List<Message> messages = messageManageMapper.queryMessage(message);
		Map<String,List<Message>> receiverMap = new HashMap<>();
		for(Message mes:messages){
			String field = "sortedSet:"+mes.getReceiverId().toString()+mes.getStatus();
			this.getRedisTemplate().execute((RedisCallback)(connection) -> {
				connection.del(new byte[][]{this.stringSerializer.serialize(field)});
				return null;
			});
			if (receiverMap.containsKey(field)){
				List<Message> mess= receiverMap.get(field);
				mess.add(mes);
				receiverMap.put(field,mess);
			}else{
				List<Message> mess = new ArrayList<>();
				mess.add(mes);
				receiverMap.put(field,mess);
			}
		}
		for(Map.Entry<String,List<Message>> entry:receiverMap.entrySet()){
			this.getRedisTemplate().execute((RedisCallback)(connection) -> {
				byte[] keyBytes = stringSerializer.serialize(entry.getKey());
				List<Message> messageList = entry.getValue();
				for(Message m:messageList){
					byte[] fieldBytes = stringSerializer.serialize(m.getMessageId().toString());
					byte[] valueKeyBytes = this.stringSerializer.serialize(m.toString());
						connection.zAdd(keyBytes,m.getMessageId(),fieldBytes);
				}
				return null;
			});
		}
	}



	/**
	 * 重新加载数据到redis
	 * @param
	 */
	public void reLoad(Message message) {
		this.getRedisTemplate().execute((RedisCallback)(connection) -> {
			connection.del(new byte[][]{this.stringSerializer.serialize("message:"+message.getReceiverId()+message.getStatus())});
			return null;
		});
		initLoadHash();
		initLoadSortedSet();
	}

	/**
	 * 获取redis的Key
	 * @param category
	 * @param cacheName
	 * @return
	 */
	protected byte[] getKey(String category, String cacheName) {
		return stringSerializer.serialize(new StringBuilder(category).append(":").append(cacheName).toString());
	}

	/**
	 * 获取redis的Field
	 * @param key
	 * @return
	 */
	protected byte[] getField(String key) {
		return stringSerializer.serialize(key);
	}


	/**
	 * 获取redis有序集合的messageId,用于分页
	 * @param key  sortedSet类型key值
	 * @param offset 从哪个位置出发
	 * @param count  取数的数量
	 * @return		LinkedHashSet集合
	 */
	public LinkedHashSet<String> zrevrangebyscore(String key,int offset,int count){
		return this.getRedisTemplate().execute((RedisCallback<LinkedHashSet<String>>)(connection) -> {
			LinkedHashSet<String> result = null;
			byte[] keyBytes = stringSerializer.serialize("sortedSet:"+key);
			Set<byte[]> messageIdBytes = connection.zRevRange(keyBytes,offset,count);
			LinkedHashSet<String> messageIdSet = new LinkedHashSet<String>();
			for(byte[] b:messageIdBytes){
				messageIdSet.add(stringSerializer.deserialize(b));
			}
			return messageIdSet;
		});
	};

	/**
	 * 从redis里面获取消息
	 * @param message
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<Map<String,String>> getMessagePage(Message message,int page,int pageSize){

		LinkedHashSet<String> messageIds = this.zrevrangebyscore(message.getReceiverId()+message.getStatus(),page,pageSize);
		StringBuilder sb = new StringBuilder();
		List list = new ArrayList<>();

		for(String m:messageIds){
			Long receiverId = message.getReceiverId();
			String status = message.getStatus();
			byte[] key = getKey("message", receiverId + status);
			byte[] field = getField(m);
			byte[] bytes = redisConnectionFactory.getConnection().hGet(key, field);
			String jsonString = stringSerializer.deserialize(bytes);

			if(jsonString!=null){
				String subString =  jsonString.substring(1, jsonString.length()-1);
				String[] splitString = subString.split(",");
				Map<String,String> map = new HashMap<String, String>();
				for(String s:splitString){
					String[] str = s.split(":");
					if(str.length>2){
						StringBuilder stringBuilder = new StringBuilder();
						for(int i = 1;i <str.length;i++){
							stringBuilder.append(str[i]);	
						}
						String sbStr = stringBuilder.toString();
						if("content".equals(str[0].substring(1,str[0].length()-1))){
							sbStr = sbStr.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
							sbStr = sbStr.replaceAll("[(/>)<]", "");
						}
						map.put(str[0].substring(1,str[0].length()-1), sbStr.substring(1,sbStr.length()-1));
					}else{
						if("content".equals(str[0].substring(1,str[0].length()-1))){
							str[1] = str[1].replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
							str[1] = str[1].replaceAll("[(/>)<]", "");
						}
						map.put(str[0].substring(1,str[0].length()-1), str[1].substring(1,str[1].length()-1));
					}

				}
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 显示消息详细
	 * @param message
	 * @return
	 */
	public List<Map<String,String>> getMessageDetail(Message message){

		Long receiverId = message.getReceiverId();
		String status = message.getStatus();
		byte[] key = getKey("message", receiverId + status);
		byte[] field = getField(message.getMessageId().toString());
		byte[] bytes = redisConnectionFactory.getConnection().hGet(key, field);
		String jsonString = stringSerializer.deserialize(bytes);
		List list = new ArrayList<>();
		if(jsonString!=null){
			String subString =  jsonString.substring(1, jsonString.length()-1);
			String[] splitString = subString.split(",");
			Map<String,String> map = new HashMap<String, String>();
			for(String s:splitString){
				String[] str = s.split(":");
				if(str.length>2){
					StringBuilder stringBuilder = new StringBuilder();
					for(int i = 1;i <str.length;i++){
						stringBuilder.append(str[i]);
						stringBuilder.append(":");
					}
					String sbStr = stringBuilder.toString();
					map.put(str[0].substring(1,str[0].length()-1), sbStr.substring(1,sbStr.length()-1));
				}else{
					map.put(str[0].substring(1,str[0].length()-1), str[1].substring(1,str[1].length()-1));
				}
			}
			list.add(map);
		}
		return list;
	}
}
