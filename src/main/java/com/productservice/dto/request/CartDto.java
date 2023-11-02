package com.productservice.dto.request;

import com.productservice.persistence.entity.ITem;
import lombok.Data;

@Data
public class CartDto {
    private ITem item;
    private int quantity;
    private Long cartId;
    private Integer total;
}
