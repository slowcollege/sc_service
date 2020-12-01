package com.slow.college.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.slow.college.exception.ScBizException;
import com.slow.college.param.response.BaseRsp;
import com.slow.college.service.UploadFileService;

@RestController
@RequestMapping(value = "/api/v1/file/")
public class UploadFileController {
	
	@Autowired
	private UploadFileService uploadFileService;
	
	/**
	 * 上传图片接口
	 * 
	 * @param request
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "uploadImg", method = RequestMethod.POST)
	public BaseRsp uploadImg (HttpServletRequest request, String token) throws ScBizException {
		return uploadFileService.uploadImg(request, token);
	}

}
