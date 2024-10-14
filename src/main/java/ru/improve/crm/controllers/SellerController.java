package ru.improve.crm.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;

public interface SellerController {

    SellerPostResponse saveSeller(@Validated @RequestBody SellerPostRequest sellerPostRequest,
                                  BindingResult bindingResult);


}
