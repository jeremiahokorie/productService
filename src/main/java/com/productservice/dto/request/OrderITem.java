package com.productservice.dto.request;

import com.productservice.persistence.entity.Category;
import lombok.Data;

@Data
public class OrderITem {
    private String name;
    private String description;
    private Integer Stock;
    private Integer amount;
    private String quantity;
}
