package com.epam.business.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/9/2022
 */
@Data
@ApiModel
@AllArgsConstructor
@Builder
public class OrderDto implements Serializable {

    @ApiModelProperty(value = "The unique id of the order",
            name = "id",
            required = true)
    private final Long id;

    @ApiModelProperty(value = "Details of user who made this order",
            name = "orderedBy",
            required = true,
            position = 1)
    private final UserDetailsDto orderedBy;

    @ApiModelProperty(value = "Details of user who changed this order",
            name = "lastModifiedBy",
            required = true,
            position = 2)
    private final UserDetailsDto lastModifiedBy;

    @ApiModelProperty(value = "The date when order was made",
            name = "createdDate",
            required = true,
            position = 3)
    private final LocalDateTime createdDate;

    @ApiModelProperty(value = "The date when order was changed",
            name = "lastModifiedDate",
            required = true,
            position = 4)
    private final LocalDateTime lastModifiedDate;

    @ApiModelProperty(value = "The list of gift-certificates ordered",
            name = "orderedCertificates",
            required = true,
            position = 5)
    private final List<GiftCertificateDto> orderedCertificates;

    @ApiModelProperty(value = "Total price of all ordered gift-certificates",
            name = "totalPrice",
            required = true,
            position = 6)
    private final BigDecimal totalPrice;
}
