package ru.improve.crm.validators.imp;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.improve.crm.dto.seller.MostProductivityByPeriodRequest;
import ru.improve.crm.dto.seller.SellerPatchRequest;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.WithLessAmountByPeriodRequest;
import ru.improve.crm.validators.DefaultCrmValidator;
import ru.improve.crm.validators.SellerValidator;

@Component
public class SellerValidatorImp extends DefaultCrmValidator implements SellerValidator {

    @Override
    public boolean supports(Class<?> clazz) {
        return (clazz.equals(SellerPostRequest.class) ||
                clazz.equals(SellerPatchRequest.class) ||
                clazz.equals(MostProductivityByPeriodRequest.class) ||
                clazz.equals(WithLessAmountByPeriodRequest.class));
    }

    @Override
    public void validate(Object target, Errors errors) {
        createAndThrowException(errors);
    }
}
