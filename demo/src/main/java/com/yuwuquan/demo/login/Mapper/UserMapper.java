package com.yuwuquan.demo.login.Mapper;

import com.yuwuquan.demo.login.account.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

//@Mapper
public interface UserMapper {

	int updateByPrimaryKey(User user);

	int updateUserPwd(User user);

	@Select("select id,username from user")
	User selectUserByNameAndPwd(User tbUser);

	boolean isSupplier(Integer id);
}