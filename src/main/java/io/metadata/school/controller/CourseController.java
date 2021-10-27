package io.metadata.school.controller;

import io.metadata.school.model.dto.CourseDto;
import io.metadata.school.model.dto.request.CreateCourseRequest;
import io.metadata.school.model.dto.request.UpdateCourseRequest;
import io.metadata.school.service.CourseService;
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
@RequestMapping("/api/course/v1")
public class CourseController implements BaseRestController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CourseDto>> findAll() {
        return ResponseEntity.ok(courseService.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<CourseDto> create(@RequestBody CreateCourseRequest request) {
        return ResponseEntity.ok(
                courseService.create(request.getName())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> update(@PathVariable("id") Long id, @RequestBody UpdateCourseRequest request) {
        return ResponseEntity.ok(
                courseService.update(id, request.getName())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        courseService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
