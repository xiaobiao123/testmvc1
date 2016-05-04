package com.user.dao;

import com.user.pojo.User;

public interface UserDAO {

	/**
	 * 
	 * 添加新用户
	 * 
	 * @param user
	 * 
	 * @return
	 */

	public int insertUser(User user);

	public int findNum();

}
