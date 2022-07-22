package com.epam.business.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
@Data
@ApiModel
@NoArgsConstructor
public class CreateGiftCertificateRequest {

    @NotBlank
    @ApiModelProperty(value = "The unique name of the gift certificate",
            name = "name",
            required = true)
    private String name;

    @NotNull
    @PositiveOrZero
    @ApiModelProperty(value = "The price of the gift certificate",
            name = "price",
            required = true,
            allowableValues = "[0, infinity]",
            position = 1)
    private BigDecimal price;

    @PositiveOrZero
    @ApiModelProperty(value = "Duration of the gift certificate in days",
            name = "duration",
            allowableValues = "[0, infinity]",
            required = true,
            position = 2)
    private Long duration;

    @ApiModelProperty(value = "Description of the gift certificate",
            name = "description",
            position = 3)
    private String description;

    @ApiModelProperty(value = "Names of tags that gift certificate has",
            name = "tags",
            position = 4)
    private Set<TagRequest> tags;
}