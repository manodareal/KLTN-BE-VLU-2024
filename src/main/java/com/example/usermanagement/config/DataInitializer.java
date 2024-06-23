package com.example.usermanagement.config;

import com.example.usermanagement.domain.entity.User;
import com.example.usermanagement.domain.repo.UserRepository;
import com.example.usermanagement.domain.service.RoleService;
import com.example.usermanagement.domain.service.UserService;
import com.example.usermanagement.util.common.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleService roleService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncrypt passwordEncrypt;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Create default roles
            roleService.createDefaultRole();

            // Check if default admin user exists
            if (userRepository.findUserByEmail("admin@admin.com").isEmpty()) {
                // Create default admin user
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setFullName("Admin User");
                adminUser.setEmail("admin@admin.com");
                adminUser.setPassword(passwordEncrypt.bcryptPassword("12345"));
                adminUser.setRole(roleService.getByRoleName(RoleEnum.ADMIN.getRole()));
                userRepository.save(adminUser);
            }
        };
    }
}
