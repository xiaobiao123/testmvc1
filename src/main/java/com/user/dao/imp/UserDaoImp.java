package com.user.dao.imp;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.user.dao.UserDAO;
import com.user.pojo.User;

@Repository
public class UserDaoImp implements UserDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public int insertUser(User user) {
		user.setNickname("你好");
		user.setState(0);
		return	sqlSessionTemplate.insert("UserDAO.insertUser", user);
	}

	public int findNum() {
		int i=0;
		i= sqlSessionTemplate.selectOne("UserDAO.findUser"); 
		return i;
	}

}
