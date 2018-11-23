package com.uts.handler.task.logger;

import com.alibaba.fastjson.JSON;
import com.uts.util.commont.ERROR_CODE;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by yxf on 2017/9/5.
 */
@Aspect
@Component
//开启注解的aop模式
@EnableAspectJAutoProxy
public class ServiceAspect {


    private static Logger logger = null;

    @Around("execution(* com.uts.service.impl.*.*(..))")
    public Object properd(ProceedingJoinPoint proceedingJoinPoint) {
        Object instance = proceedingJoinPoint.getTarget();
        logger = LoggerFactory.getLogger(instance.getClass());
        Signature signature = proceedingJoinPoint.getSignature();
        String methodName = signature.getName();
        String className = instance.getClass().getSimpleName();

        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        Method[] declaredMethods = instance.getClass().getDeclaredMethods();
        Method realMethod = null;
        for(Method method : declaredMethods){
            if (method.getName().equals(methodName)) {
                realMethod = declaredMethods[0];
            }
        }
        String[] parameNames = u.getParameterNames(realMethod);

        Object[] args = proceedingJoinPoint.getArgs();

        String logInfo = "";
        if (parameNames == null || parameNames.toString().isEmpty()) {
            logInfo = "method in";
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < args.length; i++) {
                sb.append(parameNames[i]+args[i]);
            }
            logInfo = JSON.toJSONString(sb);
        }
        logger.info("{}.{} {}", className, methodName, logInfo);
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            logger.error("", className, methodName, logInfo, e);
            return ERROR_CODE.ERROR;
        }
        return result;
    }
}
