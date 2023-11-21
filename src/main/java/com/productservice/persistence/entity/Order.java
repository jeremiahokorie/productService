package com.productservice.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "Order")
public class Order {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne
    private Item item;
    @Column(name = "created_date")
    private Date date;
    private Integer qty;

    @ManyToOne
    private ShippingAddress address;

    @ManyToOne
    private Category category;

}
