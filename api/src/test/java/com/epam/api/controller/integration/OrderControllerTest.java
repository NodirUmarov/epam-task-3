package com.epam.api.controller.integration;

import com.epam.api.controller.OrderController;
import com.epam.api.model.hypermediaresource.OrderResource;
import com.epam.business.model.enums.OrderSortBy;
import com.epam.business.model.enums.SortType;
import com.epam.business.model.request.MakeOrderRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("dev")
@TestPropertySource(locations = {"classpath:test.env"})
class OrderControllerTest {

    @Autowired
    private OrderController orderController;

    @DisplayName("Should make an order")
    @ParameterizedTest
    @MethodSource("makeOrderRequestProvider")
    void create(String username, MakeOrderRequest request) {
        ResponseEntity<?> responseOrder = orderController.create(username, request);
        OrderResource orderResource = (OrderResource) responseOrder.getBody();

        ResponseEntity<?> responseOrderList = orderController.getAll(username, 10000, 0, SortType.ASC, OrderSortBy.ID);
        CollectionModel<OrderResource> orderResourceList = (CollectionModel<OrderResource>) responseOrderList.getBody();

        Assertions.assertThat(orderResourceList)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("lastModifiedDate").contains(orderResource);
    }

    private static Stream<Arguments> makeOrderRequestProvider() {
        return IntStream.range(1, 51).mapToObj(value -> {
            MakeOrderRequest request = new MakeOrderRequest();
            request.setDedicatedTo("username_" + value + "@gmail.com");
            request.setGiftCertificatesNames(IntStream.range(1, 20)
                    .mapToObj(i -> "certificate_name_" + (1 + (int) (Math.random() * 200))).collect(Collectors.toList()));
            return Arguments.of("username_" + (value + 1) + "@gmail.com", request);
        });
    }
}