package com.productservice.core.constants;

import com.productservice.persistence.entity.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Data
@Component
public class AppConstant {
    public static final String APP_CONTEXT = "/api/core/v1";
    public static final String resetPasswordTemplate = "reset-password.ftl";


    @Qualifier("passwordEncoder")
    private final PasswordEncoder encoder;
    @Qualifier("argon2")
    private final PasswordEncoder argon2PasswordEncoder;
    @Qualifier("scrypt")
    private PasswordEncoder scryptPasswordEncoder;


//    @Autowired
//    public AppConstant(@Qualifier("passwordEncoder") PasswordEncoder encoder,
//                        @Qualifier("argon2") PasswordEncoder argon2PasswordEncoder,
//                        @Qualifier("argon2") PasswordEncoder scryptPasswordEncoder) {
//        this.encoder = encoder;
//        this.argon2PasswordEncoder = argon2PasswordEncoder;
//        this.scryptPasswordEncoder = scryptPasswordEncoder;
//    }


    public AppConstant(PasswordEncoder encoder, PasswordEncoder argon2PasswordEncoder) {
        this.encoder = encoder;
        this.argon2PasswordEncoder = argon2PasswordEncoder;
    }

    public static Optional<User> getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (null != auth && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return Optional.of((User) auth.getPrincipal());
        }
        return Optional.empty();
    }


    public static Optional<DefaultOidcUser> getOAuth2User() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (null != auth && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return Optional.of((DefaultOidcUser) auth.getPrincipal());
        }
        return Optional.empty();
    }

    public  String encryptCipher(User user, String newCipher) {
        return user.getUserLevel() == 2 ? "{scrypt}" + scryptPasswordEncoder.encode(newCipher) :
                user.getUserLevel() == 3 ? "{argon2}" + argon2PasswordEncoder.encode(newCipher) : encoder.encode(newCipher);
    }

    @SuppressWarnings("unused")
    public interface ApiResponseMessage {

        String SUCCESSFUL = "Successfully processed";
        String PENDING = "Pending approval";
        String FAILED = "Failed request";
        String UPDATE = "Successfully updated";
        String GET = "Successfully fetched records";

    }
}
