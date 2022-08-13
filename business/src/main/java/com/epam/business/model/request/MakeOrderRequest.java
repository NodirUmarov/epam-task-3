package com.epam.business.model.request;

import java.util.List;
import lombok.Data;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/9/2022
 */
@Data
public class MakeOrderRequest {
    private List<String> giftCertificatesNames;
    private String dedicatedTo;
}
