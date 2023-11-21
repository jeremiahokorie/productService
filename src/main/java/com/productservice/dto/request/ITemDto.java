package com.productservice.dto.request;

import com.productservice.persistence.entity.Category;
import com.productservice.persistence.entity.Location;
import lombok.Data;

@Data
public class ITemDto {
    private String name;
    private String description;
    private Integer Stock;
    private Category category;
    private Integer amount;
    private String quantity;
    private String image;
    private Location location;
}
