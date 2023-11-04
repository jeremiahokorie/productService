package com.productservice.core.installer;


import com.productservice.dto.enums.Role;
import com.productservice.persistence.entity.User;
import com.productservice.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Project title: authservice
 *
 * @author johnadeshola
 * Date: 9/21/23
 * Time: 12:54 PM
 */
@Service
@RequiredArgsConstructor
public class DefaultInstaller implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private boolean alreadySetup = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        createUserIfNotExist("superadmin@gmail.com", "Super Admin", "080", Role.ADMIN.name());
        createUserIfNotExist("jerry@gmail.com", "Jerry", "080", Role.USER.name());

        alreadySetup = true;
    }

    protected void createUserIfNotExist(String email, String name, String phone, String role) {
        userRepository.findByEmail(email).orElseGet(() -> userRepository.save(User.builder()
                .email(email)
                .password(passwordEncoder.encode("welcome"))
                .name(name)
                .phone(phone)
                .role(role)
                .build()));
    }
}
