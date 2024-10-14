package ru.improve.crm.controllers.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.crm.controllers.SellerController;
import ru.improve.crm.dto.seller.SellerGetResponse;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;
import ru.improve.crm.services.SellerService;

import java.util.List;

@RestController
@RequestMapping("/sellers")
@RequiredArgsConstructor
public class SellerControllerImp implements SellerController {

    private final SellerService sellerService;

    @GetMapping
    public List<SellerGetResponse> getAllSellers() {
        return null;
    }

    @PostMapping()
    @Override
    public SellerPostResponse saveSeller(@Validated @RequestBody SellerPostRequest sellerPostRequest,
                                         BindingResult bindingResult) {

        /* todo: намутить валидатор */

        return sellerService.saveSeller(sellerPostRequest);
    }
}
