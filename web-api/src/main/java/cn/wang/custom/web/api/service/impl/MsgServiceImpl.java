package cn.wang.custom.web.api.service.impl;

import cn.wang.custom.web.api.dtos.DtoMsgReq;
import cn.wang.custom.web.api.dtos.DtoMsgResp;
import cn.wang.custom.web.api.service.IMsgService;
import cn.wang.custom.web.api.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MsgServiceImpl implements IMsgService {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void sendMsg(DtoMsgReq req) {

    }

    @Override
    public DtoMsgResp queryMsg(String userMarkId) {
        return null;
    }
}
