package com.epam.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class EndPointErrorResponse {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;

    private EndPointErrorResponse() {
        timestamp = LocalDateTime.now();
    }

    public EndPointErrorResponse(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }
}