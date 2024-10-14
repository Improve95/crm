package ru.improve.crm.error.responseBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

@Data
@Setter
@AllArgsConstructor
public class CustomErrorResponse {

    private String message;

    private Set<String> fieldsWithError;

    private Instant time;

    public CustomErrorResponse(String message, Set<String> fieldsWithError) {
        this.message = message;
        this.fieldsWithError = fieldsWithError;
        time = LocalDateTime.now().toInstant(ZoneOffset.UTC);
    }
}
