package com.project.order_service.aop;

import brave.Span;
import brave.Tracer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static java.nio.charset.StandardCharsets.UTF_8;

@Aspect
@Component
public class B3PropagationAspect {

    private final Tracer tracer;

    public B3PropagationAspect(Tracer tracer) {
        this.tracer = tracer;
    }

    @Around("execution(* org.springframework.kafka.core.KafkaTemplate.send(String, ..))")
    public Object addB3HeadersToProducerMessage(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        // 메서드 시그니처: send(String topic, V data)
        String topic = (String) args[0];
        String data = (String) args[1];

        // ProducerRecord 생성
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, data);

        // 현재 활성화된 Span에서 b3 헤더에 필요한 값 추출
        Span currentSpan = tracer.currentSpan();
        if (currentSpan != null) {
            record.headers().add("X-B3-TraceId", currentSpan.context().traceIdString().getBytes(UTF_8));
            record.headers().add("X-B3-SpanId", currentSpan.context().spanIdString().getBytes(UTF_8));
            String sampled = (currentSpan.context().sampled() != null && currentSpan.context().sampled()) ? "1" : "0";
            record.headers().add("X-B3-Sampled", sampled.getBytes(UTF_8));
        }

        // 원래의 KafkaTemplate.send(String, V) 대신,
        // ProducerRecord를 인자로 받는 send 메서드를 호출하여 메시지 전송
        KafkaTemplate<String, String> kafkaTemplate = (KafkaTemplate<String, String>) joinPoint.getTarget();

        Span newSpan = tracer.nextSpan().name(joinPoint.getSignature().toString()).start();
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(newSpan.start())) {
            return kafkaTemplate.send(record);
        } catch (Throwable e) {
            newSpan.error(e);
            throw e;
        } finally {
            newSpan.finish();
        }
    }
}
