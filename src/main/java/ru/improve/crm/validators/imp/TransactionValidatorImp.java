package ru.improve.crm.validators.imp;

import org.springframework.validation.Errors;
import ru.improve.crm.dto.transaction.TransactionPostRequest;
import ru.improve.crm.validators.DefaultCrmValidator;
import ru.improve.crm.validators.TransactionValidator;

public class TransactionValidatorImp extends DefaultCrmValidator implements TransactionValidator {

    @Override
    public boolean supports(Class<?> clazz) {
        return (clazz.equals(TransactionPostRequest.class));
    }

    @Override
    public void validate(Object target, Errors errors) {
        createAndThrowException(errors);
    }
}
