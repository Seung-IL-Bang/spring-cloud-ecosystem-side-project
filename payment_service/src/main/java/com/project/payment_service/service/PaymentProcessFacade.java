package com.project.payment_service.service;

import com.project.payment_service.constant.PaymentMethodTypes;
import com.project.payment_service.dto.CompensationInfo;
import com.project.payment_service.entity.Payment;
import com.project.payment_service.entity.PaymentMethod;
import com.project.payment_service.entity.PaymentMethodMapping;
import com.project.payment_service.entity.details.PaymentDetail;
import com.project.payment_service.repository.PaymentDetailRepository;
import com.project.payment_service.repository.PaymentMethodMappingRepository;
import com.project.payment_service.repository.PaymentMethodRepository;
import com.project.payment_service.repository.PaymentRepository;
import com.project.payment_service.service.pg.PaymentGateway;
import com.project.payment_service.service.pg.PaymentGatewayFactory;
import com.project.payment_service.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.project.payment_service.constant.PaymentStatus.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentProcessFacade {

    private final PaymentGatewayFactory paymentGatewayFactory;
    private final PaymentRepository paymentRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMappingRepository paymentMethodMappingRepository;
    private final PaymentDetailRepository paymentDetailRepository;

    @Transactional
    public PaymentEvent processPay(OrderCreatedEvent event) {

        String paymentId = UUID.randomUUID().toString();

        List<CompensationInfo> compensationInfos = new ArrayList<>(); // 각 결제 단계별 보상 정보를 누적 (보상 API 호출에 필요한 데이터)

        try {
            for (OrderCreatedEvent.PaymentInfo paymentInfo : event.getPaymentInfos()) { // 여러 결제 수단이 있을 수 있음 (다양한 결제 수단 조합 가능)

                PaymentMethodTypes paymentMethodType = PaymentMethodTypes.valueOf(paymentInfo.getPaymentMethod());

                // PG 사 선택
                PaymentGateway paymentGateway = paymentGatewayFactory.getPaymentGateway(paymentMethodType);

                // PG 사 API 요청 - 결제 요청 및 결제 상세 정보 응답
                PaymentDetail paymentDetail = null;
                try {
                    paymentDetail = paymentGateway.pay(paymentInfo.getAmount());
                } catch (Exception e) {
                    compensate(compensationInfos);
                    throw new RuntimeException(e.getMessage(), e);
                }

                if (paymentGateway.supportsCompensate()) {
                    compensationInfos.add(new CompensationInfo(paymentGateway, paymentDetail.getTransactionId(), paymentInfo.getAmount()));
                }

                PaymentMethod paymentMethod = paymentMethodRepository.findByPaymentMethodType(paymentMethodType)
                        .orElseThrow(() -> new IllegalArgumentException("Unsupported payment method"));

                // PaymentMethodMapping 생성
                PaymentMethodMapping paymentMethodMapping = PaymentMethodMapping.builder()
                        .paymentId(paymentId)
                        .paymentMethodId(paymentMethod.getId())
                        .amount(paymentInfo.getAmount())
                        .build();
                // PaymentMethodMapping 저장
                PaymentMethodMapping savedPaymentMethodMapping = paymentMethodMappingRepository.save(paymentMethodMapping);

                // PaymentDetail 저장
                paymentDetail.setPaymentMethodMapping(savedPaymentMethodMapping);
                paymentDetailRepository.save(paymentDetail);
            }

            // Payment 생성
            Payment payment = Payment.builder()
                    .orderId(event.getOrderId())
                    .paymentId(paymentId)
                    .paymentStatus(APPROVED)
                    .totalAmount(event.getTotalAmount())
                    .build();
            // Payment 저장
            paymentRepository.save(payment);

            log.info("Payment processed for order: {}", event.getOrderId());
            return new PaymentApprovedEvent(event.getOrderId(), paymentId, APPROVED, event.getTotalAmount());
        } catch (Exception e) {
            log.error("Error processing payment", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Transactional
    public PaymentEvent processRefund(OrderCancelledEvent event) {
        log.info("Payment refunded for order: {}", event.getOrderId());

        List<PaymentMethodMapping> paymentMethodMapping = paymentMethodMappingRepository.findByPaymentId(event.getPaymentId());

        for (PaymentMethodMapping mapping : paymentMethodMapping) {
            refund(mapping);
        }

        // Payment 상태 변경
        paymentRepository.findByPaymentId(event.getPaymentId())
                .ifPresent(payment -> payment.updateStatus(REFUNDED));

        return new PaymentRefundedEvent(event.getOrderId(), event.getPaymentId(), REFUNDED, "Customer request");
    }

    private void refund(PaymentMethodMapping paymentMethodMapping) {
        Integer paymentMethodId = paymentMethodMapping.getPaymentMethodId(); // 결제 수단 ID

        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new IllegalArgumentException("PaymentMethod not found"));

        PaymentGateway paymentGateway = paymentGatewayFactory.getPaymentGateway(paymentMethod.getPaymentMethodType());

        // PG 사 API 요청 - 결제 취소
        paymentGateway.cancel(paymentMethodMapping.getPaymentId(), paymentMethodMapping.getAmount());
    }

    // 각 결제 단계에서 성공한 결제에 대해 보상(취소) 로직 실행
    private void compensate(List<CompensationInfo> compensationInfos) {
        for (CompensationInfo info : compensationInfos) {
            try {
                info.getPaymentGateway().cancel(info.getTransactionId(), info.getAmount());
                log.info("보상(취소) 성공: transactionId={}, amount={}", info.getTransactionId(), info.getAmount());
            } catch (Exception cancelEx) {
                log.error("보상(취소) 실패: transactionId={}, amount={}", info.getTransactionId(), info.getAmount(), cancelEx);
            }
        }
    }
}
