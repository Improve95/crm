package ru.improve.crm.error.exceptions;

import java.util.List;

public class InDtoException extends CustomCrmException {

    public InDtoException(String message, List<String> fieldsWithError) {
        super(message, fieldsWithError);
    }
}
