package cn.wang.custom.web.api.config;

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
 * @author 王叠 2019/5/5 14:24
 */
@Configuration
@PropertySource("classpath:jdbc/${spring.profiles.active}.properties")
public class DataSourceConfig {

    @Value("${jdbc.user}")
    private String name;

    @Value("${jdbc.pwd}")
    private String pwd;

    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.clzss}")
    private String clzss;


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
        source.setValidationQuery("select 1");
        source.init();
        return source;
    }


}

