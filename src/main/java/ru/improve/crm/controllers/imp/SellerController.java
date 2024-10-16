package ru.improve.crm.controllers.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("")
    public ResponseEntity<List<SellerDataResponse>> getAllSellers() {
        return new ResponseEntity<>(sellerService.getAllSellers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerDataResponse> getSellerById(@PathVariable("id") int id) {
        return new ResponseEntity<>(sellerService.getSellerById(id), HttpStatus.OK);
    }

    @GetMapping("/mostProductivity")
    public SellerDataResponse getMostProductivitySellerByPeriod(@Validated @RequestBody MostProductivityByPeriodRequest request,
                                                                BindingResult bindingResult) {

        sellerValidator.validate(request, bindingResult);

        return sellerService.getMostProductivitySellerByPeriod(request);
    }

    @GetMapping("/withLessAmount")
    public ResponseEntity<List<SellerDataResponse>>  getSellersWithLessAmountByPeriod(@Validated @RequestBody WithLessAmountByPeriodRequest request,
                                                                                      BindingResult bindingResult) {

        sellerValidator.validate(request, bindingResult);

        return new ResponseEntity<>(sellerService.getSellersWithLessAmountByPeriod(request), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<SellerPostResponse> saveSeller(@Validated @RequestBody SellerPostRequest sellerPostRequest,
                                         BindingResult bindingResult) {

        sellerValidator.validate(sellerPostRequest, bindingResult);

        return new ResponseEntity<>(sellerService.saveSeller(sellerPostRequest), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SellerDataResponse> patchSeller(@PathVariable("id") int patchSellerId,
                            @Validated @RequestBody SellerPatchRequest sellerPatchRequest,
                            BindingResult bindingResult) {

        sellerValidator.validate(sellerPatchRequest, bindingResult);

        SellerDataResponse sellerDataResponse = sellerService.patchSeller(patchSellerId, sellerPatchRequest);
        return new ResponseEntity<>(sellerDataResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable("id") int deleteSellerId) {
        sellerService.deleteSellerById(deleteSellerId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
