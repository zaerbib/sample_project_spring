package com.tuto.aopdemo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class TimeMonitorAspect {
    Logger log = LoggerFactory.getLogger(TimeMonitorAspect.class);

    @Around("@annotation(TimeMonitor)")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Starting "+joinPoint.getSignature().toShortString());
        //log.info(joinPoint.getSignature().getName() + " input -> "+ Arrays.toString((int[])joinPoint.getArgs()[0]));
        double start = System.nanoTime();
        Object proceed = joinPoint.proceed();
        double executime = (System.nanoTime() - start)/1000000000.0;
        //log.info(joinPoint.getSignature().getName() + " ed -> "+Arrays.toString((int[]) joinPoint.getArgs()[0]));
        log.info(joinPoint.getSignature().getName() + " took: "+executime + "s\n");

        return proceed;
    }
}
