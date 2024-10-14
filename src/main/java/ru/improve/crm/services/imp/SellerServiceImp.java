package ru.improve.crm.services.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.improve.crm.dto.seller.SellerGetResponse;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;
import ru.improve.crm.dto.seller.SellerUpdateRequest;
import ru.improve.crm.error.exceptions.NotFoundException;
import ru.improve.crm.models.Seller;
import ru.improve.crm.repositories.SellerRepository;
import ru.improve.crm.services.SellerService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerServiceImp implements SellerService {

    private final SellerRepository sellerRepository;

    
    @Override
    public List<SellerGetResponse> getAllSellers() {
        List<Seller> sellerList = sellerRepository.findAll();
        return sellerList.stream()
                .map(seller -> SellerGetResponse.builder()
                        .id(seller.getId())
                        .name(seller.getName())
                        .contactInfo(seller.getContactInfo())
                        .registrationDate(seller.getRegistrationDate())
                        .build())
                .collect(Collectors.toList());
    }

    
    @Override
    public SellerGetResponse getSellerById(int id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("not found seller", List.of("id")));

        return SellerGetResponse.builder()
                .id(seller.getId())
                .name(seller.getName())
                .contactInfo(seller.getContactInfo())
                .registrationDate(seller.getRegistrationDate())
                .build();
    }

    
    @Override
    public SellerPostResponse saveSeller(SellerPostRequest sellerPostRequest) {
        Seller seller = Seller.builder()
                .name(sellerPostRequest.getName())
                .contactInfo(sellerPostRequest.getContactInfo())
                .registrationDate(LocalDateTime.now())
                .build();

        Seller saveSeller = sellerRepository.save(seller);
        return new SellerPostResponse(saveSeller.getId());
    }

    
    @Override
    public void updateSeller(int updateSellerId, SellerUpdateRequest sellerUpdateRequest) {
        Seller seller = sellerRepository.findById(updateSellerId)
                .orElseThrow(() -> new NotFoundException("not found seller for update", List.of("id")));
        
        seller.setName(sellerUpdateRequest.getName());
        seller.setContactInfo(sellerUpdateRequest.getContactInfo());
    }
    
    @Override
    public void deleteSellerById(int sellerId) {
        if (!sellerRepository.existsById(sellerId)) {
            throw new NotFoundException("not found seller for delete", List.of("id"));
        }
        sellerRepository.deleteById(sellerId);
    }
}
