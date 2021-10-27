package io.metadata.school.controller;

import io.metadata.school.model.dto.StudentDto;
import io.metadata.school.model.dto.request.AddStudentToCourseRequest;
import io.metadata.school.model.dto.request.CreateStudentRequest;
import io.metadata.school.model.dto.request.UpdateStudentRequest;
import io.metadata.school.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student/v1")
public class StudentController implements BaseRestController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDto>> findAll() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<StudentDto> create(@RequestBody CreateStudentRequest request) {
        return ResponseEntity.ok(
                studentService.create(
                        request.getEmail(),
                        request.getFirstName(),
                        request.getSecondName(),
                        request.getPassword()
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> update(@PathVariable("id") Long id, @RequestBody UpdateStudentRequest request) {
        return ResponseEntity.ok(
                studentService.update(
                        id,
                        request.getEmail(),
                        request.getFirstName(),
                        request.getSecondName()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        studentService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-to-course/{id}")
    public ResponseEntity<StudentDto> addToCourse(@PathVariable("id") Long id, @RequestBody AddStudentToCourseRequest request) {
        return ResponseEntity.ok(
                studentService.addToCourse(id, request.getCourseId())
        );
    }
}
