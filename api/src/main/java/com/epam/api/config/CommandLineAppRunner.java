package com.epam.api.config;

import com.epam.business.model.request.CreateGiftCertificateRequest;
import com.epam.business.model.request.CreateUserDetailsRequest;
import com.epam.business.model.request.TagRequest;
import com.epam.business.service.GiftCertificateService;
import com.epam.business.service.TagService;
import com.epam.business.service.UserDetailsService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class CommandLineAppRunner implements CommandLineRunner {

    @Value("${tags}")
    private Integer tags;

    @Value("${users}")
    private Integer users;

    @Value("${giftCertificates}")
    private Integer giftCertificates;

    private final TagService tagService;
    private final UserDetailsService userDetailsService;
    private final GiftCertificateService giftCertificateService;

    @Override
    public void run(String... args) throws Exception {
        createUserDetails();
        createTags();
        createGiftCertificates();
    }

    private void createUserDetails() {
        for (int i = 1; i <= users; i++) {
            CreateUserDetailsRequest request = new CreateUserDetailsRequest();
            request.setUsername("username_" + i + "@gmail.com");
            request.setFirstName("first_name_" + i);
            request.setLastName("last_name_" + i);
            request.setPatronymic("patronymic_" + i);
            request.setDob(LocalDate.now().minusYears(i));
            request.setSendEmail(true);
            userDetailsService.create(request);
        }
    }

    private void createTags() {
        tagService.create(IntStream.range(1, tags).mapToObj(value -> {
            TagRequest tagRequest = new TagRequest();
            tagRequest.setTagName("Tag #" + value);
            return tagRequest;
        }).collect(Collectors.toList()));
    }

    private void createGiftCertificates() {
        for (int i = 1; i <= giftCertificates; i++) {
            CreateGiftCertificateRequest request = new CreateGiftCertificateRequest();
            request.setCertificateName("certificate_name_" + i);
            request.setCreatedBy("username_" + (1 + (int) (Math.random() * users)) + "@gmail.com");
            request.setDescription("description_" + i);
            request.setDuration((long) i);
            request.setPrice(BigDecimal.valueOf(giftCertificates * 1000 - i));
            request.setTags(IntStream.range(1, (1 + (int) (Math.random() * (tags / 10)))).mapToObj(value -> {
                TagRequest tagRequest = new TagRequest();
                tagRequest.setTagName("Tag #" + (1 + (int) (Math.random() * tags)));
                return tagRequest;
            }).collect(Collectors.toList()));

            giftCertificateService.create(request);
        }
    }
}
