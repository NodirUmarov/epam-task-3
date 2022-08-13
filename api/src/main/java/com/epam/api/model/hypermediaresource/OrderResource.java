package com.epam.api.model.hypermediaresource;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/10/2022
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderResource extends RepresentationModel<OrderResource> {

    @ApiModelProperty(value = "The unique id of the order",
            name = "id",
            required = true)
    private Long id;

    @ApiModelProperty(value = "Details of user who made this order",
            name = "orderedBy",
            required = true,
            position = 1)
    private UserDetailsResource orderedBy;

    @ApiModelProperty(value = "Details of user who changed this order",
            name = "lastModifiedBy",
            required = true,
            position = 2)
    private UserDetailsResource lastModifiedBy;

    @ApiModelProperty(value = "The date when order was made",
            name = "createdDate",
            required = true,
            position = 3)
    private LocalDateTime createdDate;

    @ApiModelProperty(value = "The date when order was changed",
            name = "lastModifiedDate",
            required = true,
            position = 4)
    private LocalDateTime lastModifiedDate;

    @ApiModelProperty(value = "The list of gift-certificates ordered",
            name = "orderedCertificates",
            required = true,
            position = 5)
    private Set<GiftCertificateResource> orderedCertificates;

    @ApiModelProperty(value = "Total price of all ordered gift-certificates",
            name = "totalPrice",
            required = true,
            position = 6)
    private BigDecimal totalPrice;
}
