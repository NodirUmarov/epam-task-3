package com.epam.business.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel
public class TagDto implements Serializable {

    @ApiModelProperty(value = "The generated ID when saved in database",
            name = "ID",
            allowableValues = "[1, infinity]",
            required = true)
    private final Long id;

    @ApiModelProperty(value = "The unique name of the tag",
            name = "name",
            required = true,
            position = 1)
    private final String name;
}
