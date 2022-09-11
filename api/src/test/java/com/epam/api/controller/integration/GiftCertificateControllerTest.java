package com.epam.api.controller.integration;

import com.epam.api.controller.GiftCertificateController;
import com.epam.api.model.hypermediaresource.GiftCertificateResource;
import com.epam.business.exception.EntityNameNotFoundException;
import com.epam.business.model.enums.GiftCertificateSortBy;
import com.epam.business.model.enums.SortType;
import com.epam.business.model.request.TagRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("dev")
@TestPropertySource(locations = {"classpath:test.env"})
class GiftCertificateControllerTest {

    // TODO: 1/9/2022 mapping of user

    @Autowired
    private GiftCertificateController giftCertificateController;

    @BeforeEach
    void beforeEach() {
        Assertions.assertThat(giftCertificateController).isNotNull();
    }

    @DisplayName("Should get certificate by name")
    @ParameterizedTest
    @MethodSource("giftCertificateNameProvider")
    void shouldGetCertificateByName(String name) {
        ResponseEntity<?> retrieved = giftCertificateController.getByName(name);

        Assertions.assertThat(retrieved.getBody()).isNotNull().isExactlyInstanceOf(GiftCertificateResource.class);

        GiftCertificateResource giftCertificateResource = (GiftCertificateResource) retrieved.getBody();
        Assertions.assertThat(giftCertificateResource.getCertificateName()).isEqualTo(name);

        Assertions.assertThat(giftCertificateResource.getId()).isNotNull().isNotNegative();
    }

    @DisplayName("Should get certificate by id")
    @ParameterizedTest
    @MethodSource("giftCertificateIdProvider")
    void shouldGetById(Long id) {
        ResponseEntity<?> retrieved = giftCertificateController.getById(id);

        Assertions.assertThat(retrieved.getBody()).isNotNull().isExactlyInstanceOf(GiftCertificateResource.class);

        GiftCertificateResource giftCertificateResource = (GiftCertificateResource) retrieved.getBody();
        Assertions.assertThat(giftCertificateResource.getId()).isEqualTo(id);
        Assertions.assertThat(giftCertificateResource.getCreateDate()).isBefore(LocalDateTime.now());
        Assertions.assertThat(giftCertificateResource.getCertificateName()).isEqualTo("certificate_name_" + giftCertificateResource.getId());

    }

    @DisplayName("Should add tag to certificate and get certificate by tag")
    @ParameterizedTest
    @MethodSource("addTagProvider")
    void shouldGetByTag(Long id, List<TagRequest> tagRequests) {
        ResponseEntity<?> retrievedGiftCertificate = giftCertificateController.addTagsToCertificate(id, tagRequests);

        Assertions.assertThat(retrievedGiftCertificate.getBody()).isNotNull().isExactlyInstanceOf(GiftCertificateResource.class);

        GiftCertificateResource giftCertificateResource = (GiftCertificateResource) retrievedGiftCertificate.getBody();

        tagRequests.forEach(tagRequest -> {
            ResponseEntity<?> retrievedCertificates = giftCertificateController.getByTag(tagRequest.getTagName(), 200, 0, SortType.DESC, GiftCertificateSortBy.UPDATED);

            Assertions.assertThat(retrievedCertificates.getBody()).isNotNull().isExactlyInstanceOf(CollectionModel.class);
            CollectionModel<GiftCertificateResource> giftCertificateResources = (CollectionModel<GiftCertificateResource>) retrievedCertificates.getBody();

            Assertions.assertThat(giftCertificateResources).isNotEmpty();

            Assertions.assertThat(giftCertificateResources).contains(giftCertificateResource);
        });
    }

    @DisplayName("Should not add tag to certificate and get certificate by tag")
    @ParameterizedTest
    @MethodSource("addTagProvider")
    void shouldNotGetByTag(Long id, List<TagRequest> tagRequests) {
        ResponseEntity<?> retrievedGiftCertificate = giftCertificateController.untagCertificate(id, tagRequests);

        Assertions.assertThat(retrievedGiftCertificate.getBody()).isNotNull().isExactlyInstanceOf(GiftCertificateResource.class);

        GiftCertificateResource giftCertificateResource = (GiftCertificateResource) retrievedGiftCertificate.getBody();

        tagRequests.forEach(tagRequest -> {
            ResponseEntity<?> retrievedCertificates = giftCertificateController.getByTag(tagRequest.getTagName(), 200, 0, SortType.DESC, GiftCertificateSortBy.UPDATED);

            Assertions.assertThat(retrievedCertificates.getBody()).isNotNull().isExactlyInstanceOf(CollectionModel.class);
            CollectionModel<GiftCertificateResource> giftCertificateResources = (CollectionModel<GiftCertificateResource>) retrievedCertificates.getBody();

            Assertions.assertThat(giftCertificateResources).isNotEmpty();

            Assertions.assertThat(giftCertificateResources).doesNotContain(giftCertificateResource);
        });
    }

    @DisplayName("Should delete GiftCertificate")
    @ParameterizedTest
    @MethodSource("giftCertificateNameProvider")
    void deleteById(String name) {
        giftCertificateController.deleteByName(name);

        Assertions.assertThat(giftCertificateController.getByName(name).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private static Stream<Arguments> giftCertificateNameProvider() {
        return IntStream.range(1, 201).mapToObj(value -> Arguments.of("certificate_name_" + value));
    }

    private static Stream<Arguments> giftCertificateIdProvider() {
        return LongStream.range(1, 201).mapToObj(Arguments::of);
    }

    private static Stream<Arguments> addTagProvider() {
        return LongStream.range(1, 201).mapToObj(value -> {
            List<TagRequest> requestSet = new ArrayList<>();
            for (int i = 1; i < 5; i++) {
                TagRequest tagRequest = new TagRequest();
                tagRequest.setTagName("Tag #" + (1 + (int) (Math.random() * 50)));
                requestSet.add(tagRequest);
            }
            return Arguments.of(value, requestSet);
        });
    }
}