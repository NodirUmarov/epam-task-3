package com.epam.api.model.hypermediaresource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/10/2022
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class GiftCertificateResource extends RepresentationModel<GiftCertificateResource> {

    @JsonCreator
    public GiftCertificateResource(Long id, String certificateName, String description, BigDecimal price, Set<TagResource> tags, LocalDateTime duration, List<UserDetailsResource> giftToUser, LocalDateTime createDate, LocalDateTime lastUpdateDate, UserDetailsResource createdBy, UserDetailsResource lastModifiedBy) {
        this.id = id;
        this.certificateName = certificateName;
        this.description = description;
        this.price = price;
        this.tags = tags;
        this.duration = duration;
        this.giftToUser = giftToUser;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
    }

    @JsonProperty
    @ApiModelProperty(value = "The generated ID when saved in database",
            name = "ID",
            required = true,
            allowableValues = "[1, infinity]")
    private Long id;

    @JsonProperty
    @ApiModelProperty(value = "The unique name of the gift certificate",
            name = "name",
            required = true,
            position = 1)
    private String certificateName;

    @JsonProperty
    @ApiModelProperty(value = "Description of the gift certificate",
            name = "description",
            position = 2)
    private String description;

    @JsonProperty
    @ApiModelProperty(value = "The price of the gift certificate",
            name = "price",
            required = true,
            allowableValues = "[0, infinity]",
            position = 3)
    private BigDecimal price;

    @JsonProperty
    @ApiModelProperty(value = "Tags that gift certificate has",
            name = "tags",
            required = true,
            position = 5)
    private Set<TagResource> tags;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "Duration of the gift certificate",
            name = "duration",
            required = true,
            position = 4)
    private LocalDateTime duration;

    @JsonProperty
    @ApiModelProperty(value = "Users to whom the certificate are gifted",
            name = "giftToUser",
            required = true,
            position = 4)
    private List<UserDetailsResource> giftToUser;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "Date of gift certificate's creation",
            name = "createDate",
            required = true,
            position = 6)
    private LocalDateTime createDate;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "Date of gift certificate's creation",
            name = "lastUpdateDate",
            position = 7)
    private LocalDateTime lastUpdateDate;

    @JsonProperty
    @ApiModelProperty(name = "createdBy",
            required = true,
            position = 8)
    private UserDetailsResource createdBy;

    @JsonProperty
    @ApiModelProperty(name = "lastModifiedBy",
            required = true,
            position = 9)
    private UserDetailsResource lastModifiedBy;


}