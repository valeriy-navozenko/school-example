package io.metadata.school.service;

import io.metadata.school.model.entity.User;
import io.metadata.school.model.enums.UserRole;
import io.metadata.school.model.exception.NotFoundException;
import io.metadata.school.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final SecurityService securityService;

    @Autowired
    public UserService(SecurityService securityService, UserRepository userRepository) {
        this.securityService = securityService;
        this.userRepository = userRepository;
    }

    public User getById(@NotNull Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id " + id));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(@NotNull String email) {
        return userRepository.findByEmail(email);
    }

    public User createStudent(@NotNull String email, @NotNull String firstName, @NotNull String secondName, @NotNull String password) {
        return createUser(email, firstName, secondName, password, UserRole.ROLE_STUDENT);
    }

    public User createAdmin(@NotNull String email, @NotNull String firstName, @NotNull String secondName, @NotNull String password) {
        return createUser(email, firstName, secondName, password, UserRole.ROLE_ADMIN);
    }

    private User createUser(@NotNull String email, @NotNull String firstName, @NotNull String secondName, @NotNull String password, @NotNull UserRole role) {
        String encodedPassword = securityService.encryptPassword(password);

        return userRepository.save(
                User.builder()
                        .email(email)
                        .firstName(firstName)
                        .secondName(secondName)
                        .password(encodedPassword)
                        .role(role)
                        .build()
        );
    }

}
