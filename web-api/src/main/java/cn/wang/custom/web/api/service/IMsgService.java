package cn.wang.custom.web.api.service;

import cn.wang.custom.web.api.dtos.DtoMsgReq;
import cn.wang.custom.web.api.dtos.DtoMsgResp;

/**
 * 消息相关服务
 */
public interface IMsgService {
    /**
     * 发送消息
     * @param req
     */
    void sendMsg(DtoMsgReq req);

    /**
     * 查询消息
     * @param userMarkId 用户接收标识
     */
    DtoMsgResp queryMsg(String userMarkId);
}
