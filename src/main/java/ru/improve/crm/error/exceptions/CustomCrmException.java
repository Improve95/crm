package ru.improve.crm.error.exceptions;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomCrmException extends RuntimeException {

    public CustomCrmException(String message, List<String> fieldsWithError) {
        this.message = message;
        this.fieldsWithError = fieldsWithError.stream().collect(Collectors.toSet());
        this.time = LocalDateTime.now().toInstant(ZoneOffset.UTC);
    }

    private String message;

    private Set<String> fieldsWithError;

    private Instant time;
}
