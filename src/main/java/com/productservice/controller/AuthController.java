package com.productservice.controller;

import com.productservice.core.constants.AppConstant;
import com.productservice.core.security.JwtAuthenticationRequest;
import com.productservice.core.security.UserTokenState;
import com.productservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstant.APP_CONTEXT)
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        UserTokenState authenticationToken = authenticationService.createAuthenticationToken(authenticationRequest);
        map.put("Status","200");
        map.put("message","Successfully Logged In");
        map.put("token",authenticationToken);
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
