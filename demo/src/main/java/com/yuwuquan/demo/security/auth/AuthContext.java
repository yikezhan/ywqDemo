package com.yuwuquan.demo.security.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
public class AuthContext {

	private List<AccessRule> rules;
	
	public AuthContext(Current current) {
		this.current=current;
	}
	
	@Setter
	@Getter
	private Current current;
	
	/**
	 * SQL数据资源权限验证 
	 */
	@Setter
	@Getter
	private boolean authEnable=false;
	
	/**
	 * Sql操作记录 
	 */
	@Setter
	@Getter
	private boolean sqlLogEnable=true;
	
	/**
	 * 批量操作日志记录
	 */
	@Setter
	@Getter
	private boolean batchInsertEnable = false;
	
	/**
	 *  是否严格校验
	 */
	@Setter
	@Getter
	private boolean strict = false;
	
	public void reset(){
		if(rules!=null){
			rules.clear();
			rules = null;
		}
		batchInsertEnable = false;
		current = null;
	}
	
	/**
	 * 返回不可修改的规则集合
	 * @return
	 */
	public List<AccessRule> getRules(){
		if(CollectionUtils.isEmpty(rules)){
			return Collections.emptyList();
		}else{
			return Collections.unmodifiableList(rules);
		}
	}
	
	public void setRules(List<AccessRule> rules){
		if(this.rules!=null){
			this.rules.clear();
			this.rules = null;
		}
		this.rules = rules;
	}
	
	 
	
	
}
