package ru.improve.crm.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.improve.crm.dto.seller.SellerGetResponse;
import ru.improve.crm.dto.seller.SellerPatchRequest;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;
import ru.improve.crm.models.Seller;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SellerMapper {

    SellerGetResponse toSellerGetResponse(Seller seller);

    SellerPostResponse toSellerPostResponse(Seller seller);

    Seller toSeller(SellerPostRequest sellerPostRequest);

    void patchSeller(SellerPatchRequest sellerPatchRequest, @MappingTarget Seller seller);
}
