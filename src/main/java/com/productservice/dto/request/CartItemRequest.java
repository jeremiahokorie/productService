package com.productservice.dto.request;

import com.productservice.persistence.entity.ITem;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
public class CartItemRequest {
    private Long cartId;
    private ITem item;
    private int quantity;
    private Date date;

}
