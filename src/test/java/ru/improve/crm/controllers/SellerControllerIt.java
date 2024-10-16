package ru.improve.crm.controllers;

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
import ru.improve.crm.dto.seller.SellerPatchRequest;
import ru.improve.crm.dto.seller.SellerPostRequest;

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

    private static final String URL = "localhost:8080";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private EntityManager em;

    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @BeforeEach
    void truncateTable() {
        em.createNativeQuery("set search_path to public").executeUpdate();
        em.createNativeQuery("alter sequence sellers_id_seq restart with 1").executeUpdate();
        em.createNativeQuery("alter sequence transactions_id_seq restart with 1").executeUpdate();
        em.createNativeQuery("truncate sellers cascade").executeUpdate();
        em.createNativeQuery("truncate transactions").executeUpdate();
    }

    private void fillSimpleData() {
        em.createNativeQuery("insert into sellers (name, contact_info, registration_date) values ('name1', 'contact1', current_timestamp)")
                .executeUpdate();
        em.createNativeQuery("insert into sellers (name, contact_info, registration_date) values ('name2', 'contact2', current_timestamp)")
                .executeUpdate();
    }

    @Test
    void getAllSellers_ReturnsValidSellerDataResponse() throws Exception {
        //given
        fillSimpleData();
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
                                    }
                                ]
                                """)
                );

    }

    @Test
    void getSellerById_ReturnsValidSellerDataResponse() throws Exception {
        //given
        fillSimpleData();
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
        fillSimpleData();
        var reqBuilderPost = post("/sellers");
        SellerPostRequest spr = new SellerPostRequest("name3", "contact3");

        //when
        this.mockMvc.perform(reqBuilderPost.contentType(MediaType.APPLICATION_JSON).content(ow.writeValueAsString(spr)))
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                    {
                                        "id": 3
                                    }
                                """)
                );
    }

    @Test
    void saveSeller_NotValidPostSellerData_ReturnsIdSavedSeller() throws Exception {
        //given
        fillSimpleData();
        var reqBuilderPost = post("/sellers");
        SellerPostRequest spr = new SellerPostRequest("name3", "contact2");

        //when
        this.mockMvc.perform(reqBuilderPost.contentType(MediaType.APPLICATION_JSON).content(ow.writeValueAsString(spr)))
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
        fillSimpleData();

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
                        status().is(400),
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
        fillSimpleData();

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
        fillSimpleData();

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
}
