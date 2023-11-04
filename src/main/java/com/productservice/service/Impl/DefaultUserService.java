package com.productservice.service.Impl;

import com.productservice.core.exceptions.CustomException;
import com.productservice.core.exceptions.ErrorDetail;
import com.productservice.dto.enums.ErrorCode;
import com.productservice.dto.request.UserDto;
import com.productservice.dto.request.UserRequest;
import com.productservice.dto.response.UserResponse;
import com.productservice.events.EmailNotificationEvent;
import com.productservice.persistence.entity.User;
import com.productservice.persistence.repository.UserRepository;
import com.productservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUserService implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher publisher;
    private final ObjectMapper mapper;
    private ModelMapper modelMapper;


    @Override
    public UserDto create(UserRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(request.getRole())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .username(request.getUsername())
                .build();
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.NOT_FOUND,"Try with a different Email Address");
        }

        userRepository.save(user);
        publisher.publishEvent(new EmailNotificationEvent(this, "welcome", ImmutableMap.of("recipient", user.getEmail(), "name", user.getName())));
        return UserDto.fromEntity(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomException("User not found"));
    }

    @Override
    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new CustomException("User not found"));
    }

    @Override
    public UserResponse updateUser(UserRequest request, Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("User not found"));
        modelMapper.map(request, user);
        userRepository.save(user);
        return modelMapper.map(user, UserResponse.class);
    }



}
