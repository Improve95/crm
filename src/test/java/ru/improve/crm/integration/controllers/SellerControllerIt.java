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
import ru.improve.crm.dao.repositories.SellerRepository;
import ru.improve.crm.dto.seller.MostProductivityByPeriodRequest;
import ru.improve.crm.dto.seller.SellerPatchRequest;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.WithLessAmountByPeriodRequest;

import java.time.LocalDateTime;
import java.time.Period;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Transactional
public class SellerControllerIt {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private EntityManager em;

    private LocalDateTime date = LocalDateTime.now();

    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    private ObjectMapper om = new ObjectMapper().findAndRegisterModules();

    @BeforeEach
    void truncateTable() {
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
        em.createNativeQuery("insert into sellers (name, contact_info, registration_date) values ('name3', 'contact3', current_timestamp)")
                .executeUpdate();
    }

    private void fillTransactionData() {
        em.createNativeQuery("insert into transactions (seller, amount, payment_type, transaction_date) VALUES (1, 100, 'CARD', current_timestamp)")
                .executeUpdate();
        em.createNativeQuery("insert into transactions (seller, amount, payment_type, transaction_date) VALUES (1, 1000, 'CARD', current_timestamp)")
                .executeUpdate();
        em.createNativeQuery("insert into transactions (seller, amount, payment_type, transaction_date) VALUES (1, 1500, 'CASH', current_timestamp)")
                .executeUpdate();
        em.createNativeQuery("insert into transactions (seller, amount, payment_type, transaction_date) VALUES (2, 1500, 'CASH', current_timestamp)")
                .executeUpdate();
        em.createNativeQuery("insert into transactions (seller, amount, payment_type, transaction_date) VALUES (2, 1500, 'CASH', current_timestamp)")
                .executeUpdate();
        em.createNativeQuery("insert into transactions (seller, amount, payment_type, transaction_date) VALUES (2, 1500, 'CASH', current_timestamp)")
                .executeUpdate();
        em.createNativeQuery("insert into transactions (seller, amount, payment_type, transaction_date) VALUES (2, 1500, 'CASH', current_timestamp)")
                .executeUpdate();
    }

    @Test
    void getAllSellers_ReturnsValidSellerDataResponse() throws Exception {
        //given
        fillSimpleSellerData();
        var reqBuilder = get("/sellers");

        //when
        this.mockMvc.perform(reqBuilder)
        //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                    {
                                        "id": 1,
                                        "name": "name1",
                                        "contactInfo": "contact1"
                                    },
                                    {
                                        "id": 2,
                                        "name": "name2",
                                        "contactInfo": "contact2"
                                    },
                                    {
                                        "id": 3,
                                        "name": "name3",
                                        "contactInfo": "contact3"
                                    }
                                ]
                                """)
                );

    }

    @Test
    void getSellerById_ReturnsValidSellerDataResponse() throws Exception {
        //given
        fillSimpleSellerData();
        var reqBuilder = get("/sellers/1");

        //when
        this.mockMvc.perform(reqBuilder)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                    {
                                        "id": 1,
                                        "name": "name1",
                                        "contactInfo": "contact1"
                                    }
                                """)
                );
    }

    @Test
    void saveSeller_ValidPostSellerData_ReturnsIdSavedSeller() throws Exception {
        //given
        fillSimpleSellerData();
        var reqBuilderPost = post("/sellers");
        SellerPostRequest spr = new SellerPostRequest("name4", "contact4");

        //when
        this.mockMvc.perform(reqBuilderPost.contentType(MediaType.APPLICATION_JSON).content(ow.writeValueAsString(spr)))
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
    void saveSeller_NotValidPostSellerData_ReturnsIdSavedSeller() throws Exception {
        //given
        fillSimpleSellerData();
        var reqBuilderPost = post("/sellers");
        SellerPostRequest spr = new SellerPostRequest("name3", "contact2");

        //when
        this.mockMvc.perform(reqBuilderPost.contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(spr)))
                //then
                .andExpectAll(
                        status().is(400),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                    {
                                        "fieldsWithError": ["contactInfo"]
                                    }
                                    """)
                );
    }

    @Test
    void deleteSellerById_ReturnsEmptyResponseEntity() throws Exception {
        //giver
        fillSimpleSellerData();

        var reqBuilderGet = get("/sellers/1");
        this.mockMvc.perform(reqBuilderGet)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                    {
                                        "id": 1,
                                        "name": "name1",
                                        "contactInfo": "contact1"
                                    }
                                """)
                );

        var reqBuilderDelete = delete("/sellers/1");
        this.mockMvc.perform(reqBuilderDelete)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().string("")
                );

        var reqBuilderGet2 = get("/sellers/1");
        this.mockMvc.perform(reqBuilderGet2)
                //then
                .andExpectAll(
                        status().is(404),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                    {
                                        "message": "not found seller",
                                        "fieldsWithError": ["id"]
                                    }
                                    """)
                );
    }

    @Test
    public void patchSeller_ValidPatchData_ReturnPatchSellerData() throws Exception {
        //given
        fillSimpleSellerData();

        var regBuilderPatch = patch("/sellers/1");
        SellerPatchRequest spr = new SellerPatchRequest("name10", "contact10");

        //when
        this.mockMvc.perform(regBuilderPatch.contentType(MediaType.APPLICATION_JSON).content(ow.writeValueAsString(spr)))
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                    {
                                        "id": 1,
                                        "name": "name10",
                                        "contactInfo": "contact10"
                                    }
                                """)
                );
    }

    @Test
    public void patchSeller_NotValidPatchData_ReturnPatchSellerData() throws Exception {
        //given
        fillSimpleSellerData();

        var regBuilderPatch = patch("/sellers/1");
        SellerPatchRequest spr = new SellerPatchRequest("", "contact10");

        //when
        this.mockMvc.perform(regBuilderPatch.contentType(MediaType.APPLICATION_JSON).content(ow.writeValueAsString(spr)))
                //then
                .andExpectAll(
                        status().is(400),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                    {
                                        "fieldsWithError": ["name"]
                                    }
                                    """)
                );
    }

    @Test
    public void getMostProductivitySellerByPeriod_RequestIsValid_ReturnSellers() throws Exception {
        //given
        fillSimpleSellerData();
        fillTransactionData();

        LocalDateTime startPeriod = LocalDateTime.now().minus(Period.ofDays(1));
        LocalDateTime endPeriod = LocalDateTime.now().plus(Period.ofDays(1));
        MostProductivityByPeriodRequest req = new MostProductivityByPeriodRequest(startPeriod, endPeriod);
        var reqBuilder = get("/sellers/mostProductivity");

        //when
        mockMvc.perform(reqBuilder.contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(req)))
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                        {
                                            "id": 2,
                                            "name": "name2",
                                            "contactInfo": "contact2"
                                        }
                                    """)
                );
    }

    @Test
    public void getSellersWithLessAmountByPeriod_RequestIsValid_ReturnSellers() throws Exception {
        //given
        fillSimpleSellerData();
        fillTransactionData();

        LocalDateTime startPeriod = LocalDateTime.now().minus(Period.ofDays(1));
        LocalDateTime endPeriod = LocalDateTime.now().plus(Period.ofDays(1));
        WithLessAmountByPeriodRequest req = new WithLessAmountByPeriodRequest(3000, startPeriod, endPeriod);
        var reqBuilder = get("/sellers/withLessAmount");

        //when
        mockMvc.perform(reqBuilder.contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(req)))
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                        [
                                            {
                                                "id": 1,
                                                "name": "name1",
                                                "contactInfo": "contact1"
                                            }
                                        ]
                                    """)
                );

        req = new WithLessAmountByPeriodRequest(10000, startPeriod, endPeriod);
        mockMvc.perform(reqBuilder.contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(req)))
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                        [
                                            {
                                                "id": 1,
                                                "name": "name1",
                                                "contactInfo": "contact1"
                                            },
                                            {
                                                "id": 2,
                                                "name": "name2",
                                                "contactInfo": "contact2"
                                            }
                                        ]
                                    """)
                );
    }
}
