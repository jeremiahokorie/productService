package com.productservice.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    @Column(name = "created_date")
    private Date date;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
