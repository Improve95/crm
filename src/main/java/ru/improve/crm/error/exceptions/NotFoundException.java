package ru.improve.crm.error.exceptions;

import java.util.List;

public class NotFoundException extends CustomCrmException {

    public NotFoundException(String message, List<String> fieldsWithError) {
        super(message, fieldsWithError);
    }
}
