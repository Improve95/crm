package ru.improve.crm.dto.transaction;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.improve.crm.models.Seller;

import java.time.LocalDateTime;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDataResponse {

    private int id;

    @JsonManagedReference
    private Seller seller;

    private int amount;

    private String paymentType;

    private LocalDateTime transactionDate;
}
