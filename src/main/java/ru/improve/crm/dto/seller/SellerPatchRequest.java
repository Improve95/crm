package ru.improve.crm.dto.seller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerPatchRequest {

    @NotEmpty
    @Size(min = 2, max = 50)
    private String name;

    @NotEmpty
    @Size(min = 5, max = 100)
    private String contactInfo;
}
