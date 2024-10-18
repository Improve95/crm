package ru.improve.crm.unit.controllers;

import org.aspectj.apache.bcel.classfile.Field;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.improve.crm.error.exceptions.InDtoException;
import ru.improve.crm.validators.imp.SellerValidatorImp;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ValidatorTest {

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    SellerValidatorImp sellerValidator;

    @Test
    void createAndThrowException_ThrowInDtoException() {
        List<FieldError> fieldErrors = List.of(
                new FieldError(Field.NoFields.toString(), "f1", "m1"),
                new FieldError(Field.NoFields.toString(), "f2", "m2")
        );
        doReturn(true).when(this.bindingResult).hasErrors();
        doReturn(fieldErrors).when(this.bindingResult).getFieldErrors();

        //when
        InDtoException ex = assertThrows(InDtoException.class, () ->
                sellerValidator.createAndThrowException(bindingResult));

        assertNotNull(ex);
        assertEquals("f1 - m1; f2 - m2; ", ex.getMessage());
        assertEquals(List.of("f1", "f2"), ex.getFieldsWithErrorList());
        verify(bindingResult).hasErrors();
        verify(bindingResult, times(2)).getFieldErrors();
    }
}
