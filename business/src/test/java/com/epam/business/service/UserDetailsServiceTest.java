package com.epam.business.service;

import com.epam.business.client.MailSenderClient;
import com.epam.business.mapper.dto.GiftCertificateMapper;
import com.epam.business.mapper.dto.UserDetailsMapper;
import com.epam.business.mapper.request.CreateUserDetailsMapper;
import com.epam.business.model.dto.UserDetailsDto;
import com.epam.business.model.request.CreateUserDetailsRequest;
import com.epam.business.service.impl.UserDetailsServiceImpl;
import com.epam.domain.entity.user.UserDetails;
import com.epam.domain.repository.UserDetailsRepository;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @Mock
    private UserDetailsMapper userDetailsMapper;

    @Mock
    private CreateUserDetailsMapper createDetailsUserMapper;

    @Mock
    private GiftCertificateMapper giftCertificateMapper;

    @Mock
    private MailSenderClient mailSenderClient;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @DisplayName("Should create user details")
    @ParameterizedTest
    @MethodSource("createUserProvider")
    void shouldCreateUserDetails(CreateUserDetailsRequest request) {
        when(userDetailsRepository.existsById(request.getUsername())).thenReturn(false);

        setUpCreateUserDetailsMapper(request);

        userDetailsService.create(request);

        ArgumentCaptor<UserDetails> userDetailsArgumentCaptor = ArgumentCaptor.forClass(UserDetails.class);

        verify(userDetailsRepository).save(userDetailsArgumentCaptor.capture());

        UserDetails userDetails = userDetailsArgumentCaptor.getValue();

        Assertions.assertThat(request).usingRecursiveComparison().ignoringFields("username").isEqualTo(userDetails);
        Assertions.assertThat(request.getUsername()).isEqualTo(userDetails.getId());
    }

    @DisplayName("should get user details by username")
    @ParameterizedTest
    @MethodSource("createUserProvider")
    void shouldGetUserDetailsDtoByUsername(CreateUserDetailsRequest request) {

        when(userDetailsRepository.findById(request.getUsername())).thenReturn(Optional.of(new UserDetails()));

        userDetailsService.getUserDetailsDtoByUsername(request.getUsername());

        ArgumentCaptor<String> userNameCaptor = ArgumentCaptor.forClass(String.class);

        verify(userDetailsRepository).findById(userNameCaptor.capture());

        String capturedUsername = userNameCaptor.getValue();

        Assertions.assertThat(capturedUsername).isEqualTo(request.getUsername());
    }

    private void setUpCreateUserDetailsMapper(CreateUserDetailsRequest request) {
        when(createDetailsUserMapper.toEntity(request)).then(invocation -> {
            UserDetails userDetails = new UserDetails();
            userDetails.setId(request.getUsername());
            userDetails.setDob(request.getDob());
            userDetails.setPatronymic(request.getPatronymic());
            userDetails.setPhoneNumber(request.getPhoneNumber());
            userDetails.setSendEmail(request.getSendEmail());
            userDetails.setLastName(request.getLastName());
            userDetails.setFirstName(request.getFirstName());
            userDetails.setFullName(request.getFirstName() + " " + request.getLastName() + " " + request.getPatronymic());
            return userDetails;
        });
    }

    private static Stream<Arguments> createUserProvider() {
        return IntStream.range(1, 51).mapToObj(value -> {
            CreateUserDetailsRequest request = new CreateUserDetailsRequest();
            request.setUsername("username_" + value + "@gmail.com");
            request.setFirstName("fName #" + value);
            request.setLastName("lName #" + value);
            request.setPatronymic("patre #" + value);
            request.setDob(LocalDate.now());
            request.setPhoneNumber("+999 999 999 999");
            request.setSendEmail(true);

            return Arguments.of(request);
        });
    }

}