package ru.improve.crm.services;

import jakarta.transaction.Transactional;
import ru.improve.crm.dto.seller.MostProductivityByPeriodRequest;
import ru.improve.crm.dto.seller.SellerGetResponse;
import ru.improve.crm.dto.seller.SellerPatchRequest;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;
import ru.improve.crm.dto.seller.WithLessAmountByPeriodRequest;

import java.util.List;

public interface SellerService {

    List<SellerGetResponse> getAllSellers();

    SellerGetResponse getSellerById(int id);

    @Transactional
    SellerGetResponse getMostProductivitySellerByPeriod(
            MostProductivityByPeriodRequest request);

    List<SellerGetResponse> getSellersWithLessAmountByPeriod(WithLessAmountByPeriodRequest request);

    SellerPostResponse saveSeller(SellerPostRequest sellerPostRequest);

    void patchSeller(int updateSellerId, SellerPatchRequest sellerPatchRequest);

    void deleteSellerById(int sellerId);
}
