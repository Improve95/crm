package ru.improve.crm.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.improve.crm.dto.seller.SellerGetResponse;
import ru.improve.crm.dto.seller.SellerPatchRequest;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;

import java.util.List;

public interface SellerController {

    @GetMapping
    List<SellerGetResponse> getAllSellers();

    @GetMapping("/{id}")
    SellerGetResponse getSellerById(@PathVariable("id") int id);

    SellerPostResponse saveSeller(@Validated @RequestBody SellerPostRequest sellerPostRequest,
                                  BindingResult bindingResult);


    @PatchMapping("/{id}")
    void patchSeller(@PathVariable("id") int patchSellerId,
                     @Validated @RequestBody SellerPatchRequest sellerPatchRequest,
                     BindingResult bindingResult);

    @DeleteMapping("/{id}")
    void deleteSeller(@PathVariable("id") int deleteSellerId);
}
