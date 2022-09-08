package com.epam.domain.repository;

import com.epam.domain.config.DomainConfig;
import com.epam.domain.entity.certificate.GiftCertificate;
import com.epam.domain.entity.certificate.Order;
import com.epam.domain.entity.certificate.Tag;
import com.epam.domain.entity.user.UserDetails;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 9/5/2022
 */
@DataJpaTest
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") // IDE thinks there is no bean of the repository
@ContextConfiguration(classes = DomainConfig.class)
class OrderRepositoryTest {

    private final TagRepository tagRepository;
    private final EntityManager entityManager;
    private final OrderRepository orderRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final GiftCertificateRepository giftCertificateRepository;

    @Autowired
    public OrderRepositoryTest(TagRepository tagRepository, EntityManager entityManager, OrderRepository orderRepository, UserDetailsRepository userDetailsRepository, GiftCertificateRepository giftCertificateRepository) {
        this.tagRepository = tagRepository;
        this.entityManager = entityManager;
        this.orderRepository = orderRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @DisplayName("should save order")
    @ParameterizedTest
    @MethodSource("orderProvider")
    void shouldSaveOrder(Order order) {
        userDetailsRepository.save(order.getCreatedBy());

        order.getOrderedCertificates().forEach(giftCertificate -> {
            tagRepository.findAllOrSaveIfNotExists(giftCertificate.getTags());
            userDetailsRepository.save(giftCertificate.getCreatedBy());
        });

        giftCertificateRepository.saveAll(order.getOrderedCertificates());
        orderRepository.save(order);

        Assertions.assertThat(order.getId()).isNotNull();

        Pageable pageable = PageRequest.of(0, 50);
        List<Order> orders = orderRepository.findAllByCreatedBy_Id(order.getCreatedBy().getId(), pageable).toList();

        Assertions.assertThat(orders).contains(order);

        entityManager.detach(order);

        Optional<Order> entity = orderRepository.findById(order.getId());

        Assertions.assertThat(entity).contains(order);
    }

    private static Stream<Arguments> orderProvider() {
        return IntStream.range(1, 51).mapToObj(value -> {
            Order order = new Order();
            order.setCreatedBy(generateUserDetails(value));
            order.setCreateDate(LocalDateTime.now());
            order.setLastModifiedBy(generateUserDetails(value));
            order.setLastModifiedDate(LocalDateTime.now());
            order.setOrderedCertificates(List.of(generateGiftCertificate(value), generateGiftCertificate(value + 1)));
            order.setTotalPrice(order.getOrderedCertificates().stream().map(GiftCertificate::getPrice)
                    .reduce(BigDecimal::add).orElse(BigDecimal.ZERO));

            return Arguments.of(order);
        });
    }

    private static GiftCertificate generateGiftCertificate(int value) {
        Random random = new Random();

        int randomFrom = 2 + random.nextInt(48);
        int randomTo = randomFrom + (2 + random.nextInt(48));

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setCertificateName("Certificate #" + value);
        giftCertificate.setDescription("Description #" + value);
        giftCertificate.setIsExpired(false);
        giftCertificate.setDuration(LocalDateTime.now().plusMinutes(1));
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setTags(generateTags(randomFrom, randomTo));
        giftCertificate.setPrice(BigDecimal.valueOf(randomFrom * randomTo));
        giftCertificate.setLastModifiedDate(LocalDateTime.now());

        UserDetails userDetails = generateUserDetails(value);

        giftCertificate.setCreatedBy(userDetails);
        giftCertificate.setLastModifiedBy(userDetails);

        return giftCertificate;
    }

    private static List<Tag> generateTags(int from, int to) {
        return IntStream.range(from, to).mapToObj(value -> {
            Tag tag = new Tag();
            tag.setTagName("Tag #" + value);

            return tag;
        }).collect(Collectors.toList());
    }

    private static UserDetails generateUserDetails(int value) {
        UserDetails userDetails = new UserDetails();
        userDetails.setFirstName("First name #" + value);
        userDetails.setDob(LocalDate.now().minusYears(value));
        userDetails.setLastName("Last name #" + value);
        userDetails.setPhoneNumber("+998 99 999 99 99");
        userDetails.setPatronymic("Patronymic #" + value);
        userDetails.setSendEmail(true);
        userDetails.setId("User_#" + value + "@gmail.com");
        userDetails.setFullName(userDetails.getFirstName() + " " + userDetails.getLastName() + " " + userDetails.getPatronymic());

        return userDetails;
    }

}
