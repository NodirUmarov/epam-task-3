package com.epam.business.model.request;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/29/2022
 */
@Data
@NoArgsConstructor
public class CreateUserDetailsRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String phoneNumber;
    private LocalDateTime dob;
    private Boolean sendEmail;
}
