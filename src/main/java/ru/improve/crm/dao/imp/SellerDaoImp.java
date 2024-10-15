package ru.improve.crm.dao.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.improve.crm.dao.SellerDao;
import ru.improve.crm.models.Seller;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Repository
public class SellerDaoImp implements SellerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Seller getMostProductivitySellerByPeriod(LocalDateTime startPeriod,
                                                    LocalDateTime endPeriod) {

        Query query = em.createQuery(
                """
                select t.seller from Transaction t
                where t.transactionDate > :startPeriod and t.transactionDate < :endPeriod
                group by t.seller
                order by sum(t.amount) desc
                """)
                .setParameter("startPeriod", startPeriod)
                .setParameter("endPeriod", endPeriod)
                .setMaxResults(1);

        List<Seller> sellers = query.getResultList();

        if (sellers.isEmpty()) return null;
        return sellers.getFirst();
    }

    @Override
    public List<Seller> getSellersWithLessSumByPeriod(LocalDateTime startPeriod,
                                                      LocalDateTime endPeriod, int maxAmount) {

        Query query = em.createNativeQuery(
                                """
                                select s.* from sellers s where s.id = (
                                    select seller from transactions t
                                    where t.transaction_date > :startPeriod and t.transaction_date < :endPeriod
                                    group by seller 
                                    having (sum(t.amount) < :maxAmount))
                                """, Seller.class)
                .setParameter("startPeriod", startPeriod)
                .setParameter("endPeriod", endPeriod)
                .setParameter("maxAmount", maxAmount);

        List<Seller> sellers = (List<Seller>) query.getResultList();
        return sellers;
    }
}