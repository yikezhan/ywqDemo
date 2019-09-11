package com.yuwuquan.demo.controller;

import com.google.common.collect.Lists;
import com.yuwuquan.demo.config.DozerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 含有一些基本方法，包括dozer（用于自动的完成相同属性名称的复制）等。
 */
@RestController
@Slf4j
public class BaseController {
	
//	@Autowired
//	protected ExcelServices excelServices;

	
	@Autowired
	public Environment env;
	
	@Autowired
	private DozerConfig.DozerFactory factory;

	public Logger getLogger(){
		return log;
	}
	public Mapper getDozerMapper(){
		return factory.getMapper();
	}
	protected <T> List<T> mapList(Collection<?> sourceList,Class<T> targetClazz){
		if(CollectionUtils.isEmpty(sourceList)) {
			return null;
		}
		List<T> list = Lists.newArrayList();
		for (Object sourceObj : sourceList) {
			T t = getDozerMapper().map(sourceObj, targetClazz);
			list.add(t);
		}
		return list;
	}
}