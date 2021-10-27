package io.metadata.school.model.dto;

import io.metadata.school.model.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StudentWithoutCoursesDto {
    private Long id;

    private UserDto user;

    public static StudentWithoutCoursesDto convert(Student student) {
        return StudentWithoutCoursesDto
                .builder()
                .id(student.getId())
                .user(UserDto.convert(student.getUser()))
                .build();
    }
}
