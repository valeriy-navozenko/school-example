package io.metadata.school.service;

import io.metadata.school.TestFixtures;
import io.metadata.school.model.entity.Course;
import io.metadata.school.model.exception.NotFoundException;
import io.metadata.school.repository.CourseRepository;
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
class CourseServiceTest {
    @MockBean
    private CourseRepository courseRepository;

    private CourseService courseService;

    @BeforeEach
    void initialize() {
        courseService = new CourseService(courseRepository);
    }

    @Test
    void testGetById() {
        Course course = TestFixtures.buildCourse(1L);

        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        assertThat(courseService.getById(1L)).isEqualTo(course);
    }

    @Test
    void testGetByIdNotFound() {
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> courseService.getById(1L));
    }
}
