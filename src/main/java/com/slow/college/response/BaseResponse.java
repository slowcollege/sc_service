package com.slow.college.response;

import java.io.Serializable;

/**
 * 接口返回公共类
 * 
 * @Description: TODO
 * @author gy
 */
@SuppressWarnings("serial")
public class BaseResponse implements Serializable {

	private int code = ResponseCode.CODE_1;
	private String message = ResponseCode.MSG_1;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return " [code=" + code + ", message=" + message + "]";
	}

}