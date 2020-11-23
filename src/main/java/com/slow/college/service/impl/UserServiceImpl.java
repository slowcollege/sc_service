package com.slow.college.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.slow.college.param.training.WaitTrainingItem;
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
	
	@Override
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
			res.setId(s.getId());
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
	
	@Override
	public ObjectResponse<ClassStudentItem> getClassStudent (HttpServletRequest request, UserReq req) {
		log.info("调用接口getClassStudent开始");
		long startTime = System.currentTimeMillis();
		ObjectResponse<ClassStudentItem> response = new ObjectResponse<>();
		try {
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			if (req.getToken() == null || req.getToken().trim().length() == 0) {
				response.setCode(ResponseCode.CODE_90);
				response.setMessage(ResponseCode.MSG_90);
				return response;
			}
			if (req.getDate() == null || req.getDate().trim().length() == 0) {
				req.setDate(DateUtil.parseString(new Date(), DateUtil.YYYY_MM_DD));
			} else {
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
					Date endD = ft.parse(req.getDate().trim());
					Date startT = ft.parse(item.getCreateTime()
						.replaceAll("_", "-").replaceAll("/", "-"));
					int days = (int) (endD.getTime() - startT.getTime()) / (1000 * 60 * 60 * 24);
					item.setTrainingDays(days);
				}
				if (sb.length() > 0) {
					//打卡信息
					List<StudentTrainingsItem> stiL = trainingTaskMapper
						.searchStudentTrainingsItemByStudentIds(
						sb.substring(1), req.getDate());
					//打卡图片
					List<StudentTrainingsItem> stiL1 = trainingTaskMapper
						.searchStudentTrainingsItemImgByStudentIds(
						sb.substring(1), req.getDate());
					if (stiL != null && stiL.size() > 0 && 
						stiL1 != null && stiL1.size() > 0) {
						for (StudentTrainingsItem item : stiL) {
							if (item.getTrainingId() != null) {
								List<String> imgL = new ArrayList<>();
								for (StudentTrainingsItem item1 : stiL1) {
									if (item.getTrainingId().intValue() == 
										item1.getTrainingId().intValue()) {
										imgL.add(item1.getVideoUrl());
									}
								}
								item.setImgList(imgL);
							}
						}
					}
					if (stiL != null && stiL.size() > 0) {
						for (StudentClassItem item : sciL) {
							List<StudentTrainingsItem> l = new ArrayList<>();
							for (StudentTrainingsItem items : stiL) {
								if (item.getId().intValue() == 
									items.getStudentId().intValue()) {
									if (items.getState() != null) {
										switch (items.getState().intValue()) {
											case 0:
												items.setStateDesc("未完成");
												break;
											case 1:
												items.setStateDesc("已完成");
												break;
											case 2:
												items.setStateDesc("未达标");
												break;
										default:
											break;
										}
									}
									l.add(items);
								}
							}
							item.setTrainings(l);
						}
					}
				}
			}
			res.setStudent(sciL);
			response.setResult(res);
			response.setCode(ResponseCode.CODE_1);
			response.setMessage(ResponseCode.MSG_1);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(ResponseCode.CODE_0);
			response.setMessage(ResponseCode.MSG_0);
		} 
		long endTime = System.currentTimeMillis();
		log.info("调用接口getClassStudent结束，耗时：" + (endTime - startTime) + "ms");
		return response;
	}
	
	@Override
	public ObjectResponse<List<WaitTrainingItem>> getStudentTrainingTask (HttpServletRequest request, UserReq req) {
		log.info("调用接口getStudentTrainingTask开始");
		long startTime = System.currentTimeMillis();
		ObjectResponse<List<WaitTrainingItem>> response = new ObjectResponse<>();
		List<WaitTrainingItem> res = new ArrayList<>();
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
			List<WaitTrainingItem> rL = trainingTaskMapper
				.searchWaitTrainingItemByStudentIdAndTime(
				s.getId(), DateUtil.parseString(new Date(), DateUtil.YYYY_MM_DD));
			if (rL != null && rL.size() > 0) {
				for (WaitTrainingItem item : rL) {
					if (item.getState() == null || 
						item.getState().intValue() == 0 ||
						item.getState().intValue() == 2) {
						if (item.getState() == null || 
							item.getState().intValue() == 0) {
							item.setState((byte) 0);
							item.setStateDesc("未完成");
						} else {
							item.setState((byte) 2);
							item.setStateDesc("未达标");
						}
						res.add(item);
					}
				}
			}
			response.setCode(ResponseCode.CODE_1);
			response.setMessage(ResponseCode.MSG_1);
			response.setResult(res);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(ResponseCode.CODE_0);
			response.setMessage("登录失败");
		} 
		long endTime = System.currentTimeMillis();
		log.info("调用接口getStudentTrainingTask结束，耗时：" + (endTime - startTime) + "ms");
		return response;
	}
	
	public static void main(String[] args) {
	}

}
