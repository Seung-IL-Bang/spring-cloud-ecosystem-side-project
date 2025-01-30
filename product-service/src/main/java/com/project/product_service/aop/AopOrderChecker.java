package com.project.product_service.aop;

import org.springframework.aop.Advisor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AopOrderChecker {

    public AopOrderChecker(ApplicationContext context) {
        System.out.println("=== 등록된 AOP 어드바이저 목록 (우선순위 순) ===");
        String[] advisorBeans = context.getBeanNamesForType(Advisor.class);

        for (String advisorBean : advisorBeans) {
            Advisor advisor = (Advisor) context.getBean(advisorBean);
            System.out.println("Advisor: " + advisor.getClass().getName());
        }
    }
}
