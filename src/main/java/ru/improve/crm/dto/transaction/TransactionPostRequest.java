package ru.improve.crm.dto.transaction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionPostRequest {

    @NotNull
    private int sellerId;

    @Min(0)
    private int amount;

    @NotNull
    private String paymentType;
}
