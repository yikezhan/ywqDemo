package com.yuwuquan.demo.security.auth;

import lombok.Data;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 规则
 * 
 * @author scm
 *
 */
@Data
public class AccessRule {
	
	public static List<AccessRule> defaultRules;
	
	static{
		//设置默认全局过滤规则
		AccessRule orderRule = new AccessRule();
		orderRule.setColumn("fk_user_create");
		orderRule.setResource("order_db.`order`");
		orderRule.setResourceAlias("_Order");
		String orderCompayRule ="#{'EXISTS (select _usr.id,_dep.fk_company from user_db.employee _emp,user_db.department _dep,user_db.`user` _usr where _emp.fk_department=_dep.id and _usr.fk_employee=_emp.id"
				+" and _usr.id =_Order.fk_user_create  and _dep.fk_company= '}#{current.company.id}#{')'}";
		orderRule.setExpression(orderCompayRule); //只能看到相同公司的订单 
		orderRule.setName("订单默认规则");
		orderRule.setUrl("/sys/order/**");
		
		AccessRule productRule = new AccessRule();
		productRule.setExpression("#{'_Product.fk_company='}#{current.company.id}"); //产品所属公司等于用户公司
		productRule.setColumn("fk_company");
		productRule.setResource("product_db.product");
		productRule.setResourceAlias("_Product");
		productRule.setName("产品默认规则");
		productRule.setUrl("/sys/**");
		
		List<AccessRule> rules = new ArrayList<>();//不设置全局数据权限拦截
		//rules.add(orderRule);
		//rules.add(productRule);
		
		defaultRules = Collections.unmodifiableList(rules);
		
	}
	
	
	
	private static final ParserContext parserContext = new ParserContext() {
        @Override  
         public boolean isTemplate() {  
            return true;  
        }  
        @Override  
        public String getExpressionPrefix() {  
            return "#{";  
        }  
        @Override  
        public String getExpressionSuffix() {  
            return "}";  
        }  
    };
	public String getAuthSql(AuthContext authCtx,String alais){
		ExpressionParser parser = new SpelExpressionParser();
		//这里需要使用表达式解析
		String sql1 = expression.replaceAll(resourceAlias, alais);//先替换资源别名
		EvaluationContext ctx2 = new StandardEvaluationContext();
		((StandardEvaluationContext) ctx2).setRootObject(authCtx);
		Expression exp = parser.parseExpression(sql1,parserContext);
		return exp.getValue(ctx2).toString();
	}
	
	/**
	 * 规则名称 
	 */
	private String name;
	
	/**
	 * 资源URL 
	 */
	private String url;
	
	/**
	 * 资源对应表
	 */
	private String resource;
	
	/**
	 * 资源表别名（用于写表达式） 
	 */
	private String resourceAlias;
	
	/**
	 * 表关联字段
	 */
	private String column;
	
	/**
	 * 表达式 
	 */
	private String expression;

}
