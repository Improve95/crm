package ru.improve.crm.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.improve.crm.dto.models.Seller;

import java.time.LocalDateTime;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionGetResponse {

    private int id;

    private Seller seller;

    private int amount;

    private String paymentType;

    private LocalDateTime transactionDate;
}
