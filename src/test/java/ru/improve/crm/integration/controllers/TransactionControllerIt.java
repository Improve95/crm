package ru.improve.crm.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.improve.crm.dto.transaction.TransactionPostRequest;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Transactional
public class TransactionControllerIt {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager em;

    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    private LocalDateTime date = LocalDateTime.now();

    @BeforeEach
    public void truncate() {
        em.createNativeQuery("set search_path to public").executeUpdate();
        em.createNativeQuery("alter sequence sellers_id_seq restart with 1").executeUpdate();
        em.createNativeQuery("alter sequence transactions_id_seq restart with 1").executeUpdate();
        em.createNativeQuery("truncate sellers cascade").executeUpdate();
        em.createNativeQuery("truncate transactions").executeUpdate();
    }

    private void fillSimpleSellerData() {
        em.createNativeQuery("insert into sellers (name, contact_info, registration_date) values ('name1', 'contact1', current_timestamp)")
                .executeUpdate();
        em.createNativeQuery("insert into sellers (name, contact_info, registration_date) values ('name2', 'contact2', current_timestamp)")
                .executeUpdate();
    }

    private void fillSimpleTransactionData() {
        em.createNativeQuery("insert into transactions (seller, amount, payment_type, transaction_date) VALUES (1, 100, 'CARD', current_timestamp)")
                .executeUpdate();
        em.createNativeQuery("insert into transactions (seller, amount, payment_type, transaction_date) VALUES (2, 1000, 'CARD', current_timestamp)")
                .executeUpdate();
        em.createNativeQuery("insert into transactions (seller, amount, payment_type, transaction_date) VALUES (2, 1500, 'CASH', current_timestamp)")
                .executeUpdate();
    }

    @Test
    public void getAllTransactions_ReturnsValidTransactions() throws Exception {
        //given
        fillSimpleSellerData();
        fillSimpleTransactionData();

        var requestBuilderGet = get("/transactions");

        //when
        mockMvc.perform(requestBuilderGet)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                            [
                                                {
                                                        "id": 1,
                                                        "sellerId": 1,
                                                        "amount": 100,
                                                        "paymentType": "CARD"
                                                    },
                                                    {
                                                        "id": 2,
                                                        "sellerId": 2,
                                                        "amount": 1000,
                                                        "paymentType": "CARD"
                                                    },
                                                    {
                                                        "id": 3,
                                                        "sellerId": 2,
                                                        "amount": 1500,
                                                        "paymentType": "CASH"
                                                    }
                                            ]
                                        """)
                );
    }

    @Test
    public void getTransactionById_TransactionExist_ReturnsTransactionData() throws Exception {
        fillSimpleSellerData();
        fillSimpleTransactionData();

        var requestBuilderGet = get("/transactions/1");

        mockMvc.perform(requestBuilderGet)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                        {
                                                        "id": 1,
                                                        "sellerId": 1,
                                                        "amount": 100,
                                                        "paymentType": "CARD"
                                        }
                                        """)
                );
    }

    @Test
    public void getTransactionById_TransactionNotExist_ReturnsTransactionData() throws Exception {
        fillSimpleSellerData();
        fillSimpleTransactionData();

        var requestBuilderGet = get("/transactions/30");

        mockMvc.perform(requestBuilderGet)
                //then
                .andExpectAll(
                        status().is(404),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                            {
                                                "message": "not found transaction",
                                                "fieldsWithError": [
                                                    "id"
                                                ]
                                            }
                                        """)
                );
    }

    @Test
    public void getTransactionBySellerId_RequestIsValid_ReturnsTransactionsDataList() throws Exception {
        //given
        fillSimpleSellerData();
        fillSimpleTransactionData();

        var requestBuilderGet = get("/transactions/seller/2");

        //when
        mockMvc.perform(requestBuilderGet)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                            [
                                                    {
                                                        "id": 2,
                                                        "sellerId": 2,
                                                        "amount": 1000,
                                                        "paymentType": "CARD"
                                                    },
                                                    {
                                                        "id": 3,
                                                        "sellerId": 2,
                                                        "amount": 1500,
                                                        "paymentType": "CASH"
                                                    }
                                            ]
                                        """)
                );
    }

    @Test
    public void getTransactionBySellerId_RequestNotValid_ReturnsTransactionsDataList() throws Exception {
        //given
        fillSimpleSellerData();
        fillSimpleTransactionData();

        var requestBuilderGet = get("/transactions/seller/5");

        //when
        mockMvc.perform(requestBuilderGet)
                //then
                .andExpectAll(
                        status().is(404),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                            {
                                                "message": "not found seller",
                                                "fieldsWithError": [
                                                    "id"
                                                ]
                                            }
                                        """)
                );
    }

    @Test
    public void saveTransaction_TransactionDataIsValid_ReturnTransactionPostResponse() throws Exception {
        //given
        fillSimpleSellerData();
        fillSimpleTransactionData();

        TransactionPostRequest tpr = new TransactionPostRequest(2, 50, "TRANSFER");
        var requestBuilderPost = post("/transactions");

        //when
        mockMvc.perform(requestBuilderPost.contentType(MediaType.APPLICATION_JSON).content(ow.writeValueAsString(tpr)))
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                            {
                                                "id": 4
                                            }
                                        """)
                );
    }

    @Test
    public void saveTransaction_TransactionDataNotValid_ReturnTransactionPostResponse() throws Exception {
        //given
        fillSimpleSellerData();
        fillSimpleTransactionData();

        TransactionPostRequest tpr = new TransactionPostRequest(2, -50, "TRANSFER");
        var requestBuilderPost = post("/transactions");

        //when
        mockMvc.perform(requestBuilderPost.contentType(MediaType.APPLICATION_JSON).content(ow.writeValueAsString(tpr)))
                //then
                .andExpectAll(
                        status().is(400),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                            {
                                                "message": "amount - must be greater than or equal to 0; ",
                                                "fieldsWithError": [
                                                    "amount"
                                                ]
                                            }
                                        """)
                );
    }
}
