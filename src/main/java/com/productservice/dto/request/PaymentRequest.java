package com.productservice.dto.request;

import lombok.Data;

@Data
public class PaymentRequest {
    private String email;
    private double amount;
    private String callbackUrl;
}
