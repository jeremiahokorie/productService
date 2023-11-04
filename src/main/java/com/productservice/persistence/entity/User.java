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
    /**
     * define column name and their length.
     * For example, @Column(name = "name", length=20)
     * Do same for all the fields here
     */
    @Column(name = "name", length=50)
    private String name;
    @Column(name = "email", length=55)
    private String email;
    @Column(name = "phone", length=20)
    private String phone;
    @Column(name = "password", length=80)
    private String password;
    @Column(name = "role", length=20)
    private String role;
}
