package com.epam.api.controller.integration;

import com.epam.api.controller.UserDetailsController;
import com.epam.api.model.hypermediaresource.UserDetailsResource;
import com.epam.business.exception.EntityExistsException;
import com.epam.business.model.dto.UserDetailsDto;
import com.epam.business.model.request.CreateUserDetailsRequest;
import com.epam.domain.entity.user.UserDetails;
import java.time.LocalDate;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
@TestPropertySource(locations = {"classpath:test.env"})
class UserDetailsControllerTest {

    @Autowired
    private UserDetailsController userDetailsController;

    @DisplayName("Should create user details")
    @ParameterizedTest
    @MethodSource("userDetailsProvider")
    void shouldCreateUserDetails(CreateUserDetailsRequest request) {
        ResponseEntity<?> response = userDetailsController.create(request);

        UserDetailsResource created = (UserDetailsResource) response.getBody();

        Assertions.assertThat(request).usingRecursiveComparison().isEqualTo(created);

        ResponseEntity<?> responseUser = userDetailsController.getUserByUsername(request.getUsername());
        UserDetailsResource foundFromDB = (UserDetailsResource) responseUser.getBody();

        Assertions.assertThat(created).isEqualTo(foundFromDB);
    }

    @DisplayName("Should create user details")
    @ParameterizedTest
    @MethodSource("userDetailsProvider")
    void shouldThrowEntityExistsException(CreateUserDetailsRequest request) {
        userDetailsController.create(request);
        Assertions.assertThat(userDetailsController.create(request).getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }

        private static Stream<Arguments> userDetailsProvider() {
        return LongStream.range(1,51).mapToObj(value -> {
            CreateUserDetailsRequest request = new CreateUserDetailsRequest();
            request.setUsername("username_" + value * 10000 + "@gmail.com");
            request.setFirstName("first_name_" + value);
            request.setLastName("last_name_" + value);
            request.setPatronymic("patronymic_" + value);
            request.setDob(LocalDate.now().minusYears(value));
            request.setSendEmail(true);

            return Arguments.of(request);
        });
    }
}