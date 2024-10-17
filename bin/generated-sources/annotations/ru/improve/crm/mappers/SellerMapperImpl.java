package ru.improve.crm.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.improve.crm.dto.seller.SellerDataResponse;
import ru.improve.crm.dto.seller.SellerPatchRequest;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;
import ru.improve.crm.models.Seller;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-17T11:18:00+0700",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.40.0.v20240919-1711, environment: Java 17.0.12 (Eclipse Adoptium)"
)
@Component
public class SellerMapperImpl implements SellerMapper {

    @Override
    public Seller toSeller(SellerPostRequest sellerPostRequest) {
        if ( sellerPostRequest == null ) {
            return null;
        }

        Seller seller = new Seller();

        seller.setContactInfo( sellerPostRequest.getContactInfo() );
        seller.setName( sellerPostRequest.getName() );

        return seller;
    }

    @Override
    public SellerPostResponse toSellerPostResponse(Seller seller) {
        if ( seller == null ) {
            return null;
        }

        SellerPostResponse sellerPostResponse = new SellerPostResponse();

        sellerPostResponse.setId( seller.getId() );
        sellerPostResponse.setRegistrationDate( seller.getRegistrationDate() );

        return sellerPostResponse;
    }

    @Override
    public SellerDataResponse toSellerDataResponse(Seller seller) {
        if ( seller == null ) {
            return null;
        }

        SellerDataResponse sellerDataResponse = new SellerDataResponse();

        sellerDataResponse.setContactInfo( seller.getContactInfo() );
        sellerDataResponse.setId( seller.getId() );
        sellerDataResponse.setName( seller.getName() );
        sellerDataResponse.setRegistrationDate( seller.getRegistrationDate() );

        return sellerDataResponse;
    }

    @Override
    public void patchSeller(SellerPatchRequest sellerPatchRequest, Seller seller) {
        if ( sellerPatchRequest == null ) {
            return;
        }

        seller.setContactInfo( sellerPatchRequest.getContactInfo() );
        seller.setName( sellerPatchRequest.getName() );
    }
}
