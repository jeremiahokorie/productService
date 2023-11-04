package com.productservice.service.Impl;

import com.productservice.dto.request.UserDto;
import com.productservice.dto.request.UserRequest;
import com.productservice.events.EmailNotificationEvent;
import com.productservice.persistence.entity.User;
import com.productservice.persistence.repository.UserRepository;
import com.productservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public UserDto create(UserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setPhone(userRequest.getPhone());
        user.setRole(userRequest.getRole());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User users = userRepository.save(user);
        UserDto userDto = new UserDto();
        userDto.setEmail(users.getEmail());
        userDto.setName(users.getName());
        userDto.setPhone(users.getPhone());
        publisher.publishEvent(new EmailNotificationEvent(this, "welcome", ImmutableMap.of("recipient", user.getEmail(), "name", user.getName())));
        return userDto;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
