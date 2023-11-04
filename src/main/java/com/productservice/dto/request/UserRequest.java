package com.productservice.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequest {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String role;
    private String address;
    private String city;
    private String state;
    private String username;
}
