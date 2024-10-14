package ru.improve.crm.dto.seller;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SellerUpdateRequest {

    @NotEmpty
    @Range(min = 1, max = 50)
    private String name;

    @NotEmpty
    @Range(min = 1, max = 100)
    private String contactInfo;
}
