package ru.improve.crm.services.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.improve.crm.dao.repositories.SellerRepository;
import ru.improve.crm.dto.seller.SellerGetResponse;
import ru.improve.crm.dto.seller.SellerPatchRequest;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;
import ru.improve.crm.error.exceptions.AlreadyExistException;
import ru.improve.crm.error.exceptions.NotFoundException;
import ru.improve.crm.mappers.SellerMapper;
import ru.improve.crm.models.Seller;
import ru.improve.crm.services.SellerService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellerServiceImp implements SellerService {

    private final SellerRepository sellerRepository;

    private final SellerMapper sellerMapper;

    @Transactional
    @Override
    public List<SellerGetResponse> getAllSellers() {
        List<Seller> sellerList = sellerRepository.findAll();
        return sellerList.stream()
                .map(seller -> sellerMapper.toSellerGetResponse(seller))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public SellerGetResponse getSellerById(int id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("not found seller", List.of("id")));

        return sellerMapper.toSellerGetResponse(seller);
    }

    @Transactional
    @Override
    public SellerPostResponse saveSeller(SellerPostRequest sellerPostRequest) {
        Seller seller = sellerMapper.toSeller(sellerPostRequest);
        seller.setRegistrationDate(LocalDateTime.now());

        try {
            Seller saveSeller = sellerRepository.save(seller);
            return sellerMapper.toSellerPostResponse(saveSeller);
        } catch (DataIntegrityViolationException ex) {
            throw new AlreadyExistException(ex.getMessage(), List.of("contactInfo"));
        }
    }

    @Transactional
    @Override
    public void patchSeller(int updateSellerId, SellerPatchRequest sellerPatchRequest) {
        Seller seller = sellerRepository.findById(updateSellerId)
                .orElseThrow(() -> new NotFoundException("not found seller for update", List.of("id")));

        sellerMapper.patchSeller(sellerPatchRequest, seller);
    }

    @Transactional
    @Override
    public void deleteSellerById(int sellerId) {
        if (!sellerRepository.existsById(sellerId)) {
            throw new NotFoundException("not found seller for delete", List.of("id"));
        }
        sellerRepository.deleteById(sellerId);
    }
}
