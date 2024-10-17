package ru.improve.crm.controllers;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Transactional
public class TransactionControllerIt {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager em;

    @Test
    public void getAllTransactions_ReturnsValidTransactions() {

    }
}
