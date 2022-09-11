package com.epam.api.model.hypermediaresource;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/5/2022
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDetailsResource extends RepresentationModel<UserDetailsResource> {

    @ApiModelProperty(name = "username",
            required = true)
    private String username;

    @ApiModelProperty(name = "firstName",
            required = true,
            position = 1)
    private String firstName;

    @ApiModelProperty(name = "lastName",
            required = true,
            position = 2)
    private String lastName;

    @ApiModelProperty(name = "patronymic",
            required = true,
            position = 3)
    private String patronymic;

    @ApiModelProperty(name = "phoneNumber",
            required = true,
            position = 4)
    private String phoneNumber;

    @ApiModelProperty(name = "dob",
            required = true,
            position = 5)
    private LocalDate dob;

    @ApiModelProperty(name = "fullName",
            required = true,
            position = 6)
    private String fullName;

    @ApiModelProperty(name = "sendEmail",
            required = true,
            position = 7)
    private Boolean sendEmail;

    @ApiModelProperty(name = "giftCertificates",
            required = true,
            position = 8)
    private Set<GiftCertificateResource> giftCertificates;

}
