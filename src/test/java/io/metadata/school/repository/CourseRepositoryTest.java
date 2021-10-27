package io.metadata.school.repository;

import io.metadata.school.TestFixtures;
import io.metadata.school.model.entity.Course;
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
class CourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void testCreate() {
        Course course1 = courseRepository.save(TestFixtures.buildCourse(1L));
        Course course2 = courseRepository.save(TestFixtures.buildCourse(2L));

        List<Course> courses = List.of(course1, course2);

        assertThat(courseRepository.findAll()).isEqualTo(courses);
    }

    @Test
    void testCreateWithStudents() {
        Student student1 = studentRepository.save(TestFixtures.buildStudent(1L, null));
        Student student2 = studentRepository.save(TestFixtures.buildStudent(2L, null));

        Course course1 = courseRepository.save(TestFixtures.buildCourse(1L, student1));
        Course course2 = courseRepository.save(TestFixtures.buildCourse(2L, student2));

        assertThat(courseRepository.findAll())
                .isEqualTo(List.of(course1, course2));
    }

    @Test
    void testFindById() {
        Course course1 = courseRepository.save(TestFixtures.buildCourse(1L));
        Course course2 = courseRepository.save(TestFixtures.buildCourse(2L));

        assertThat(courseRepository.findById(course1.getId())).isEqualTo(Optional.of(course1));
        assertThat(courseRepository.findById(course2.getId())).isEqualTo(Optional.of(course2));
    }

    @Test
    void testDeleteById() {
        Course course1 = courseRepository.save(TestFixtures.buildCourse(1L));
        Course course2 = courseRepository.save(TestFixtures.buildCourse(2L));

        assertThat(courseRepository.findAll()).isEqualTo(List.of(course1, course2));

        courseRepository.deleteById(course2.getId());

        assertThat(courseRepository.findAll()).isEqualTo(List.of(course1));
    }
}
