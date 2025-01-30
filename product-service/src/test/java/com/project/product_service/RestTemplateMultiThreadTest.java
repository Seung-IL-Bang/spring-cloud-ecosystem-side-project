package com.project.product_service;

import org.junit.jupiter.api.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestTemplateMultiThreadTest {

    private static final String URL = "http://localhost:5500/product-service/products/CATALOG-0001/order?quantity=1";
    private RestTemplate restTemplate;
    private ExecutorService executorService;

    @BeforeAll
    void setup() {
        restTemplate = new RestTemplate();
        executorService = Executors.newFixedThreadPool(10); // 최대 10개의 스레드 사용
    }

    @AfterAll
    void tearDown() {
        executorService.shutdown();
    }

    @Test
    @DisplayName("멀티스레드 환경에서 RestTemplate을 이용한 동시 요청 테스트")
    void testConcurrentRestTemplateRequests() throws InterruptedException, ExecutionException {
        int requestCount = 50; // ??? 개의 동시 요청
        List<Callable<ResponseEntity<String>>> tasks = new ArrayList<>();

        for (int i = 0; i < requestCount; i++) {
            tasks.add(() -> {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> requestEntity = new HttpEntity<>(headers);
                return restTemplate.exchange(URL, HttpMethod.POST, requestEntity, String.class);
            });
        }

        // 멀티스레드로 실행
        List<Future<ResponseEntity<String>>> futures = executorService.invokeAll(tasks);

        for (Future<ResponseEntity<String>> future : futures) {
            ResponseEntity<String> response = future.get();
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); // 200 응답 검증
        }
    }
}

