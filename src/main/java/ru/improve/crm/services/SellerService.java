package ru.improve.crm.services;

import ru.improve.crm.dto.seller.SellerGetResponse;
import ru.improve.crm.dto.seller.SellerPatchRequest;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;

import java.util.List;

public interface SellerService {

    List<SellerGetResponse> getAllSellers();

    SellerGetResponse getSellerById(int id);

    SellerPostResponse saveSeller(SellerPostRequest sellerPostRequest);

    void patchSeller(int updateSellerId, SellerPatchRequest sellerPatchRequest);

    void deleteSellerById(int sellerId);
}
