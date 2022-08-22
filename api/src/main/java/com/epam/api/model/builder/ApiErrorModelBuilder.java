package com.epam.api.model.builder;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/16/2022
 */

import com.epam.api.internationalization.MessageCode;
import com.epam.api.internationalization.Translator;
import com.epam.api.model.response.EndPointErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ApiErrorModelBuilder {

    private Translator translator;

    private EndPointErrorResponse generateApiError(MessageCode messageCode, HttpStatus httpStatus) {
        return new EndPointErrorResponse(httpStatus, translator.getMessage(messageCode));
    }

    public ResponseEntity<Object> buildResponseEntity(MessageCode messageCode, HttpStatus httpStatus) {
        return new ResponseEntity<>(generateApiError(messageCode, httpStatus), httpStatus);
    }
}
