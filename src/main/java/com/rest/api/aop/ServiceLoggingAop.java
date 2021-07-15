//package com.rest.api.aop;
//
// TODO : ExceptionControllerAdvice를 위해서 일단 막아두었습니다. 비즈니스 로직 시간 체크 기능
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StopWatch;
//
//@Aspect
//@Component
//@Slf4j
//public class ServiceLoggingAop {
//    private static final Logger logger = LoggerFactory.getLogger(ControllerLoggingAop.class);
//
//    @Around("execution(* com.rest.api.service..*Service.*(..))")
//    public Object methodLogger(ProceedingJoinPoint proceedingJoinPoint) {
//        String className = proceedingJoinPoint.getSignature().getName();
//        StopWatch stopWatch = new StopWatch();
//        Object result = null;
//        try {
//            logger.info("----------------------------------");
//            stopWatch.start();
//            result = proceedingJoinPoint.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        } finally {
//            stopWatch.stop();
//            logger.info("{} elapsed time :: {}", className, stopWatch.getTotalTimeMillis());
//            logger.info("----------------------------------\n\n");
//        }
//        return result;
//    }
//}
