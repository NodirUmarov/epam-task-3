package com.epam.business.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/29/2022
 */
@Data
@ApiModel
@Builder
public class UserDetailsDto implements Serializable {

    @ApiModelProperty(name = "username",
            required = true)
    private final String username;

    @ApiModelProperty(name = "firstName",
            required = true,
            position = 1)
    private final String firstName;

    @ApiModelProperty(name = "lastName",
            required = true,
            position = 2)
    private final String lastName;

    @ApiModelProperty(name = "patronymic",
            required = true,
            position = 3)
    private final String patronymic;

    @ApiModelProperty(name = "phoneNumber",
            required = true,
            position = 4)
    private final String phoneNumber;

    @ApiModelProperty(name = "dob",
            required = true,
            position = 5)
    private final LocalDate dob;

    @ApiModelProperty(name = "fullName",
            required = true,
            position = 6)
    private final String fullName;

    @ApiModelProperty(name = "sendEmail",
            required = true,
            position = 7)
    private final Boolean sendEmail;

    @ApiModelProperty(name = "giftCertificates",
            required = true,
            position = 8)
    private final List<GiftCertificateDto> giftCertificates;
}