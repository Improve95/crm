package ru.improve.crm.services.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.improve.crm.dao.SellerDao;
import ru.improve.crm.dao.repositories.SellerRepository;
import ru.improve.crm.dto.seller.MostProductivityByPeriodRequest;
import ru.improve.crm.dto.seller.SellerDataResponse;
import ru.improve.crm.dto.seller.SellerPatchRequest;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;
import ru.improve.crm.dto.seller.WithLessAmountByPeriodRequest;
import ru.improve.crm.error.exceptions.AlreadyExistException;
import ru.improve.crm.error.exceptions.NotFoundException;
import ru.improve.crm.mappers.SellerMapper;
import ru.improve.crm.models.Seller;
import ru.improve.crm.services.SellerService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SellerServiceImp implements SellerService {

    private final SellerRepository sellerRepository;
    private final SellerDao sellerDao;

    private final SellerMapper sellerMapper;

    @Transactional
    @Override
    public List<SellerDataResponse> getAllSellers() {
        List<Seller> sellers = sellerRepository.findAll();
        return sellers.stream()
                .map(seller -> sellerMapper.toSellerDataResponse(seller))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public SellerDataResponse getSellerById(int id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("not found seller", List.of("id")));

        return sellerMapper.toSellerDataResponse(seller);
    }

    @Transactional
    @Override
    public SellerDataResponse getMostProductivitySellerByPeriod(
            MostProductivityByPeriodRequest request) {

        LocalDateTime startPeriod = request.getStartPeriod();
        LocalDateTime endPeriod = request.getEndPeriod();

        Seller seller = sellerDao.getMostProductivitySellerByPeriod(startPeriod, endPeriod);
        return sellerMapper.toSellerDataResponse(seller);
    }

    @Transactional
    @Override
    public List<SellerDataResponse> getSellersWithLessAmountByPeriod(WithLessAmountByPeriodRequest request) {
        LocalDateTime startPeriod = request.getStartPeriod();
        LocalDateTime endPeriod = request.getEndPeriod();
        int maxAmount = request.getMaxAmount();

        return sellerDao.getSellersWithLessSumByPeriod(startPeriod, endPeriod, maxAmount).stream()
                .map(seller -> sellerMapper.toSellerDataResponse(seller))
                .collect(Collectors.toList());
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
    public SellerDataResponse patchSeller(int updateSellerId, SellerPatchRequest sellerPatchRequest) {
        Seller seller = sellerRepository.findById(updateSellerId)
                .orElseThrow(() -> new NotFoundException("not found seller for update", List.of("id")));

        sellerMapper.patchSeller(sellerPatchRequest, seller);
        return sellerMapper.toSellerDataResponse(seller);
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
