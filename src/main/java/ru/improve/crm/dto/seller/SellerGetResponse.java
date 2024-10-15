package ru.improve.crm.dto.seller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SellerGetResponse {

    private int id;

    private String name;

    private String contactInfo;

    private LocalDateTime registrationDate;
}
