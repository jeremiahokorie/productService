package com.productservice.core.util;

import com.productservice.core.security.CustomUserDetailsService;
import com.productservice.persistence.entity.User;
import com.productservice.persistence.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import static com.productservice.core.constants.AppDetails.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@Service
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token) {
        final String username = extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String createToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("authority", new SimpleGrantedAuthority(role));

        Date now = new Date();
        Date validity = new Date(now.getTime() + EXPIRE_IN);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        log.info("username: {}", username);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Optional<User> optionalUser = userRepository.findByEmail(getUsername(token));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new UsernamePasswordAuthenticationToken(userDetails, user.getRole(), userDetails.getAuthorities());
        }
        return null;
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

}
