package ru.improve.crm.unit.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import ru.improve.crm.controllers.TransactionController;
import ru.improve.crm.dto.transaction.TransactionDataResponse;
import ru.improve.crm.dto.transaction.TransactionPostRequest;
import ru.improve.crm.dto.transaction.TransactionPostResponse;
import ru.improve.crm.models.Seller;
import ru.improve.crm.models.transaction.Transaction;
import ru.improve.crm.services.imp.TransactionServiceImp;
import ru.improve.crm.validators.imp.TransactionValidatorImp;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    TransactionServiceImp transactionService;

    @Mock
    TransactionValidatorImp transactionValidator;

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    TransactionController transactionController;

    static LocalDateTime date = LocalDateTime.now();

    List<Seller> sellers;

    List<Transaction> transactions;

    List<TransactionDataResponse> tdrs;

    @BeforeEach
    void initialLists() {
        sellers = List.of(
                new Seller(1, "name1", "contact1", date),
                new Seller(2, "name2", "contact2", date)
        );
        transactions = List.of(
                new Transaction(1, sellers.get(0), 50, "CARD", date),
                new Transaction(2, sellers.get(1), 150, "CARD", date)
        );
        tdrs = List.of(
                new TransactionDataResponse(1, 1, 50, "CARD", date),
                new TransactionDataResponse(2, 2, 150, "CARD", date)
        );
    }

    @Test
    void getAllTransactions() {
        //given
        doReturn(tdrs).when(this.transactionService).getAllTransactions();

        //when
        var re = this.transactionController.getAllTransactions();

        //then
        assertNotNull(re);
        assertEquals(HttpStatus.OK, re.getStatusCode());
        assertEquals(2, re.getBody().size());
        assertEquals(tdrs, re.getBody());
        verify(transactionService).getAllTransactions();
    }

    @Test
    void getTransactionById() {
        //given
        doReturn(tdrs.get(0)).when(this.transactionService).getTransactionById(0);

        //when
        var re = this.transactionController.getTransactionById(0);

        //then
        assertNotNull(re);
        assertEquals(HttpStatus.OK, re.getStatusCode());
        assertEquals(tdrs.get(0), re.getBody());
        verify(transactionService).getTransactionById(0);
    }

    @Test
    void getAllTransactionsBySellerId() {
        //given
        doReturn(tdrs).when(this.transactionService).getAllTransactionsBySellerId(1);

        //when
        var re = this.transactionController.getAllTransactionsBySellerId(1);

        //then
        assertNotNull(re);
        assertEquals(HttpStatus.OK, re.getStatusCode());
        assertEquals(tdrs, re.getBody());
        verify(transactionService).getAllTransactionsBySellerId(1);
    }

    @Test
    void saveTransaction_PostRequestDataIsValid_ReturnSavedTransaction() {
        //given
        TransactionPostRequest tpreq = new TransactionPostRequest(1, 200, "CARD");
        TransactionPostResponse tpres = new TransactionPostResponse(1, date);
        doReturn(tpres).when(this.transactionService).saveTransaction(tpreq);
        doNothing().when(this.transactionValidator).validate(tpreq, bindingResult);

        //when
        var re = this.transactionController.saveTransaction(tpreq, bindingResult);

        //then
        assertNotNull(re);
        assertEquals(HttpStatus.OK, re.getStatusCode());
        assertEquals(tpres, re.getBody());
        verify(transactionService).saveTransaction(tpreq);
    }
}