package ru.improve.crm.itegration.controllers;

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

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Transactional
public class ControllersTest {

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

        var requestBuilderGet = get("/not_exist_sellers");

        //when
        mockMvc.perform(requestBuilderGet)
                //then
                .andExpectAll(
                        status().is(500),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                        {
                                            "message": "No static resource not_exist_sellers."
                                        }
                                        """)
                );
    }
}
