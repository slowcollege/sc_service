package com.slow.college.service;

import com.slow.college.exception.ScBizException;
import com.slow.college.param.response.BaseRsp;
import com.slow.college.request.UserReq;

public interface UserServiceV2 {

    BaseRsp getStudentProfile(UserReq req) throws ScBizException;
}
