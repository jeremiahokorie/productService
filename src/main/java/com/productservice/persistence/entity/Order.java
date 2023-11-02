package com.productservice.persistence.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Order")
public class Order {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne
    private ITem item;
    @Column(name = "created_date")
    private Date date;
    private Integer qty;

    @ManyToOne
    private ShippingAddress address;

    @ManyToOne
    private Category category;

}
