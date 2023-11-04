package com.productservice.core.constants;

import java.text.SimpleDateFormat;

public interface AppDetails {
    public static final Long EXPIRE_IN = 36000000L;
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
}
