package com.productservice.service;

import com.productservice.dto.request.PaymentRequest;
import com.productservice.dto.response.PaystackResponse;

public interface PaystackService {
    PaystackResponse makePayment(PaymentRequest request);
}
