package com.productservice.persistence.entity;

import lombok.Data;

@Data
public class EmailEntity {

    private String to;
    private String subject;
    private String message;
}
