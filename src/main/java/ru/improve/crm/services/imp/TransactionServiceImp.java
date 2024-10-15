package ru.improve.crm.services.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.improve.crm.dto.transaction.TransactionGetResponse;
import ru.improve.crm.dto.transaction.TransactionPostRequest;
import ru.improve.crm.dto.transaction.TransactionPostResponse;
import ru.improve.crm.error.exceptions.NotFoundException;
import ru.improve.crm.mappers.TransactionMapper;
import ru.improve.crm.models.Seller;
import ru.improve.crm.models.Transaction;
import ru.improve.crm.dao.repositories.SellerRepository;
import ru.improve.crm.dao.repositories.TransactionRepository;
import ru.improve.crm.services.TransactionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImp implements TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final SellerRepository sellerRepository;

    private final TransactionMapper transactionMapper;

    @Override
    public List<TransactionGetResponse> getAllTransactions() {
        List<Transaction> transactionList = transactionRepository.findAll();
        return transactionList.stream()
                .map(transaction -> transactionMapper.toTransactionGetResponse(transaction))
                .collect(Collectors.toList());
    }

    @Override
    public TransactionGetResponse getTransactionById(int id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("not found transaction", List.of("id")));

        return transactionMapper.toTransactionGetResponse(transaction);
    }

    @Override
    public List<TransactionGetResponse> getAllTransactionsBySellerId(int sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("not found seller", List.of("id")));

        List<Transaction> transactionList = seller.getTransactionList();
        return transactionList.stream()
                .map(transaction -> transactionMapper.toTransactionGetResponse(transaction))
                .collect(Collectors.toList());
    }

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
