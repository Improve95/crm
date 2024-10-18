package ru.improve.crm.modules.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.improve.crm.dao.repositories.SellerRepository;
import ru.improve.crm.dao.repositories.TransactionRepository;
import ru.improve.crm.dto.transaction.TransactionDataResponse;
import ru.improve.crm.dto.transaction.TransactionPostRequest;
import ru.improve.crm.dto.transaction.TransactionPostResponse;
import ru.improve.crm.error.exceptions.NotFoundException;
import ru.improve.crm.mappers.TransactionMapper;
import ru.improve.crm.models.Seller;
import ru.improve.crm.models.transaction.Transaction;
import ru.improve.crm.services.imp.TransactionServiceImp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    SellerRepository sellerRepository;

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
        sellers.get(0).setTransactions(transactions.subList(0, 1));
        sellers.get(1).setTransactions(transactions.subList(1, 3));
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

    @Test
    void getTransactionById_TransactionIsExist_ReturnDataTransaction() {
        //given
        doReturn(Optional.ofNullable(transactions.get(0))).when(this.transactionRepository).findById(1);
        doReturn(transactionDataResponses.get(0)).when(this.transactionMapper)
                .toTransactionDataResponse(transactions.get(0));

        //when
        TransactionDataResponse tdr = transactionService.getTransactionById(1);

        //then
        assertNotNull(tdr);
        assertEquals(transactionDataResponses.get(0), tdr);
        verify(transactionRepository).findById(1);
        verify(transactionMapper).toTransactionDataResponse(transactions.get(0));
    }

    @Test
    void getAllTransactionsBySellerId_SellerFound_ReturnDataTransactionList() {
        doReturn(Optional.ofNullable(sellers.get(0))).when(this.sellerRepository).findById(1);
        doReturn(transactionDataResponses.get(0)).when(this.transactionMapper)
                .toTransactionDataResponse(transactions.get(0));

        //when
        List<TransactionDataResponse> tdrs = transactionService.getAllTransactionsBySellerId(1);

        //then
        assertNotNull(tdrs);
        assertEquals(List.of(transactionDataResponses.get(0)), tdrs);
        verify(sellerRepository).findById(1);
        verify(transactionMapper).toTransactionDataResponse(transactions.get(0));
    }

    @Test
    void getAllTransactionsBySellerId_SellerNotFound_ReturnDataTransactionList() {
        doThrow(new NotFoundException("not found seller", List.of("id")))
                .when(this.sellerRepository).findById(1);

        NotFoundException ex = assertThrows(NotFoundException.class, () ->
                transactionService.getAllTransactionsBySellerId(1));

        assertNotNull(ex);
        assertEquals("not found seller", ex.getMessage());
        assertEquals(List.of("id"), ex.getFieldsWithErrorList());
        verify(sellerRepository).findById(1);
        verifyNoInteractions(transactionMapper);
    }

    @Test
    void saveTransaction_SellerFound_ReturnsTransactionPostData() {
        //given
        doReturn(Optional.ofNullable(sellers.get(0))).when(this.sellerRepository).findById(1);
        doReturn(transactions.get(0)).when(this.transactionMapper)
                .toTransaction(transactionPostRequests.get(0));
        doReturn(transactions.get(0)).when(this.transactionRepository)
                .save(transactions.get(0));
        doReturn(transactionPostResponses.get(0)).when(this.transactionMapper)
                .toTransactionPostResponse(transactions.get(0));

        //then
        TransactionPostResponse tpr = transactionService
                .saveTransaction(transactionPostRequests.get(0));

        //when
        assertNotNull(tpr);
        assertEquals(transactionPostResponses.get(0), tpr);
        verify(sellerRepository).findById(1);
        verify(transactionMapper).toTransaction(transactionPostRequests.get(0));
        verify(transactionRepository).save(transactions.get(0));
    }

    @Test
    void saveTransaction_SellerNotFound_ReturnsTransactionPostData() {
        doThrow(new NotFoundException("not found seller", List.of("id")))
                .when(this.sellerRepository).findById(1);

        NotFoundException ex = assertThrows(NotFoundException.class, () ->
                transactionService.getAllTransactionsBySellerId(1));

        assertNotNull(ex);
        assertEquals("not found seller", ex.getMessage());
        assertEquals(List.of("id"), ex.getFieldsWithErrorList());
        verify(sellerRepository).findById(1);
        verifyNoInteractions(transactionMapper);
    }
}
