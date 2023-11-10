package com.productservice.config;

import com.productservice.core.security.CustomCorsFilter;
import com.productservice.core.security.JwtTokenFilterConfigurer;
import com.productservice.core.security.RestAccessDeniedHandler;
import com.productservice.core.security.RestAuthenticationEntryPoint;
import com.productservice.core.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final RestAccessDeniedHandler accessDeniedHandler;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception {
        http.apply(new JwtTokenFilterConfigurer(jwtUtil));
        http
                .securityMatcher(new RequestHeaderRequestMatcher("Authorization"))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login", "/signup").permitAll();
                    authorize.requestMatchers("/swagger-ui**").permitAll();
                    authorize.anyRequest().authenticated();

                })
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint))
                .headers((header) -> {
                    header.defaultsDisabled();
                    header.cacheControl(Customizer.withDefaults());
                    header.xssProtection(Customizer.withDefaults());
                    header.frameOptions(Customizer.withDefaults());
                    header.httpStrictTransportSecurity(Customizer.withDefaults());
                })
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new CustomCorsFilter(), ChannelProcessingFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.httpFirewall(allowUrlEncodedSlashHttpFirewall())
                .ignoring()
                .requestMatchers("/v1/api-docs")
                .requestMatchers("/v2/api-docs")
                .requestMatchers("/swagger-resources/**")
                .requestMatchers("/swagger-ui/index.html")
                .requestMatchers("/configuration/**")
                .requestMatchers("/webjars/**")
                .requestMatchers("/images/**")
                .requestMatchers("/css/**")
                .requestMatchers("/assets/**")
                .requestMatchers("/js/**")
                .requestMatchers("/fonts/**")
                .requestMatchers("/favicon.ico")
                .requestMatchers("/public/**")
                .requestMatchers("/actuator/**");
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }


 //authenticate other endpoint except login and signup
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.apply(new JwtTokenFilterConfigurer(jwtUtil));
        http
                .securityMatcher(new RequestHeaderRequestMatcher("Authorization"))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login", "/signup").permitAll();
                    authorize.requestMatchers("/swagger-ui**").permitAll();
                    authorize.anyRequest().authenticated();
                });
        return http.build();
    }


}