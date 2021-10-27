package io.metadata.school.service;

import io.metadata.school.model.dto.CourseDto;
import io.metadata.school.model.entity.Course;
import io.metadata.school.model.entity.Student;
import io.metadata.school.model.exception.NotFoundException;
import io.metadata.school.model.exception.ValidationException;
import io.metadata.school.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Value("${settings.max-students-per-course}")
    private Integer maxStudentsPerCourse = 50;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    public List<CourseDto> findAll() {
        return courseRepository.findAll().stream()
                .map(CourseDto::convert)
                .collect(Collectors.toList());
    }

    public Course getById(@NotNull Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found with id " + id));
    }

    public CourseDto create(@NotNull String name) {
        Course course = courseRepository.save(
                Course.builder()
                        .name(name)
                        .build()
        );

        return CourseDto.convert(course);
    }

    public CourseDto update(@NotNull Long id, @NotNull String name) {
        Course course = getById(id);
        course.setName(name);

        course = courseRepository.save(course);

        return CourseDto.convert(course);
    }

    public void deleteById(@NotNull Long id) {
        courseRepository.deleteById(id);
    }

    public void validateAddStudent(Course course) {
        List<Student> students = course.getStudents();

        if (students != null && students.size() >= maxStudentsPerCourse) {
            throw new ValidationException("Course is full, not possible to assign student");
        }
    }

    public List<CourseDto> findAllWithoutStudents() {
        return courseRepository.findAllByStudentsIsNull().stream()
                .map(CourseDto::convert)
                .collect(Collectors.toList());
    }
}
