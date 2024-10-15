package ru.improve.crm.services;

import ru.improve.crm.dto.transaction.TransactionGetResponse;
import ru.improve.crm.dto.transaction.TransactionPostRequest;
import ru.improve.crm.dto.transaction.TransactionPostResponse;

import java.util.List;

public interface TransactionService {

    List<TransactionGetResponse> getAllTransactions();

    TransactionGetResponse getTransactionById(int id);

    List<TransactionGetResponse> getAllTransactionsBySellerId(int sellerId);

    TransactionPostResponse saveTransaction(TransactionPostRequest transactionPostRequest);
}
