package ru.improve.crm.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.crm.dto.transaction.TransactionDataResponse;
import ru.improve.crm.dto.transaction.TransactionPostRequest;
import ru.improve.crm.dto.transaction.TransactionPostResponse;
import ru.improve.crm.services.TransactionService;
import ru.improve.crm.validators.TransactionValidator;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    private final TransactionValidator transactionValidator;

    @GetMapping("")
    public ResponseEntity<List<TransactionDataResponse>> getAllTransactions() {
        return new ResponseEntity<>(transactionService.getAllTransactions(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDataResponse> getTransactionById(@PathVariable("id") int id) {
        return new ResponseEntity<>(transactionService.getTransactionById(id), HttpStatus.OK);
    }
    @GetMapping("/seller/{sellerId}")
    
    public ResponseEntity<List<TransactionDataResponse>> getAllTransactionsBySellerId(@PathVariable("sellerId") int id) {
        return new ResponseEntity<>(transactionService.getAllTransactionsBySellerId(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<TransactionPostResponse> saveTransaction(@Validated @RequestBody TransactionPostRequest transactionPostRequest,
                                                   BindingResult bindingResult) {

        transactionValidator.validate(transactionPostRequest, bindingResult);

        return new ResponseEntity<>(transactionService.saveTransaction(transactionPostRequest), HttpStatus.OK);
    }
}
