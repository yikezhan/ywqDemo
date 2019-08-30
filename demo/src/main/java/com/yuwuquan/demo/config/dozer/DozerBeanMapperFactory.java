package com.yuwuquan.demo.config.dozer;

import com.github.dozermapper.spring.DozerBeanMapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
public class DozerBeanMapperFactory {
    @Bean(name = "DozerBeanMapperFactory")
    public DozerBeanMapperFactoryBean createDozerFactory() throws Exception {
        DozerBeanMapperFactoryBean factory = new DozerBeanMapperFactoryBean();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //这里看代码也可以没有这些配置文件。传空进去也没关系。有配置文件可以配置一些不同变量名的映射。比如a映射到b。
        Resource[] appMappingConfig = resolver.getResources("classpath*:dozer/sys/**/*Mapping.xml");
        factory.setMappingFiles(appMappingConfig);
        return factory;
    }
}
