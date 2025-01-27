package com.project.order_service.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public abstract class OrderIdGenerator {

    private OrderIdGenerator() {}

    public static String get() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String timeStr = formatter.format(LocalDateTime.now());

        // 고유성 강화를 위한 3자리 난수
        Random random = new Random();
        int randomNum = random.nextInt(1000); // 0~999 사이의 숫자
        String randomStr = String.format("%03d", randomNum); // 포맷 맞추기 (3자리)

        return timeStr + randomStr;
    }

}
