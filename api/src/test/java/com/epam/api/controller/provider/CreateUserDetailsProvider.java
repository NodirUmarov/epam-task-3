package com.epam.api.controller.provider;

import com.epam.business.model.request.CreateUserDetailsRequest;
import java.time.LocalDate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class CreateUserDetailsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return IntStream.range(1, 51).mapToObj(value -> {
            CreateUserDetailsRequest request = new CreateUserDetailsRequest();
            request.setUsername("username_" + value + "@gmail.com");
            request.setFirstName("first_name_" + value);
            request.setLastName("last_name_" + value);
            request.setPatronymic("patronymic_" + value);
            request.setDob(LocalDate.now().minusYears(value));
            request.setSendEmail(true);
            return Arguments.of(request);
        });
    }
}