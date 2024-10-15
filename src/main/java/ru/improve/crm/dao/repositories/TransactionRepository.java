package ru.improve.crm.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.improve.crm.models.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
