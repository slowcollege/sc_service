package com.slow.college.service;

import javax.servlet.http.HttpServletRequest;

import com.slow.college.param.user.ClassStudentItem;
import com.slow.college.param.user.UserLoginItem;
import com.slow.college.request.UserReq;
import com.slow.college.response.ObjectResponse;

public interface UserService {
	
	ObjectResponse<UserLoginItem> login (HttpServletRequest request, UserReq req);
	
	ObjectResponse<ClassStudentItem> getClassStudent (HttpServletRequest request, UserReq req);
	
}
