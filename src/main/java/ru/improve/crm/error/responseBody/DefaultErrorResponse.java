package ru.improve.crm.error.responseBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@Setter
@AllArgsConstructor
public class DefaultErrorResponse {

    public DefaultErrorResponse(String message) {
        this.message = message;
        this.time = LocalDateTime.now().toInstant(ZoneOffset.UTC);
    }

    private String message;

    private Instant time;
}
