package ru.improve.crm.dao;

import ru.improve.crm.models.Seller;

import java.time.LocalDateTime;
import java.util.List;

public interface SellerDao {

    Seller getMostProductivitySellerByPeriod(LocalDateTime startPeriod,
                                             LocalDateTime endPeriod);

    List<Seller> getSellersWithLessSumByPeriod(LocalDateTime startPeriod,
                                               LocalDateTime endPeriod, int maxTotalAmount);
}
