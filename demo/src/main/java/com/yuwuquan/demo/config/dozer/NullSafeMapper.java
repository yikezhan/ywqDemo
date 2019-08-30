package com.yuwuquan.demo.config.dozer;

import lombok.AllArgsConstructor;
import org.dozer.Mapper;
import org.dozer.MappingException;

/**
 * 可以接收null对象的map
 * 
 * @author scm
 *
 */
@AllArgsConstructor
public class NullSafeMapper implements Mapper {
	
	private Mapper mapper;
	
	@Override
	public void map(Object arg0, Object arg1, String arg2)
			throws MappingException {
		if(arg0!=null && arg1 != null){
			mapper.map(arg0, arg1,arg2);
		}
	}
	
	@Override
	public <T> T map(Object arg0, Class<T> arg1, String arg2)
			throws MappingException {
		if(arg0!=null){
			return mapper.map(arg0, arg1, arg2);
		}else{
			return null;
		}
	}
	
	@Override
	public void map(Object arg0, Object arg1) throws MappingException {
		if(arg0!=null && arg1 != null){
			mapper.map(arg0, arg1);
		}
	}
	
	@Override
	public <T> T map(Object arg0, Class<T> arg1) throws MappingException {
		if(arg0!=null){
			return mapper.map(arg0, arg1);
		}else{
			return null;
		}
	}
}
