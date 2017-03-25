package hps.fnd.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.message.IMessageConsumer;
import com.hand.hap.message.QueueMonitor;

import hps.fnd.dto.Message;
import hps.fnd.mapper.MessageManageMapper;

/**
 * @name MessageConsumer
 * @description 消息消费者 
 * @author yanjie.zhang@hand-china.com	2016年9月9日下午3:51:34
 * @version 1.0
 */
@Service
@QueueMonitor(queue="messageQueue")
public class MessageConsumer implements IMessageConsumer<Message>{
	
	@Autowired
	private MessageManageMapper messageManageMapper;
	
	@Autowired
	private MessageCache messageCache;
	
	/**
	 * 消息类型  优先级高
	 */
	public static final String messageTypeCodeHigh = "0";
	
	/**
	 * 消息类型  优先级中
	 */
	public static final String messageTypeCodeMiddle = "1";
	
	/**
	 * 消息类型  优先级低
	 */
	public static final String messageTypeCodeLow = "2";
	

	@Override
	public void onMessage(@StdWho final Message message, String queue) {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

		cachedThreadPool.execute(new Runnable() {
			public void run() {
				messageManageMapper.insertSelective(message);
				System.out.println("++++++++++++++++"+message.getReceiverId());
				messageCache.reLoad(message);
			}
		});

	}

}
