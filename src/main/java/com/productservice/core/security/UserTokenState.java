package com.productservice.core.security;

import lombok.*;

import java.io.Serializable;

/**
 * Project title: authservice
 *
 * @author johnadeshola
 * Date: 9/21/23
 * Time: 1:32 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserTokenState implements Serializable {
    private String access_token;
    private Long expires_in;
}
