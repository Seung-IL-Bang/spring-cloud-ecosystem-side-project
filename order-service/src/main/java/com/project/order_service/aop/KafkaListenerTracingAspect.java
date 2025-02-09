package com.project.order_service.aop;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.propagation.TraceContextOrSamplingFlags;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Aspect
@Component
@Slf4j
public class KafkaListenerTracingAspect {

    private final Tracing tracing;
    private final Tracer tracer;

    public KafkaListenerTracingAspect(Tracing tracing) {
        this.tracing = tracing;
        this.tracer = tracing.tracer();
    }

    @Around("@annotation(org.springframework.kafka.annotation.KafkaListener)")
    public Object aroundKafkaListener(ProceedingJoinPoint joinPoint) throws Throwable {
        ConsumerRecord<?, ?> consumerRecord = extractConsumerRecord(joinPoint.getArgs());

        Span newSpan;
        if (consumerRecord != null) {
            // b3 헤더 추출을 위한 extractor 람다 (key에 해당하는 헤더 값을 문자열로 변환)
            TraceContextOrSamplingFlags extracted = tracing.propagation().extractor(
                    (org.apache.kafka.common.header.Headers headers, String key) -> {
                        Header header = headers.lastHeader(key);
                        return header != null ? new String(header.value(), StandardCharsets.UTF_8) : null;
                    }
            ).extract(consumerRecord.headers());

            // 추출된 context가 있다면 이를 기반으로 자식 스팬 생성
            newSpan = tracer.nextSpan(extracted).name(joinPoint.getSignature().toShortString());
        } else {
            // ConsumerRecord가 없으면 단순히 새 스팬 생성
            newSpan = tracer.nextSpan().name(joinPoint.getSignature().toShortString());
        }

        try (Tracer.SpanInScope ws = tracer.withSpanInScope(newSpan.start())) {
            // 스팬이 활성화된 상태에서 대상 메서드 실행: 이때 MDC에 TraceId 등이 반영되어 로그에 출력됨
            return joinPoint.proceed();
        } catch (Throwable t) {
            newSpan.error(t);
            throw t;
        } finally {
            newSpan.finish();
        }
    }

    private ConsumerRecord<?, ?> extractConsumerRecord(Object[] args) {
        if (args != null) {
            for (Object arg : args) {
                if (arg instanceof ConsumerRecord) {
                    return (ConsumerRecord<?, ?>) arg;
                }
            }
        }
        return null;
    }
}
