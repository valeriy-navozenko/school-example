package io.metadata.school.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class SecurityService {

    public String encryptPassword(@NotNull String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
