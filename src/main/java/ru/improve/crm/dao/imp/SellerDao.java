package ru.improve.crm.dao.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.improve.crm.dto.seller.MostProductivityByPeriodRequest;
import ru.improve.crm.dto.seller.SellerGetResponse;
import ru.improve.crm.models.Seller;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SellerDao {

    @PersistenceContext
    private EntityManager em;

    public List<Seller> getMostProductivitySellerByPeriod(LocalDateTime startPeriod,
                                                          LocalDateTime endPeriod) {

        em.createQuery("");

        return null;
    }

    public List<Seller> getSellersWithLessSumByPeriod() {

        return null;
    }
}
