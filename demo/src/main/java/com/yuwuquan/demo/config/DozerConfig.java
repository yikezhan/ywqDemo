package com.yuwuquan.demo.config;

import com.yuwuquan.demo.config.dozer.NullSafeMapper;
import lombok.Setter;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class DozerConfig {

	@Resource(name= "DozerBeanMapperFactory")
	private Mapper mapper;
    
	@Bean(name="dozerSysMapper")
    public DozerFactory getDozerFactory() throws Exception {
    	DozerFactory factory = new DozerFactory();
    	factory.setMapper(mapper);
    	return factory;
    }
    
    public class DozerFactory {

		/*
		 * mydal : SonarQube issue-fix , 2019-08-08
		 * http://sonar.lvmama.com/project/issues?id=com.tims%3Atims-common&resolved=false&severities=BLOCKER&types=BUG
		 * 使用 volatile JVM 不可重新排序 解决 Double-Checked Locking 不生效问题
		 */

		@Setter
    	private Mapper mapper;
    	
    	/**
    	 * 支持映射源对象检测的mapper
    	 */
    	private volatile Mapper nullSafeMapper = null;
    	
    	/**
    	 * 返回可以接收null对象的map
    	 * @return
    	 */
    	public Mapper getMapper(){
    		if(nullSafeMapper==null) {
				synchronized (this) {
					if (nullSafeMapper == null) {
						nullSafeMapper = new NullSafeMapper(mapper);
					}
				}
			}
    		return nullSafeMapper;
    	}
    }
}