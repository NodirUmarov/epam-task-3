package com.epam.business.service;

import com.epam.business.mapper.dto.OrderMapper;
import com.epam.business.model.dto.GiftCertificateDto;
import com.epam.business.model.dto.OrderDto;
import com.epam.business.model.dto.UserDetailsDto;
import com.epam.business.model.request.MakeOrderRequest;
import com.epam.business.service.impl.OrderServiceImpl;
import com.epam.domain.entity.certificate.GiftCertificate;
import com.epam.domain.entity.certificate.Order;
import com.epam.domain.entity.user.UserDetails;
import com.epam.domain.repository.OrderRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private GiftCertificateService giftCertificateService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    @DisplayName("should make an order")
    @ParameterizedTest
    @MethodSource("orderProvider")
    void shouldMakeOrder(MakeOrderRequest makeOrderRequest) {
        UserDetails userDetails = generateUserDetails();
        when(giftCertificateService.findAllByNames(makeOrderRequest.getGiftCertificatesNames()))
                .thenReturn(makeOrderRequest.getGiftCertificatesNames().stream()
                        .map(s -> GiftCertificateDto.builder()
                                .certificateName(s)
                                .price(BigDecimal.valueOf(250)).build())
                        .collect(Collectors.toList()));

        when(userDetailsService.getUserDetailsDtoByUsername(userDetails.getId()))
                .thenReturn(generateUserDetailsDto());

        Order order = new Order();
        order.setCreatedBy(userDetails);
        order.setOrderedCertificates(makeOrderRequest.getGiftCertificatesNames().stream()
                .map(s -> {
                    GiftCertificate giftCertificate = new GiftCertificate();
                    giftCertificate.setCertificateName(s);
                    giftCertificate.setPrice(BigDecimal.valueOf(250));
                    return giftCertificate;
                }).collect(Collectors.toList()));
        order.setTotalPrice(BigDecimal.valueOf(500));
        order.setLastModifiedBy(userDetails);

        when(orderMapper.toEntity(OrderDto.builder()
                .lastModifiedBy(generateUserDetailsDto())
                .orderedBy(generateUserDetailsDto())
                .totalPrice(order.getTotalPrice())
                .orderedCertificates(makeOrderRequest.getGiftCertificatesNames().stream()
                        .map(s -> GiftCertificateDto.builder()
                                .certificateName(s)
                                .price(BigDecimal.valueOf(250)).build())
                        .collect(Collectors.toList()))
                .build())).thenReturn(order);

        orderServiceImpl.create(userDetails.getId(), makeOrderRequest);

        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);

        verify(orderRepository).save(orderArgumentCaptor.capture());

        Order captured = orderArgumentCaptor.getValue();

        Assertions.assertThat(captured).usingRecursiveComparison().ignoringFields("createdDate", "lastModifiedDate").isEqualTo(order);
    }

    private UserDetails generateUserDetails() {
        UserDetails userDetails = new UserDetails();
        userDetails.setId("username@gmail.com");
        userDetails.setDob(LocalDate.now());
        userDetails.setGiftCertificates(new ArrayList<>());
        userDetails.setFirstName("fName");
        userDetails.setLastName("lName");
        userDetails.setPatronymic("patreName");
        userDetails.setSendEmail(true);
        userDetails.setPhoneNumber("+999 999 999 999");
        userDetails.setFullName("fname lname patreName");
        return userDetails;
    }

    private UserDetailsDto generateUserDetailsDto() {
        return UserDetailsDto.builder()
                .username("username@gmail.com")
                .dob(LocalDate.now())
                .giftCertificates(new ArrayList<>())
                .firstName("fName")
                .lastName("lName")
                .patronymic("patreName")
                .sendEmail(true)
                .phoneNumber("+999 999 999 999")
                .fullName("fname lname patreName")
                .build();
    }

    private static Stream<Arguments> orderProvider() {
        return IntStream.range(1, 51).mapToObj(value -> {
            MakeOrderRequest request = new MakeOrderRequest();
            request.setDedicatedTo("dedicated@gmail.com");
            request.setGiftCertificatesNames(List.of("c1", "c2"));

            return Arguments.of(request);
        });
    }
}