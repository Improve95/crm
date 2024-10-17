package ru.improve.crm.error.responseBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultErrorResponse {

    public DefaultErrorResponse(String message) {
        this.message = message;
        this.time = LocalDateTime.now();
    }

    private String message;

    private LocalDateTime time;
}
