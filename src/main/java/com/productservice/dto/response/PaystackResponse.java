package com.productservice.dto.response;

import lombok.Data;

@Data
public class PaystackResponse {
    private String status;
    private String message;
    private String data;
}
