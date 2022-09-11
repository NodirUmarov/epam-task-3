package com.epam.mailsender.service;

import com.epam.mailsender.model.SendMailRequest;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/18/2022
 */
public interface MailSenderService {

    void sendMail(SendMailRequest sendMailRequest);

}
