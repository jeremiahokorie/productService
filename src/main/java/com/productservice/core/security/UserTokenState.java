package com.productservice.core.security;

import lombok.*;

import java.io.Serializable;


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
