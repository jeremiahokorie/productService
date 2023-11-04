package com.productservice.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Cart {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    @OneToMany
    @JoinColumn(name = "item_id")
    private List<ITem> items;
    private int quantity;
    @Column(name = "created_date")
    private Date date;
    private String status;


}
