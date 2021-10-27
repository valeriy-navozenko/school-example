package io.metadata.school.config;

import io.metadata.school.service.StudentService;
import io.metadata.school.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final String ADMIN_EMAIL = "admin@test.com";

    @Bean
    CommandLineRunner initDatabase(UserService userService, StudentService studentService) {
        return args -> {
            if (userService.findByEmail(ADMIN_EMAIL).isEmpty()) {
                userService.createAdmin(ADMIN_EMAIL, "admin", "", "test");
            }
        };
    }
}
