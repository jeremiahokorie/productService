package com.productservice.service;


import com.productservice.core.security.JwtAuthenticationRequest;
import com.productservice.core.security.UserTokenState;


public interface AuthenticationService {

    public UserTokenState createAuthenticationToken(JwtAuthenticationRequest authenticationRequest);
}
