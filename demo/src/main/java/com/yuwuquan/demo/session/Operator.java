package com.yuwuquan.demo.session;

import com.yuwuquan.demo.orm.model.SysUserInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lenovo
 *
 */
@Data
public class Operator extends SysUserInfo implements Serializable {
	
	private static final long serialVersionUID = -7884488850361068157L;
	
	public final static Operator EMPTY = new Operator();
	
	static{
		EMPTY.setId(-1l);
		EMPTY.setUnique_id("-1");
		EMPTY.setPhone("unkonw");
		EMPTY.setUsername("unkonw");
		EMPTY.setName("unknow");
		EMPTY.setSex(0);
		EMPTY.setRemoteIp("127.0.0.1");
		EMPTY.setAccessToken("-1");
	}
	
	public static Operator getEmpty(){
		Operator empty = new Operator();

		EMPTY.setId(-1l);
		EMPTY.setUnique_id("-1");
		EMPTY.setPhone("unkonw");
		EMPTY.setUsername("unkonw");
		EMPTY.setName("unknow");
		EMPTY.setSex(0);
		EMPTY.setRemoteIp("127.0.0.1");
		EMPTY.setAccessToken("-1");
		return empty;
	}
	private String accessToken;
	private String remoteIp;
    
}
