package com.project.product_service.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@Order(value = Integer.MAX_VALUE - 1) // AOP 우선순위를 지정합니다.
@RequiredArgsConstructor
public class StockAspect {

    private final RedissonLockService redissonLockService;

    @Around("@annotation(stockLock) && args(productId,..)")
    public Object doLock(ProceedingJoinPoint joinPoint, StockLock stockLock, String productId) throws Throwable {
        log.info("StockLockAspect.doLock {}", joinPoint.getSignature());

        long leaseTime = stockLock.leaseTime();
        long waitTime = stockLock.waitTime();
        String lockKey = "lock:product:stock:" + productId;

        if (redissonLockService.tryLock(lockKey, waitTime, leaseTime)) {
            try {
                return joinPoint.proceed();
            } finally {
                redissonLockService.unlock(lockKey);
            }
        } else {
            log.error("락을 획득하지 못하여 종료합니다. productId={}", productId);
            return false;
        }
    }
}
