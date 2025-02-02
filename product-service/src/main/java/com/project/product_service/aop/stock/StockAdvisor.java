package com.project.product_service.aop.stock;

import com.project.product_service.service.RedissonLockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

@Slf4j
//@Component
@RequiredArgsConstructor
public class StockAdvisor extends AbstractPointcutAdvisor {

    private final RedissonLockService redissonLockService;

    // @StockLock 애노테이션이 붙은 메서드를 대상으로 설정
    private final Pointcut pointcut = new AnnotationMatchingPointcut(null, StockLock.class);

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public MethodInterceptor getAdvice() {
        return new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                // 대상 메서드 정보 가져오기
                Object[] args = invocation.getArguments();
                String productId = (String) args[0]; // 첫 번째 파라미터가 productId라고 가정

                StockLock stockLock = invocation.getMethod().getAnnotation(StockLock.class);
                long leaseTime = stockLock.leaseTime();
                long waitTime = stockLock.waitTime();
                String lockKey = "lock:product:stock:" + productId;

                log.info("StockAdvisor - Locking: {}", lockKey);

                if (redissonLockService.tryLock(lockKey, waitTime, leaseTime)) {
                    try {
                        return invocation.proceed(); // 실제 메서드 실행
                    } finally {
                        redissonLockService.unlock(lockKey); // 락 해제
                        log.info("StockAdvisor - Unlocking: {}", lockKey);
                    }
                } else {
                    log.error("락을 획득하지 못하여 종료합니다. productId={}", productId);
                    return false;
                }
            }
        };
    }
}
