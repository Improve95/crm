package ru.improve.crm.error.exceptions;

import java.util.List;

public class AlreadyException extends CustomCrmException {

    public AlreadyException(String message, List<String> fieldsWithError) {
        super(message, fieldsWithError);
    }
}
