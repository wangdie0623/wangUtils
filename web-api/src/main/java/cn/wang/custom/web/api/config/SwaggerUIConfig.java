package cn.wang.custom.web.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger-ui配置
 * @author 2019-05-05 15:14
 */
@Configuration
@EnableSwagger2
public class SwaggerUIConfig {
    @Bean
    public Docket apiDocket(ApiInfo apiInfo) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .license("证书？")
                .title("主题")
                .description("描述")
                .version("版本号")
                .licenseUrl("证书地址？")
                .termsOfServiceUrl("服务条款地址")
                .build();
    }
}
