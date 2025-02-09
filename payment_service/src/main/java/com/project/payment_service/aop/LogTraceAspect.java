package com.project.payment_service.aop;

import brave.Span;
import brave.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogTraceAspect {

    private final Tracer tracer;

    @Autowired
    public LogTraceAspect(Tracer tracer) {
        this.tracer = tracer;
    }

    @Around("execution(* com.project.payment_service.repository.*Repository.*(..))")
    public Object traceRepositoryLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Span newSpan = tracer.nextSpan().name(joinPoint.getSignature().toString()).start();
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(newSpan.start())) {
            return joinPoint.proceed();
        } catch (Throwable e) {
            newSpan.error(e);
            throw e;
        } finally {
            newSpan.finish();
        }
    }

}
