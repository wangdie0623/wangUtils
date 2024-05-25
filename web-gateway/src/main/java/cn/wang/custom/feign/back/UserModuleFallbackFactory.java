package cn.wang.custom.feign.back;

import cn.wang.custom.feign.UserModuleFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserModuleFallbackFactory implements FallbackFactory <UserModuleFeign>{
    @Override
    public UserModuleFeign create(Throwable cause) {
        log.error("feign远程服务调用异常", cause);
        return null;
    }
}
