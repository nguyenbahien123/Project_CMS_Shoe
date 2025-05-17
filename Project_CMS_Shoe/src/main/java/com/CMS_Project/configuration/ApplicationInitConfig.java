package com.CMS_Project.configuration;

import com.CMS_Project.constant.PredefinedRole;
import com.CMS_Project.entity.Roles;
import com.CMS_Project.entity.Users;
import com.CMS_Project.repository.RoleRepository;
import com.CMS_Project.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_EMAIL = "admin@gmail.com";

    @NonFinal
    static final String ADMIN_PASSWORD = "123456";

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            if(!userRepository.existsByEmail(ADMIN_EMAIL)) {
                roleRepository.save(Roles.builder()
                        .name(PredefinedRole.USER_ROLE)
                        .description("User role")
                        .build());

                Roles adminRole = roleRepository.save(Roles.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .description("Admin role")
                        .build());

                Users admin = Users.builder()
                        .email(ADMIN_EMAIL)
                        .passwordHash(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(adminRole)
                        .build();

                userRepository.save(admin);
                log.warn("admin user has been created with default email:'admin@gamil.com', password: '123456', please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}
