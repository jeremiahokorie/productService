package com.productservice.core.security;

import lombok.*;

import java.io.Serializable;

/**
 * Project title: authservice
 *
 * @author johnadeshola
 * Date: 9/21/23
 * Time: 1:30 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtAuthenticationRequest implements Serializable {
    private String email;
    private String password;
}
