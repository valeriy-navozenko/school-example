package io.metadata.school.service;

import io.metadata.school.TestFixtures;
import io.metadata.school.model.entity.User;
import io.metadata.school.model.enums.UserRole;
import io.metadata.school.model.exception.NotFoundException;
import io.metadata.school.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private SecurityService securityService;

    private UserService userService;

    @BeforeEach
    void initialize() {
        userService = new UserService(securityService, userRepository);
    }

    @Test
    void testGetById() {
        User user = TestFixtures.buildUser(1L);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThat(userService.getById(1L)).isEqualTo(user);
    }

    @Test
    void testGetByIdNotFound() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getById(1L));
    }

    @Test
    void testSave() {
        User user = TestFixtures.buildUser(1L);

        Mockito.when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.save(user);

        Mockito.verify(userRepository).save(user);
        assertThat(savedUser).isEqualTo(user);
    }

    @Test
    void testFindByEmail() {
        User user = TestFixtures.buildUser(1L);

        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertThat(userService.findByEmail(user.getEmail())).isEqualTo(Optional.of(user));
    }

    @Test
    void testCreateAdmin() {
        User user = TestFixtures.buildUser(1L, UserRole.ROLE_ADMIN);

        Mockito.when(securityService.encryptPassword("password")).thenReturn("password");

        userService.createAdmin(user.getEmail(), user.getFirstName(), user.getSecondName(), "password");

        Mockito.verify(userRepository).save(user);
    }

    @Test
    void testCreateStudent() {
        User user = TestFixtures.buildUser(1L, UserRole.ROLE_STUDENT);

        Mockito.when(securityService.encryptPassword("password")).thenReturn("password");

        userService.createStudent(user.getEmail(), user.getFirstName(), user.getSecondName(), "password");

        Mockito.verify(userRepository).save(user);
    }
}
