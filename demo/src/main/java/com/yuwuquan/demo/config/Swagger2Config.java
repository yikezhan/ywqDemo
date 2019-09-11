package com.yuwuquan.demo.config;

import com.github.pagehelper.util.StringUtil;
import com.sun.tools.javac.util.StringUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ConditionalOnExpression("${swagger.enabled:true}")
public class Swagger2Config {

	@Autowired
	private Environment env;

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("ywq","http://localhost:8080","734041370@qq.com");
		String path = env.getProperty("server.servlet.context-path")+"/swagger";
		if(StringUtil.isEmpty(path)) {
			path="/demo";
		}
		return new ApiInfoBuilder()
				.title("测试Swagger的使用")
					.description("这是一个测试Swagger的使用的页面")
				.termsOfServiceUrl("http://localhost:8080")
				.contact(contact)
				.version("1.0")
				.build();
	}
}
