package com.productservice.service.Impl;

import com.productservice.dto.request.PaymentRequest;
import com.productservice.dto.response.PaystackResponse;
import com.productservice.service.PaystackService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DefaultPaystackService implements PaystackService {

    @Value("${paystack.api.key}")
    private String apiKey;

    private RestTemplate restTemplate = new RestTemplate();

    public PaystackResponse makePayment(PaymentRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);

        HttpEntity<PaymentRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<PaystackResponse> response = restTemplate.exchange(
                "https://api.paystack.co/transaction/initialize",
                HttpMethod.POST,
                entity,
                PaystackResponse.class
        );

        return response.getBody();
    }

}

