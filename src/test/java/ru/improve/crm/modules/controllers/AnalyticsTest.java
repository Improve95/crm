package ru.improve.crm.modules.controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import ru.improve.crm.controllers.SellerController;
import ru.improve.crm.dto.seller.MostProductivityByPeriodRequest;
import ru.improve.crm.dto.seller.SellerDataResponse;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;
import ru.improve.crm.dto.seller.WithLessAmountByPeriodRequest;
import ru.improve.crm.error.exceptions.InDtoException;
import ru.improve.crm.models.Seller;
import ru.improve.crm.services.imp.SellerServiceImp;
import ru.improve.crm.validators.imp.SellerValidatorImp;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class AnalyticsTest {

    @Mock
    SellerServiceImp sellerService;

    @Mock
    SellerValidatorImp sellerValidator;

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    SellerController sellerController;

    List<SellerPostRequest> sellerPostRequests;
    List<Seller> sellers;
    List<SellerPostResponse> sellerPostResponses;
    List<SellerDataResponse> sellerDataResponses;

    LocalDateTime dateTime = LocalDateTime.now();
    LocalDateTime periodStart = LocalDateTime.now().minus(Period.ofDays(1));
    LocalDateTime periodEnd = LocalDateTime.now().plus(Period.ofDays(1));

    @BeforeEach
    void intialLists() {
        sellerPostRequests = List.of(
                new SellerPostRequest("name1", "contact1"),
                new SellerPostRequest("name", "contact2")
        );
        sellers = List.of(
                new Seller(1, "name1", "contact1", dateTime),
                new Seller(2, "name2", "contact2", dateTime)
        );
        sellerPostResponses = List.of(
                new SellerPostResponse(1, dateTime),
                new SellerPostResponse(2, dateTime)
        );
        sellerDataResponses = List.of(
                new SellerDataResponse(1, "name1", "contact1", dateTime),
                new SellerDataResponse(2, "name2", "contact2", dateTime)
        );
    }

    @Test
    void getMostProductivitySellerByPeriod_RequestDataIsValid_ReturnsSeller() {
        //given
        MostProductivityByPeriodRequest req = new MostProductivityByPeriodRequest(periodStart, periodEnd);
        doNothing().when(this.sellerValidator).validate(req, bindingResult);
        doReturn(sellerDataResponses.get(0)).when(this.sellerService).getMostProductivitySellerByPeriod(req);

        //when
        var re= sellerController.getMostProductivitySellerByPeriod(req, bindingResult);

        //then
        assertNotNull(re);
        assertEquals(HttpStatus.OK, re.getStatusCode());
        assertEquals(sellerDataResponses.get(0), re.getBody());
        verify(sellerValidator).validate(req, bindingResult);
        verify(sellerService).getMostProductivitySellerByPeriod(req);
    }

    @Test
    void getMostProductivitySellerByPeriod_RequestDataNotValid_ReturnsSeller() {
        //given
        MostProductivityByPeriodRequest req = new MostProductivityByPeriodRequest(periodStart, periodEnd);
        doThrow(new InDtoException("dto exception", List.of(""))).when(this.sellerValidator).validate(req, bindingResult);

        //when
        InDtoException ex = assertThrows(InDtoException.class, () ->
                sellerController.getMostProductivitySellerByPeriod(req, bindingResult));

        //then
        assertNotNull(ex);
        assertEquals("dto exception", ex.getMessage());
        verifyNoInteractions(sellerService);
    }

    @Test
    void getSellersWithLessAmountByPeriod_RequestDataIsValid_ReturnsSeller() {
        //given
        WithLessAmountByPeriodRequest req = new WithLessAmountByPeriodRequest(1, periodStart, periodEnd);
        doNothing().when(this.sellerValidator).validate(req, bindingResult);
        doReturn(sellerDataResponses).when(this.sellerService).getSellersWithLessAmountByPeriod(req);

        //when
        var re= sellerController.getSellersWithLessAmountByPeriod(req, bindingResult);

        //then
        assertNotNull(re);
        assertEquals(HttpStatus.OK, re.getStatusCode());
        assertEquals(sellerDataResponses, re.getBody());
        verify(sellerValidator).validate(req, bindingResult);
        verify(sellerService).getSellersWithLessAmountByPeriod(req);
    }

    @Test
    void getSellersWithLessAmountByPeriod_RequestDataNotValid_ReturnsSeller() {
        //given
        WithLessAmountByPeriodRequest req = new WithLessAmountByPeriodRequest(1, periodStart, periodEnd);
        doThrow(new InDtoException("dto exception", List.of(""))).when(this.sellerValidator).validate(req, bindingResult);

        //when
        InDtoException ex = assertThrows(InDtoException.class, () ->
                sellerController.getSellersWithLessAmountByPeriod(req, bindingResult));

        //then
        assertNotNull(ex);
        assertEquals("dto exception", ex.getMessage());
        verifyNoInteractions(sellerService);
    }
}
