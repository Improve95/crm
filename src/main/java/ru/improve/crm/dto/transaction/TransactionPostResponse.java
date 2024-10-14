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
public class TransactionPostResponse {

    private int id;

    private LocalDateTime transactionDate;
}
