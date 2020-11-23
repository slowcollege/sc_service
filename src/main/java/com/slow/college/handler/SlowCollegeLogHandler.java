package com.slow.college.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author gy
 * @version v3.0.0
 * @description 日志AOP切面
 * @company 嘉福
 * @date: 2018年7月4日 下午6:02:03
 */
@Component
@Aspect
public class SlowCollegeLogHandler {
    private static final Logger LOG = LoggerFactory.getLogger(SlowCollegeLogHandler.class);

    @Pointcut(value = "execution(* com.slow.college.controllerV2.*.*(..))")
    public void controller() {
    }

    @Before(value = "controller()")
    public void before(JoinPoint joinPoint) throws Throwable {
        StringBuilder log = new StringBuilder(joinPoint.getSignature().getDeclaringType() + ",method："
                + joinPoint.getSignature().getName() + ",请求参数：");
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            log.append("无");
        } else {
            log.append(JSON.toJSONString(args[0]));
        }
        LOG.info(log.toString());
    }

    @Around(value = "controller()")
    public Object aroud(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        StringBuilder log = new StringBuilder(joinPoint.getSignature().getDeclaringType() + ",method:"
                + joinPoint.getSignature().getName() + ",req：");
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            log.append("无");
        } else {
            log.append(JSON.toJSONString(args[0]));
        }
        Object result = joinPoint.proceed(args);
        if (result.toString().contains("code")) {
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(result));
            log.append("返回结果：" + jsonObject.get("code") + "\t" + jsonObject.get("msg"));
        } else if (result != null && result.toString().length() < 100) {
            log.append("返回结果：" + new Gson().toJson(result));
        } else {
            //log.append("无返回");
        }
        // log.append(",rsp:" + JSON.toJSONString(result));
        long end = System.currentTimeMillis();
        log.append(",cost：" + (end - start) + "ms");
        LOG.info(log.toString());
        return result;

    }
}
