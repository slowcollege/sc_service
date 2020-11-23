package com.slow.college.param.scenum;

/**
 * @author yuan
 * @version v1.0.0
 * @description 请求返回code和msg
 */
public enum RspEnum {

    /**
     * 请求成功：0
     * 公共错误码：1-999
     * 业务错误码：1000-1999
     */

    SUCCESS(1, "success"),
    SYSTEM_ERROR(401, "系统内部异常"),
    ERROR_BAD_REQUEST(400, "请求错误"),
    PARAM_LEAK(403, "部分参数为空"),
    NO_DATA(402, "数据未找到"),
    SIGN_ERROR(406, "验签失败"),
    ERROR_DATA(407, "数据不可用"),

    /**
     * 用户
     */
    USER_UNEXISTS(2,"用户不存在"),




    ;

    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String msg;

    RspEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
