package ru.improve.crm.modules.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.improve.crm.dao.imp.SellerDaoImp;
import ru.improve.crm.models.Seller;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SellerDaoTest {

    @Mock
    Query query;

    @Mock
    EntityManager em;

    @InjectMocks
    SellerDaoImp sellerDao;

    List<Seller> sellers;

    LocalDateTime dateTime = LocalDateTime.now();
    LocalDateTime startPeriod = LocalDateTime.now().minus(Period.ofDays(1));
    LocalDateTime endPeriod = LocalDateTime.now().plus(Period.ofDays(1));

    @BeforeEach
    void initialLists() {
        sellers = List.of(
                new Seller(1, "name1", "contact1", dateTime),
                new Seller(2, "name2", "contact2", dateTime)
        );
    }

    @Test
    void getMostProductivitySellerByPeriod_SellersListNotEmpty_ReturnsSeller() {
        doReturn(query).when(em).createQuery(anyString());
        doReturn(query).when(query).setParameter("startPeriod", startPeriod);
        doReturn(query).when(query).setParameter("endPeriod", endPeriod);
        doReturn(query).when(query).setMaxResults(1);
        doReturn(sellers).when(query).getResultList();

        //when
        Seller seller = sellerDao.getMostProductivitySellerByPeriod(startPeriod, endPeriod);

        //then
        assertNotNull(seller);
        assertEquals(sellers.get(0), seller);
        verify(em).createQuery(anyString());
        verify(query).setParameter("startPeriod", startPeriod);
        verify(query).setParameter("endPeriod", endPeriod);
        verify(query).setMaxResults(1);
        verify(query).getResultList();
    }

    @Test
    void getMostProductivitySellerByPeriod_SellersListEmpty_ReturnsSeller() {
        doReturn(query).when(em).createQuery(anyString());
        doReturn(query).when(query).setParameter("startPeriod", startPeriod);
        doReturn(query).when(query).setParameter("endPeriod", endPeriod);
        doReturn(query).when(query).setMaxResults(1);
        doReturn(List.of()).when(query).getResultList();

        //when
        Seller seller = sellerDao.getMostProductivitySellerByPeriod(startPeriod, endPeriod);

        //then
        assertNull(seller);
        verify(em).createQuery(anyString());
        verify(query).setParameter("startPeriod", startPeriod);
        verify(query).setParameter("endPeriod", endPeriod);
        verify(query).setMaxResults(1);
        verify(query).getResultList();
    }

    @Test
    void getSellersWithLessSumByPeriod_ReturnsSellerList() {
        doReturn(query).when(em).createNativeQuery(anyString(), eq(Seller.class));
        doReturn(query).when(query).setParameter("startPeriod", startPeriod);
        doReturn(query).when(query).setParameter("endPeriod", endPeriod);
        doReturn(query).when(query).setParameter("maxAmount", 10);
        doReturn(sellers).when(query).getResultList();

        //when
        List<Seller> sellerListReq = sellerDao.getSellersWithLessSumByPeriod(startPeriod, endPeriod,10);

        //then
        assertNotNull(sellerListReq);
        assertEquals(sellers, sellerListReq);
        verify(em).createNativeQuery(anyString(), eq(Seller.class));
        verify(query).setParameter("startPeriod", startPeriod);
        verify(query).setParameter("endPeriod", endPeriod);
        verify(query).setParameter("maxAmount", 10);
        verify(query).getResultList();

    }
}
