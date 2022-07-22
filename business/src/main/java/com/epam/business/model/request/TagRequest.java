package com.epam.business.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
@Data
@ApiModel
@NoArgsConstructor
public class TagRequest {

    @NotBlank
    @ApiModelProperty(value = "The unique name of the tag",
            name = "name",
            required = true)
    private String name;



}