package io.metadata.school.repository;

import io.metadata.school.TestFixtures;
import io.metadata.school.model.entity.Student;
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
class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    void testCreate() {
        Student student1 = studentRepository.save(TestFixtures.buildStudent(1L, null));
        Student student2 = studentRepository.save(TestFixtures.buildStudent(2L, null));

        assertThat(studentRepository.findAll())
                .isEqualTo(List.of(student1, student2));
    }

    @Test
    void testFindById() {
        Student student1 = studentRepository.save(TestFixtures.buildStudent(1L, null));
        Student student2 = studentRepository.save(TestFixtures.buildStudent(2L, null));

        assertThat(studentRepository.findById(student1.getId())).isEqualTo(Optional.of(student1));
        assertThat(studentRepository.findById(student2.getId())).isEqualTo(Optional.of(student2));
    }

    @Test
    void testDeleteById() {
        Student student1 = studentRepository.save(TestFixtures.buildStudent(1L, null));
        Student student2 = studentRepository.save(TestFixtures.buildStudent(2L, null));

        assertThat(studentRepository.findAll()).isEqualTo(List.of(student1, student2));

        studentRepository.deleteById(student2.getId());

        assertThat(studentRepository.findAll()).isEqualTo(List.of(student1));
    }
}
