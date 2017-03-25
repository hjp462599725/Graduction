package hps.fnd.mapper;



import java.util.List;

import com.hand.hap.mybatis.common.Mapper;

import hps.fnd.dto.Message;

/**
 * @name MessageMapper
 * @description 消息推送
 * @author yanjie.zhang@hand-china.com	2016年9月9日下午5:29:43
 * @version 1.0
 */
public interface MessageManageMapper extends Mapper<Message>{
	/**
	 * 查询消息
	 * @param message
	 * @return
	 */
	List<Message> queryMessage(Message message);

	/**
	 * 修改消息成已读状态
	 * @param messageId
	 */
	void updataStatus(Long messageId);

	/**
	 * 查找message的总数
	 * @param message
	 * @return
	 */
	int queryMessageCount(Message message);
}
