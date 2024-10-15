package ru.improve.crm.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import ru.improve.crm.controllers.imp.SellerControllerImp;
import ru.improve.crm.dto.seller.SellerGetResponse;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;
import ru.improve.crm.services.imp.SellerServiceImp;
import ru.improve.crm.validators.imp.SellerValidatorImp;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;

@ExtendWith(MockitoExtension.class)
public class SellerControllerTest {

    @Mock
    SellerServiceImp sellerService;

    @Mock
    SellerValidatorImp sellerValidator;

    @InjectMocks
    SellerControllerImp sellerController;

    @Test
    void getAllSellers_ReturnsValidSeller() {
        //given
        var sellers = List.of(new SellerGetResponse(1, "name0", "contact0", LocalDateTime.now()),
                new SellerGetResponse(2, "name1", "contact1", LocalDateTime.now()));
        doReturn(sellers).when(this.sellerService).getAllSellers();

        //when
        var responseEntity = this.sellerController.getAllSellers();

        //then
        assertNotNull(responseEntity);
        assertEquals(2, responseEntity.size());
        assertEquals(sellers, responseEntity);
    }

    @Test
    void getSellerById_ReturnsValidSeller() {
        // given
        SellerGetResponse sellerExpect = new SellerGetResponse(2, "name1", "contact1", LocalDateTime.now()) ;
        doReturn(sellerExpect).when(this.sellerService).getSellerById(2);

        //when
        SellerGetResponse seller = this.sellerController.getSellerById(2);

        //then
        assertNotNull(seller);
        assertEquals(sellerExpect, seller);
    }

    @Test
    void saveSeller_PostSellerIsValid_ReturnsValidSellerPostResponse() {
        // given
        LocalDateTime regTime = LocalDateTime.now();

        SellerPostRequest postRequest = new SellerPostRequest("name0", "contact0");
        SellerPostResponse postResponse = new SellerPostResponse(1, regTime);

        doNothing().when(sellerValidator).validate(postRequest, bindingResult);
        doReturn(postResponse).when(sellerService.saveSeller(postRequest));

        // when
        SellerPostResponse result = sellerController.saveSeller(postRequest, bindingResult);

        // then
        assertEquals(1, result.getId());
        assertEquals(regTime, result.getRegistrationDate());

//        verify(sellerValidator, times(1)).validate(postRequest, bindingResult);
        verify(sellerService, times(1)).saveSeller(postRequest);
    }
}
