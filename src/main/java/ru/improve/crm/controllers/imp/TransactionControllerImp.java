package ru.improve.crm.controllers.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.improve.crm.dto.transaction.TransactionGetResponse;
import ru.improve.crm.dto.transaction.TransactionPostRequest;
import ru.improve.crm.dto.transaction.TransactionPostResponse;
import ru.improve.crm.services.TransactionService;
import ru.improve.crm.validators.TransactionValidator;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionControllerImp {

    private final TransactionService transactionService;

    private final TransactionValidator transactionValidator;

    @GetMapping
    public List<TransactionGetResponse> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public TransactionGetResponse getTransactionById(@PathVariable("id") int id) {
        return transactionService.getTransactionById(id);
    }

    @GetMapping("/seller/{sellerId}}")
    public List<TransactionGetResponse> getAllTransactionsBySellerId(@PathVariable("sellerId") int id) {
        return transactionService.getAllTransactionsBySellerId(id);
    }

    @PostMapping
    public TransactionPostResponse saveTransaction(@Validated @RequestBody TransactionPostRequest transactionPostRequest,
                                                   BindingResult bindingResult) {

        transactionValidator.validate(transactionPostRequest, bindingResult);

        return transactionService.saveTransaction(transactionPostRequest);
    }
}
