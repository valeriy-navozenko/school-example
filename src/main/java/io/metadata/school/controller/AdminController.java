package io.metadata.school.controller;

import io.metadata.school.model.dto.CourseDto;
import io.metadata.school.model.dto.StudentDto;
import io.metadata.school.model.dto.StudentWithoutCoursesDto;
import io.metadata.school.model.entity.Course;
import io.metadata.school.model.entity.Student;
import io.metadata.school.service.CourseService;
import io.metadata.school.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/v1")
public class AdminController implements BaseRestController {

    private final CourseService courseService;

    private final StudentService studentService;

    @Autowired
    public AdminController(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @GetMapping("/course/{courseId}/students")
    public ResponseEntity<List<StudentWithoutCoursesDto>> findAllStudentsForCourse(@PathVariable("courseId") Long courseId) {
        Course course = courseService.getById(courseId);

        return ResponseEntity.ok(
                course.getStudents().stream()
                        .map(StudentWithoutCoursesDto::convert)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/course/find-all-without-students")
    public ResponseEntity<List<CourseDto>> findAllCourseWithoutStudents() {
        return ResponseEntity.ok(courseService.findAllWithoutStudents());
    }

    @GetMapping("/student/{studentId}/courses")
    public ResponseEntity<List<CourseDto>> findAllCoursesForStudent(@PathVariable("studentId") Long studentId) {
        Student student = studentService.getById(studentId);

        return ResponseEntity.ok(
                student.getCourses().stream()
                        .map(CourseDto::convert)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/student/find-all-without-courses")
    public ResponseEntity<List<StudentDto>> findAllStudentWithoutCourses() {
        return ResponseEntity.ok(studentService.findAllWithoutCourses());
    }
}
