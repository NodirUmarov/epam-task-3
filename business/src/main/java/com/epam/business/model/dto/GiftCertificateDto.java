package com.epam.business.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel
public class GiftCertificateDto implements Serializable {

    @ApiModelProperty(value = "The generated ID when saved in database",
            name = "ID",
            required = true,
            allowableValues = "[1, infinity]")
    private final Long id;

    @ApiModelProperty(value = "The unique name of the gift certificate",
            name = "name",
            required = true,
            position = 1)
    private final String name;

    @ApiModelProperty(value = "Description of the gift certificate",
            name = "description",
            position = 2)
    private final String description;

    @ApiModelProperty(value = "The price of the gift certificate",
            name = "price",
            required = true,
            allowableValues = "[0, infinity]",
            position = 3)
    private final BigDecimal price;


    @ApiModelProperty(value = "Tags that gift certificate has",
            name = "tags",
            required = true,
            position = 5)
    private final Set<TagDto> tags;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "Duration of the gift certificate",
            name = "duration",
            required = true,
            position = 4)
    private final LocalDateTime duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "Date of gift certificate's creation",
            name = "createDate",
            required = true,
            position = 6)
    private final LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "Date of gift certificate's creation",
            name = "lastUpdateDate",
            position = 7)
    private final LocalDateTime lastUpdateDate;
}
