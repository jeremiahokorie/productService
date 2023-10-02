package com.productservice.dto.request;

import com.productservice.persistence.entity.ITem;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long customerId;
    private List<ITem> orderItems;
//    private Address shippingAddress;
//    private PaymentInfo paymentInfo;

}
