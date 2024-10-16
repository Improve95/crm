package ru.improve.crm.controllers;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.improve.crm.dao.repositories.SellerRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Transactional
@RequiredArgsConstructor
public class SellerControllerIt {

    private final MockMvc mockMvc;

    private final SellerRepository sellerRepository;

    @Test
    void getAllSellers_ReturnsValidSeller() throws Exception {
        //given
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
                                    }
                                ]
                                """)
                );
    }
}
