package com.slow.college.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slow.college.mapper.StudentMapper;
import com.slow.college.mapper.TrainingTaskMapper;
import com.slow.college.model.Student;
import com.slow.college.param.user.ClassStudentItem;
import com.slow.college.param.user.StudentClassItem;
import com.slow.college.param.user.StudentTrainingsItem;
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
	
	@Autowired
	private TrainingTaskMapper trainingTaskMapper;
	
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
	
	public ObjectResponse<ClassStudentItem> getClassStudent (HttpServletRequest request, UserReq req) {
		log.info("调用接口getClassStudent开始");
		long startTime = System.currentTimeMillis();
		ObjectResponse<ClassStudentItem> response = new ObjectResponse<>();
		try {
			if (req.getToken() == null || req.getToken().trim().length() == 0) {
				response.setCode(ResponseCode.CODE_90);
				response.setMessage(ResponseCode.MSG_90);
				return response;
			}
			if (req.getDate() == null || req.getDate().trim().length() == 0) {
				req.setDate(DateUtil.parseString(new Date(), DateUtil.YYYY_MM_DD));
			} else {
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
				Date d = ft.parse(req.getDate().trim());
				if (d.getTime() - startTime < 0) {
					response.setCode(ResponseCode.CODE_0);
					response.setMessage("日期格式错误");
					return response;
				} else {
					req.setDate(req.getDate().trim());
				}
			}
			Student s = studentMapper.searchStudentByToken(req.getToken().trim());
			if (s == null) {
				response.setCode(ResponseCode.CODE_90);
				response.setMessage(ResponseCode.MSG_90);
				return response;
			}
			ClassStudentItem res = studentMapper.searchUserDataItemByToken(s.getToken());
			if (res == null) {
				response.setCode(ResponseCode.CODE_0);
				response.setMessage("您没有参加过任何班级！");
				return response;
			}
			res.setDate(req.getDate());
			//这块取的总的分数（student表里的分数）
			List<StudentClassItem> sciL = studentMapper.searchUserDataItemByClassId(res.getClassId());
			if (sciL != null && sciL.size() > 0) {
				StringBuilder sb = new StringBuilder();
				for (StudentClassItem item : sciL) {
					sb.append(",").append(item.getId());
				}
				if (sb.length() > 0) {
					List<StudentTrainingsItem> stiL = trainingTaskMapper
						.searchStudentTrainingsItemByStudentIds(
						sb.substring(1), req.getDate());
				}
			}
			response.setResult(res);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(ResponseCode.CODE_0);
			response.setMessage(ResponseCode.MSG_0);
		} 
		long endTime = System.currentTimeMillis();
		log.info("调用接口getClassStudent结束，耗时：" + (endTime - startTime) + "ms");
		return response;
	}
	
	public static void main(String[] args) {
	}

}
