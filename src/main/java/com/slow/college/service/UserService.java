package com.slow.college.service;

import com.slow.college.exception.ScBizException;
import com.slow.college.param.response.BaseRsp;
import com.slow.college.request.TrainingReq;
import com.slow.college.request.UserReq;

public interface UserService {
	
	BaseRsp login (UserReq req) throws ScBizException;
	
	BaseRsp getClassStudent (UserReq req) throws ScBizException;

	BaseRsp getStudentTrainingTask (UserReq req) throws ScBizException;
	
	BaseRsp submitStudentTraining (TrainingReq req) throws ScBizException;
	
}
