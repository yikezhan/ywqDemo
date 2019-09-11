package com.yuwuquan.demo.login.service.common;

import com.yuwuquan.demo.config.DozerConfig;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseService implements DozerSupport{
	

	@Autowired
	protected DozerConfig.DozerFactory factory;

	@Override
	public Mapper getDozerMapper() {
		return factory.getMapper();
	}

}
