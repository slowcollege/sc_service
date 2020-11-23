package com.slow.college.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slow.college.mapper.StudentMapper;
import com.slow.college.model.Student;
import com.slow.college.param.user.UserDataItem;
import com.slow.college.param.user.UserLoginItem;
import com.slow.college.request.UserReq;
import com.slow.college.response.ObjectResponse;
import com.slow.college.response.ResponseCode;
import com.slow.college.service.UserService;
import com.slow.college.utils.DateUtil;
import com.slow.college.utils.EncryptUtil;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	private StudentMapper studentMapper;
	
	public ObjectResponse<UserLoginItem> login (HttpServletRequest request, UserReq req) {
		log.info("调用接口login开始");
		long startTime = System.currentTimeMillis();
		ObjectResponse<UserLoginItem> response = new ObjectResponse<>();
		try {
			if (req.getMobile() == null || req.getMobile().trim().length() == 0 || 
				req.getPassword() == null || req.getPassword().trim().length() == 0) {
				response.setCode(ResponseCode.CODE_0);
				response.setMessage("手机号或密码为空");
				return response;
			}
			Student s = studentMapper.searchStudentByPhone(req.getMobile().trim());
			if (s == null) {
				response.setCode(ResponseCode.CODE_0);
				response.setMessage("该手机号尚未注册");
				return response;
			}
			if (s.getPassword() == null || 
				!req.getPassword().trim().equals(s.getPassword().trim())) {
				response.setCode(ResponseCode.CODE_1);
				response.setMessage(ResponseCode.MSG_1);
				return response;
			}
			long time = System.currentTimeMillis();
			String token = EncryptUtil.GetMD5Code(s.getName().trim() + time + "json");
			s.setToken(token);
			studentMapper.updateStudentById(s.getId(), s.getToken());
			response.setCode(ResponseCode.CODE_1);
			response.setMessage(ResponseCode.MSG_1);
			UserLoginItem res = new UserLoginItem();
			res.setToken(token);
			res.setName(s.getName());
			long createTime = DateUtil.parseDate(s.getCreateTime(), DateUtil.YYYY_MM_DDHHMMSS).getTime();
			int days = (int) (time - createTime) / (1000 * 60 * 60 * 24);
			res.setTrainingDays(days);
			response.setResult(res);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(ResponseCode.CODE_0);
			response.setMessage("登录失败");
		} 
		long endTime = System.currentTimeMillis();
		log.info("调用接口login结束，耗时：" + (endTime - startTime) + "ms");
		return response;
	}
	
	public ObjectResponse<UserDataItem> searhUserData (HttpServletRequest request, UserReq req) {
		log.info("调用接口login开始");
		long startTime = System.currentTimeMillis();
		ObjectResponse<UserDataItem> response = new ObjectResponse<>();
		try {
			if (req.getToken() == null || req.getToken().trim().length() == 0) {
				response.setCode(ResponseCode.CODE_90);
				response.setMessage(ResponseCode.MSG_90);
				return response;
			}
			Student s = studentMapper.searchStudentByToken(req.getToken().trim());
			if (s == null) {
				response.setCode(ResponseCode.CODE_90);
				response.setMessage(ResponseCode.MSG_90);
				return response;
			}
			if (s.getPassword() == null || 
				!req.getPassword().trim().equals(s.getPassword().trim())) {
				response.setCode(ResponseCode.CODE_0);
				response.setMessage("密码错误");
				return response;
			}
			UserDataItem res = new UserDataItem();
			//TODO res
			response.setResult(res);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(ResponseCode.CODE_0);
			response.setMessage(ResponseCode.MSG_0);
		} 
		long endTime = System.currentTimeMillis();
		log.info("调用接口login结束，耗时：" + (endTime - startTime) + "ms");
		return response;
	}

}
