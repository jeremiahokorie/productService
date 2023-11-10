package com.productservice.service.Impl;


import com.productservice.core.security.JwtAuthenticationRequest;
import com.productservice.core.security.UserTokenState;
import com.productservice.core.util.JwtUtil;
import com.productservice.persistence.entity.User;
import com.productservice.persistence.repository.UserRepository;
import com.productservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.productservice.core.constants.AppDetails.EXPIRE_IN;


/**
 * Project title: authservice
 *
 * @author johnadeshola
 * Date: 9/21/23
 * Time: 2:04 PM
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Override
    public UserTokenState createAuthenticationToken(JwtAuthenticationRequest authenticationRequest) {

        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        // Inject into security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // token creation
        User user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new BadCredentialsException("User not found"));
        user.setLastLoginDate(new Date());
        userRepository.save(user);
        String jwtToken = jwtUtil.createToken(user.getEmail(), user.getRole());
        return new UserTokenState(jwtToken, EXPIRE_IN);
    }
}
