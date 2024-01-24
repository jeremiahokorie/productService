package com.productservice.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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

    @Column(name = "last_login_date")
    private Date lastLoginDate;

    @Column(name = "last_login_ip")
    private String lastLoginIp;

    @Column(name = "user_lock")
    private Integer userLock;

    @Column(name = "user_lock_date")
    private Date userLockDate;

    @Column(name = "last_password_reset_date")
    private Date lastPasswordResetDate;

    @Basic(optional = false)
    @Column(name = "user_level")
    private int userLevel;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_image")
    private String userImage;


}
