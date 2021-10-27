package io.metadata.school.model.dto;

import io.metadata.school.model.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StudentDto {
    private Long id;

    private UserDto user;

    private List<CourseDto> courses;

    public static StudentDto convert(Student student) {
        StudentDtoBuilder builder = StudentDto
                .builder()
                .id(student.getId())
                .user(UserDto.convert(student.getUser()));

        if (student.getCourses() != null) {
            builder.courses(
                    student.getCourses().stream()
                            .map(CourseDto::convert)
                            .collect(Collectors.toList())
            );
        }

        return builder.build();
    }
}
