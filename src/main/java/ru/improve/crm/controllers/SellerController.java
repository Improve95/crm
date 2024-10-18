package ru.improve.crm.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.crm.controllers.SellerController;
import ru.improve.crm.dto.seller.MostProductivityByPeriodRequest;
import ru.improve.crm.dto.seller.SellerDataResponse;
import ru.improve.crm.dto.seller.SellerPatchRequest;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;
import ru.improve.crm.dto.seller.WithLessAmountByPeriodRequest;
import ru.improve.crm.services.SellerService;
import ru.improve.crm.validators.SellerValidator;

import java.util.List;

@RestController
@RequestMapping("/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    private final SellerValidator sellerValidator;

    @GetMapping
    public List<SellerDataResponse> getAllSellers() {
        return sellerService.getAllSellers();
    }

    @GetMapping("/{id}")
    public SellerDataResponse getSellerById(@PathVariable("id") int id) {
        return sellerService.getSellerById(id);
    }

    @GetMapping("/mostProductivity")
    public SellerDataResponse getMostProductivitySellerByPeriod(@Validated @RequestBody MostProductivityByPeriodRequest request,
                                                               BindingResult bindingResult) {

        sellerValidator.validate(request, bindingResult);

        return sellerService.getMostProductivitySellerByPeriod(request);
    }

    @GetMapping("/withLessAmount")
    public List<SellerDataResponse>  getSellersWithLessAmountByPeriod(@Validated @RequestBody WithLessAmountByPeriodRequest request,
                                                                     BindingResult bindingResult) {

        sellerValidator.validate(request, bindingResult);

        return sellerService.getSellersWithLessAmountByPeriod(request);
    }

    @PostMapping()
    public SellerPostResponse saveSeller(@Validated @RequestBody SellerPostRequest sellerPostRequest,
                                         BindingResult bindingResult) {

        sellerValidator.validate(sellerPostRequest, bindingResult);

        return sellerService.saveSeller(sellerPostRequest);
    }

    @PatchMapping("/{id}")
    public void patchSeller(@PathVariable("id") int patchSellerId,
                            @Validated @RequestBody SellerPatchRequest sellerPatchRequest,
                            BindingResult bindingResult) {

        sellerValidator.validate(sellerPatchRequest, bindingResult);

        sellerService.patchSeller(patchSellerId, sellerPatchRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteSeller(@PathVariable("id") int deleteSellerId) {
        sellerService.deleteSellerById(deleteSellerId);
    }
}
