package hps.fnd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hps.fnd.dto.Message;
import hps.fnd.mapper.MessageManageMapper;
import hps.fnd.service.IMessageManageService;
import hps.fnd.service.MessageCache;

/**
 * @name MessageManageServiceImpl
 * @description
 * @author yanjie.zhang@hand-china.com	2016年9月21日下午14:20:29
 * @version 1.0
 */
@Service
@Transactional
public class MessageManageServiceImpl implements IMessageManageService{

    @Autowired
    private MessageManageMapper messageManageMapper;

    @Autowired
    private MessageCache messageCache;

    @Override
    public void updataStatus(Message message) {
        messageManageMapper.updataStatus(message.getMessageId());
        messageCache.reLoad(message);
    }

    @Override
    public int queryMessageCount(Message message) {
        return messageManageMapper.queryMessageCount(message);
    }
}
