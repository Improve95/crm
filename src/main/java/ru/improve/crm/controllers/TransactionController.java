package ru.improve.crm.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.improve.crm.dto.transaction.TransactionDataResponse;
import ru.improve.crm.dto.transaction.TransactionPostRequest;
import ru.improve.crm.dto.transaction.TransactionPostResponse;

import java.util.List;

public interface TransactionController {

    List<TransactionDataResponse> getAllTransactions();

    TransactionDataResponse getTransactionById(@PathVariable("id") int id);

    List<TransactionDataResponse> getAllTransactionsBySellerId(@PathVariable("sellerId") int id);

    TransactionPostResponse saveTransaction(@Validated @RequestBody TransactionPostRequest transactionPostRequest,
                                            BindingResult bindingResult);
}
