package com.epam.domain.repository;

import com.epam.domain.config.DomainConfig;
import com.epam.domain.entity.certificate.GiftCertificate;
import com.epam.domain.repository.provider.GiftCertificateProvider;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 9/4/2022
 */
@DataJpaTest
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") // IDE thinks there is no bean of the repository
@ContextConfiguration(classes = DomainConfig.class)
class GiftCertificateRepositoryTest {

    private final TagRepository tagRepository;
    private final EntityManager entityManager;
    private final UserDetailsRepository userDetailsRepository;
    private final GiftCertificateRepository giftCertificateRepository;

    @Autowired
    public GiftCertificateRepositoryTest(TagRepository tagRepository, EntityManager entityManager, UserDetailsRepository userDetailsRepository, GiftCertificateRepository giftCertificateRepository) {
        this.tagRepository = tagRepository;
        this.entityManager = entityManager;
        this.userDetailsRepository = userDetailsRepository;
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @DisplayName("Should gift-certificate and find most used tags and retrieve")
    @ParameterizedTest
    @ArgumentsSource(GiftCertificateProvider.class)
    void shouldSaveGiftCertificateAndThenDelete(GiftCertificate giftCertificate) {
        userDetailsRepository.save(giftCertificate.getCreatedBy());
        tagRepository.findAllOrSaveIfNotExists(giftCertificate.getTags());
        giftCertificateRepository.saveAndFlush(giftCertificate);

        Assertions.assertThat(giftCertificateRepository.existsByCertificateName(giftCertificate.getCertificateName())).isTrue();

        Optional<GiftCertificate> entityByName = giftCertificateRepository.findByCertificateName(giftCertificate.getCertificateName());
        Optional<GiftCertificate> entityById = giftCertificateRepository.findById(giftCertificate.getId());

        entityManager.detach(giftCertificate);

        Assertions.assertThat(entityByName).isPresent();
        Assertions.assertThat(entityById).isPresent();

        Assertions.assertThat(entityByName.get()).usingRecursiveComparison().isEqualTo(entityById.get());

        Pageable pageable = PageRequest.of(0, 50);

        String tagName = giftCertificate.getTags().get(0).getTagName();

        List<GiftCertificate> giftCertificates = giftCertificateRepository.findByTags_TagName(tagName, pageable).toList();

        Assertions.assertThat(giftCertificates).contains(entityById.get());

        giftCertificateRepository.deleteByCertificateName(giftCertificate.getCertificateName());

        Assertions.assertThat(giftCertificateRepository.existsByCertificateName(giftCertificate.getCertificateName())).isFalse();
    }

    @DisplayName("should not save and throw data integrity violation exception")
    @ParameterizedTest
    @ArgumentsSource(GiftCertificateProvider.class)
    void shouldNotSaveAndThrowDataIntegrityViolationException(GiftCertificate giftCertificate) {
        giftCertificateRepository.save(giftCertificate);
        entityManager.detach(giftCertificate);

        giftCertificate.setId(null);

        Assertions.assertThatThrownBy(() -> giftCertificateRepository.save(giftCertificate)).isInstanceOf(DataIntegrityViolationException.class);
    }



}