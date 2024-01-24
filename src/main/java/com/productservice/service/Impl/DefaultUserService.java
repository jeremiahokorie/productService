package com.productservice.service.Impl;

import com.productservice.core.constants.AppConstant;
import com.productservice.core.exceptions.CustomException;
import com.productservice.dto.enums.ErrorCode;
import com.productservice.dto.request.ChangePasswordRequest;
import com.productservice.dto.request.UserDto;
import com.productservice.dto.request.UserRequest;
import com.productservice.dto.response.ChangePasswordResponse;
import com.productservice.dto.response.UserResponse;
import com.productservice.events.EmailNotificationEvent;
import com.productservice.persistence.entity.User;
import com.productservice.persistence.repository.BaseRepository;
import com.productservice.persistence.repository.UserRepository;
import com.productservice.service.UserService;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;
import static com.productservice.core.util.Randomizer.generate;
@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUserService implements UserService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher publisher;
    private final BaseRepository repo;
    private final ObjectMapper mapper;
    private ModelMapper modelMapper;
    String userId = "HBS" + UUID.randomUUID().toString();


    @Override
    public UserDto create(UserRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .phone(request.getPhone())
                .role(request.getRole())
                .password(passwordEncoder.encode(request.getPassword()))
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .username(request.getUsername())
                .userLock(1)
                .userId(userId)
                .userLockDate(new Date())
                .lastLoginDate(new Date())
                .build();
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException("User with this email " + request.getEmail() +". Already exist, Try another email address");

        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new CustomException(" " + request.getEmail() + " Already exist, Try another Username address");

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
    public UserResponse updateUser(UserRequest request, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("User not found"));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setUsername(request.getUsername());
        user.setUpdatedAt(new Date());
        userRepository.save(user);
        return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .address(user.getAddress())
                .city(user.getCity())
                .state(user.getState())
                .username(user.getUsername())
                .build();
    }


    //
    public ChangePasswordResponse changePassword(ChangePasswordRequest request) throws AccessDeniedException {
//        User user = repo.findOne(User.class, AppConstant.getAuthUser()
//                .orElseThrow(() -> new AccessDeniedException("Full authentication required to access this resource")).getId());
        User user = new User();
        if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            repo.dynamicUpdate(User.class, ImmutableMap.of("password", passwordEncoder.encode(request.getNewPassword()), "LastPasswordChange",
                    org.joda.time.DateTime.now().toDate()), ImmutableMap.of("id", user.getId()));
        } else {
            throw new BadRequestException("Invalid old password");
        }
        return ChangePasswordResponse.builder()
                .email(user.getEmail())
                .Id(user.getId())
                .build();
    }


}
