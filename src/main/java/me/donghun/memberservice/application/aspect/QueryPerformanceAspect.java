package me.donghun.memberservice.application.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class QueryPerformanceAspect {

    @Around("execution(* me.donghun.memberservice.application.service..*(..)) && @within(me.donghun.memberservice.application.aspect.QueryPerformanceLogging)")
    public Object logPerf(ProceedingJoinPoint joinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        Object retVal = joinPoint.proceed(); // 메서드 호출 자체를 감쌈
        log.info("QueryPerformance [{}] => {}ms", joinPoint.getSignature().getName(), System.currentTimeMillis() - begin);
        return retVal;
    }
}
