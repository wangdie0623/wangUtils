package cn.wang.custom.feign;

import cn.wang.custom.feign.back.UserModuleFallbackFactory;
import cn.wang.custom.query.UserQuery;
import cn.wang.custom.vo.AccountVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "user-module",
        path = "account",
        fallbackFactory = UserModuleFallbackFactory.class)
public interface UserModuleFeign {

    @PostMapping("/userInfo")
    public AccountVo getUserFullInfo(@RequestBody UserQuery query);
}
