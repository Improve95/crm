package ru.improve.crm.error.responseBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorResponse extends DefaultErrorResponse {

    private Set<String> fieldsWithError;

    public CustomErrorResponse(String message, Set<String> fieldsWithError) {
        super(message);
        this.fieldsWithError = fieldsWithError;
    }
}
