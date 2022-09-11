package com.epam.business.client;

import com.epam.business.model.request.SendMailClientRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/18/2022
 */
@FeignClient(name = "mailsender", url = "http://localhost:8081")
public interface MailSenderClient {

    @PostMapping(value = "/api/v1/send-mail", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> sendMail(@RequestBody SendMailClientRequest sendMailClientRequest);
}
