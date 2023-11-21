package com.productservice.dto.request;

import com.productservice.persistence.entity.Item;
import com.productservice.persistence.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
public class CartDto {
    private long itemId;
    private int quantity;
    private Long cartId;
    private Integer total;
    private String status;
    private Date date;
    private long userId;

//    {
//        "item": {
//            "id": 1,
//            "name": "item1",
//            "price": 100,
//            "description": "item1",
//            "category": "category1"
//        },
//        "quantity": 1,
//        "cartId": 1,
//        "total": 100,
//        "status": "active",
//        "date": "2021-09-28T10:00:00.000+00:00"
//    }

    // post request


}
