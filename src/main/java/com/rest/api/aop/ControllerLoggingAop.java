package com.rest.api.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class ControllerLoggingAop {

    private static final Logger logger = LoggerFactory.getLogger(ControllerLoggingAop.class);

    @Pointcut("execution(* com.rest.api.controller..*Controller.*(..))")
    public void loggerPointCut() {
    }

    @Around("loggerPointCut()")
    public Object methodLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        logger.info("-------------------------------");
        logger.info(joinPoint.getSignature().getName() + " 시작");

        Object proceed = joinPoint.proceed();

        stopWatch.stop();
        logger.info(stopWatch.prettyPrint());
        logger.info(joinPoint.getSignature().getName() + " 종료");
        logger.info("-------------------------------");
        return proceed;
    }
}
