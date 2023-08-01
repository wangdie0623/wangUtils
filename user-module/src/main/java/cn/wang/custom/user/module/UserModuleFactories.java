package cn.wang.custom.user.module;


import cn.wang.custom.user.module.aspects.ValidationAspect;
import cn.wang.custom.user.module.aspects.WebLogAspect;
import cn.wang.custom.user.module.config.DataSourceConfig;
import cn.wang.custom.user.module.config.RedisConfig;
import cn.wang.custom.user.module.config.SwaggerUIConfig;
import cn.wang.custom.user.module.config.WebConfig;
import cn.wang.custom.user.module.dao.impl.CommonDaoImpl;
import cn.wang.custom.user.module.dao.impl.VuePageCommonDaoImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


@Configuration
//指定hibernate 数据库对象管理范围
@EntityScan("cn.wang.custom.user.module.entity")
//初始化该package下所有bean
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = {ValidationAspect.class, WebLogAspect.class, DataSourceConfig.class, RedisConfig.class,
                SwaggerUIConfig.class, WebConfig.class, CommonDaoImpl.class, VuePageCommonDaoImpl.class}))

public class UserModuleFactories{

}
