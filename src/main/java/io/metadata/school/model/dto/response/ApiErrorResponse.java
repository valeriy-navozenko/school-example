package io.metadata.school.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiErrorResponse {
    private String message;
}
