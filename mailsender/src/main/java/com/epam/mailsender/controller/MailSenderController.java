package com.epam.mailsender.controller;

import com.epam.mailsender.model.EndPointErrorResponse;
import com.epam.mailsender.model.SendMailRequest;
import com.epam.mailsender.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/18/2022
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/send-mail")
public class MailSenderController {

    private final MailSenderService mailSenderService;

    @PostMapping
    public ResponseEntity<?> sendMail(@RequestBody @Validated SendMailRequest sendMailRequest) {
        try {
            log.info("Sending mail to users...");
            mailSenderService.sendMail(sendMailRequest);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Failed to send error. Error message: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(EndPointErrorResponse.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .message("Some error occurred during sending email. Please contact certificates.assist@gmail.com for further clarifications.")
                            .build());
        }
    }
}
