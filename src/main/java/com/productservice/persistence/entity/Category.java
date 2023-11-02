package com.productservice.persistence.entity;

import com.productservice.dto.request.CategoryDto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Category extends CategoryDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}
