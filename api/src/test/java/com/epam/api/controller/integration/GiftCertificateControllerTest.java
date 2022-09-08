package com.epam.api.controller.integration;

import com.epam.api.controller.GiftCertificateController;
import com.epam.api.controller.OrderController;
import com.epam.api.controller.TagController;
import com.epam.api.controller.UserDetailsController;
import com.epam.business.model.request.CreateGiftCertificateRequest;
import com.epam.business.service.GiftCertificateService;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = {"classpath:test.env"})
class GiftCertificateControllerTest {

    // TODO: 1/9/2022 native query, mapping of user

    @Autowired
    private GiftCertificateController giftCertificateController;

    @Autowired
    private UserDetailsController userDetailsController;

    @Autowired
    private TagController tagController;

    @Autowired
    private OrderController orderController;

    @BeforeEach
    void beforeEach() {
        Assertions.assertThat(giftCertificateController).isNotNull();
        Assertions.assertThat(userDetailsController).isNotNull();
        Assertions.assertThat(tagController).isNotNull();
        Assertions.assertThat(orderController).isNotNull();
    }

    @Test
    void getByName() {
        Assertions.assertThat(giftCertificateController).isNotNull();
    }

    @Test
    void getById() {
    }

    @Test
    void getByTag() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void addTagsToCertificate() {
    }

    @Test
    void untagCertificate() {
    }

    @Test
    void deleteById() {
    }

    private static Stream<Arguments> giftCertificateRequestProvider() {
        return IntStream.range(1, 51).mapToObj(value -> {
            CreateGiftCertificateRequest request = new CreateGiftCertificateRequest();
            return null;
        });
    }

}