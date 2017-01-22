package com.base.framework.log;

import com.alibaba.fastjson.JSON;
import com.base.framework.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Wx logger aspect 日志切片
 * @author zhouyw
 * @date 2016.06.03
 */
public class LoggerAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @PostConstruct
    public void init() {

    }
    /**
     * Simple advice
     * createby zhouyw on 2016.06.03
     * @param joinPoint
     * @return object
     * @throws Throwable
     */
    public Object simpleAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        try {

            Object[] args = joinPoint.getArgs();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            if (method.getDeclaringClass().isInterface()) {
                Class clazz = joinPoint.getTarget().getClass();
                method = clazz.getDeclaredMethod(joinPoint.getSignature().getName(), method.getParameterTypes());
            }
            Annotation[] annotations = method.getAnnotations();
            logger.info("log -->annotations={}", annotations);
            logger.info("log str-->method={},args={}",method,JSON.toJSONString(args));



            Object resultObj = null;
            try {
                // 执行方法
                resultObj = joinPoint.proceed(args);
            } catch (BusinessException e) {
                logger.error("log asp proceed busi err:-->", e);
            } catch (Exception e) {
                logger.error("log asp proceed excep err:-->", e);
            }


            if (resultObj != null) {//解析返回的对象 的状态 及 描述

            }

            try{//构建日志对象
                logger.info("log end-->method={},resultObj={}",method,resultObj);
            }catch(Exception e){
                logger.error("wx OperLog error:-->",e);
            }

            return resultObj;

        } catch (Exception exception) {
            logger.error("日志切片处理失败。。。。。", exception);
            return null;
        }
    }
}
