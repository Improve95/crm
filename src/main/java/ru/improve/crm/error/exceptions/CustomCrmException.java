package ru.improve.crm.error.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class CustomCrmException extends RuntimeException {

    public CustomCrmException(String message, List<String> fieldsWithError) {
        this.message = message;
        this.fieldsWithError = fieldsWithError.stream().collect(Collectors.toSet());
        this.time = LocalDateTime.now().toInstant(ZoneOffset.UTC);
    }

    protected String message;

    protected Set<String> fieldsWithError;

    protected Instant time;

    public List<String> getFieldsWithErrorList() {
        return fieldsWithError.stream().toList();
    }
}
