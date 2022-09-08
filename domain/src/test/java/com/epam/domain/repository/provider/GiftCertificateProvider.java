package com.epam.domain.repository.provider;

import com.epam.domain.entity.certificate.GiftCertificate;
import com.epam.domain.entity.certificate.Tag;
import com.epam.domain.entity.user.UserDetails;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 9/4/2022
 */
public class GiftCertificateProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        Random random = new Random();
        return IntStream.range(1, 51).mapToObj(value -> {
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

            UserDetails userDetails = generateUser(value);

            giftCertificate.setCreatedBy(userDetails);
            giftCertificate.setLastModifiedBy(userDetails);

            return Arguments.of(giftCertificate);
        });
    }

    private UserDetails generateUser(int index) {
        UserDetails userDetails = new UserDetails();
        userDetails.setFirstName("First name #" + index);
        userDetails.setDob(LocalDate.now().minusYears(index));
        userDetails.setLastName("Last name #" + index);
        userDetails.setPhoneNumber("+998 99 999 99 99");
        userDetails.setPatronymic("Patronymic #" + index);
        userDetails.setSendEmail(true);
        userDetails.setId("User_#" + index + "@gmail.com");
        return userDetails;
    }

    private List<Tag> generateTags(int from, int to) {
        return IntStream.range(from, to).mapToObj(value -> {
            Tag tag = new Tag();
            tag.setTagName("Tag #" + value);

            return tag;
        }).collect(Collectors.toList());
    }

}
