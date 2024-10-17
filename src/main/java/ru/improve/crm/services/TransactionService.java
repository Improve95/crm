package ru.improve.crm.services;

import ru.improve.crm.dto.transaction.TransactionDataResponse;
import ru.improve.crm.dto.transaction.TransactionPostRequest;
import ru.improve.crm.dto.transaction.TransactionPostResponse;

import java.util.List;

public interface TransactionService {

    List<TransactionDataResponse> getAllTransactions();

    TransactionDataResponse getTransactionById(int id);

    List<TransactionDataResponse> getAllTransactionsBySellerId(int sellerId);

    TransactionPostResponse saveTransaction(TransactionPostRequest transactionPostRequest);
}
