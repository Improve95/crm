package ru.improve.crm.modules.services;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.improve.crm.dao.imp.SellerDaoImp;
import ru.improve.crm.dto.seller.MostProductivityByPeriodRequest;
import ru.improve.crm.dto.seller.SellerDataResponse;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;
import ru.improve.crm.dto.seller.WithLessAmountByPeriodRequest;
import ru.improve.crm.mappers.SellerMapper;
import ru.improve.crm.models.Seller;
import ru.improve.crm.services.imp.SellerServiceImp;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AnalyticsTests {

    @Mock
    SellerDaoImp sellerDao;

    @Mock
    EntityManager em;

    @Mock
    SellerMapper sellerMapper;

    @InjectMocks
    SellerServiceImp sellerService;

    List<SellerPostRequest> sellerPostRequests;
    List<Seller> sellers;
    List<SellerPostResponse> sellerPostResponses;
    List<SellerDataResponse> sellerDataResponses;

    LocalDateTime startPeriod = LocalDateTime.now().minus(Period.ofDays(1));
    LocalDateTime endPeriod = LocalDateTime.now().plus(Period.ofDays(1));
    LocalDateTime dateTime;

    @BeforeEach
    void initialLists() {
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
    void getMostProductivitySellerByPeriod_ReturnSellerData() {
        //given
        MostProductivityByPeriodRequest req =
                new MostProductivityByPeriodRequest(startPeriod, endPeriod);
        doReturn(sellers.get(0)).when(this.sellerDao).getMostProductivitySellerByPeriod(startPeriod, endPeriod);
        doReturn(sellerDataResponses.get(0)).when(this.sellerMapper).toSellerDataResponse(sellers.get(0));

        //when
        SellerDataResponse sdr = sellerService.getMostProductivitySellerByPeriod(req);

        //then
        assertNotNull(sdr);
        assertEquals(sellerDataResponses.get(0), sdr);
        verify(sellerDao).getMostProductivitySellerByPeriod(startPeriod, endPeriod);
        verify(sellerMapper).toSellerDataResponse(sellers.get(0));
    }

    @Test
    void getSellersWithLessAmountByPeriod_ReturnSellerDataList() {
        //given
        WithLessAmountByPeriodRequest req =
                new WithLessAmountByPeriodRequest(10, startPeriod, endPeriod);
        doReturn(sellers).when(this.sellerDao).getSellersWithLessSumByPeriod(startPeriod, endPeriod,10);
        doReturn(sellerDataResponses.get(0)).when(this.sellerMapper).toSellerDataResponse(sellers.get(0));
        doReturn(sellerDataResponses.get(1)).when(this.sellerMapper).toSellerDataResponse(sellers.get(1));

        //when
        List<SellerDataResponse> sdrs = sellerService.getSellersWithLessAmountByPeriod(req);

        //then
        assertNotNull(sdrs);
        assertEquals(sellerDataResponses, sdrs);
        verify(sellerDao).getSellersWithLessSumByPeriod(startPeriod, endPeriod, 10);
        verify(sellerMapper).toSellerDataResponse(sellers.get(0));
        verify(sellerMapper).toSellerDataResponse(sellers.get(1));
    }
}
