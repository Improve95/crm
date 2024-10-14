package ru.improve.crm.services;

import org.springframework.transaction.annotation.Transactional;
import ru.improve.crm.dto.seller.SellerGetResponse;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;
import ru.improve.crm.dto.seller.SellerUpdateRequest;

import java.util.List;

public interface SellerService {

    List<SellerGetResponse> getAllSellers();

    SellerGetResponse getSellerById(int id);

    SellerPostResponse saveSeller(SellerPostRequest sellerPostRequest);

    @Transactional
    void updateSeller(int updateSellerId, SellerUpdateRequest sellerUpdateRequest);

    void deleteSellerById(int sellerId);
}
