package cn.wang.custom.user.module;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration(proxyBeanMethods = false)
@ComponentScan(
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = UserModuleApplication.class),
                //@ComponentScan.Filter(type = FilterType.REGEX, pattern = "cn.wang.custom.user.module.controller.*.*")
        })
@EntityScan("cn.wang.custom.user.module.entity")
public class UserModuleAutoConfig {
}
