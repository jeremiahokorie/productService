package com.productservice.controller;


import com.productservice.core.constants.AppConstant;
import com.productservice.dto.request.ChangePasswordRequest;
import com.productservice.dto.request.UserDto;
import com.productservice.dto.request.UserRequest;
import com.productservice.dto.response.AppResponse;
import com.productservice.dto.response.ChangePasswordResponse;
import com.productservice.dto.response.UserResponse;
import com.productservice.persistence.entity.User;
import com.productservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(AppConstant.APP_CONTEXT)
@RequiredArgsConstructor
//@PreAuthorize("isAuthenticated()")
public class UserController {
    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<UserDto> createUser(@RequestBody UserRequest user) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        UserDto userdto = userService.create(user);
        map.put("Status","201");
        map.put("message","User Created successfully");
        map.put("data",userdto);
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public String checkAvailability(){
        return "Available";
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<?> updateCredentials(){
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        List<User> users =  userService.getAllUsers();
        map.put("Status","302");
        map.put("message","User Found ");
        map.put("data",users);
        return new  ResponseEntity(map, HttpStatus.FOUND);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        User user =  userService.getUserById(id);
        map.put("Status","200");
        map.put("message","User Found ");
        map.put("data",user);
        return new  ResponseEntity(map, HttpStatus.FOUND);
    }

    @GetMapping("/getUserByUserName/{username}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String username){
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        User user =  userService.getUserByUserName(username);
        map.put("Status","200");
        map.put("message","User Found ");
        map.put("data",user);
        return new  ResponseEntity(map, HttpStatus.FOUND);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest user, @PathVariable Long id){
       UserResponse userResponse = userService.updateUser(user, id);
         return new ResponseEntity(userResponse, HttpStatus.OK);
    }

    //change password functionality
//    @PostMapping("/changePassword/{id}")
//    public ResponseEntity<UserResponse> changePassword(@RequestBody UserRequest user, @PathVariable Long id){
//       UserResponse userResponse = userService.changePassword(user, id);
//         return new ResponseEntity(userResponse, HttpStatus.OK);
//    }


    @PutMapping(path = "password/change")
    public ResponseEntity<AppResponse<ChangePasswordResponse>> changePassword(@RequestBody @Valid ChangePasswordRequest request) throws AccessDeniedException {

        ChangePasswordResponse passwordChange = userService.changePassword(request);

        AppResponse<ChangePasswordResponse> response = AppResponse.<ChangePasswordResponse>builder().message(AppConstant.ApiResponseMessage.SUCCESSFUL)
                .status(HttpStatus.OK.value()).data(passwordChange).error("").build();
        return ResponseEntity.ok().body(response);
    }

}
