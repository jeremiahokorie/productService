package com.productservice.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ITem {

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    private Long id;
    private String name;
    private String description;
    private Integer stock;
    private Integer amount;
    private String quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


}
