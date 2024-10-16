package ru.improve.crm.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.improve.crm.dto.seller.SellerDataResponse;
import ru.improve.crm.dto.seller.SellerPatchRequest;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;
import ru.improve.crm.models.Seller;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SellerMapper {

    Seller toSeller(SellerPostRequest sellerPostRequest);

    SellerPostResponse toSellerPostResponse(Seller seller);

    SellerDataResponse toSellerDataResponse(Seller seller);

    void patchSeller(SellerPatchRequest sellerPatchRequest, @MappingTarget Seller seller);
}
