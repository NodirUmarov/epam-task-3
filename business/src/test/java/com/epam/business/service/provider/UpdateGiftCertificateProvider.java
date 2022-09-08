package com.epam.business.service.provider;

import com.epam.business.model.request.UpdateGiftCertificateRequest;
import java.math.BigDecimal;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class UpdateGiftCertificateProvider implements ArgumentsProvider {
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return LongStream.rangeClosed(1, 50).mapToObj(i -> {
            UpdateGiftCertificateRequest request = new UpdateGiftCertificateRequest();
            request.setGiftCertificateName("GiftCertificate #" + i);
            request.setDescription("Description #" + i);
            request.setPrice(BigDecimal.TEN.multiply(BigDecimal.valueOf(i)));
            request.setDuration(i);
            return Arguments.of(i, request);
        });
    }
}
