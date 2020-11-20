package com.slow.college.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Manager;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.slow.college.response.ObjectResponse;
import com.slow.college.response.ResponseCode;
import com.slow.college.service.UserService;
import com.slow.college.utils.EncryptUtil;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LogManager.getLogger(UserServiceImpl.class);
	
	/*public ObjectResponse<Map<String, String>> login (HttpServletRequest request, ManagerReq req){
		log.info("调用接口login开始");
		long startTime = System.currentTimeMillis();
		ObjectResponse<Map<String, String>> response = new ObjectResponse<>();
		Map<String, String> res = new HashMap<>();
		try {
			if (req.getName() == null || req.getName().trim().length() == 0 || 
				req.getPassword() == null || req.getPassword().trim().length() == 0) {
				response.setCode(ResponseCode.CODE_0);
				response.setMessage(ResponseCode.MSG_0);
				res.put("success", "0");
				res.put("message", "用户名或密码为空");
				response.setResult(res);
				return response;
			}
			Manager m = masterManagerMapper.selectManagerByName(req.getName().trim());
			if (m == null) {
				response.setCode(ResponseCode.CODE_1);
				response.setMessage(ResponseCode.MSG_1);
				res.put("success", "0");
				res.put("message", "该用户不存在");
				response.setResult(res);
				ManagerOperateLog mol = CommonUtils.createManagerOperateLog(request, "登录", "登录失败，原因：该用户不存在，用户名：" + req.getName());
				if (mol != null) {
					masterManagerOperateLogMapper.insertSelective(mol);
				}
				return response;
			}
			if (m.getPassword() == null || 
				!req.getPassword().trim().equals(m.getPassword().trim())) {
				response.setCode(ResponseCode.CODE_1);
				response.setMessage(ResponseCode.MSG_1);
				res.put("success", "0");
				res.put("message", "密码错误");
				response.setResult(res);
				ManagerOperateLog mol = CommonUtils.createManagerOperateLog(request, "登录", "登录失败，原因：密码错误，用户名：" + req.getName());
				if (mol != null) {
					masterManagerOperateLogMapper.insertSelective(mol);
				}
				response.setResult(res);
				return response;
			}
			response.setCode(ResponseCode.CODE_1);
			response.setMessage(ResponseCode.MSG_1);
			res.put("success", "1");
			res.put("message", "登录成功");
			long time = System.currentTimeMillis();
			String token = EncryptUtil.GetMD5Code(m.getName().trim() + time + "json");
			m.setToken(token);
			res.put("token", token);
			res.put("success", "1");
			Date times = new Date(time);
			res.put("tokenTime", DateUtil.format(times, DateUtil.YYYY_MM_DDHHMMSS));
			times.setDate(times.getDate() + 1);
			m.setTokenTime(times);
			masterManagerMapper.updateSelective(m);
			res.put("name", m.getName());
			res.put("id", m.getId() + "");
			response.setResult(res);
			response.setResult(res);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(ResponseCode.CODE_0);
			response.setMessage(ResponseCode.MSG_0);
			res.put("success", "0");
			res.put("message", "参数错误");
			response.setResult(res);
		} 
		long endTime = System.currentTimeMillis();
		log.info("调用接口login结束，耗时：" + (endTime - startTime) + "ms");
		return response;
	}*/

}
