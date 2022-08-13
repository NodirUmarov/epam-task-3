package com.epam.business.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
@Data
@ApiModel
@NoArgsConstructor
public class UpdateGiftCertificateRequest {

    @ApiModelProperty(value = "Username of user who updates gift-certificate",
            name = "updatedBy")
    private String updatedBy;

    @ApiModelProperty(value = "The unique name of the gift-certificate",
            name = "giftCertificateName")
    private String giftCertificateName;

    @ApiModelProperty(value = "The price of the gift-certificate",
            name = "price",
            allowableValues = "[0, infinity]",
            position = 1)
    private BigDecimal price;

    @ApiModelProperty(value = "Duration of the gift-certificate in days",
            name = "duration",
            allowableValues = "[0, infinity]",
            position = 2)
    private Long duration;

    @ApiModelProperty(value = "Description of the gift-certificate",
            name = "description",
            position = 3)
    private String description;
}
