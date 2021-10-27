package io.metadata.school.service;

import io.metadata.school.model.dto.StudentDto;
import io.metadata.school.model.entity.Course;
import io.metadata.school.model.entity.Student;
import io.metadata.school.model.entity.User;
import io.metadata.school.model.exception.NotFoundException;
import io.metadata.school.model.exception.ValidationException;
import io.metadata.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    private final UserService userService;

    private final CourseService courseService;

    @Value("${settings.max-courses-per-student}")
    private Integer maxCoursesPerStudent = 5;

    @Autowired
    public StudentService(UserService userService, CourseService courseService, StudentRepository studentRepository) {
        this.userService = userService;
        this.courseService = courseService;
        this.studentRepository = studentRepository;
    }

    public Student getById(@NotNull Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found with id " + id));
    }

    public StudentDto addToCourse(@NotNull Long id, @NotNull Long courseId) {
        Student student = getById(id);
        List<Course> courses = student.getCourses();
        Course assigningCourse = courseService.getById(courseId);

        validateAddToCourse(student, assigningCourse);
        courseService.validateAddStudent(assigningCourse);

        if (courses == null) {
            courses = new ArrayList<>();
        }

        courses.add(assigningCourse);

        return StudentDto.convert(studentRepository.save(student));
    }

    public List<StudentDto> findAll() {
        return studentRepository.findAll().stream()
                .map(StudentDto::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    public StudentDto create(@NotNull String email, @NotNull String firstName, @NotNull String secondName, @NotNull String password) {
        User user = userService.createStudent(email, firstName, secondName, password);

        Student student = studentRepository.save(
                Student.builder()
                        .user(user)
                        .courses(null)
                        .build()
        );

        return StudentDto.convert(student);
    }

    @Transactional
    public StudentDto update(@NotNull Long id, @NotNull String email, @NotNull String firstName, @NotNull String secondName) {
        Student student = getById(id);

        User user = student.getUser();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setSecondName(secondName);

        userService.save(user);

        return StudentDto.convert(getById(id));
    }

    public void deleteById(@NotNull Long id) {
        studentRepository.deleteById(id);
    }

    public List<StudentDto> findAllWithoutCourses() {
        return studentRepository.findAllByCoursesIsNull().stream()
                .map(StudentDto::convert)
                .collect(Collectors.toList());
    }

    private void validateAddToCourse(Student student, Course course) {
        List<Course> courses = student.getCourses();

        if (courses != null) {
            if (courses.size() >= maxCoursesPerStudent) {
                throw new ValidationException(
                        String.format("Student already assigned to '%d' cources and max value is '%d'", courses.size(), maxCoursesPerStudent)
                );
            }

            List<Long> courseIds = courses.stream().map(Course::getId)
                    .collect(Collectors.toList());
            if (courseIds.contains(course.getId())) {
                throw new ValidationException("Student already assigned to the course");
            }
        }
    }
}
