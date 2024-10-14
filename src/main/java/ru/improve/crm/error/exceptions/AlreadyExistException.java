package ru.improve.crm.error.exceptions;

import java.util.List;

public class AlreadyExistException extends CustomCrmException {

    public AlreadyExistException(String message, List<String> fieldsWithError) {
        super(message, fieldsWithError);
    }
}
