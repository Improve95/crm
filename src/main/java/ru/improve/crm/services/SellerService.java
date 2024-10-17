package ru.improve.crm.services;

import org.springframework.transaction.annotation.Transactional;
import ru.improve.crm.dto.seller.MostProductivityByPeriodRequest;
import ru.improve.crm.dto.seller.SellerDataResponse;
import ru.improve.crm.dto.seller.SellerPatchRequest;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;
import ru.improve.crm.dto.seller.WithLessAmountByPeriodRequest;

import java.util.List;

public interface SellerService {

    List<SellerDataResponse> getAllSellers();

    SellerDataResponse getSellerById(int id);

    @Transactional
    SellerDataResponse getMostProductivitySellerByPeriod(
            MostProductivityByPeriodRequest request);

    List<SellerDataResponse> getSellersWithLessAmountByPeriod(WithLessAmountByPeriodRequest request);

    SellerPostResponse saveSeller(SellerPostRequest sellerPostRequest);

    SellerDataResponse patchSeller(int updateSellerId, SellerPatchRequest sellerPatchRequest);

    void deleteSellerById(int sellerId);
}
