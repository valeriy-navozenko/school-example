package io.metadata.school.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateStudentRequest {
    private String email;

    private String firstName;

    private String secondName;

    private String password;
}