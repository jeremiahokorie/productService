package com.productservice.dto.request;

import com.productservice.persistence.entity.Item;
import lombok.Data;

import java.util.Date;

@Data
public class CartItemRequest {
    private Long cartId;
    private Item item;
    private int quantity;
    private Date date;

}
