package com.slow.college.service;

import javax.servlet.http.HttpServletRequest;

import com.slow.college.exception.ScBizException;
import com.slow.college.param.response.BaseRsp;

public interface UploadFileService {
	
	BaseRsp uploadImg (HttpServletRequest request, String token) throws ScBizException ;

}
