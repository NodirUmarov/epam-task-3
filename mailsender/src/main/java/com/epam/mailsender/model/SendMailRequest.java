package com.epam.mailsender.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/18/2022
 */
@Data
@NoArgsConstructor
public class SendMailRequest {

    @Email
    private String receiver;

    @NotBlank
    private String subject;
    private String text;

}
