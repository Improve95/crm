package ru.improve.crm.dto.seller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SellerPostResponse {

    private int id;

    private LocalDateTime registrationDate;
}
