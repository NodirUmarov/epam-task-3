package com.epam.domain.repository.provider;

import com.epam.domain.entity.user.UserDetails;
import java.time.LocalDate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 9/5/2022
 */
public class UserDetailsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return IntStream.range(1, 51).mapToObj(value -> {
            UserDetails userDetails = new UserDetails();
            userDetails.setFirstName("First name #" + value);
            userDetails.setDob(LocalDate.now().minusYears(value));
            userDetails.setLastName("Last name #" + value);
            userDetails.setPhoneNumber("+998 99 999 99 99");
            userDetails.setPatronymic("Patronymic #" + value);
            userDetails.setSendEmail(true);
            userDetails.setId("User_#" + value + "@gmail.com");
            userDetails.setFullName(userDetails.getFirstName() + " " + userDetails.getLastName() + " " + userDetails.getPatronymic());

            return Arguments.of(userDetails);
        });
    }
}
