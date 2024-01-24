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
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Item item;
    @Column(name = "created_date")
    private Date date;
    private Integer qty;
    private String status;

    @ManyToOne
    private ShippingAddress address;

    @ManyToOne
    private Category category;

}
