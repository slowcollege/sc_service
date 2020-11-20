package com.slow.college.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.slow.college.param.user.UserLoginItem;
import com.slow.college.request.UserReq;
import com.slow.college.response.ObjectResponse;
import com.slow.college.service.UserService;

@RestController
@RequestMapping(value = "/api/user", produces = "application/json; charset=UTF-8")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ObjectResponse<UserLoginItem> login (HttpServletRequest request, UserReq req) {
		return null;
	}

}
