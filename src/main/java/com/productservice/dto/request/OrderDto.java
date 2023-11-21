package com.productservice.dto.request;

import com.productservice.persistence.entity.Category;
import com.productservice.persistence.entity.Item;
import com.productservice.persistence.entity.ShippingAddress;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {
    private Long id;
    private Item item;
    private Date date;
    private Integer qty;
    private ShippingAddress address;
    private Category category;


    
}
