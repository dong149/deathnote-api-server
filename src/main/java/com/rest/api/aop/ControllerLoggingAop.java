package com.rest.api.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ControllerLoggingAop {


    private static final Logger logger = LoggerFactory.getLogger(ControllerLoggingAop.class);

    @Pointcut("execution(* com.rest.api.controller..*Controller.*(..))")
    public void loggerPointCut() {
    }

    @Before("loggerPointCut()")
    public void methodLogger(JoinPoint joinPoint) {
        logger.info("--------------");
        logger.info(joinPoint.getSignature().getName());
        logger.info("--------------");
    }

}
