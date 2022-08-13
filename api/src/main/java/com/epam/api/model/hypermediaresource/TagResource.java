package com.epam.api.model.hypermediaresource;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/10/2022
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TagResource extends RepresentationModel<TagResource> {

    @ApiModelProperty(value = "The generated ID when saved in database",
            name = "ID",
            allowableValues = "[1, infinity]",
            required = true)
    private Long id;

    @ApiModelProperty(value = "The unique name of the tag",
            name = "name",
            required = true,
            position = 1)
    private String tagName;
}