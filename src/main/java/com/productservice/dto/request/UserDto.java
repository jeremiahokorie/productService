package com.productservice.dto.request;

import com.productservice.persistence.entity.User;
import lombok.Data;

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
        return userDto;
    }
}
