package io.metadata.school.model.dto;

import io.metadata.school.model.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CourseDto {
    private Long id;
    private String name;

    public static CourseDto convert(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .build();
    }
}
