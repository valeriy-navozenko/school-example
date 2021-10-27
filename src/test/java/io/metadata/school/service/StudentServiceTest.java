package io.metadata.school.service;

import io.metadata.school.TestFixtures;
import io.metadata.school.model.entity.Student;
import io.metadata.school.model.exception.NotFoundException;
import io.metadata.school.repository.StudentRepository;
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
class StudentServiceTest {

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private CourseService courseService;

    private StudentService studentService;

    @BeforeEach
    void initialize() {
        studentService = new StudentService(userService, courseService, studentRepository);
    }

    @Test
    void testGetById() {
        Student student = TestFixtures.buildStudent(1L, null);

        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        assertThat(studentService.getById(1L)).isEqualTo(student);
    }

    @Test
    void testGetByIdNotFound() {
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> studentService.getById(1L));
    }
}
