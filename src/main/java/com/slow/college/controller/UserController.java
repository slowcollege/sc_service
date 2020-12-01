package com.slow.college.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.slow.college.exception.ScBizException;
import com.slow.college.param.response.BaseRsp;
import com.slow.college.request.TrainingReq;
import com.slow.college.request.UserReq;
import com.slow.college.service.UserService;

@RestController
@RequestMapping(value = "/api/v1/user", produces = "application/json; charset=UTF-8")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 登录
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public BaseRsp login (UserReq req) throws ScBizException  {
		return userService.login(req);
	}
	
	/**
	 * 读取初始化班级信息
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getClassStudent")
	public BaseRsp getClassStudent (UserReq req) throws ScBizException  {
		return userService.getClassStudent(req);
	}
	
	/**
	 * 读取待打卡列表
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getStudentTrainingTask")
	public BaseRsp getStudentTrainingTask (UserReq req) throws ScBizException  {
		return userService.getStudentTrainingTask(req);
	}
	
	/**
	 * 提交打卡接口
	 * 
	 * @param req
	 * @return
	 * @throws ScBizException
	 */
	@RequestMapping(value = "/submitStudentTraining", method = RequestMethod.POST)
	public BaseRsp submitStudentTraining (TrainingReq req) throws ScBizException {
		return userService.submitStudentTraining(req);
	}

}
