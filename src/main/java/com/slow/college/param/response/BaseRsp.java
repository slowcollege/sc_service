package com.slow.college.param.response;


import com.slow.college.param.scenum.RspEnum;

/**
 * @author gy
 * @version v3.0.0
 * @description 接口统一返回对象
 * @company 嘉福
 * @date: 2018年11月8日
 */
@SuppressWarnings("rawtypes")
public class BaseRsp<T> {

    // 错误码
    private int code;
    // 信息
    private String message;
    // 数据
    private T data;

    private BaseRsp(RspEnum result, T data){
        this.code = result.getCode();
        this.message = result.getMsg();
        this.data = data;
    }

    private BaseRsp(int code, String msg, T data){
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public static BaseRsp success(){
        return success(null);
    }

    public static <T> BaseRsp success(String msg){
        return new BaseRsp<T>(1,msg,null);
    }
    public static <T> BaseRsp success(String msg,T data){
        return new BaseRsp<T>(1,msg,data);
    }

    public static <T> BaseRsp success(T data){
        return new BaseRsp<T>(RspEnum.SUCCESS,data);
    }

    public static <T> BaseRsp fail(RspEnum result){
        return new BaseRsp<T>(result,null);
    }

    public static <T> BaseRsp fail(int code,String msg){
        return new BaseRsp<T>(code,msg,null);
    }

    public static <T> BaseRsp fail(RspEnum result,T data){
        return new BaseRsp<T>(result,data);
    }

    public int getCode(){
        return code;
    }

    public void setCode(int code){
        this.code = code;
    }

    public String getMsg(){
        return message;
    }

    public void setMsg(String msg){
        this.message = msg;
    }

    public T getData(){
        return data;
    }

    public void setData(T data){
        this.data = data;
    }


}
