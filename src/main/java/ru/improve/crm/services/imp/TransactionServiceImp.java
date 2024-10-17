package ru.improve.crm.services.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.improve.crm.dao.repositories.SellerRepository;
import ru.improve.crm.dao.repositories.TransactionRepository;
import ru.improve.crm.models.Seller;
import ru.improve.crm.models.transaction.Transaction;
import ru.improve.crm.dto.transaction.TransactionDataResponse;
import ru.improve.crm.dto.transaction.TransactionPostRequest;
import ru.improve.crm.dto.transaction.TransactionPostResponse;
import ru.improve.crm.error.exceptions.NotFoundException;
import ru.improve.crm.mappers.TransactionMapper;
import ru.improve.crm.services.TransactionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImp implements TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final SellerRepository sellerRepository;

    private final TransactionMapper transactionMapper;

    @Transactional
    @Override
    public List<TransactionDataResponse> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(transaction -> transactionMapper.toTransactionGetResponse(transaction))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public TransactionDataResponse getTransactionById(int id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("not found transaction", List.of("id")));

        return transactionMapper.toTransactionGetResponse(transaction);
    }

    @Transactional
    @Override
    public List<TransactionDataResponse> getAllTransactionsBySellerId(int sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("not found seller", List.of("id")));

        List<Transaction> transactions = seller.getTransactions();
        return transactions.stream()
                .map(transaction -> transactionMapper.toTransactionGetResponse(transaction))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public TransactionPostResponse saveTransaction(TransactionPostRequest transactionPostRequest) {
        Seller seller = sellerRepository.findById(transactionPostRequest.getSellerId())
                .orElseThrow(() -> new NotFoundException("not found seller", List.of("id")));

        Transaction transaction = transactionMapper.toTransaction(transactionPostRequest);
        transaction.setSeller(seller);
        transaction.setTransactionDate(LocalDateTime.now());

        Transaction saveTransaction = transactionRepository.save(transaction);
        return transactionMapper.toTransactionPostResponse(saveTransaction);
    }
}
