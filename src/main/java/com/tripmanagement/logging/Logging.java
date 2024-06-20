package com.tripmanagement.logging;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logging {
    private static final Logger logger = LoggerFactory.getLogger(Logging.class);  
    @Before(" execution(* com.cognizant.*.*.*(..))")
    public void beforeAdvice(JoinPoint point) {
        logger.info("INFO:"+point.getSignature().getName());
    }
    @After(" execution(* com.cognizant.*.*.*(..))")
    public void afterAdvice(JoinPoint point) {
        logger.info("INFO:"+point.getSignature().getName());
    }
    @AfterReturning(pointcut="execution(* com.cognizant.*.*.*(..))",returning="result")
    public void afterReturning(JoinPoint point,Object result) {
         logger.debug("DEBUG:"+point.getSignature().getName()+" Return Value:"+result);
    }
    @AfterThrowing(pointcut="execution(* com.cognizant.*.*.*(..))",throwing="error")
    public void afterThrowing(JoinPoint point,Throwable error) {
        logger.error("Error:"+point.getSignature().getName()+" threw exception :"+error);
    }
    @Pointcut("execution(* com.cognizant.*.*.*(..))")
    public void getPointCut() {
        
    }
    @Around("getPointCut()")
    public Object aroundAdvice(ProceedingJoinPoint point) {
        logger.info("INFO:"+point.getSignature().getName());
        Object returnValue=null;
        try {
            returnValue=point.proceed();
            logger.debug("DEBUG:"+returnValue);
        } catch (Throwable e) {
            logger.error("Error:"+e.getMessage());
        }
        

        return returnValue;
    }

}

