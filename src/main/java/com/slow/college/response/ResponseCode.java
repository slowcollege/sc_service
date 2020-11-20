package com.slow.college.response;

/**
 * 接口返回的代码及提示信息公共类
 * 
 * @author songming
 */
public class ResponseCode {

	public static final int CODE_1 = 1;
	public static final String MSG_1 = "success";

	public static final int CODE_0 = 0;
	public static final String MSG_0 = "参数不正确";

	public static final int CODE_90 = 90;
	public static final String MSG_90 = "用户信息为空";

	public static final int CODE_91 = 91;
	public static final String MSG_91 = "登录失效";

	public static final int CODE_92 = 92;
	public static final String MSG_92 = "验证码无效或已过期";

	public static final int CODE_93 = 93;
	public static final String MSG_93 = "用户已存在";

	public static final int CODE_4 = 4;
	public static final String MSG_4 = "手机号无效或不存在";

	public static final int CODE_5 = 5;
	public static final String MSG_5 = "兑换失败";

	public static final int CODE_6 = 6;
	public static final String MSG_6 = "无可转赠礼品册";
}