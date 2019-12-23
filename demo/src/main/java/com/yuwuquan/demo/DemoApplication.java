package com.yuwuquan.demo;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.dubbo.spring.boot.context.event.DubboBannerApplicationListener;
import com.yuwuquan.demo.util.common.NetUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan({"com.yuwuquan.demo.orm.dao","com.yuwuquan.demo.util","com.yuwuquan.demo.dubbo.provider.impl"})
@EnableDubbo(scanBasePackages = {"com.yuwuquan.demo.dubbo.provider.impl"})
public class DemoApplication extends SpringBootServletInitializer {
    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
    public static String localIP = NetUtil.getLocalIp();

    public static void main(String[] args) {
        DubboBannerApplicationListener.setBANNER_MODE(Banner.Mode.OFF);
        SpringApplication.run(DemoApplication.class, args);
        logger.info("localIP:"+localIP+",YWQ's program is running...");
    }
    /**
     * 为了打包springboot项目
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
