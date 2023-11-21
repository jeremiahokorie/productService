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
public class Item {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "description", length = 1000)
    private String description;
    @Column(name = "stock")
    private Integer stock;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "quantity")
    private String quantity;
    @Column(name = "created_date")
    private Date date;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

}
