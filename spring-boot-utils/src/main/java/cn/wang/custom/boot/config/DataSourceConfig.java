package cn.wang.custom.boot.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 数据源配置
 *
 * @author  2019/5/5 14:24
 */
@Configuration(proxyBeanMethods = false)
public class DataSourceConfig {

    /**
     * 构建数据源
     *
     * @return 数据源对象
     * @throws SQLException sql异常
     * @author 王叠 2019/5/5 15:17
     */
    @Bean
    //有该对象
    @ConditionalOnBean(DataSourceProBean.class)
    //该对象只有一个或有主要
    @ConditionalOnSingleCandidate(DataSourceProBean.class)
    public DataSource buildDataSource(DataSourceProBean dataSourceProBean) throws SQLException {
        DruidDataSource source = new DruidDataSource();
        source.setUsername(dataSourceProBean.getName());
        source.setPassword(dataSourceProBean.getPwd());
        source.setUrl(dataSourceProBean.getUrl());
        source.setDriverClassName(dataSourceProBean.getClzss());
        source.setValidationQuery(dataSourceProBean.getValidationQuery());
        source.init();
        return source;
    }

}

