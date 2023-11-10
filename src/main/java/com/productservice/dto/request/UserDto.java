package com.productservice.dto.request;

import com.productservice.persistence.entity.User;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String address;
    private String city;
    private String state;
    private String role;
    private String username;
    private Date lastLoginDate;
    private String lastLoginIp;
    private Integer userLock;
    private Date userLockDate;
    private Date lastPasswordResetDate;
    private Date updatedAt;
    private String userId;
    private String userImage;


    public static UserDto fromEntity(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setPassword(user.getPassword());
        userDto.setAddress(user.getAddress());
        userDto.setCity(user.getCity());
        userDto.setState(user.getState());
        userDto.setRole(user.getRole());
        userDto.setUsername(user.getUsername());
        userDto.setLastLoginDate(user.getLastLoginDate());
        userDto.setLastLoginIp(user.getLastLoginIp());
        userDto.setUserLock(user.getUserLock());
        userDto.setUserLockDate(user.getUserLockDate());
        userDto.setLastPasswordResetDate(user.getLastPasswordResetDate());
        return userDto;
    }
}
