package com.productservice.dto.request;


import lombok.Data;

import java.util.Date;

@Data
public class OrderRequestDto {
    private Long itemId;
    private Long addressId;
    private Long categoryId;
    private Date date;
    private Integer qty;

}
