package cn.wang.custom.user.module.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 数据源配置
 *
 * @author  2019/5/5 14:24
 */
@Configuration
@PropertySource("classpath:jdbc/${spring.profiles.active}.properties")
public class DataSourceConfig {

    //数据源 用户名
    @Value("${jdbc.user}")
    private String name;
    //数据源 密码
    @Value("${jdbc.pwd}")
    private String pwd;
    //数据源 jdbc url
    @Value("${jdbc.url}")
    private String url;
    //数据源 jdbc 驱动实现类
    @Value("${jdbc.clzss}")
    private String clzss;
    //数据源 有效性校验sql
    @Value("${jdbc.validationQuery}")
    private String validationQuery;


    /**
     * 构建数据源
     *
     * @return 数据源对象
     * @throws SQLException sql异常
     * @author 王叠 2019/5/5 15:17
     */
    @Primary
    @Bean
    public DataSource buildDataSource() throws SQLException {
        DruidDataSource source = new DruidDataSource();
        source.setUsername(name);
        source.setPassword(pwd);
        source.setUrl(url);
        source.setDriverClassName(clzss);
        source.setValidationQuery(validationQuery);
        source.init();
        return source;
    }


}

