package ru.improve.crm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.improve.crm.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
