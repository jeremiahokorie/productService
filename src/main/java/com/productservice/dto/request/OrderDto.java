package com.productservice.dto.request;

import com.productservice.persistence.entity.Category;
import com.productservice.persistence.entity.ITem;
import com.productservice.persistence.entity.ShippingAddress;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {
    private Long id;
    private ITem item;
    private Date date;
    private Integer qty;
    private ShippingAddress address;
    private Category category;


    
}
