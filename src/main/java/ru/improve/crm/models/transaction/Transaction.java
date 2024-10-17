package ru.improve.crm.models.transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.improve.crm.models.Seller;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "seller", referencedColumnName = "id")
    private Seller seller;

    private int amount;

    /* не знаю почему в тз указана строка как тип данных,
        логичнее было бы использовать перечисления для типа перевода */
    private String paymentType;

    private LocalDateTime transactionDate;

    public Transaction(Seller seller, int amount, String paymentType, LocalDateTime transactionDate) {
        this.seller = seller;
        this.amount = amount;
        this.paymentType = paymentType;
        this.transactionDate = transactionDate;
    }
}
