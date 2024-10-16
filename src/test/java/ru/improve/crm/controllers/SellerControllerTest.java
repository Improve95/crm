package ru.improve.crm.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import ru.improve.crm.controllers.imp.SellerController;
import ru.improve.crm.dto.seller.SellerDataResponse;
import ru.improve.crm.dto.seller.SellerPatchRequest;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;
import ru.improve.crm.error.exceptions.InDtoException;
import ru.improve.crm.services.imp.SellerServiceImp;
import ru.improve.crm.validators.imp.SellerValidatorImp;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SellerControllerTest {

    @Mock
    SellerServiceImp sellerService;

    @Mock
    SellerValidatorImp sellerValidator;

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    SellerController sellerController;

    @Test
    void getAllSellers_ReturnsValidSeller() {
        //given
        var sellers = List.of(new SellerDataResponse(1, "name0", "contact0", LocalDateTime.now()),
                new SellerDataResponse(2, "name1", "contact1", LocalDateTime.now()));
        doReturn(sellers).when(this.sellerService).getAllSellers();

        //when
        var re = this.sellerController.getAllSellers();

        //then
        assertNotNull(re);
        assertEquals(HttpStatus.OK, re.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, re.getHeaders().getContentType());
        assertEquals(2, re.getBody().size());
        assertEquals(sellers, re.getBody());
        verify(sellerService).getAllSellers();
    }

    @Test
    void getSellerById_ReturnsValidSeller() {
        // given
        SellerDataResponse sellerExpect = new SellerDataResponse(2, "name1", "contact1", LocalDateTime.now()) ;
        doReturn(sellerExpect).when(this.sellerService).getSellerById(2);

        //when
        var re = this.sellerController.getSellerById(2);

        //then
        assertNotNull(re);
        assertEquals(HttpStatus.OK, re.getStatusCode());
        assertEquals(sellerExpect, re.getBody());
        verify(sellerService).getSellerById(2);
    }

    @Test
    void saveSeller_PostSellerIsValid_ReturnsValidSellerPostResponse() {
        // given
        LocalDateTime regTime = LocalDateTime.now();

        SellerPostRequest postRequest = new SellerPostRequest("name0", "contact0");
        SellerPostResponse postResponse = new SellerPostResponse(1, regTime);
        doReturn(postResponse).when(this.sellerService).saveSeller(postRequest);

        // when
        var re = sellerController.saveSeller(postRequest, bindingResult);

        // then
        assertNotNull(re);
        assertEquals(1, re.getBody().getId());
        assertEquals(HttpStatus.OK, re.getStatusCode());
        assertEquals(regTime, re.getBody().getRegistrationDate());
        verify(sellerValidator).validate(postRequest, bindingResult);
        verify(sellerService).saveSeller(postRequest);
    }

    @Test()
    void saveSeller_PostSellerIsNotValid_ThrowInDtoException() {
        // given
        SellerPostRequest postRequest = new SellerPostRequest("", "contact0");
        doThrow(new InDtoException("", List.of("name"))).when(this.sellerValidator).validate(postRequest, bindingResult);

        // when
        InDtoException inDtoException = assertThrows(InDtoException.class,
                () -> sellerController.saveSeller(postRequest, bindingResult));

        // then
        assertEquals(List.of("name"), inDtoException.getFieldsWithErrorList());
    }

    @Test
    void patchSeller_PatchSellerReqIsValid_ReturnsPatchedSeller() {
        //given
        LocalDateTime regTime = LocalDateTime.now();

        SellerDataResponse dataResponse = new SellerDataResponse(1, "name1", "contact1", regTime);
        SellerPatchRequest patchReq = new SellerPatchRequest("name1", "contact1");
        doReturn(dataResponse).when(this.sellerService).patchSeller(1, patchReq);

        //when
        var re = sellerController.patchSeller(1, patchReq, bindingResult);

        //then
        assertNotNull(re);
        assertEquals(dataResponse, re.getBody());
        assertEquals(HttpStatus.OK, re.getStatusCode());
        verify(sellerValidator).validate(patchReq, bindingResult);
        verify(sellerService).patchSeller(1, patchReq);
    }

    @Test
    void deleteSeller_ReturnsNull() {
        //given
        doNothing().when(this.sellerService).deleteSellerById(1);

        //when
        var re = sellerController.deleteSeller(1);

        //then
        assertNotNull(re);
        assertEquals(HttpStatus.OK, re.getStatusCode());
        assertEquals(null, re.getBody());
        verify(sellerService).deleteSellerById(1);
    }
}
