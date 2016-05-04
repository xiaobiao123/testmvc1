package com.user.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user.dao.UserDAO;
import com.user.pojo.User;
import com.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDao;

	public void setUserDaoImp(UserDAO userDao) {
		this.userDao = userDao;
	}

	public int insertUser(User user) {
		return userDao.insertUser(user);
	}

	@Transactional
	public int findNum() {
		User user=new User();
		user.setNickname("你好");
		user.setState(0);
		userDao.insertUser(user);
		return userDao.findNum();
	}
}
