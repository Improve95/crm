package ru.improve.crm.dao.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.improve.crm.models.Seller;

import java.util.List;

@Repository
public class SellerDao {

    @PersistenceContext
    private EntityManager em;

    public Seller getMostProductivitySellerByPeriod() {

        return null;
    }

    public List<Seller> getSellersWithLessSumByPeriod() {

        return null;
    }
}
