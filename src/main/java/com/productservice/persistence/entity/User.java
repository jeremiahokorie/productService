package com.productservice.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "name", length=50)
    private String name;
    @Column(name = "email", length=55)
    private String email;
    @Column(name = "phone", length=20)
    private String phone;
    @Column(name = "password", length=80)
    private String password;
    @Column(name = "address", length=100)
    private String address;
    @Column(name = "city", length=50)
    private String city;
    @Column(name = "state", length=50)
    private String state;
    @Column(name = "role", length=20)
    private String role;
    @Column(name = "username", length=50)
    private String username;
}
