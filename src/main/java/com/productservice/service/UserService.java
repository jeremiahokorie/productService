package com.productservice.service;



import com.productservice.dto.request.ChangePasswordRequest;
import com.productservice.dto.request.UserDto;
import com.productservice.dto.request.UserRequest;
import com.productservice.dto.response.ChangePasswordResponse;
import com.productservice.dto.response.UserResponse;
import com.productservice.persistence.entity.User;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface UserService {
    UserDto create(UserRequest user);

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByUserName(String username);

    UserResponse updateUser(UserRequest user, Long id);

   ChangePasswordResponse changePassword(ChangePasswordRequest request) throws AccessDeniedException;
}
