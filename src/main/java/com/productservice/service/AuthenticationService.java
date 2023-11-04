package com.productservice.service;


import com.productservice.core.security.JwtAuthenticationRequest;
import com.productservice.core.security.UserTokenState;

/**
 * Project title: authservice
 *
 * @author johnadeshola
 * Date: 9/21/23
 * Time: 2:03 PM
 */
public interface AuthenticationService {

    public UserTokenState createAuthenticationToken(JwtAuthenticationRequest authenticationRequest);
}
