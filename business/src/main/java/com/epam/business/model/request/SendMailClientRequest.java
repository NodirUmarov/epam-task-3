package com.epam.business.model.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/18/2022
 */
@Data
@Builder
public class SendMailClientRequest {
    private final String receiver;
    private final String subject;
    private final String text;
}
