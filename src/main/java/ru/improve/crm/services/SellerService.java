package ru.improve.crm.services;

import org.springframework.transaction.annotation.Transactional;
import ru.improve.crm.dto.seller.SellerGetResponse;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;
import ru.improve.crm.dto.seller.SellerPatchRequest;

import java.util.List;

public interface SellerService {

    List<SellerGetResponse> getAllSellers();

    SellerGetResponse getSellerById(int id);

    SellerPostResponse saveSeller(SellerPostRequest sellerPostRequest);

    @Transactional
    void patchSeller(int updateSellerId, SellerPatchRequest sellerPatchRequest);

    void deleteSellerById(int sellerId);
}
