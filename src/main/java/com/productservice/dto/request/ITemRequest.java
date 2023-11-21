package com.productservice.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class ITemRequest {
    private String name;
    private String description;
    private Integer stock;
    private Long category_id;
    private Integer amount;
    private String quantity;
    private Date date;
    private String image;
}
