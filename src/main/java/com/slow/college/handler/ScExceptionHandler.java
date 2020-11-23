package com.slow.college.handler;


import com.slow.college.exception.ScBizException;
import com.slow.college.param.response.BaseRsp;
import com.slow.college.param.scenum.RspEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;

/**
 * @author yuan
 * @version v1.0.0
 * @description 统一异常处理
 * @company 嘉福
 * @date: 2018年7月4日 下午6:26:08
 */
@ControllerAdvice
@RestController
@SuppressWarnings("rawtypes")
public class ScExceptionHandler extends AbstractErrorController {
    private static final Logger LOG = LoggerFactory.getLogger(ScExceptionHandler.class);

    public ScExceptionHandler(ErrorAttributes errorAttributes){
        super(errorAttributes);
    }

    /**
     * 业务错误
     */
    @ExceptionHandler(ScBizException.class)
    public BaseRsp bookException(ScBizException ex){
        LOG.error("EcardBizException！code=" + ex.getCode() + ",msg=" + ex,ex);
        return BaseRsp.fail(ex.getCode(),ex.getMessage());
    }

    /**
     * 其他未捕捉异常
     */
    @ExceptionHandler(Exception.class)
    public BaseRsp uncaughtException(Exception ex){
        LOG.error("Uncaught exception!",ex,ex);
        return BaseRsp.fail(RspEnum.SYSTEM_ERROR);
    }

    /**
     * 参数错误
     */
    @ExceptionHandler({ValidationException.class,TypeMismatchException.class,HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class})
    public BaseRsp argumentNotValidException(Exception ex){
        LOG.error("Argument exception",ex,ex);
        return BaseRsp.fail(RspEnum.ERROR_BAD_REQUEST);
    }

    @Override
    public String getErrorPath(){
        return "/error";
    }
}
