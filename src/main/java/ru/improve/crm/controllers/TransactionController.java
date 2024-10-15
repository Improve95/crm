package ru.improve.crm.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.improve.crm.dto.transaction.TransactionGetResponse;
import ru.improve.crm.dto.transaction.TransactionPostRequest;
import ru.improve.crm.dto.transaction.TransactionPostResponse;

import java.util.List;

public interface TransactionController {

    List<TransactionGetResponse> getAllTransactions();

    TransactionGetResponse getTransactionById(@PathVariable("id") int id);

    List<TransactionGetResponse> getAllTransactionsBySellerId(@PathVariable("sellerId") int id);

    TransactionPostResponse saveTransaction(@Validated @RequestBody TransactionPostRequest transactionPostRequest,
                                            BindingResult bindingResult);
}
