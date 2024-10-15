package ru.improve.crm.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionGetResponse {

    private int id;

    private int sellerId;

    private int amount;

    private String paymentType;

    private LocalDateTime transactionDate;
}
