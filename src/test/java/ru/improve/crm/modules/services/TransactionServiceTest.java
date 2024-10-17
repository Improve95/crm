package ru.improve.crm.modules.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.improve.crm.dao.repositories.TransactionRepository;
import ru.improve.crm.dto.transaction.TransactionDataResponse;
import ru.improve.crm.dto.transaction.TransactionPostRequest;
import ru.improve.crm.dto.transaction.TransactionPostResponse;
import ru.improve.crm.mappers.TransactionMapper;
import ru.improve.crm.models.Seller;
import ru.improve.crm.models.transaction.Transaction;
import ru.improve.crm.services.imp.TransactionServiceImp;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    TransactionMapper transactionMapper;

    @InjectMocks
    TransactionServiceImp transactionService;

    List<Seller> sellers;

    List<TransactionDataResponse> transactionDataResponses;
    List<TransactionPostRequest> transactionPostRequests;
    List<Transaction> transactions;
    List<TransactionPostResponse> transactionPostResponses;

    LocalDateTime dateTime = LocalDateTime.now();

    @BeforeEach
    void initialLists() {
        sellers = List.of(
                new Seller(1, "name1", "contact1", dateTime),
                new Seller(2, "name2", "contact2", dateTime)
        );
        transactionDataResponses = List.of(
                new TransactionDataResponse(1, 1, 100, "CARD", dateTime),
                new TransactionDataResponse(2, 2, 100, "CARD", dateTime),
                new TransactionDataResponse(3, 2, 150, "CASH", dateTime)
        );
        transactionPostRequests = List.of(
                new TransactionPostRequest(1, 100, "CARD"),
                new TransactionPostRequest(2, 100, "CARD"),
                new TransactionPostRequest(2, 150, "CASH")
        );
        transactions = List.of(
                new Transaction(1, sellers.get(0), 100, "CARD", dateTime),
                new Transaction(2, sellers.get(1), 100, "CARD", dateTime),
                new Transaction(3, sellers.get(1), 150, "CASH", dateTime)
        );
        transactionPostResponses = List.of(
                new TransactionPostResponse(1, dateTime),
                new TransactionPostResponse(2, dateTime),
                new TransactionPostResponse(3, dateTime)
        );
    }

    @Test
    void getAllTransactions_ReturnsTransaction() {
        //given
        doReturn(transactions).when(this.transactionRepository).findAll();
        doReturn(transactionDataResponses.get(0)).when(this.transactionMapper)
                .toTransactionDataResponse(transactions.get(0));
        doReturn(transactionDataResponses.get(1)).when(this.transactionMapper)
                .toTransactionDataResponse(transactions.get(1));
        doReturn(transactionDataResponses.get(2)).when(this.transactionMapper)
                .toTransactionDataResponse(transactions.get(2));

        //when
        List<TransactionDataResponse> tdrs = transactionService.getAllTransactions();

        //then
        assertNotNull(tdrs);
        assertEquals(transactionDataResponses, tdrs);
        verify(transactionRepository).findAll();
        verify(transactionMapper).toTransactionDataResponse(transactions.get(0));
        verify(transactionMapper).toTransactionDataResponse(transactions.get(1));
        verify(transactionMapper).toTransactionDataResponse(transactions.get(2));
    }
}
