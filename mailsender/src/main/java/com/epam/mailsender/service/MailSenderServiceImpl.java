package com.epam.mailsender.service;

import com.epam.mailsender.model.SendMailRequest;
import java.util.Date;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/18/2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(SendMailRequest sendMailRequest) {
        log.info("Preparing message to send...");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("certificates.support");
        message.setTo(sendMailRequest.getReceiver());
        message.setReplyTo("certificates.assist@gmail.com");
        message.setSubject(sendMailRequest.getSubject());
        message.setSentDate(new Date());
        message.setText(sendMailRequest.getText());

        log.info("Message is sending...");
        javaMailSender.send(message);
        log.info("Message send successfully. {}", message);
    }
}