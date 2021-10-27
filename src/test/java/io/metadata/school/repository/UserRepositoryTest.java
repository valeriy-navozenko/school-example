package io.metadata.school.repository;

import io.metadata.school.TestFixtures;
import io.metadata.school.model.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByEmail() {
        User user1 = userRepository.save(TestFixtures.buildUser(1L));
        User user2 = userRepository.save(TestFixtures.buildUser(2L));

        assertThat(userRepository.findByEmail(user1.getEmail())).isEqualTo(Optional.of(user1));
        assertThat(userRepository.findByEmail(user2.getEmail())).isEqualTo(Optional.of(user2));
        assertThat(userRepository.findByEmail("test" + user2.getEmail())).isNotPresent();
    }

    @Test
    void testCreate() {
        User user1 = TestFixtures.buildUser(1L);
        User user2 = TestFixtures.buildUser(2L);
        List<User> users = List.of(user1, user2);

        userRepository.saveAll(users);

        assertThat(userRepository.findAll()).isEqualTo(users);
    }

    @Test
    void testFindById() {
        User user1 = TestFixtures.buildUser(1L);
        User user2 = TestFixtures.buildUser(2L);
        List<User> users = List.of(user1, user2);

        userRepository.saveAll(users);

        assertThat(userRepository.findById(user1.getId())).isEqualTo(Optional.of(user1));
        assertThat(userRepository.findById(user2.getId())).isEqualTo(Optional.of(user2));
    }

    @Test
    void testDeleteById() {
        User user1 = userRepository.save(TestFixtures.buildUser(1L));
        User user2 = userRepository.save(TestFixtures.buildUser(2L));
        List<User> users = List.of(user1, user2);

        userRepository.saveAll(users);

        assertThat(userRepository.findAll()).isEqualTo(users);

        userRepository.deleteById(user2.getId());

        assertThat(userRepository.findAll()).isEqualTo(List.of(user1));
    }
}
