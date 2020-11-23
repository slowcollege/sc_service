package com.slow.college.exception;

import com.slow.college.param.scenum.RspEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @description  自定义业务异常类
 * @version v1.0.0
 */
@Data
public class ScBizException extends Exception {
	
	private static final long serialVersionUID = 7716274319918194241L;

	/**
	 * 异常码
	 */
	private int code;
	/**
	 * 异常描述
	 */
	private String error;

	public ScBizException(int code, String message) {
		super(message);
		this.code = code;
		this.error = message;
	}

	public ScBizException(RspEnum result) {
		super(result.getMsg());
		this.code = result.getCode();
		this.error = result.getMsg();
	}

	public ScBizException(RspEnum result, String message) {
		super(message);
		this.code = result.getCode();
		if(StringUtils.isNotBlank(message)) {
            this.error = String.format("%s   desc : [%s]", result.getMsg(), message);
        } else {
            this.error = result.getMsg();
        }
	}

	public ScBizException(RspEnum result, Throwable e) {
		super(String.format("%d | %s", result.getCode(), result.getMsg()), e);
		this.code = result.getCode();
		this.error = result.getMsg();
	}

	public ScBizException(RspEnum result, String message, Throwable e) {
		super(message, e);
		this.code = result.getCode();
		if(StringUtils.isNotBlank(message)) {
            this.error = String.format("%s   desc : [%s]", result.getMsg(), message);
        } else {
            this.error = result.getMsg();
        }
	}


}
