package com.slow.college.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.slow.college.common.CommonUtils;
import com.slow.college.exception.ScBizException;
import com.slow.college.param.response.BaseRsp;
import com.slow.college.param.scenum.RspEnum;
import com.slow.college.service.UploadFileService;

@Service
public class UploadFileServiceImpl implements UploadFileService {
	
	private static final Logger log = LogManager.getLogger(UploadFileServiceImpl.class);

	@Override
	public BaseRsp uploadImg (HttpServletRequest request, String token) throws ScBizException  {
		String response = CommonUtils.alterBaseImg(request, "ss" + new Date().getTime());
		log.info(response);
		if (response != null && response.length() > 0) {
			return BaseRsp.success(response);
		}
		return BaseRsp.fail(RspEnum.SYSTEM_ERROR);
	}
	
}
