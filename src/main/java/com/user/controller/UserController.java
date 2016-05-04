package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.user.pojo.User;
import com.user.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping("index")
	public String index() {
		User user =new User();
		user.setNickname("还");
		user.setState(0);
		userService.insertUser(user);
		return "index";
	}

	@RequestMapping("add")
	public String findNum() {
		System.out.println(userService.findNum());
		ModelAndView mv=new ModelAndView();
		return "index";
	}
}
