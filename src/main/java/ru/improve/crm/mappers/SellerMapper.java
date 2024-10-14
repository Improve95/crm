package ru.improve.crm.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.improve.crm.dto.seller.SellerPatchRequest;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.models.Seller;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SellerMapper {

    Seller toSeller(SellerPostRequest sellerPostRequest);

    void patchSeller(@MappingTarget Seller seller, SellerPatchRequest sellerPatchRequest);
}
