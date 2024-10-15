package ru.improve.crm.dao.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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

        Query query = em.createQuery(
                """
                select sum(t.amount) as total_amount from Transaction t 
                where t.transactionDate > :startPeriod and t.transactionDate < :endPeriod
                group by t.seller
                order by total_amount
                fetch first 1 rows only
                """)
                .setParameter("startPeriod", startPeriod)
                .setParameter("endPeriod", endPeriod);
        return query.getResultList();
    }

    public List<Seller> getSellersWithLessSumByPeriod() {

        return null;
    }
}