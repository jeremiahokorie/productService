package com.productservice.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class ShippingAddressDto {
    private String address;
    private String city;
    private String state;
    private String phone;
    private Date date;

}
