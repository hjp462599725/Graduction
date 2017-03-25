package hps.fnd.service;

import hps.fnd.dto.Message;

/**
 * @name IMessageManageService
 * @description
 * @author yanjie.zhang@hand-china.com	2016年9月21日下午14:27:29
 * @version 1.0
 */
public interface IMessageManageService{
    /**
     * 修改消息成已读状态
     * @param message
     */
    void updataStatus(Message message);

    /**
     * 查看message的总数
     * @param message
     * @return
     */
    int queryMessageCount(Message message);
}
